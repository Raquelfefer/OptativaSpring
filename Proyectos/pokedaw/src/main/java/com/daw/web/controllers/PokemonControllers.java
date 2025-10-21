package com.daw.web.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
	
	//Modificar pokemon
	@PutMapping
	public ResponseEntity<?> update(@RequestBody Pokemon pokemon, @PathVariable int idPokemon){
		try {
			return ResponseEntity.ok(this.pokemonService.update(pokemon, idPokemon));
		}catch(PokemonNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}catch(PokemonException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	//Borrar un pokemon
	@DeleteMapping("/{idPokemon}")
	public ResponseEntity<?> delete(@PathVariable int idPokemon){
		try {
			this.pokemonService.delete(idPokemon);
			return ResponseEntity.ok().build();
		}catch(PokemonNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
	
	//Obtener pokemon por su numero de pokedex
	@GetMapping("/pokedex/{numPokedex}")
	public ResponseEntity<?> findByNumPokedex(@PathVariable int idPokedex){
		try {
			return ResponseEntity.ok(this.pokemonService.findByNumPokedex(idPokedex));
		}catch(PokemonNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
	
	//Obtener pokemons entre rango de fechas
	@GetMapping("/fechaCapturados")
	public ResponseEntity<?> findPokemonBetweenDates(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate inicio, 
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate fin){
		try {
			return ResponseEntity.ok(this.pokemonService.findPokemonBetweenDates(inicio, fin));
		}catch(PokemonException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
