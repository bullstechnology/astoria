package com.bulls.astoria.persistence;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="detallepedidoconciliado")
public class DetallePedidoConciliado implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "cantidadfull", nullable = true)
	private Double cantidadfull;
	@Column(name = "cantidadporfull", nullable = true)
	private Integer cantidadporfull;
	@Column(name = "cantidadtotal", nullable = true)
	private Integer cantidadtotal;
	@Column(name = "idplantacion", nullable = true)
	private Integer idplantacion;
	@Column(name = "precio", nullable = true)
	private Double precio;
	@Column(name = "observaciones", nullable = true)
	private String observaciones;

	
	@ManyToOne
    @JoinColumn(name="idpedido", nullable=false)
    private PedidoConciliado pedido;
	
	
	@OneToMany(mappedBy="detalle" , cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private Set<ComposicionConciliado> composiciones;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public Double getCantidadfull() {
		return cantidadfull;
	}
	public void setCantidadfull(Double cantidadfull) {
		this.cantidadfull = cantidadfull;
	}
	public Integer getCantidadporfull() {
		return cantidadporfull;
	}
	public void setCantidadporfull(Integer cantidadporfull) {
		this.cantidadporfull = cantidadporfull;
	}
	public Integer getCantidadtotal() {
		return cantidadtotal;
	}
	public void setCantidadtotal(Integer cantidadtotal) {
		this.cantidadtotal = cantidadtotal;
	}
	public Integer getIdplantacion() {
		return idplantacion;
	}
	public void setIdplantacion(Integer idplantacion) {
		this.idplantacion = idplantacion;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public PedidoConciliado getPedido() {
		return pedido;
	}
	public void setPedido(PedidoConciliado pedido) {
		this.pedido = pedido;
	}
	public Set<ComposicionConciliado> getComposiciones() {
		return composiciones;
	}
	public void setComposiciones(Set<ComposicionConciliado> composiciones) {
		this.composiciones = composiciones;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	@Override
	public String toString() {
		return "DetallePedidoConciliado [id=" + id + ", cantidadfull="
				+ cantidadfull + ", cantidadporfull=" + cantidadporfull
				+ ", cantidadtotal=" + cantidadtotal + ", idplantacion="
				+ idplantacion + ", precio=" + precio + ", observaciones="
				+ observaciones + "]";
	}
    
	
}
