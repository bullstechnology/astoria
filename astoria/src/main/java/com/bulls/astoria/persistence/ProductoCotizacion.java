package com.bulls.astoria.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="productocotizacion")
public class ProductoCotizacion implements Serializable{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "precio", nullable = false)
	private Double precio;
	@Column(name = "preciototal", nullable = false)
	private Double preciototal;
	@Column(name = "flor", nullable = false)
	private Integer flor;
	@Column(name = "variedad", nullable = false)
	private Integer variedad;
	@Column(name = "grado", nullable = false)
	private Integer grado;
	@Column(name = "idcotizacion", nullable = false)
	private Integer idcotizacion;
	@Column(name = "boxes", nullable = false)
	private Integer boxes;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public Double getPreciototal() {
		return preciototal;
	}
	public void setPreciototal(Double preciototal) {
		this.preciototal = preciototal;
	}
	public Integer getFlor() {
		return flor;
	}
	public void setFlor(Integer flor) {
		this.flor = flor;
	}
	public Integer getVariedad() {
		return variedad;
	}
	public void setVariedad(Integer variedad) {
		this.variedad = variedad;
	}
	public Integer getGrado() {
		return grado;
	}
	public void setGrado(Integer grado) {
		this.grado = grado;
	}
	
	
	public Integer getIdcotizacion() {
		return idcotizacion;
	}
	public void setIdcotizacion(Integer idcotizacion) {
		this.idcotizacion = idcotizacion;
	}
	public Integer getBoxes() {
		return boxes;
	}
	public void setBoxes(Integer boxes) {
		this.boxes = boxes;
	}
	@Override
	public String toString() {
		return "ProductoCotizacion [id=" + id + ", precio=" + precio + ", preciototal=" + preciototal + ", flor=" + flor
				+ ", variedad=" + variedad + ", grado=" + grado + ", idcotizacion=" + idcotizacion + ", boxes=" + boxes
				+ "]";
	}
	

	
	
	
	
}