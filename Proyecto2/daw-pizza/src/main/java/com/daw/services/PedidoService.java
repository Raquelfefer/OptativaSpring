package com.daw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.Cliente;
import com.daw.persistence.entities.Metodo;
import com.daw.persistence.entities.Pedido;
import com.daw.persistence.repositories.PedidoRepository;
import com.daw.services.dto.PedidoDTO;
import com.daw.services.exceptions.ClienteException;
import com.daw.services.exceptions.ClienteNotFoundException;
import com.daw.services.exceptions.PedidoNotFoundException;
import com.daw.services.mappers.PedidoMapper;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	//Obtener todos los pedidos
	public List<Pedido> findAll(){
		return this.pedidoRepository.findAll();
	}

	//Obtener un pedido mediante su ID
	public Pedido findById(int idPedido) {
		if(!this.pedidoRepository.existsById(idPedido)) {
			throw new ClienteNotFoundException("Este id no existe.");
		}
		return this.pedidoRepository.findById(idPedido).get();
	}
	
	public PedidoDTO findEntityById(int idPedido) {
		if(!this.pedidoRepository.existsById(idPedido)) {
			throw new ClienteNotFoundException("Este id no existe.");
		}
		return PedidoMapper.toDTO(this.pedidoRepository.findById(idPedido).get());
	}
		
	//Crear un pedido
	public PedidoDTO create(Pedido pedido) {
		pedido.setId(0);
		
		return PedidoMapper.toDTO(this.pedidoRepository.save(pedido));
	}
	
	//Modificar un cliente
	public Pedido update(int idPedido, Pedido pedido) {
		if(idPedido != pedido.getId()) {
			throw new PedidoNotFoundException("El ID del path y del body no coinciden. ");
		}
		
		Pedido pedidoBD = this.findById(idPedido);
		pedidoBD.setFecha(pedido.getFecha());
		pedidoBD.setTotal(pedido.getTotal());
		pedidoBD.setIdCliente(pedido.getIdCliente());
		pedidoBD.setNotas(pedido.getNotas());
		pedidoBD.setMetodo(pedido.getMetodo());
		
		return this.pedidoRepository.save(pedidoBD);
	}
	
	//Borrar un cliente
	public void delete(int idPedido) {
		if(!this.pedidoRepository.existsById(idPedido)) {
			throw new ClienteNotFoundException("Ese id de cliente no existe.");
		}
		this.pedidoRepository.deleteById(idPedido);
	}
	
	//Modificar notas
	public Pedido updateNotas(int idPedido, String nota) {
		if(!this.pedidoRepository.existsById(idPedido)) {
			throw new ClienteNotFoundException("Este id no existe.");
		}
		Pedido pedidoBD = this.findById(idPedido);
		pedidoBD.setNotas(nota);
		return this.pedidoRepository.save(pedidoBD);
	}
	
	
	
}
