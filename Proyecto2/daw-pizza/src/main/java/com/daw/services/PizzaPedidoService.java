package com.daw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.Pedido;
import com.daw.persistence.entities.PizzaPedido;
import com.daw.persistence.repositories.PizzaPedidoRepository;
import com.daw.services.exceptions.ClienteNotFoundException;
import com.daw.services.exceptions.PedidoNotFoundException;
import com.daw.services.exceptions.PizzaPedidoNotFoundException;

@Service
public class PizzaPedidoService {

	@Autowired
	private PizzaPedidoRepository pizzapedidorepository;
	
	//Obtener todos los clientes
	public List<PizzaPedido> findAll(){
		return this.pizzapedidorepository.findAll();
	}
	
	//Obtener una pizza mediante su ID
	public PizzaPedido findById(int idPizzaPedido){
		if(!this.pizzapedidorepository.existsById(idPizzaPedido)) {
			throw new PizzaPedidoNotFoundException("Ese Id no existe.");
		}
		return this.pizzapedidorepository.findById(idPizzaPedido).get();
	}
	
	public PizzaPedido create(PizzaPedido pizzaPedido) {
		pizzaPedido.setId(0);
		return this.pizzapedidorepository.save(pizzaPedido);
	}
	
	//Modificar un cliente
	public PizzaPedido update(int idPizzaPedido, PizzaPedido pizzaPedido) {
		if(idPizzaPedido != pizzaPedido.getId()) {
			throw new PedidoNotFoundException("El ID del path y del body no coinciden. ");
		}
		
		PizzaPedido pizzaPedidoBD = this.findById(idPizzaPedido);
		pizzaPedidoBD.setIdPedido(pizzaPedido.getIdPedido());
		pizzaPedidoBD.setIdPizza(pizzaPedido.getIdPizza());
		pizzaPedidoBD.setPrecio(pizzaPedido.getPrecio());
		pizzaPedidoBD.setCantidad(pizzaPedido.getCantidad());
		
		
		return this.pizzapedidorepository.save(pizzaPedidoBD);
	}
		
	//Borrar un cliente
	public void delete(int idPizzaPedido) {
		if(!this.pizzapedidorepository.existsById(idPizzaPedido)) {
			throw new ClienteNotFoundException("Ese id de cliente no existe.");
		}
		this.pizzapedidorepository.deleteById(idPizzaPedido);
	}
}
