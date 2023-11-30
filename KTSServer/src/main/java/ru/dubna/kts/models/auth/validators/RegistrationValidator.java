package ru.dubna.kts.models.auth.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dubna.todolist.exceptions.specific.UnauthorizedException;
import ru.dubna.todolist.models.auth.dtos.UserInputDto;
import ru.dubna.todolist.models.user.UserService;

@Component
@RequiredArgsConstructor
public class RegistrationValidator implements Validator {
	private final UserService userService;

	public void validate(Object target) {
		UserInputDto userInputDto = (UserInputDto) target;

		if (!userInputDto.getPassword().equals(userInputDto.getConfirmPassword()))
			throw new UnauthorizedException("Пароли не совпадают");

		if (userService.findByUsername(userInputDto.getUsername()).isPresent())
			throw new UnauthorizedException("Пользователь с указанным именем уже существует");
	}
}
