package com.daw.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.Pokeball;
import com.daw.persistence.entities.Pokemon;
import com.daw.persistence.entities.Tipo;
import com.daw.persistence.repositories.PokemonRepository;
import com.daw.services.exceptions.PokemonException;
import com.daw.services.exceptions.PokemonNotFoundException;

@Service
public class PokemonService {

	@Autowired
	private PokemonRepository pokemonrepository;
	
	//Obtener todos los pokemon
	public List<Pokemon> findAll(){
		return this.pokemonrepository.findAll();
	}
	
	//Obtener un pokemon mediante su ID
	public Pokemon findById(int idPokemon) {
		if(!this.pokemonrepository.existsById(idPokemon)){
			throw new PokemonNotFoundException("El pokemon no existe.");
		}
		return this.pokemonrepository.findById(idPokemon).get();
	}
	
	//Crear un pokemon
	public Pokemon create(Pokemon pokemon) {
		if(pokemon.getTipo1() != null && pokemon.getTipo1() == pokemon.getTipo2()) {
			throw new PokemonException("Los dos tipos no pueden ser iguales.");
		}
		if(pokemon.getTipo2() == null) {
			pokemon.setTipo2(Tipo.NINGUNO);
		}
		if(pokemon.getCapturado() == null) {
			pokemon.setCapturado(Pokeball.POKEBALL);
		}
		
		pokemon.setId(0);
		pokemon.setFechaCaptura(LocalDate.now());
		
		return this.pokemonrepository.save(pokemon);
	}
	
	//Modificar un pokemon
	public Pokemon update(Pokemon pokemon, int idPokemon) {
		if(pokemon.getId() != idPokemon) {
			throw new PokemonException("El id del body no corresponde con el id del path.");
		}
		if(!this.pokemonrepository.existsById(idPokemon)) {
			throw new PokemonNotFoundException("Este id de Pokemon no existe.");
		}
		if(pokemon.getFechaCaptura() != null) {
			throw new PokemonException("No se puede modificar la fecha de captura.");
		}
		if(pokemon.getCapturado() != null) {
			throw new PokemonException("No se puede modificar el tipo de captura.");
		}
		
		Pokemon pokemonBD = this.findById(idPokemon);
		pokemonBD.setTitulo(pokemon.getTitulo());
		pokemonBD.setNumeroPokedex(pokemon.getNumeroPokedex());
		return this.pokemonrepository.save(pokemonBD);
	}
	
	//Borrar un pokemon
	public void delete(int idPokemon) {
		if(!this.pokemonrepository.existsById(idPokemon)) {
			throw new PokemonNotFoundException("Este id de Pokemon no existe.");
		}
		this.pokemonrepository.deleteById(idPokemon);
	}
	
	//Obtener pokemon por su numero de pokedex
	public Pokemon findByNumPokedex(int numPokedex) {
		if(!this.pokemonrepository.existsByNumeroPokedex(numPokedex)) {
			throw new PokemonNotFoundException("Este número de Pokedex no está registrado.");
		}
		return this.pokemonrepository.findByNumeroPokedex(numPokedex);
	}
	
	//Obtener pokemons entre rango de fechas
	public List<Pokemon> findPokemonBetweenDates(LocalDate inicio, LocalDate fin){
		if(inicio == null || fin == null) {
			throw new PokemonException("La fecha no puede ser nula.");
		}
		if(inicio.isAfter(fin)) {
			throw new PokemonException("La fecha de inicio no puede ser posterior a la de fin.");
		}
		return this.pokemonrepository.findByFechaCapturaBetween(inicio, fin);
	}
	
	//Obtener pokemon por su tipo
	public List<Pokemon> findByTipo(String tipo){
		Tipo t;
		try {
			t = Tipo.valueOf(tipo.toUpperCase());
		}catch(IllegalArgumentException ex) {
			throw new PokemonException ("Ese tipo no existe.");
		}

		return this.pokemonrepository.findByTipo(t);
	}
	
	//Cambiar el tipo de un pokemon
	public Pokemon modificarTipo(Pokemon pokemon, int idPokemon, int posicion, String tipo) {
		if(pokemon.getId() != idPokemon) {
			throw new PokemonException ("El id del body no corresponde con el id del path.");
		}
		if(!this.pokemonrepository.existsById(idPokemon)) {
			throw new PokemonNotFoundException("Ese id no existe.");
		}
		if(posicion == 1 || posicion == 2) {
			throw new PokemonException("El tipo debe ser tipo1 o tipo2");
		}
		Tipo t;
		try {
			t = Tipo.valueOf(tipo.toUpperCase());
		}catch(IllegalArgumentException ex) {
			throw new PokemonException ("Ese tipo no existe.");
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
