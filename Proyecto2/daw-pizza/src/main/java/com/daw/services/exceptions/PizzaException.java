package com.daw.services.exceptions;

public class PizzaException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public PizzaException(String message) {
		super(message);
	}
}
