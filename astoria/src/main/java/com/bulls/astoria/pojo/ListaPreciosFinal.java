package com.bulls.astoria.pojo;

public class ListaPreciosFinal {
	
	String pais;
	String plantacion;
	String variedad;
	String grado;
	String color;
	String titulo;
	String fechaini;
	String fechafin;
	String precio;
	
	public ListaPreciosFinal(String pais, String plantacion, String variedad, String grado, String color, String titulo,
			String fechaini, String fechafin, String precio) {
		super();
		this.pais = pais;
		this.plantacion = plantacion;
		this.variedad = variedad;
		this.grado = grado;
		this.color = color;
		this.titulo = titulo;
		this.fechaini = fechaini;
		this.fechafin = fechafin;
		this.precio = precio;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getPlantacion() {
		return plantacion;
	}

	public void setPlantacion(String plantacion) {
		this.plantacion = plantacion;
	}

	public String getVariedad() {
		return variedad;
	}

	public void setVariedad(String variedad) {
		this.variedad = variedad;
	}

	public String getGrado() {
		return grado;
	}

	public void setGrado(String grado) {
		this.grado = grado;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getFechaini() {
		return fechaini;
	}

	public void setFechaini(String fechaini) {
		this.fechaini = fechaini;
	}

	public String getFechafin() {
		return fechafin;
	}

	public void setFechafin(String fechafin) {
		this.fechafin = fechafin;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}
	
	
	
	
	

}
