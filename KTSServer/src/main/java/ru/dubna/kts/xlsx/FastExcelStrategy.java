package ru.dubna.kts.xlsx;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.poi.util.DocumentFormatException;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.dubna.kts.models.answer.Answer;
import ru.dubna.kts.models.answer.AnswerService;
import ru.dubna.kts.models.answer.dtos.AnswerInputDto;
import ru.dubna.kts.models.question.Question;
import ru.dubna.kts.models.question.QuestionService;
import ru.dubna.kts.models.question.dtos.QuestionInputDto;

@Component
@RequiredArgsConstructor
public class FastExcelStrategy implements ExcelStrategy {
	@Value("${file.location}")
	private String fileLocation;

	private final QuestionService questionService;
	private final AnswerService answerService;

	@Override
	public List<Question> readAndSave() throws IOException {
		FileInputStream file = new FileInputStream(fileLocation);
		ReadableWorkbook workbook = new ReadableWorkbook(file);
		Sheet sheet = workbook.getFirstSheet();

		Stream<Row> rows = sheet.openStream();

		List<Question> questions = new ArrayList<>();

		rows.skip(1).forEach(row -> {
			QuestionInputDto questionInputDto = new QuestionInputDto(row.getCellAsString(2)
					.orElseThrow(() -> new DocumentFormatException("Не удалось получить значение")));
			Question question = questionService.create(questionInputDto);

			List<AnswerInputDto> answerInputDtos = new ArrayList<>();

			answerInputDtos.add(new AnswerInputDto(question.getId(),
					row.getCellAsString(3)
							.orElseThrow(() -> new DocumentFormatException("Не удалось получить значение")),
					row.getCellAsNumber(6).orElse(BigDecimal.valueOf(0)).intValue() == 1));
			answerInputDtos.add(new AnswerInputDto(question.getId(),
					row.getCellAsString(4)
							.orElseThrow(() -> new DocumentFormatException("Не удалось получить значение")),
					row.getCellAsNumber(7).orElse(BigDecimal.valueOf(0)).intValue() == 1));
			answerInputDtos.add(new AnswerInputDto(question.getId(),
					row.getCellAsString(5)
							.orElseThrow(() -> new DocumentFormatException("Не удалось получить значение")),
					row.getCellAsNumber(8).orElse(BigDecimal.valueOf(0)).intValue() == 1));

			List<Answer> answers = answerService.bulkCreate(answerInputDtos);
			question.setAnswers(answers);
			questions.add(question);
		});

		return questions;
	}
}
