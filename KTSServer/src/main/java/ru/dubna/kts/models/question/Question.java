package ru.dubna.kts.models.question;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dubna.kts.config.db.BaseEntity;
import ru.dubna.kts.models.answer.Answer;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
public class Question extends BaseEntity {
	@Column(name = "question")
	private String question;

	@OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
	private List<Answer> answers;

	public Question(UUID id) {
		this.id = id;
	}

	public Question(String question) {
		this.question = question;
	}
}
