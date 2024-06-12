package com.bulls.astoria.pojo;

import java.io.Serializable;
import java.util.Date;

public class ConsolidadoVentasBean implements Serializable {
	
	Date fechaDespacho;
	Integer idpedido;
    Integer pais;
    String nombrepais;
    Integer semana;
    String bodega;
    Integer idCliente;
    String nombreCliente;
    String codigoCliente;
    Integer idPlantacion;
    String nombrePlantacion;
    String composicion;
    Integer idTipo;
    String nombreTipo;
    Integer idVariedad;
    String nombreVariedad;
    Integer grado;
    String nombreGrado;
    Double cajas;
    Integer packing;
    Integer totalTallosCaja;
    Integer totalTallosvariedad;
    Double valorPlantacion;
    Double valorCliente;
    Double totalFacturaPlantacionFlor;
    Double totalFacturaClienteFlor;
    String numeroFacturaPlantacion;
    String numeroFacturaCliente;
    Double ingresoPorFlor;
    String awb;
    Double valorTransporteCliente;
    Double valorTransporteAstoria;
    Double gananciaPorTransporte;
    Double gananciaPorFactura;
    Double descuentoPorPrepago;
    Double totalFacturaCliente;
    Double pesoAstoria;
    Double pesoCliente;
    Double valorKgAstoria;
    Double valorKgCliente;
    String diligenciador;
    String estado;
    
    
	
	public Date getFechaDespacho() {
		return fechaDespacho;
	}
	public void setFechaDespacho(Date fechaDespacho) {
		this.fechaDespacho = fechaDespacho;
	}
	public Integer getIdpedido() {
		return idpedido;
	}
	public void setIdpedido(Integer idpedido) {
		this.idpedido = idpedido;
	}
	public Integer getPais() {
		return pais;
	}
	public void setPais(Integer pais) {
		this.pais = pais;
	}
	public String getNombrepais() {
		return nombrepais;
	}
	public void setNombrepais(String nombrepais) {
		this.nombrepais = nombrepais;
	}
	public Integer getSemana() {
		return semana;
	}
	public void setSemana(Integer semana) {
		this.semana = semana;
	}
	public String getBodega() {
		return bodega;
	}
	public void setBodega(String bodega) {
		this.bodega = bodega;
	}
	public Integer getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public String getCodigoCliente() {
		return codigoCliente;
	}
	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}
	public Integer getIdPlantacion() {
		return idPlantacion;
	}
	public void setIdPlantacion(Integer idPlantacion) {
		this.idPlantacion = idPlantacion;
	}
	public String getNombrePlantacion() {
		return nombrePlantacion;
	}
	public void setNombrePlantacion(String nombrePlantacion) {
		this.nombrePlantacion = nombrePlantacion;
	}
	public String getComposicion() {
		return composicion;
	}
	public void setComposicion(String composicion) {
		this.composicion = composicion;
	}
	public Integer getIdTipo() {
		return idTipo;
	}
	public void setIdTipo(Integer idTipo) {
		this.idTipo = idTipo;
	}
	public String getNombreTipo() {
		return nombreTipo;
	}
	public void setNombreTipo(String nombreTipo) {
		this.nombreTipo = nombreTipo;
	}
	public Integer getIdVariedad() {
		return idVariedad;
	}
	public void setIdVariedad(Integer idVariedad) {
		this.idVariedad = idVariedad;
	}
	public String getNombreVariedad() {
		return nombreVariedad;
	}
	public void setNombreVariedad(String nombreVariedad) {
		this.nombreVariedad = nombreVariedad;
	}
	public Integer getGrado() {
		return grado;
	}
	public void setGrado(Integer grado) {
		this.grado = grado;
	}
	public String getNombreGrado() {
		return nombreGrado;
	}
	public void setNombreGrado(String nombreGrado) {
		this.nombreGrado = nombreGrado;
	}
	public Double getCajas() {
		return cajas;
	}
	public void setCajas(Double cajas) {
		this.cajas = cajas;
	}
	public Integer getPacking() {
		return packing;
	}
	public void setPacking(Integer packing) {
		this.packing = packing;
	}
	public Integer getTotalTallosCaja() {
		return totalTallosCaja;
	}
	public void setTotalTallosCaja(Integer totalTallosCaja) {
		this.totalTallosCaja = totalTallosCaja;
	}
	public Integer getTotalTallosvariedad() {
		return totalTallosvariedad;
	}
	public void setTotalTallosvariedad(Integer totalTallosvariedad) {
		this.totalTallosvariedad = totalTallosvariedad;
	}
	public Double getValorPlantacion() {
		return valorPlantacion;
	}
	public void setValorPlantacion(Double valorPlantacion) {
		this.valorPlantacion = valorPlantacion;
	}
	public Double getValorCliente() {
		return valorCliente;
	}
	public void setValorCliente(Double valorCliente) {
		this.valorCliente = valorCliente;
	}
	public Double getTotalFacturaPlantacionFlor() {
		return totalFacturaPlantacionFlor;
	}
	public void setTotalFacturaPlantacionFlor(Double totalFacturaPlantacionFlor) {
		this.totalFacturaPlantacionFlor = totalFacturaPlantacionFlor;
	}
	public Double getTotalFacturaClienteFlor() {
		return totalFacturaClienteFlor;
	}
	public void setTotalFacturaClienteFlor(Double totalFacturaClienteFlor) {
		this.totalFacturaClienteFlor = totalFacturaClienteFlor;
	}
	public String getNumeroFacturaPlantacion() {
		return numeroFacturaPlantacion;
	}
	public void setNumeroFacturaPlantacion(String numeroFacturaPlantacion) {
		this.numeroFacturaPlantacion = numeroFacturaPlantacion;
	}
	public String getNumeroFacturaCliente() {
		return numeroFacturaCliente;
	}
	public void setNumeroFacturaCliente(String numeroFacturaCliente) {
		this.numeroFacturaCliente = numeroFacturaCliente;
	}
	public Double getIngresoPorFlor() {
		return ingresoPorFlor;
	}
	public void setIngresoPorFlor(Double ingresoPorFlor) {
		this.ingresoPorFlor = ingresoPorFlor;
	}
	public String getAwb() {
		return awb;
	}
	public void setAwb(String awb) {
		this.awb = awb;
	}
	public Double getValorTransporteCliente() {
		return valorTransporteCliente;
	}
	public void setValorTransporteCliente(Double valorTransporteCliente) {
		this.valorTransporteCliente = valorTransporteCliente;
	}
	public Double getValorTransporteAstoria() {
		return valorTransporteAstoria;
	}
	public void setValorTransporteAstoria(Double valorTransporteAstoria) {
		this.valorTransporteAstoria = valorTransporteAstoria;
	}
	public Double getGananciaPorTransporte() {
		return gananciaPorTransporte;
	}
	public void setGananciaPorTransporte(Double gananciaPorTransporte) {
		this.gananciaPorTransporte = gananciaPorTransporte;
	}
	public Double getGananciaPorFactura() {
		return gananciaPorFactura;
	}
	public void setGananciaPorFactura(Double gananciaPorFactura) {
		this.gananciaPorFactura = gananciaPorFactura;
	}
	public Double getDescuentoPorPrepago() {
		return descuentoPorPrepago;
	}
	public void setDescuentoPorPrepago(Double descuentoPorPrepago) {
		this.descuentoPorPrepago = descuentoPorPrepago;
	}
	public Double getTotalFacturaCliente() {
		return totalFacturaCliente;
	}
	public void setTotalFacturaCliente(Double totalFacturaCliente) {
		this.totalFacturaCliente = totalFacturaCliente;
	}
	public Double getPesoAstoria() {
		return pesoAstoria;
	}
	public void setPesoAstoria(Double pesoAstoria) {
		this.pesoAstoria = pesoAstoria;
	}
	public Double getPesoCliente() {
		return pesoCliente;
	}
	public void setPesoCliente(Double pesoCliente) {
		this.pesoCliente = pesoCliente;
	}
	public Double getValorKgAstoria() {
		return valorKgAstoria;
	}
	public void setValorKgAstoria(Double valorKgAstoria) {
		this.valorKgAstoria = valorKgAstoria;
	}
	public Double getValorKgCliente() {
		return valorKgCliente;
	}
	public void setValorKgCliente(Double valorKgCliente) {
		this.valorKgCliente = valorKgCliente;
	}
	public String getDiligenciador() {
		return diligenciador;
	}
	public void setDiligenciador(String diligenciador) {
		this.diligenciador = diligenciador;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
    
    
    
    
    
}
