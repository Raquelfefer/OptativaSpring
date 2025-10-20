package com.daw.persistence.repositories;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Pokemon;

public interface PokemonRepository extends ListCrudRepository<Pokemon, Integer>{

}
