package ru.dubna.kts.models.answer;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dubna.kts.config.db.BaseEntity;
import ru.dubna.kts.models.question.Question;

@Entity
@Table(name = "answers")
@Getter
@Setter
@NoArgsConstructor
public class Answer extends BaseEntity {
	@Column(name = "answer")
	private String answer;

	@Column(name = "is_correct")
	private boolean isCorrect;

	@ManyToOne
	@JoinColumn(name = "question_id")
	private Question question;

	public Answer(UUID id) {
		this.id = id;
	}

	public Answer(String answer, boolean isCorrect, Question question) {
		this.answer = answer;
		this.isCorrect = isCorrect;
		this.question = question;
	}
}
