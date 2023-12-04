package ru.dubna.kts.models.auth.validators;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.dubna.kts.models.auth.validators.auth.AuthCredentialsLengthValidatorChain;
import ru.dubna.kts.models.auth.validators.auth.AuthVerifyValidatorChain;

@Component
@RequiredArgsConstructor
public class AuthValidationChain {
	private final AuthenticationManager authenticationManager;

	public void validate(Object target) {
		AuthVerifyValidatorChain verifyValidator = new AuthVerifyValidatorChain(authenticationManager);
		AuthCredentialsLengthValidatorChain credentialsLengthValidator = new AuthCredentialsLengthValidatorChain();

		credentialsLengthValidator.setNext(verifyValidator);
		credentialsLengthValidator.validate(target);
	}
}
