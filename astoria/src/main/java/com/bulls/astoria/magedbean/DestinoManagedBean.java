package com.bulls.astoria.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.PrimeFaces;

//import org.primefaces.context.RequestContext;

import com.bulls.astoria.enumeracion.EnuDominio;
import com.bulls.astoria.persistence.AgenciaCarga;
import com.bulls.astoria.persistence.Destino;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.Ruta;
import com.bulls.astoria.service.DominioService;
import com.bulls.astoria.utils.Convertidor;
import com.bulls.astoria.utils.Validador;


@ManagedBean (name="destinoMB")
@SessionScoped
//@RequestScoped
public class DestinoManagedBean extends GeneralManagedBean implements Serializable{


	ResourceBundle bundle ;
	
	List <Dominio> paises;
	List <Dominio> ciudades;
	List <Dominio> ciudadesDestino;
	public List<Dominio> getCiudadesDestino() {
		return ciudadesDestino;
	}


	public void setCiudadesDestino(List<Dominio> ciudadesDestino) {
		this.ciudadesDestino = ciudadesDestino;
	}

	List <SelectItem> listaPaisesDom;
	List <SelectItem> listaCiudadesDom;
	List <SelectItem> listaCiudadesOrigenDom;
	List <SelectItem> listaCiudadesDestinoDom;

	private List <com.bulls.astoria.pojo.Destino> listaDestinos;
	
	
	
	private Integer idorigen;
	private Integer iddestino;
	private Integer idciudadorigen;
	private Integer idciudaddestino;

	private SelectItem idorigendom;
	
	public SelectItem getIdorigendom() {
		return idorigendom;
	}


	public void setIdorigendom(SelectItem idorigendom) {
		this.idorigendom = idorigendom;
	}

	@ManagedProperty(value="#{DominioService}")
	DominioService dominioService;
	
	
	@PostConstruct
	public void DestinoManagedBean(){
		System.out.println("DestinoManagedBean***********");
		borrarSession();
		
		bundle =  ResourceBundle.getBundle("messages");
		paises = dominioService.getDominios(EnuDominio.PAISES.getIdTipoDominio());
		ciudades = dominioService.getDominios(EnuDominio.CIUDADES.getIdTipoDominio());
		listaPaisesDom = Convertidor.dominiosToSelectdItems(paises);
		//listaCiudadesDom = Convertidor.dominiosToSelectdItems(ciudades);
		listaDestinos = new ArrayList <com.bulls.astoria.pojo.Destino> ();
		
		getDestinos();
	}
	
	
    public void getCiudadesPaisOrigen(){
    	
    	System.out.println("Trayendo las ciudades origen");

    	ciudades= dominioService.getDominiosXPadre(this.idorigen);
    	
    	System.out.println("Trajociudades origen " + ciudades.size());
    	listaCiudadesOrigenDom = Convertidor.dominiosToSelectdItems(ciudades);
    	
    	System.out.println("Trajociudades origen fianl  " + listaCiudadesOrigenDom.size());
    }
    public void getCiudadesPaisDestino(){

    	ciudadesDestino= dominioService.getDominiosXPadre(this.iddestino);
    	listaCiudadesDestinoDom = Convertidor.dominiosToSelectdItems(ciudadesDestino);
    }
    
    
    
    public void subir (){
		
    	System.out.println("subiendo destinoooooooo0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
    	if(validarSubir()){
    	
    		Ruta ruta = new Ruta();

    		ruta.setIdpaisdestino(this.iddestino);
    		ruta.setIdpaisorigen(this.idorigen);
    		ruta.setIdciudaddestino(this.idciudaddestino);
    		ruta.setIdciudadorigen(this.idciudadorigen);
    	    dominioService.crearRuta(ruta);
    	    this.putAuditoria("Crear Destino", "C", "Creo el destino así : - " + ruta.toString());
    	    getDestinos();
    	    abrirConfirmacion();
    	}
    	return;	
     }
    
   public  void eliminar(com.bulls.astoria.pojo.Destino destino){
	   dominioService.eliminarRuta(destino.getIdciudadorigen(),destino.getIdciudaddestino());
	   this.putAuditoria("Eliminar Ruta", "E", "Elimino ruta ");
	   getDestinos();
	   abrirConfirmacionEliminar();
    }
    
    public boolean validarSubir(){
  	  
  	  boolean ok = true;
   		  if(0 == this.idorigen.intValue() || 0 == this.iddestino.intValue() || 0 == this.idciudadorigen.intValue() || 0 == this.idciudaddestino.intValue()){
  			  ok = false;
  			  FacesContext.getCurrentInstance().addMessage(null,
  						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Destinos","Debe escoger un Origen y un Destino" ));
  		  }
   		  
   		  for(com.bulls.astoria.pojo.Destino destino:listaDestinos){
   			 if((destino.getIdciudadorigen().intValue() == this.idciudadorigen.intValue()) && (destino.getIdciudaddestino().intValue() == this.idciudaddestino.intValue())){
   				 ok = false;
     			  FacesContext.getCurrentInstance().addMessage(null,
     						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Destinos","El destino ya existe" ));
   		  }
   		  }

   	  return ok;
  	
    }
    
    
public void getDestinos (){
		
	
	System.out.println("entre a destinosssssssssssssssssssssssssssssssssssssssssssssssss");
    		
    		listaDestinos = new ArrayList <com.bulls.astoria.pojo.Destino> ();
    		List <Ruta> rutas = dominioService.getListaRutas();
    		for(Ruta ruta:rutas){
    			com.bulls.astoria.pojo.Destino destino = new com.bulls.astoria.pojo.Destino();
    			
    			destino.setIddestino(ruta.getIdpaisdestino());
        		destino.setIdorigen(ruta.getIdpaisorigen());
        		destino.setIdciudaddestino(ruta.getIdciudaddestino());
        		destino.setIdciudadorigen(ruta.getIdciudadorigen());
        	
        		destino.setNombreorigen(dominioService.getDominio(ruta.getIdpaisorigen()).getNomcorto());
        		destino.setNombrefinal(dominioService.getDominio(ruta.getIdpaisdestino()).getNomcorto());
        		
        		destino.setNombreciudad(dominioService.getDominio(ruta.getIdciudadorigen()).getNomcorto());
        		destino.setNombreciudadfinal(dominioService.getDominio(ruta.getIdciudaddestino()).getNomcorto());
        	
        		listaDestinos.add(destino);
    		}

    	return;	
     }
    
    private void abrirConfirmacion(){
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmacreardestino"));
		//RequestContext.getCurrentInstance().showMessageInDialog(message);
		PrimeFaces.current().dialog().showMessageDynamic(message);
		return;

	}
    
   private void abrirConfirmacionEliminar(){
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmaeliminardestino"));
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


	public List<Dominio> getPaises() {
		return paises;
	}


	public void setPaises(List<Dominio> paises) {
		this.paises = paises;
	}


	public List<Dominio> getCiudades() {
		return ciudades;
	}


	public void setCiudades(List<Dominio> ciudades) {
		this.ciudades = ciudades;
	}


	public List<SelectItem> getListaPaisesDom() {
		return listaPaisesDom;
	}


	public void setListaPaisesDom(List<SelectItem> listaPaisesDom) {
		this.listaPaisesDom = listaPaisesDom;
	}


	public List<SelectItem> getListaCiudadesDom() {
		return listaCiudadesDom;
	}


	public void setListaCiudadesDom(List<SelectItem> listaCiudadesDom) {
		this.listaCiudadesDom = listaCiudadesDom;
	}


	public List<SelectItem> getListaCiudadesOrigenDom() {
		return listaCiudadesOrigenDom;
	}


	public void setListaCiudadesOrigenDom(List<SelectItem> listaCiudadesOrigenDom) {
		this.listaCiudadesOrigenDom = listaCiudadesOrigenDom;
	}


	public List<SelectItem> getListaCiudadesDestinoDom() {
		return listaCiudadesDestinoDom;
	}


	public void setListaCiudadesDestinoDom(List<SelectItem> listaCiudadesDestinoDom) {
		this.listaCiudadesDestinoDom = listaCiudadesDestinoDom;
	}


	public List<com.bulls.astoria.pojo.Destino> getListaDestinos() {
		return listaDestinos;
	}


	public void setListaDestinos(List<com.bulls.astoria.pojo.Destino> listaDestinos) {
		this.listaDestinos = listaDestinos;
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


	public DominioService getDominioService() {
		return dominioService;
	}


	public void setDominioService(DominioService dominioService) {
		this.dominioService = dominioService;
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
		  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("destinoMB");
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
		  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuarioMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("variedadMB");

	   }
}

