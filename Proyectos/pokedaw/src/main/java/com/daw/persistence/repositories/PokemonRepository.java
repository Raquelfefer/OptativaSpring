package com.daw.persistence.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Pokemon;

public interface PokemonRepository extends ListCrudRepository<Pokemon, Integer>{

	boolean existsByNumeroPokedex(int numPokedex);
	Pokemon findByNumeroPokedex(int numPokedex);
	List<Pokemon> findByFechaCapturaBetween(LocalDate inicio,LocalDate fin);
}
