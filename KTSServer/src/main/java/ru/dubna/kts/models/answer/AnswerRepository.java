package ru.dubna.kts.models.answer;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, UUID> {
	List<Answer> findAllByQuestionId(UUID questionId);
}
