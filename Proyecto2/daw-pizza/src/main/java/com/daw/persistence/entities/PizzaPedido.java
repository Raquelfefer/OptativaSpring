package com.daw.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pizza_pedido")
@Getter
@Setter
@NoArgsConstructor
public class PizzaPedido {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name= "id_pedido")
	private int idPedido;
	
	@Column(name= "id_pizza")
	private int idPizza;
	
	@Column(columnDefinition = "DECIMAL(2,1)")
	private double cantidad;
	
	@Column(columnDefinition = "DECIMAL(5,2)")
	private double precio;
	
}
