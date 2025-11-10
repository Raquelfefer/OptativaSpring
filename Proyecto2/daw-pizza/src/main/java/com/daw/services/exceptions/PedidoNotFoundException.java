package com.daw.services.exceptions;

public class PedidoNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public PedidoNotFoundException(String message) {
		super(message);
	}
}
