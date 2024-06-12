package com.bulls.astoria.pojo;

import java.io.Serializable;

public class DetallePedidoBean implements Serializable{

	private Integer idpedido;
	private Integer iddetalle;
	private Integer idcomposicion;
	private Integer idproducto;
	private Integer idplantacion;
	private String nombretipo;
	private String nombrevariedad;
	private String nombregrado;
	private String nombreplantacion;
	private Integer cantidadporfull;
	private Integer cantidaddefull;
	private Integer cantidadtotall;
	private Integer packing;
	private Double precio;
	public Integer getIdpedido() {
		return idpedido;
	}
	public void setIdpedido(Integer idpedido) {
		this.idpedido = idpedido;
	}
	public Integer getIddetalle() {
		return iddetalle;
	}
	public void setIddetalle(Integer iddetalle) {
		this.iddetalle = iddetalle;
	}
	public Integer getIdcomposicion() {
		return idcomposicion;
	}
	public void setIdcomposicion(Integer idcomposicion) {
		this.idcomposicion = idcomposicion;
	}
	public Integer getIdproducto() {
		return idproducto;
	}
	public void setIdproducto(Integer idproducto) {
		this.idproducto = idproducto;
	}
	public Integer getIdplantacion() {
		return idplantacion;
	}
	public void setIdplantacion(Integer idplantacion) {
		this.idplantacion = idplantacion;
	}
	public String getNombretipo() {
		return nombretipo;
	}
	public void setNombretipo(String nombretipo) {
		this.nombretipo = nombretipo;
	}
	public String getNombrevariedad() {
		return nombrevariedad;
	}
	public void setNombrevariedad(String nombrevariedad) {
		this.nombrevariedad = nombrevariedad;
	}
	public String getNombregrado() {
		return nombregrado;
	}
	public void setNombregrado(String nombregrado) {
		this.nombregrado = nombregrado;
	}
	public String getNombreplantacion() {
		return nombreplantacion;
	}
	public void setNombreplantacion(String nombreplantacion) {
		this.nombreplantacion = nombreplantacion;
	}
	public Integer getCantidadporfull() {
		return cantidadporfull;
	}
	public void setCantidadporfull(Integer cantidadporfull) {
		this.cantidadporfull = cantidadporfull;
	}
	public Integer getCantidaddefull() {
		return cantidaddefull;
	}
	public void setCantidaddefull(Integer cantidaddefull) {
		this.cantidaddefull = cantidaddefull;
	}
	public Integer getCantidadtotall() {
		return cantidadtotall;
	}
	public void setCantidadtotall(Integer cantidadtotall) {
		this.cantidadtotall = cantidadtotall;
	}
	public Integer getPacking() {
		return packing;
	}
	public void setPacking(Integer packing) {
		this.packing = packing;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	
	
	
	
	
}
