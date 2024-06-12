package com.bulls.astoria.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;


@Entity
@Table(name="clientetruck")
@IdClass(ClienteTruckId.class)
public class ClienteTruck implements Serializable {
	@Column(name = "idcliente", nullable = false)
	@Id
	private Integer idcliente;
	@Column(name = "idtruck", nullable = false)
	@Id
	private Integer idtruck;
	public Integer getIdcliente() {
		return idcliente;
	}
	public void setIdcliente(Integer idcliente) {
		this.idcliente = idcliente;
	}
	public Integer getIdtruck() {
		return idtruck;
	}
	public void setIdtruck(Integer idtruck) {
		this.idtruck = idtruck;
	}
	@Override
	public String toString() {
		return "ClienteTruck [idcliente=" + idcliente + ", idtruck=" + idtruck
				+ "]";
	}
	
	
	
}
