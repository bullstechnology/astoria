package com.bulls.astoria.pojo;

import java.io.Serializable;

public class CatalogoBean implements Serializable{

	private Integer idTipoFlor;
	private Integer idVariedad;
	private Integer idColor;
	private String nombreFlor;
	private String nombreVariedad;
	private String nombreColor;
	private String url;
	
	
	
	
	
	public CatalogoBean(Integer idTipoFlor, Integer idVariedad,
			Integer idColor, String nombreFlor, String nombreVariedad,
			String nombreColor,String url) {
		super();
		this.idTipoFlor = idTipoFlor;
		this.idVariedad = idVariedad;
		this.idColor = idColor;
		this.nombreFlor = nombreFlor;
		this.nombreVariedad = nombreVariedad;
		this.nombreColor = nombreColor;
		this.url=url;
	}
	public Integer getIdTipoFlor() {
		return idTipoFlor;
	}
	public void setIdTipoFlor(Integer idTipoFlor) {
		this.idTipoFlor = idTipoFlor;
	}
	public Integer getIdVariedad() {
		return idVariedad;
	}
	public void setIdVariedad(Integer idVariedad) {
		this.idVariedad = idVariedad;
	}
	public Integer getIdColor() {
		return idColor;
	}
	public void setIdColor(Integer idColor) {
		this.idColor = idColor;
	}
	public String getNombreFlor() {
		return nombreFlor;
	}
	public void setNombreFlor(String nombreFlor) {
		this.nombreFlor = nombreFlor;
	}
	public String getNombreVariedad() {
		return nombreVariedad;
	}
	public void setNombreVariedad(String nombreVariedad) {
		this.nombreVariedad = nombreVariedad;
	}
	public String getNombreColor() {
		return nombreColor;
	}
	public void setNombreColor(String nombreColor) {
		this.nombreColor = nombreColor;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
