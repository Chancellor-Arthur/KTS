package ru.dubna.kts.cookie;

import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.dubna.kts.models.auth.dtos.CookieInfoDto;

@Data
@RequiredArgsConstructor
@Component
public class TokenBuilder implements Token {
	private StringBuilder token = new StringBuilder();
	private final CookieUtils cookieUtils;

	@Override
	public Token addUserId(UUID userId) {
		token.append(userId).append("&");
		return this;
	}

	@Override
	public Token addUsername(String username) {
		token.append(username).append("&");
		return this;
	}

	@Override
	public Token addSignature(CookieInfoDto cookieInfoDto) {
		token.append(cookieUtils.calculateHmac(cookieInfoDto)).append("&");
		return this;
	}

	@Override
	public String build() {
		token.setLength(token.length() - 1);
		return token.toString();
	}
}
