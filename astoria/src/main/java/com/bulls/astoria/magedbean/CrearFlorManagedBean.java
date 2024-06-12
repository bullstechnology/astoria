package com.bulls.astoria.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.PrimeFaces;
//import org.primefaces.context.RequestContext;
import org.springframework.dao.DataAccessException;

import com.bulls.astoria.enumeracion.EnuDominio;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.Persona;
import com.bulls.astoria.persistence.ProductoGrados;
import com.bulls.astoria.persistence.Role;
import com.bulls.astoria.persistence.TipoDominio;
import com.bulls.astoria.persistence.User;
import com.bulls.astoria.service.DominioService;
import com.bulls.astoria.service.PersonaService;
import com.bulls.astoria.service.RoleService;
import com.bulls.astoria.service.UserService;
import com.bulls.astoria.utils.Convertidor;
import com.bulls.astoria.utils.Validador;


@ManagedBean (name="crearFlorMB")
@SessionScoped
public class CrearFlorManagedBean extends GeneralManagedBean implements Serializable{
	
private static final long serialVersionUID = 1L;
private static final String SUCCESS = "success";
private static final String ERROR   = "error";

@ManagedProperty(value="#{DominioService}")
DominioService dominioService;


private String tipo ;
private String descripcion ;
private String codigo ;
List <Dominio> tiposFlor;
private Dominio dominio;
private Dominio dominioEditar;
List<SelectItem> listaTiposFlor;

List<Dominio> grados;
List<Dominio> gradosSelected;
List<String> gradosSelectedAnterior;


List<SelectItem> listaGrados;
List<Dominio> listaGradosDom;
private Integer idGrado;

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
	    tiposFlor = dominioService.getDominios(EnuDominio.FLORES.getIdTipoDominio());
	    listaTiposFlor = Convertidor.dominiosToSelectdItems(tiposFlor);
	    listaGradosDom  = dominioService.getDominios(EnuDominio.GRADOS.getIdTipoDominio());
	    listaGrados = Convertidor.dominiosToSelectdItems(listaGradosDom);
		bundle =  ResourceBundle.getBundle("messages");
		grados = dominioService.getDominios(EnuDominio.GRADOS.getIdTipoDominio());

}


 public void crearFlor() {
        try {
            TipoDominio tipoDominio = dominioService.getTipoDominio(EnuDominio.FLORES.getIdTipoDominio());
            dominio = new Dominio();
            dominio.setNomcorto(this.tipo);
        	dominio.setNomlargo(this.tipo);
        	dominio.setCodigo(this.codigo);
        	dominio.setDescripcion(this.descripcion);
        	dominio.setTipodominio(tipoDominio);   
        	
        	
        	if(this.validar()){
        		
        		dominioService.saveTipoFlor(dominio,gradosSelected);
        		this.putAuditoria("Crear Tipo Flor", "C", "Creo flor así : - " + dominio.toString());
        		reset();
        		tiposFlor = dominioService.getDominios(EnuDominio.FLORES.getIdTipoDominio());
        		gradosSelected = new ArrayList<Dominio> ();
        		grados = dominioService.getDominios(EnuDominio.GRADOS.getIdTipoDominio());
        		abrirConfirmacion();
        	}
           
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
      
    }

 
 public String editarFlor() {
     try {
    	
     	dominioEditar.setNomcorto(this.tipo);
     	dominioEditar.setNomlargo(this.tipo);
     	dominioEditar.setDescripcion(this.descripcion);
     	dominioEditar.setCodigo(this.codigo);
     	
     	
     	if(this.validarEditar()){
     		
     		dominioService.editarTipoFlor(dominioEditar,gradosSelected);
     		this.putAuditoria("Editar Flor", "U", "Edito Flor así : - " + dominioEditar.toString());
     		tiposFlor = dominioService.getDominios(EnuDominio.FLORES.getIdTipoDominio());
     		gradosSelected = new ArrayList<Dominio> ();
    		grados = dominioService.getDominios(EnuDominio.GRADOS.getIdTipoDominio());
    		listaTiposFlor = Convertidor.dominiosToSelectdItems(tiposFlor);
    		reset();
     		abrirConfirmacionEditar();
     	}
        
     } catch (DataAccessException e) {
         e.printStackTrace();
     }
     
     return "creartipoflor";
   
 }
 
 public String agregarGrado() {
     try {
    	
     	   		
     		dominioService.agregarGrado(dominioEditar.getId(),this.idGrado);
     		this.putAuditoria("Agregar Grado", "U", "o grado así : - " + dominioEditar.toString()+  "  " +this.idGrado  );
     		tiposFlor = dominioService.getDominios(EnuDominio.FLORES.getIdTipoDominio());
    		listaTiposFlor = Convertidor.dominiosToSelectdItems(tiposFlor);
    		reset();
     		abrirConfirmacionGrado();
     } catch (DataAccessException e) {
         e.printStackTrace();
     }
     
     return "creartipoflor";
   
 }


public void reset() {
    this.tipo= null;
    this.descripcion= null;
    this.codigo=null;

}

public boolean validar(){
	boolean ok = true;
	
		if (!Validador.esLongitudCorrecta(this.tipo, 1, 25)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Tipo de Flor: "," Debe tener entre 1 y 25 caracteres" ));
			ok = false;
		}
		if (!Validador.esLongitudCorrecta(this.descripcion, 1, 45)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Descripción: "," Debe tener entre 1 y 45 caracteres" ));
			ok = false;
		}
		if (!Validador.esLongitudCorrecta(this.codigo, 1, 3)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Codigo: "," Debe tener entre 1 y 3 caracteres" ));
			ok = false;
		}
		
		
		// validaciones logicas.
		
		
		if(dominioService.getDominiosXNombre(this.tipo).size() > 0){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errortipofloryaexiste"));
			//RequestContext.getCurrentInstance().showMessageInDialog(message);
			PrimeFaces.current().dialog().showMessageDynamic(message);
			ok =  false;
		}
		if(dominioService.getDominiosXCodigo(this.codigo,EnuDominio.FLORES.getIdTipoDominio())!= null){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errorcodigoyaexiste"));
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
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Tipo de Flor: "," Debe tener entre 1 y 25 caracteres" ));
			ok = false;
		}
		if (!Validador.esLongitudCorrecta(this.descripcion, 1, 45)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Descripción: "," Debe tener entre 1 y 45 caracteres" ));
			ok = false;
		}
		if (!Validador.esLongitudCorrecta(this.codigo, 1, 3)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Codigo: "," Debe tener entre 1 y 3 caracteres" ));
			ok = false;
		}
		
		
		// validaciones logicas.
		
		if(dominioService.getDominiosXNombre(this.tipo).size() > 1){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errortipofloryaexiste"));
			//RequestContext.getCurrentInstance().showMessageInDialog(message);
			PrimeFaces.current().dialog().showMessageDynamic(message);
			ok =  false;
		} 
		
		if(dominioService.getDominiosXCodigoId(this.codigo,dominioEditar.getId(),EnuDominio.FLORES.getIdTipoDominio())!= null){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errorcodigoyaexiste"));
			//RequestContext.getCurrentInstance().showMessageInDialog(message);
			PrimeFaces.current().dialog().showMessageDynamic(message);
			ok =  false;
		}
		
		//validar consistencia grados productos
	/*	for (String gradoanterior : gradosSelectedAnterior){
			boolean okexiste=false;
			boolean okproducto=false;
			for (Dominio gradoactualizado : gradosSelected){
				if(Integer.parseInt(gradoanterior) == gradoactualizado.getId().intValue()){
					okexiste=true;
				}
			}
			if(!okexiste){
				//preguntar se hay un producto creado ya para este grado
				List <Dominio> variedadesflor = dominioService.getDominiosXPadre(dominioEditar.getId());
				for (Dominio variedad : variedadesflor){
					if(dominioService.getProductoVariedadGrado(variedad.getId(), dominioService.getDominio(Integer.parseInt(gradoanterior)).getId())!=null){
						//si hay un producto
						okproducto =true;
					}
				}
				if(okproducto){
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("erroryaexisteunproducto"));
					RequestContext.getCurrentInstance().showMessageInDialog(message);
					ok =  false;
				}
			}
		}
				   	
   */
	return ok;
}



private void abrirConfirmacion(){
	
	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmacreaflor"));
	//RequestContext.getCurrentInstance().showMessageInDialog(message);
	PrimeFaces.current().dialog().showMessageDynamic(message);
	return;

}


private void abrirConfirmacionEditar(){
	
	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmaeditarflor"));
	//RequestContext.getCurrentInstance().showMessageInDialog(message);
	PrimeFaces.current().dialog().showMessageDynamic(message);
	return;

}

private void abrirConfirmacionGrado(){
	
	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmaagregargrado"));
	//RequestContext.getCurrentInstance().showMessageInDialog(message);
	PrimeFaces.current().dialog().showMessageDynamic(message);
	return;

}

public String editar (Dominio dominio){

	 reset();
	 this.tipo = dominio.getNomcorto();
	 this.descripcion= dominio.getDescripcion();
	 this.dominioEditar = dominio;
	 this.codigo=dominio.getCodigo();
	 //lista de grados que ya tiene
	 List <ProductoGrados> lg = dominioService.getGradosFlor(dominio.getId());
	 gradosSelectedAnterior = new ArrayList <String>();
	 gradosSelected = new ArrayList <Dominio>();
	 for(ProductoGrados grado:lg){
		 Dominio dom = dominioService.getDominio(grado.getIdgrado());
		 gradosSelectedAnterior.add(dom.getNomcorto());
	 }
	 return "editartipoflor";

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

public DominioService getDominioService() {
	return dominioService;
}

public void setDominioService(DominioService dominioService) {
	this.dominioService = dominioService;
}

public List<Dominio> getTiposFlor() {
	return tiposFlor;
}

public void setTiposFlor(List<Dominio> tiposFlor) {
	this.tiposFlor = tiposFlor;
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

public List<SelectItem> getListaTiposFlor() {
	return listaTiposFlor;
}

public void setListaTiposFlor(List<SelectItem> listaTiposFlor) {
	this.listaTiposFlor = listaTiposFlor;
}

public List<Dominio> getGrados() {
	return grados;
}

public void setGrados(List<Dominio> grados) {
	this.grados = grados;
}

public List<Dominio> getGradosSelected() {
	return gradosSelected;
}

public void setGradosSelected(List<Dominio> gradosSelected) {
	this.gradosSelected = gradosSelected;
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

public List<String> getGradosSelectedAnterior() {
	return gradosSelectedAnterior;
}

public void setGradosSelectedAnterior(List<String> gradosSelectedAnterior) {
	this.gradosSelectedAnterior = gradosSelectedAnterior;
}

public String getCodigo() {
	return codigo;
}

public void setCodigo(String codigo) {
	this.codigo = codigo;
}


public List<SelectItem> getListaGrados() {
	return listaGrados;
}

public void setListaGrados(List<SelectItem> listaGrados) {
	this.listaGrados = listaGrados;
}

public List<Dominio> getListaGradosDom() {
	return listaGradosDom;
}

public void setListaGradosDom(List<Dominio> listaGradosDom) {
	this.listaGradosDom = listaGradosDom;
}

public Integer getIdGrado() {
	return idGrado;
}

public void setIdGrado(Integer idGrado) {
	this.idGrado = idGrado;
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
	  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("crearFlorMB");
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
