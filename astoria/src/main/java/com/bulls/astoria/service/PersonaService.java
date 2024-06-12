package com.bulls.astoria.service;

import java.util.List;

import com.bulls.astoria.persistence.Persona;
import com.bulls.astoria.persistence.User;
import com.bulls.astoria.pojo.ProductoPrecio;

public interface PersonaService {

	void savePersona(Persona persona);
	void savePersona(Persona persona,User user);
	List <Persona> getPersonas (String cadena);
	List <Persona> getPersonas (String cadena, String tipoUsuario);
	Persona getPersonaXusername (String username);
	Persona getPersonaXId (Integer id);
	void editarPersona(Persona persona,User user);
	List <Persona> getEmpleados();
	
}	