package com.daw_task.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw_task.persistence.entities.Estado;
import com.daw_task.persistence.entities.Tarea;
import com.daw_task.persistence.repositories.TareaRepository;
import com.daw_task.services.exceptions.TareaException;
import com.daw_task.services.exceptions.TareaNotFoundException;

@Service
public class TareaService {
	
	@Autowired
	private TareaRepository tareaRepository;
	
	//Recuperar todas las tareas
	public List<Tarea> findAll(){
		return this.tareaRepository.findAll();
	}
	
	//Recuperar tarea por id
	public Tarea findById(int idTarea){
		if(!this.tareaRepository.existsById(idTarea)) {
			throw new TareaNotFoundException("No existe la tarea con el id: " + idTarea);
		}
		return this.tareaRepository.findById(idTarea).get();
	}
	
	//Crear una tarea
	public Tarea create(Tarea tarea) {
		if(tarea.getFechaVencimiento().isBefore(LocalDate.now())){
			throw new TareaException("La fecha de vencimiento no puede ser anterior a la fecha actual.");
		}
		
		tarea.setId(0);
		tarea.setFechaCreacion(LocalDate.now());
		tarea.setEstado(Estado.PENDIENTE);
		
		return this.tareaRepository.save(tarea);
	}
	
	//Modificar una tarea
	public Tarea update(Tarea tarea, int idTarea) {
		if(tarea.getId() != idTarea) {
			throw new TareaException("El id del body y el id del path no coinciden.");
		}
		if(!this.tareaRepository.existsById(idTarea)) {
			throw new TareaNotFoundException("No existe la tarea con el id: " + idTarea);
		}
		if(tarea.getEstado() != null) {
			throw new TareaException("No se puede modificar el estado de la tarea.");
		}
		if(tarea.getFechaCreacion() != null) {
			throw new TareaException("No se puede modificar la fecha de creación de la tarea.");
		}
		
		Tarea tareaBD = this.findById(idTarea);
		tareaBD.setDescripcion(tarea.getDescripcion());
		tareaBD.setTitulo(tarea.getTitulo());
		tareaBD.setFechaVencimiento(tarea.getFechaVencimiento());
		
		return this.tareaRepository.save(tarea);
		
	}
	
	//Borrar una tarea
	public void delete(int idTarea) {
		if(!this.tareaRepository.existsById(idTarea)) {
			throw new TareaNotFoundException("La tarea no existe.");
		}
		this.tareaRepository.deleteById(idTarea);
	}
	
	//Marcar una tarea en progreso
	public Tarea marcarEnProgreso(int idTarea) {
		Tarea tarea = this.findById(idTarea);
		
		if(!tarea.getEstado().equals(Estado.PENDIENTE)) {
			throw new TareaException("El estado de la tarea debe ser pendiente para pasarla a en progreso.");
		}
		
		tarea.setEstado(Estado.EN_PROGRESO);
		return this.tareaRepository.save(tarea);
	}
	
	//Marcar una tarea en completada
	public Tarea marcarCompletada(int idTarea) {
		Tarea tarea = this.findById(idTarea);
		
		if(!tarea.getEstado().equals(Estado.EN_PROGRESO)) {
			throw new TareaException("El estado de la tarea debe ser en progreso para pasarla a completada.");
		}
		
		tarea.setEstado(Estado.COMPLETADA);
		return this.tareaRepository.save(tarea);
	}
	
	//Obtener las tareas pendientes
	public List<Tarea> pendientes(){
		return this.tareaRepository.findByEstado(Estado.PENDIENTE);
	}
	
	//Obtener las tareas en progreso
	public List<Tarea> enProgreso(){
		return this.tareaRepository.findByEstado(Estado.EN_PROGRESO);
	}
	
	//Obtener las tareas completadas
	public List<Tarea> completadas(){
		return this.tareaRepository.findByEstado(Estado.COMPLETADA);
	}
	
	//Obtener las tareas vencidas
	public List<Tarea> vencidas(){
		return this.tareaRepository.findByFechaVencimientoBefore(LocalDate.now());
	}
	
	//Obtener las tareas no vencidas
	public List<Tarea> noVencidas(){
		return this.tareaRepository.findByFechaVencimientoAfter(LocalDate.now());
	}
	
	//Obtener las tareas por su título
	public List<Tarea> titulo(String titulo){
		return this.tareaRepository.findByTituloContainingIgnoreCase(titulo);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
