package ru.dubna.kts.models.auth.validators.registration;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.dubna.kts.exceptions.specific.BadRequestException;
import ru.dubna.kts.models.auth.dtos.CredentialsDto;
import ru.dubna.kts.models.auth.validators.ValidatorChain;

@Component
@RequiredArgsConstructor
public class CredentialsLengthValidatorChain extends ValidatorChain {
	@Override
	public void validate(Object target) {
		CredentialsDto credentialsDto = (CredentialsDto) target;

		if (credentialsDto.getUsername().length() > 50 || credentialsDto.getUsername().length() < 3)
			throw new BadRequestException("Имя пользователя не соответствует формату");

		if (credentialsDto.getPassword().length() > 256 || credentialsDto.getPassword().length() < 3)
			throw new BadRequestException("Пароль не соответствует формату");

		if (next != null)
			next.validate(target);
	}
}
