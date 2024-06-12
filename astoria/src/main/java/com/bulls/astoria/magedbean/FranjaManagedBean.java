package com.bulls.astoria.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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

import com.bulls.astoria.enumeracion.EnuDominio;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.FranjaPrecios;
import com.bulls.astoria.persistence.Plantacion;
import com.bulls.astoria.persistence.PlantacionPacking;
import com.bulls.astoria.persistence.PlantacionProducto;
import com.bulls.astoria.pojo.Grado;
import com.bulls.astoria.pojo.Producto;
import com.bulls.astoria.service.DominioService;
import com.bulls.astoria.service.PlantacionService;
import com.bulls.astoria.utils.Convertidor;
import com.bulls.astoria.utils.Validador;


@ManagedBean (name="franjaMB")
@SessionScoped
public class FranjaManagedBean extends GeneralManagedBean implements Serializable{

	
	private Integer id;
	private Integer idplantacion;
	private String nombreplantacion;
	private String titulo;
	private String descripcion;
	private Date fechaini;
	private Date fechafin;
	
	transient ResourceBundle bundle ;
	
	
	List <Plantacion> listaPlantaciones;
	List <FranjaPrecios> listaFranjas;
	List<SelectItem> listaPlantacionesSelect;
	
	
	@ManagedProperty(value="#{DominioService}")
	DominioService dominioService;
	
	@ManagedProperty(value="#{PlantacionService}")
	PlantacionService plantacionService;
	
	
	
	@PostConstruct
	public void FranjaManagedBean(){
		
		borrarSession();
		
		bundle =  ResourceBundle.getBundle("messages");
		
		listaPlantaciones = plantacionService.getPlantaciones();
		listaPlantacionesSelect = Convertidor.plantacionToSelectdItems(listaPlantaciones);
		listaFranjas = new ArrayList <FranjaPrecios>();
		

	}
	
	
	public void getListaFranjas2(){
		
		listaFranjas = plantacionService.getFranjasPrecios(this.idplantacion);
		
	}
	public void crearFranja (){
		
		
		if(validarCrear()){
			FranjaPrecios franja = new FranjaPrecios();
		
			franja.setIdplantacion(this.idplantacion);
			franja.setTitulo(this.titulo);
			franja.setDescripcion(this.descripcion);
			franja.setFechafin(this.fechafin);
			franja.setFechaini(this.fechaini);
			plantacionService.crearFranjaPrecios(franja);
			this.putAuditoria("Crear Franja", "C", "Creo la franja así : - " + franja.toString());
			getListaFranjas2();
			abrirConfirmacion();
		}
		return;
	}
	
public void editarFranja (){
		
		
		if(validarCrear()){
			FranjaPrecios franja = new FranjaPrecios();
			franja.setId(this.getId());
			franja.setIdplantacion(this.idplantacion);
			franja.setTitulo(this.titulo);
			franja.setDescripcion(this.descripcion);
			franja.setFechafin(this.fechafin);
			franja.setFechaini(this.fechaini);
			plantacionService.editarFranjaPrecios(franja);
			this.putAuditoria("Editar Franja", "U", "Edito la franja así : - " + franja.toString());
			getListaFranjas2();
			abrirConfirmacion();
		}
		return;
	}
	
public void eliminarFranja (FranjaPrecios franja){
		
		plantacionService.eliminarFranjaPrecios(franja);
		this.putAuditoria("Eliminar Franja", "D", "Elimino la franja : - " + franja.toString());
		getListaFranjas2();
		abrirConfirmacioneliminar();
		return;
	}

public String editar(FranjaPrecios franja){
	
	this.id= franja.getId();
    this.idplantacion = franja.getIdplantacion();
	this.titulo = franja.getTitulo();
	this.descripcion = franja.getDescripcion();
	this.fechaini = franja.getFechaini();
	this.fechafin = franja.getFechafin();
	this.nombreplantacion = plantacionService.getPlantacion(franja.getIdplantacion()).getNombre();
	
	return "editarfranja";
	
}

public boolean validarCrear(){
	
	boolean ok = true;
	
	
	if (fechaini.after(fechafin) || fechaini.equals(fechafin)){
		
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fechas :","Fecha inicial debe ser anterior a Fecha final" ));
		ok = false;
		return ok;
		
	}
	
  /*  for (FranjaPrecios franja : listaFranjas){
    	
    	if(fechaini.equals(franja.getFechaini())){
    		FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fechas :","Fecha inicial ya existe para otro rango" ));
    		ok = false;
    		return ok;
    	}
    	
    	if(fechaini.after(franja.getFechaini()) && fechaini.before(franja.getFechafin())){
    		FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Rangos : ","Ya existe un rango que incluye la fecha" ));
    		ok =false;
    		return ok;
    	}
    	
        if(fechafin.after(franja.getFechaini()) && fechafin.before(franja.getFechafin())){
        	FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Rangos : ","Ya existe un rango que incluye la fecha" ));
        	ok =false;
        	return ok;
    	}
        
    }*/
    	
    	return ok;
    	

	
	
}

public void reset (){
	
	
	 this.titulo = null;
	 this.descripcion = null;
	 this.fechaini = null;
	 this.fechafin = null;
	
}
	
   	
	 private void abrirConfirmacion(){
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmacrearfranja"));
			//RequestContext.getCurrentInstance().showMessageInDialog(message);
			PrimeFaces.current().dialog().showMessageDynamic(message);
			return;

	}
	 
	 private void abrirConfirmacionEditar(){
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmaeditarfranja"));
			//RequestContext.getCurrentInstance().showMessageInDialog(message);
			PrimeFaces.current().dialog().showMessageDynamic(message);
			return;

	}
	 
	 private void abrirConfirmacioneliminar(){
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmaeliminarfranja"));
			//RequestContext.getCurrentInstance().showMessageInDialog(message);
			PrimeFaces.current().dialog().showMessageDynamic(message);
			return;

	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getIdplantacion() {
		return idplantacion;
	}


	public void setIdplantacion(Integer idplantacion) {
		this.idplantacion = idplantacion;
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Date getFechaini() {
		return fechaini;
	}


	public void setFechaini(Date fechaini) {
		this.fechaini = fechaini;
	}


	public Date getFechafin() {
		return fechafin;
	}


	public void setFechafin(Date fechafin) {
		this.fechafin = fechafin;
	}


	public ResourceBundle getBundle() {
		return bundle;
	}


	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}


	public List<Plantacion> getListaPlantaciones() {
		return listaPlantaciones;
	}


	public void setListaPlantaciones(List<Plantacion> listaPlantaciones) {
		this.listaPlantaciones = listaPlantaciones;
	}


	public List<SelectItem> getListaPlantacionesSelect() {
		return listaPlantacionesSelect;
	}


	public void setListaPlantacionesSelect(List<SelectItem> listaPlantacionesSelect) {
		this.listaPlantacionesSelect = listaPlantacionesSelect;
	}


	public DominioService getDominioService() {
		return dominioService;
	}


	public void setDominioService(DominioService dominioService) {
		this.dominioService = dominioService;
	}


	public PlantacionService getPlantacionService() {
		return plantacionService;
	}


	public void setPlantacionService(PlantacionService plantacionService) {
		this.plantacionService = plantacionService;
	}


	public List<FranjaPrecios> getListaFranjas() {
		return listaFranjas;
	}


	public void setListaFranjas(List<FranjaPrecios> listaFranjas) {
		this.listaFranjas = listaFranjas;
	}
	
	

	public String getNombreplantacion() {
		return nombreplantacion;
	}


	public void setNombreplantacion(String nombreplantacion) {
		this.nombreplantacion = nombreplantacion;
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
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("pedidoMB");
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
		  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("franjaMB");
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
		  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuarioMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("variedadMB");

	   }
	
}
