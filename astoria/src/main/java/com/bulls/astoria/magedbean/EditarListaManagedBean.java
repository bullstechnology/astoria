package com.bulls.astoria.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.bulls.astoria.enumeracion.EnuDominio;
import com.bulls.astoria.persistence.DetalleListaPrecio;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.FranjaPrecios;
import com.bulls.astoria.persistence.ListaPrecio;
import com.bulls.astoria.persistence.Plantacion;
import com.bulls.astoria.persistence.PlantacionProducto;
import com.bulls.astoria.pojo.ListaPrecioBean;
import com.bulls.astoria.persistence.Producto;
import com.bulls.astoria.pojo.ProductoPrecio;
import com.bulls.astoria.service.DominioService;
import com.bulls.astoria.service.PlantacionService;
import com.bulls.astoria.utils.Convertidor;


@ManagedBean (name="editarListaMB")
@SessionScoped
public class EditarListaManagedBean extends GeneralManagedBean implements Serializable{

	
	private List <Plantacion> listaPlantaciones ;
	private List <SelectItem> listaPlantacionesSelect;
	private List <ProductoPrecio> productos;
	private List <Dominio> colores;
	private List <Dominio> grados;
	private List <SelectItem> coloresSelect;
	private List <SelectItem> gradosSelect;
	private List <ProductoPrecio> listaDetalles ;
	private List <ProductoPrecio> listaDetallesFiltrada ;
	
	private Integer idPlantacion;
	private List <ListaPrecioBean> listasPrecios;
	private List <ListaPrecio> listas;
	private Plantacion plantacioneditar;
	private ListaPrecioBean listaeditar;
	private List <ProductoPrecio> productos3;
	private List <ProductoPrecio> productos3Filtrada;

	
	transient ResourceBundle bundle ;
	
	
	@ManagedProperty(value="#{DominioService}")
	DominioService dominioService;
	
	@ManagedProperty(value="#{PlantacionService}")
	PlantacionService plantacionService;
	
	
	
	@PostConstruct
	public void EditarListaManagedBean(){
		
		borrarSession();
		
		bundle =  ResourceBundle.getBundle("messages");
		listaPlantaciones = plantacionService.getPlantaciones();
		listaPlantacionesSelect = Convertidor.plantacionToSelectdItems(listaPlantaciones);		
		colores = dominioService.getDominios(EnuDominio.COLORES.getIdTipoDominio());
		grados =   dominioService.getDominios(EnuDominio.GRADOS.getIdTipoDominio());
		
		coloresSelect = Convertidor.dominiosToSelectdItems(colores);
		gradosSelect = Convertidor.dominiosToSelectdItems(grados);
		productos = new ArrayList<ProductoPrecio>();

	}
	
	public void getListasPlantacion(){
		listas =  plantacionService.getListaPrecios(this.idPlantacion);
		listasPrecios = new ArrayList <ListaPrecioBean>();
		for(ListaPrecio listaPrecio : listas ){
			ListaPrecioBean lp = getListaPrecioBean(listaPrecio);
			listasPrecios.add(lp);
		}
		return;
	}
	public String getDetallesLista(ListaPrecioBean lp){
		
		List <ProductoPrecio> lista = new ArrayList<ProductoPrecio>();
		List <DetalleListaPrecio> listaProductos = plantacionService.getDetalleListaPrecios(lp.getId());
		plantacioneditar = plantacionService.getPlantacion(lp.getIdplantacion());
		listaeditar = lp;
		for (DetalleListaPrecio pro :listaProductos){

			Dominio color = dominioService.getDominio(pro.getIdcolor());
			Producto pro2 = dominioService.getProductoXId(pro.getIdproducto());
			Dominio grado = dominioService.getDominio(pro2.getIdgrado());
			Dominio variedad  = dominioService.getDominio(pro2.getIdvariedad());
			Dominio flor  = dominioService.getDominio(variedad.getDominiopadre());
			String nombreproducto =  flor.getNomcorto() + " " + variedad.getNomcorto()  + " " + grado.getNomcorto();
			//ProductoPrecio prosalida = new ProductoPrecio (null,null,pro.getId(),  nombreproducto  ,pro.getPrecio(),grado.getId(),grado.getNomcorto(),color.getId(),color.getNomcorto());
			ProductoPrecio prosalida = new ProductoPrecio (null,flor.getNomcorto(),pro.getId(),  variedad.getNomcorto()  ,pro.getPrecio(),grado.getId(),grado.getNomcorto(),color.getId(),color.getNomcorto());
			lista.add(prosalida);
		}
		listaDetalles = lista;
		
		/*RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('detalleDialogo').show();");*/
		return "editarlista";

	}
	
	public ListaPrecioBean getListaPrecioBean(ListaPrecio listaPrecio){
		
		ListaPrecioBean lp = new ListaPrecioBean();
		lp.setId(listaPrecio.getId());
		lp.setIdfranja(listaPrecio.getIdfranja());
		lp.setIdplantacion(listaPrecio.getIdplantacion());
		lp.setTipo(listaPrecio.getTipo());
		
		FranjaPrecios franja = plantacionService.getFranja(listaPrecio.getIdfranja());
		Plantacion plantacion = plantacionService.getPlantacion(listaPrecio.getIdplantacion());
		String nfranja = new String("");
		Date fechaini;
		Date fechafin;
		if(franja != null){
			nfranja = franja.getTitulo();
			fechaini = franja.getFechaini();
			fechafin = franja.getFechafin();
			lp.setFechaini(fechaini);
			lp.setFechafin(fechafin);
		}	
		
		lp.setNombrefranja(nfranja);
		lp.setNombreplantacion(plantacion.getNombre());
		
		return lp;
	}
	
	public void eliminar(ListaPrecioBean lp){
		plantacionService.eliminarListaPrecios(lp.getId());
		getListasPlantacion();
		abrirConfirmacionEliminar();
		return;
	}
	
	private void abrirConfirmacionEliminar(){
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmaeliminarlista"));
		//RequestContext.getCurrentInstance().showMessageInDialog(message);
		PrimeFaces.current().dialog().showMessageDynamic(message);
		return;
	}
	
public String editarLista(ListaPrecioBean lp){
		
		productos3 = new ArrayList<ProductoPrecio>();
		List <Map> productosMap;
		List <ProductoPrecio> productos;
		plantacioneditar = plantacionService.getPlantacion(lp.getIdplantacion());
		listaeditar = lp;
		List <PlantacionProducto> listaprodplan = plantacionService.getProductosPlantacion(lp.getIdplantacion());
    	productosMap = dominioService.getProductosCompletoPlantacion(lp.getIdplantacion());
		productos = getProductosPrecio(productosMap);
		
		for (ProductoPrecio pro :productos){
			//Producto producto = dominioService.getProductoVariedadGrado(pro.getI, pro.getIdgrado());
			DetalleListaPrecio detalle = plantacionService.getDetalleListaPreciosProducto(lp.getId(),pro.getIdProducto());
			
			ProductoPrecio profin = pro;
			if(detalle != null){
				profin.setPrecio(detalle.getPrecio());
			}
			productos3.add(profin);
		}

		return "editarlistaprecios";

	}


public void guardarEditar(){
	
	if(productos3.size() == 0){
		 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errorproductosvacio"));
			// RequestContext.getCurrentInstance().showMessageInDialog(message);
		 PrimeFaces.current().dialog().showMessageDynamic(message);
			 return;
	}
	
	ListaPrecio lista = new ListaPrecio();
	
	lista.setIdplantacion(listaeditar.getIdplantacion());
	lista.setIdfranja(listaeditar.getIdfranja());
	lista.setTipo(listaeditar.getTipo());
	
	Set<DetalleListaPrecio> set = new HashSet<DetalleListaPrecio>();
	
	for(ProductoPrecio producto: productos3){
		
		DetalleListaPrecio detalle = new DetalleListaPrecio();
		detalle.setIdproducto(producto.getIdProducto());
		detalle.setPrecio(producto.getPrecio());
		detalle.setIdcolor(producto.getIdcolor());
		if(detalle.getPrecio()== null)
			detalle.setPrecio(0.0);
		set.add(detalle);
		detalle.setListaPrecio(lista);
	}
	
	lista.setDetalleListaPrecio(set);
	
	plantacionService.editarLista(lista,listaeditar.getId());
	this.putAuditoria("Editar lista de precios", "U", "Edito lista de precios así : - " + lista.toString());
	abrirConfirmacion();
	return;
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
        }            
	
		ProductoPrecio productosalida2 = new ProductoPrecio(flor,nomflor,variedad,nomvariedad,null,grado,nomgrado,color,nomcolor);
		productosalida2.setIdProducto(procod.getId());
		productossalida.add(productosalida2);

	}
	return productossalida;
}



private void abrirConfirmacion(){
	
	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmaeditarlista"));
	//RequestContext.getCurrentInstance().showMessageInDialog(message);
	PrimeFaces.current().dialog().showMessageDynamic(message);
	return;

}

public void clonar (){
	
	try {
		FacesContext.getCurrentInstance().getExternalContext().redirect("clonarlistaprecios.xhtml?id=" + listaeditar.getId());
	}catch (Exception e ){
		e.printStackTrace();
	}
}
	
	public String regresarLista (){
		return "plantacionlistas";
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

	public List<ProductoPrecio> getProductos() {
		return productos;
	}

	public void setProductos(List<ProductoPrecio> productos) {
		this.productos = productos;
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

	public List<SelectItem> getColoresSelect() {
		return coloresSelect;
	}

	public void setColoresSelect(List<SelectItem> coloresSelect) {
		this.coloresSelect = coloresSelect;
	}

	public List<SelectItem> getGradosSelect() {
		return gradosSelect;
	}

	public void setGradosSelect(List<SelectItem> gradosSelect) {
		this.gradosSelect = gradosSelect;
	}

	public Integer getIdPlantacion() {
		return idPlantacion;
	}

	public void setIdPlantacion(Integer idPlantacion) {
		this.idPlantacion = idPlantacion;
	}

	public List<ListaPrecioBean> getListasPrecios() {
		return listasPrecios;
	}

	public void setListasPrecios(List<ListaPrecioBean> listasPrecios) {
		this.listasPrecios = listasPrecios;
	}

	public List<ListaPrecio> getListas() {
		return listas;
	}

	public void setListas(List<ListaPrecio> listas) {
		this.listas = listas;
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

	public List<ProductoPrecio> getListaDetalles() {
		return listaDetalles;
	}

	public void setListaDetalles(List<ProductoPrecio> listaDetalles) {
		this.listaDetalles = listaDetalles;
	}

	public Plantacion getPlantacioneditar() {
		return plantacioneditar;
	}

	public void setPlantacioneditar(Plantacion plantacioneditar) {
		this.plantacioneditar = plantacioneditar;
	}

	public ListaPrecioBean getListaeditar() {
		return listaeditar;
	}

	public void setListaeditar(ListaPrecioBean listaeditar) {
		this.listaeditar = listaeditar;
	}
	
	
	
	public List<ProductoPrecio> getProductos3() {
		return productos3;
	}

	public void setProductos3(List<ProductoPrecio> productos3) {
		this.productos3 = productos3;
	}
	
	public void onCellEdit(CellEditEvent event) {
	     /*   Object oldValue = event.getOldValue();
	        Object newValue = event.getNewValue();
	         
	        if(newValue != null && !newValue.equals(oldValue)) {
	            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
	            FacesContext.getCurrentInstance().addMessage(null, msg);
	        }*/
	    }
	
	

	public List<ProductoPrecio> getListaDetallesFiltrada() {
		return listaDetallesFiltrada;
	}

	public void setListaDetallesFiltrada(List<ProductoPrecio> listaDetallesFiltrada) {
		this.listaDetallesFiltrada = listaDetallesFiltrada;
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
		  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("editarListaMB");
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
