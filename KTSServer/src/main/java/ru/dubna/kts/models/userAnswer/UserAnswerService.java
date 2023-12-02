package ru.dubna.kts.models.userAnswer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.dubna.kts.exceptions.specific.NotFoundException;
import ru.dubna.kts.models.answer.Answer;
import ru.dubna.kts.models.user.User;
import ru.dubna.kts.models.userAnswer.dtos.UserAnswerInputDto;

@Service
@Transactional
@RequiredArgsConstructor
public class UserAnswerService {
	private final UserAnswerRepository userAnswerRepository;

	public List<UserAnswer> bulkCreate(UserAnswerInputDto userAnswerInputDto, UUID userId) {
		List<UserAnswer> userAnswers = userAnswerInputDto.getAnswerIds().stream()
				.map(answerId -> new UserAnswer(new Answer(answerId), new User(userId))).toList();
		return userAnswerRepository.saveAll(userAnswers);
	}

	public Optional<UserAnswer> find(UUID id) {
		return userAnswerRepository.findById(id);
	}

	public List<UserAnswer> getAll(UUID userId) {
		return userAnswerRepository.findAllByUserId(userId);
	}

	public UserAnswer get(UUID id) {
		return find(id)
				.orElseThrow(() -> new NotFoundException(String.format("Вопрос: '%s' не найден", id.toString())));
	}
}
