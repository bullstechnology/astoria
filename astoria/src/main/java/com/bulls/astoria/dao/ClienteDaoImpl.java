package com.bulls.astoria.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bulls.astoria.persistence.Cliente;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.Handler;
import com.bulls.astoria.persistence.Truck;



@Repository("clienteDao")
public class ClienteDaoImpl extends AbstractDao implements ClienteDao  {

	@Transactional
	public void crearCliente(Cliente cliente){
		persist(cliente);
	}
	
	public List <Cliente> getClientes(){
		Query query = getSession().createQuery("from Cliente order by nombre");
		return query.list();
	}
		
	public List getTrucks(Integer idCliente){
		Query query = getSession().createQuery("select new Truck (t.id,t.codigo,t.nombre,t.descripcion) from Truck t, ClienteTruck ct where ct.idcliente = :idCliente and ct.idtruck = t.id ");
		query.setInteger("idCliente", idCliente);
		return query.list();
	}
	public List getHandlers(Integer idCliente){
		Query query = getSession().createQuery("select new Handler (t.id,t.codigo,t.nombre,t.descripcion) from Handler t, ClienteHandler ct where ct.idcliente = :idCliente and ct.idhandler = t.id ");
		query.setInteger("idCliente", idCliente);
		return query.list();
	}
	
	public void editarCliente (Cliente cliente){
		getSession().update(cliente);
		getSession().flush();
		return;
	}
	
	public Cliente getClienteXId(Integer idCliente){
		Query query = getSession().createQuery("from Cliente where id =:id ");
		query.setInteger("id", idCliente);
		return (Cliente) query.uniqueResult();
	}
	
	
	public List getVendedores(Integer idCliente){
		Query query = getSession().createQuery("select new Persona (t.id,t.nombre,t.segnombre,t.apellido,t.segapellido) from Persona t, ClienteVendedor ct where ct.idcliente = :idCliente and ct.idvendedor = t.id ");
		query.setInteger("idCliente", idCliente);
		return query.list();
	}
	
	public List getCompradores(Integer idCliente){
		Query query = getSession().createQuery("select new Persona (t.id,t.nombre,t.segnombre,t.apellido,t.segapellido) from Persona t, ClienteComprador ct where ct.idcliente = :idCliente and ct.idcomprador = t.id ");
		query.setInteger("idCliente", idCliente);
		return query.list();
	}
}
