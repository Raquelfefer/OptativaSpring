package com.daw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.PizzaPedido;
import com.daw.persistence.repositories.PizzaPedidoRepository;

@Service
public class PizzaPedidoService {

	@Autowired
	private PizzaPedidoRepository pizzapedidorepository;
	
	//Obtener todos los clientes
	public List<PizzaPedido> findAll(){
		return this.pizzapedidorepository.findAll();
	}
}
