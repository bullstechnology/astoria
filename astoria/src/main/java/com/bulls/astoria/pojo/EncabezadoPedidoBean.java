package com.bulls.astoria.pojo;

import java.io.Serializable;
import java.util.Date;

public class EncabezadoPedidoBean implements Serializable{
	
	private Integer idpedido;
	private Integer idCliente;
	private Integer idPais;
	private Integer idCiudad;
	private Date fechapedido;
	private Date fechallegada;
	private Date fechallegadafinal;
	private Date fechadespacho;
	private boolean programado;
	private String nombrecliente;
	private String nombreciudad;
	private String nombrepais;
	private Character estado;
	private boolean lunes;
	private boolean martes;
	private boolean miercoles;
	private boolean jueves;
	private boolean viernes;
	private boolean sabado;
	private boolean domingo;
	
	
	public boolean esEditable(){
		if(this.estado.equals(new Character('R')) || this.estado.equals(new Character('S'))){
			return true;
		}else {
			return false;
		}
	}
	
	public boolean esConciliable(){
		if(this.estado.equals(new Character('R'))){
			return true;
		}else {
			return false;
		}
	}
	
	public boolean esEditableConciliar(){
		if(this.estado.equals(new Character('C'))){
			return true;
		}else {
			return false;
		}
	}
	
	public boolean esFacturable(){
		if(this.estado.equals(new Character('D')) || this.estado.equals(new Character('F'))){
			return true;
		}else {
			return false;
		}
	}
	public boolean esFacturableEditar(){
		if(this.estado.equals(new Character('F'))){
			return true;
		}else {
			return false;
		}
	}
	
	public boolean esCerrable(){
		if(this.estado.equals(new Character('F'))){
			return true;
		}else {
			return false;
		}
	}
	
	public boolean esEntregado(){
		if(this.estado.equals(new Character('E'))){
			return true;
		}else {
			return false;
		}
	}
	
	public boolean esCancelable(){
		if(!this.estado.equals('E') && !this.estado.equals('X')){
			return true;
		}else {
			return false;
		}
	}

	
	public Integer getIdpedido() {
		return idpedido;
	}
	public void setIdpedido(Integer idpedido) {
		this.idpedido = idpedido;
	}
	public Integer getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	public Integer getIdPais() {
		return idPais;
	}
	public void setIdPais(Integer idPais) {
		this.idPais = idPais;
	}
	public Integer getIdCiudad() {
		return idCiudad;
	}
	public void setIdCiudad(Integer idCiudad) {
		this.idCiudad = idCiudad;
	}
	public Date getFechapedido() {
		return fechapedido;
	}
	public void setFechapedido(Date fechapedido) {
		this.fechapedido = fechapedido;
	}
	public Date getFechallegada() {
		return fechallegada;
	}
	public void setFechallegada(Date fechallegada) {
		this.fechallegada = fechallegada;
	}
	public Date getFechallegadafinal() {
		return fechallegadafinal;
	}
	public void setFechallegadafinal(Date fechallegadafinal) {
		this.fechallegadafinal = fechallegadafinal;
	}
	public boolean isProgramado() {
		return programado;
	}
	public void setProgramado(boolean programado) {
		this.programado = programado;
	}
	public String getNombrecliente() {
		return nombrecliente;
	}
	public void setNombrecliente(String nombrecliente) {
		this.nombrecliente = nombrecliente;
	}
	public String getNombreciudad() {
		return nombreciudad;
	}
	public void setNombreciudad(String nombreciudad) {
		this.nombreciudad = nombreciudad;
	}
	public String getNombrepais() {
		return nombrepais;
	}
	public void setNombrepais(String nombrepais) {
		this.nombrepais = nombrepais;
	}
	public Character getEstado() {
		return estado;
	}
	public void setEstado(Character estado) {
		this.estado = estado;
	}
	public Date getFechadespacho() {
		return fechadespacho;
	}
	public void setFechadespacho(Date fechadespacho) {
		this.fechadespacho = fechadespacho;
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
	
	
	

}
