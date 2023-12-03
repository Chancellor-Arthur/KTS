package ru.dubna.kts.models.auth.validators.registration;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.dubna.kts.models.auth.dtos.CredentialsDto;
import ru.dubna.kts.models.auth.validators.ValidatorChain;

@Component
@Slf4j
public class LogValidatorChain extends ValidatorChain {
	@Override
	public void validate(Object target) {
		CredentialsDto credentialsDto = (CredentialsDto) target;

		log.info("[INFO]: Новый пользователь: " + credentialsDto.getUsername());

		if (next != null)
			next.validate(target);
	}
}
