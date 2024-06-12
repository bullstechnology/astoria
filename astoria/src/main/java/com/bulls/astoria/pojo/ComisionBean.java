package com.bulls.astoria.pojo;

import java.io.Serializable;
import java.util.Date;

public class ComisionBean implements Serializable{
	
	private Integer id;
	private Integer idPais;
	private Integer idTipoFlor;
	private Integer idGrado;
	private Integer idtipoflorgrados;
	private String nombreflor;
	private String nombregrado;
	private Double comision = 0.0;
	private String username;
	private Date fecha;
	
	
	
	
	public ComisionBean(Integer id, Integer idPais, Integer idTipoFlor,
			Integer idGrado, Integer idtipoflorgrados, String nombreflor,
			String nombregrado, Double comision, String username, Date fecha) {
		super();
		this.id = id;
		this.idPais = idPais;
		this.idTipoFlor = idTipoFlor;
		this.idGrado = idGrado;
		this.idtipoflorgrados = idtipoflorgrados;
		this.nombreflor = nombreflor;
		this.nombregrado = nombregrado;
		this.comision = comision;
		this.username = username;
		this.fecha = fecha;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdPais() {
		return idPais;
	}
	public void setIdPais(Integer idPais) {
		this.idPais = idPais;
	}
	public Integer getIdTipoFlor() {
		return idTipoFlor;
	}
	public void setIdTipoFlor(Integer idTipoFlor) {
		this.idTipoFlor = idTipoFlor;
	}
	public Integer getIdGrado() {
		return idGrado;
	}
	public void setIdGrado(Integer idGrado) {
		this.idGrado = idGrado;
	}
	public String getNombreflor() {
		return nombreflor;
	}
	public void setNombreflor(String nombreflor) {
		this.nombreflor = nombreflor;
	}
	public String getNombregrado() {
		return nombregrado;
	}
	public void setNombregrado(String nombregrado) {
		this.nombregrado = nombregrado;
	}
	public Double getComision() {
		return comision;
	}
	public void setComision(Double comision) {
		this.comision = comision;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Integer getIdtipoflorgrados() {
		return idtipoflorgrados;
	}
	public void setIdtipoflorgrados(Integer idtipoflorgrados) {
		this.idtipoflorgrados = idtipoflorgrados;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	
	
	

}
