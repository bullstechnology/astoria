package com.bulls.astoria.magedbean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.springframework.dao.DataAccessException;

import com.bulls.astoria.enumeracion.EnuDominio;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.TipoDominio;
import com.bulls.astoria.service.DominioService;
import com.bulls.astoria.utils.ArchivoUtils;
import com.bulls.astoria.utils.Convertidor;
import com.bulls.astoria.utils.Validador;


@ManagedBean (name="variedadMB")
@SessionScoped
public class VariedadManagedBean extends GeneralManagedBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static final String SUCCESS = "success";
	private static final String ERROR   = "error";

	@ManagedProperty(value="#{DominioService}")
	DominioService dominioService;


	private String tipo ;
	private String descripcion ;
	private Integer padre;
	private String padre2;
	private String codigo;
	List <Dominio> tiposFlor;
	List <Dominio> listaVariedades;
	List <com.bulls.astoria.pojo.Dominio> listaVariedades2;
	List <Dominio> colores;
	private Dominio dominio;
	private Dominio dominioEditar;
	List<SelectItem> listaTiposFlor;
	List <SelectItem> coloresSelect;
	
	private String foto;
	private InputStream fotois;
	private String rutafoto;
	private String extencion; 
	private String selectedFoto;
	private Dominio selectedDominio;
	private com.bulls.astoria.pojo.Dominio selectedDominio2;
	private Integer idColor;

	private String message;
	ResourceBundle bundle;
	ResourceBundle bundleParam ;
	private String city;  
	
	
	private Map<String,String> cities = new HashMap<String, String>(); 
	
	//puestas despues para manejo de fotos cambio de version.  30 marzo de 2024
	
	private UploadedFile file;
	public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public void upload() {
        if (file != null) {
            FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
	// fin puestas despues 

	 
	public String getMessage() {
	    return message;
	}

	public void setMessage(String message) {
	    this.message = message;
	}

	@PostConstruct
	public void VariedadManagedBean(){
		
		    borrarSession();
		    dominioService = getDominioService();
		    tiposFlor = dominioService.getDominios(EnuDominio.FLORES.getIdTipoDominio());
		    listaTiposFlor = Convertidor.dominiosToSelectdItems(tiposFlor);
		    colores = dominioService.getDominios(EnuDominio.COLORES.getIdTipoDominio());
			coloresSelect = Convertidor.dominiosToSelectdItems(colores);
		    
			bundle =  ResourceBundle.getBundle("messages");
			bundleParam =  ResourceBundle.getBundle("parametros");

	}
	
	public void verimagen(com.bulls.astoria.pojo.Dominio dominio){
		this.selectedDominio2  = dominio;
		return;
	}


	 public void crearVariedad() {
	        try {
	            TipoDominio tipoDominio = dominioService.getTipoDominio(EnuDominio.VARIEDADES.getIdTipoDominio());
	            dominio = new Dominio();
	            dominio.setNomcorto(this.tipo);
	        	dominio.setNomlargo(this.tipo);
	        	dominio.setDescripcion(this.descripcion);
	        	dominio.setTipodominio(tipoDominio);   
	        	dominio.setDominiopadre(this.padre);
	        	dominio.setIdcolor(this.idColor);
	        	dominio.setCodigo(this.codigo);
	        	
	        	if(this.validar()){
	        		dominioService.saveVariedad(dominio,fotois,bundleParam.getString("carpetafotos"),this.extencion);
	        		this.putAuditoria("Crear Variedad", "C", "Creo variedad así : - " + dominio.toString());
	        		reset();
	        		tiposFlor = dominioService.getDominios(EnuDominio.FLORES.getIdTipoDominio());
	        		listaTiposFlor = Convertidor.dominiosToSelectdItems(tiposFlor);
	        		abrirConfirmacion();
	        		
	        	}
	           
	        } catch (DataAccessException e) {
	            e.printStackTrace();
	        }
	      
	    }

	 
	 public String editarVariedad() {
	     try {
	    	
	     	dominioEditar.setNomcorto(this.tipo);
	     	dominioEditar.setNomlargo(this.tipo);
	     	dominioEditar.setDescripcion(this.descripcion);
	     	dominioEditar.setIdcolor(this.idColor);
	     	dominioEditar.setCodigo(this.codigo);
    	
	     	
	     	if(this.validarEditar()){
	     		
	     		//dominioService.editarDominio(dominioEditar);
	     		dominioService.editarVariedad(dominioEditar,fotois,bundleParam.getString("carpetafotos"),this.extencion);
	     		this.putAuditoria("Editar Variedad", "U", "Edito variedad así : - " + dominioEditar.toString());
	     		tiposFlor = dominioService.getDominios(EnuDominio.FLORES.getIdTipoDominio());
	     		listaTiposFlor = Convertidor.dominiosToSelectdItems(tiposFlor);
	     		reset();
	     		abrirConfirmacionEditar();
	     	}
	        
	     } catch (DataAccessException e) {
	         e.printStackTrace();
	     }
	     return "crearvariedad";
	   
	 }

    public void getVariedades(){
    	
    	System.out.println("en get Variedades");
    	listaVariedades = dominioService.getDominiosXPadre(this.padre);
    	listaVariedades2 = new ArrayList <com.bulls.astoria.pojo.Dominio> ();
    	for(Dominio dom : listaVariedades){
    		com.bulls.astoria.pojo.Dominio dompojo = new com.bulls.astoria.pojo.Dominio();
    		dompojo.setId(dom.getId());
    		dompojo.setUrl(dom.getUrl());
    		dompojo.setDescripcion(dom.getDescripcion());
    		dompojo.setNomcorto(dom.getNomcorto());
    		dompojo.setColor(dominioService.getDominio(dom.getIdcolor()).getNomcorto());
    		listaVariedades2.add(dompojo);
    	}
    }
    
	public void reset() {
	    this.tipo= null;
	    this.descripcion= null;
	    this.idColor=null;
	    this.foto=null;
	    this.fotois=null;
	    this.codigo=null;
	}
	

	public boolean validar(){
		boolean ok = true;
		
			if (!Validador.esLongitudCorrecta(this.tipo, 1, 45)){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Variedad: "," Debe tener entre 1 y 25 caracteres" ));
				ok = false;
			}
			if (!Validador.esLongitudCorrecta(this.descripcion, 1, 45)){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Descripción: "," Debe tener entre 1 y 45 caracteres" ));
				ok = false;
			}
			
			if (this.idColor.intValue() == 0){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Color: "," Debe escoger un Color" ));
				ok = false;
			}
			if (!Validador.esLongitudCorrecta(this.codigo, 1, 3)){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Codigo: "," Debe tener entre 1 y 3 caracteres" ));
				ok = false;
			}
			
			
			// validaciones logicas.
			
			
			if(dominioService.getDominioPadreNombre(this.padre,this.tipo)!= null){
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errorvariedadyaexiste"));
				//RequestContext.getCurrentInstance().showMessageInDialog(message);
				PrimeFaces.current().dialog().showMessageDynamic(message);
				ok =  false;
			} 
			if(dominioService.getDominioPadreCodigo(this.padre,this.codigo)!= null){
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errorvariedadyaexiste"));
				//RequestContext.getCurrentInstance().showMessageInDialog(message);
				PrimeFaces.current().dialog().showMessageDynamic(message);
				ok =  false;
			}
			
			
				   	

		return ok;
	}


	public boolean validarEditar(){
		boolean ok = true;
		
			if (!Validador.esLongitudCorrecta(this.tipo, 1, 45)){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Variedad: "," Debe tener entre 1 y 25 caracteres" ));
				ok = false;
			}
			if (!Validador.esLongitudCorrecta(this.descripcion, 1, 45)){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Descripción: "," Debe tener entre 1 y 45 caracteres" ));
				ok = false;
			}
			if (this.idColor.intValue() == 0){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Color: "," Debe escoger un Color" ));
				ok = false;
			}
			if (!Validador.esLongitudCorrecta(this.codigo, 1, 3)){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Codigo: "," Debe tener entre 1 y 3 caracteres" ));
				ok = false;
			}
			
			
			// validaciones logicas.
			
			/*if(dominioService.getDominiosXNombre(this.tipo).size() > 1){
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errorvariedadyaexiste"));
				RequestContext.getCurrentInstance().showMessageInDialog(message);
				ok =  false;
			} 
			
			if(dominioService.getDominiosXCodigoId(this.codigo,dominioEditar.getId(),EnuDominio.VARIEDADES.getIdTipoDominio())!= null){
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errorcodigoyaexiste"));
				RequestContext.getCurrentInstance().showMessageInDialog(message);
				ok =  false;
			}*/
			
			// validaciones logicas.
			
			Dominio domaux = dominioService.getDominioPadreNombre(this.padre,this.tipo);
						if(domaux!= null){
							if(domaux.getId().intValue() != dominioEditar.getId().intValue()){
								FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errorvariedadyaexiste"));
								//RequestContext.getCurrentInstance().showMessageInDialog(message);
								PrimeFaces.current().dialog().showMessageDynamic(message);
								ok =  false;
							}
						} 
			domaux = 	dominioService.getDominioPadreCodigo(this.padre,this.codigo);		
						if(domaux!= null){
							if(domaux.getId().intValue() != dominioEditar.getId().intValue()){
								FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errorvariedadyaexiste"));
								//RequestContext.getCurrentInstance().showMessageInDialog(message);
								PrimeFaces.current().dialog().showMessageDynamic(message);
								ok =  false;
							}	
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
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmaeditarvariedad"));
		//RequestContext.getCurrentInstance().showMessageInDialog(message);
		PrimeFaces.current().dialog().showMessageDynamic(message);
		return;

	}

	public String editar (com.bulls.astoria.pojo.Dominio dominiopojo){

		 Dominio dominio = dominioService.getDominio(dominiopojo.getId());
		 this.tipo = dominio.getNomcorto();
		 this.descripcion= dominio.getDescripcion();
		 this.idColor = dominio.getIdcolor();
		 this.foto = null;
		 this.dominioEditar = dominio;
		 this.codigo=dominio.getCodigo();
		 		 
		 return "editarvariedad";

		}
	                
	 	public void handleFileUpload(FileUploadEvent event) {
	 		
	 		try {
	 		this.foto = event.getFile().getFileName();
	 		this.fotois = this.file.getInputStream(); //puesto despues de arreglo de version 
	 		//this.fotois = event.getFile().getInputstream();
	 		if(this.foto != null){
	 			this.extencion = ArchivoUtils.getExtencion(this.foto);
	 		}		

	 		}catch (Exception e){
	 			e.printStackTrace();
	 		}

	    }

	 	
/**
 * Se cero despues de cambio de version para que acepte el componente JSF
 */
public void handleFileUploadJSF() {
	 		
	 		try {
	 		this.foto = file.getFileName();
	 		this.fotois = this.file.getInputStream(); //puesto despues de arreglo de version 
	 		//this.fotois = event.getFile().getInputstream();
	 		if(this.foto != null){
	 			this.extencion = ArchivoUtils.getExtencion(this.foto);
	 		}		

	 		}catch (Exception e){
	 			e.printStackTrace();
	 		}

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

	public Integer getPadre() {
		return padre;
	}

	public void setPadre(Integer padre) {
		this.padre = padre;
	}

	public List<Dominio> getListaVariedades() {
		return listaVariedades;
	}

	public void setListaVariedades(List<Dominio> listaVariedades) {
		this.listaVariedades = listaVariedades;
	}

	public List<SelectItem> getListaTiposFlor() {
		return listaTiposFlor;
	}

	public void setListaTiposFlor(List<SelectItem> listaTiposFlor) {
		this.listaTiposFlor = listaTiposFlor;
	}

	public String getPadre2() {
		return padre2;
	}

	public void setPadre2(String padre2) {
		this.padre2 = padre2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Map<String, String> getCities() {
		return cities;
	}

	public void setCities(Map<String, String> cities) {
		this.cities = cities;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public InputStream getFotois() {
		return fotois;
	}

	public void setFotois(InputStream fotois) {
		this.fotois = fotois;
	}

	public String getRutafoto() {
		return rutafoto;
	}

	public void setRutafoto(String rutafoto) {
		this.rutafoto = rutafoto;
	}

	public String getExtencion() {
		return extencion;
	}

	public void setExtencion(String extencion) {
		this.extencion = extencion;
	}

	public Dominio getDominioEditar() {
		return dominioEditar;
	}

	public void setDominioEditar(Dominio dominioEditar) {
		this.dominioEditar = dominioEditar;
	}

	public String getSelectedFoto() {
		return selectedFoto;
	}

	public void setSelectedFoto(String selectedFoto) {
		this.selectedFoto = selectedFoto;
	}

	public Dominio getSelectedDominio() {
		return selectedDominio;
	}

	public void setSelectedDominio(Dominio selectedDominio) {
		this.selectedDominio = selectedDominio;
	}

	public List<Dominio> getColores() {
		return colores;
	}

	public void setColores(List<Dominio> colores) {
		this.colores = colores;
	}

	public List<SelectItem> getColoresSelect() {
		return coloresSelect;
	}

	public void setColoresSelect(List<SelectItem> coloresSelect) {
		this.coloresSelect = coloresSelect;
	}

	public Integer getIdColor() {
		return idColor;
	}

	public void setIdColor(Integer idColor) {
		this.idColor = idColor;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	
	
	public List<com.bulls.astoria.pojo.Dominio> getListaVariedades2() {
		return listaVariedades2;
	}

	public void setListaVariedades2(
			List<com.bulls.astoria.pojo.Dominio> listaVariedades2) {
		this.listaVariedades2 = listaVariedades2;
	}

	public com.bulls.astoria.pojo.Dominio getSelectedDominio2() {
		return selectedDominio2;
	}

	public void setSelectedDominio2(com.bulls.astoria.pojo.Dominio selectedDominio2) {
		this.selectedDominio2 = selectedDominio2;
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
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("truckMB");
		  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuarioMB");
		  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("variedadMB");

	   }
	
}
