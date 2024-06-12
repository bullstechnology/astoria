package com.bulls.astoria.pojo;

public class CostosAdicionalesBean {
	
	
	private String nombrecosto;
	private Double costo ;
	
	public Double getCosto() {
		return costo;
	}
	public void setCosto(Double costo) {
		this.costo = costo;
	}
	public String getNombrecosto() {
		return nombrecosto;
	}
	public void setNombrecosto(String nombrecosto) {
		this.nombrecosto = nombrecosto;
	}
	public CostosAdicionalesBean(String nombrecosto, Double costo) {
		super();
		this.nombrecosto = nombrecosto;
		this.costo = costo;
	}
	
	
	
	

}
