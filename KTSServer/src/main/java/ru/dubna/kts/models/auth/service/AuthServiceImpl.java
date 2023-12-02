package ru.dubna.kts.models.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.dubna.kts.models.auth.dtos.CookieInfoDto;
import ru.dubna.kts.models.auth.dtos.CredentialsDto;
import ru.dubna.kts.models.user.User;
import ru.dubna.kts.models.user.UserService;
import ru.dubna.kts.models.user.dtos.UserOutputDto;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final UserService userService;

	@Override
	public CookieInfoDto signIn(CredentialsDto credentialsDto) {
		User user = userService.getByUsername(credentialsDto.getUsername());
		return new CookieInfoDto(user.getId(), user.getUsername());
	}

	@Override
	public UserOutputDto signUp(CredentialsDto credentialsDto) {
		User user = userService.create(credentialsDto);
		return new UserOutputDto(user.getId(), user.getUsername());
	}
}
