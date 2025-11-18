package com.daw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.PizzaPedido;
import com.daw.persistence.repositories.PizzaPedidoRepository;
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
}
