package ru.dubna.kts.exceptions.specific;

import org.springframework.http.HttpStatus;

import ru.dubna.kts.exceptions.ApplicationException;

public class UnauthorizedException extends ApplicationException {
	public UnauthorizedException(String message) {
		super(HttpStatus.UNAUTHORIZED, message);
	}
}
