package com.bulls.astoria.pojo;

import java.io.Serializable;
import java.util.Date;

public class NotaBean implements Serializable{

	private Integer id;
	private Integer idcliente;
	private Integer idplantacion;
	private Integer idagencia;
	private Integer idhandler;
	private Integer idestado;
	private Integer idconcepto;
	private Double valor;
	private String nombrecliente;
	private String nombreplantacion;
	private String nombreagencia;
	private String nombrehandler;
	private String nombreestado;
	private Integer valortipo;
	private String tipo;
	private Date fecha;
	private String username;
	private String awb;
	private String factura;
	private String descripcion;
	private String titular;
	
	public NotaBean(){
		super();
	}
	
	
	public NotaBean(Integer idcliente, Integer idplantacion, Double valor,
			String nombrecliente, String nombreplantacion, Integer valortipo,
			String tipo, Date fecha, String username) {
		super();
		this.idcliente = idcliente;
		this.idplantacion = idplantacion;
		this.valor = valor;
		this.nombrecliente = nombrecliente;
		this.nombreplantacion = nombreplantacion;
		this.valortipo = valortipo;
		this.tipo = tipo;
		this.fecha = fecha;
		this.username = username;
	}
	public NotaBean(Integer idcliente, Integer idplantacion, Integer idagencia,Integer idhandler,Double valor,
			String nombrecliente, String nombreplantacion,String nombreagencia,String nombrehandler, Integer valortipo,
			String tipo, Date fecha, String username) {
		super();
		this.idcliente = idcliente;
		this.idplantacion = idplantacion;
		this.idagencia = idagencia;
		this.idhandler = idhandler;
		this.valor = valor;
		this.nombrecliente = nombrecliente;
		this.nombreplantacion = nombreplantacion;
		this.nombreagencia = nombreagencia;
		this.nombrehandler = nombrehandler;
		this.valortipo = valortipo;
		this.tipo = tipo;
		this.fecha = fecha;
		this.username = username;
	}
	public Integer getIdcliente() {
		return idcliente;
	}
	public void setIdcliente(Integer idcliente) {
		this.idcliente = idcliente;
	}
	public Integer getIdplantacion() {
		return idplantacion;
	}
	public void setIdplantacion(Integer idplantacion) {
		this.idplantacion = idplantacion;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public String getNombrecliente() {
		return nombrecliente;
	}
	public void setNombrecliente(String nombrecliente) {
		this.nombrecliente = nombrecliente;
	}
	public String getNombreplantacion() {
		return nombreplantacion;
	}
	public void setNombreplantacion(String nombreplantacion) {
		this.nombreplantacion = nombreplantacion;
	}
	public Integer getValortipo() {
		return valortipo;
	}
	public void setValortipo(Integer valortipo) {
		this.valortipo = valortipo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Integer getIdagencia() {
		return idagencia;
	}


	public void setIdagencia(Integer idagencia) {
		this.idagencia = idagencia;
	}


	public Integer getIdestado() {
		return idestado;
	}


	public void setIdestado(Integer idestado) {
		this.idestado = idestado;
	}


	public String getNombreagencia() {
		return nombreagencia;
	}


	public void setNombreagencia(String nombreagencia) {
		this.nombreagencia = nombreagencia;
	}


	public String getNombreestado() {
		return nombreestado;
	}


	public void setNombreestado(String nombreestado) {
		this.nombreestado = nombreestado;
	}


	public String getAwb() {
		return awb;
	}


	public void setAwb(String awb) {
		this.awb = awb;
	}


	public String getFactura() {
		return factura;
	}


	public void setFactura(String factura) {
		this.factura = factura;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getTitular() {
		return titular;
	}


	public void setTitular(String titular) {
		this.titular = titular;
	}


	public Integer getIdconcepto() {
		return idconcepto;
	}


	public void setIdconcepto(Integer idconcepto) {
		this.idconcepto = idconcepto;
	}


	public Integer getIdhandler() {
		return idhandler;
	}


	public void setIdhandler(Integer idhandler) {
		this.idhandler = idhandler;
	}


	public String getNombrehandler() {
		return nombrehandler;
	}


	public void setNombrehandler(String nombrehandler) {
		this.nombrehandler = nombrehandler;
	}
	
	
	
	
}
