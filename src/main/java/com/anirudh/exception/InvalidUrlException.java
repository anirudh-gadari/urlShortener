package com.anirudh.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Input Url not found")
public class InvalidUrlException extends RuntimeException {

	private static final long serialVersionUID = 4856028723156335042L;

	private String message;

	public InvalidUrlException(String message) {
		this.message = message;
	}

}
