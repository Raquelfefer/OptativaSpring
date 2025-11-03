package com.daw.services.exceptions;

public class PizzaNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public PizzaNotFoundException(String message) {
		super(message);
	}
}
