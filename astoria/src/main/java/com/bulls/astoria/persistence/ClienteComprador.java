package com.bulls.astoria.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="clientecomprador")
@IdClass(ClienteCompradorId.class)
public class ClienteComprador implements Serializable{
	@Column(name = "idcliente", nullable = false)
	@Id
	private Integer idcliente;
	@Column(name = "idcomprador", nullable = false)
	@Id
	private Integer idcomprador;
	public Integer getIdcliente() {
		return idcliente;
	}
	public void setIdcliente(Integer idcliente) {
		this.idcliente = idcliente;
	}
	public Integer getIdcomprador() {
		return idcomprador;
	}
	public void setIdcomprador(Integer idcomprador) {
		this.idcomprador = idcomprador;
	}
	@Override
	public String toString() {
		return "ClienteComprador [idcliente=" + idcliente + ", idcomprador="
				+ idcomprador + "]";
	}
	
	
}
