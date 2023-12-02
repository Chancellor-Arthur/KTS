package ru.dubna.kts.models.question;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {
	@Query("select questions from Question questions order by random() limit 20")
	List<Question> find20RandomQuestions();
}
