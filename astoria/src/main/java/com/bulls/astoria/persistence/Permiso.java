package com.bulls.astoria.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="permisos")
public class Permiso implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "idrole", nullable = true)
	private Integer idrole;
	@Column(name = "idproceso", nullable = true)
	private Integer idproceso;
	@Column(name = "tipo", nullable = true)
	private String tipo;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getIdrole() {
		return idrole;
	}
	public void setIdrole(Integer idrole) {
		this.idrole = idrole;
	}
	public Integer getIdproceso() {
		return idproceso;
	}
	public void setIdproceso(Integer idproceso) {
		this.idproceso = idproceso;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	@Override
	public String toString() {
		return "Permiso [id=" + id + ", idrole=" + idrole + ", idproceso="
				+ idproceso + ", tipo=" + tipo + "]";
	}
	
	
	

}
