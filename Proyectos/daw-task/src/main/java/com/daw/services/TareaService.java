package com.daw.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.Estado;
import com.daw.persistence.entities.Tarea;
import com.daw.persistence.repositories.TareaRepository;
import com.daw.services.exceptions.TareaNotFoundException;

@Service
public class TareaService {

	@Autowired
	private TareaRepository tareaRepository;
	
	//findAll
	public List<Tarea> findAll(){
		return this.tareaRepository.findAll();
	}
	
	//findById
	public Tarea findById(int idTarea) {
		if(!this.tareaRepository.existsById(idTarea)) {
			throw new TareaNotFoundException("No existe la tarea con ID: " + idTarea);
		}
		
		return this.tareaRepository.findById(idTarea).get();
	}
	
	//create
	public Tarea create(Tarea tarea) {
		if(tarea.getFechaVencimiento().isBefore(LocalDate.now())) {
			throw new TareaException()
		}
		
		tarea.setId(0);
		tarea.setEstado(Estado.PENDIENTE);
		tarea.setFechaCreacion(LocalDate.now());
		
		return this.tareaRepository.save(tarea);
	}
	
	
	
	//update
}
