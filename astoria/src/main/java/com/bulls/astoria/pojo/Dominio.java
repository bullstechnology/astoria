package com.bulls.astoria.pojo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
 



public class Dominio implements Serializable{


	private Integer id ;

	private Integer dominiopadre;

	private Integer idcolor;

	private String nomcorto;
	

	private String nomlargo;
	

	private String descripcion;
	

	private String url;
	

	private String codigo;
	

	private Double pesofullbox;
	

	private Double valor1;
	
	
	private String color;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public Integer getDominiopadre() {
		return dominiopadre;
	}
	public void setDominiopadre(Integer dominiopadre) {
		this.dominiopadre = dominiopadre;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getIdcolor() {
		return idcolor;
	}
	public void setIdcolor(Integer idcolor) {
		this.idcolor = idcolor;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Double getPesofullbox() {
		return pesofullbox;
	}
	public void setPesofullbox(Double pesofullbox) {
		this.pesofullbox = pesofullbox;
	}
	public Double getValor1() {
		return valor1;
	}
	public void setValor1(Double valor1) {
		this.valor1 = valor1;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	
	
	
}
