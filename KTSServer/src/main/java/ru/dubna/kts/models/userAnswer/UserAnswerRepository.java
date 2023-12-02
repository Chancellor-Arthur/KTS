package ru.dubna.kts.models.userAnswer;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, UUID> {
	List<UserAnswer> findAllByUserId(UUID userId);
}
