package com.daw.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.daw.persistence.entities.Pizza;
import com.daw.services.PizzaService;
import com.daw.services.exceptions.PizzaException;
import com.daw.services.exceptions.PizzaNotFoundException;

@Controller
@RequestMapping("/pizza")
public class PizzaController {

	@Autowired
	private PizzaService pizzaService;
	
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
	
	
	//Crear una pizza
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Pizza pizza){
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(this.pizzaService.create(pizza));
		}catch(PizzaException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	//Modificar una pizza
	@PutMapping("/{idPizza}")
	public ResponseEntity<?> update(@PathVariable int idPizza, @RequestBody Pizza pizza){
		try {
			return ResponseEntity.ok(this.pizzaService.update(idPizza, pizza));
		}
		catch(PizzaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
		catch(PizzaException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	
	//Borrar una pizza
	@DeleteMapping("/{idPizza}")
	public ResponseEntity<?> delete(@PathVariable int idPizza){
		try {
			this.pizzaService.delete(idPizza);
			return ResponseEntity.ok().build();
		}catch(PizzaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
	
	//Pizzas disponibles ordenadas por precio
	@GetMapping("/pizzasDisponibles")
	public ResponseEntity<?> pizzaDisponiblePrecioAsc(){
		return ResponseEntity.ok(this.pizzaDisponiblePrecioAsc());
	}
	
	//Buscar pizzas por su nombre(solo las que estén disponibles)
	@GetMapping("/pizzasDisponibles/buscar")
	public ResponseEntity<?> findByNamePizza(@RequestParam String nombrePizza){
		try {
			return ResponseEntity.ok(this.pizzaService.findByNamePizza(nombrePizza));
		}catch(PizzaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
	
	//Buscar pizzas que lleven un determinado ingrediente
	@GetMapping("/pizzaIngrediente")
	public ResponseEntity<?> findByIngrediente(@RequestParam String ingrediente){
		try {
			return ResponseEntity.ok(this.pizzaService.findByIngrediente(ingrediente));
		}catch(PizzaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
	
	//Buscar pizzas que no lleven un determinado ingrediente
	@GetMapping("/pizzaSinIngrediente")
	public ResponseEntity<?> findByNotIngrediente(@RequestParam String ingrediente){
		try {
			return ResponseEntity.ok(this.pizzaService.findByNotIngrediente(ingrediente));
		}catch(PizzaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
	
	//Actualizar precio de una pizza
	@PutMapping("/cambiarPrecio")
	public ResponseEntity<?> updatePrecio(@RequestParam int idPizza, @RequestParam double precio){
		try {
			return ResponseEntity.ok(this.pizzaService.updatePrecio(idPizza, precio));
		}catch(PizzaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
	
	//Marcar una pizza como disponible/no disponible
	@PutMapping("/cambiarDisponibilidad")
	public ResponseEntity<?> updateDisponible(@RequestParam int idPizza){
		try {
			return ResponseEntity.ok(this.pizzaService.updateDisponible(idPizza));
		}catch(PizzaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
	
	
	
	
	
	
}
