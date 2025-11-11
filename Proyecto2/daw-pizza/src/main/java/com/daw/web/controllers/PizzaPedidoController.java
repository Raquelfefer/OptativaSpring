package com.daw.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.services.PizzaPedidoService;

@RestController
@RequestMapping("/pizzapedidos")
public class PizzaPedidoController {

	@Autowired
	private PizzaPedidoService pizzapedidoservice;
	
	//Obtener todos los clientes
	@GetMapping
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(this.pizzapedidoservice.findAll());
	}
}
