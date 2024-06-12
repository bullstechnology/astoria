package com.bulls.astoria.pojo;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;


public class ListaPrecioBean implements Serializable{
	
	private Integer id;
	private Character tipo;
	private Integer idfranja;
	private Integer idplantacion;
	

	private String nombrefranja;
	private String nombreplantacion;
	
	private Date fechaini;
	private Date fechafin;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Character getTipo() {
		return tipo;
	}
	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}
	public Integer getIdfranja() {
		return idfranja;
	}
	public void setIdfranja(Integer idfranja) {
		this.idfranja = idfranja;
	}
	public Integer getIdplantacion() {
		return idplantacion;
	}
	public void setIdplantacion(Integer idplantacion) {
		this.idplantacion = idplantacion;
	}
	public String getNombrefranja() {
		return nombrefranja;
	}
	public void setNombrefranja(String nombrefranja) {
		this.nombrefranja = nombrefranja;
	}
	public String getNombreplantacion() {
		return nombreplantacion;
	}
	public void setNombreplantacion(String nombreplantacion) {
		this.nombreplantacion = nombreplantacion;
	}
	public Date getFechaini() {
		return fechaini;
	}
	public void setFechaini(Date fechaini) {
		this.fechaini = fechaini;
	}
	public Date getFechafin() {
		return fechafin;
	}
	public void setFechafin(Date fechafin) {
		this.fechafin = fechafin;
	}
	
	
	

}
