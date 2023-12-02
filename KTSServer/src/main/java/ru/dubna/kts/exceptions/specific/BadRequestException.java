package ru.dubna.kts.exceptions.specific;

import org.springframework.http.HttpStatus;

import ru.dubna.kts.exceptions.ApplicationException;

public class BadRequestException extends ApplicationException {
	public BadRequestException(String reason) {
		super(HttpStatus.BAD_REQUEST, reason);
	}
}
