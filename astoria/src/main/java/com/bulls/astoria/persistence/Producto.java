package com.bulls.astoria.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="producto")
public class Producto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "codigo", nullable = false)
	private String codigo;
	@Column(name = "idgrado", nullable = true)
	private Integer idgrado;
	@Column(name = "idvariedad", nullable = false)
	private Integer idvariedad;
	@Column(name = "enabled", nullable = true)
	private boolean enabled;
	@Column(name = "recomendado", nullable = true)
	private String recomendado ;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Integer getIdgrado() {
		return idgrado;
	}
	public void setIdgrado(Integer idgrado) {
		this.idgrado = idgrado;
	}
	public Integer getIdvariedad() {
		return idvariedad;
	}
	public void setIdvariedad(Integer idvariedad) {
		this.idvariedad = idvariedad;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getRecomendado() {
		return recomendado;
	}
	public void setRecomendado(String recomendado) {
		this.recomendado = recomendado;
	}
	@Override
	public String toString() {
		return "Producto [id=" + id + ", codigo=" + codigo + ", idgrado="
				+ idgrado + ", idvariedad=" + idvariedad + ", enabled="
				+ enabled + ", recomendado=" + recomendado + "]";
	}
	
	

}
