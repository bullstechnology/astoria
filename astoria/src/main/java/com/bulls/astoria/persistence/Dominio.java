package com.bulls.astoria.persistence;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
 


@Entity
@Table(name="dominio")
public class Dominio implements Serializable{

	
	//private TipoDominio tipodominio;
	
	@ManyToOne
    @JoinColumn(name="tipodominio")
    private TipoDominio tipodominio;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id ;
	
	
	
	@Column(name = "dominiopadre", nullable = true)
	private Integer dominiopadre;
	
	@Column(name = "idcolor", nullable = true)
	private Integer idcolor;
	
	@Column(name = "nomcorto", nullable = false)
	private String nomcorto;
	
	@Column(name = "nomlargo", nullable = false)
	private String nomlargo;
	
	
	@Column(name = "descripcion", nullable = true)
	private String descripcion;
	
	@Column(name = "url", nullable = true)
	private String url;
	
	@Column(name = "codigo", nullable = true)
	private String codigo;
	
	@Column(name = "pesofullbox", nullable = true)
	private Double pesofullbox;
	
	@Column(name = "valor1", nullable = true)
	private Double valor1;

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	// @OneToOne(cascade = CascadeType.ALL)
	// @JoinColumn(name = "tipodominio")
	public TipoDominio getTipodominio() {
		return tipodominio;
	}
	public void setTipodominio(TipoDominio tipoDominio) {
		this.tipodominio = tipoDominio;
	}
	public Integer getDominiopadre() {
		return dominiopadre;
	}
	public void setDominiopadre(Integer dominiopadre) {
		this.dominiopadre = dominiopadre;
	}
	public String getNomcorto() {
		return nomcorto;
	}
	public void setNomcorto(String nomcorto) {
		this.nomcorto = nomcorto;
	}
	public String getNomlargo() {
		return nomlargo;
	}
	public void setNomlargo(String nomlargo) {
		this.nomlargo = nomlargo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getIdcolor() {
		return idcolor;
	}
	public void setIdcolor(Integer idcolor) {
		this.idcolor = idcolor;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Double getPesofullbox() {
		return pesofullbox;
	}
	public void setPesofullbox(Double pesofullbox) {
		this.pesofullbox = pesofullbox;
	}
	public Double getValor1() {
		return valor1;
	}
	public void setValor1(Double valor1) {
		this.valor1 = valor1;
	}
	@Override
	public String toString() {
		return "Dominio [tipodominio=" + tipodominio + ", id=" + id
				+ ", dominiopadre=" + dominiopadre + ", idcolor=" + idcolor
				+ ", nomcorto=" + nomcorto + ", nomlargo=" + nomlargo
				+ ", descripcion=" + descripcion + ", url=" + url + ", codigo="
				+ codigo + ", pesofullbox=" + pesofullbox + ", valor1="
				+ valor1 + "]";
	}
	
	
}
