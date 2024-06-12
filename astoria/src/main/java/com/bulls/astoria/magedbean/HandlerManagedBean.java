package com.bulls.astoria.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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

import com.bulls.astoria.persistence.Handler;
import com.bulls.astoria.persistence.HandlerTruck;
import com.bulls.astoria.persistence.Role;
import com.bulls.astoria.persistence.Truck;
import com.bulls.astoria.service.DominioService;
import com.bulls.astoria.utils.Convertidor;
import com.bulls.astoria.utils.Validador;

@ManagedBean (name="handlerMB")
@SessionScoped
public class HandlerManagedBean extends GeneralManagedBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final String SUCCESS = "success";
	private static final String ERROR   = "error";

	@ManagedProperty(value="#{DominioService}")
	DominioService dominioService;

    private Integer id;
	private String codigo ;
	private String nombre;
	private String descripcion ;
	List <Handler> handlers;
	List <Truck> trucks;
	List <Truck> listaTrucks;
	List <SelectItem> listaHandlersItem;
	List <SelectItem> listaTrucksItem;
	private Handler handler;
	private Handler handlerEditar;
	private Integer idHandler; 
	private Integer idTruck; 
	private Double preculincli=0.0;
	private Double preculinast=0.0;
	private Double saldofinal=0.0;

	private String message;
	ResourceBundle bundle ;

	 
	public String getMessage() {
	    return message;
	}

	public void setMessage(String message) {
	    this.message = message;
	}

	@PostConstruct
	public void CrearHandlerManagedBean(){
		
		    borrarSession();
		    dominioService = getDominioService();
		    handlers = dominioService.getHandlers();
		    trucks = dominioService.getTrucks();
		    listaHandlersItem = handlersToSelectdItems(handlers);
		    listaTrucksItem = trucksToSelectdItems(trucks);
			bundle =  ResourceBundle.getBundle("messages");

	}


	 public void crearHandler() {
	        try {
	            
	            handler = new Handler();
	            handler.setCodigo(this.codigo);
	            handler.setNombre(this.nombre);
	            handler.setDescripcion(this.descripcion);
	            handler.setPreculinast(this.preculinast);
	            handler.setPreculincli(this.preculincli);  
	            handler.setSaldofinal(0.0);
	        	
	        	if(this.validar()){
	        		
	        		dominioService.saveHandler(handler);
	        		this.putAuditoria("Crear Handler", "C", "Creo Handler así : - " + handler.toString());
	        		reset();
	        		handlers = dominioService.getHandlers();
	        		abrirConfirmacion();
	        	}
	           
	        } catch (DataAccessException e) {
	            e.printStackTrace();
	        }
	      
	    }

	 
	 public void editarHandler() {
	     try {
	    	
	    	 handlerEditar.setCodigo(this.codigo);
	    	 handlerEditar.setNombre(this.nombre);
	    	 handlerEditar.setDescripcion(this.descripcion);
	    	 handlerEditar.setPreculinast(this.preculinast);
	    	 handlerEditar.setPreculincli(this.preculincli);
	    	 handlerEditar.setSaldofinal(this.saldofinal);
	     	
	     	if(this.validarEditar()){
	     		
	     		dominioService.editarHandler(handlerEditar);
	     		this.putAuditoria("Editar handler", "U", "Edito handler así : - " + handlerEditar.toString());
	     		handlers = dominioService.getHandlers();
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
	    this.preculinast=0.0;
	    this.preculincli=0.0;

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
			
			
			if(dominioService.getHandlerXCodigo(this.codigo).size() > 0){
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errorhandleryaexiste"));
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
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errorhandleryaexiste"));
				//RequestContext.getCurrentInstance().showMessageInDialog(message);
				PrimeFaces.current().dialog().showMessageDynamic(message);
				ok =  false;
			} 
					   	

		return ok;
	}
	
	public boolean validarCrearHandlerTruck(){
		boolean ok = true;
		
			// validaciones logicas.
			
			if(dominioService.getHandlerTruck(this.idHandler,this.idTruck).size() > 0){
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errorhandlertruckyaexiste"));
				//RequestContext.getCurrentInstance().showMessageInDialog(message);
				PrimeFaces.current().dialog().showMessageDynamic(message);
				ok =  false;
			} 
					   	

		return ok;
	}
	
	public void getTrucks(){
		
		Handler handleraux = new Handler();
		handleraux = dominioService.getHandlerXId(this.idHandler);
	    if (handleraux!= null){
	    	listaTrucks = new ArrayList<Truck>(handleraux.getTrucks());
	    }else {
	    	listaTrucks = null;
	    }
		
	}
	
   public void crearHandlerTruck(){
	   
	   if(validarCrearHandlerTruck()){
		
		HandlerTruck ht = new HandlerTruck();
		ht.setIdhandler(this.idHandler);
		ht.setIdtruck(this.idTruck);
		dominioService.saveHandlerTruck(ht);
		 dominioService = getDominioService();
		    handlers = dominioService.getHandlers();
		    trucks = dominioService.getTrucks();
		    listaHandlersItem = handlersToSelectdItems(handlers);
		    listaTrucksItem = trucksToSelectdItems(trucks);
		    this.getTrucks();
		    this.abrirConfirmacionHandlertruck();
	   }	    
	}
   
   public void eliminarHandlerTruck(Truck truck){
		
	   
	   HandlerTruck ht = dominioService.getHandlerTruck(this.idHandler,truck.getId()).get(0);
	   
		/*HandlerTruck ht = new HandlerTruck();
		ht.setIdhandler(this.idHandler);
		ht.setIdtruck(truck.getId());*/
		dominioService.deleteHandlerTruck(ht);
		 dominioService = getDominioService();
		    handlers = dominioService.getHandlers();
		    trucks = dominioService.getTrucks();
		    listaHandlersItem = handlersToSelectdItems(handlers);
		    listaTrucksItem = trucksToSelectdItems(trucks);
		    this.getTrucks();
		    this.abrirConfirmacionEliminarHandlertruck();
	}


   public Double getSaldoHandler(Handler handler){
		Double saldo = 0.0;
		saldo = dominioService.getSaldo(handler.getId(), "H","A");
		if(saldo==null){
			saldo = 0.0;
		}
		return saldo;
	}

	private void abrirConfirmacion(){
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmacrearhandler"));
		//RequestContext.getCurrentInstance().showMessageInDialog(message);
		PrimeFaces.current().dialog().showMessageDynamic(message);
		return;

	}


	private void abrirConfirmacionEditar(){
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmaeditarhandler"));
		//RequestContext.getCurrentInstance().showMessageInDialog(message);
		PrimeFaces.current().dialog().showMessageDynamic(message);
		return;

	}
	
    private void abrirConfirmacionHandlertruck(){
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmacrearhandlertruck"));
		//RequestContext.getCurrentInstance().showMessageInDialog(message);
		PrimeFaces.current().dialog().showMessageDynamic(message);
		return;

	}

    private void abrirConfirmacionEliminarHandlertruck(){
	
	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmaeliminarhandlertruck"));
	//RequestContext.getCurrentInstance().showMessageInDialog(message);
	PrimeFaces.current().dialog().showMessageDynamic(message);
	return;

    }

	public String editar (Handler handler){

		 reset();
		 this.codigo = handler.getCodigo();
		 this.nombre = handler.getNombre();
		 this.descripcion= handler.getDescripcion();
		 this.preculinast=handler.getPreculinast();
		 this.preculincli=handler.getPreculincli();
		 this.saldofinal = handler.getSaldofinal();
		 this.handlerEditar = handler;
		 return "editarhandler";

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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Handler> getHandlers() {
		return handlers;
	}

	public void setHandlers(List<Handler> handlers) {
		this.handlers = handlers;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Handler getHandlerEditar() {
		return handlerEditar;
	}

	public void setHandlerEditar(Handler handlerEditar) {
		this.handlerEditar = handlerEditar;
	}

	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	public List<Truck> getListaTrucks() {
		return listaTrucks;
	}

	public void setListaTrucks(List<Truck> listaTrucks) {
		this.listaTrucks = listaTrucks;
	}

	public Integer getIdHandler() {
		return idHandler;
	}

	public void setIdHandler(Integer idHandler) {
		this.idHandler = idHandler;
	}

	public Integer getIdTruck() {
		return idTruck;
	}

	public void setIdTruck(Integer idTruck) {
		this.idTruck = idTruck;
	}

	public void setTrucks(List<Truck> trucks) {
		this.trucks = trucks;
	}
	
	

	public List<SelectItem> getListaHandlersItem() {
		return listaHandlersItem;
	}

	public void setListaHandlersItem(List<SelectItem> listaHandlersItem) {
		this.listaHandlersItem = listaHandlersItem;
	}

	public List<SelectItem> getListaTrucksItem() {
		return listaTrucksItem;
	}

	public void setListaTrucksItem(List<SelectItem> listaTrucksItem) {
		this.listaTrucksItem = listaTrucksItem;
	}

	public static List<SelectItem> trucksToSelectdItems(List <Truck> lista){
		
		List<SelectItem> generico = new ArrayList<SelectItem>();
		generico = new ArrayList<SelectItem>();
		for(Truck truck : lista){
		   generico.add(new SelectItem(truck.getId(), truck.getCodigo() + "-" + truck.getNombre()));
		}
		return generico;
	}
	public static List<SelectItem> handlersToSelectdItems(List <Handler> lista){
		
		List<SelectItem> generico = new ArrayList<SelectItem>();
		generico = new ArrayList<SelectItem>();
		for(Handler handler : lista){
		   generico.add(new SelectItem(handler.getId(), handler.getCodigo() + "-" + handler.getNombre()));
		}
		return generico;
	}
	
	
	
	public Double getPreculincli() {
		return preculincli;
	}

	public void setPreculincli(Double preculincli) {
		this.preculincli = preculincli;
	}

	public Double getPreculinast() {
		return preculinast;
	}

	public void setPreculinast(Double preculinast) {
		this.preculinast = preculinast;
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
		  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("handlerMB");
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

