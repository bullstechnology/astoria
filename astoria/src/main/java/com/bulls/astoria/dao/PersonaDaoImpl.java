package com.bulls.astoria.dao;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bulls.astoria.persistence.Persona;
import com.bulls.astoria.persistence.User;


@Repository("personaDao")
public class PersonaDaoImpl extends AbstractDao implements PersonaDao{

	@Transactional
	public void savePersona(Persona persona){
		
		persist(persona);
		
	}
	
	@Transactional
	public void editarPersona(Persona persona){
		getSession().update(persona);
	    getSession().flush();
	}
	
	
	
	public List <Persona> getPersonas(String cadena){
		List<Persona> personaList = new ArrayList<Persona>();
        Query query = getSession().createQuery("from Persona p where lower(p.username) like :cadena or lower(p.nombre) like :cadena or lower(p.segnombre) like :cadena or lower (p.apellido) like :cadena or lower(p.segapellido) like :cadena");
        query.setParameter("cadena", "%" + cadena.toLowerCase() + "%");
        personaList = query.list();
        return personaList;
	}
	
	public List <Persona> getPersonas(String cadena,String  tipoUsuario){
		List<Persona> personaList = new ArrayList<Persona>();
		String querystr = "from Persona p where (lower(p.username) like :cadena or lower(p.nombre) like :cadena or lower(p.segnombre) like :cadena or lower (p.apellido) like :cadena or lower(p.segapellido) like :cadena) and ";
        
        
        
        if(tipoUsuario.equalsIgnoreCase("E")){
        	querystr = querystr + "p.empleado = 1"; 
        }else if (tipoUsuario.equalsIgnoreCase("P")){
        	querystr = querystr + "p.plantacion = 1"; 
        }else if (tipoUsuario.equalsIgnoreCase("C")){
        	querystr = querystr + "p.cliente = 1"; 
        }else if (tipoUsuario.equalsIgnoreCase("A")){
        	querystr = querystr + "p.agencia = 1"; 
        }else if (tipoUsuario.equalsIgnoreCase("H")){
        	querystr = querystr + "p.handler = 1"; 
        }
        
        Query query = getSession().createQuery(querystr);
        query.setParameter("cadena", "%" + cadena.toLowerCase() + "%");
        
        personaList = query.list();
        return personaList;
	}
	
	
	public Persona getPersonaXusername (String username){
        Query query = getSession().createQuery("from Persona p where p.username = :username");
        query.setParameter("username", username);
        Persona persona  = (Persona) query.uniqueResult();
        return persona;
	}
	
	
	public List <Persona> getEmpleados(){
		
		Query query = getSession().createQuery("from Persona p where p.empleado = 1");
        return query.list();
		
	}
	
	public Persona getPersonaXId (Integer id){
		 Query query = getSession().createQuery("from Persona p where p.id = :id");
	        query.setParameter("id", id);
	        Persona persona  = (Persona) query.uniqueResult();
	        return persona;
	}
	
}
