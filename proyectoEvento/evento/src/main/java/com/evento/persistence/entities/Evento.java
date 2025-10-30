package com.evento.persistence.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = "evento")
@Getter
@Setter
@NoArgsConstructor
public class Evento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String nombre;
	
	private LocalDate fecha;
	
	@Enumerated(value = EnumType.STRING)
	private Tipo tipo;
	
	@Enumerated(value = EnumType.STRING)
	private Estado estado;
	

}
