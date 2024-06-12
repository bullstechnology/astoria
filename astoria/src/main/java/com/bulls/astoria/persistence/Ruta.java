package com.bulls.astoria.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="ruta")
public class Ruta implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "idpaisorigen", nullable = false)
	private Integer idpaisorigen;
	@Column(name = "idpaisdestino", nullable = false)
	private Integer idpaisdestino;
	@Column(name = "idciudadorigen", nullable = false)
	private Integer idciudadorigen;
	@Column(name = "idciudaddestino", nullable = false)
	private Integer idciudaddestino;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdpaisorigen() {
		return idpaisorigen;
	}
	public void setIdpaisorigen(Integer idpaisorigen) {
		this.idpaisorigen = idpaisorigen;
	}
	public Integer getIdpaisdestino() {
		return idpaisdestino;
	}
	public void setIdpaisdestino(Integer idpaisdestino) {
		this.idpaisdestino = idpaisdestino;
	}
	public Integer getIdciudadorigen() {
		return idciudadorigen;
	}
	public void setIdciudadorigen(Integer idciudadorigen) {
		this.idciudadorigen = idciudadorigen;
	}
	public Integer getIdciudaddestino() {
		return idciudaddestino;
	}
	public void setIdciudaddestino(Integer idciudaddestino) {
		this.idciudaddestino = idciudaddestino;
	}
	@Override
	public String toString() {
		return "Ruta [id=" + id + ", idpaisorigen=" + idpaisorigen
				+ ", idpaisdestino=" + idpaisdestino + ", idciudadorigen="
				+ idciudadorigen + ", idciudaddestino=" + idciudaddestino + "]";
	}
	
	

}


	