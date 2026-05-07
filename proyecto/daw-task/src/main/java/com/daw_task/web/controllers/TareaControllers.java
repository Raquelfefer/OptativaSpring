package com.daw_task.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daw_task.persistence.entities.Tarea;
import com.daw_task.services.TareaService;
import com.daw_task.services.exceptions.TareaException;
import com.daw_task.services.exceptions.TareaNotFoundException;

@RestController
@RequestMapping("/tarea")
public class TareaControllers {

	@Autowired
	private TareaService tareaService;
	
	//Recuperar todas las tareas
	@GetMapping
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(this.tareaService.findAll());
	}
	
	//Recuperar tarea por ID
	@GetMapping("/{idTarea}")
	public ResponseEntity<?> findById(@PathVariable int idTarea){
		try {
			return ResponseEntity.ok(this.tareaService.findById(idTarea));
		}catch(TareaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
	
	//Crear una tarea
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Tarea tarea){
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(this.tareaService.create(tarea));
		}catch(TareaException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	//Modificar tarea
	@PutMapping("/{idTarea}")
	public ResponseEntity<?> update(@PathVariable int idTarea, @RequestBody Tarea tarea){
		try {
			return ResponseEntity.ok(this.tareaService.update(tarea, idTarea));
		}catch(TareaException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}catch(TareaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	//Borrar tarea
	@DeleteMapping("/{idTarea}")
	public ResponseEntity<?> delete(@PathVariable int idTarea){
		try {
			this.tareaService.delete(idTarea);
			return ResponseEntity.ok().build();
		}catch(TareaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
		
	}
	
	//Marcar una tarea en progreso
	@PutMapping("/idTarea/iniciar")
	public ResponseEntity<?> marcarEnProgreso(@PathVariable int idTarea){
		try {
			return ResponseEntity.ok(this.tareaService.marcarEnProgreso(idTarea));
		}catch(TareaException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}catch(TareaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
	
	//Marcar una tarae en completada
	@PutMapping("/idTarea/completar")
	public ResponseEntity<?> marcarCompletada(@PathVariable int idTarea){
		try {
			return ResponseEntity.ok(this.tareaService.marcarCompletada(idTarea));
		}catch(TareaException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}catch(TareaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
	
	//Obtener las tareas pendientes
	@GetMapping("/pendientes")
	public ResponseEntity<?> pendientes(){
		return ResponseEntity.ok(this.tareaService.pendientes());
	}
	
	//Obtener las tareas en progreso
	@GetMapping("/en-progreso")
	public ResponseEntity<?> enProgreso(){
		return ResponseEntity.ok(this.tareaService.enProgreso());
	}
	
	//Obtener las tareas completadas
	@GetMapping("/completadas")
	public ResponseEntity<?> completadas(){
		return ResponseEntity.ok(this.tareaService.completadas());
	}
	
	//Obtener las tareas vencidas
	@GetMapping("/vencidas")
	public ResponseEntity<?> vencidas(){
		return ResponseEntity.ok(this.tareaService.vencidas());
	}
	
	//Obtener las tareas no vencidas
	@GetMapping("/no-vencidas")
	public ResponseEntity<?> noVencidas(){
		return ResponseEntity.ok(this.tareaService.noVencidas());
	}
	
	//Obtener las tareas por su título
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<?> titulo(@PathVariable String titulo){
		return ResponseEntity.ok(this.tareaService.titulo(titulo));
	}
	
	
	
}
