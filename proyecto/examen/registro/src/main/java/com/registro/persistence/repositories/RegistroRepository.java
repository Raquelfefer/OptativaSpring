package com.registro.persistence.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.registro.persistence.entities.Registro;

public interface RegistroRepository extends ListCrudRepository<Registro, Integer>{

	
	List<Registro> findByUbicacionAndFechaLecturaBetween(String ubicacion, LocalDate fechaInicio, LocalDate fechaFin);
}


