package com.bulls.astoria.magedbean;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
//import org.primefaces.context.RequestContext;
import org.springframework.dao.DataAccessException;

import com.bulls.astoria.enumeracion.EnuDominio;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.TipoDominio;
import com.bulls.astoria.persistence.Truck;
import com.bulls.astoria.service.DominioService;
import com.bulls.astoria.utils.Validador;


@ManagedBean (name="truckMB")
@SessionScoped
public class TruckManagedBean extends GeneralManagedBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final String SUCCESS = "success";
	private static final String ERROR   = "error";

	@ManagedProperty(value="#{DominioService}")
	DominioService dominioService;

    private Integer id;
	private String codigo ;
	private String nombre;
	private String descripcion ;
	List <Truck> trucks;
	private Truck truck;
	private Truck truckEditar;

	private String message;
	ResourceBundle bundle ;

	 
	public String getMessage() {
	    return message;
	}

	public void setMessage(String message) {
	    this.message = message;
	}

	@PostConstruct
	public void TruckManagedBean(){
		
		    borrarSession();
		    dominioService = getDominioService();
		    trucks = dominioService.getTrucks();
			bundle =  ResourceBundle.getBundle("messages");

	}


	 public void crearTruck() {
	        try {
	            
	            truck = new Truck();
	            truck.setCodigo(this.codigo);
	            truck.setNombre(this.nombre);
	            truck.setDescripcion(this.descripcion);
	                  	
	        	
	        	if(this.validar()){
	        		
	        		dominioService.saveTruck(truck);
	        		this.putAuditoria("Crear Truck", "C", "Creo truck así : - " + truck);
	        		reset();
	        		trucks = dominioService.getTrucks();
	        		abrirConfirmacion();
	        	}
	           
	        } catch (DataAccessException e) {
	            e.printStackTrace();
	        }
	      
	    }

	 
	 public void editarTruck() {
	     try {
	    	
	    	 truckEditar.setCodigo(this.codigo);
	    	 truckEditar.setNombre(this.nombre);
	    	 truckEditar.setDescripcion(this.descripcion);
	     	
	     	
	     	if(this.validarEditar()){
	     		
	     		dominioService.editarTruck(truckEditar);
	     		this.putAuditoria("Editar Truck", "C", "Editar Truck : - " + truckEditar);
	     		trucks = dominioService.getTrucks();
	     		abrirConfirmacionEditar();
	     	}
	        
	     } catch (DataAccessException e) {
	         e.printStackTrace();
	     }
	   
	 }


	public void reset() {
	    this.codigo= null;
	    this.nombre= null;
	    this.descripcion= null;

	}

	public boolean validar(){
		boolean ok = true;
		
			if (!Validador.esLongitudCorrecta(this.codigo, 1, 25)){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Codigo: "," Debe tener entre 1 y 25 caracteres" ));
				ok = false;
			}
			if (!Validador.esLongitudCorrecta(this.nombre, 1, 45)){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nombre: "," Debe tener entre 1 y 45 caracteres" ));
				ok = false;
			}
			if (!Validador.esLongitudCorrecta(this.descripcion, 1, 500)){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Descripción: "," Debe tener entre 1 y 500 caracteres" ));
				ok = false;
			}
			
			
			// validaciones logicas.
			
			
			if(dominioService.getTruckXCodigo(this.codigo).size() > 0){
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errortruckyaexiste"));
				//RequestContext.getCurrentInstance().showMessageInDialog(message);
				PrimeFaces.current().dialog().showMessageDynamic(message);
				ok =  false;
			} 
			
			
				   	

		return ok;
	}


	public boolean validarEditar(){
		boolean ok = true;
		
			if (!Validador.esLongitudCorrecta(this.codigo, 1, 25)){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Codigo: "," Debe tener entre 1 y 25 caracteres" ));
				ok = false;
			}

			if (!Validador.esLongitudCorrecta(this.nombre, 1, 45)){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Codigo: "," Debe tener entre 1 y 25 caracteres" ));
				ok = false;
			}
			if (!Validador.esLongitudCorrecta(this.descripcion, 1, 500)){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Descripción: "," Debe tener entre 1 y 45 caracteres" ));
				ok = false;
			}
			
			
			// validaciones logicas.
			
			if(dominioService.getDominiosXNombre(this.codigo).size() > 1){
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errortruckyaexiste"));
				//RequestContext.getCurrentInstance().showMessageInDialog(message);
				PrimeFaces.current().dialog().showMessageDynamic(message);
				ok =  false;
			} 
					   	

		return ok;
	}



	private void abrirConfirmacion(){
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmacreartruck"));
		//RequestContext.getCurrentInstance().showMessageInDialog(message);
		PrimeFaces.current().dialog().showMessageDynamic(message);
		return;

	}


	private void abrirConfirmacionEditar(){
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmaeditartruck"));
		//RequestContext.getCurrentInstance().showMessageInDialog(message);
		PrimeFaces.current().dialog().showMessageDynamic(message);
		return;

	}

	public String editar (Truck truck){

		 reset();
		 this.codigo = truck.getCodigo();
		 this.nombre = truck.getNombre();
		 this.descripcion= truck.getDescripcion();
		 this.truckEditar = truck;
		 return "editartruck";

		}

	public DominioService getDominioService() {
		return dominioService;
	}

	public void setDominioService(DominioService dominioService) {
		this.dominioService = dominioService;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Truck> getTrucks() {
		return trucks;
	}

	public void setTrucks(List<Truck> trucks) {
		this.trucks = trucks;
	}

	public Truck getTruck() {
		return truck;
	}

	public void setTruck(Truck truck) {
		this.truck = truck;
	}

	public Truck getTruckEditar() {
		return truckEditar;
	}

	public void setTruckEditar(Truck truckEditar) {
		this.truckEditar = truckEditar;
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
		  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("truckMB");
		  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuarioMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("variedadMB");

	   }

	}
