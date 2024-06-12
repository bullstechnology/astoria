package com.bulls.astoria.persistence;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="agenciacarga")
public class AgenciaCarga implements Serializable {
		
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "codigo", nullable = false)
	private String codigo;
	@Column(name = "tipoident", nullable = false)
	private Integer tipoident;
	@Column(name = "numident", nullable = false)
	private String numident;
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
	@Column(name = "contacto1", nullable = true)
	private String contacto1;
	@Column(name = "contacto2", nullable = true)
	private String contacto2;
	@Column(name = "skype1", nullable = true)
	private String skype1;
	@Column(name = "skype2", nullable = true)
	private String skype2;
	@Column(name = "cupo", nullable = true)
	private Double cupo;
	@Column(name = "desccupo", nullable = true)
	private String desccupo;
	@Column(name = "saldoinicial", nullable = true)
	private Double saldoinicial;
	@Column(name = "saldofinal", nullable = true)
	private Double saldofinal;
	@Column(name = "descsaldofinal", nullable = true)
	private String descsaldofinal;
	@Column(name = "observaciones", nullable = true)
    private String observaciones;
	@Column(name = "enabled", nullable = false)
    private boolean enabled;
	@Column(name = "infobancaria", nullable = true)
	private String infobancaria;
	
	
	@OneToMany(mappedBy="agencia" , cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private Set<Destino> Destino;
	
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
	public Integer getTipoident() {
		return tipoident;
	}
	public void setTipoident(Integer tipoident) {
		this.tipoident = tipoident;
	}
	public String getNumident() {
		return numident;
	}
	public void setNumident(String numident) {
		this.numident = numident;
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
	public String getContacto1() {
		return contacto1;
	}
	public void setContacto1(String contacto1) {
		this.contacto1 = contacto1;
	}
	public String getContacto2() {
		return contacto2;
	}
	public void setContacto2(String contacto2) {
		this.contacto2 = contacto2;
	}
	public String getSkype1() {
		return skype1;
	}
	public void setSkype1(String skype1) {
		this.skype1 = skype1;
	}
	public String getSkype2() {
		return skype2;
	}
	public void setSkype2(String skype2) {
		this.skype2 = skype2;
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
	public Double getSaldofinal() {
		return saldofinal;
	}
	public void setSaldofinal(Double saldofinal) {
		this.saldofinal = saldofinal;
	}
	public String getDescsaldofinal() {
		return descsaldofinal;
	}
	public void setDescsaldofinal(String descsaldofinal) {
		this.descsaldofinal = descsaldofinal;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Set<Destino> getDestino() {
		return Destino;
	}
	public void setDestino(Set<Destino> destino) {
		Destino = destino;
	}
	public String getInfobancaria() {
		return infobancaria;
	}
	public void setInfobancaria(String infobancaria) {
		this.infobancaria = infobancaria;
	}
	@Override
	public String toString() {
		return "AgenciaCarga [id=" + id + ", codigo=" + codigo + ", tipoident="
				+ tipoident + ", numident=" + numident + ", nombre=" + nombre
				+ ", pais=" + pais + ", ciudad=" + ciudad + ", direccion="
				+ direccion + ", tel1=" + tel1 + ", tel2=" + tel2 + ", fax="
				+ fax + ", cel1=" + cel1 + ", cel2=" + cel2 + ", web=" + web
				+ ", correo1=" + correo1 + ", correo2=" + correo2
				+ ", contacto1=" + contacto1 + ", contacto2=" + contacto2
				+ ", skype1=" + skype1 + ", skype2=" + skype2 + ", cupo="
				+ cupo + ", desccupo=" + desccupo + ", saldoinicial="
				+ saldoinicial + ", saldofinal=" + saldofinal
				+ ", descsaldofinal=" + descsaldofinal + ", observaciones="
				+ observaciones + ", enabled=" + enabled + ", infobancaria="
				+ infobancaria +"]";
	}
	
	
	
	
}
