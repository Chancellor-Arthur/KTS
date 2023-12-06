package ru.dubna.kts.cookie;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.dubna.kts.models.auth.dtos.CookieInfoDto;

@Component
@RequiredArgsConstructor
public class TokenCreator {
	private final CookieUtils cookieUtils;

	public String createToken(CookieInfoDto cookieInfo) {
		return new TokenBuilder(cookieUtils).addUserId(cookieInfo.getId()).addUsername(cookieInfo.getUsername())
				.addSignature(cookieInfo).build();
	}
}
