package com.bulls.astoria.persistence;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="cotizacion")
public class Cotizacion implements Serializable{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idcotizacion;
	@Column(name = "nombre", nullable = false)
	private String nombre;
	@Column(name = "codigoarea", nullable = true)
	private String codigoarea;
	@Column(name = "telefono", nullable = true)
	private String telefono;
	@Column(name = "celular", nullable = true)
	private String celular;
	@Column(name = "codigociudad", nullable = true)
	private String codigociudad;
	@Column(name = "email", nullable = false)
	private String email;
	@Column(name = "descripcion", nullable = false)
	private String descripcion;
	@Column(name = "estado", nullable = false)
	private String estado;
	@Column(name = "acepta", nullable = false)
	private String acepta;
	
	@Column(name = "fecha", nullable = true)
	private Date fecha;
	
	public Integer getIdcotizacion() {
		return idcotizacion;
	}
	public void setIdcotizacion(Integer idcotizacion) {
		this.idcotizacion = idcotizacion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCodigoarea() {
		return codigoarea;
	}
	public void setCodigoarea(String codigoarea) {
		this.codigoarea = codigoarea;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getCodigociudad() {
		return codigociudad;
	}
	public void setCodigociudad(String codigociudad) {
		this.codigociudad = codigociudad;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getAcepta() {
		return acepta;
	}
	public void setAcepta(String acepta) {
		this.acepta = acepta;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	@Override
	public String toString() {
		return "Cotizacion [idcotizacion=" + idcotizacion + ", nombre=" + nombre + ", codigoarea=" + codigoarea
				+ ", telefono=" + telefono + ", celular=" + celular + ", codigociudad=" + codigociudad + ", email="
				+ email + ", descripcion=" + descripcion + ", estado=" + estado + ", acepta=" + acepta + ", fecha="
				+ fecha + "]";
	}
	
	
	
	 
	
	
	
	
	
}