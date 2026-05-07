package com.daw.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.Estado;
import com.daw.persistence.entities.Tarea;
import com.daw.persistence.entities.Usuario;
import com.daw.persistence.repositories.TareaRepository;
import com.daw.persistence.repositories.UsuarioRepository;
import com.daw.services.exceptions.TareaException;
import com.daw.services.exceptions.TareaNotFoundException;
import com.daw.services.exceptions.TareaSecurityException;

@Service
public class TareaService {

	@Autowired
	private TareaRepository tareaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<Tarea> findAll() {
		return this.tareaRepository.findAll();
	}

	public Tarea findById(int idTarea) {
		if (!this.tareaRepository.existsById(idTarea)) {
			throw new TareaNotFoundException("La tarea con id " + idTarea + " no existe. ");
		}

		return this.tareaRepository.findById(idTarea).get();
	}

	public Tarea create(Tarea tarea) {
        if (tarea.getFechaVencimiento().isBefore(LocalDate.now())) {
            throw new TareaException("La fecha de vencimiento debe ser posterior.");
        }
        
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new TareaSecurityException("Usuario no identificado"));
     
        tarea.setId(0);
        tarea.setEstado(Estado.PENDIENTE);
        tarea.setFechaCreacion(LocalDate.now());
        tarea.setIdUsuario(usuario.getId());

        return this.tareaRepository.save(tarea);
    }

	public Tarea update(Tarea tarea, int idTarea) {
	    if (tarea.getId() != idTarea) {
	        throw new TareaException(
	                String.format("El id del body (%d) y el id del path (%d) no coinciden", tarea.getId(), idTarea));
	    }

	    Tarea tareaBD = this.findById(idTarea); 

	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    boolean isAdmin = auth.getAuthorities().stream()
	            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
	    boolean esDueno = tareaBD.getUsuario().getUsername().equals(auth.getName());

	    if (!isAdmin && !esDueno) {
	        throw new TareaSecurityException("No tienes permiso para editar esta tarea.");
	    }

	    if (tarea.getEstado() != null) {
	        throw new TareaException("No se puede modificar el estado desde aquí.");
	    }
	    if (tarea.getFechaCreacion() != null) {
	        throw new TareaException("No se puede modificar la fecha de creación.");
	    }

	    tareaBD.setDescripcion(tarea.getDescripcion());
	    tareaBD.setTitulo(tarea.getTitulo());
	    tareaBD.setFechaVencimiento(tarea.getFechaVencimiento());

	    return this.tareaRepository.save(tareaBD);
	}

	public void delete(int idTarea) {
	    Tarea tarea = this.findById(idTarea); 

	    boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
	            .stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
	    boolean esDueno = tarea.getUsuario().getUsername()
	            .equals(SecurityContextHolder.getContext().getAuthentication().getName());

	    if (!isAdmin && !esDueno) {
	        throw new TareaSecurityException("No tienes permiso para borrar esta tarea.");
	    }

	    this.tareaRepository.deleteById(idTarea);
	}

	public Tarea marcarEnProgreso(int idTarea) {
        Tarea tarea = this.findByIdAndUser(idTarea);

        if (!tarea.getEstado().equals(Estado.PENDIENTE)) {
            throw new TareaException("La tarea ya está completada o ya está en progreso.");
        }

        tarea.setEstado(Estado.EN_PROGRESO);
        return this.tareaRepository.save(tarea);
    }
	

	public List<Tarea> pendientes() {
		return this.tareaRepository.findByEstado(Estado.PENDIENTE);
	}
	
	public List<Tarea> enProgreso() {
		return this.tareaRepository.findByEstado(Estado.EN_PROGRESO);
	}

	public List<Tarea> completadas() {
		return this.tareaRepository.findByEstado(Estado.COMPLETADA);
	}

	
	public List<Tarea> findByUser() {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username = auth.getName();

	    boolean isAdmin = auth.getAuthorities().stream()
	            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

	    if (isAdmin) {
	        return this.tareaRepository.findAll();
	    } else {
	        return this.tareaRepository.findByUsuarioUsername(username);
	    }
	}
	
	public Tarea findByIdAndUser(int idTarea) {
	    Tarea tarea = this.findById(idTarea); 

	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    boolean isAdmin = auth.getAuthorities().stream()
	            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
	    
	    boolean esDueno = tarea.getUsuario().getUsername().equals(auth.getName());

	    if (!isAdmin && !esDueno) {
	        throw new TareaSecurityException("No tienes permiso para ver esta tarea.");
	    }

	    return tarea;
	}
	
	
}
