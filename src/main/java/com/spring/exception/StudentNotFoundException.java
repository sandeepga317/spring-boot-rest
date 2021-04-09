package com.spring.exception;

public class StudentNotFoundException extends RuntimeException {
	/**
	 * Default exception message
	 */
	public static final String DEFAULT_MESSAGE = "account is disabled";

	public StudentNotFoundException() {
		super(DEFAULT_MESSAGE);
	}

	public StudentNotFoundException(String message) {
		super(message);
	}

}
