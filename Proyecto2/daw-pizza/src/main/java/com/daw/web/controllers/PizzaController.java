package com.daw.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.daw.services.PizzaService;
import com.daw.services.exceptions.PizzaNotFoundException;

@Controller
@RequestMapping("/pizza")
public class PizzaController {

	@Autowired
	PizzaService pizzaService;
	
	//Obtener todas las pizzas
	@GetMapping
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(this.pizzaService.findAll());
	}
	
	//Obtener una pizza mediante su ID
	@GetMapping("/{idPizza}")
	public ResponseEntity<?> findById(@PathVariable int idPizza){
		try {
			return ResponseEntity.ok(this.pizzaService.findById(idPizza));
		}catch(PizzaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
	
	
}
