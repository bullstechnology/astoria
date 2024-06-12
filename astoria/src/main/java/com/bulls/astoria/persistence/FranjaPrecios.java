package com.bulls.astoria.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="franjaprecios")
public class FranjaPrecios implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "idplantacion", nullable = false)
	private Integer idplantacion;
	
	@Column(name = "titulo", nullable = true)
	private String titulo;

	
	@Column(name = "descripcion", nullable = true)
	private String descripcion;
	
	@Column(name = "fechaini", nullable = false)
	private Date fechaini;
	
	@Column(name = "fechafin", nullable = false)
	private Date fechafin;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaini() {
		return fechaini;
	}

	public void setFechaini(Date fechaini) {
		this.fechaini = fechaini;
	}

	public Date getFechafin() {
		return fechafin;
	}

	public void setFechafin(Date fechafin) {
		this.fechafin = fechafin;
	}

	public Integer getIdplantacion() {
		return idplantacion;
	}

	public void setIdplantacion(Integer idplantacion) {
		this.idplantacion = idplantacion;
	}

	@Override
	public String toString() {
		return "FranjaPrecios [id=" + id + ", idplantacion=" + idplantacion
				+ ", titulo=" + titulo + ", descripcion=" + descripcion
				+ ", fechaini=" + fechaini + ", fechafin=" + fechafin + "]";
	}
	
	
	
	
	

}
