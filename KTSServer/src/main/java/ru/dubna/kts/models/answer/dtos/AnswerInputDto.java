package ru.dubna.kts.models.answer.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerInputDto {
	private UUID questionId;
	private String answer;
	private boolean isCorrect;
}
