package com.bulls.astoria.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="composicionfacturado")

public class ComposicionFacturado implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "idproducto", nullable = false)
	private Integer idproducto;
	@Column(name = "cantidadporfull", nullable = false)
	private Integer cantidadporfull;
	@Column(name = "precio", nullable = true)
	private Double precio;
	@Column(name = "comision", nullable = true)
	private Double comision;
	@Column(name = "observaciones", nullable = true)
	private String observaciones;
	
	@ManyToOne
    @JoinColumn(name="iddetalle", nullable=false)
    private DetallePedidoFacturado detalle;
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getIdproducto() {
		return idproducto;
	}
	public void setIdproducto(Integer idproducto) {
		this.idproducto = idproducto;
	}
	public Integer getCantidadporfull() {
		return cantidadporfull;
	}
	public void setCantidadporfull(Integer cantidadporfull) {
		this.cantidadporfull = cantidadporfull;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public DetallePedidoFacturado getDetalle() {
		return detalle;
	}
	public void setDetalle(DetallePedidoFacturado detalle) {
		this.detalle = detalle;
	}
	public Double getComision() {
		return comision;
	}
	public void setComision(Double comision) {
		this.comision = comision;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	@Override
	public String toString() {
		return "ComposicionFacturado [id=" + id + ", idproducto=" + idproducto
				+ ", cantidadporfull=" + cantidadporfull + ", precio=" + precio
				+ ", comision=" + comision + ", observaciones=" + observaciones
				+ "]";
	}
	
	
	

}

