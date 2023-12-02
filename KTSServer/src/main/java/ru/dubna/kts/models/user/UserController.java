package ru.dubna.kts.models.user;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.dubna.kts.exceptions.dtos.BadRequestExceptionPayload;
import ru.dubna.kts.exceptions.dtos.DefaultExceptionPayload;
import ru.dubna.kts.models.user.dtos.UserOutputDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "Пользователи")
@ApiResponse(responseCode = "401", content = {
		@Content(schema = @Schema(implementation = DefaultExceptionPayload.class)) })
public class UserController {
	private final UserService userService;

	@GetMapping
	@Operation(summary = "Получение списка пользователей")
	@ApiResponse(responseCode = "200", content = {
			@Content(array = @ArraySchema(schema = @Schema(implementation = UserOutputDto.class))) })
	public List<UserOutputDto> getAll() {
		return userService.getAll().stream().map(user -> new ModelMapper().map(user, UserOutputDto.class)).toList();
	}

	@GetMapping("/{username}")
	@Operation(summary = "Получение пользователя по уникальному имени")
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserOutputDto.class)) })
	@ApiResponse(responseCode = "400", content = {
			@Content(schema = @Schema(implementation = BadRequestExceptionPayload.class)) })
	public UserOutputDto getOne(@PathVariable String username) {
		User user = userService.getByUsername(username);

		return new ModelMapper().map(user, UserOutputDto.class);
	}
}
