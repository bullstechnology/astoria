package com.bulls.astoria.persistence;

import java.io.Serializable;
import java.util.Date;
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
@Table(name="pedidofacturado")
public class PedidoFacturado implements Serializable{

	@Id
	private Integer id;
	@Column(name = "idcliente", nullable = false)
	private Integer idcliente;
	@Column(name = "idpais", nullable = false)
	private Integer idpais;
	@Column(name = "idciudad", nullable = false)
	private Integer idciudad;
	@Column(name = "idpaisdestino", nullable = false)
	private Integer idpaisdestino;
	@Column(name = "idciudaddestino", nullable = false)
	private Integer idciudaddestino;
	@Column(name = "fechapedido", nullable = false)
	private Date fechapedido;
	@Column(name = "fechadespacho", nullable =true)
	private Date fechadespacho;
	@Column(name = "fechallegada", nullable = true)
	private Date fechallegada;
	@Column(name = "fechallegadafinal", nullable = true)
	private Date fechallegadafinal;
	@Column(name = "comprador", nullable = true)
	private Integer comprador;
	@Column(name = "vendedor", nullable = true)
	private Integer vendedor;
	@Column(name = "diligenciador", nullable = true)
	private String diligenciador;
	@Column(name = "observaciones", nullable = true)
	private String observaciones;
	@Column(name = "estado", nullable = false)
	private Character estado;
	
	@Column(name = "programado", nullable = true)
	private boolean programado;
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
	
	
	@Column(name = "airline", nullable = true)
	private Integer airline;
	@Column(name = "forwarder", nullable = true)
	private Integer forwarder;
	@Column(name = "idhandler", nullable = true)
	private Integer idHandler;
	@Column(name = "idtruck", nullable = true)
	private Integer idTruck;
	@Column(name = "valorconceppre", nullable = true)
	private Double valorconceppre;
	@Column(name = "invoice", nullable = true)
	private String invoice;
	@Column(name = "client", nullable = true)
	private String client;
	@Column(name = "boxesid", nullable = true)
	private String boxesid;
	@Column(name = "awb", nullable = true)
	private String awb;
	@Column(name = "date", nullable = true)
	private Date date;
	
	
	@Column(name = "pesoastoria", nullable = true)
	private Double pesoastoria;
	@Column(name = "pesocliente", nullable = true)
	private Double pesocliente;
	@Column(name = "tarifaastoria", nullable = true)
	private Double tarifaastoria;
	@Column(name = "tarifacliente", nullable = true)
	private Double tarifacliente;
	@Column(name = "concepto", nullable = true)
	private String concepto;
	@Column(name = "concepto1", nullable = true)
	private String concepto1;
	@Column(name = "concepto2", nullable = true)
	private String concepto2;
	@Column(name = "valorconcepto", nullable = true)
	private Double valorconcepto;
	@Column(name = "valorconcepto1", nullable = true)
	private Double valorconcepto1;
	@Column(name = "valorconcepto2", nullable = true)
	private Double valorconcepto2;
	@Column(name = "valorconceptoast", nullable = true)
	private Double valorconceptoast;
	@Column(name = "valorconcepto1ast", nullable = true)
	private Double valorconcepto1ast;
	@Column(name = "valorconcepto2ast", nullable = true)
	private Double valorconcepto2ast;
	
	@Column(name = "conceptotrans1", nullable = true)
	private String conceptotrans1;
	@Column(name = "conceptotrans2", nullable = true)
	private String conceptotrans2;
	@Column(name = "conceptotrans3", nullable = true)
	private String conceptotrans3;
	@Column(name = "conceptotrans4", nullable = true)
	private String conceptotrans4;
	
	@Column(name = "valconceptotrans1", nullable = true)
	private Double valconceptotrans1;
	@Column(name = "valconceptotrans2", nullable = true)
	private Double valconceptotrans2;
	@Column(name = "valconceptotrans3", nullable = true)
	private Double valconceptotrans3;
	@Column(name = "valconceptotrans4", nullable = true)
	private Double valconceptotrans4;
	
	@Column(name = "valconceptotransast1", nullable = true)
	private Double valconceptotransast1;
	@Column(name = "valconceptotransast2", nullable = true)
	private Double valconceptotransast2;
	@Column(name = "valconceptotransast3", nullable = true)
	private Double valconceptotransast3;
	@Column(name = "valconceptotransast4", nullable = true)
	private Double valconceptotransast4;
	
	@Column(name = "preculincli", nullable = true)
	private Double preculincli;
	@Column(name = "preculinast", nullable = true)
	private Double preculinast;
	
	@Column(name = "totalpreculincli", nullable = true)
	private Double totalpreculincli;
	@Column(name = "totalpreculinast", nullable = true)
	private Double totalpreculinast;
	
	@Column(name = "piezasadicionales", nullable = true)
	private Integer piezasadicionales;
	
	
	
	
	@Column(name = "tipofactura", nullable = true)
	private Character tipofactura;
	
	@Column(name = "notafinal", nullable = true)
	private String notafinal;

	
	@OneToMany(mappedBy="pedido" , cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private Set<DetallePedidoFacturado> detalles;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdcliente() {
		return idcliente;
	}
	public void setIdcliente(Integer idcliente) {
		this.idcliente = idcliente;
	}
	public Integer getIdpais() {
		return idpais;
	}
	public void setIdpais(Integer idpais) {
		this.idpais = idpais;
	}
	public Integer getIdciudad() {
		return idciudad;
	}
	public void setIdciudad(Integer idciudad) {
		this.idciudad = idciudad;
	}
	public Date getFechapedido() {
		return fechapedido;
	}
	public void setFechapedido(Date fechapedido) {
		this.fechapedido = fechapedido;
	}
	public Date getFechadespacho() {
		return fechadespacho;
	}
	public void setFechadespacho(Date fechadespacho) {
		this.fechadespacho = fechadespacho;
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
	public Integer getComprador() {
		return comprador;
	}
	public void setComprador(Integer comprador) {
		this.comprador = comprador;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public Integer getVendedor() {
		return vendedor;
	}
	public void setVendedor(Integer vendedor) {
		this.vendedor = vendedor;
	}
	public Character getEstado() {
		return estado;
	}
	public void setEstado(Character estado) {
		this.estado = estado;
	}
	public Set<DetallePedidoFacturado> getDetalles() {
		return detalles;
	}
	public void setDetalles(Set<DetallePedidoFacturado> detalles) {
		this.detalles = detalles;
	}
	public boolean isProgramado() {
		return programado;
	}
	public void setProgramado(boolean programado) {
		this.programado = programado;
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
	public Integer getAirline() {
		return airline;
	}
	public void setAirline(Integer airline) {
		this.airline = airline;
	}
	public Integer getForwarder() {
		return forwarder;
	}
	public void setForwarder(Integer forwarder) {
		this.forwarder = forwarder;
	}
	public String getInvoice() {
		return invoice;
	}
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getBoxesid() {
		return boxesid;
	}
	public void setBoxesid(String boxesid) {
		this.boxesid = boxesid;
	}
	public String getAwb() {
		return awb;
	}
	public void setAwb(String awb) {
		this.awb = awb;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getIdpaisdestino() {
		return idpaisdestino;
	}
	public void setIdpaisdestino(Integer idpaisdestino) {
		this.idpaisdestino = idpaisdestino;
	}
	public Integer getIdciudaddestino() {
		return idciudaddestino;
	}
	public void setIdciudaddestino(Integer idciudaddestino) {
		this.idciudaddestino = idciudaddestino;
	}
	public Double getPesoastoria() {
		return pesoastoria;
	}
	public void setPesoastoria(Double pesoastoria) {
		this.pesoastoria = pesoastoria;
	}
	public Double getPesocliente() {
		return pesocliente;
	}
	public void setPesocliente(Double pesocliente) {
		this.pesocliente = pesocliente;
	}
	public Double getTarifaastoria() {
		return tarifaastoria;
	}
	public void setTarifaastoria(Double tarifaastoria) {
		this.tarifaastoria = tarifaastoria;
	}
	public Double getTarifacliente() {
		return tarifacliente;
	}
	public void setTarifacliente(Double tarifacliente) {
		this.tarifacliente = tarifacliente;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getConcepto1() {
		return concepto1;
	}
	public void setConcepto1(String concepto1) {
		this.concepto1 = concepto1;
	}
	public String getConcepto2() {
		return concepto2;
	}
	public void setConcepto2(String concepto2) {
		this.concepto2 = concepto2;
	}
	public Double getValorconcepto() {
		return valorconcepto;
	}
	public void setValorconcepto(Double valorconcepto) {
		this.valorconcepto = valorconcepto;
	}
	public Double getValorconcepto1() {
		return valorconcepto1;
	}
	public void setValorconcepto1(Double valorconcepto1) {
		this.valorconcepto1 = valorconcepto1;
	}
	public Double getValorconcepto2() {
		return valorconcepto2;
	}
	public void setValorconcepto2(Double valorconcepto2) {
		this.valorconcepto2 = valorconcepto2;
	}
	public Character getTipofactura() {
		return tipofactura;
	}
	public void setTipofactura(Character tipofactura) {
		this.tipofactura = tipofactura;
	}
	public Double getValorconceptoast() {
		return valorconceptoast;
	}
	public void setValorconceptoast(Double valorconceptoast) {
		this.valorconceptoast = valorconceptoast;
	}
	public Double getValorconcepto1ast() {
		return valorconcepto1ast;
	}
	public void setValorconcepto1ast(Double valorconcepto1ast) {
		this.valorconcepto1ast = valorconcepto1ast;
	}
	public Double getValorconcepto2ast() {
		return valorconcepto2ast;
	}
	public void setValorconcepto2ast(Double valorconcepto2ast) {
		this.valorconcepto2ast = valorconcepto2ast;
	}
	public String getConceptotrans1() {
		return conceptotrans1;
	}
	public void setConceptotrans1(String conceptotrans1) {
		this.conceptotrans1 = conceptotrans1;
	}
	public String getConceptotrans2() {
		return conceptotrans2;
	}
	public void setConceptotrans2(String conceptotrans2) {
		this.conceptotrans2 = conceptotrans2;
	}
	public String getConceptotrans3() {
		return conceptotrans3;
	}
	public void setConceptotrans3(String conceptotrans3) {
		this.conceptotrans3 = conceptotrans3;
	}
	public String getConceptotrans4() {
		return conceptotrans4;
	}
	public void setConceptotrans4(String conceptotrans4) {
		this.conceptotrans4 = conceptotrans4;
	}
	public Double getValconceptotrans1() {
		return valconceptotrans1;
	}
	public void setValconceptotrans1(Double valconceptotrans1) {
		this.valconceptotrans1 = valconceptotrans1;
	}
	public Double getValconceptotrans2() {
		return valconceptotrans2;
	}
	public void setValconceptotrans2(Double valconceptotrans2) {
		this.valconceptotrans2 = valconceptotrans2;
	}
	public Double getValconceptotrans3() {
		return valconceptotrans3;
	}
	public void setValconceptotrans3(Double valconceptotrans3) {
		this.valconceptotrans3 = valconceptotrans3;
	}
	public Double getValconceptotrans4() {
		return valconceptotrans4;
	}
	public void setValconceptotrans4(Double valconceptotrans4) {
		this.valconceptotrans4 = valconceptotrans4;
	}
	public Double getValconceptotransast1() {
		return valconceptotransast1;
	}
	public void setValconceptotransast1(Double valconceptotransast1) {
		this.valconceptotransast1 = valconceptotransast1;
	}
	public Double getValconceptotransast2() {
		return valconceptotransast2;
	}
	public void setValconceptotransast2(Double valconceptotransast2) {
		this.valconceptotransast2 = valconceptotransast2;
	}
	public Double getValconceptotransast3() {
		return valconceptotransast3;
	}
	public void setValconceptotransast3(Double valconceptotransast3) {
		this.valconceptotransast3 = valconceptotransast3;
	}
	public Double getValconceptotransast4() {
		return valconceptotransast4;
	}
	public void setValconceptotransast4(Double valconceptotransast4) {
		this.valconceptotransast4 = valconceptotransast4;
	}
	public Integer getPiezasadicionales() {
		return piezasadicionales;
	}
	public void setPiezasadicionales(Integer piezasadicionales) {
		this.piezasadicionales = piezasadicionales;
	}
	public Integer getIdHandler() {
		return idHandler;
	}
	public void setIdHandler(Integer idHandler) {
		this.idHandler = idHandler;
	}
	public Integer getIdTruck() {
		return idTruck;
	}
	public void setIdTruck(Integer idTruck) {
		this.idTruck = idTruck;
	}
	public Double getValorconceppre() {
		return valorconceppre;
	}
	public void setValorconceppre(Double valorconceppre) {
		this.valorconceppre = valorconceppre;
	}
	public String getDiligenciador() {
		return diligenciador;
	}
	public void setDiligenciador(String diligenciador) {
		this.diligenciador = diligenciador;
	}
	
	
	public Double getPreculincli() {
		return preculincli;
	}
	public void setPreculincli(Double preculincli) {
		this.preculincli = preculincli;
	}
	public Double getPreculinast() {
		return preculinast;
	}
	public void setPreculinast(Double preculinast) {
		this.preculinast = preculinast;
	}
	public Double getTotalpreculincli() {
		return totalpreculincli;
	}
	public void setTotalpreculincli(Double totalpreculincli) {
		this.totalpreculincli = totalpreculincli;
	}
	public Double getTotalpreculinast() {
		return totalpreculinast;
	}
	public void setTotalpreculinast(Double totalpreculinast) {
		this.totalpreculinast = totalpreculinast;
	}
	
    
	public String getNotafinal() {
		return notafinal;
	}
	public void setNotafinal(String notafinal) {
		this.notafinal = notafinal;
	}
	@Override
	public String toString() {
		return "PedidoFacturado [id=" + id + ", idcliente=" + idcliente
				+ ", idpais=" + idpais + ", idciudad=" + idciudad
				+ ", idpaisdestino=" + idpaisdestino + ", idciudaddestino="
				+ idciudaddestino + ", fechapedido=" + fechapedido
				+ ", fechadespacho=" + fechadespacho + ", fechallegada="
				+ fechallegada + ", fechallegadafinal=" + fechallegadafinal
				+ ", comprador=" + comprador + ", vendedor=" + vendedor
				+ ", diligenciador=" + diligenciador + ", observaciones="
				+ observaciones + ", estado=" + estado + ", programado="
				+ programado + ", lunes=" + lunes + ", martes=" + martes
				+ ", miercoles=" + miercoles + ", jueves=" + jueves
				+ ", viernes=" + viernes + ", sabado=" + sabado + ", domingo="
				+ domingo + ", airline=" + airline + ", forwarder=" + forwarder
				+ ", idHandler=" + idHandler + ", idTruck=" + idTruck
				+ ", valorconceppre=" + valorconceppre + ", invoice=" + invoice
				+ ", client=" + client + ", boxesid=" + boxesid + ", awb="
				+ awb + ", date=" + date + ", pesoastoria=" + pesoastoria
				+ ", pesocliente=" + pesocliente + ", tarifaastoria="
				+ tarifaastoria + ", tarifacliente=" + tarifacliente
				+ ", concepto=" + concepto + ", concepto1=" + concepto1
				+ ", concepto2=" + concepto2 + ", valorconcepto="
				+ valorconcepto + ", valorconcepto1=" + valorconcepto1
				+ ", valorconcepto2=" + valorconcepto2 + ", valorconceptoast="
				+ valorconceptoast + ", valorconcepto1ast=" + valorconcepto1ast
				+ ", valorconcepto2ast=" + valorconcepto2ast
				+ ", conceptotrans1=" + conceptotrans1 + ", conceptotrans2="
				+ conceptotrans2 + ", conceptotrans3=" + conceptotrans3
				+ ", conceptotrans4=" + conceptotrans4 + ", valconceptotrans1="
				+ valconceptotrans1 + ", valconceptotrans2="
				+ valconceptotrans2 + ", valconceptotrans3="
				+ valconceptotrans3 + ", valconceptotrans4="
				+ valconceptotrans4 + ", valconceptotransast1="
				+ valconceptotransast1 + ", valconceptotransast2="
				+ valconceptotransast2 + ", valconceptotransast3="
				+ valconceptotransast3 + ", valconceptotransast4="
				+ valconceptotransast4 + ", piezasadicionales="
				+ piezasadicionales + ", tipofactura=" + tipofactura
				+ "]";
	}
	
	
	
	
	
	

}
