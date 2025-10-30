package com.evento.services.exceptions;

public class EventoNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public EventoNotFoundException(String message) {
		super(message);
	}
}
