package com.bulls.astoria.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;


@Entity
@Table(name="plantacionproducto")
@IdClass(ProductoId.class)
public class PlantacionProducto implements Serializable{
	
	
	@Column(name = "idplantacion", nullable = false)
	@Id
	private Integer idplantacion;
	
	@Column(name = "idproducto", nullable = false)
	@Id
	private Integer idproducto;
	@Column(name = "caracteristica", nullable = true)
	private Character caracteristica;
	@Column(name = "estado", nullable = true)
	private boolean estado;
	
	
	public Integer getIdplantacion() {
		return idplantacion;
	}
	public void setIdplantacion(Integer idplantacion) {
		this.idplantacion = idplantacion;
	}
	public Integer getIdproducto() {
		return idproducto;
	}
	public void setIdproducto(Integer idproducto) {
		this.idproducto = idproducto;
	}
	public Character getCaracteristica() {
		return caracteristica;
	}
	public void setCaracteristica(Character caracteristica) {
		this.caracteristica = caracteristica;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	@Override
	public String toString() {
		return "PlantacionProducto [idplantacion=" + idplantacion
				+ ", idproducto=" + idproducto + ", caracteristica="
				+ caracteristica + ", estado=" + estado + "]";
	}
	
	
	
	

}
