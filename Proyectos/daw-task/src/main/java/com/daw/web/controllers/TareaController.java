package com.daw.web.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.persistence.entities.Tarea;
import com.daw.services.TareaService;


@RestController
@RequestMapping("/tarea")
public class TareaController {

	@Autowired
	private TareaService tareaService;
	
	@GetMapping
	public List<Tarea> list(){
		return this.tareaService.findAll();
	}
	
	@GetMapping("/("idTarea)")
	public Tarea findById(@PathVariable int idTarea) {
		return this.tareaService.findById(idTarea);
	}
	
}
