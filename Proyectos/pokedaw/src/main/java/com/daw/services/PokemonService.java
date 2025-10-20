package com.daw.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.daw.persistence.repositories.PokemonRepository;

public class PokemonService {

	@Autowired
	private PokemonRepository pokemonrepository;
}
