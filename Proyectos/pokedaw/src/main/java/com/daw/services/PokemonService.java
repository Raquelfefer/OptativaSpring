package com.daw.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.daw.persistence.entities.Pokeball;
import com.daw.persistence.entities.Pokemon;
import com.daw.persistence.entities.Tipo;
import com.daw.persistence.repositories.PokemonRepository;
import com.daw.services.exceptions.PokemonException;
import com.daw.services.exceptions.PokemonNotFoundException;

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
	}
}
