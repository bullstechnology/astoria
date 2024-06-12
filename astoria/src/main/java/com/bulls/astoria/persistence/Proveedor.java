package com.bulls.astoria.persistence;

import java.io.Serializable;
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
@Table(name="proveedor")
public class Proveedor implements Serializable{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idproveedor;
	@Column(name = "nombre", nullable = false)
	private String nombre;
	@Column(name = "cupo", nullable = false)
	private Double cupo;
	@Column(name = "saldo", nullable = false)
	private Double saldo;
	public Integer getIdproveedor() {
		return idproveedor;
	}
	public void setIdproveedor(Integer idproveedor) {
		this.idproveedor = idproveedor;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Double getCupo() {
		return cupo;
	}
	public void setCupo(Double cupo) {
		this.cupo = cupo;
	}
	public Double getSaldo() {
		return saldo;
	}
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	@Override
	public String toString() {
		return "Proveedor [idprovedor=" + idproveedor + ", nombre=" + nombre + ", cupo=" + cupo + ", saldo=" + saldo
				+ "]";
	}

	
	
	
	
	
}