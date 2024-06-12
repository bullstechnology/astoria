package com.bulls.astoria.persistence;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;



@Entity
@Table(name="cliente")
public class Cliente implements Serializable{
	
	
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
	@Column(name = "tel1", nullable = false)
	private String tel1;
	@Column(name = "tel2", nullable = true)
	private String tel2;
	@Column(name = "fax", nullable = true)
	private String fax;
	@Column(name = "cel1", nullable = true)
	private String cel1;
	@Column(name = "cel2", nullable = true)
	private String cel2;
	@Column(name = "email", nullable = true)
	private String email;
	@Column(name = "email2", nullable = true)
	private String email2;
	@Column(name = "web", nullable = true)
	private String web;
	@Column(name = "estado", nullable = true)
	private boolean estado;
	@Column(name = "nomprop", nullable = true)
	private String nomprop;
	@Column(name = "nomventas", nullable = true)
	private String nomventas;
	@Column(name = "nominvoice", nullable = true)
	private String nominvoice;
	@Column(name = "reqfactura", nullable = true)
	private boolean reqfactura;
	@Column(name = "reqfito", nullable = true)
	private boolean reqfito;
	@Column(name = "observaciones", nullable = true)
	private String observaciones;
	@Column(name = "idvendedor", nullable = true)
	private Integer idvendedor;
	@Column(name = "limitecredito", nullable = true)
	private Double limitecredito;
	@Column(name = "saldoinicial", nullable = true)
	private Double saldoinicial;
	@Column(name = "saldofinal", nullable = true)
	private Double saldofinal;
	@Column(name = "notas", nullable = true)
	private String notas;
	
	@Column(name = "skypeven", nullable = true)
	private String skypeven;
	@Column(name = "skypeprop", nullable = true)
	private String skypeprop;
	@Column(name = "skypealt", nullable = true)
	private String skypealt;
	@Column(name = "desccupo", nullable = true)
	private String desccupo;
	@Column(name = "descsaldofinal", nullable = true)
	private String descsaldofinal;
	
	@Column(name = "lunes", nullable = true)
	private boolean lunes;
	@Column(name = "martes", nullable = true)
	private boolean martes;
	@Column(name = "miercoles", nullable = true)
	private boolean miercoles;
	@Column(name = "jueves", nullable = true)
	private boolean jueves;
	@Column(name = "viernes", nullable = true)
	private boolean viernes;
	@Column(name = "sabado", nullable = true)
	private boolean sabado;
	@Column(name = "domingo", nullable = true)
	private boolean domingo;
	
	
	
	
	 @OneToMany(cascade=CascadeType.MERGE)
	 @JoinTable(name="clientehandler", joinColumns={@JoinColumn(name="idcliente", referencedColumnName="id")}
	 , inverseJoinColumns={@JoinColumn(name="idhandler", referencedColumnName="id")})
	 private Set<Handler> handlers;
	 
	 
	 @OneToMany(cascade=CascadeType.MERGE)
	 @JoinTable(name="clientetruck", joinColumns={@JoinColumn(name="idcliente", referencedColumnName="id")}
	 , inverseJoinColumns={@JoinColumn(name="idtruck", referencedColumnName="id")})
	 private Set<Truck> trucks;
	 
	 
	 @OneToMany(cascade=CascadeType.MERGE)
	 @JoinTable(name="clientevendedor", joinColumns={@JoinColumn(name="idcliente", referencedColumnName="id")}
	 , inverseJoinColumns={@JoinColumn(name="idvendedor", referencedColumnName="id")})
	 private Set<Persona> vendedores;
	 
	 @OneToMany(cascade=CascadeType.MERGE)
	 @JoinTable(name="clientecomprador", joinColumns={@JoinColumn(name="idcliente", referencedColumnName="id")}
	 , inverseJoinColumns={@JoinColumn(name="idcomprador", referencedColumnName="id")})
	 private Set<Persona> compradores;
	 
	 
	 
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	public String getNomprop() {
		return nomprop;
	}
	public void setNomprop(String nomprop) {
		this.nomprop = nomprop;
	}
	public String getNomventas() {
		return nomventas;
	}
	public void setNomventas(String nomventas) {
		this.nomventas = nomventas;
	}
	public boolean isReqfactura() {
		return reqfactura;
	}
	public void setReqfactura(boolean reqfactura) {
		this.reqfactura = reqfactura;
	}
	public boolean isReqfito() {
		return reqfito;
	}
	public void setReqfito(boolean reqfito) {
		this.reqfito = reqfito;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public Integer getIdvendedor() {
		return idvendedor;
	}
	public void setIdvendedor(Integer idvendedor) {
		this.idvendedor = idvendedor;
	}
	public Double getLimitecredito() {
		return limitecredito;
	}
	public void setLimitecredito(Double limitecredito) {
		this.limitecredito = limitecredito;
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
	public String getNotas() {
		return notas;
	}
	public void setNotas(String notas) {
		this.notas = notas;
	}
	public Set<Handler> getHandlers() {
		return handlers;
	}
	public void setHandlers(Set<Handler> handlers) {
		this.handlers = handlers;
	}
	public Set<Truck> getTrucks() {
		return trucks;
	}
	public void setTrucks(Set<Truck> trucks) {
		this.trucks = trucks;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	public String getNominvoice() {
		return nominvoice;
	}
	public void setNominvoice(String nominvoice) {
		this.nominvoice = nominvoice;
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
	public String getSkypealt() {
		return skypealt;
	}
	public void setSkypealt(String skypealt) {
		this.skypealt = skypealt;
	}
	public String getDesccupo() {
		return desccupo;
	}
	public void setDesccupo(String desccupo) {
		this.desccupo = desccupo;
	}
	public String getDescsaldofinal() {
		return descsaldofinal;
	}
	public void setDescsaldofinal(String descsaldofinal) {
		this.descsaldofinal = descsaldofinal;
	}
	public Set<Persona> getVendedores() {
		return vendedores;
	}
	public void setVendedores(Set<Persona> vendedores) {
		this.vendedores = vendedores;
	}
	public Set<Persona> getCompradores() {
		return compradores;
	}
	public void setCompradores(Set<Persona> compradores) {
		this.compradores = compradores;
	}
	public boolean isLunes() {
		return lunes;
	}
	public void setLunes(boolean lunes) {
		this.lunes = lunes;
	}
	public boolean isMartes() {
		return martes;
	}
	public void setMartes(boolean martes) {
		this.martes = martes;
	}
	public boolean isMiercoles() {
		return miercoles;
	}
	public void setMiercoles(boolean miercoles) {
		this.miercoles = miercoles;
	}
	public boolean isJueves() {
		return jueves;
	}
	public void setJueves(boolean jueves) {
		this.jueves = jueves;
	}
	public boolean isViernes() {
		return viernes;
	}
	public void setViernes(boolean viernes) {
		this.viernes = viernes;
	}
	public boolean isSabado() {
		return sabado;
	}
	public void setSabado(boolean sabado) {
		this.sabado = sabado;
	}
	public boolean isDomingo() {
		return domingo;
	}
	public void setDomingo(boolean domingo) {
		this.domingo = domingo;
	}
	@Override
	public String toString() {
		return "Cliente [id=" + id + ", codigo=" + codigo + ", tipoident="
				+ tipoident + ", numident=" + numident + ", nombre=" + nombre
				+ ", pais=" + pais + ", ciudad=" + ciudad + ", direccion="
				+ direccion + ", tel1=" + tel1 + ", tel2=" + tel2 + ", fax="
				+ fax + ", cel1=" + cel1 + ", cel2=" + cel2 + ", email="
				+ email + ", email2=" + email2 + ", web=" + web + ", estado="
				+ estado + ", nomprop=" + nomprop + ", nomventas=" + nomventas
				+ ", nominvoice=" + nominvoice + ", reqfactura=" + reqfactura
				+ ", reqfito=" + reqfito + ", observaciones=" + observaciones
				+ ", idvendedor=" + idvendedor + ", limitecredito="
				+ limitecredito + ", saldoinicial=" + saldoinicial
				+ ", saldofinal=" + saldofinal + ", notas=" + notas
				+ ", skypeven=" + skypeven + ", skypeprop=" + skypeprop
				+ ", skypealt=" + skypealt + ", desccupo=" + desccupo
				+ ", descsaldofinal=" + descsaldofinal + ", lunes=" + lunes
				+ ", martes=" + martes + ", miercoles=" + miercoles
				+ ", jueves=" + jueves + ", viernes=" + viernes + ", sabado="
				+ sabado + ", domingo=" + domingo + "]";
	}
	
	
		
	
}
