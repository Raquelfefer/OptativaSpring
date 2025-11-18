package com.daw.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.services.PizzaPedidoService;
import com.daw.services.exceptions.PizzaPedidoNotFoundException;

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
	
	//Obtener una pizza mediante su ID
	@GetMapping("/{idPizzaPedido}")
	public ResponseEntity<?> findById(@PathVariable int idPizzaPedido){
		try {
			return ResponseEntity.ok(this.pizzapedidoservice.findById(idPizzaPedido));
		}catch(PizzaPedidoNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
}
