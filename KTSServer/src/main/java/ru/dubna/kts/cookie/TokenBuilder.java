package ru.dubna.kts.cookie;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dubna.kts.models.auth.dtos.CookieInfoDto;

import java.util.UUID;

@Data
@NoArgsConstructor
public class TokenBuilder implements Token {
	private StringBuilder token = new StringBuilder();

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
		token.append(CookieUtils.calculateHmac(cookieInfoDto)).append("&");
		return this;
	}

	@Override
	public String build() {
		token.setLength(token.length() - 1);
		return token.toString();
	}
}
