package ru.dubna.kts.models.userAnswer;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ru.dubna.kts.exceptions.dtos.BadRequestExceptionPayload;
import ru.dubna.kts.exceptions.dtos.DefaultExceptionPayload;
import ru.dubna.kts.models.answer.Answer;
import ru.dubna.kts.models.answer.AnswerService;
import ru.dubna.kts.models.answer.dtos.AnswerOutputDto;
import ru.dubna.kts.models.answer.dtos.FullAnswerOutputDto;
import ru.dubna.kts.models.question.Question;
import ru.dubna.kts.models.question.QuestionService;
import ru.dubna.kts.models.question.dtos.FullQuestionOutputDto;
import ru.dubna.kts.models.userAnswer.dtos.ShortUserAnswersOutputDto;
import ru.dubna.kts.models.userAnswer.dtos.UserAnswerInputDto;
import ru.dubna.kts.models.userAnswer.dtos.UserAnswerOutputDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/answers")
@Tag(name = "Ответы")
@ApiResponse(responseCode = "401", content = {
		@Content(schema = @Schema(implementation = DefaultExceptionPayload.class)) })
public class UserAnswerController {
	private final UserAnswerService userAnswerService;
	private final AnswerService answerService;
	private final QuestionService questionService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Отправить ответы на вопросы")
	@ApiResponse(responseCode = "201", content = {
			@Content(array = @ArraySchema(schema = @Schema(implementation = ShortUserAnswersOutputDto.class))) })
	@ApiResponse(responseCode = "400", content = {
			@Content(schema = @Schema(implementation = BadRequestExceptionPayload.class)) })
	public List<ShortUserAnswersOutputDto> createUserAnswers(@Valid @RequestBody UserAnswerInputDto userAnswerInputDto,
			Authentication authentication) {
		List<UserAnswer> userAnswers = userAnswerService.bulkCreate(userAnswerInputDto,
				(UUID) authentication.getCredentials());

		return userAnswers.stream().map(userAnswer -> {
			Answer answer = answerService.get(userAnswer.getAnswer().getId());

			return new ShortUserAnswersOutputDto(userAnswer.getId(), answer.getQuestion().getId(),
					userAnswer.getAnswer().getId());
		}).toList();
	}

	@GetMapping
	@Operation(summary = "Получение списка ответов пользователя")
	@ApiResponse(responseCode = "200", content = {
			@Content(array = @ArraySchema(schema = @Schema(implementation = UserAnswerOutputDto.class))) })
	public List<UserAnswerOutputDto> getAll(Authentication authentication) {
		List<UserAnswer> userAnswers = userAnswerService.getAll((UUID) authentication.getCredentials());

		return userAnswers.stream().map(userAnswer -> {
			Answer answer = answerService.get(userAnswer.getAnswer().getId());
			Question question = questionService.get(answer.getQuestion().getId());

			return new UserAnswerOutputDto(
					new FullQuestionOutputDto(question.getId(), question.getQuestion(), question.getAnswers().stream()
							.map(a -> new ModelMapper().map(a, FullAnswerOutputDto.class)).toList()),
					new ModelMapper().map(answer, AnswerOutputDto.class));
		}).toList();
	}
}
