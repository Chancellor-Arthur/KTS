package ru.dubna.kts.config.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ru.dubna.kts.cookie.CookieUtils;
import ru.dubna.kts.models.auth.dtos.CookieInfoDto;

@Component
@RequiredArgsConstructor
public class CookieAuthenticationFilter extends OncePerRequestFilter {
	public final static String cookieName = "JSESSIONID";

	private final CookieUtils cookieUtils;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		Optional<Cookie> cookieAuth = Stream.of(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
				.filter(cookie -> cookieName.equals(cookie.getName())).findFirst();

		if (cookieAuth.isPresent()) {
			String[] parts = cookieAuth.get().getValue().split("&");

			UUID id = UUID.fromString(parts[0]);
			String username = parts[1];
			String hmac = parts[2];

			if (hmac.equals(cookieUtils.calculateHmac(new CookieInfoDto(id, username)))
					&& SecurityContextHolder.getContext().getAuthentication() == null) {
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, id,
						Collections.singletonList(new SimpleGrantedAuthority("ALL")));
				SecurityContextHolder.getContext().setAuthentication(token);
			}
		}

		filterChain.doFilter(request, response);
	}
}
