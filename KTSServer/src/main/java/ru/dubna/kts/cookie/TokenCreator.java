package ru.dubna.kts.cookie;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.dubna.kts.models.auth.dtos.CookieInfoDto;

@Component
@RequiredArgsConstructor
public class TokenCreator {
	private final Token tokenBuilder;

	public String createToken(CookieInfoDto cookieInfo) {
		return tokenBuilder.addUserId(cookieInfo.getId()).addUsername(cookieInfo.getUsername()).addSignature(cookieInfo)
				.build();
	}
}
