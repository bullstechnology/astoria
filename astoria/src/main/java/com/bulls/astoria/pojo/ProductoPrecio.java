package com.bulls.astoria.pojo;

import java.io.Serializable;

public class ProductoPrecio implements Serializable{
	
	 private Integer idPadre;
	 private String nombrePadre;
	 private Integer idProducto;
	 private String nombreProducto;
	 private Integer idgrado;
	 private Integer idcolor;
	 private String nombregrado;
	 private String nombrecolor;
	 private Double precio = 0.0;
	 private String codigo;
	 
	 
	 public ProductoPrecio (Integer idPadre,String nombrePadre,Integer idProducto,String nombreProducto,Double precio,Integer idgrado,String nombregrado,Integer idcolor, String nombrecolor){
	    	
	    	this.idPadre = idPadre;
	    	this.nombrePadre = nombrePadre;
	    	this.idProducto =  idProducto;
	    	this.nombreProducto = nombreProducto;
	    	this.precio = precio;
	    	this.idgrado = idgrado;
	    	this.nombregrado=nombregrado;
	    	this.idcolor=idcolor;
	    	this.nombrecolor=nombrecolor;

	 }
	 
	 public ProductoPrecio (Integer idPadre,String nombrePadre,Integer idProducto,String nombreProducto,Double precio,Integer idgrado,String nombregrado,Integer idcolor, String nombrecolor,String codigo){
	    	
	    	this.idPadre = idPadre;
	    	this.nombrePadre = nombrePadre;
	    	this.idProducto =  idProducto;
	    	this.nombreProducto = nombreProducto;
	    	this.precio = precio;
	    	this.idgrado = idgrado;
	    	this.nombregrado=nombregrado;
	    	this.idcolor=idcolor;
	    	this.nombrecolor=nombrecolor;
	    	this.codigo=codigo;

	 }
	public Integer getIdPadre() {
		return idPadre;
	}
	public void setIdPadre(Integer idPadre) {
		this.idPadre = idPadre;
	}
	public String getNombrePadre() {
		return nombrePadre;
	}
	public void setNombrePadre(String nombrePadre) {
		this.nombrePadre = nombrePadre;
	}
	public Integer getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}
	public String getNombreProducto() {
		return nombreProducto;
	}
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public Integer getIdgrado() {
		return idgrado;
	}
	public void setIdgrado(Integer idgrado) {
		this.idgrado = idgrado;
	}
	public Integer getIdcolor() {
		return idcolor;
	}
	public void setIdcolor(Integer idcolor) {
		this.idcolor = idcolor;
	}
	public String getNombregrado() {
		return nombregrado;
	}
	public void setNombregrado(String nombregrado) {
		this.nombregrado = nombregrado;
	}
	public String getNombrecolor() {
		return nombrecolor;
	}
	public void setNombrecolor(String nombrecolor) {
		this.nombrecolor = nombrecolor;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	
	 
	 

}
