package com.bulls.astoria.pojo;

import java.io.Serializable;

public class Grado implements Serializable{

	private Integer id;
	private String nombreCorto;
	private String nombreLargo;
	private String descripcion;
	private Integer cantidad;
	private boolean enabled;
	
	public Grado (Integer id, String nombreCorto,String nombreLargo, String descripcion, Integer cantidad){
		this.id=id;
		this.nombreCorto=nombreCorto;
		this.nombreLargo=nombreLargo;
		this.descripcion=descripcion;
		this.cantidad=cantidad;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombreCorto() {
		return nombreCorto;
	}
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}
	public String getNombreLargo() {
		return nombreLargo;
	}
	public void setNombreLargo(String nombreLargo) {
		this.nombreLargo = nombreLargo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	
	
}
