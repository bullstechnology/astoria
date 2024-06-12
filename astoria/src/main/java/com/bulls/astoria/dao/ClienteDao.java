package com.bulls.astoria.dao;

import java.util.List;
import java.util.Map;

import com.bulls.astoria.persistence.Cliente;
import com.bulls.astoria.persistence.Handler;
import com.bulls.astoria.persistence.Truck;

public interface ClienteDao {
	
	void crearCliente(Cliente Cliente);
	List <Cliente> getClientes();
	List  getTrucks(Integer idCliente); 
	List  getHandlers(Integer idCliente);
	List  getVendedores(Integer idCliente); 
	List  getCompradores(Integer idCliente);
	void editarCliente(Cliente cliente);
	Cliente getClienteXId(Integer idCliente);

}
