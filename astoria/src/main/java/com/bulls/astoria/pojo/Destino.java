package com.bulls.astoria.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bulls.astoria.persistence.Aerolinea;



public class Destino implements Serializable{
	
	
	private Integer id;
	private Integer idorigen;
	private Integer iddestino;
	private Integer idciudadorigen;
	private Integer idciudaddestino;
	private Double tarifaguia;
	
	private Double tarifacliente;
	private Double tarifaastoria;
	private Integer rangoinicial;
	private Integer rangofinal;
	
	private Double tarifacliente2;
	private Double tarifaastoria2;
	private Integer rangoinicial2;
	private Integer rangofinal2;
	
	private Double tarifacliente3;
	private Double tarifaastoria3;
	private Integer rangoinicial3;
	private Integer rangofinal3;
	
	private String nombreorigen;
	private String nombrefinal;
	private String nombreciudad;
	private String nombreciudadfinal;
	
	private Integer aerolinea;
	private boolean lunes;
	private boolean martes;
	private boolean miercoles;
	private boolean jueves;
	private boolean viernes;
	private boolean sabado;
	private boolean domingo;
	
	private String concepto1;
	private Double valor1;
	private Double valorast1;
	private String concepto2;
	private Double valor2;
	private Double valorast2;
	private String concepto3;
	private Double valor3;
	private Double valorast3;
	private String concepto4;
	private Double valor4;
	private Double valorast4;
	private String observaciones;
	private String nombreaerolinea;
	private String nombredestino;
	
	
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
	
	public String getNombreorigen() {
		return nombreorigen;
	}
	public void setNombreorigen(String nombreorigen) {
		this.nombreorigen = nombreorigen;
	}
	public String getNombrefinal() {
		return nombrefinal;
	}
	public void setNombrefinal(String nombrefinal) {
		this.nombrefinal = nombrefinal;
	}
	public String getNombreciudad() {
		return nombreciudad;
	}
	public void setNombreciudad(String nombreciudad) {
		this.nombreciudad = nombreciudad;
	}
	public String getNombreciudadfinal() {
		return nombreciudadfinal;
	}
	public void setNombreciudadfinal(String nombreciudadfinal) {
		this.nombreciudadfinal = nombreciudadfinal;
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
	public Integer getAerolinea() {
		return aerolinea;
	}
	public void setAerolinea(Integer aerolinea) {
		this.aerolinea = aerolinea;
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
	public Double getTarifacliente2() {
		return tarifacliente2;
	}
	public void setTarifacliente2(Double tarifacliente2) {
		this.tarifacliente2 = tarifacliente2;
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
	public Double getTarifacliente3() {
		return tarifacliente3;
	}
	public void setTarifacliente3(Double tarifacliente3) {
		this.tarifacliente3 = tarifacliente3;
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
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getNombreaerolinea() {
		return nombreaerolinea;
	}
	public void setNombreaerolinea(String nombreaerolinea) {
		this.nombreaerolinea = nombreaerolinea;
	}
	public String getNombredestino() {
		return nombredestino;
	}
	public void setNombredestino(String nombredestino) {
		this.nombredestino = nombredestino;
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
	
	

}
