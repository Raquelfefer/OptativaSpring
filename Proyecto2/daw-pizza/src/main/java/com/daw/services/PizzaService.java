package com.daw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.Pizza;
import com.daw.persistence.repositories.PizzaRepository;
import com.daw.services.exceptions.PizzaException;
import com.daw.services.exceptions.PizzaNotFoundException;

@Service
public class PizzaService {

	@Autowired
	private PizzaRepository pizzaRepository;

    PizzaService(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }
	
	//Obtener todas las pizzas
	public List<Pizza> findAll(){
		return this.pizzaRepository.findAll();
	}
	
	//Obtener una pizza mediante su ID
	public Pizza findById(int idPizza){
		if(!this.pizzaRepository.existsById(idPizza)) {
			throw new PizzaNotFoundException("Ese Id no existe.");
		}
		return this.pizzaRepository.findById(idPizza).get();
	}
	
	//Crear una pizza
	public Pizza create(Pizza pizza) {
		if(pizza.getDescripcion().isBlank() || pizza.getNombre().isBlank() || pizza.getPrecio() == 0) {
			throw new PizzaException("Los campos descripción, nombre o precio no pueden estar vacíos.");
		}
		return this.pizzaRepository.save(pizza);
	}
	
	//Modificar una pizza
	public Pizza update(int idPizza, Pizza pizza) {
		if(idPizza != pizza.getId()) {
			throw new PizzaNotFoundException("El ID del path y del body no coinciden. ");
		}
		
		Pizza pizzaBD = this.findById(idPizza);
		pizzaBD.setDescripcion(pizza.getDescripcion());
		pizzaBD.setDisponible(pizza.getDisponible());
		pizzaBD.setNombre(pizza.getNombre());
		pizzaBD.setPrecio(pizza.getPrecio());
		pizzaBD.setVegana(pizza.getVegana());
		pizzaBD.setVegetariana(pizza.getVegetariana());
		
		return this.pizzaRepository.save(pizzaBD);
	}
	
	//Borrar una pizza
	public void delete(int idPizza) {
		if(!this.pizzaRepository.existsById(idPizza)) {
			throw new PizzaNotFoundException("Ese id de pizza no existe.");
		}
		this.pizzaRepository.deleteById(idPizza);
	}
	
	//Pizzas disponibles ordenadas por precio
	public List<Pizza> pizzaDisponiblePrecioAsc(){
		return this.pizzaDisponiblePrecioAsc();
	}
	
	//Buscar pizzas por su nombre(solo las que estén disponibles)
	public List<Pizza> findByNamePizza(String nombrePizza){
		List<Pizza> pizzas = pizzaRepository.findByNombreContainingAndDisponibleTrue(nombrePizza);
		if(pizzas.isEmpty()) {
			throw new PizzaNotFoundException("No hay pizzas disponibles con ese nombre");
		}
		return pizzas;
	}
	
	//Buscar pizzas que lleven un determinado ingrediente
	public List<Pizza> findByIngrediente(String ingrediente){
		List<Pizza> pizzas = pizzaRepository.findByDescripcionContaining(ingrediente);
		if(pizzas.isEmpty()) {
			throw new PizzaNotFoundException("No hay pizzas con ese ingrediente");
		}
		return pizzas;
	}
	
	//Buscar pizzas que no lleven un determinado ingrediente
	public List<Pizza> findByNotIngrediente(String ingrediente){
		List<Pizza> pizzas = pizzaRepository.findByDescripcionNotContaining(ingrediente);
		if(pizzas.isEmpty()) {
			throw new PizzaNotFoundException("No hay pizzas sin ese ingrediente.");
		}
		return pizzas;
	}
	
	//Actualizar precio de una pizza
	public Pizza updatePrecio(int idPizza, double precio) {
		if(!this.pizzaRepository.existsById(idPizza)) {
			throw new PizzaNotFoundException("No existe una pizza con ese ID");
		}
		Pizza pizzaBD = this.findById(idPizza);
		pizzaBD.setPrecio(precio);
		return this.pizzaRepository.save(pizzaBD);
	}
	
	//Marcar una pizza como disponible/no disponible
	public Pizza updateDisponible(int idPizza) {
		if(!this.pizzaRepository.existsById(idPizza)) {
			throw new PizzaNotFoundException("No existe una pizza con ese ID");
		}
		Pizza pizzaBD = this.findById(idPizza);
		if(pizzaBD.getDisponible()) {
			pizzaBD.setDisponible(false);
		}else {
			pizzaBD.setDisponible(true);
		}
		return this.pizzaRepository.save(pizzaBD);
	}
	
	
	
	
	
	
	
	
	
}
