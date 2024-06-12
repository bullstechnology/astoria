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

//import org.primefaces.context.RequestContext;

import com.bulls.astoria.enumeracion.EnuDominio;
import com.bulls.astoria.persistence.Aerolinea;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.Empresa;
import com.bulls.astoria.persistence.Proveedor;
import com.bulls.astoria.service.DominioService;
import com.bulls.astoria.service.PlantacionService;
import com.bulls.astoria.utils.Convertidor;
import com.bulls.astoria.utils.Validador;



@ManagedBean (name="proveedorMB")
@SessionScoped
public class ProveedorManagedBean extends GeneralManagedBean implements Serializable{
	
	private Integer id;
	private String nombre;
	private Double cupo;
	private Double saldo;
	private List <Proveedor> proveedores;
	
	private Empresa empresa;
	Proveedor proveedoreditar = null;
	transient ResourceBundle bundle ;
	
	
	@ManagedProperty(value="#{PlantacionService}")
	PlantacionService plantacionService;
	
	
	
	@PostConstruct
	public void ProveedorManagedBean(){
		proveedoreditar =  new Proveedor();
		borrarSession();
		bundle =  ResourceBundle.getBundle("messages");
		proveedores = plantacionService.getProveedores();
	}
	
	
	
	
	public String crearProveedor(){

		Proveedor proveedor =  new Proveedor(); 
		if(validarCrear()){
			Proveedor proveedor2 =  new Proveedor(); 
			proveedor2.setCupo(this.cupo);
			proveedor2.setNombre(this.nombre);
			proveedor2.setSaldo(this.saldo);
			
			plantacionService.crearProveedor(proveedor2);
			proveedores = plantacionService.getProveedores();
			this.putAuditoria("Crear", "C", "Creo Proveedor así : - " + proveedor2.toString());
			abrirConfirmacion();
			
		}
		return null;
	}
	
	public String editar(Proveedor proveedoredit){

			proveedoreditar =  new Proveedor(); 
			proveedoreditar.setIdproveedor(proveedoredit.getIdproveedor());
			proveedoreditar.setCupo(proveedoredit.getCupo());
			proveedoreditar.setNombre(proveedoredit.getNombre());
			proveedoreditar.setSaldo(proveedoredit.getSaldo());
			
			this.id = proveedoreditar.getIdproveedor();
			this.nombre  = proveedoreditar.getNombre();
			this.saldo   = proveedoreditar.getSaldo();
			this.cupo = proveedoreditar.getCupo();

		return "editarproveedor";
	}
	
	public String editarProveedor(){
		if(validarEditar()){
			proveedoreditar.setIdproveedor(this.id);
			proveedoreditar.setCupo(this.cupo);
			proveedoreditar.setNombre(this.nombre);
			proveedoreditar.setSaldo(this.saldo);
			
			plantacionService.editarProveedor(proveedoreditar);
			this.putAuditoria("Editar", "U", "Edito Proveedor así : - " + proveedoreditar.toString());
			abrirConfirmacioneditar();
		}
		proveedores = plantacionService.getProveedores();
		return null;
	}
	
	public void reset() {
	    this.nombre= null;
	    this.cupo= null;
	    this.saldo= null;

	}
	
	public boolean validarCrear(){
		  boolean ok = true;
		  
		  if(this.nombre.equalsIgnoreCase("")){
     		  ok = false;
			  FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nombre","Debe digitar un nombre para el proveedor" ));
			  
		  }
		 
		  
		  return ok;
	  }
	
	public boolean validarEditar(){
		  boolean ok = true;
		  
		  if(this.nombre.equalsIgnoreCase("")){
   		  ok = false;
			  FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Debe digitar un nombre para el proveedor","Debe digitar un nombre para el proveedor" ));
			  
		  }
		 
		  
		  return ok;
	  }
	
	private void abrirConfirmacion(){
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmacrearproveedor"));
		//RequestContext.getCurrentInstance().showMessageInDialog(message);
		PrimeFaces.current().dialog().showMessageDynamic(message);
		return;

    }
	
private void abrirConfirmacioneditar(){
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmaeditarproveedor"));
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



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	


	public Empresa getEmpresa() {
		return empresa;
	}



	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}



	public ResourceBundle getBundle() {
		return bundle;
	}



	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}



	public Double getCupo() {
		return cupo;
	}




	public void setCupo(Double cupo) {
		this.cupo = cupo;
	}




	public Double getSaldo() {
		return saldo;
	}




	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}




	public PlantacionService getPlantacionService() {
		return plantacionService;
	}




	public void setPlantacionService(PlantacionService plantacionService) {
		this.plantacionService = plantacionService;
	}




	public List<Proveedor> getProveedores() {
		return proveedores;
	}




	public void setProveedores(List<Proveedor> proveedores) {
		this.proveedores = proveedores;
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
