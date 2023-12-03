package ru.dubna.kts.models.auth.validators;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.dubna.kts.models.auth.validators.registration.CredentialsLengthValidatorChain;
import ru.dubna.kts.models.auth.validators.registration.LogValidatorChain;
import ru.dubna.kts.models.auth.validators.registration.RegistrationDuplicateValidatorChain;
import ru.dubna.kts.models.user.UserService;

@Component
@RequiredArgsConstructor
public class RegistrationValidationChain {
	private final UserService userService;

	public void validate(Object target) {
		RegistrationDuplicateValidatorChain duplicateValidator = new RegistrationDuplicateValidatorChain(userService);
		CredentialsLengthValidatorChain credentialsLengthValidator = new CredentialsLengthValidatorChain();
		LogValidatorChain logValidator = new LogValidatorChain();

		credentialsLengthValidator.setNext(duplicateValidator).setNext(logValidator);
	}
}
