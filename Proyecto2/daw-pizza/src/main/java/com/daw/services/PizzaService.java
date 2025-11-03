package com.daw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.Pizza;
import com.daw.persistence.repositories.PizzaRepository;
import com.daw.services.exceptions.PizzaNotFoundException;

@Service
public class PizzaService {

    private final PizzaRepository pizzaRepository;

	@Autowired
	PizzaService pizzaService;

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
	
}
