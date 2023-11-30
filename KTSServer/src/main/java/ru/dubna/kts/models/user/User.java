package ru.dubna.kts.models.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dubna.todolist.config.db.BaseEntity;
import ru.dubna.todolist.models.tasks.Task;

import java.util.List;

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

	@OneToMany(mappedBy = "user")
	private List<Task> tasks;

	public User(int id) {
		this.id = id;
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
