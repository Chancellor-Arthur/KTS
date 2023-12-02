package ru.dubna.kts.models.question.dtos;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dubna.kts.models.answer.dtos.FullAnswerOutputDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullQuestionOutputDto {
	private UUID id;
	private String question;
	private List<FullAnswerOutputDto> answers;
}
