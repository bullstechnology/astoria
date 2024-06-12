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
@Table(name="comision")
public class Comision implements Serializable{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "idpais", nullable = false)
	private Integer idpais;
	@Column(name = "idtipoflorgrados", nullable = false)
	private Integer idtipoflorgrados;
	@Column(name = "comision", nullable = false)
	private Double comision;
	@Column(name = "fecha", nullable = false)
	private Date fecha;
	@Column(name = "username", nullable = false)
	private String username;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdpais() {
		return idpais;
	}
	public void setIdpais(Integer idpais) {
		this.idpais = idpais;
	}
	public Integer getIdtipoflorgrados() {
		return idtipoflorgrados;
	}
	public void setIdtipoflorgrados(Integer idtipoflorgrados) {
		this.idtipoflorgrados = idtipoflorgrados;
	}
	public Double getComision() {
		return comision;
	}
	public void setComision(Double comision) {
		this.comision = comision;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "Comision [id=" + id + ", idpais=" + idpais
				+ ", idtipoflorgrados=" + idtipoflorgrados + ", comision="
				+ comision + ", fecha=" + fecha + ", username=" + username
				+ "]";
	}
    
	
}