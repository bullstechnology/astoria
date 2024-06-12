package com.bulls.astoria.pojo;

import java.io.Serializable;

public class Producto implements Serializable{
	
	
    private Integer idPadre;
    private String nombrePadre;
    private Integer idProducto;
    private String nombreProducto;
    private String recomendado;
    private boolean estado = true ;
    private Integer color ;
    private Integer grado;
    private String nombrecolor ;
    private String nombregrado;
    private String codigo;
    private Integer id;
    
    public Producto (Integer idPadre,String nombrePadre,Integer idProducto,String nombreProducto){
    	
    	this.idPadre = idPadre;
    	this.nombrePadre = nombrePadre;
    	this.idProducto =  idProducto;
    	this.nombreProducto = nombreProducto;

    }
    
 public Producto (Integer idPadre,String nombrePadre,Integer idProducto,String nombreProducto,String recomendado, boolean estado){
    	
    	this.idPadre = idPadre;
    	this.nombrePadre = nombrePadre;
    	this.idProducto =  idProducto;
    	this.nombreProducto = nombreProducto;
    	this.estado = estado;
    	this.recomendado= recomendado;

    }
 public Producto (Integer id, Integer idPadre,String nombrePadre,Integer idProducto,String nombreProducto,String nombregrado,String recomendado, boolean estado){
	 this.id= id;
 	this.idPadre = idPadre;
 	this.nombrePadre = nombrePadre;
 	this.idProducto =  idProducto;
 	this.nombreProducto = nombreProducto;
 	this.nombregrado = nombregrado;
 	this.estado = estado;
 	this.recomendado= recomendado;
 	

 }
 
 public Producto (Integer idPadre,String nombrePadre,Integer idProducto,String nombreProducto,Integer color,String nomcolor,Integer grado, String nomgrado){
 	
 	this.idPadre = idPadre;
 	this.nombrePadre = nombrePadre;
 	this.idProducto =  idProducto;
 	this.nombreProducto = nombreProducto;
 	this.estado = estado;
 	this.recomendado= recomendado;
 	this.color= color;
 	this.nombrecolor = nomcolor;
 	this.grado=grado;
 	this.nombregrado=nomgrado;
 }
 
 public Producto (Integer idPadre,String nombrePadre,Integer idProducto,String nombreProducto,Integer grado, String nomgrado,String codigo,boolean estado){
	 	
	 	this.idPadre = idPadre;
	 	this.nombrePadre = nombrePadre;
	 	this.idProducto =  idProducto;
	 	this.nombreProducto = nombreProducto;
	 	this.grado=grado;
	 	this.nombregrado=nomgrado;
	 	this.codigo =codigo;
	 	this.estado = estado;
	 }
 
 public Producto (Integer id,Integer idPadre,String nombrePadre,Integer idProducto,String nombreProducto,Integer grado, String nomgrado,String codigo,boolean estado){
	 this.id = id;
	 	this.idPadre = idPadre;
	 	this.nombrePadre = nombrePadre;
	 	this.idProducto =  idProducto;
	 	this.nombreProducto = nombreProducto;
	 	this.grado=grado;
	 	this.nombregrado=nomgrado;
	 	this.codigo =codigo;
	 	this.estado = estado;
	 	
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

	public String getRecomendado() {
		return recomendado;
	}

	public void setRecomendado(String recomendado) {
		this.recomendado = recomendado;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Integer getColor() {
		return color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	public Integer getGrado() {
		return grado;
	}

	public void setGrado(Integer grado) {
		this.grado = grado;
	}

	public String getNombrecolor() {
		return nombrecolor;
	}

	public void setNombrecolor(String nombrecolor) {
		this.nombrecolor = nombrecolor;
	}

	public String getNombregrado() {
		return nombregrado;
	}

	public void setNombregrado(String nombregrado) {
		this.nombregrado = nombregrado;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
   
}
