package com.daw_task.persistence.repositories;

import java.time.LocalDate;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.daw_task.persistence.entities.Estado;
import com.daw_task.persistence.entities.Tarea;

@Repository
public interface TareaRepository extends ListCrudRepository<Tarea, Integer>{

	List<Tarea> findByEstado(Estado estado);
	List<Tarea> findByFechaVencimientoBefore(LocalDate fecha);
	List<Tarea> findByFechaVencimientoAfter(LocalDate fecha);
	List<Tarea> findByTituloContainingIgnoreCase(String titulo);
}
