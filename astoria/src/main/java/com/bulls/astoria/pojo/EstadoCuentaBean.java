package com.bulls.astoria.pojo;

import java.io.Serializable;
import java.util.Date;



public class EstadoCuentaBean implements Serializable{

	private Double debito;
	private Double credito;
	private Double saldo;
	private String concepto;
	private Date fecha;
	private Integer id; 
	private String factura;
	private String awb;
	private String inv;
	private String tipotitular;
	private String nomtitular;
	private String cliente;
	public Double getDebito() {
		return debito;
	}
	public void setDebito(Double debito) {
		this.debito = debito;
	}
	public Double getCredito() {
		return credito;
	}
	public void setCredito(Double credito) {
		this.credito = credito;
	}
	public Double getSaldo() {
		return saldo;
	}
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getTipotitular() {
		return tipotitular;
	}
	public void setTipotitular(String tipotitular) {
		this.tipotitular = tipotitular;
	}
	public String getNomtitular() {
		return nomtitular;
	}
	public void setNomtitular(String nomtitular) {
		this.nomtitular = nomtitular;
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
	
	
	
	
}
