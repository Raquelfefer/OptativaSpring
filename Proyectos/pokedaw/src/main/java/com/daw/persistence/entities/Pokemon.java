package com.daw.persistence.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
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
@Table
@Getter
@Setter
@NoArgsConstructor
public class Pokemon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "numero_pokedex")
	private int numeroPokedex;
	
	@Column(name = "nombre")
	private String titulo;
	
	@Enumerated(value = EnumType.STRING)
	private Tipo tipo1;
	
	@Enumerated(value = EnumType.STRING)
	private Tipo tipo2;
	
	@Column(name = "fecha_captura")
	private LocalDate fechaCaptura;
	
	@Enumerated(value = EnumType.STRING)
	private Capturado capturado;
	
}
