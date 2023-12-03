package ru.dubna.kts.models.user;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dubna.kts.config.db.BaseEntity;
import ru.dubna.kts.models.userAnswer.UserAnswer;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {
	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<UserAnswer> answers;

	public User(UUID id) {
		this.id = id;
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
