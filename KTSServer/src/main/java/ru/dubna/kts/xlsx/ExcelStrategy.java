package ru.dubna.kts.xlsx;

import java.io.IOException;
import java.util.List;

import ru.dubna.kts.models.question.Question;

public interface ExcelStrategy {
	List<Question> readAndSave() throws IOException;
}
