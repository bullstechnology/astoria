package com.bulls.astoria.service;



import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bulls.astoria.dao.PersonaDao;
import com.bulls.astoria.dao.UserDao;
import com.bulls.astoria.persistence.Persona;
import com.bulls.astoria.persistence.User;
import com.bulls.astoria.pojo.ProductoPrecio;

@Service("PersonaService")
@Transactional
public class PersonaServiceImpl implements PersonaService, Serializable{
	
	@Autowired
    private PersonaDao dao;
	
	
	@Autowired
    private UserDao userdao;
    
	
	@Transactional
    public void savePersona(Persona persona) {
		
        dao.savePersona(persona);
        
    }
	
	@Transactional
    public void savePersona(Persona persona, User user) {
		
        dao.savePersona(persona);
        userdao.saveUser(user);
    }
	
	@Transactional
	public List <Persona> getPersonas (String cadena){
		return dao.getPersonas(cadena);
	}
	
	@Transactional
	public List <Persona> getPersonas (String cadena,String tipoUsuario){
		return dao.getPersonas(cadena,tipoUsuario);
	}
	
	@Transactional
	public Persona getPersonaXusername (String username){
		return dao.getPersonaXusername(username);
	}
	
	@Transactional
	public void editarPersona (Persona persona, User user){
		dao.editarPersona(persona);
        userdao.editarUser(user);
	}
	
	public List <Persona> getEmpleados(){
		return dao.getEmpleados();
	}
	
	public Persona getPersonaXId (Integer id){
		return dao.getPersonaXId(id);
	}

}
