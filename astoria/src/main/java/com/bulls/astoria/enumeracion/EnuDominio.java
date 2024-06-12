package com.bulls.astoria.enumeracion;

public enum EnuDominio {
	
	TIPOS_DOCUMENTO ("Tipo Documento",1),
	PAISES("Paises",2), CIUDADES("Ciudades",3),DEPARTAMENTOS("Departamentos",4), TIPOS_PERSONA ("Tipo Persona",5),
	CARGOS("Cargos",10),FLORES("Flores",8),COLORES("Colores",6),VARIEDADES("Variedades",7),GRADOS("Grados",11),TRUCKS("Trucks",12),RECOMIENDA("Recomienda",13),GRADOSMULTIPLE("Recomienda",14)
	,CONCEPTOSNOTAS("Conceptosnotas",15),ESTADOSNOTAS("estadosnotas",16); 
	
	private String nombre;
	private int idTipoDominio;
	
	private EnuDominio (String nombre, int idTipoDominio){
		this.nombre = nombre;
		this.idTipoDominio = idTipoDominio;
	}

	public String getNombre() {
		return nombre;
	}

	public int getIdTipoDominio() {
		return idTipoDominio;
	}	
	

}
