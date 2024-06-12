package com.bulls.astoria.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;


@Entity
@Table(name="clientehandler")
@IdClass(ClienteHandlerId.class)
public class ClienteHandler implements Serializable{
	
		@Column(name = "idcliente", nullable = false)
		@Id
		private Integer idcliente;
		@Column(name = "idhandler", nullable = false)
		@Id
		private Integer idhandler;
		public Integer getIdcliente() {
			return idcliente;
		}
		public void setIdcliente(Integer idcliente) {
			this.idcliente = idcliente;
		}
		public Integer getIdhandler() {
			return idhandler;
		}
		public void setIdhandler(Integer idhandler) {
			this.idhandler = idhandler;
		}
		@Override
		public String toString() {
			return "ClienteHandler [idcliente=" + idcliente + ", idhandler="
					+ idhandler + "]";
		}
		
		
		
}
