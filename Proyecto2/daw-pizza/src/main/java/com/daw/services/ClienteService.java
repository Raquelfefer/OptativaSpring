package com.daw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.Cliente;
import com.daw.persistence.entities.Pizza;
import com.daw.persistence.repositories.ClienteRepository;
import com.daw.services.exceptions.ClienteException;
import com.daw.services.exceptions.ClienteNotFoundException;
import com.daw.services.exceptions.PizzaException;
import com.daw.services.exceptions.PizzaNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	//Obtener todos los clientes
	public List<Cliente> findAll(){
		return this.clienteRepository.findAll();
	}
	
	//Obtener una cliente mediante su ID
	public Cliente findById(int idCliente) {
		if(!this.clienteRepository.existsById(idCliente)) {
			throw new ClienteNotFoundException("Este id no existe.");
		}
		return this.clienteRepository.findById(idCliente).get();
	}
	
	//Crear un cliente
	public Cliente create(Cliente cliente) {
		if(cliente.getNombre().isBlank() || cliente.getDireccion().isBlank() || cliente.getEmail().isBlank() || cliente.getTelefono().isBlank()) {
			throw new ClienteException("Los campos nombre, direccion, email y teléfono no pueden estar vacíos.");
		}
		return this.clienteRepository.save(cliente);
	}
	
	//Modificar un cliente
	public Cliente update(int idCliente, Cliente cliente) {
		if(idCliente != cliente.getId()) {
			throw new ClienteNotFoundException("El ID del path y del body no coinciden. ");
		}
		
		Cliente clienteBD = this.findById(idCliente);
		clienteBD.setNombre(cliente.getNombre());
		clienteBD.setDireccion(cliente.getDireccion());
		clienteBD.setEmail(cliente.getEmail());
		clienteBD.setTelefono(cliente.getTelefono());
		
		return this.clienteRepository.save(clienteBD);
	}
	
	//Borrar un cliente
	public void delete(int idCliente) {
		if(!this.clienteRepository.existsById(idCliente)) {
			throw new ClienteNotFoundException("Ese id de cliente no existe.");
		}
		this.clienteRepository.deleteById(idCliente);
	}
	
	//Modificar dirección de envío
	public Cliente updateDireccion(int idCliente, String direccion) {
		if(!this.clienteRepository.existsById(idCliente)) {
			throw new ClienteNotFoundException("Este id de cliente no existe.");
		}
		Cliente clienteBD = this.findById(idCliente);
		clienteBD.setDireccion(direccion);
		return this.clienteRepository.save(clienteBD);
	}
	
	//Buscar cliente por telefono
	public Cliente findByTelefono(String telefono) {
		Cliente cliente = this.findByTelefono(telefono);
		if(cliente == null) {
			throw new ClienteNotFoundException("Cliente no encontrado con ese teléfono");
		}
		return cliente;
	}
	
	
	
	
	
	
	
}
