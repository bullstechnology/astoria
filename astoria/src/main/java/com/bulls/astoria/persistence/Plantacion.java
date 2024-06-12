package com.bulls.astoria.persistence;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="plantacion")
public class Plantacion implements Serializable {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "codigo", nullable = false)
	private String codigo;
	@Column(name = "nit", nullable = false)
	private String nit;
	@Column(name = "nombre", nullable = false)
	private String nombre;
	@Column(name = "pais", nullable = false)
	private Integer pais;
	@Column(name = "ciudad", nullable = false)
	private Integer ciudad;
	@Column(name = "direccion", nullable = false)
	private String direccion;
	@Column(name = "tel1", nullable = true)
	private String tel1;
	@Column(name = "tel2", nullable = true)
	private String tel2;
	@Column(name = "fax", nullable = true)
	private String fax;
	@Column(name = "cel1", nullable = true)
	private String cel1;
	@Column(name = "cel2", nullable = true)
	private String cel2;
	@Column(name = "web", nullable = true)
	private String web;
	@Column(name = "correo1", nullable = true)
	private String correo1;
	@Column(name = "correo2", nullable = true)
	private String correo2;
	@Column(name = "contactoven", nullable = true)
	private String contactoven;
	@Column(name = "contactoprop", nullable = true)
	private String contactoprop;
	@Column(name = "contactoinvoice", nullable = true)
	private String contactoinvoice;
	@Column(name = "skypeven", nullable = true)
	private String skypeven;
	@Column(name = "skypeprop", nullable = true)
	private String skypeprop;
	@Column(name = "skypealterno", nullable = true)
	private String skypealterno;
	@Column(name = "observaciones", nullable = true)
	private String observaciones;
	@Column(name = "cupo", nullable = false)
	private Double cupo;
	@Column(name = "desccupo", nullable = false)
	private String desccupo;
	@Column(name = "saldoinicial", nullable = false)
	private Double saldoinicial;
	@Column(name = "saldofinal", nullable = false)
	private double saldofinal;
	@Column(name = "descsaldofinal", nullable = true)
	private String descsaldofinal;
	
	@Column(name = "cantidadxfull", nullable = true)
	private Integer cantidadxfull;
	
	@Column(name = "idproveedor", nullable = true)
	private Integer idproveedor;
	
	
	 @OneToMany(cascade=CascadeType.MERGE)
	 @JoinTable(name="plantacionproducto", joinColumns={@JoinColumn(name="idplantacion", referencedColumnName="id")}
	 , inverseJoinColumns={@JoinColumn(name="idproducto", referencedColumnName="id")})
	 private Set<Producto> productos;
	 
	 @OneToMany(cascade=CascadeType.MERGE)
	 @JoinTable(name="plantacionpacking", joinColumns={@JoinColumn(name="idplantacion", referencedColumnName="id")}
	 , inverseJoinColumns={@JoinColumn(name="idgrado", referencedColumnName="id")})
	 private Set<Dominio> grados;

	
	
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
	public String getNit() {
		return nit;
	}
	public void setNit(String nit) {
		this.nit = nit;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getPais() {
		return pais;
	}
	public void setPais(Integer pais) {
		this.pais = pais;
	}
	public Integer getCiudad() {
		return ciudad;
	}
	public void setCiudad(Integer ciudad) {
		this.ciudad = ciudad;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTel1() {
		return tel1;
	}
	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}
	public String getTel2() {
		return tel2;
	}
	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getCel1() {
		return cel1;
	}
	public void setCel1(String cel1) {
		this.cel1 = cel1;
	}
	public String getCel2() {
		return cel2;
	}
	public void setCel2(String cel2) {
		this.cel2 = cel2;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
	public String getCorreo1() {
		return correo1;
	}
	public void setCorreo1(String correo1) {
		this.correo1 = correo1;
	}
	public String getCorreo2() {
		return correo2;
	}
	public void setCorreo2(String correo2) {
		this.correo2 = correo2;
	}
	public String getContactoven() {
		return contactoven;
	}
	public void setContactoven(String contactoven) {
		this.contactoven = contactoven;
	}
	public String getContactoprop() {
		return contactoprop;
	}
	public void setContactoprop(String contactoprop) {
		this.contactoprop = contactoprop;
	}
	public String getContactoinvoice() {
		return contactoinvoice;
	}
	public void setContactoinvoice(String contactoinvoice) {
		this.contactoinvoice = contactoinvoice;
	}
	public String getSkypeven() {
		return skypeven;
	}
	public void setSkypeven(String skypeven) {
		this.skypeven = skypeven;
	}
	public String getSkypeprop() {
		return skypeprop;
	}
	public void setSkypeprop(String skypeprop) {
		this.skypeprop = skypeprop;
	}
	public String getSkypealterno() {
		return skypealterno;
	}
	public void setSkypealterno(String skypealterno) {
		this.skypealterno = skypealterno;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public Double getCupo() {
		return cupo;
	}
	public void setCupo(Double cupo) {
		this.cupo = cupo;
	}
	public String getDesccupo() {
		return desccupo;
	}
	public void setDesccupo(String desccupo) {
		this.desccupo = desccupo;
	}
	public Double getSaldoinicial() {
		return saldoinicial;
	}
	public void setSaldoinicial(Double saldoinicial) {
		this.saldoinicial = saldoinicial;
	}
	public double getSaldofinal() {
		return saldofinal;
	}
	public void setSaldofinal(double saldofinal) {
		this.saldofinal = saldofinal;
	}
	public String getDescsaldofinal() {
		return descsaldofinal;
	}
	public void setDescsaldofinal(String descsaldofinal) {
		this.descsaldofinal = descsaldofinal;
	}
	public Set<Producto> getProductos() {
		return productos;
	}
	public void setProductos(Set<Producto> productos) {
		this.productos = productos;
	}
	public Set<Dominio> getGrados() {
		return grados;
	}
	public void setGrados(Set<Dominio> grados) {
		this.grados = grados;
	}
	public Integer getCantidadxfull() {
		return cantidadxfull;
	}
	public void setCantidadxfull(Integer cantidadxfull) {
		this.cantidadxfull = cantidadxfull;
	}
	
	
	public Integer getIdproveedor() {
		return idproveedor;
	}
	public void setIdproveedor(Integer idproveedor) {
		this.idproveedor = idproveedor;
	}
	@Override
	public String toString() {
		return "Plantacion [id=" + id + ", codigo=" + codigo + ", nit=" + nit
				+ ", nombre=" + nombre + ", pais=" + pais + ", ciudad="
				+ ciudad + ", direccion=" + direccion + ", tel1=" + tel1
				+ ", tel2=" + tel2 + ", fax=" + fax + ", cel1=" + cel1
				+ ", cel2=" + cel2 + ", web=" + web + ", correo1=" + correo1
				+ ", correo2=" + correo2 + ", contactoven=" + contactoven
				+ ", contactoprop=" + contactoprop + ", contactoinvoice="
				+ contactoinvoice + ", skypeven=" + skypeven + ", skypeprop="
				+ skypeprop + ", skypealterno=" + skypealterno
				+ ", observaciones=" + observaciones + ", cupo=" + cupo
				+ ", desccupo=" + desccupo + ", saldoinicial=" + saldoinicial
				+ ", saldofinal=" + saldofinal + ", descsaldofinal="
				+ descsaldofinal + ", cantidadxfull=" + cantidadxfull
				 + "]";
	}
	
	
	
	

}
