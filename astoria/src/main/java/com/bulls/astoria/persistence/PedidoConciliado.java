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
@Table(name="pedidoconciliado")
public class PedidoConciliado implements Serializable{

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
	
	@Column(name = "autorizacion", nullable = true)
	private boolean autorizacion;
	@Column(name = "autorizo", nullable = true)
	private String autorizo;
	
	@OneToMany(mappedBy="pedido" , cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private Set<DetallePedidoConciliado> detalles;
	
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
	public Set<DetallePedidoConciliado> getDetalles() {
		return detalles;
	}
	public void setDetalles(Set<DetallePedidoConciliado> detalles) {
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
	public boolean isAutorizacion() {
		return autorizacion;
	}
	public void setAutorizacion(boolean autorizacion) {
		this.autorizacion = autorizacion;
	}
	public String getAutorizo() {
		return autorizo;
	}
	public void setAutorizo(String autorizo) {
		this.autorizo = autorizo;
	}
	public String getDiligenciador() {
		return diligenciador;
	}
	public void setDiligenciador(String diligenciador) {
		this.diligenciador = diligenciador;
	}
	@Override
	public String toString() {
		return "PedidoConciliado [id=" + id + ", idcliente=" + idcliente
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
				+ domingo + ", autorizacion=" + autorizacion + ", autorizo="
				+ autorizo  + "]";
	}
	
	
	

}
