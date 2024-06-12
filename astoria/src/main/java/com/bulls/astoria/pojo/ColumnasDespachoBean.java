package com.bulls.astoria.pojo;

import java.io.Serializable;

public class ColumnasDespachoBean implements Serializable{
	
	private String header;
	private String property;
	
	
	
	public ColumnasDespachoBean(String header, String property) {
		this.header = header;
		this.property = property;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}

	
	
}
