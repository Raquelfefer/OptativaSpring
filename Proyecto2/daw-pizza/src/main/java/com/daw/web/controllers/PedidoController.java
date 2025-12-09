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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daw.persistence.entities.Pedido;
import com.daw.persistence.repositories.PedidoRepository;
import com.daw.services.PedidoService;
import com.daw.services.exceptions.PedidoException;
import com.daw.services.exceptions.PedidoNotFoundException;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
	
	@Autowired
	private PedidoService pedidoService;


    PedidoController(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }
	

	//Obtener todos los pedidos
	@GetMapping
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(this.pedidoService.findAll());
	}
	
	//Obtener un pedido mediante su ID
	@GetMapping("/{idPedido}")
	public ResponseEntity<?> findById(@PathVariable int idPedido){
		try {
			return ResponseEntity.ok(this.pedidoService.findById(idPedido));
		}catch(PedidoNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
		
		
	//Crear un pedido
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Pedido pedido){
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(this.pedidoService.create(pedido));
		}catch(PedidoException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
		
	//Modificar un pedido
	@PutMapping("/{idPedido}")
	public ResponseEntity<?> update(@PathVariable int idPedido, @RequestBody Pedido pedido){
		try {
			return ResponseEntity.ok(this.pedidoService.update(idPedido, pedido));
		}
		catch(PedidoNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
		catch(PedidoException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	
	//Borrar un pedido
	@DeleteMapping("/{idPedido}")
	public ResponseEntity<?> delete(@PathVariable int idPedido){
		try {
			this.pedidoService.delete(idPedido);
			return ResponseEntity.ok().build();
		}catch(PedidoNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
	
	//Modificar notas
	@PutMapping("notas")
	public ResponseEntity<?> updateNotas(@RequestParam int idPedido, @RequestParam String nota){
		try {
			return ResponseEntity.ok(this.pedidoService.updateNotas(idPedido, nota));
		}catch(PedidoNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
	
	//
	
	
	
}
