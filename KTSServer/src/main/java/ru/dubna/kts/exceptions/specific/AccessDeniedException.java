package ru.dubna.kts.exceptions.specific;

import org.springframework.http.HttpStatus;

import ru.dubna.kts.exceptions.ApplicationException;

public class AccessDeniedException extends ApplicationException {
	public AccessDeniedException(String reason) {
		super(HttpStatus.FORBIDDEN, reason);
	}
}
