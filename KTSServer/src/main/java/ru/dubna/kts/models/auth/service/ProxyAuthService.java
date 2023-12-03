package ru.dubna.kts.models.auth.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.dubna.kts.models.auth.dtos.CookieInfoDto;
import ru.dubna.kts.models.auth.dtos.CredentialsDto;
import ru.dubna.kts.models.auth.validators.AuthValidationChain;
import ru.dubna.kts.models.auth.validators.RegistrationValidationChain;
import ru.dubna.kts.models.user.dtos.UserOutputDto;

@Service
@RequiredArgsConstructor
public class ProxyAuthService implements AuthService {
	private final AuthServiceImpl authServiceImpl;
	private final RegistrationValidationChain registrationValidationChain;
	private final AuthValidationChain authValidationChain;

	@Override
	public CookieInfoDto signIn(CredentialsDto credentialsDto) {
		authValidationChain.validate(credentialsDto);

		return authServiceImpl.signIn(credentialsDto);
	}

	@Override
	public UserOutputDto signUp(CredentialsDto credentialsDto) {
		registrationValidationChain.validate(credentialsDto);

		return authServiceImpl.signUp(credentialsDto);
	}
}
