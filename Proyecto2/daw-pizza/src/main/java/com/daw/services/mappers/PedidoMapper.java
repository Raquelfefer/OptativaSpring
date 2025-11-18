package com.daw.services.mappers;

import java.util.ArrayList;
import java.util.List;

import com.daw.persistence.entities.Pedido;
import com.daw.persistence.entities.PizzaPedido;
import com.daw.services.dto.PedidoDTO;
import com.daw.services.dto.PizzaPedidoOutputDTO;

public class PedidoMapper {

	public static PedidoDTO toDTO(Pedido pedido) {
		PedidoDTO dto = new PedidoDTO();
		
		//Transformamos la entidad en el DTO
		dto.setId(pedido.getId());
		dto.setFecha(pedido.getFecha());
		dto.setMetodo(pedido.getMetodo());
		
		dto.setCliente(pedido.getCliente().getNombre());
		dto.setCliente(pedido.getCliente().getTelefono());
		dto.setCliente(pedido.getCliente().getDireccion());
		
		dto.setNotas(pedido.getNotas());
		
		dto.setNumeroPizzas(pedido.getPizzaPedido().size());
		
		List<PizzaPedidoOutputDTO> pizzas = new ArrayList<PizzaPedidoOutputDTO>();
		for(PizzaPedido pp:pedido.getPizzaPedido()) {
			PizzaPedidoOutputDTO ppDTO = PizzaPedidoMapper.toDTO(pp);
			pizzas.add(ppDTO);
		}
		
		dto.setPizzas(pizzas);
		
		return dto;
	}
}
