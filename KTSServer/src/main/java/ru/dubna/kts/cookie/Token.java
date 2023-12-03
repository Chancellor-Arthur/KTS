package ru.dubna.kts.cookie;

import java.util.UUID;

import ru.dubna.kts.models.auth.dtos.CookieInfoDto;

public interface Token {
	Token addUserId(UUID userId);

	Token addUsername(String username);

	Token addSignature(CookieInfoDto cookieInfoDto);

	String build();
}
