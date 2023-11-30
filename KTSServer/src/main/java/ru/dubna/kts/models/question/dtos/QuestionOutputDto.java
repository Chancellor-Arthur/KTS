package ru.dubna.kts.models.question.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dubna.todolist.models.tasks.TaskStatuses;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionOutputDto {
	private int id;
	private String name;
	private String description;
	private TaskStatuses status;
}
