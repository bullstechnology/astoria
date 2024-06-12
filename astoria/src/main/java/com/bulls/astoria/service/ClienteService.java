package com.bulls.astoria.service;

import java.util.List;

import com.bulls.astoria.persistence.Cliente;
import com.bulls.astoria.persistence.Handler;
import com.bulls.astoria.persistence.Truck;

public interface ClienteService {

	
	public void crearCliente(Cliente cliente);
	public List <Cliente> getClientes();
	public List getTrucks(Integer idCliente);
	public List getHandlers(Integer idCliente);
	public List getVendedores(Integer idCliente);
	public List getCompradores(Integer idCliente);
	public void editarCliente(Cliente cliente);
	public Cliente getClienteXId(Integer idCliente);
}
