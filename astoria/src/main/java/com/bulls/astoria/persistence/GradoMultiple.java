package com.bulls.astoria.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="gradomultiple")
public class GradoMultiple implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "idgrado", nullable = false)
	private Integer idgrado;
	@Column(name = "idgradocompone", nullable = false)
	private Integer idgradocompone;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdgrado() {
		return idgrado;
	}
	public void setIdgrado(Integer idgrado) {
		this.idgrado = idgrado;
	}
	public Integer getIdgradocompone() {
		return idgradocompone;
	}
	public void setIdgradocompone(Integer idgradocompone) {
		this.idgradocompone = idgradocompone;
	}
	
	
}
