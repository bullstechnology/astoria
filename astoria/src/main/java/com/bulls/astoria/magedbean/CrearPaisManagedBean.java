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



@ManagedBean (name="crearPaisMB")
@SessionScoped
public class CrearPaisManagedBean extends GeneralManagedBean implements Serializable{
	
private static final long serialVersionUID = 1L;
private static final String SUCCESS = "success";
private static final String ERROR   = "error";

@ManagedProperty(value="#{DominioService}")
DominioService dominioService;


private String pais ;
private String descripcion = "" ;
private double pesofullbox;
private double descuento = 0.0;
private String codigo; 
List <Dominio> paises;
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
public void CrearPaisManagedBean(){
	
	    borrarSession();
	    dominioService = getDominioService();
	    paises = dominioService.getDominios(EnuDominio.PAISES.getIdTipoDominio());
	    listaPaises = Convertidor.dominiosToSelectdItems(paises);
		bundle =  ResourceBundle.getBundle("messages");

}


 public void crearPais() {
        try {
            TipoDominio tipoDominio = dominioService.getTipoDominio(EnuDominio.PAISES.getIdTipoDominio());
            dominio = new Dominio();
            dominio.setNomcorto(this.pais);
        	dominio.setNomlargo(this.pais);
        	dominio.setDescripcion(this.descripcion);
        	dominio.setTipodominio(tipoDominio);  
        	dominio.setPesofullbox(this.pesofullbox);
        	dominio.setCodigo(this.codigo);
        	dominio.setValor1(this.descuento);
        	
        	
        	if(this.validar()){
        		
        		dominioService.saveDominio(dominio);
        		this.putAuditoria("Crear Pais", "C", "Creo Pais así : - " + dominio.toString());
        		reset();
        		paises = dominioService.getDominios(EnuDominio.PAISES.getIdTipoDominio());
        		abrirConfirmacion();
        	}
           
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
      
    }

 
 public void editarPais() {
     try {
    	
     	dominioEditar.setNomcorto(this.pais);
     	dominioEditar.setNomlargo(this.pais);
     	dominioEditar.setDescripcion(this.descripcion);
     	dominioEditar.setPesofullbox(this.pesofullbox);
     	dominioEditar.setCodigo(this.codigo);
     	dominioEditar.setValor1(this.getDescuento());
     	
     	
     	if(this.validarEditar()){
     		
     		dominioService.editarDominio(dominioEditar);
     		this.putAuditoria("Crear Pais", "C", "Creo Pais así : - " + dominioEditar.toString());
     		paises = dominioService.getDominios(EnuDominio.PAISES.getIdTipoDominio());
     		abrirConfirmacionEditar();
     	}
        
     } catch (DataAccessException e) {
         e.printStackTrace();
     }
   
 }


public void reset() {
    this.pais= null;
    this.descripcion= null;

}

public boolean validar(){
	boolean ok = true;
	
		if (!Validador.esLongitudCorrecta(this.pais, 1, 25)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"País: "," Debe tener entre 1 y 25 caracteres" ));
			ok = false;
		}
	
		if (!Validador.esLongitudCorrecta(this.codigo, 1, 5)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Codigo: "," Debe tener entre 1 y 5 caracteres" ));
			ok = false;
		}
		
		// validaciones logicas.
		
		
		if(dominioService.getDominiosXNombre(this.pais).size() > 0){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errorpaisyaexiste"));
			//RequestContext.getCurrentInstance().showMessageInDialog(message);
			PrimeFaces.current().dialog().showMessageDynamic(message);
			ok =  false;
		} 
		if(dominioService.getDominiosXCodigo(this.codigo, EnuDominio.PAISES.getIdTipoDominio())!= null){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errorcodigoyaexiste"));
			//RequestContext.getCurrentInstance().showMessageInDialog(message);
			PrimeFaces.current().dialog().showMessageDynamic(message);
			ok =  false;
		} 
		
			   	

	return ok;
}


public boolean validarEditar(){
	boolean ok = true;
	
		if (!Validador.esLongitudCorrecta(this.pais, 1, 25)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"País: "," Debe tener entre 1 y 25 caracteres" ));
			ok = false;
		}
		
		
		// validaciones logicas.
		List <Dominio> ListaDominios = dominioService.getDominiosXNombre(this.pais);
		
		
		if(dominioService.getDominiosXNombre(this.pais).size() > 0){
			for (Dominio dom : ListaDominios){
				if(dom.getId().intValue() != dominioEditar.getId().intValue()){
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errorpaisyaexiste"));
					//RequestContext.getCurrentInstance().showMessageInDialog(message);
					PrimeFaces.current().dialog().showMessageDynamic(message);
					ok =  false;
				}
					
			}

		} 
		Dominio dom =  dominioService.getDominiosXCodigo(this.codigo, EnuDominio.PAISES.getIdTipoDominio());
		if(dom != null){
			if(dom.getId().intValue() != dominioEditar.getId().intValue()){
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errorcodigoyaexiste"));
				//RequestContext.getCurrentInstance().showMessageInDialog(message);
				PrimeFaces.current().dialog().showMessageDynamic(message);
				ok =  false;
			}	
		}
				   	

	return ok;
}



private void abrirConfirmacion(){
	
	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmacreapais"));
	//RequestContext.getCurrentInstance().showMessageInDialog(message);
	PrimeFaces.current().dialog().showMessageDynamic(message);
	return;

}


private void abrirConfirmacionEditar(){
	
	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmaeditarpais"));
	//RequestContext.getCurrentInstance().showMessageInDialog(message);
	PrimeFaces.current().dialog().showMessageDynamic(message);
	return;

}

public String editar (Dominio dominio){

	 reset();
	 this.pais = dominio.getNomcorto();
	 this.descripcion= dominio.getDescripcion();
 	 this.pesofullbox = dominio.getPesofullbox();
 	 this.codigo = dominio.getCodigo();
 	 this.descuento = dominio.getValor1();
	 this.dominioEditar = dominio;
	 return "editarpais";

	}

public String getPais() {
	return pais;
}

public void setPais(String pais) {
	this.pais = pais;
}

public String getDescripcion() {
	return descripcion;
}

public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}

public DominioService getDominioService() {
	return dominioService;
}

public void setDominioService(DominioService dominioService) {
	this.dominioService = dominioService;
}

public List<Dominio> getPaises() {
	return paises;
}

public void setPaises(List<Dominio> paises) {
	this.paises = paises;
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

public double getPesofullbox() {
	return pesofullbox;
}

public void setPesofullbox(double pesofullbox) {
	this.pesofullbox = pesofullbox;
}

public String getCodigo() {
	return codigo;
}

public void setCodigo(String codigo) {
	this.codigo = codigo;
}



public double getDescuento() {
	return descuento;
}

public void setDescuento(double descuento) {
	this.descuento = descuento;
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
	  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("crearPaisMB");
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
