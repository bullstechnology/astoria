package com.bulls.astoria.dao;

import java.util.List;

import com.bulls.astoria.persistence.Persona;

public interface PersonaDao {
	
	
	void savePersona(Persona persona); 
	void editarPersona(Persona persona); 
	
	List <Persona> getPersonas(String cadena);
	List <Persona> getPersonas(String cadena,String tipoUsuario);
	Persona getPersonaXusername (String username);
	List <Persona> getEmpleados();
	
	Persona getPersonaXId (Integer id);
}
