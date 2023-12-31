package ru.dubna.kts.exceptions.specific;

import org.springframework.http.HttpStatus;

import ru.dubna.kts.exceptions.ApplicationException;

public class NotFoundException extends ApplicationException {
	public NotFoundException(String message) {
		super(HttpStatus.NOT_FOUND, message);
	}
}
