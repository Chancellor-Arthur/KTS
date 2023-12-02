package ru.dubna.kts.models.auth.service;

import ru.dubna.kts.models.auth.dtos.CookieInfoDto;
import ru.dubna.kts.models.auth.dtos.CredentialsDto;
import ru.dubna.kts.models.user.dtos.UserOutputDto;

public interface AuthService {
	CookieInfoDto signIn(CredentialsDto credentialsDto);

	UserOutputDto signUp(CredentialsDto credentialsDto);
}
