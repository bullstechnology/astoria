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
import org.springframework.dao.DataAccessException;

import com.bulls.astoria.enumeracion.EnuDominio;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.TipoDominio;
import com.bulls.astoria.service.DominioService;
import com.bulls.astoria.utils.Validador;




@ManagedBean (name="colorMB")
@SessionScoped
public class ColorManagedBean extends GeneralManagedBean implements Serializable{
	
private static final long serialVersionUID = 1L;
private static final String SUCCESS = "success";
private static final String ERROR   = "error";

@ManagedProperty(value="#{DominioService}")
DominioService dominioService;


private String tipo ;
private String descripcion ;
List <Dominio> tiposColor;
private Dominio dominio;
private Dominio dominioEditar;

private String message;
ResourceBundle bundle ;

 
public String getMessage() {
    return message;
}

public void setMessage(String message) {
    this.message = message;
}

@PostConstruct
public void CrearFlorManagedBean(){
	
	    borrarSession();
	    dominioService = getDominioService();
	    tiposColor = dominioService.getDominios(EnuDominio.COLORES.getIdTipoDominio());
		bundle =  ResourceBundle.getBundle("messages");

}


 public void crearColor() {
        try {
            TipoDominio tipoDominio = dominioService.getTipoDominio(EnuDominio.COLORES.getIdTipoDominio());
            dominio = new Dominio();
            dominio.setNomcorto(this.tipo);
        	dominio.setNomlargo(this.tipo);
        	dominio.setDescripcion(this.descripcion);
        	dominio.setTipodominio(tipoDominio);   
        	
        	
        	if(this.validar()){
        		
        		dominioService.saveDominio(dominio);
        		this.putAuditoria("Crear Color", "C", "Creo el color así : - " + dominio.toString());
        		reset();
        		tiposColor = dominioService.getDominios(EnuDominio.COLORES.getIdTipoDominio());
        		abrirConfirmacion();
        	}
           
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
      
    }

 
 public void editarColor() {
     try {
    	
     	dominioEditar.setNomcorto(this.tipo);
     	dominioEditar.setNomlargo(this.tipo);
     	dominioEditar.setDescripcion(this.descripcion);
     	
     	
     	if(this.validarEditar()){
     		
     		dominioService.editarDominio(dominioEditar);
     		this.putAuditoria("Editar Color", "U", "Edito el color así : - " + dominioEditar.toString());
     		tiposColor = dominioService.getDominios(EnuDominio.COLORES.getIdTipoDominio());
     		abrirConfirmacionEditar();
     	}
        
     } catch (DataAccessException e) {
         e.printStackTrace();
     }
   
 }


public void reset() {
    this.tipo= null;
    this.descripcion= null;

}

public boolean validar(){
	boolean ok = true;
	
		if (!Validador.esLongitudCorrecta(this.tipo, 1, 45)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Color: "," Debe tener entre 1 y 45 caracteres" ));
			ok = false;
		}
		if (!Validador.esLongitudCorrecta(this.descripcion,0, 45)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Descripción: "," Debe tener entre 0 y 45 caracteres" ));
			ok = false;
		}
		
		
		// validaciones logicas.
		
		
		if(dominioService.getDominiosXNombre(this.tipo).size() > 0){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errorcoloryaexiste"));
			//RequestContext.getCurrentInstance().showMessageInDialog(message);
			PrimeFaces.current().dialog().showMessageDynamic(message);
			ok =  false;
		} 
		
		
			   	

	return ok;
}


public boolean validarEditar(){
	boolean ok = true;
	
		if (!Validador.esLongitudCorrecta(this.tipo, 1, 25)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Color: "," Debe tener entre 1 y 25 caracteres" ));
			ok = false;
		}
		if (!Validador.esLongitudCorrecta(this.descripcion, 0, 45)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Descripción: "," Debe tener entre 0 y 45 caracteres" ));
			ok = false;
		}
		
		
		// validaciones logicas.
		
		if(dominioService.getDominiosXNombre(this.tipo).size() > 1){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errorcoloryaexiste"));
			//RequestContext.getCurrentInstance().showMessageInDialog(message);
			PrimeFaces.current().dialog().showMessageDynamic(message);
			ok =  false;
		} 
				   	

	return ok;
}



private void abrirConfirmacion(){
	
	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmacreacolor"));
	//RequestContext.getCurrentInstance().showMessageInDialog(message);
	PrimeFaces.current().dialog().showMessageDynamic(message);
	return;

}


private void abrirConfirmacionEditar(){
	
	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmaeditarcolor"));
	//RequestContext.getCurrentInstance().showMessageInDialog(message);
	PrimeFaces.current().dialog().showMessageDynamic(message);
	return;

}

public String editar (Dominio dominio){

	 reset();
	 this.tipo = dominio.getNomcorto();
	 this.descripcion= dominio.getDescripcion();
	 this.dominioEditar = dominio;
	 return "editarcolor";

	}

public DominioService getDominioService() {
	return dominioService;
}

public void setDominioService(DominioService dominioService) {
	this.dominioService = dominioService;
}

public String getTipo() {
	return tipo;
}

public void setTipo(String tipo) {
	this.tipo = tipo;
}

public String getDescripcion() {
	return descripcion;
}

public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
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



public List<Dominio> getTiposColor() {
	return tiposColor;
}

public void setTiposColor(List<Dominio> tiposColor) {
	this.tiposColor = tiposColor;
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
	  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("colorMB");
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
	  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuarioMB");
	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("variedadMB");

   }




}
