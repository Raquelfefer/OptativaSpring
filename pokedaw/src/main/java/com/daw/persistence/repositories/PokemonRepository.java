package com.daw.persistence.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import com.daw.persistence.entities.Pokemon;
import com.daw.persistence.entities.Tipo;

public interface PokemonRepository extends ListCrudRepository<Pokemon, Integer>{

	boolean existsByNumeroPokedex(int numPokedex);
	Pokemon findByNumeroPokedex(int numPokedex);
	
	List<Pokemon> findByFechaCapturaBetween(LocalDate inicio,LocalDate fin);
	
	@Query("SELECT p FROM Pokemon p WHERE p.tipo1 = :tipo OR p.tipo2 = :tipo")
	List<Pokemon> findByTipo(@Param("tipo") Tipo tipo);
	
	
}
