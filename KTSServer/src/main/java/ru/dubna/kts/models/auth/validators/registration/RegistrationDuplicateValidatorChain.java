package ru.dubna.kts.models.auth.validators.registration;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.dubna.kts.exceptions.specific.UnauthorizedException;
import ru.dubna.kts.models.auth.dtos.CredentialsDto;
import ru.dubna.kts.models.auth.validators.ValidatorChain;
import ru.dubna.kts.models.user.UserService;

@Component
@RequiredArgsConstructor
public class RegistrationDuplicateValidatorChain extends ValidatorChain {
	private final UserService userService;

	public void validate(Object target) {
		CredentialsDto userInputDto = (CredentialsDto) target;

		if (userService.findByUsername(userInputDto.getUsername()).isPresent())
			throw new UnauthorizedException("Пользователь с указанным именем уже существует");

		if (next != null)
			next.validate(target);
	}
}
