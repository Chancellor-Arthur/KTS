package ru.dubna.kts.models.auth;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ru.dubna.kts.config.security.CookieAuthenticationFilter;
import ru.dubna.kts.cookie.TokenCreator;
import ru.dubna.kts.exceptions.dtos.BadRequestExceptionPayload;
import ru.dubna.kts.exceptions.dtos.DefaultExceptionPayload;
import ru.dubna.kts.models.auth.dtos.CookieInfoDto;
import ru.dubna.kts.models.auth.dtos.CredentialsDto;
import ru.dubna.kts.models.auth.service.AuthService;
import ru.dubna.kts.models.user.dtos.UserOutputDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Авторизация/Регистрация")
public class AuthController {
	private final AuthService authService;
	private final TokenCreator tokenCreator;

	@Autowired
	public AuthController(TokenCreator tokenCreator, @Qualifier("proxyAuthService") AuthService authService) {
		this.tokenCreator = tokenCreator;
		this.authService = authService;
	}

	private void createCookie(CookieInfoDto cookieInfo, HttpServletResponse servletResponse) {
		Cookie authCookie = new Cookie(CookieAuthenticationFilter.cookieName, tokenCreator.createToken(cookieInfo));
		authCookie.setHttpOnly(true);
		authCookie.setSecure(true);
		authCookie.setMaxAge((int) Duration.of(1, ChronoUnit.DAYS).toSeconds());
		authCookie.setPath("/");

		servletResponse.addCookie(authCookie);
	}

	private void deleteCookie(HttpServletResponse servletResponse) {
		Cookie authCookie = new Cookie(CookieAuthenticationFilter.cookieName, "");
		authCookie.setHttpOnly(true);
		authCookie.setSecure(true);
		authCookie.setMaxAge(0);
		authCookie.setPath("/");

		servletResponse.addCookie(authCookie);
	}

	@PostMapping("/login")
	@Operation(summary = "Авторизация пользователя")
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = CookieInfoDto.class)) })
	@ApiResponse(responseCode = "401", content = {
			@Content(schema = @Schema(implementation = DefaultExceptionPayload.class)) })
	@ApiResponse(responseCode = "400", content = {
			@Content(schema = @Schema(implementation = BadRequestExceptionPayload.class)) })
	public CookieInfoDto signIn(@Valid @RequestBody CredentialsDto credentials, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		Optional<Cookie> cookieAuth = Stream.of(Optional.ofNullable(servletRequest.getCookies()).orElse(new Cookie[0]))
				.filter(cookie -> CookieAuthenticationFilter.cookieName.equals(cookie.getName())).findFirst();

		CookieInfoDto cookieInfo = authService.signIn(credentials);
		if (cookieAuth.isEmpty()) {
			createCookie(cookieInfo, servletResponse);
		}

		return cookieInfo;
	}

	@PostMapping("/logout")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Выход пользователя из системы")
	@ApiResponse(responseCode = "204")
	public void signOut(HttpServletResponse servletResponse) {
		deleteCookie(servletResponse);
	}

	@PostMapping("/registration")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Регистрация пользователя")
	@ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = UserOutputDto.class)) })
	@ApiResponse(responseCode = "400", content = {
			@Content(schema = @Schema(implementation = BadRequestExceptionPayload.class)) })
	public UserOutputDto signUp(@Valid @RequestBody CredentialsDto credentialsDto) {
		return authService.signUp(credentialsDto);
	}
}
