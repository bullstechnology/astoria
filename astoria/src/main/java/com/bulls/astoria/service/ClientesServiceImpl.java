package com.bulls.astoria.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bulls.astoria.dao.ClienteDao;
import com.bulls.astoria.dao.DominioDao;
import com.bulls.astoria.persistence.Cliente;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.Handler;
import com.bulls.astoria.persistence.Truck;


@Service("ClienteService")
@Transactional
public class ClientesServiceImpl implements  ClienteService, Serializable{

	@Autowired
    private ClienteDao clienteDAO;
	
	@Transactional
	public void crearCliente(Cliente cliente){
		clienteDAO.crearCliente(cliente);
	}
	
	public List <Cliente> getClientes(){
		return clienteDAO.getClientes();
	}
	
	public List  getHandlers(Integer idCliente){
		return clienteDAO.getHandlers(idCliente);
	}
	
	public List  getTrucks(Integer idCliente){
		return clienteDAO.getTrucks(idCliente);
	}
	
	public void editarCliente(Cliente cliente){
		clienteDAO.editarCliente(cliente);
		return;
	}
	
	public Cliente getClienteXId(Integer idCliente){
		return clienteDAO.getClienteXId(idCliente);
	}
	
	public List getCompradores(Integer idCliente){
		return clienteDAO.getCompradores(idCliente);
	}
	
	public List getVendedores(Integer idCliente){
		return clienteDAO.getVendedores(idCliente);
	}

}
