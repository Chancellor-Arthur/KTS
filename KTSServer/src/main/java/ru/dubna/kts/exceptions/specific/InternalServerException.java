package ru.dubna.kts.exceptions.specific;

import org.springframework.http.HttpStatus;

import ru.dubna.kts.exceptions.ApplicationException;

public class InternalServerException extends ApplicationException {
	public InternalServerException(String message) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, message);
	}
}
