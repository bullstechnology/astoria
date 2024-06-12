package com.bulls.astoria.persistence;

import java.io.Serializable;
import java.util.HashSet;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="handler")
public class Handler implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id ;
	
	@Column(name = "codigo", nullable = false)
	private String codigo;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "descripcion", nullable = true)
	private String descripcion;
	
	@Column(name = "preculincli", nullable = false)
	private Double preculincli;
	
	@Column(name = "preculinast", nullable = false)
	private Double preculinast;
	
	@Column(name = "saldofinal", nullable = true)
	private Double saldofinal;
	
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "handlertruck", joinColumns = { 
			@JoinColumn(name = "idhandler", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "idtruck", 
					nullable = false, updatable = false) })
	
	private Set<Truck> trucks = new HashSet<Truck>(0);
	
	public Set<Truck> getTrucks() {
		return this.trucks;
	}
	
	public void setTrucks(Set<Truck> trucks) {
		this.trucks = trucks;
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

	public Handler (Integer id,String codigo,String nombre,String decripcion){
		this.id = id;
		this.nombre = nombre;
		this.codigo = codigo;
		this.descripcion = decripcion;
		
	}
	
	
	
	public Handler(Integer id, String codigo, String nombre,
			String descripcion, Double preculincli, Double preculinast) {
		this.id = id;
		this.codigo = codigo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.preculincli = preculincli;
		this.preculinast = preculinast;
	}

	public Double getPreculincli() {
		return preculincli;
	}

	public void setPreculincli(Double preculincli) {
		this.preculincli = preculincli;
	}

	public Double getPreculinast() {
		return preculinast;
	}

	public void setPreculinast(Double preculinast) {
		this.preculinast = preculinast;
	}

	public Handler(){
		
	}

	
	public Double getSaldofinal() {
		return saldofinal;
	}

	public void setSaldofinal(Double saldofinal) {
		this.saldofinal = saldofinal;
	}

	@Override
	public String toString() {
		return "Handler [id=" + id + ", codigo=" + codigo + ", nombre=" + nombre + ", descripcion=" + descripcion
				+ ", preculincli=" + preculincli + ", preculinast=" + preculinast + ", saldofinal=" + saldofinal
				+ ", trucks=" + trucks + "]";
	}

	


	
	

}