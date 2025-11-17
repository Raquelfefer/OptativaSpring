package com.daw.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daw.persistence.entities.Pizza;
import java.util.List;


public interface PizzaRepository extends JpaRepository<Pizza, Integer>{
	
	List<Pizza> findByDisponibleTrueOrderByPrecioAsc();
	List<Pizza> findByNombreContainingAndDisponibleTrue(String nombreParcial);
	List<Pizza> findByDescripcionContaining(String ingrediente);
	List<Pizza> findByDescripcionNotContaining(String ingrediente);
}
