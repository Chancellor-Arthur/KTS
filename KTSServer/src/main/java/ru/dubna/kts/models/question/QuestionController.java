package ru.dubna.kts.models.question;

import java.io.IOException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.dubna.kts.exceptions.dtos.DefaultExceptionPayload;
import ru.dubna.kts.models.answer.dtos.AnswerOutputDto;
import ru.dubna.kts.models.question.dtos.QuestionOutputDto;
import ru.dubna.kts.xlsx.ExcelStrategy;

@RestController
@RequestMapping("/questions")
@Tag(name = "Вопросы")
@ApiResponse(responseCode = "401", content = {
		@Content(schema = @Schema(implementation = DefaultExceptionPayload.class)) })
public class QuestionController {
	private final QuestionService questionService;
	private final ExcelStrategy excelStrategy;

	@Autowired
	public QuestionController(QuestionService questionService,
			@Qualifier("apachePoiStrategy") ExcelStrategy excelStrategy) {
		this.questionService = questionService;
		this.excelStrategy = excelStrategy;
	}

	@PostMapping("/import")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Импорт вопросов и ответов")
	@ApiResponse(responseCode = "201", content = {
			@Content(array = @ArraySchema(schema = @Schema(implementation = QuestionOutputDto.class))) })
	public List<QuestionOutputDto> importAll() throws IOException {
		List<Question> questions = excelStrategy.readAndSave();
		return questions.stream()
				.map(question -> new QuestionOutputDto(question.getId(), question.getQuestion(),
						question.getAnswers().stream()
								.map(answer -> new ModelMapper().map(answer, AnswerOutputDto.class)).toList()))
				.toList();
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Удаление вопросов и ответов")
	@ApiResponse(responseCode = "204")
	public void deleteAll() {
		questionService.deleteAll();
	}

	@GetMapping("/all")
	@Operation(summary = "Получение полного списка вопросов")
	@ApiResponse(responseCode = "200", content = {
			@Content(array = @ArraySchema(schema = @Schema(implementation = QuestionOutputDto.class))) })
	public List<QuestionOutputDto> getAll() {
		return questionService.getAll().stream()
				.map(question -> new QuestionOutputDto(question.getId(), question.getQuestion(),
						question.getAnswers().stream()
								.map(answer -> new ModelMapper().map(answer, AnswerOutputDto.class)).toList()))
				.toList();
	}

	@GetMapping("/test")
	@Operation(summary = "Получение списка вопросов к тесту")
	@ApiResponse(responseCode = "200", content = {
			@Content(schema = @Schema(implementation = QuestionOutputDto.class)) })
	public List<QuestionOutputDto> getForTest() {
		List<Question> questions = questionService.get20RandomQuestions();
		return questions.stream()
				.map(question -> new QuestionOutputDto(question.getId(), question.getQuestion(),
						question.getAnswers().stream()
								.map(answer -> new ModelMapper().map(answer, AnswerOutputDto.class)).toList()))
				.toList();
	}
}
