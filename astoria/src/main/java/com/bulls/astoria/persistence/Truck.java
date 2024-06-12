package com.bulls.astoria.persistence;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="truck")
public class Truck implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id ;
	
	@Column(name = "codigo", nullable = false)
	private String codigo;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "descripcion", nullable = true)
	private String descripcion;
	
	@ManyToMany(fetch=FetchType.EAGER, mappedBy = "trucks")
	private Set<Handler> handlers = new HashSet<Handler>(0);
	
	
	public Set<Handler> getHandlers() {
		return this.handlers;
	}
 
	public void setHandlers(Set<Handler> handlers) {
		this.handlers = handlers;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	public Truck (Integer id,String codigo,String nombre,String decripcion){
		this.id = id;
		this.nombre = nombre;
		this.codigo = codigo;
		this.descripcion = decripcion;
		
	}
	
	public Truck(){
		
	}

	@Override
	public String toString() {
		return "Truck [id=" + id + ", codigo=" + codigo + ", nombre=" + nombre
				+ ", descripcion=" + descripcion + "]";
	}

	
	
	
	

}


