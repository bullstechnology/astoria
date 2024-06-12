package com.bulls.astoria.magedbean;

import java.io.Serializable;
import java.util.HashMap;
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
import org.springframework.dao.DataAccessException;

import com.bulls.astoria.enumeracion.EnuDominio;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.TipoDominio;
import com.bulls.astoria.service.DominioService;
import com.bulls.astoria.utils.Convertidor;
import com.bulls.astoria.utils.Validador;



@ManagedBean (name="crearCiudadMB")
@SessionScoped
public class CrearCiudadManagedBean extends GeneralManagedBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static final String SUCCESS = "success";
	private static final String ERROR   = "error";

	@ManagedProperty(value="#{DominioService}")
	DominioService dominioService;


	private String ciudad ;
	private String descripcion ;
	private Integer pais;
	private String pais2;
	List <Dominio> paises;
	List <Dominio> listaCiudades;
	private Dominio dominio;
	private Dominio dominioEditar;
	List<SelectItem> listaPaises;

	private String message;
	ResourceBundle bundle ;
		 
	public String getMessage() {
	    return message;
	}

	public void setMessage(String message) {
	    this.message = message;
	}

	@PostConstruct
	public void CrearCiudadManagedBean(){
		
		    borrarSession();
		    dominioService = getDominioService();
		    paises = dominioService.getDominios(EnuDominio.PAISES.getIdTipoDominio());
		    listaPaises = Convertidor.dominiosToSelectdItems(paises);
		    
			bundle =  ResourceBundle.getBundle("messages");

	}


	 public void crearCiudad() {
	        try {
	            TipoDominio tipoDominio = dominioService.getTipoDominio(EnuDominio.CIUDADES.getIdTipoDominio());
	            dominio = new Dominio();
	            dominio.setNomcorto(this.ciudad);
	        	dominio.setNomlargo(this.ciudad);
	        	dominio.setDescripcion(this.descripcion);
	        	dominio.setTipodominio(tipoDominio);   
	        	dominio.setDominiopadre(this.pais);
	        	
	        	
	        	if(this.validar()){
	        		
	        		dominioService.saveDominio(dominio);
	        		this.putAuditoria("Crear Ciudad", "C", "Creo la ciudad asi : - " + dominio.toString());
	        		reset();
	        		paises = dominioService.getDominios(EnuDominio.PAISES.getIdTipoDominio());
	        		listaPaises = Convertidor.dominiosToSelectdItems(paises);
	        		abrirConfirmacion();
	        		
	        	}
	           
	        } catch (DataAccessException e) {
	            e.printStackTrace();
	        }
	      
	    }

	 
	 public void editarCiudad() {
	     try {
	    	
	     	dominioEditar.setNomcorto(this.ciudad);
	     	dominioEditar.setNomlargo(this.ciudad);
	     	dominioEditar.setDescripcion(this.descripcion);
	     	
	     	
	     	if(this.validarEditar()){
	     		
	     		dominioService.editarDominio(dominioEditar);
	     		this.putAuditoria("Editar Ciudad", "U", "Edito la ciudad así : - " + dominioEditar.toString());
	     		paises = dominioService.getDominios(EnuDominio.PAISES.getIdTipoDominio());
	     		listaPaises = Convertidor.dominiosToSelectdItems(paises);
	     		abrirConfirmacionEditar();
	     	}
	        
	     } catch (DataAccessException e) {
	         e.printStackTrace();
	     }
	   
	 }

    public void getCiudades(){
    	
    	System.out.println("en get Variedades");
    	listaCiudades= dominioService.getDominiosXPadre(this.pais);
    }
    

	public void reset() {
	    this.ciudad= null;
	    this.descripcion= null;
	    
	    System.out.println("en reset*************************************+");

	}
	

	public boolean validar(){
		boolean ok = true;
		
			if (!Validador.esLongitudCorrecta(this.ciudad, 1, 45)){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ciudad: "," Debe tener entre 1 y 45 caracteres" ));
				ok = false;
			}
			if (!Validador.esLongitudCorrecta(this.descripcion, 1, 45)){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Descripción: "," Debe tener entre 1 y 45 caracteres" ));
				ok = false;
			}
			
			
			// validaciones logicas.
			
			
			if(dominioService.getDominiosXNombre(this.ciudad).size() > 0){
				for(Dominio dom : dominioService.getDominiosXNombre(this.ciudad)){
					if(dom.getDominiopadre().intValue() == this.pais.intValue()){
						FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errorciudadyaexiste"));
						//RequestContext.getCurrentInstance().showMessageInDialog(message);
						PrimeFaces.current().dialog().showMessageDynamic(message);
						ok =  false;
					}
				}
				
			} 
			
			
				   	

		return ok;
	}


	public boolean validarEditar(){
		boolean ok = true;
		
			if (!Validador.esLongitudCorrecta(this.ciudad, 1, 45)){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ciudad: "," Debe tener entre 1 y 45 caracteres" ));
				ok = false;
			}
			if (!Validador.esLongitudCorrecta(this.descripcion, 1, 45)){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Descripción: "," Debe tener entre 1 y 45 caracteres" ));
				ok = false;
			}
			
			
			// validaciones logicas.
			
			if(dominioService.getDominiosXNombre(this.ciudad).size() > 1){
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errorciudadyaexiste"));
				//RequestContext.getCurrentInstance().showMessageInDialog(message);
				PrimeFaces.current().dialog().showMessageDynamic(message);
				ok =  false;
			} 
					   	

		return ok;
	}



	private void abrirConfirmacion(){
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmacrearciudad"));
		//RequestContext.getCurrentInstance().showMessageInDialog(message);
		PrimeFaces.current().dialog().showMessageDynamic(message);
		return;

	}


	private void abrirConfirmacionEditar(){
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmaeditarciudad"));
		//RequestContext.getCurrentInstance().showMessageInDialog(message);
		PrimeFaces.current().dialog().showMessageDynamic(message);
		return;

	}

	public String editar (Dominio dominio){

		 reset();
		 this.ciudad = dominio.getNomcorto();
		 this.descripcion= dominio.getDescripcion();
		 this.dominioEditar = dominio;
		 return "editarciudad";

		}

	public DominioService getDominioService() {
		return dominioService;
	}

	public void setDominioService(DominioService dominioService) {
		this.dominioService = dominioService;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getPais() {
		return pais;
	}

	public void setPais(Integer pais) {
		this.pais = pais;
	}

	public String getPais2() {
		return pais2;
	}

	public void setPais2(String pais2) {
		this.pais2 = pais2;
	}

	public List<Dominio> getPaises() {
		return paises;
	}

	public void setPaises(List<Dominio> paises) {
		this.paises = paises;
	}

	public List<Dominio> getListaCiudades() {
		return listaCiudades;
	}

	public void setListaCiudades(List<Dominio> listaCiudades) {
		this.listaCiudades = listaCiudades;
	}

	public Dominio getDominio() {
		return dominio;
	}

	public void setDominio(Dominio dominio) {
		this.dominio = dominio;
	}

	public Dominio getDominioEditar() {
		return dominioEditar;
	}

	public void setDominioEditar(Dominio dominioEditar) {
		this.dominioEditar = dominioEditar;
	}

	public List<SelectItem> getListaPaises() {
		return listaPaises;
	}

	public void setListaPaises(List<SelectItem> listaPaises) {
		this.listaPaises = listaPaises;
	}

	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static String getSuccess() {
		return SUCCESS;
	}

	public static String getError() {
		return ERROR;
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
		  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("crearCiudadMB");
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
		  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuarioMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("variedadMB");

	   }

}

