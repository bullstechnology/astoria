package com.bulls.astoria.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;



@Entity
@Table(name="plantacionpacking")
@IdClass(PackingtId.class)
public class PlantacionPacking implements Serializable{
	
	@Column(name = "idplantacion", nullable = false)
	@Id
	private Integer idplantacion;
	@Column(name = "idgrado", nullable = false)
	@Id
	private Integer idgrado;
	@Column(name = "cantidad", nullable = true)
	private Integer cantidad;
	public Integer getIdplantacion() {
		return idplantacion;
	}
	public void setIdplantacion(Integer idplantacion) {
		this.idplantacion = idplantacion;
	}
	public Integer getIdgrado() {
		return idgrado;
	}
	public void setIdgrado(Integer idgrado) {
		this.idgrado = idgrado;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	@Override
	public String toString() {
		return "PlantacionPacking [idplantacion=" + idplantacion + ", idgrado="
				+ idgrado + ", cantidad=" + cantidad + "]";
	}
	
	
	

}
