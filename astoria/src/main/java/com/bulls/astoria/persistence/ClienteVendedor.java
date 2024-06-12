package com.bulls.astoria.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="clientevendedor")
@IdClass(ClienteVendedorId.class)
public class ClienteVendedor implements Serializable{
	@Column(name = "idcliente", nullable = false)
	@Id
	private Integer idcliente;
	@Column(name = "idvendedor", nullable = false)
	@Id
	private Integer idvendedor;
	public Integer getIdcliente() {
		return idcliente;
	}
	public void setIdcliente(Integer idcliente) {
		this.idcliente = idcliente;
	}
	public Integer getIdvendedor() {
		return idvendedor;
	}
	public void setIdvendedor(Integer idvendedor) {
		this.idvendedor = idvendedor;
	}
	@Override
	public String toString() {
		return "ClienteVendedor [idcliente=" + idcliente + ", idvendedor="
				+ idvendedor + "]";
	}
	
	
	
}
