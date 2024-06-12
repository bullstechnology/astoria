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
@Table(name="nota")
public class Nota implements Serializable{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "idCliente", nullable = false)
	private Integer idCliente;
	@Column(name = "idPlantacion", nullable = false)
	private Integer idPlantacion;
	@Column(name = "idDebitoCredito", nullable = false)
	private Integer idDebitoCredito;
	@Column(name = "idAgencia", nullable = false)
	private Integer idAgencia;
	@Column(name = "idHandler", nullable = false)
	private Integer idHandler;
	@Column(name = "idEstado", nullable = false)
	private Integer idEstado;
	@Column(name = "idConcepto", nullable = false)
	private Integer idConcepto;
	@Column(name = "fecha", nullable = true)
	private Date fecha;
	@Column(name = "username", nullable = true)
	private String username;
	@Column(name = "valor", nullable = true)
	private Double valor;
	@Column(name = "concepto", nullable = true)
	private String concepto;
	@Column(name = "factura", nullable = true)
	private String factura;
	@Column(name = "awb", nullable = true)
	private String awb;
	@Column(name = "inv", nullable = true)
	private String inv;
	@Column(name = "cliente", nullable = true)
	private String cliente;
	@Column(name = "idpedido", nullable = true)
	private Integer idpedido;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	public Integer getIdPlantacion() {
		return idPlantacion;
	}
	public void setIdPlantacion(Integer idPlantacion) {
		this.idPlantacion = idPlantacion;
	}
	public Integer getIdDebitoCredito() {
		return idDebitoCredito;
	}
	public void setIdDebitoCredito(Integer idDebitoCredito) {
		this.idDebitoCredito = idDebitoCredito;
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
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public Integer getIdAgencia() {
		return idAgencia;
	}
	public void setIdAgencia(Integer idAgencia) {
		this.idAgencia = idAgencia;
	}
	public Integer getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}
	public String getFactura() {
		return factura;
	}
	public void setFactura(String factura) {
		this.factura = factura;
	}
	public String getAwb() {
		return awb;
	}
	public void setAwb(String awb) {
		this.awb = awb;
	}
	public Integer getIdConcepto() {
		return idConcepto;
	}
	public void setIdConcepto(Integer idConcepto) {
		this.idConcepto = idConcepto;
	}
	public String getInv() {
		return inv;
	}
	public void setInv(String inv) {
		this.inv = inv;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	
	
	public Integer getIdHandler() {
		return idHandler;
	}
	public void setIdHandler(Integer idHandler) {
		this.idHandler = idHandler;
	}
	
	public Integer getIdpedido() {
		return idpedido;
	}
	public void setIdpedido(Integer idpedido) {
		this.idpedido = idpedido;
	}
	@Override
	public String toString() {
		return "Nota [id=" + id + ", idCliente=" + idCliente + ", idPlantacion=" + idPlantacion + ", idDebitoCredito="
				+ idDebitoCredito + ", idAgencia=" + idAgencia + ", idHandler=" + idHandler + ", idEstado=" + idEstado
				+ ", idConcepto=" + idConcepto + ", fecha=" + fecha + ", username=" + username + ", valor=" + valor
				+ ", concepto=" + concepto + ", factura=" + factura + ", awb=" + awb + ", inv=" + inv + ", cliente="
				+ cliente + "]";
	}
	
	
	 

	
}