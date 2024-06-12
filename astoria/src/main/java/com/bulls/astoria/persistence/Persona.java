package com.bulls.astoria.persistence;


import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
 





import org.hibernate.annotations.Type;


@Entity
@Table(name="personas")

public class Persona implements Serializable{
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	int id ;
	 
	@Column(name = "nombre", nullable = false) 
	String nombre;
	
	@Column(name = "segnombre", nullable = true)
	private String segnombre ;
	
	@Column(name = "apellido", nullable = false)
	private String apellido;
	
	@Column(name = "segapellido", nullable = true)
	private String segapellido;
	
	@Column(name = "tipodoc", nullable = false)
	private Integer tipodoc;
	
	@Column(name = "numdoc", nullable = false)
	private String numdoc;
	
	@Column(name = "tel1", nullable = true)
	private String tel1;
	
	@Column(name = "tel2", nullable = true)
	private String tel2;
	
	@Column(name = "cel1", nullable = true)
	private String cel1;
	
	@Column(name = "cel2", nullable = true)
	private String cel2;
	
	@Column(name = "messenger", nullable = true)
	private String messenger;
	
	@Column(name = "skype", nullable = true)
	private String skype;
	
	@Column(name = "email", nullable = true)
	private String email;
	
	@Column(name = "direccion", nullable = true)
	private String direccion;
	
	@Column(name = "pais", nullable = true)
	private Integer pais;
	
	@Column(name = "ciudad", nullable = true)
	private Integer ciudad;
	
	@Column(name = "tipopersona", nullable = false)
	private Integer tipopersona;
	
	@Column(name = "username", nullable = false)
	private String username;
	
	@Column(name = "empleado", nullable = true)
	private boolean empleado;
	
	@Column(name = "cliente", nullable = true)
	private boolean cliente;
	
	@Column(name = "plantacion", nullable = true)
	private boolean plantacion;
	
	@Column(name = "agencia", nullable = true)
	private boolean agencia;
	
	@Column(name = "handler", nullable = true)
	private boolean handler;
	
	@Column(name = "cargo", nullable = true)
	private Integer cargo;
	
	@Column(name = "idcliente", nullable = true)
	private Integer idCliente;
	
	@Column(name = "idplantacion", nullable = true)
	private Integer idPlantacion;
	
	@Column(name = "idagencia", nullable = true)
	private Integer idAgencia;
	
	@Column(name = "idhandler", nullable = true)
	private Integer idHandler;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getSegnombre() {
		return segnombre;
	}
	public void setSegnombre(String segnombre) {
		this.segnombre = segnombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getSegapellido() {
		return segapellido;
	}
	public void setSegapellido(String segapellido) {
		this.segapellido = segapellido;
	}
	public Integer getTipodoc() {
		return tipodoc;
	}
	public void setTipodoc(Integer tipodoc) {
		this.tipodoc = tipodoc;
	}
	public String getNumdoc() {
		return numdoc;
	}
	public void setNumdoc(String numdoc) {
		this.numdoc = numdoc;
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
	public String getMessenger() {
		return messenger;
	}
	public void setMessenger(String messenger) {
		this.messenger = messenger;
	}
	public String getSkype() {
		return skype;
	}
	public void setSkype(String skype) {
		this.skype = skype;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
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
	public Integer getTipopersona() {
		return tipopersona;
	}
	public void setTipopersona(Integer tipopersona) {
		this.tipopersona = tipopersona;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Boolean getEmpleado() {
		return empleado;
	}
	public void setEmpleado(Boolean empleado) {
		this.empleado = empleado;
	}
	public Integer getCargo() {
		return cargo;
	}
	public void setCargo(Integer cargo) {
		this.cargo = cargo;
	}
	
	
	
	public boolean isCliente() {
		return cliente;
	}
	public void setCliente(boolean cliente) {
		this.cliente = cliente;
	}
	public boolean isPlantacion() {
		return plantacion;
	}
	public void setPlantacion(boolean plantacion) {
		this.plantacion = plantacion;
	}
	public boolean isAgencia() {
		return agencia;
	}
	public void setAgencia(boolean agencia) {
		this.agencia = agencia;
	}
	public Integer getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	public Integer getIdPlantacion() {
		return idPlantacion;
	}
	public void setIdPlantacion(Integer idPlantacion) {
		this.idPlantacion = idPlantacion;
	}
	public Integer getIdAgencia() {
		return idAgencia;
	}
	public void setIdAgencia(Integer idAgencia) {
		this.idAgencia = idAgencia;
	}
	public void setIdHandler(Integer idHandler) {
		this.idHandler = idHandler;
	}
	public Integer getIdHandler() {
		return this.idHandler;
	}
	public void setEmpleado(boolean empleado) {
		this.empleado = empleado;
	}
	
	
	public boolean isHandler() {
		return handler;
	}
	public void setHandler(boolean handler) {
		this.handler = handler;
	}
	public Persona (Integer id ,String nombre ,String segnombre,String apellido,String segapellido){
		this.id=id;
		this.nombre=nombre;
		this.segnombre=segnombre;
		this.apellido=apellido;
		this.segapellido=segapellido;
	}
	
	public Persona(){
		
	}
	@Override
	public String toString() {
		return "Persona [id=" + id + ", nombre=" + nombre + ", segnombre=" + segnombre + ", apellido=" + apellido
				+ ", segapellido=" + segapellido + ", tipodoc=" + tipodoc + ", numdoc=" + numdoc + ", tel1=" + tel1
				+ ", tel2=" + tel2 + ", cel1=" + cel1 + ", cel2=" + cel2 + ", messenger=" + messenger + ", skype="
				+ skype + ", email=" + email + ", direccion=" + direccion + ", pais=" + pais + ", ciudad=" + ciudad
				+ ", tipopersona=" + tipopersona + ", username=" + username + ", empleado=" + empleado + ", cliente="
				+ cliente + ", plantacion=" + plantacion + ", agencia=" + agencia + ", handler=" + handler + ", cargo="
				+ cargo + ", idCliente=" + idCliente + ", idPlantacion=" + idPlantacion + ", idAgencia=" + idAgencia
				+ ", idHandler=" + idHandler + "]";
	}
	
	
	

}
