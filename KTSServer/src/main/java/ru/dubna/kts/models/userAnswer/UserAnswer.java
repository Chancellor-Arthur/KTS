package ru.dubna.kts.models.userAnswer;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dubna.kts.config.db.BaseEntity;
import ru.dubna.kts.models.answer.Answer;
import ru.dubna.kts.models.user.User;

@Entity
@Table(name = "user_answers")
@Getter
@Setter
@NoArgsConstructor
public class UserAnswer extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "answer_id")
	private Answer answer;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public UserAnswer(Answer answer, User user) {
		this.answer = answer;
		this.user = user;
	}
}
