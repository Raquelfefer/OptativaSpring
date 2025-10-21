package com.daw.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.persistence.entities.Pokemon;
import com.daw.services.PokemonService;
import com.daw.services.exceptions.PokemonException;
import com.daw.services.exceptions.PokemonNotFoundException;

@RestController
@RequestMapping("/pokemon")
public class PokemonControllers {

	@Autowired
	private PokemonService pokemonService;
	
	//Obtener todos los pokemon
	@GetMapping
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(this.pokemonService.findAll());
	}
	
	//Obtener un pokemon mediante su ID
	@GetMapping("/{idPokemon}")
	public ResponseEntity<?> findById(@PathVariable int idPokemon){
		try {
			return ResponseEntity.ok(this.pokemonService.findById(idPokemon));
		}catch(PokemonNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
	
	//Crear un pokemon
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Pokemon pokemon){
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(this.pokemonService.create(pokemon));
		}catch(PokemonException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
