package ru.dubna.kts.cookie;

import ru.dubna.kts.models.auth.dtos.CookieInfoDto;

import java.util.UUID;

public interface Token {
	Token addUserId(UUID userId);

	Token addUsername(String username);

	Token addSignature(CookieInfoDto cookieInfoDto);

	String build();
}
