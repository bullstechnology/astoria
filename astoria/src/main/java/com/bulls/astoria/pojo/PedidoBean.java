package com.bulls.astoria.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public class PedidoBean implements Serializable{
	
	private Integer idgrado;
	private Integer idcolor;
	private Integer idtipoflor;
	private Integer idvariedad;
	private Integer idplantacion;
	private Double cantidadfull;
	private Integer tallosporfull;
	private Integer cantidadtotal;
	private Integer unidades;
	private Double precio;
	private Double preciototal;
	private Double preciototalast;
	private Double comision; 
	private Double preciototaltalloclie;
	private Double preciototaltalloast;
	
	
	private Integer idpedido;
	private Integer iddetalle;
	private Integer idcomposicion;
	private Integer packing;
	private String codigo;
	private String tipocomposicion;
	private String nombrecomposicion;
	
	
	private String nombregrado;
	private String nombrecolor;
	private String nombreplantacion;
	private String nombretipo;
	private String nombrevariedad;
	private String bodega;
	private Integer num;
	
	boolean detalle = false;
	
	private SelectItem plantacion = new SelectItem();
	
	private Integer piezas;
	private String awb;
	private String invoice;
	private String observaciones;
	
	//para guardar los que componene esta cabeza de item
	private List<PedidoBean> composiciones = new ArrayList <PedidoBean> ();
		
	public Integer getIdgrado() {
		return idgrado;
	}
	public void setIdgrado(Integer idgrado) {
		this.idgrado = idgrado;
	}
	public Integer getIdcolor() {
		return idcolor;
	}
	public void setIdcolor(Integer idcolor) {
		this.idcolor = idcolor;
	}
	public Integer getIdtipoflor() {
		return idtipoflor;
	}
	public void setIdtipoflor(Integer idtipoflor) {
		this.idtipoflor = idtipoflor;
	}
	public Integer getIdvariedad() {
		return idvariedad;
	}
	public void setIdvariedad(Integer idvariedad) {
		this.idvariedad = idvariedad;
	}
	public Integer getIdplantacion() {
		return idplantacion;
	}
	public void setIdplantacion(Integer idplantacion) {
		this.idplantacion = idplantacion;
	}
	public Double getCantidadfull() {
		return cantidadfull;
	}
	public void setCantidadfull(Double cantidadfull) {
		this.cantidadfull = cantidadfull;
	}
	public Integer getTallosporfull() {
		return tallosporfull;
	}
	public void setTallosporfull(Integer tallosporfull) {
		this.tallosporfull = tallosporfull;
	}
	public String getNombregrado() {
		return nombregrado;
	}
	public void setNombregrado(String nombregrado) {
		this.nombregrado = nombregrado;
	}
	public String getNombrecolor() {
		return nombrecolor;
	}
	public void setNombrecolor(String nombrecolor) {
		this.nombrecolor = nombrecolor;
	}
	public String getNombreplantacion() {
		return nombreplantacion;
	}
	public void setNombreplantacion(String nombreplantacion) {
		this.nombreplantacion = nombreplantacion;
	}
	public String getNombretipo() {
		return nombretipo;
	}
	public void setNombretipo(String nombretipo) {
		this.nombretipo = nombretipo;
	}
	public String getNombrevariedad() {
		return nombrevariedad;
	}
	public void setNombrevariedad(String nombrevariedad) {
		this.nombrevariedad = nombrevariedad;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public Integer getCantidadtotal() {
		return cantidadtotal;
	}
	public void setCantidadtotal(Integer cantidadtotal) {
		this.cantidadtotal = cantidadtotal;
	}
	public Integer getIdpedido() {
		return idpedido;
	}
	public void setIdpedido(Integer idpedido) {
		this.idpedido = idpedido;
	}
	public Integer getIddetalle() {
		return iddetalle;
	}
	public void setIddetalle(Integer iddetalle) {
		this.iddetalle = iddetalle;
	}
	public Integer getIdcomposicion() {
		return idcomposicion;
	}
	public void setIdcomposicion(Integer idcomposicion) {
		this.idcomposicion = idcomposicion;
	}
	public Integer getPacking() {
		return packing;
	}
	public void setPacking(Integer packing) {
		this.packing = packing;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getTipocomposicion() {
		return tipocomposicion;
	}
	public void setTipocomposicion(String tipocomposicion) {
		this.tipocomposicion = tipocomposicion;
	}
	public String getNombrecomposicion() {
		return nombrecomposicion;
	}
	public void setNombrecomposicion(String nombrecomposicion) {
		this.nombrecomposicion = nombrecomposicion;
	}
	public Integer getUnidades() {
		return unidades;
	}
	public void setUnidades(Integer unidades) {
		this.unidades = unidades;
	}
	public Double getPreciototal() {
		return preciototal;
	}
	public void setPreciototal(Double preciototal) {
		this.preciototal = preciototal;
	}
	public boolean isDetalle() {
		return detalle;
	}
	public void setDetalle(boolean detalle) {
		this.detalle = detalle;
	}
	public SelectItem getPlantacion() {
		return plantacion;
	}
	public void setPlantacion(SelectItem plantacion) {
		this.plantacion = plantacion;
	}
	public Integer getPiezas() {
		return piezas;
	}
	public void setPiezas(Integer piezas) {
		this.piezas = piezas;
	}
	public Double getComision() {
		return comision;
	}
	public void setComision(Double comision) {
		this.comision = comision;
	}
	public String getAwb() {
		return awb;
	}
	public void setAwb(String awb) {
		this.awb = awb;
	}
	public String getInvoice() {
		return invoice;
	}
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public Double getPreciototalast() {
		return preciototalast;
	}
	public void setPreciototalast(Double preciototalast) {
		this.preciototalast = preciototalast;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getBodega() {
		return bodega;
	}
	public void setBodega(String bodega) {
		this.bodega = bodega;
	}
	public Double getPreciototaltalloclie() {
		return preciototaltalloclie;
	}
	public void setPreciototaltalloclie(Double preciototaltalloclie) {
		this.preciototaltalloclie = preciototaltalloclie;
	}
	public Double getPreciototaltalloast() {
		return preciototaltalloast;
	}
	public void setPreciototaltalloast(Double preciototaltalloast) {
		this.preciototaltalloast = preciototaltalloast;
	}
	public List<PedidoBean> getComposiciones() {
		return composiciones;
	}
	public void setComposiciones(List<PedidoBean> composiciones) {
		this.composiciones = composiciones;
	}
	
	
	
	
	
}

