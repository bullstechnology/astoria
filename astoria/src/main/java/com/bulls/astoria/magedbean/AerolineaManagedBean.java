package com.bulls.astoria.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
////import org.primefaces.context.RequestContext;

import com.bulls.astoria.enumeracion.EnuDominio;
import com.bulls.astoria.persistence.Aerolinea;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.Permiso;
import com.bulls.astoria.service.DominioService;
import com.bulls.astoria.utils.Convertidor;
import com.bulls.astoria.utils.Validador;

import com.bulls.astoria.magedbean.GeneralManagedBean;



@ManagedBean (name="aerolineaMB")
@SessionScoped
public class AerolineaManagedBean extends GeneralManagedBean implements Serializable{
	
	private Integer id;
	private String codigo;
	private String nombre;
	private String prefijo;
	private String descripcion;
	
	private List <com.bulls.astoria.pojo.Destino> listaDestinos;
	
	
	private Integer idorigen;
	private Integer iddestino;
	private Integer rangoinicial;
	private Integer rangofinal;
	private Double tarifacliente;
	private Double tarifaastoria;
	private Double tarifaguia;
	
	
	
	List <Dominio> paises;
	List <Dominio> ciudades;
	List <SelectItem> listaPaisesDom;
	List <SelectItem> listaCiudadesDom;
	List <Aerolinea> listaerolineas;

	transient ResourceBundle bundle ;
	
	
	@ManagedProperty(value="#{DominioService}")
	DominioService dominioService;
	
	
	
	@PostConstruct
	public void AerolineaManagedBean(){
		
		
		borrarSession();
		bundle =  ResourceBundle.getBundle("messages");
		paises = dominioService.getDominios(EnuDominio.PAISES.getIdTipoDominio());
		ciudades = dominioService.getDominios(EnuDominio.CIUDADES.getIdTipoDominio());
		listaPaisesDom = Convertidor.dominiosToSelectdItems(paises);
		//listaCiudadesDom = Convertidor.dominiosToSelectdItems(ciudades);
		listaerolineas = dominioService.getAerolineas();
		listaDestinos = new ArrayList <com.bulls.astoria.pojo.Destino> ();

	}
	
	
	
	public String crearAerolinea(){
		
		
		Aerolinea aerolinea = new Aerolinea();
		if(validarCrear()){
			aerolinea = this.putAerolinea();
			dominioService.crearAerolinea(aerolinea);
			dominioService.getAerolineas();
			this.putAuditoria("Crear Aerolinea", "C", "Creo la aerolinea asi : - " + aerolinea.toString());
			abrirConfirmacion();
			return "listaaerolineas";
		}
		return null;
	}
	
	
	
	
	
	private Aerolinea putAerolinea (){
		Aerolinea aerolinea = new Aerolinea ();
		aerolinea.setCodigo(this.codigo);
		aerolinea.setDescripcion(this.descripcion);
		aerolinea.setNombre(this.nombre);
		aerolinea.setPrefijo(this.prefijo);
		//aerolinea.setDestino(putDestinos(aerolinea));
		return aerolinea;
		
	}
	
	private Set<com.bulls.astoria.persistence.Destino> putDestinos(Aerolinea aerolinea){
		
		List <com.bulls.astoria.persistence.Destino> listasalida = new ArrayList <com.bulls.astoria.persistence.Destino>();
		
		for (com.bulls.astoria.pojo.Destino destino : listaDestinos){
			
			com.bulls.astoria.persistence.Destino destinofinal = new com.bulls.astoria.persistence.Destino ();
			destinofinal.setIddestino(destino.getIddestino());
			destinofinal.setIdorigen(destino.getIdorigen());
			destinofinal.setRangofinal(destino.getRangofinal());
			destinofinal.setRangoinicial(destino.getRangoinicial());
			destinofinal.setTarifaastoria(destino.getTarifaastoria());
			destinofinal.setTarifacliente(destino.getTarifacliente());
			destinofinal.setTarifaguia(destino.getTarifaguia());
			//destinofinal.setAerolinea(aerolinea);
			
			listasalida.add(destinofinal);
		}
		
		Set<com.bulls.astoria.persistence.Destino> targeDestinos = new HashSet<com.bulls.astoria.persistence.Destino>(listasalida);
		return targeDestinos;
		
		
	}
	
	
	
public void subir (){
		
	
	if(validarSubir()){
	
		com.bulls.astoria.pojo.Destino destino = new com.bulls.astoria.pojo.Destino();

		destino.setIddestino(this.iddestino);
		destino.setIdorigen(this.idorigen);
		destino.setRangofinal(this.rangofinal);
		destino.setRangoinicial(this.rangoinicial);
		destino.setTarifaastoria(this.tarifaastoria);
		destino.setTarifacliente(this.tarifacliente);
		destino.setTarifaguia(this.tarifaguia);
	
		destino.setNombreorigen(dominioService.getDominio(this.idorigen).getNomcorto());
		destino.setNombrefinal(dominioService.getDominio(this.iddestino).getNomcorto());
	
		listaDestinos.add(destino);
	}
	return;	
 }

  public boolean validarSubir(){
	  
	  boolean ok = true;
	  Integer finalaux = this.rangofinal;
	  
	  if (this.rangofinal.intValue() == 0){
		  finalaux = 999999999;
	  }
	  
	  
	  if(this.rangoinicial.intValue() >= finalaux.intValue()){
		  
		  ok=false;
		  
	  }  
	  if(this.rangoinicial.intValue() < 0 || finalaux.intValue() < 0){
		  
		  ok=false;
		  
	  }  
	  
	  for (com.bulls.astoria.pojo.Destino pojo : listaDestinos){
		  
		  Integer origen = pojo.getIdorigen();
		  Integer destino = pojo.getIddestino();
		  Integer raangoinicial = pojo.getRangoinicial();
		  Integer raangofinal = pojo.getRangofinal();
		  		  
		  if(pojo.getRangofinal().intValue() == 0){
			  raangofinal= 999999999;
		  }
		  
		  
		  if(0 == this.idorigen.intValue() || 0 == this.iddestino.intValue() ){
			  ok = false;
			  FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Destinos","Debe escoger un Origen y un Destino" ));
		  }
		  
		  if(this.tarifaastoria.doubleValue() < 0 ||  this.tarifacliente.doubleValue() < 0 || this.tarifaguia.doubleValue() < 0){
			  ok = false;
			  FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Tarifas","Las tarifas deben ser valores positivos" ));
		  }
		  
		 
		  
		  if(origen.intValue() == this.idorigen.intValue() && destino.intValue() == this.iddestino.intValue() ){
			  
			 
			  
			  if(this.rangoinicial.intValue() >= raangoinicial.intValue()   &&   this.rangoinicial.intValue() < raangofinal.intValue()){
				  
				  ok=false;
			  }
			  
          if(finalaux.intValue() > raangoinicial.intValue()   &&   finalaux.intValue() <= raangofinal.intValue()){
				  
				  ok=false;
			  }
          
          }
	  }
	  
	  if (!ok){
   	   
    	  FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Rangos","El rango definido es inconsistente o ya existe" ));
    	  return ok;
      }
	  
	  
	  return ok;
	
  }
  
  public boolean validarCrear(){
	  boolean ok = true;
	  
	  Aerolinea areolinea = new Aerolinea ();
	  
	  areolinea = dominioService.getAerolineaXCodigo(this.codigo);
	  if(areolinea!= null){
		  
		  ok = false;
		  FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Codigo","Ya existe Aerolinea con ese Codigo" ));
		  
	  }
	  areolinea = dominioService.getAerolineaXPrefijo(this.prefijo);
	  
	  if(areolinea!= null){
		  
		  ok = false;
		  FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Prefijo","Ya existe Aerolinea con ese Prefijo" ));
		  
	  }
	  
	  return ok;
  }
	
	public void eliminar (com.bulls.astoria.pojo.Destino destino){
		
		for(com.bulls.astoria.pojo.Destino dest : listaDestinos){
			
			if(dest == destino){
				listaDestinos.remove(destino);
				break;
			}
		}
		return;	
	}
	
	
	
	
	private List <com.bulls.astoria.pojo.Destino> getDestinos(List <com.bulls.astoria.persistence.Destino> destinos){
		
		List <com.bulls.astoria.pojo.Destino> destinossalida = new ArrayList <com.bulls.astoria.pojo.Destino>();
		for (com.bulls.astoria.persistence.Destino destino : destinos){
			
			com.bulls.astoria.pojo.Destino destinofinal = new com.bulls.astoria.pojo.Destino ();
			destinofinal.setIddestino(destino.getIddestino());
			destinofinal.setIdorigen(destino.getIdorigen());
			destinofinal.setRangofinal(destino.getRangofinal());
			destinofinal.setRangoinicial(destino.getRangoinicial());
			destinofinal.setTarifaastoria(destino.getTarifaastoria());
			destinofinal.setTarifacliente(destino.getTarifacliente());
			destinofinal.setTarifaguia(destino.getTarifaguia());
			
			destinofinal.setNombreorigen((dominioService.getDominio(destino.getIdorigen())).getNomcorto());
			destinofinal.setNombrefinal((dominioService.getDominio(destino.getIddestino())).getNomcorto());
			destinossalida.add(destinofinal);
		}
		return destinossalida;
		
	}
	
	
	private void abrirConfirmacion(){
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmacrearaerolinea"));
		// era lo que estaba antes de nueva version d eenero. 
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
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPrefijo() {
		return prefijo;
	}
	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public List<com.bulls.astoria.pojo.Destino> getListaDestinos() {
		return listaDestinos;
	}



	public void setListaDestinos(List<com.bulls.astoria.pojo.Destino> listaDestinos) {
		this.listaDestinos = listaDestinos;
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



	public ResourceBundle getBundle() {
		return bundle;
	}



	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}



	public DominioService getDominioService() {
		return dominioService;
	}



	public void setDominioService(DominioService dominioService) {
		this.dominioService = dominioService;
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



	public Double getTarifacliente() {
		return tarifacliente;
	}



	public void setTarifacliente(Double tarifacliente) {
		this.tarifacliente = tarifacliente;
	}



	public Double getTarifaastoria() {
		return tarifaastoria;
	}



	public void setTarifaastoria(Double tarifaastoria) {
		this.tarifaastoria = tarifaastoria;
	}



	public Double getTarifaguia() {
		return tarifaguia;
	}



	public void setTarifaguia(Double tarifaguia) {
		this.tarifaguia = tarifaguia;
	}



	public List<Aerolinea> getListaerolineas() {
		return listaerolineas;
	}



	public void setListaerolineas(List<Aerolinea> listaerolineas) {
		this.listaerolineas = listaerolineas;
	}
	
	public void borrarSession(){

		//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("aerolineaMB");
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
