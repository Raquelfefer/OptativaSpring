package com.daw.services.exceptions;

public class PizzaPedidoNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public PizzaPedidoNotFoundException(String message) {
		super(message);
	}

}
