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
import com.bulls.astoria.persistence.Destino;
import com.bulls.astoria.persistence.DetallePedido;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.Handler;
import com.bulls.astoria.persistence.Nota;
import com.bulls.astoria.persistence.Pedido;
import com.bulls.astoria.persistence.PedidoConciliado;
import com.bulls.astoria.persistence.PedidoFacturado;
import com.bulls.astoria.persistence.Persona;
import com.bulls.astoria.persistence.Plantacion;
import com.bulls.astoria.persistence.PlantacionProducto;
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


@ManagedBean (name="encabezadoPedidoMB")
@SessionScoped
public class EncabezadoPedidoManagedBean extends GeneralManagedBean implements Serializable{
	
	private Integer idPedido;
	private String notafinal;
	private Date fechafactura;
	

	private Date fechadespacho;
	private Date fechallegada;
	private Date fechallegadafinal;
	
	
	
	ResourceBundle bundle ;
	
	
	
	
	
	
	@ManagedProperty(value="#{DominioService}")
	DominioService dominioService;
	
	@ManagedProperty(value="#{PersonaService}")
    PersonaService personaService;
	
	@ManagedProperty(value="#{ClienteService}")
    ClienteService clienteService;
	
	@ManagedProperty(value="#{PlantacionService}")
	PlantacionService plantacionService;
	
	@ManagedProperty(value="#{PedidoService}")
	PedidoService pedidoService;
	
	@ManagedProperty(value="#{uMB}")
	private UtilsManagedBean utils;
	
	
	@PostConstruct
	public void CrearPedidoManagedBean(){
		
		borrarSession();
		Object ptincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		bundle =  ResourceBundle.getBundle("messages");
		
	}
	
	public String  getPedido(EncabezadoPedidoBean pedidoEditar){
		Pedido pedido = pedidoService.getPedidoXId(pedidoEditar.getIdpedido());
		PedidoFacturado pedidoFacturado = pedidoService.getPedidoFacturadoXId(pedidoEditar.getIdpedido());
		this.idPedido = pedidoEditar.getIdpedido();
    	this.fechadespacho = pedido.getFechadespacho();
    	this.fechallegada = pedido.getFechallegada();
    	this.fechallegadafinal = pedido.getFechallegadafinal();
    	this.notafinal= pedidoFacturado.getNotafinal();
    	
    	if(pedidoFacturado != null){
    		this.fechafactura= pedidoFacturado.getDate();
    	}
    	return "editarencabezado";
	}
	
	
	
	public void editarEncabezado (){
		Pedido encabezado = new Pedido();
		encabezado.setId(this.idPedido);
		encabezado.setFechadespacho(this.fechadespacho);
		encabezado.setFechallegada(this.fechallegada);
		encabezado.setFechallegadafinal(this.fechallegadafinal);
		encabezado.setObservaciones(this.notafinal);
		pedidoService.editarEncabezado(encabezado, fechafactura);
		
		
		List <Nota> listaNotas  = dominioService.getNotasPedido(this.idPedido);
		for (Nota nota:listaNotas){
			if(nota.getIdpedido() != null){
				nota.setFecha(this.fechadespacho);
				dominioService.editNota(nota);
			}
		}
		
		this.putAuditoria("Editar fechas pedido y sus notas", "U", "Edito fechas pedido asi : - " + encabezado.toString());
		abrirConfirmacion(this.idPedido);
		
	}
	
   private void abrirConfirmacion(Integer id){
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), "Edicion exitosa del pedido " + id);
		//RequestContext.getCurrentInstance().showMessageInDialog(message);
		PrimeFaces.current().dialog().showMessageDynamic(message);
		
		
		return;

	}

	

	public DominioService getDominioService() {
		return dominioService;
	}

	public void setDominioService(DominioService dominioService) {
		this.dominioService = dominioService;
	}

	public PersonaService getPersonaService() {
		return personaService;
	}

	public void setPersonaService(PersonaService personaService) {
		this.personaService = personaService;
	}

	public ClienteService getClienteService() {
		return clienteService;
	}

	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	public PlantacionService getPlantacionService() {
		return plantacionService;
	}

	public void setPlantacionService(PlantacionService plantacionService) {
		this.plantacionService = plantacionService;
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
     
	
	public Integer getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Integer idPedido) {
		this.idPedido = idPedido;
	}

	public String getNotafinal() {
		return notafinal;
	}

	public void setNotafinal(String notafinal) {
		this.notafinal = notafinal;
	}
	
	

	public Date getFechafactura() {
		return fechafactura;
	}



	public void setFechafactura(Date fechafactura) {
		this.fechafactura = fechafactura;
	}



	public ResourceBundle getBundle() {
		return bundle;
	}



	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
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
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("variedadMB");

	   }    
}
