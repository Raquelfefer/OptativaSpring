package com.daw.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.Estado;
import com.daw.persistence.entities.Tarea;
import com.daw.persistence.repositories.TareaRepository;
import com.daw.services.exceptions.TareaException;
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
			throw new TareaException("La fecha de vencimiento debe ser posterior. ");
		}
		
		tarea.setId(0);
		tarea.setEstado(Estado.PENDIENTE);
		tarea.setFechaCreacion(LocalDate.now());
		
		return this.tareaRepository.save(tarea);
	}
	
	//update
	public Tarea update(Tarea tarea, int idTarea) {
		if(tarea.getId() != idTarea) {
			throw new TareaException(String.format("El id del body (%d) y el id del path(%d) no coinciden", tarea.getId(), idTarea));
		}
		if(!this.tareaRepository.existsById(idTarea)) {
			throw new TareaNotFoundException("No existe la tarea con ID: " + idTarea);
		}
		if(tarea.getEstado() != null) {
			throw new TareaException("No se puede modificar el estado.");
		}
		if(tarea.getFechaCreacion() != null) {
			throw new TareaException("No se puede modificar la fecha de creación.");
		}
		
		Tarea tareaBD = this.findById(idTarea);
		tareaBD.setDescripcion(tarea.getDescripcion());
		tareaBD.setTitulo(tarea.getTitulo());
		
		return this.tareaRepository.save(tarea);
	}
	
	
	
	//delete
	
}
