package com.bulls.astoria.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
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
import org.primefaces.event.CellEditEvent;

import com.bulls.astoria.enumeracion.EnuDominio;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.pojo.Producto;
import com.bulls.astoria.service.DominioService;
import com.bulls.astoria.utils.Convertidor;



@ManagedBean (name="productoMB")
@SessionScoped

public class ProductosManagedBean extends GeneralManagedBean implements Serializable{

	
    transient ResourceBundle bundle ;
	List <Map> productosMap;
	List <Producto> productos;
	List <Dominio> tiposFlor;
	List <Dominio> variedades;
	List <SelectItem> listaVariedades;
	List<SelectItem> listaTiposFlor;
	private Integer tipoFlor;
	public Integer variedad;
	
	@ManagedProperty(value="#{DominioService}")
	DominioService dominioService;
	
	@PostConstruct
	public void ProductosManagedBean(){
		borrarSession();
		bundle =  ResourceBundle.getBundle("messages");
		tiposFlor = dominioService.getDominios(EnuDominio.FLORES.getIdTipoDominio());
	    listaTiposFlor = Convertidor.dominiosToSelectdItems(tiposFlor);
	    variedades = dominioService.getDominios(EnuDominio.VARIEDADES.getIdTipoDominio());
	    //listaVariedades = Convertidor.dominiosToSelectdItems(variedades);
	    listaVariedades = new ArrayList<SelectItem>();
		productosMap = dominioService.getProductosCompleto();
		//productos = getProductosmap(productosMap);
	}
	
	public void onCellEdit(CellEditEvent event) {
       
    }
	
	public void editar() {
       for(Producto producto:productos){
    	   Integer idvariedad = producto.getIdProducto();
    	   Integer idgrado = producto.getGrado();
    	   String codigo = producto.getCodigo();
    	   boolean enabled= producto.isEstado();	   
    	   dominioService.actualizarProducto(idvariedad, idgrado, codigo, enabled);
    	   this.putAuditoria("Editar producto", "U", "Edito el producto así : - " + idvariedad.toString() + "-- " + codigo);
    	   
       }
       productosMap = dominioService.getProductosCompleto();
	   productos = getProductosmap(productosMap);
       abrirConfirmacion();
    }
	
	
	
	private List <Producto> getProductosmap(List <Map> productosmap){
		List <Producto> productossalida = new ArrayList<Producto>();
		Iterator ite = productosmap.iterator();
		while ( ite.hasNext() ) {
			
			Map pro = (Map) ite.next();
			int variedad= ((Integer) pro.get("idhijo"));
			int flor= ((Integer) pro.get("idpadre"));
			int grado= ((Integer) pro.get("idgrado"));
            String nomvariedad = ((String) pro.get("nomhijo"));
            String nomflor = ((String) pro.get("nompadre"));
            String nomgrado = ((String) pro.get("nomgrado"));
            String codigo = ((String) pro.get("codigo"));
            int idproducto = ((Integer) pro.get("idproducto"));
            String codigopro = ((String) pro.get("codigo"));
            boolean enabled = ((Boolean) pro.get("enabled"));
            
            
           /* com.bulls.astoria.persistence.Producto procod = dominioService.getProductoVariedadGrado(variedad, grado);
            String codigo = null; 
            boolean enabled = false;
            if(procod!= null){
            	codigo = procod.getCodigo(); 
                enabled = procod.isEnabled();
            }  */          
			
			Producto productosalida = new Producto (flor,nomflor,variedad,nomvariedad,grado,nomgrado,codigopro,enabled);
			productossalida.add(productosalida);

		}
		return productossalida;
	}
	
public List<SelectItem> getVariedades(Integer idtipoFlor){
    	
    	System.out.println("en get Variedades con tipo flor :" + idtipoFlor);
    	System.out.println("en get Variedades con tipo flor2 :" + this.tipoFlor);
    	variedades = dominioService.getDominiosXPadre(idtipoFlor);
    	listaVariedades = Convertidor.dominiosToSelectdItems(variedades);
    	return listaVariedades;
 
    }

public List<SelectItem> getVariedades2(){
	

	System.out.println("en get Variedades2 con tipo flor2 :" + this.tipoFlor);
	variedades = dominioService.getDominiosXPadre(this.tipoFlor);
	listaVariedades = Convertidor.dominiosToSelectdItems(variedades);
	return listaVariedades;

}
	
	public List<Producto> buscarProductos(Integer idTipoFlor, Integer idvariedad) {
		
		System.out.println("entre en buscar productos con idTipoFlor " + idTipoFlor + "  y variedad  " +idvariedad) ;
		System.out.println("entre en buscar productos2 con idTipoFlor " + this.tipoFlor + "  y variedad  " +this.variedad) ;
		productosMap = dominioService.getProductosCompleto(idTipoFlor,idvariedad);
		productos = getProductosmap(productosMap);
		System.out.println("retornando busqueda con "+productos.size());
		return productos;
	}
	
public List<Producto> buscarProductos2() {
		
		
		System.out.println("entre en buscar productos2 con idTipoFlor " + this.tipoFlor + "  y variedad  " +this.variedad) ;
		if (validarBuscar()){
			
		
		if(this.variedad != 0) {
		   productosMap = dominioService.getProductosCompleto(this.tipoFlor,this.variedad);
		}else {
			productosMap = dominioService.getProductosCompletoFlor(this.tipoFlor);	
		}
		productos = getProductosmap(productosMap);
		System.out.println("retornando busqueda con "+productos.size());
		}
		return productos;
	}
	 private void abrirConfirmacion(){
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmaeditarlistaproductos"));
			//RequestContext.getCurrentInstance().showMessageInDialog(message);
			PrimeFaces.current().dialog().showMessageDynamic(message);
			return;

		}
	 
	 
	 public boolean validarBuscar(){
	  	  
	  	  boolean ok = true;
	   		  if(0 == this.tipoFlor.intValue() || 0 == this.variedad.intValue()){
	  			  ok = false;
	  			  FacesContext.getCurrentInstance().addMessage(null,
	  						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Busqueda","Debe escoger un tipo de Flor y una Variedad" ));
	  		  }
	   	  return ok;
	  	
	}

	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	
	public List<Map> getProductosMap() {
		return productosMap;
	}

	public void setProductosMap(List<Map> productosMap) {
		this.productosMap = productosMap;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public DominioService getDominioService() {
		return dominioService;
	}

	public void setDominioService(DominioService dominioService) {
		this.dominioService = dominioService;
	}
	
	
	public Integer getTipoFlor() {
		return tipoFlor;
	}

	public void setTipoFlor(Integer tipoFlor) {
		this.tipoFlor = tipoFlor;
	}
	

	public Integer getVariedad() {
		return variedad;
	}

	public void setVariedad(Integer variedad) {
		this.variedad = variedad;
	}
	
	

	public List<Dominio> getTiposFlor() {
		return tiposFlor;
	}

	public void setTiposFlor(List<Dominio> tiposFlor) {
		this.tiposFlor = tiposFlor;
	}

	public List<Dominio> getVariedades() {
		return variedades;
	}

	public void setVariedades(List<Dominio> variedades) {
		this.variedades = variedades;
	}

	public List<SelectItem> getListaVariedades() {
		return listaVariedades;
	}

	public void setListaVariedades(List<SelectItem> listaVariedades) {
		this.listaVariedades = listaVariedades;
	}

	public List<SelectItem> getListaTiposFlor() {
		return listaTiposFlor;
	}

	public void setListaTiposFlor(List<SelectItem> listaTiposFlor) {
		this.listaTiposFlor = listaTiposFlor;
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
		  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("productoMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("truckMB");
		  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuarioMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("variedadMB");

	   }
}
