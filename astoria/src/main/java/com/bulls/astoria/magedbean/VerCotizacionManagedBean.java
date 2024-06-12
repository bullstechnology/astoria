package com.bulls.astoria.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.PrimeFaces;
//import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bulls.astoria.enumeracion.EnuDominio;
import com.bulls.astoria.persistence.Aerolinea;
import com.bulls.astoria.persistence.AgenciaCarga;
import com.bulls.astoria.persistence.Cliente;
import com.bulls.astoria.persistence.Comision;
import com.bulls.astoria.persistence.Composicion;
import com.bulls.astoria.persistence.Cotizacion;
import com.bulls.astoria.persistence.Destino;
import com.bulls.astoria.persistence.DetallePedido;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.Handler;
import com.bulls.astoria.persistence.Pedido;
import com.bulls.astoria.persistence.Persona;
import com.bulls.astoria.persistence.Plantacion;
import com.bulls.astoria.persistence.PlantacionProducto;
import com.bulls.astoria.persistence.ProductoCotizacion;
import com.bulls.astoria.persistence.ProductoGrados;
import com.bulls.astoria.persistence.Truck;
import com.bulls.astoria.pojo.EncabezadoPedidoBean;
import com.bulls.astoria.pojo.PedidoBean;
import com.bulls.astoria.pojo.Producto;
import com.bulls.astoria.pojo.ProductoPrecio;
import com.bulls.astoria.service.ClienteService;
import com.bulls.astoria.service.DominioService;
import com.bulls.astoria.service.PedidoService;
import com.bulls.astoria.service.PersonaService;
import com.bulls.astoria.service.PlantacionService;
import com.bulls.astoria.utils.Convertidor;
import com.bulls.astoria.utils.Validador;


@ManagedBean (name="verCotizacionMB")
@SessionScoped
public class VerCotizacionManagedBean extends GeneralManagedBean implements Serializable{
	
	
	
    
	ResourceBundle bundle ;
    private Cotizacion cotizacion; 
    private Integer idCotizacion;
    private List <Cotizacion> listaCotizaciones;
    private List <ProductoCotizacion> listaProductos;
    private List <PedidoBean> listaPedido = new ArrayList <PedidoBean>();
    private Double totalPrecio;
    private Double totalBoxes;

	
	
	@ManagedProperty(value="#{DominioService}")
	DominioService dominioService;
	
		
	@ManagedProperty(value="#{PedidoService}")
	PedidoService pedidoService;
	
	@ManagedProperty(value="#{uMB}")
	private UtilsManagedBean utils;
	
	
	@PostConstruct
	public void VerCotizacionManagedBean(){
		
		borrarSession();
		bundle =  ResourceBundle.getBundle("messages");
		cotizacion = new Cotizacion();
		listaCotizaciones = new ArrayList <Cotizacion> ();
		listaCotizaciones = pedidoService.getCotizaciones("A");
	}
	
	public String verCotizacion (Cotizacion cotizacion){
		this.cotizacion = cotizacion;
		listaPedido = subir(pedidoService.getProductosCotizacion(cotizacion.getIdcotizacion()));
		return "vercotizacion";
		//pedidoService.cotizar(cotizacion);
	}
	
	 public List <PedidoBean> subir(List<ProductoCotizacion> listaProductos){
	    
	   List <PedidoBean> listaPedido = new ArrayList <PedidoBean>();
	   for (ProductoCotizacion producto : listaProductos){ 	
	    	Dominio domtipo = dominioService.getDominio(producto.getFlor());
	    	Dominio domvariedad = dominioService.getDominio(producto.getVariedad());
	    	Dominio domgrado = dominioService.getDominio(producto.getGrado());
	    	
	    	
	    	PedidoBean pedido = new PedidoBean();
	    	pedido.setCantidadfull(producto.getBoxes().doubleValue());
	    	pedido.setPrecio(producto.getPrecio());
	    	pedido.setIdtipoflor(producto.getFlor());
	    	pedido.setIdvariedad(producto.getVariedad());
	    	pedido.setIdgrado(producto.getGrado());
	    	pedido.setNombretipo(domtipo.getNomcorto());
	    	pedido.setNombrevariedad(domvariedad.getNomcorto());
	    	pedido.setNombregrado(domgrado.getNomcorto());
	    	listaPedido.add(pedido);
	        }  
	   totalizar();
	   return listaPedido; 
	 }
	 
	 public void totalizar(){
	    	
	    	Double totalPrecioAux = 0.0;
	    	Double totalBoxesAux = 0.0;
	    	
	    	for(PedidoBean pedido : this.listaPedido){
	    		totalPrecioAux = totalPrecioAux + pedido.getPrecio();
	    		totalBoxesAux  = totalBoxesAux + pedido.getCantidadfull();
	    	}
	    	
	    	this.totalPrecio = totalPrecioAux;
	        this.totalBoxes  = totalBoxesAux;
	    }
	 
	 public boolean estaRevisado(){
	    	
	    	if(this.cotizacion.getEstado().equalsIgnoreCase("A")){
	    		return false;
	    	}else {
	    		return true;
	    	}
	    	
	}
	 
	 public void editar(){
	    	
       this.cotizacion.setEstado("R");
	   pedidoService.cotizar(this.cotizacion); 
	   this.putAuditoria("Edito Cotizacion", "U", "Edito cotizacion así : - " + cotizacion.toString());
	   abrirConfirmacion();
       return;
	}
	    
	 private void abrirConfirmacion(){
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmaeditarcotizacion"));
			//RequestContext.getCurrentInstance().showMessageInDialog(message);
			PrimeFaces.current().dialog().showMessageDynamic(message);
			return;

	}
	
	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	public Cotizacion getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(Cotizacion cotizacion) {
		this.cotizacion = cotizacion;
	}

	public Integer getIdCotizacion() {
		return idCotizacion;
	}

	public void setIdCotizacion(Integer idCotizacion) {
		this.idCotizacion = idCotizacion;
	}

	public List<Cotizacion> getListaCotizaciones() {
		return listaCotizaciones;
	}

	public void setListaCotizaciones(List<Cotizacion> listaCotizaciones) {
		this.listaCotizaciones = listaCotizaciones;
	}

	public DominioService getDominioService() {
		return dominioService;
	}

	public void setDominioService(DominioService dominioService) {
		this.dominioService = dominioService;
	}

	public PedidoService getPedidoService() {
		return pedidoService;
	}

	public void setPedidoService(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

	public UtilsManagedBean getUtils() {
		return utils;
	}

	public void setUtils(UtilsManagedBean utils) {
		this.utils = utils;
	}
    
	
	public List<ProductoCotizacion> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(List<ProductoCotizacion> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public List<PedidoBean> getListaPedido() {
		return listaPedido;
	}

	public void setListaPedido(List<PedidoBean> listaPedido) {
		this.listaPedido = listaPedido;
	}

	public Double getTotalPrecio() {
		return totalPrecio;
	}

	public void setTotalPrecio(Double totalPrecio) {
		this.totalPrecio = totalPrecio;
	}

	public Double getTotalBoxes() {
		return totalBoxes;
	}

	public void setTotalBoxes(Double totalBoxes) {
		this.totalBoxes = totalBoxes;
	}

	public void borrarSession(){
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("aerolineaMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("cargaMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("buscarNotasMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("buscarOrdenMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("buscarPedidoMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("buscarPedidoAutoMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("buscarPedidoDespachadoMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("buscarPedidoProgramadoMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("catalogoMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("clienteMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("colorMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("comisionesMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("conciliarPedidoMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("consolidadoVentasMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("crearCiudadMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("crearFlorMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("notasMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("crearPaisMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("pedidocotMB");
		 //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("pedidoMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("plantillaMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("customerMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("destinoMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("editarAerolineaMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("editarCargaMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("editarClaveMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("editarClienteMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("editarListaMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("editarPedidoMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("editarPlantacionMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("editarPlantillaMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("editarUsuarioMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("empresaMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("entregaPedidoMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("estadoCuentaConMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("estadoCuentaMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("franjaMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("gradoMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("gradomultipleMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("handlerMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idiomaMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("invoicePedidoMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("loginMgmtBean");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("personaMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("plantacionMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("preciosMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("productoMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("truckMB");
		  // FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuarioMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("cotizacionMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("variedadMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("verCotizacionRevMB");

	   }    
}
