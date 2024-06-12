package com.bulls.astoria.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="destino")
public class Destino implements Serializable{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "idorigen", nullable = false)
	private Integer idorigen;
	@Column(name = "iddestino", nullable = false)
	private Integer iddestino;
	@Column(name = "idciudadorigen", nullable = false)
	private Integer idciudadorigen;
	@Column(name = "idciudaddestino", nullable = false)
	private Integer idciudaddestino;
	
	@Column(name = "tarifaguia", nullable = false)
	private Double tarifaguia;
	@Column(name = "tarifacliente", nullable = false)
	private Double tarifacliente;
	@Column(name = "tarifaastoria", nullable = false)
	private Double tarifaastoria;
	@Column(name = "rangoinicial", nullable = false)
	private Integer rangoinicial;
	@Column(name = "rangofinal", nullable = false)
	private Integer rangofinal;
	
	@Column(name = "tarifacliente2", nullable = false)
	private Double tarifacliente2;
	@Column(name = "tarifaastoria2", nullable = false)
	private Double tarifaastoria2;
	@Column(name = "rangoinicial2", nullable = false)
	private Integer rangoinicial2;
	@Column(name = "rangofinal2", nullable = false)
	private Integer rangofinal2;
	
	
	@Column(name = "tarifacliente3", nullable = false)
	private Double tarifacliente3;
	@Column(name = "tarifaastoria3", nullable = false)
	private Double tarifaastoria3;
	@Column(name = "rangoinicial3", nullable = false)
	private Integer rangoinicial3;
	@Column(name = "rangofinal3", nullable = false)
	private Integer rangofinal3;
	
	
	@Column(name = "idruta", nullable = false)
	private Integer idruta;
	@Column(name = "idaerolinea", nullable = false)
	private Integer idaerolinea;
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
	
	@Column(name = "concepto1", nullable = true)
	private String concepto1;
	@Column(name = "valor1", nullable = true)
	private Double valor1;
	@Column(name = "valorast1", nullable = true)
	private Double valorast1;
	@Column(name = "concepto2", nullable = true)
	private String concepto2;
	@Column(name = "valor2", nullable = true)
	private Double valor2;
	@Column(name = "valorast2", nullable = true)
	private Double valorast2;
	@Column(name = "concepto3", nullable = true)
	private String concepto3;
	@Column(name = "valor3", nullable = true)
	private Double valor3;
	@Column(name = "valorast3", nullable = true)
	private Double valorast3;
	@Column(name = "concepto4", nullable = true)
	private String concepto4;
	@Column(name = "valor4", nullable = true)
	private Double valor4;
	@Column(name = "valorast4", nullable = true)
	private Double valorast4;
	
	@Column(name = "observaciones", nullable = true)
	private String observaciones;
	

	
	
	@ManyToOne
    @JoinColumn(name="idagencia", nullable=false)
    private AgenciaCarga agencia;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdorigen() {
		return idorigen;
	}
	public void setIdorigen(Integer idorigen) {
		this.idorigen = idorigen;
	}
	public Integer getIddestino() {
		return iddestino;
	}
	public void setIddestino(Integer iddestino) {
		this.iddestino = iddestino;
	}
	public Double getTarifacliente() {
		return tarifacliente;
	}
	public void setTarifacliente(Double tarifacliente) {
		this.tarifacliente = tarifacliente;
	}
	public Double getTarifaguia() {
		return tarifaguia;
	}
	public void setTarifaguia(Double tarifaguia) {
		this.tarifaguia = tarifaguia;
	}
	public Double getTarifaastoria() {
		return tarifaastoria;
	}
	public void setTarifaastoria(Double tarifaastoria) {
		this.tarifaastoria = tarifaastoria;
	}
	public Integer getRangoinicial() {
		return rangoinicial;
	}
	public void setRangoinicial(Integer rangoinicial) {
		this.rangoinicial = rangoinicial;
	}
	public Integer getRangofinal() {
		return rangofinal;
	}
	public void setRangofinal(Integer rangofinal) {
		this.rangofinal = rangofinal;
	}
	public AgenciaCarga getAgencia() {
		return agencia;
	}
	public void setAgencia(AgenciaCarga agencia) {
		this.agencia = agencia;
	}
	public Integer getIdciudadorigen() {
		return idciudadorigen;
	}
	public void setIdciudadorigen(Integer idciudadorigen) {
		this.idciudadorigen = idciudadorigen;
	}
	public Integer getIdciudaddestino() {
		return idciudaddestino;
	}
	public void setIdciudaddestino(Integer idciudaddestino) {
		this.idciudaddestino = idciudaddestino;
	}
	public Integer getIdruta() {
		return idruta;
	}
	public void setIdruta(Integer idruta) {
		this.idruta = idruta;
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
	public String getConcepto1() {
		return concepto1;
	}
	public void setConcepto1(String concepto1) {
		this.concepto1 = concepto1;
	}
	public Double getValor1() {
		return valor1;
	}
	public void setValor1(Double valor1) {
		this.valor1 = valor1;
	}
	public String getConcepto2() {
		return concepto2;
	}
	public void setConcepto2(String concepto2) {
		this.concepto2 = concepto2;
	}
	public Double getValor2() {
		return valor2;
	}
	public void setValor2(Double valor2) {
		this.valor2 = valor2;
	}
	public String getConcepto3() {
		return concepto3;
	}
	public void setConcepto3(String concepto3) {
		this.concepto3 = concepto3;
	}
	public Double getValor3() {
		return valor3;
	}
	public void setValor3(Double valor3) {
		this.valor3 = valor3;
	}
	public String getConcepto4() {
		return concepto4;
	}
	public void setConcepto4(String concepto4) {
		this.concepto4 = concepto4;
	}
	public Double getValor4() {
		return valor4;
	}
	public void setValor4(Double valor4) {
		this.valor4 = valor4;
	}
	public Integer getIdaerolinea() {
		return idaerolinea;
	}
	public void setIdaerolinea(Integer idaerolinea) {
		this.idaerolinea = idaerolinea;
	}
	public Double getTarifaastoria2() {
		return tarifaastoria2;
	}
	public void setTarifaastoria2(Double tarifaastoria2) {
		this.tarifaastoria2 = tarifaastoria2;
	}
	public Integer getRangoinicial2() {
		return rangoinicial2;
	}
	public void setRangoinicial2(Integer rangoinicial2) {
		this.rangoinicial2 = rangoinicial2;
	}
	public Integer getRangofinal2() {
		return rangofinal2;
	}
	public void setRangofinal2(Integer rangofinal2) {
		this.rangofinal2 = rangofinal2;
	}
	public Double getTarifaastoria3() {
		return tarifaastoria3;
	}
	public void setTarifaastoria3(Double tarifaastoria3) {
		this.tarifaastoria3 = tarifaastoria3;
	}
	public Integer getRangoinicial3() {
		return rangoinicial3;
	}
	public void setRangoinicial3(Integer rangoinicial3) {
		this.rangoinicial3 = rangoinicial3;
	}
	public Integer getRangofinal3() {
		return rangofinal3;
	}
	public void setRangofinal3(Integer rangofinal3) {
		this.rangofinal3 = rangofinal3;
	}
	public Double getTarifacliente2() {
		return tarifacliente2;
	}
	public void setTarifacliente2(Double tarifacliente2) {
		this.tarifacliente2 = tarifacliente2;
	}
	public Double getTarifacliente3() {
		return tarifacliente3;
	}
	public void setTarifacliente3(Double tarifacliente3) {
		this.tarifacliente3 = tarifacliente3;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public Double getValorast1() {
		return valorast1;
	}
	public void setValorast1(Double valorast1) {
		this.valorast1 = valorast1;
	}
	public Double getValorast2() {
		return valorast2;
	}
	public void setValorast2(Double valorast2) {
		this.valorast2 = valorast2;
	}
	public Double getValorast3() {
		return valorast3;
	}
	public void setValorast3(Double valorast3) {
		this.valorast3 = valorast3;
	}
	public Double getValorast4() {
		return valorast4;
	}
	public void setValorast4(Double valorast4) {
		this.valorast4 = valorast4;
	}
	@Override
	public String toString() {
		return "Destino [id=" + id + ", idorigen=" + idorigen + ", iddestino="
				+ iddestino + ", idciudadorigen=" + idciudadorigen
				+ ", idciudaddestino=" + idciudaddestino + ", tarifaguia="
				+ tarifaguia + ", tarifacliente=" + tarifacliente
				+ ", tarifaastoria=" + tarifaastoria + ", rangoinicial="
				+ rangoinicial + ", rangofinal=" + rangofinal
				+ ", tarifacliente2=" + tarifacliente2 + ", tarifaastoria2="
				+ tarifaastoria2 + ", rangoinicial2=" + rangoinicial2
				+ ", rangofinal2=" + rangofinal2 + ", tarifacliente3="
				+ tarifacliente3 + ", tarifaastoria3=" + tarifaastoria3
				+ ", rangoinicial3=" + rangoinicial3 + ", rangofinal3="
				+ rangofinal3 + ", idruta=" + idruta + ", idaerolinea="
				+ idaerolinea + ", lunes=" + lunes + ", martes=" + martes
				+ ", miercoles=" + miercoles + ", jueves=" + jueves
				+ ", viernes=" + viernes + ", sabado=" + sabado + ", domingo="
				+ domingo + ", concepto1=" + concepto1 + ", valor1=" + valor1
				+ ", valorast1=" + valorast1 + ", concepto2=" + concepto2
				+ ", valor2=" + valor2 + ", valorast2=" + valorast2
				+ ", concepto3=" + concepto3 + ", valor3=" + valor3
				+ ", valorast3=" + valorast3 + ", concepto4=" + concepto4
				+ ", valor4=" + valor4 + ", valorast4=" + valorast4
				+ ", observaciones=" + observaciones +  "]";
	}
	
	
	

}
