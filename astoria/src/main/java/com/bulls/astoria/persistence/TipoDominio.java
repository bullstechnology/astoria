package com.bulls.astoria.persistence;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name="tipodominio")
public class TipoDominio implements Serializable{

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nomcorto", nullable = false)
	private String nomcorto;
	
	@Column(name = "nomlargo", nullable = false)
	private String nomlargo;
	
	@Column(name = "descripcion", nullable = false)
	private String descripcion;
	
	
	/* @OneToMany(mappedBy="tipodominio")
	 private Set<Dominio> dominios;*/
	
	 @OneToMany(mappedBy="tipodominio")
	 private Set<Dominio> dominios;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNomcorto() {
		return nomcorto;
	}
	public void setNomcorto(String nomcorto) {
		this.nomcorto = nomcorto;
	}
	public String getNomlargo() {
		return nomlargo;
	}
	public void setNomlargo(String nomlargo) {
		this.nomlargo = nomlargo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Set<Dominio> getDominios() {
		return dominios;
	}
	public void setDominios(Set<Dominio> dominios) {
		this.dominios = dominios;
	}
	
	
	
	
	
}
