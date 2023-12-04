package ru.dubna.kts.models.auth.validators.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.dubna.kts.exceptions.specific.UnauthorizedException;
import ru.dubna.kts.models.auth.dtos.CredentialsDto;
import ru.dubna.kts.models.auth.validators.ValidatorChain;

@Component
@RequiredArgsConstructor
public class AuthVerifyValidatorChain extends ValidatorChain {
	private final AuthenticationManager authenticationManager;

	@Override
	public void validate(Object target) {
		CredentialsDto credentialsDto = (CredentialsDto) target;

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentialsDto.getUsername(),
					credentialsDto.getPassword()));
		} catch (BadCredentialsException exception) {
			throw new UnauthorizedException("Неправильный логин или пароль");
		}

		if (next != null)
			next.validate(target);
	}
}
