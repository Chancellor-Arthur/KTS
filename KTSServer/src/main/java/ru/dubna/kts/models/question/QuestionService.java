package ru.dubna.kts.models.question;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.dubna.kts.exceptions.specific.NotFoundException;
import ru.dubna.kts.models.answer.AnswerService;
import ru.dubna.kts.models.question.dtos.QuestionInputDto;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {
	private final QuestionRepository questionRepository;
	private final AnswerService answerService;

	public Question create(QuestionInputDto questionInputDto) {
		Question question = new Question(questionInputDto.getQuestion());
		return questionRepository.save(question);
	}

	public List<Question> bulkCreate(List<QuestionInputDto> questionInputDtos) {
		List<Question> questions = questionInputDtos.stream()
				.map(questionInputDto -> new Question(questionInputDto.getQuestion())).toList();
		return questionRepository.saveAll(questions);
	}

	public void deleteAll() {
		answerService.deleteAll();
		questionRepository.deleteAll();
	}

	public Optional<Question> find(UUID id) {
		return questionRepository.findById(id);
	}

	public List<Question> get20RandomQuestions() {
		return questionRepository.find20RandomQuestions();
	}

	public List<Question> getAll() {
		return questionRepository.findAll();
	}

	public Question get(UUID id) {
		return find(id)
				.orElseThrow(() -> new NotFoundException(String.format("Вопрос: '%s' не найден", id.toString())));
	}
}
