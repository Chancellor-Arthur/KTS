package ru.dubna.kts.models.auth.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.dubna.kts.models.auth.dtos.CookieInfoDto;
import ru.dubna.kts.models.auth.dtos.CredentialsDto;
import ru.dubna.kts.models.auth.validators.AuthValidator;
import ru.dubna.kts.models.auth.validators.RegistrationValidator;
import ru.dubna.kts.models.user.dtos.UserOutputDto;

@Service
@RequiredArgsConstructor
public class ProxyAuthService implements AuthService {
	private final AuthServiceImpl authServiceImpl;
	private final RegistrationValidator registrationValidator;
	private final AuthValidator authValidator;

	@Override
	public CookieInfoDto signIn(CredentialsDto credentialsDto) {
		authValidator.validate(credentialsDto);

		return authServiceImpl.signIn(credentialsDto);
	}

	@Override
	public UserOutputDto signUp(CredentialsDto credentialsDto) {
		registrationValidator.validate(credentialsDto);

		return authServiceImpl.signUp(credentialsDto);
	}
}
