package com.bulls.astoria.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="tipoflorgrados")
public class ProductoGrados implements Serializable{
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "idtipoflor", nullable = false)
	private Integer idtipoflor;
	@Column(name = "idgrado", nullable = false)
	private Integer idgrado;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdtipoflor() {
		return idtipoflor;
	}
	public void setIdtipoflor(Integer idtipoflor) {
		this.idtipoflor = idtipoflor;
	}
	public Integer getIdgrado() {
		return idgrado;
	}
	public void setIdgrado(Integer idgrado) {
		this.idgrado = idgrado;
	}
	@Override
	public String toString() {
		return "ProductoGrados [id=" + id + ", idtipoflor=" + idtipoflor
				+ ", idgrado=" + idgrado + "]";
	}
	
	
    
}
