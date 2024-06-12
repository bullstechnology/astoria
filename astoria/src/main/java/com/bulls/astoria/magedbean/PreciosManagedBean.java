package com.bulls.astoria.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FlowEvent;

import com.bulls.astoria.enumeracion.EnuDominio;
import com.bulls.astoria.persistence.DetalleListaPrecio;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.FranjaPrecios;
import com.bulls.astoria.persistence.ListaPrecio;
import com.bulls.astoria.persistence.Plantacion;
import com.bulls.astoria.persistence.PlantacionPacking;
import com.bulls.astoria.persistence.PlantacionProducto;
import com.bulls.astoria.pojo.Grado;
import com.bulls.astoria.pojo.Producto;
import com.bulls.astoria.pojo.ProductoPrecio;
import com.bulls.astoria.service.DominioService;
import com.bulls.astoria.service.PlantacionService;
import com.bulls.astoria.utils.Convertidor;
import com.bulls.astoria.utils.Validador;



@ManagedBean (name="preciosMB")
@SessionScoped
public class PreciosManagedBean extends GeneralManagedBean implements Serializable{
	

	private Integer id;
	private Integer idPlantacion;
	private Character tipo;
	private Integer idFranja;
	private Integer idProducto;
	private String codigo;
	private Integer idColor;
	private Integer idGrado;
	private Double precio;
	
	
	List <ProductoPrecio> productos;
	List <FranjaPrecios> franjas;
	List <Dominio> colores;
	List <Dominio> grados;
	List <Map> listaProductosMap;
	List <Plantacion>  listaPlantaciones;
	List <SelectItem> listaPlantacionesSelect;
	List <SelectItem> franjasSelect;
	List <SelectItem> productosPlantacionSelect;
	
	List <SelectItem>  gradosSelect;
	List <SelectItem> coloresSelect;
	List <SelectItem> listaProductosSelect;
	
	transient ResourceBundle bundle ;
	
	List <Map> productosMap;
	List <Producto> productos2;
	List <ProductoPrecio> productos3;
	List <ProductoPrecio> productos3Filtrada;
	
	
	@ManagedProperty(value="#{DominioService}")
	DominioService dominioService;
	
	@ManagedProperty(value="#{PlantacionService}")
	PlantacionService plantacionService;
	
	
	
	@PostConstruct
	public void PreciosManagedBean(){
		
		borrarSession();
		
		bundle =  ResourceBundle.getBundle("messages");
		listaPlantaciones = plantacionService.getPlantaciones();
		listaPlantacionesSelect = Convertidor.plantacionToSelectdItems(listaPlantaciones);		
		colores = dominioService.getDominios(EnuDominio.COLORES.getIdTipoDominio());
		grados =   dominioService.getDominios(EnuDominio.GRADOS.getIdTipoDominio());
		
		coloresSelect = Convertidor.dominiosToSelectdItems(colores);
		gradosSelect = Convertidor.dominiosToSelectdItems(grados);
		productos = new ArrayList<ProductoPrecio>();
		listaProductosMap = dominioService.getProductos();
		listaProductosSelect = Convertidor.mapToSelectdItems(listaProductosMap);

	}
	
	public void onCellEdit(CellEditEvent event) {
     /*   Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }*/
    }
    
    public void getProductosPlantacion(){
    	this.idColor= null;
    	this.idGrado = null;
    	this.precio=null;
    	List <PlantacionProducto> lista = plantacionService.getProductosPlantacion(this.idPlantacion);
    	productosMap = dominioService.getProductosCompletoPlantacion(this.idPlantacion);
		productos2 = getProductosmap(productosMap);
		productos3 = getProductosPrecio(productosMap);
    	
    	getFranjasPlantacion();
    	List <ProductoPrecio> productos = plantacionProductoToProducto(lista);
        //productosPlantacionSelect = Convertidor.productoPrecioToSelectdItems(productos);
    	productosPlantacionSelect = Convertidor.ProductoPojoToSelectdItems(productos2);
    	
    	
    }
    
    public void getFranjasPlantacion(){
    	List <FranjaPrecios> lista = plantacionService.getFranjasPrecios(this.idPlantacion);
    	franjasSelect = franjasToFranjasSelect(lista);
    	return;
    }
    
    
    public List <SelectItem> franjasToFranjasSelect(List <FranjaPrecios> lista){
		List <SelectItem> listafinal = new ArrayList<SelectItem>();
		for (FranjaPrecios franja :lista){
			SelectItem ss = new SelectItem();
			ss.setLabel(franja.getTitulo());
			ss.setValue(franja.getId());
			listafinal.add(ss);
		}
		return listafinal;
		
	}
    
  public List <ProductoPrecio> plantacionProductoToProducto(List <PlantacionProducto> listaProductos){
		List <ProductoPrecio> lista = new ArrayList<ProductoPrecio>();
		for (PlantacionProducto pro :listaProductos){
			com.bulls.astoria.persistence.Producto proper = dominioService.getProductoXId(pro.getIdproducto());
			Dominio dom = dominioService.getDominio(proper.getIdvariedad());
			ProductoPrecio producto = new ProductoPrecio (null,null,dom.getId(),dom.getNomcorto(),null,idGrado,"",idColor,"");
			lista.add(producto);
		}
		return lista;
		
	}
    
    
    public void subir(){
		
    	if(validarSubir()){
    		com.bulls.astoria.persistence.Producto pro = dominioService.getProductoXCodigo(this.codigo);
			//Dominio producto = dominioService.getDominio(this.idProducto);
    		Dominio producto = dominioService.getDominio(pro.getIdvariedad());
    		
			//Dominio grado = dominioService.getDominio(this.idGrado);
    		Dominio grado = dominioService.getDominio(pro.getIdgrado());
			Dominio color = dominioService.getDominio(this.idColor);
			ProductoPrecio pp = new ProductoPrecio (null,null,pro.getId(),producto.getNomcorto(),this.precio,grado.getId(),grado.getNomcorto(),this.idColor,color.getNomcorto(),this.codigo);
			
			
			productos.add(pp);
		}	
		return;

	}
    
    public void eliminar (ProductoPrecio producto){
    	   productos.remove(producto);
    	   return;
    }
    
    public boolean validarSubir (){
    	boolean ok = true;
    	
      if(this.codigo.equalsIgnoreCase("")){
    	  FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Producto: "," Debe escoger un producto" ));
			ok = false;
      }
      if(this.idColor.intValue() == 0){
    	  FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Color: "," Debe escoger un color" ));
			ok = false; 
      }
           
      if(this.precio.doubleValue() <= 0){
    	  FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Precio: "," Debe digitar un precio mayor que cero" ));
			ok = false;
      }
     
    	
 	  for(ProductoPrecio prolista :productos){
 		  if(prolista.getCodigo().equalsIgnoreCase(this.codigo) ){
 			 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errorsubirproducto"));
 			 //RequestContext.getCurrentInstance().showMessageInDialog(message);
 			PrimeFaces.current().dialog().showMessageDynamic(message);
 			 ok = false;
 			 return ok;
 			  
 		  }
 	  }
 	  return ok;
 }
    

    public void crearLista(){
    	
    	if(productos3.size() == 0){
    		 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errorproductosvacio"));
 			 //RequestContext.getCurrentInstance().showMessageInDialog(message);
    		 PrimeFaces.current().dialog().showMessageDynamic(message);
 			 return;
    	}
    	
    	List<ListaPrecio> listatmp = plantacionService.getListaPrecios(this.idPlantacion);
    	FranjaPrecios franja = plantacionService.getFranja(this.idFranja);
    	for(ListaPrecio lp : listatmp){
    		FranjaPrecios franjaaux = plantacionService.getFranja(lp.getIdfranja());
  		    if(franja.getFechaini().after(franjaaux.getFechaini())  && franja.getFechaini().before(franjaaux.getFechafin())){
  		    	// si la nueva es una especial y la comparacion es una especial , hay error
  		    	// si la nueva es I o V y la comparaciobn es I o V , hay error
  		    	//si la nueva es especial y la comparacion es i o V es correcto
  		    	//si la nueva es I o V y la comparacion es E es correcto 
    			//error fechas
  		    	
  		    	if((lp.getTipo().equals('I') || lp.getTipo().equals('V')) && (this.getTipo().equals('I') || this.getTipo().equals('V')) ){
  		    		
  		    		 
  		    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), "Ya existe una lista de precios para esa fecha de Invierno o verano");
  					//RequestContext.getCurrentInstance().showMessageInDialog(message);
  		    		PrimeFaces.current().dialog().showMessageDynamic(message);
  					return;
  		    	}
  		    	
                if(lp.getTipo().equals('E')  && this.getTipo().equals('E')){
  		    		
                	                	
                	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), "Ya existe una lista de precios para temporada especial en esa fecha");
  					//RequestContext.getCurrentInstance().showMessageInDialog(message);
                	PrimeFaces.current().dialog().showMessageDynamic(message);
  					return;
  		    	}
    		}
  		    
    	}
    	
    	ListaPrecio lista = new ListaPrecio();
    	
    	lista.setIdplantacion(this.idPlantacion);
    	lista.setIdfranja(this.idFranja);
    	lista.setTipo(this.tipo);
    	
    	Set<DetalleListaPrecio> set = new HashSet<DetalleListaPrecio>();
    	
    	for(ProductoPrecio producto: productos3){
    		
    		DetalleListaPrecio detalle = new DetalleListaPrecio();
    		detalle.setIdproducto(producto.getIdProducto());
    		detalle.setPrecio(producto.getPrecio());
    		detalle.setIdcolor(producto.getIdcolor());
    		set.add(detalle);
    		detalle.setListaPrecio(lista);
    	}
    	
    	lista.setDetalleListaPrecio(set);
    	
    	plantacionService.crearLista(lista);
    	this.putAuditoria("Crear lista precios", "C", "Creo  clista precios así : - " + lista.toString());
    	abrirConfirmacion();
    	return;
    }
	
	 private void abrirConfirmacion(){
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmacrearlista"));
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

	public Integer getIdPlantacion() {
		return idPlantacion;
	}

	public void setIdPlantacion(Integer idPlantacion) {
		this.idPlantacion = idPlantacion;
	}

	public Character getTipo() {
		return tipo;
	}

	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}

	public List<ProductoPrecio> getProductos() {
		return productos;
	}

	public void setProductos(List<ProductoPrecio> productos) {
		this.productos = productos;
	}

	public List<FranjaPrecios> getFranjas() {
		return franjas;
	}

	public void setFranjas(List<FranjaPrecios> franjas) {
		this.franjas = franjas;
	}

	public List<Plantacion> getListaPlantaciones() {
		return listaPlantaciones;
	}

	public void setListaPlantaciones(List<Plantacion> listaPlantaciones) {
		this.listaPlantaciones = listaPlantaciones;
	}

	public List<SelectItem> getListaPlantacionesSelect() {
		return listaPlantacionesSelect;
	}

	public void setListaPlantacionesSelect(List<SelectItem> listaPlantacionesSelect) {
		this.listaPlantacionesSelect = listaPlantacionesSelect;
	}

	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	public DominioService getDominioService() {
		return dominioService;
	}

	public void setDominioService(DominioService dominioService) {
		this.dominioService = dominioService;
	}

	public PlantacionService getPlantacionService() {
		return plantacionService;
	}

	public void setPlantacionService(PlantacionService plantacionService) {
		this.plantacionService = plantacionService;
	}

	public List<SelectItem> getFranjasSelect() {
		return franjasSelect;
	}

	public void setFranjasSelect(List<SelectItem> franjasSelect) {
		this.franjasSelect = franjasSelect;
	}

	public Integer getIdFranja() {
		return idFranja;
	}

	public void setIdFranja(Integer idFranja) {
		this.idFranja = idFranja;
	}

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public Integer getIdColor() {
		return idColor;
	}

	public void setIdColor(Integer idColor) {
		this.idColor = idColor;
	}

	public Integer getIdGrado() {
		return idGrado;
	}

	public void setIdGrado(Integer idGrado) {
		this.idGrado = idGrado;
	}

	public List<Dominio> getColores() {
		return colores;
	}

	public void setColores(List<Dominio> colores) {
		this.colores = colores;
	}

	public List<Dominio> getGrados() {
		return grados;
	}

	public void setGrados(List<Dominio> grados) {
		this.grados = grados;
	}

	public List<SelectItem> getProductosPlantacionSelect() {
		return productosPlantacionSelect;
	}

	public void setProductosPlantacionSelect(
			List<SelectItem> productosPlantacionSelect) {
		this.productosPlantacionSelect = productosPlantacionSelect;
	}

	public List<SelectItem> getGradosSelect() {
		return gradosSelect;
	}

	public void setGradosSelect(List<SelectItem> gradosSelect) {
		this.gradosSelect = gradosSelect;
	}

	public List<SelectItem> getColoresSelect() {
		return coloresSelect;
	}

	public void setColoresSelect(List<SelectItem> coloresSelect) {
		this.coloresSelect = coloresSelect;
	}
	
	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	
	
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<Map> getListaProductosMap() {
		return listaProductosMap;
	}

	public void setListaProductosMap(List<Map> listaProductosMap) {
		this.listaProductosMap = listaProductosMap;
	}

	public List<SelectItem> getListaProductosSelect() {
		return listaProductosSelect;
	}

	public void setListaProductosSelect(List<SelectItem> listaProductosSelect) {
		this.listaProductosSelect = listaProductosSelect;
	}

	public List<Map> getProductosMap() {
		return productosMap;
	}

	public void setProductosMap(List<Map> productosMap) {
		this.productosMap = productosMap;
	}

	public List<Producto> getProductos2() {
		return productos2;
	}

	public void setProductos2(List<Producto> productos2) {
		this.productos2 = productos2;
	}

	public String onFlowProcess(FlowEvent event) {
		
		boolean ok = true;
		
		if(this.idPlantacion.intValue() == 0){
			 
 			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.plantacion") + ":",bundle.getString("errordebeplantacion")));
 			 ok = false;
		}
		if(this.tipo == 'Z'){
			
 			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.temporada") + ":",bundle.getString("errordebetipo")));
 			 ok = false;
		}
		if(this.idFranja.intValue() == 0){
			
 			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.franja") + ":",bundle.getString("errordebefranja")));
 			 ok = false;
		}
		
       /*if(this.tipo == 'E' && this.idFranja.intValue() == 0){
			
 			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.franja") + ":",bundle.getString("errordebefranja")));
 			 ok = false;
		}*/
		
		List<ListaPrecio> listasprecios = plantacionService.getListaPrecios(this.idPlantacion);
		for(ListaPrecio lp : listasprecios){
			if (this.tipo.equals('I') || this.tipo.equals('V') ){
				if(this.tipo.equals(lp.getTipo())){
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.temporada") + ":",bundle.getString("errordetemporada")));
					ok=false;
				}
			}
			
			if(this.idFranja.intValue() == lp.getIdfranja().intValue()){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.franja") + ":",bundle.getString("errorfranjaprecio")));
				ok=false;
			}
		}
		
		
		
		if(ok){
			
			return event.getNewStep();
			
		}else {
			return "generales";
		}
		
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
            
            com.bulls.astoria.persistence.Producto procod = dominioService.getProductoVariedadGrado(variedad, grado);
            String codigo = null; 
            boolean enabled = false;
            if(procod!= null){
            	codigo = procod.getCodigo(); 
                enabled = procod.isEnabled();
            }            
			
			Producto productosalida = new Producto (flor,nomflor,variedad,nomvariedad,grado,nomgrado,codigo,enabled);
			productossalida.add(productosalida);

		}
		return productossalida;
	}
	
	private List <ProductoPrecio> getProductosPrecio(List <Map> productosmap){
		List <ProductoPrecio> productossalida = new ArrayList<ProductoPrecio>();
		Iterator ite = productosmap.iterator();
		while ( ite.hasNext() ) {
			
			Map pro = (Map) ite.next();
			int variedad= ((Integer) pro.get("idhijo"));
			int flor= ((Integer) pro.get("idpadre"));
			int grado= ((Integer) pro.get("idgrado"));
			int color= ((Integer) pro.get("idcolor"));
            String nomvariedad = ((String) pro.get("nomhijo"));
            String nomflor = ((String) pro.get("nompadre"));
            String nomgrado = ((String) pro.get("nomgrado"));
            String nomcolor = ((String) pro.get("nomcolor"));
            
            com.bulls.astoria.persistence.Producto procod = dominioService.getProductoVariedadGrado(variedad, grado);
            String codigo = null; 
            boolean enabled = false;
            if(procod!= null){
            	codigo = procod.getCodigo(); 
                enabled = procod.isEnabled();
                
                ProductoPrecio productosalida2 = new ProductoPrecio(flor,nomflor,variedad,nomvariedad,0.0,grado,nomgrado,color,nomcolor);
    			productosalida2.setIdProducto(procod.getId());
    			productossalida.add(productosalida2);
            }            
			
		//	Producto productosalida = new Producto (flor,nomflor,variedad,nomvariedad,grado,nomgrado,codigo,enabled);
			
			

		}
		return productossalida;
	}
	
	
	
	public List<ProductoPrecio> getProductos3() {
		return productos3;
	}

	public void setProductos3(List<ProductoPrecio> productos3) {
		this.productos3 = productos3;
	}
	
	

	public List<ProductoPrecio> getProductos3Filtrada() {
		return productos3Filtrada;
	}

	public void setProductos3Filtrada(List<ProductoPrecio> productos3Filtrada) {
		this.productos3Filtrada = productos3Filtrada;
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
		  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("preciosMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("productoMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("truckMB");
		  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuarioMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("variedadMB");

	   }

}
