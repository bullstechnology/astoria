package com.bulls.astoria.magedbean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.PrimeFaces;
//import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FileUploadEvent;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bulls.astoria.enumeracion.EnuDominio;
import com.bulls.astoria.persistence.Comision;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.TipoDominio;
import com.bulls.astoria.pojo.ComisionBean;
import com.bulls.astoria.service.DominioService;
import com.bulls.astoria.utils.ArchivoUtils;
import com.bulls.astoria.utils.Convertidor;
import com.bulls.astoria.utils.Validador;


@ManagedBean (name="comisionesMB")
@SessionScoped
public class ComisionesManagedBean extends GeneralManagedBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static final String SUCCESS = "success";
	private static final String ERROR   = "error";

	@ManagedProperty(value="#{DominioService}")
	DominioService dominioService;


	List <Dominio> tiposFlor;
	List <Dominio> paises;
	List<SelectItem> listaTiposFlor;
	List <SelectItem> listaPaisesDom;
	List <ComisionBean> listaComisiones; 
	
	private Integer idPais;
	private Integer idTipoFlor; 
	private String message;
	ResourceBundle bundle ;
 
	

	@PostConstruct
	public void ComisionesManagedBean(){
		
		    borrarSession();
		    dominioService = getDominioService();
		    tiposFlor = dominioService.getDominios(EnuDominio.FLORES.getIdTipoDominio());
		    listaTiposFlor = Convertidor.dominiosToSelectdItems(tiposFlor);
			paises = dominioService.getDominios(EnuDominio.PAISES.getIdTipoDominio());
			listaPaisesDom = Convertidor.dominiosToSelectdItems(paises);
			bundle =  ResourceBundle.getBundle("messages");
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	}
	
	public String getMessage() {
	    return message;
	}

	public void setMessage(String message) {
	    this.message = message;
	}
	
	public void aceptar(){
		if(this.validar()){
		listaComisiones = new ArrayList<ComisionBean>();
		List <Map> lista = dominioService.getProductosTipoFlor(this.idTipoFlor);
		for(Map map:lista){
			Object idflor =   map.get("idtipoflor");
		    Object idvariedad =  null;// map.get("idvariedad");
		    Object idgrado =   map.get("idgrado");
		    Object idtipoflorgrados =   map.get("idtipoflorgrados");
		    Object nombrevariedad =   null;//map.get("nombrevariedad");
		    Object nombreflor =   map.get("nombreflor");
		    Object nombregrado =   map.get("nombregrado");
		    
		    Comision com = dominioService.getComisionProductoPais((Integer)idtipoflorgrados, this.idPais);
		    Integer id=null;
		    Double comision=null;
		    String username=null;
		    Date fecha = null;
		    if(com!= null){
		       id= com.getId();
		       comision=com.getComision();
		       username = com.getUsername();
		       fecha = com.getFecha();
		    }
		    ComisionBean cb = new ComisionBean(id, this.idPais, this.idTipoFlor,
					(Integer) idgrado, (Integer) idtipoflorgrados, (String) nombreflor, (String) nombregrado,
					comision, username,fecha);


		    listaComisiones.add(cb);

		}
		}
	}


    public String editar() {
	     try {
	    	 
	    	 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    	 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    	
         	if(this.validar()){
         		for(ComisionBean cb:listaComisiones){
         			Comision comision = new Comision();
         			comision.setId(cb.getId());
         			comision.setIdpais(cb.getIdPais());
         			comision.setIdtipoflorgrados(cb.getIdtipoflorgrados());
         			comision.setFecha(Calendar.getInstance().getTime());
         			comision.setComision(cb.getComision());
         			comision.setUsername(auth.getName());
         			
         			dominioService.editComision(comision);
         			this.putAuditoria("Crear - Editar Comision", "C", "Edito la comision así : - " + comision.toString());
         		}
	     		reset();
	     		abrirConfirmacionEditar();
	     	}
	        
	     } catch (DataAccessException e) {
	         e.printStackTrace();
	     }
	     return "crearcomisiones";
	   
	 }

	public void reset() {

	}
	

	public boolean validar(){
		boolean ok = true;
			
			if (this.idPais.intValue() == 0){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"País: "," Debe escoger un país" ));
				ok = false;
			}
			if (this.idTipoFlor.intValue() == 0){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Tipo Flor: "," Debe escoger un tipo de flor" ));
				ok = false;
			}
		return ok;
	}


	


	private void abrirConfirmacion(){
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmacrearvariedad"));
		//RequestContext.getCurrentInstance().showMessageInDialog(message);
		PrimeFaces.current().dialog().showMessageDynamic(message);
		return;

	}


	private void abrirConfirmacionEditar(){
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmaeditarcomision"));
		//RequestContext.getCurrentInstance().showMessageInDialog(message);
		PrimeFaces.current().dialog().showMessageDynamic(message);
		return;

	}
	
   public void onCellEdit(CellEditEvent event) {
		
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

	public List<SelectItem> getListaTiposFlor() {
		return listaTiposFlor;
	}

	public void setListaTiposFlor(List<SelectItem> listaTiposFlor) {
		this.listaTiposFlor = listaTiposFlor;
	}

	public List<Dominio> getPaises() {
		return paises;
	}

	public void setPaises(List<Dominio> paises) {
		this.paises = paises;
	}

	public List<SelectItem> getListaPaisesDom() {
		return listaPaisesDom;
	}

	public void setListaPaisesDom(List<SelectItem> listaPaisesDom) {
		this.listaPaisesDom = listaPaisesDom;
	}

	public List<ComisionBean> getListaComisiones() {
		return listaComisiones;
	}

	public void setListaComisiones(List<ComisionBean> listaComisiones) {
		this.listaComisiones = listaComisiones;
	}

	public Integer getIdPais() {
		return idPais;
	}

	public void setIdPais(Integer idPais) {
		this.idPais = idPais;
	}

	public Integer getIdTipoFlor() {
		return idTipoFlor;
	}

	public void setIdTipoFlor(Integer idTipoFlor) {
		this.idTipoFlor = idTipoFlor;
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
		  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("comisionesMB");
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

