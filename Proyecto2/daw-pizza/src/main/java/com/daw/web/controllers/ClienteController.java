package com.daw.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.persistence.entities.Cliente;
import com.daw.services.ClienteService;
import com.daw.services.exceptions.ClienteException;
import com.daw.services.exceptions.ClienteNotFoundException;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	//Obtener todos los clientes
	@GetMapping
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(this.clienteService.findAll());
	}
	
	//Obtener un cliente mediante su ID
	@GetMapping("/{idCliente}")
	public ResponseEntity<?> findById(@PathVariable int idCliente){
		try {
			return ResponseEntity.ok(this.clienteService.findById(idCliente));
		}catch(ClienteNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
			
			
	//Crear un cliente
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Cliente cliente){
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(this.clienteService.create(cliente));
		}catch(ClienteException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
		
	//Modificar un cliente
	@PutMapping("/{idCliente}")
	public ResponseEntity<?> update(@PathVariable int idCliente, @RequestBody Cliente cliente){
		try {
			return ResponseEntity.ok(this.clienteService.update(idCliente, cliente));
		}
		catch(ClienteNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
		catch(ClienteException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	
	//Borrar un cliente
	@DeleteMapping("/{idCliente}")
	public ResponseEntity<?> delete(@PathVariable int idCliente){
		try {
			this.clienteService.delete(idCliente);
			return ResponseEntity.ok().build();
		}catch(ClienteNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

}
