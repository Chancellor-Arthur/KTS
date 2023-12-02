package ru.dubna.kts.models.userAnswer.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortUserAnswersOutputDto {
	private UUID id;
	private UUID questionId;
	private UUID answerId;
}
