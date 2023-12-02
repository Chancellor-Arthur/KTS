package ru.dubna.kts.xlsx;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
public class ApachePoiStrategy implements ExcelStrategy {
	@Value("${file.location}")
	private String fileLocation;

	private final QuestionService questionService;
	private final AnswerService answerService;

	@Override
	public List<Question> readAndSave() throws IOException {
		FileInputStream file = new FileInputStream(fileLocation);
		Workbook workbook = new XSSFWorkbook(file);

		Sheet sheet = workbook.getSheetAt(0);

		List<Question> questions = new ArrayList<>();

		int startRow = 1;
		int endRow = 6;
		for (int i = startRow; i < endRow; i++) {
			Row row = sheet.getRow(i);

			QuestionInputDto questionInputDto = new QuestionInputDto(row.getCell(2).getStringCellValue());
			Question question = questionService.create(questionInputDto);

			List<AnswerInputDto> answerInputDtos = new ArrayList<>();

			answerInputDtos.add(new AnswerInputDto(question.getId(), row.getCell(3).getStringCellValue(),
					row.getCell(6).getNumericCellValue() == 1));
			answerInputDtos.add(new AnswerInputDto(question.getId(), row.getCell(4).getStringCellValue(),
					row.getCell(7).getNumericCellValue() == 1));
			answerInputDtos.add(new AnswerInputDto(question.getId(), row.getCell(5).getStringCellValue(),
					row.getCell(8).getNumericCellValue() == 1));

			List<Answer> answers = answerService.bulkCreate(answerInputDtos);
			question.setAnswers(answers);
			questions.add(question);
		}

		return questions;
	}
}
