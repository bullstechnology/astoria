package com.bulls.astoria.pojo;

import java.io.Serializable;

public class SaldoBean implements Serializable{

	private Integer idPais;
	private String nombrePais;
	private String tipoTitular;
	private String nombreTitular;
	private Double saldoInicial;
	private Double saldoFinal;
	
	
	
	public SaldoBean(Integer idPais, String nombrePais, String tipoTitular,
			String nombreTitular, Double saldoInicial, Double saldoFinal) {
		super();
		this.idPais = idPais;
		this.nombrePais = nombrePais;
		this.tipoTitular = tipoTitular;
		this.nombreTitular = nombreTitular;
		this.saldoInicial = saldoInicial;
		this.saldoFinal = saldoFinal;
	}
	
	
	public Integer getIdPais() {
		return idPais;
	}
	public void setIdPais(Integer idPais) {
		this.idPais = idPais;
	}
	public String getNombrePais() {
		return nombrePais;
	}
	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
	}
	public String getTipoTitular() {
		return tipoTitular;
	}
	public void setTipoTitular(String tipoTitular) {
		this.tipoTitular = tipoTitular;
	}
	public String getNombreTitular() {
		return nombreTitular;
	}
	public void setNombreTitular(String nombreTitular) {
		this.nombreTitular = nombreTitular;
	}
	public Double getSaldoInicial() {
		return saldoInicial;
	}
	public void setSaldoInicial(Double saldoInicial) {
		this.saldoInicial = saldoInicial;
	}
	public Double getSaldoFinal() {
		return saldoFinal;
	}
	public void setSaldoFinal(Double saldoFinal) {
		this.saldoFinal = saldoFinal;
	}
	
	
}
