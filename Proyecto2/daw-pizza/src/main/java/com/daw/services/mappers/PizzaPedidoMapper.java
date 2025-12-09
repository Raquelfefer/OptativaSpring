package com.daw.services.mappers;

import java.util.List;

import com.daw.persistence.entities.PizzaPedido;
import com.daw.services.dto.PizzaPedidoOutputDTO;

public class PizzaPedidoMapper {

	public static PizzaPedidoOutputDTO toDTO(PizzaPedido pizzaPedido) {
		PizzaPedidoOutputDTO dto = new PizzaPedidoOutputDTO();
		
		dto.setId(pizzaPedido.getId());
		dto.setCantidad(pizzaPedido.getCantidad());
		dto.setPrecio(pizzaPedido.getPrecio());
		dto.setIdPizza(pizzaPedido.getIdPizza());
		dto.setPizza(pizzaPedido.getPizza().getNombre());
		
		
		
		
		
		
		return dto;
	}
	
	public static List<PizzaPedidoOutputDTO> toDtos(List<PizzaPedido> pizzaPedidos){
		return pizzaPedidos.stream().map(pp -> toDTO(pp)).toList();
	}
	
	
	
	
}
