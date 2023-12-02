package ru.dubna.kts.models.answer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.dubna.kts.exceptions.specific.NotFoundException;
import ru.dubna.kts.models.answer.dtos.AnswerInputDto;
import ru.dubna.kts.models.question.Question;

@Service
@Transactional
@RequiredArgsConstructor
public class AnswerService {
	private final AnswerRepository answerRepository;

	public List<Answer> bulkCreate(List<AnswerInputDto> answerInputDtos) {
		List<Answer> answers = answerInputDtos.stream().map(answerInputDto -> new Answer(answerInputDto.getAnswer(),
				answerInputDto.isCorrect(), new Question(answerInputDto.getQuestionId()))).toList();
		return answerRepository.saveAll(answers);
	}

	public void deleteAll() {
		answerRepository.deleteAll();
	}

	public Optional<Answer> find(UUID id) {
		return answerRepository.findById(id);
	}

	public List<Answer> getAll(UUID questionId) {
		return answerRepository.findAllByQuestionId(questionId);
	}

	public Answer get(UUID id) {
		return find(id)
				.orElseThrow(() -> new NotFoundException(String.format("Вопрос: '%s' не найден", id.toString())));
	}
}
