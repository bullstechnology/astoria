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

import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.pojo.Producto;
import com.bulls.astoria.service.DominioService;



@ManagedBean (name="productoMB")
@SessionScoped
public class ProductosManagedBean extends GeneralManagedBean implements Serializable{

	
    transient ResourceBundle bundle ;
	List <Map> productosMap;
	List <Producto> productos;
	
	@ManagedProperty(value="#{DominioService}")
	DominioService dominioService;
	
	@PostConstruct
	public void ProductosManagedBean(){
		borrarSession();
		bundle =  ResourceBundle.getBundle("messages");
		productosMap = dominioService.getProductosCompleto();
		productos = getProductosmap(productosMap);
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
	
	
	 private void abrirConfirmacion(){
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmaeditarlistaproductos"));
			//RequestContext.getCurrentInstance().showMessageInDialog(message);
			PrimeFaces.current().dialog().showMessageDynamic(message);
			return;

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
