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
}
