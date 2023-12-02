package ru.dubna.kts.models.userAnswer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dubna.kts.models.answer.dtos.AnswerOutputDto;
import ru.dubna.kts.models.question.dtos.FullQuestionOutputDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAnswerOutputDto {
	private FullQuestionOutputDto questionAnswer;
	private AnswerOutputDto userAnswer;
}
