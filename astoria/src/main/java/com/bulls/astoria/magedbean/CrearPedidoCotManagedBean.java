package com.bulls.astoria.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
////import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bulls.astoria.enumeracion.EnuDominio;
import com.bulls.astoria.persistence.Cliente;
import com.bulls.astoria.persistence.Comision;
import com.bulls.astoria.persistence.Composicion;
import com.bulls.astoria.persistence.DetallePedido;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.Handler;
import com.bulls.astoria.persistence.Pedido;
import com.bulls.astoria.persistence.Persona;
import com.bulls.astoria.persistence.Plantacion;
import com.bulls.astoria.persistence.PlantacionProducto;
import com.bulls.astoria.persistence.ProductoGrados;
import com.bulls.astoria.persistence.Role;
import com.bulls.astoria.persistence.Truck;
import com.bulls.astoria.persistence.User;
import com.bulls.astoria.pojo.NotaBean;
import com.bulls.astoria.pojo.PedidoBean;
import com.bulls.astoria.pojo.Producto;
import com.bulls.astoria.pojo.ProductoPrecio;
import com.bulls.astoria.service.ClienteService;
import com.bulls.astoria.service.DominioService;
import com.bulls.astoria.service.PedidoService;
import com.bulls.astoria.service.PersonaService;
import com.bulls.astoria.service.PlantacionService;
import com.bulls.astoria.service.UserService;
import com.bulls.astoria.utils.Convertidor;


@ManagedBean (name="pedidocotMB")
@SessionScoped
public class CrearPedidoCotManagedBean extends GeneralManagedBean implements Serializable{
	
	
	private Integer idCliente;
	private String codigoCliente;
	private String nombreCliente;
	private Integer idPais;
	private Integer idCiudad;
	private Integer idPaisDestino;
	private Integer idCiudadDestino;
	
	
	private Integer idGrado;
	private Integer idColor;
	private Integer idPlantacion;
	private Integer idProducto;
	
	private Integer cantidadxfull;
	private Double cantidadfull;
	private Double precio;
	private String observaciones;
	private Integer packing;
	
	private Date fechapedido = new Date();
	private Date fechadespacho;
	private Date fechallegada;
	private Date fechallegadafinal;
	
	private boolean programado;
	private boolean lunes;
	private boolean martes;
	private boolean miercoles;
	private boolean jueves;
	private boolean viernes;
	private boolean sabado;
	private boolean domingo;
	
	ResourceBundle bundle ;
	
	List <Dominio> paises;
	List <Dominio> ciudades;
	List <Dominio> ciudadesDestino;
	List <Dominio> tiposDocumento;
	List <Persona> empleados;
	List <Truck> trucks;
	List <Handler> handlers;
	List<Truck> selectedTrucks;
	List<Handler> selectedHandlers;
	List <SelectItem> listaPaisesDom;
	List <SelectItem> listaCiudadesDom;
	List <SelectItem> listaCiudadesDestinoDom;
	List<SelectItem> listaTipodocumentoDom;
	List<SelectItem> listaEmpleados;
	List <Plantacion>  listaPlantaciones;
	List <SelectItem> listaPlantacionesSelect;
	List <SelectItem> productosPlantacionSelect;
	List<Cliente> clientes;
	List <SelectItem> clientesSelect;
	
	List <PedidoBean> detallesPedido;
	List <PedidoBean> detallesTemporal;
	

	List <Dominio> colores;
	List <Dominio> grados;
	List <SelectItem>  gradosSelect;
	List <SelectItem> coloresSelect;
	
	
	//productos
	
	List <Map> productosMap;
	List <Producto> productos;
	List <Producto> productos2;
	List <Producto> productos2filtrado;
	
	private Producto selectedProducto;
	
    String codigo; 
    String codigoaux;
    Integer cantidad;
    String nombreproducto;
    
    
    private Integer totalUnidades;
    private Double totalPrecio;
    private Double totalBoxes;
    
    private Double comision;
    private Double precioconcomision;
    
    private Integer item;
	
	
	@ManagedProperty(value="#{DominioService}")
	DominioService dominioService;
	
	@ManagedProperty(value="#{PersonaService}")
    PersonaService personaService;
	
	@ManagedProperty(value="#{UserService}")
    UserService userService;
	
	@ManagedProperty(value="#{ClienteService}")
    ClienteService clienteService;
	
	@ManagedProperty(value="#{PlantacionService}")
	PlantacionService plantacionService;
	
	@ManagedProperty(value="#{PedidoService}")
	PedidoService pedidoService;
	
	@ManagedProperty(value="#{uMB}")
	private UtilsManagedBean utils;
	
	
	
	
	@PostConstruct
	public void CrearPedidoCotManagedBean(){
		
		borrarSession();
		Object ptincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		bundle =  ResourceBundle.getBundle("messages");
		paises = dominioService.getDominios(EnuDominio.PAISES.getIdTipoDominio());
		ciudades = dominioService.getDominios(EnuDominio.CIUDADES.getIdTipoDominio());
		listaPaisesDom = Convertidor.dominiosToSelectdItems(paises);
		//listaCiudadesDom = Convertidor.dominiosToSelectdItems(ciudades);
		empleados = personaService.getEmpleados();
		trucks = dominioService.getTrucks();
		handlers = dominioService.getHandlers();
		
		
		tiposDocumento = dominioService.getDominios(EnuDominio.TIPOS_DOCUMENTO.getIdTipoDominio());
		listaTipodocumentoDom = Convertidor.dominiosToSelectdItems(tiposDocumento);
		listaEmpleados = Convertidor.personaToSelectdItems(empleados);
		
		listaPlantaciones = plantacionService.getPlantaciones();
		listaPlantacionesSelect = Convertidor.plantacionToSelectdItems(listaPlantaciones);	
		
		clientes = clienteService.getClientes();
		clientesSelect = Convertidor.clientesToSelectdItems(clientes);
		
		colores = dominioService.getDominios(EnuDominio.COLORES.getIdTipoDominio());
		grados =   dominioService.getDominios(EnuDominio.GRADOS.getIdTipoDominio());
		
		coloresSelect = Convertidor.dominiosToSelectdItems(colores);
		gradosSelect = Convertidor.dominiosToSelectdItems(grados);
		
		detallesPedido = new ArrayList<PedidoBean>();
		detallesTemporal = new ArrayList<PedidoBean>();
		
		//productos
		/*productosMap = dominioService.getProductosCompleto();
		productos = getProductosmap(productosMap);
		productos2 = getProductosmap(productosMap);*/
		
		buscarCliente();

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
			if(this.tienePrecioProducto(productosalida.getCodigo())){
				productossalida.add(productosalida);
			}
			

		}
		return productossalida;
	}
	
    public void getCiudadesPais(){

    	ciudades= dominioService.getDominiosXPadre(this.idPais);
    	listaCiudadesDom = Convertidor.dominiosToSelectdItems(ciudades);
    }
    
    public void getCiudadesPaisDestino(){

    	ciudadesDestino= dominioService.getDominiosXPadre(this.idPaisDestino);
    	listaCiudadesDestinoDom = Convertidor.dominiosToSelectdItems(ciudadesDestino);
    }
    
    public void getProductosPlantacion(){
    	/*productos = new ArrayList<ProductoPrecio>();
    	List <PlantacionProducto> lista = plantacionService.getProductosPlantacion(this.idPlantacion);
    	List <ProductoPrecio> productos = plantacionProductoToProducto(lista);
    	productosPlantacionSelect = Convertidor.productoPrecioToSelectdItems(productos);
    */	
    }
    
    public List <ProductoPrecio> plantacionProductoToProducto(List <PlantacionProducto> listaProductos){
		List <ProductoPrecio> lista = new ArrayList<ProductoPrecio>();
		for (PlantacionProducto pro :listaProductos){
			Dominio dom = dominioService.getDominio(pro.getIdproducto());
			ProductoPrecio producto = new ProductoPrecio (null,null,dom.getId(),dom.getNomcorto(),null,idGrado,"",idColor,"");
			lista.add(producto);
		}
		return lista;
		
	}
    
    public void handleKeyEvent() {
    	if(this.codigo != null){
    		this.nombreproducto = getNombreProducto(this.codigo);
    	}	
    }
    
    public void onRowSelect(SelectEvent event) {
     	String cod  = ((Producto)event.getObject()).getCodigo();
     	this.nombreproducto = getNombreProducto(cod);
    	this.codigo= cod;
    	this.getPrecioView();

    }
 
    public void onRowUnselect(UnselectEvent event) {

    }
    
    public String getNombreProductoAnterior(String codigo){
    	String nombre = null;
    	for(Producto productoaux:productos){
    		if(productoaux.getCodigo() != null){
    			if(productoaux.getCodigo().equalsIgnoreCase(codigo)){
    				nombre = productoaux.getCodigo() + " " +productoaux.getNombrePadre() + "  " + productoaux.getNombreProducto();
    			}
    		}	
    	}
    	return nombre;
    }
    
    public String getNombreProducto(String codigo){
    	String nombre = null;
    	com.bulls.astoria.persistence.Producto producto = dominioService.getProductoXCodigo(codigo);
    	Dominio dominio = dominioService.getDominio(producto.getIdvariedad());
    	Dominio dominioPadre = dominioService.getDominio(dominio.getDominiopadre());
    	nombre = codigo + " " + dominioPadre.getNomcorto() + " " + dominio.getNomcorto();
    	/*for(Producto productoaux:productos){
    		if(productoaux.getCodigo() != null){
    			if(productoaux.getCodigo().equalsIgnoreCase(codigo)){
    				nombre = productoaux.getCodigo() + " " +productoaux.getNombrePadre() + "  " + productoaux.getNombreProducto();
    			}
    		}	
    	}*/
    	return nombre;
    }
    
    
    public void subirDetalleAnt(){

    	PedidoBean pedidoaux = new PedidoBean();
    			
    		for(Producto productoaux:productos){
            	
            	if(this.codigo.equalsIgnoreCase(productoaux.getCodigo())){
            	
            	Dominio producto= dominioService.getDominio(productoaux.getIdProducto());
            	Dominio grado = dominioService.getDominio(productoaux.getGrado());
            	//Dominio color = dominioService.getDominio(productoaux.getColor());
            	Plantacion plantacion = null;
            	if(this.idPlantacion != null){
            		plantacion = plantacionService.getPlantacion(this.idPlantacion);
            	}
                
            	pedidoaux.setCodigo(this.codigo);
            	pedidoaux.setIdcolor(productoaux.getColor());
            	pedidoaux.setIdgrado(grado.getId());
            	pedidoaux.setIdplantacion(this.idPlantacion);
            	pedidoaux.setIdvariedad(producto.getId());
            	pedidoaux.setNombrevariedad(producto.getNomcorto());
            	pedidoaux.setNombretipo((dominioService.getDominio(dominioService.getDominio(producto.getId()).getDominiopadre())).getNomcorto());
            	//pedido.setNombrecolor(color.getNomcorto());
            	pedidoaux.setNombregrado(grado.getNomcorto());
            	if(plantacion != null ){
            		pedidoaux.setNombreplantacion(plantacion.getNombre());
            	}
            	pedidoaux.setUnidades(this.cantidadxfull);
            	pedidoaux.setTallosporfull(this.cantidadxfull);
            	pedidoaux.setPrecio(this.precio);
            	pedidoaux.setComision(this.comision);
            	pedidoaux.setPreciototal((this.precio + this.comision) * this.cantidadxfull);
            	detallesTemporal.add(pedidoaux);
            	break;
            	}
            }

    	return;
    	
    }
    
    public void subirDetalle(){

    	PedidoBean pedidoaux = new PedidoBean();
    	com.bulls.astoria.persistence.Producto productodb = dominioService.getProductoXCodigo(this.codigo);
    	Dominio producto = dominioService.getDominio(productodb.getIdvariedad());
    	Dominio dominioPadre = dominioService.getDominio(producto.getDominiopadre());
    	Dominio grado = dominioService.getDominio(productodb.getIdgrado());
    			
    	//	for(Producto productoaux:productos){
            	
            	//if(this.codigo.equalsIgnoreCase(productoaux.getCodigo())){
            	
            	//Dominio producto= dominioService.getDominio(productoaux.getIdProducto());
            	//Dominio grado = dominioService.getDominio(productoaux.getGrado());
            	//Dominio color = dominioService.getDominio(productoaux.getColor());
            	Plantacion plantacion = null;
            	if(this.idPlantacion != null){
            		plantacion = plantacionService.getPlantacion(this.idPlantacion);
            	}
                
            	pedidoaux.setCodigo(this.codigo);
            	// estaba descomentariado pedidoaux.setIdcolor(productoaux.getColor());
            	pedidoaux.setIdgrado(grado.getId());
            	pedidoaux.setIdplantacion(this.idPlantacion);
            	pedidoaux.setIdvariedad(producto.getId());
            	pedidoaux.setNombrevariedad(producto.getNomcorto());
            	pedidoaux.setNombretipo((dominioService.getDominio(dominioService.getDominio(producto.getId()).getDominiopadre())).getNomcorto());
            	//pedido.setNombrecolor(color.getNomcorto());
            	pedidoaux.setNombregrado(grado.getNomcorto());
            	if(plantacion != null ){
            		pedidoaux.setNombreplantacion(plantacion.getNombre());
            	}
            	pedidoaux.setUnidades(this.cantidadxfull);
            	pedidoaux.setTallosporfull(this.cantidadxfull);
            	pedidoaux.setPrecio(this.precio);
            	pedidoaux.setComision(this.comision);
            	pedidoaux.setPreciototal((this.precio + this.comision) * this.cantidadxfull);
            	detallesTemporal.add(pedidoaux);
            	//break;
            	//}
      //      }

    	return;
    	
    }
    public void subirAnterior(){
    	List<PedidoBean> listaDetalles = new ArrayList <PedidoBean> ();
    	PedidoBean pedido = new PedidoBean();
    	pedido.setDetalle(true);
    	
    	int idDetalle = this.generarAleatorio();
    	pedido.setIddetalle(idDetalle);
    	pedido.setPacking(this.packing);
    	//ponerlo mismo en talos por full
    	//pedido.setTallosporfull(this.packing);
    	pedido.setCantidadfull(this.cantidadfull);
    	pedido.setUnidades(new Double(this.packing * this.cantidadfull).intValue());
    	pedido.setTallosporfull(new Double(this.packing * this.cantidadfull).intValue());
    	double preciototal= 0.0;
    	
    	if(validarSubir()){
    	 	
    		for(PedidoBean detalletemporal: detallesTemporal){
    			int idComposicion = this.generarAleatorio();
    			PedidoBean itemcomposicion = new PedidoBean();
    			itemcomposicion.setIddetalle(idDetalle);
    			itemcomposicion.setIdcomposicion(idComposicion);
    			itemcomposicion.setCodigo(detalletemporal.getCodigo());
    			itemcomposicion.setUnidades(detalletemporal.getUnidades());

    			
    		for(Producto productoaux:productos){
            	
            	if(detalletemporal.getCodigo().equalsIgnoreCase(productoaux.getCodigo())){
            	
            	//los datos de pedido,detalle y composici�n

            	
            	
            	Dominio producto= dominioService.getDominio(productoaux.getIdProducto());
            	Dominio grado = dominioService.getDominio(productoaux.getGrado());
            	//Dominio color = dominioService.getDominio(productoaux.getColor());
            	Plantacion plantacion = null;
            	if(this.idPlantacion != null){
            		plantacion = plantacionService.getPlantacion(this.idPlantacion);
            	}
                
            	
            	itemcomposicion.setIdcolor(productoaux.getColor());
            	itemcomposicion.setIdgrado(productoaux.getGrado());
            	itemcomposicion.setIdplantacion(this.idPlantacion);
            	itemcomposicion.setIdvariedad(producto.getId());
            	itemcomposicion.setNombrevariedad(producto.getNomcorto());
            	itemcomposicion.setNombretipo((dominioService.getDominio(dominioService.getDominio(producto.getId()).getDominiopadre())).getNomcorto());
            	//pedido.setNombrecolor(color.getNomcorto());
            	itemcomposicion.setNombregrado(grado.getNomcorto());
            	if(plantacion != null ){
            		itemcomposicion.setNombreplantacion(plantacion.getNombre());
            	}
            	itemcomposicion.setUnidades(detalletemporal.getUnidades());
            	itemcomposicion.setTallosporfull(detalletemporal.getUnidades());
            	itemcomposicion.setPrecio(detalletemporal.getPrecio());
            	itemcomposicion.setComision(detalletemporal.getComision());
            	itemcomposicion.setPreciototal((detalletemporal.getPrecio() + detalletemporal.getComision())* detalletemporal.getUnidades());
            	preciototal = preciototal + ((detalletemporal.getPrecio() + detalletemporal.getComision())* detalletemporal.getUnidades());
            	listaDetalles.add(itemcomposicion);
            	//pone en temporal para luego poner toda la lista al final del primer item totalizador
            	break;
            	}
            }
    		}
    		pedido.setPreciototal(preciototal);
    		if(listaDetalles.size() >1){
    			pedido.setTipocomposicion("M");
    			pedido.setNombrecomposicion("Mixto");
    		}else {
    			pedido.setTipocomposicion("S");
    			pedido.setNombrecomposicion("Solido");
    		}
    		detallesPedido.add(pedido);
    		detallesPedido.addAll(listaDetalles);
    		reset();
    		//getTotales(detallesPedido);
    		this.getNuevaLista();
    		
    	}
    	return;
    	
    }
    
    public void subir(){
    	List<PedidoBean> listaDetalles = new ArrayList <PedidoBean> ();
    	PedidoBean pedido = new PedidoBean();
    	pedido.setDetalle(true);
    	
    	int idDetalle = this.generarAleatorio();
    	pedido.setIddetalle(idDetalle);
    	pedido.setPacking(this.packing);
    	//ponerlo mismo en talos por full
    	//pedido.setTallosporfull(this.packing);
    	pedido.setCantidadfull(this.cantidadfull);
    	pedido.setUnidades(new Double(this.packing * this.cantidadfull).intValue());
    	pedido.setTallosporfull(new Double(this.packing * this.cantidadfull).intValue());
    	double preciototal= 0.0;
    	
    	if(validarSubir()){
    	 	
    		for(PedidoBean detalletemporal: detallesTemporal){
    			int idComposicion = this.generarAleatorio();
    			PedidoBean itemcomposicion = new PedidoBean();
    			itemcomposicion.setIddetalle(idDetalle);
    			itemcomposicion.setIdcomposicion(idComposicion);
    			itemcomposicion.setCodigo(detalletemporal.getCodigo());
    			itemcomposicion.setUnidades(detalletemporal.getUnidades());
    			
    			
    			com.bulls.astoria.persistence.Producto productodb = dominioService.getProductoXCodigo(detalletemporal.getCodigo());
    	    	Dominio productoaux = dominioService.getDominio(productodb.getIdvariedad());
    	    	//Dominio dominioPadre = dominioService.getDominio(productoaux.getDominiopadre());
    	    	Dominio grado = dominioService.getDominio(productodb.getIdgrado());

    			
    	//	for(Producto productoaux:productos){
            	
        //    	if(detalletemporal.getCodigo().equalsIgnoreCase(productoaux.getCodigo())){
            	
            	//los datos de pedido,detalle y composición

            	
            	
            	Dominio producto= dominioService.getDominio(productoaux.getId());
            // estaba descomentariado	Dominio grado = dominioService.getDominio(productoaux.getGrado());
            	//Dominio color = dominioService.getDominio(productoaux.getColor());
            	Plantacion plantacion = null;
            	if(this.idPlantacion != null){
            		plantacion = plantacionService.getPlantacion(this.idPlantacion);
            	}
                
            	
            //	itemcomposicion.setIdcolor(productoaux.getColor());
            	itemcomposicion.setIdgrado(grado.getId());
            	itemcomposicion.setIdplantacion(this.idPlantacion);
            	itemcomposicion.setIdvariedad(producto.getId());
            	itemcomposicion.setNombrevariedad(producto.getNomcorto());
            	itemcomposicion.setNombretipo((dominioService.getDominio(dominioService.getDominio(producto.getId()).getDominiopadre())).getNomcorto());
            	//pedido.setNombrecolor(color.getNomcorto());
            	itemcomposicion.setNombregrado(grado.getNomcorto());
            	if(plantacion != null ){
            		itemcomposicion.setNombreplantacion(plantacion.getNombre());
            	}
            	itemcomposicion.setUnidades(detalletemporal.getUnidades());
            	itemcomposicion.setTallosporfull(detalletemporal.getUnidades());
            	itemcomposicion.setPrecio(detalletemporal.getPrecio());
            	itemcomposicion.setComision(detalletemporal.getComision());
            	itemcomposicion.setPreciototal((detalletemporal.getPrecio() + detalletemporal.getComision())* detalletemporal.getUnidades());
            	preciototal = preciototal + ((detalletemporal.getPrecio() + detalletemporal.getComision())* detalletemporal.getUnidades());
            	listaDetalles.add(itemcomposicion);
            	//pone en temporal para luego poner toda la lista al final del primer item totalizador
           // 	break;
            //	}
         //   }
    		}
    		pedido.setPreciototal(preciototal);
    		if(listaDetalles.size() >1){
    			pedido.setTipocomposicion("M");
    			pedido.setNombrecomposicion("Mixto");
    		}else {
    			pedido.setTipocomposicion("S");
    			pedido.setNombrecomposicion("Solido");
    		}
    		//se lo habia agregado antes pedido.setComposiciones(listaDetalles);
    		detallesPedido.add(pedido);
    		detallesPedido.addAll(listaDetalles);
    		
    		//generar una lista con el pedido y sus detalles dentro de el.
    		
    		reset();
    		this.getNuevaLista();
    		
    	}
    	return;
    	
    }
    
    public void getNuevaLista(){
    	List<PedidoBean> arraycabezote = detallesPedido;
    	List<PedidoBean> arrayinterior = detallesPedido;
    	List<PedidoBean> detallesPedidoNueva = new ArrayList<PedidoBean>();
    	item=0;
     	for(PedidoBean pedidomod : arraycabezote){
        	List<PedidoBean> detallesPedidoNuevaDetalles = new ArrayList<PedidoBean>();
    		PedidoBean pedidomodnuevo = new PedidoBean();
    		pedidomodnuevo = pedidomod;
    		Double preciototalint = new Double(0.0);
    		Integer unidades = new Integer(0);
    		
    		if(pedidomod.isDetalle()){
    			item=++item;
    			pedidomod.setNum(item);
    			for(PedidoBean pedidomodint : arrayinterior){
    				if(!pedidomodint.isDetalle() && (pedidomodint.getIddetalle().intValue() == pedidomod.getIddetalle().intValue())){
    					PedidoBean pedidointnuevo = new PedidoBean();
        				pedidointnuevo = pedidomodint;
        				pedidointnuevo.setPreciototal(pedidomodint.getTallosporfull() * (pedidomodint.getPrecio() + pedidomodint.getComision()));
        				detallesPedidoNuevaDetalles.add(pedidointnuevo);
    					//para calcular el total arriba
    					preciototalint = preciototalint + (pedidomodint.getTallosporfull() * (pedidomodint.getPrecio() + pedidomodint.getComision()));
    					unidades = unidades + pedidointnuevo.getTallosporfull();
    				}
    			}
    			//setea el precio total calculado
    			pedidomodnuevo.setPreciototal(preciototalint);
    			//setea las unidades calculadas
    			pedidomodnuevo.setTallosporfull(unidades);
    			//setea el encabezado
    			detallesPedidoNueva.add(pedidomodnuevo);
    			//setea los detalles
    			detallesPedidoNueva.addAll(detallesPedidoNuevaDetalles);
    		}
    		
    	}
     	
     	detallesPedido = detallesPedidoNueva;
     	getTotales(detallesPedido);
		
    }
    
    public void reset(){
    	
    	
    	this.cantidad = null;
    	this.cantidadfull=null;
    	this.cantidadxfull=null;
    	this.codigo=null;
    	this.nombreproducto=null;
    	this.precio=null;
    	this.packing=null;
    	this.idPlantacion=0;
    	this.comision = null;
    	this.precioconcomision = null;
    	detallesTemporal = new ArrayList<PedidoBean>();
    	
    }
    
  public void multiplica(){
	  if(this.packing != null && this.cantidadfull != null){
		    Double prev = this.packing * this.cantidadfull;
		  	this.cantidad =  prev.intValue();
	  }
	 return;
  }
  
  public void multiplica2(){
	  
	 return;
  }
  
  public void multiplica3(){
	  this.precioconcomision =  this.precio + this.comision;
  }
   public boolean validarSubir(){
	   boolean ok = true;
	  /* if(this.codigo == null){
		   ok=false;
		   FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.codigo") + ":","Debe seleccionar una producto"));
       }else if (this.codigo.equalsIgnoreCase("")){
    	   ok=false;
		   FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.codigo") + ":","Debe seleccionar una producto"));
       }else if(!validarCodigo(this.codigo)){
    	   ok=false;
		   FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.codigo") + ":","Codigo incorrecto"));   
       }
       else */if(this.packing == null){
    	   ok=false;
		   FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.packing") + ":","Packing debe digitar un valor para el Packing"));   
       }
	   if (!validarPacking(detallesTemporal)){
		   ok=false;
		   FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.packing") + ":","( Packing * Boxes ) debe ser igual al total de unidades")); 
	   }
	   return ok;
   }
   
  
   
   public boolean validarCodigo(String codigo){
	   for(Producto productoaux:productos){
		   if(codigo.equalsIgnoreCase(productoaux.getCodigo())){
			   return true;
		   }
	   }
	   return false;
   }
   
   public void alertaPrecio(){
		 
		 
		 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errorobtenerprecio"));
			//RequestContext.getCurrentInstance().showMessageInDialog(message);
		 PrimeFaces.current().dialog().showMessageDynamic(message);
			return;
	 }
   
   public void alertaComision(){
		 
		 
		 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errorobtenercomision"));
		 //RequestContext.getCurrentInstance().showMessageInDialog(message);
		 PrimeFaces.current().dialog().showMessageDynamic(message);
		 return;
	 }
    
public String onFlowProcess(FlowEvent event) {
		
		boolean ok = true;
		
		if(this.idCliente == null){
			 
 			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.cliente") + ":",bundle.getString("errordebecliente")));
 			 ok = false;
		}
		if(this.idPais == null){
			 
 			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.pais") + ":",bundle.getString("errordebepais")));
 			 ok = false;
		}
		if(this.idCiudad == null){
			 
 			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.ciudad") + ":",bundle.getString("errordebeciudad")));
 			 ok = false;
		}
		
		if(this.idPaisDestino == 0){
			 
 			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.paisdestino") + ":",bundle.getString("errordebepais")));
 			 ok = false;
		}
		if(this.idCiudadDestino == 0){
			 
 			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.ciudaddestino") + ":",bundle.getString("errordebeciudad")));
 			 ok = false;
		}
		
		/*if(this.fechadespacho == null || this.fechallegada == null || this.fechallegadafinal == null){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("Fechas") + ":","Las fechas deben tener valores estimados"));
 			 ok = false;
		}else {
			if(this.fechadespacho.compareTo(new Date())< 0){
			 
				FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.fechadespacho") + ":",bundle.getString("errorfechadespacho")));
 				ok = false;
			}
			if(this.fechallegada.compareTo(new Date())< 0){
			 
				FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.fechallegada") + ":",bundle.getString("errorfechallegada")));
				ok = false;
			}
			if(this.fechallegadafinal.compareTo(new Date())< 0){
			 
				FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.fechallegadafinal") + ":",bundle.getString("errorfechallegadafinal")));
				ok = false;
			}
		}*/	
		if(ok){
			listaPlantaciones = plantacionService.getPlantacionesPais(this.idPais);
			listaPlantacionesSelect = Convertidor.plantacionToSelectdItems(listaPlantaciones);
			return event.getNewStep();
			
		}else {
			return "generales";
		}
		
	}
    
    public String crearPedido(){
    	
    	Pedido pedido = new Pedido();
    	pedido.setComprador(null);
    	pedido.setEstado('S');//Recibido conciliado despachado cancelado cotizado (s)

    	pedido.setFechadespacho(this.fechadespacho);
    	pedido.setFechallegada(this.fechallegada);
    	pedido.setFechallegadafinal(this.fechallegadafinal);
    	pedido.setFechapedido(this.fechapedido);
    	pedido.setIdciudad(this.idCiudad);
    	pedido.setIdciudaddestino(this.idCiudadDestino);
    	pedido.setIdpais(this.idPais);
    	pedido.setIdpaisdestino(this.idPaisDestino);
    	pedido.setIdcliente(this.idCliente);
    	pedido.setObservaciones(this.observaciones);
    	pedido.setVendedor(null);
    	
    	pedido.setProgramado(this.programado);
    	pedido.setLunes(this.lunes);
    	pedido.setMartes(this.martes);
    	pedido.setMiercoles(this.miercoles);
    	pedido.setJueves(this.jueves);
    	pedido.setViernes(this.viernes);
    	pedido.setSabado(this.sabado);
    	pedido.setDomingo(this.domingo);
    	
    	
    	List <PedidoBean> detallesPedidoAux = detallesPedido;
    	List <DetallePedido> listasalida = new ArrayList <DetallePedido>();
    	
    	DetallePedido detalle = new DetallePedido();
    	for(PedidoBean pedidoBean:detallesPedido){

     		if(pedidoBean.isDetalle()){
    			detalle = new DetallePedido();
    			detalle.setCantidadfull(pedidoBean.getCantidadfull());
        		detalle.setCantidadporfull(pedidoBean.getPacking());
        		//detalle.setCantidadtotal(pedidoBean.getCantidadtotal());
        		Double prev = pedidoBean.getCantidadfull() * pedidoBean.getPacking();
        		detalle.setCantidadtotal(prev.intValue());
        		if(pedidoBean.getIdplantacion()!= null)
        		detalle.setIdplantacion(pedidoBean.getIdplantacion());
        		//detalle.setIdproducto(dominioService.getProductoVariedadGrado(pedidoBean.getIdvariedad(), pedidoBean.getIdgrado()).getId());
        		detalle.setPedido(pedido);
        		Set set = new HashSet(getComposiciones(detallesPedidoAux,pedidoBean.getIddetalle(),detalle));
        		detalle.setComposiciones(set);
        		listasalida.add(detalle);
    			
    		}    		
    	}
    	Set<com.bulls.astoria.persistence.DetallePedido> targeDetalles = new HashSet<com.bulls.astoria.persistence.DetallePedido>(listasalida);
    	pedido.setDetalles(targeDetalles);
    	//salvar
    	pedidoService.savePedido(pedido);
    	utils.eviarCorreo(bundle.getString("mensaje.nuevopedido") + ":" + pedido.getId());
    	this.putAuditoria("Crear Pedido", "C", "Creo Pedido as� : - " + pedido.toString());
    	abrirConfirmacion(pedido.getId());
    	return "listapedidos";
    }
    
    public List<Composicion> getComposiciones(List<PedidoBean> detalles, Integer idDetalle,DetallePedido detalle){
    	List <Composicion> composiciones = new ArrayList<Composicion>();
    	double  precio = 0.0;
    	Integer unidades = 0;
    	for(PedidoBean pedidoBean : detalles){
    		
    		if((pedidoBean.getIddetalle().intValue() == idDetalle.intValue()) && !pedidoBean.isDetalle()){
    			
    			Composicion composicion = new Composicion();
    			composicion.setCantidadporfull(pedidoBean.getTallosporfull());
    			//composicion.setIdproducto(pedidoBean.getIdvariedad());
    			composicion.setIdproducto((dominioService.getProductoVariedadGrado(pedidoBean.getIdvariedad(), pedidoBean.getIdgrado())).getId());
    			composicion.setPrecio(pedidoBean.getPrecio());
    			composicion.setComision(pedidoBean.getComision());
    			composicion.setDetalle(detalle);
    			//llenarcomposicion
    			composiciones.add(composicion);
    			detalle.setIdplantacion(pedidoBean.getIdplantacion());
    			precio = precio + ((pedidoBean.getPrecio() + pedidoBean.getComision())*pedidoBean.getTallosporfull());
    			unidades = unidades + pedidoBean.getTallosporfull();
    		}			
    	}	
    	detalle.setCantidadtotal(unidades);
    	detalle.setPrecio(precio);
	
    	return composiciones;
    }
    
    private void abrirConfirmacion(Integer id){
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmacrearpedido")+id);
		//RequestContext.getCurrentInstance().showMessageInDialog(message);
		PrimeFaces.current().dialog().showMessageDynamic(message);
		return;

	}
    
    public void eliminar(PedidoBean pedido){
    	detallesTemporal.remove(pedido);
    	return;
    }
    public void eliminar2(PedidoBean pedido){
    	
    	List <PedidoBean> detallesPedidoAux = detallesPedido;
    	List <PedidoBean> detallesPedidoIndex = new ArrayList<PedidoBean>();
    	int index= 0;
    	for(PedidoBean pedidoaux:detallesPedido){
    		if(pedidoaux.getIddetalle().intValue() == pedido.getIddetalle().intValue()){
    			detallesPedidoIndex.add(pedidoaux);
    		}
    		index ++;
    	}

    	detallesPedido.removeAll(detallesPedidoIndex);
        getTotales(detallesPedido);
    	
    	return;
    }
    
    public void getPackingPlantacion(){
    	Integer cant = 0;
    	if(this.idPlantacion != null){
    		if(this.idPlantacion.intValue() != 0){
    			Plantacion plan = plantacionService.getPlantacion(this.idPlantacion);
    			cant = plan.getCantidadxfull();
    			this.packing = cant;
    			getProductos2Plantacion(this.idPlantacion);
    		}	
    	}	
    	this.packing = cant;
    	multiplica();
    }
    
public void getProductos2Plantacion(Integer idPlantacion){
		
		productosMap = dominioService.getProductosCompletoPlantacion(idPlantacion);
		//productos = getProductosmap(productosMap);
		productos2 = getProductosmap(productosMap);
        return; 
	}
    
    public boolean validarPacking(List<PedidoBean> detallesTemporalValidar){
 	   
        boolean ok = true;
        Integer cant = 0;
		for(PedidoBean detalletemporal: detallesTemporalValidar){
			 cant = cant + detalletemporal.getUnidades();
		}
		if(cant.intValue() != (this.packing * this.cantidadfull)){
			ok= false;
		}
	   return ok;
   }
    
    public Double getTotalPrecio(){
    	return this.totalPrecio;
    }
    
    public Integer getTotalUnidades(){
    	return this.totalUnidades;
    }
    
    public void programar(){
    	System.out.println("programando");
    	
    }

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public Integer getIdPais() {
		return idPais;
	}

	public void setIdPais(Integer idPais) {
		this.idPais = idPais;
	}

	public Integer getIdCiudad() {
		return idCiudad;
	}

	public void setIdCiudad(Integer idCiudad) {
		this.idCiudad = idCiudad;
	}

	public Integer getIdGrado() {
		return idGrado;
	}

	public void setIdGrado(Integer idGrado) {
		this.idGrado = idGrado;
	}

	public Integer getIdColor() {
		return idColor;
	}

	public void setIdColor(Integer idColor) {
		this.idColor = idColor;
	}

	public Integer getIdPlantacion() {
		return idPlantacion;
	}

	public void setIdPlantacion(Integer idPlantacion) {
		this.idPlantacion = idPlantacion;
	}

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public Integer getCantidadxfull() {
		return cantidadxfull;
	}

	public void setCantidadxfull(Integer cantidadxfull) {
		this.cantidadxfull = cantidadxfull;
	}

	public Double getCantidadfull() {
		return cantidadfull;
	}

	public void setCantidadfull(Double cantidadfull) {
		this.cantidadfull = cantidadfull;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Date getFechapedido() {
		return fechapedido;
	}

	public void setFechapedido(Date fechapedido) {
		this.fechapedido = fechapedido;
	}

	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	public List<Dominio> getPaises() {
		return paises;
	}

	public void setPaises(List<Dominio> paises) {
		this.paises = paises;
	}

	public List<Dominio> getCiudades() {
		return ciudades;
	}

	public void setCiudades(List<Dominio> ciudades) {
		this.ciudades = ciudades;
	}

	public List<Dominio> getTiposDocumento() {
		return tiposDocumento;
	}

	public void setTiposDocumento(List<Dominio> tiposDocumento) {
		this.tiposDocumento = tiposDocumento;
	}

	public List<Persona> getEmpleados() {
		return empleados;
	}

	public void setEmpleados(List<Persona> empleados) {
		this.empleados = empleados;
	}

	public List<Truck> getTrucks() {
		return trucks;
	}

	public void setTrucks(List<Truck> trucks) {
		this.trucks = trucks;
	}

	public List<Handler> getHandlers() {
		return handlers;
	}

	public void setHandlers(List<Handler> handlers) {
		this.handlers = handlers;
	}

	public List<Truck> getSelectedTrucks() {
		return selectedTrucks;
	}

	public void setSelectedTrucks(List<Truck> selectedTrucks) {
		this.selectedTrucks = selectedTrucks;
	}

	public List<Handler> getSelectedHandlers() {
		return selectedHandlers;
	}

	public void setSelectedHandlers(List<Handler> selectedHandlers) {
		this.selectedHandlers = selectedHandlers;
	}

	public List<SelectItem> getListaPaisesDom() {
		return listaPaisesDom;
	}

	public void setListaPaisesDom(List<SelectItem> listaPaisesDom) {
		this.listaPaisesDom = listaPaisesDom;
	}

	public List<SelectItem> getListaCiudadesDom() {
		return listaCiudadesDom;
	}

	public void setListaCiudadesDom(List<SelectItem> listaCiudadesDom) {
		this.listaCiudadesDom = listaCiudadesDom;
	}

	public List<SelectItem> getListaTipodocumentoDom() {
		return listaTipodocumentoDom;
	}

	public void setListaTipodocumentoDom(List<SelectItem> listaTipodocumentoDom) {
		this.listaTipodocumentoDom = listaTipodocumentoDom;
	}

	public List<SelectItem> getListaEmpleados() {
		return listaEmpleados;
	}

	public void setListaEmpleados(List<SelectItem> listaEmpleados) {
		this.listaEmpleados = listaEmpleados;
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

	public List<SelectItem> getProductosPlantacionSelect() {
		return productosPlantacionSelect;
	}

	public void setProductosPlantacionSelect(
			List<SelectItem> productosPlantacionSelect) {
		this.productosPlantacionSelect = productosPlantacionSelect;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<SelectItem> getClientesSelect() {
		return clientesSelect;
	}

	public void setClientesSelect(List<SelectItem> clientesSelect) {
		this.clientesSelect = clientesSelect;
	}

	public List<PedidoBean> getDetallesPedido() {
		return detallesPedido;
	}

	public void setDetallesPedido(List<PedidoBean> detallesPedido) {
		this.detallesPedido = detallesPedido;
	}

	

	public DominioService getDominioService() {
		return dominioService;
	}

	public void setDominioService(DominioService dominioService) {
		this.dominioService = dominioService;
	}

	public PersonaService getPersonaService() {
		return personaService;
	}

	public void setPersonaService(PersonaService personaService) {
		this.personaService = personaService;
	}

	public ClienteService getClienteService() {
		return clienteService;
	}

	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	public PlantacionService getPlantacionService() {
		return plantacionService;
	}

	public void setPlantacionService(PlantacionService plantacionService) {
		this.plantacionService = plantacionService;
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigoaux() {
		return codigoaux;
	}

	public void setCodigoaux(String codigoaux) {
		this.codigoaux = codigoaux;
	}

	public Producto getSelectedProducto() {
		return selectedProducto;
	}

	public void setSelectedProducto(Producto selectedProducto) {
		this.selectedProducto = selectedProducto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getNombreproducto() {
		return nombreproducto;
	}

	public void setNombreproducto(String nombreproducto) {
		this.nombreproducto = nombreproducto;
	}

	public List<Producto> getProductos2() {
		return productos2;
	}

	public void setProductos2(List<Producto> productos2) {
		this.productos2 = productos2;
	}

	public Date getFechadespacho() {
		return fechadespacho;
	}

	public void setFechadespacho(Date fechadespacho) {
		this.fechadespacho = fechadespacho;
	}

	public Date getFechallegada() {
		return fechallegada;
	}

	public void setFechallegada(Date fechallegada) {
		this.fechallegada = fechallegada;
	}

	public Date getFechallegadafinal() {
		return fechallegadafinal;
	}

	public void setFechallegadafinal(Date fechallegadafinal) {
		this.fechallegadafinal = fechallegadafinal;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public PedidoService getPedidoService() {
		return pedidoService;
	}

	public void setPedidoService(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}
	
	
	
	public boolean isProgramado() {
		return programado;
	}

	public void setProgramado(boolean programado) {
		this.programado = programado;
	}

	public boolean isLunes() {
		return lunes;
	}

	public void setLunes(boolean lunes) {
		this.lunes = lunes;
	}

	public boolean isMartes() {
		return martes;
	}

	public void setMartes(boolean martes) {
		this.martes = martes;
	}

	public boolean isMiercoles() {
		return miercoles;
	}

	public void setMiercoles(boolean miercoles) {
		this.miercoles = miercoles;
	}

	public boolean isJueves() {
		return jueves;
	}

	public void setJueves(boolean jueves) {
		this.jueves = jueves;
	}

	public boolean isViernes() {
		return viernes;
	}

	public void setViernes(boolean viernes) {
		this.viernes = viernes;
	}

	public boolean isSabado() {
		return sabado;
	}

	public void setSabado(boolean sabado) {
		this.sabado = sabado;
	}

	public boolean isDomingo() {
		return domingo;
	}

	public void setDomingo(boolean domingo) {
		this.domingo = domingo;
	}
	
	public Integer getPacking() {
		return packing;
	}

	public void setPacking(Integer packing) {
		this.packing = packing;
	}

	public List<PedidoBean> getDetallesTemporal() {
		return detallesTemporal;
	}

	public void setDetallesTemporal(List<PedidoBean> detallesTemporal) {
		this.detallesTemporal = detallesTemporal;
	}
	
	

	public void setTotalUnidades(Integer totalUnidades) {
		this.totalUnidades = totalUnidades;
	}

	public void setTotalPrecio(Double totalPrecio) {
		this.totalPrecio = totalPrecio;
	}

	public void onCellEdit(CellEditEvent event) {
		//getNuevaLista(this.detallesPedido);
    }
	
	public int generarAleatorio(){
		Random  rnd = new Random();
		return rnd.nextInt();
		
	}

	public Double getPrecioProducto (Integer idPlantacion, Integer idProducto){
		
		return pedidoService.getPrecioProducto(idPlantacion, idProducto,this.fechadespacho);
	}
	
	public void getPrecioView(){
		boolean okprecio=false;
		boolean okcomision=false;
		Integer id = null;
		Double precio;
		Comision comision;
		com.bulls.astoria.persistence.Producto pro = dominioService.getProductoXCodigo(this.codigo);
		if(this.idPlantacion.intValue()!=0)
			id = this.idPlantacion;
		
		precio = this.getPrecioProducto(id, pro.getId());
		comision = dominioService.getComision(pro.getId(),this.idPais);
		if(precio == null){
			//alerta
			alertaPrecio();
		}else {
			okprecio= true;
			this.precio = precio;
		}
		if(comision == null){
			alertaComision();
		}else{
			okcomision=true;
			this.comision = comision.getComision();
    	}
		
		if(okprecio && okcomision){
			this.precioconcomision =  this.precio + this.comision;
		}
		
	}
	
	public boolean tienePrecioProducto(String codigo){
		boolean okprecio=false;
		boolean okcomision=false;
		Integer id = null;
		Double precio;
		Comision comision;
		com.bulls.astoria.persistence.Producto pro = dominioService.getProductoXCodigo(codigo);
		if(this.idPlantacion!=null)
			id = this.idPlantacion;
		
		precio = this.getPrecioProducto(id, pro.getId());
		comision = dominioService.getComision(pro.getId(),this.idPais);
		if(precio == null){
			return false;
		}else {
			okprecio= true;
		}
		if(comision == null){
			return false;
		}else{
			okcomision=true;
    	}
		
		return true;
		
	}
	
	public void getTotales(List <PedidoBean> detallesPedido){
		double precio = 0.0;
		int cantidad = 0;
		double cantboxes=0;
		for(PedidoBean pedido: detallesPedido){
			if(pedido.getTipocomposicion()!= null){
			if(pedido.getTallosporfull() != null)
				cantidad = cantidad + pedido.getTallosporfull();
			if(pedido.getTallosporfull() != null)
				precio = precio + pedido.getPreciototal();
			if(pedido.getCantidadfull() != null)
				cantboxes = cantboxes + pedido.getCantidadfull();
			}
					
		}
		this.totalPrecio = precio;
		this.totalUnidades = cantidad;
		this.totalBoxes = cantboxes;
		
	}

	public Integer getIdPaisDestino() {
		return idPaisDestino;
	}

	public void setIdPaisDestino(Integer idPaisDestino) {
		this.idPaisDestino = idPaisDestino;
	}

	public Integer getIdCiudadDestino() {
		return idCiudadDestino;
	}

	public void setIdCiudadDestino(Integer idCiudadDestino) {
		this.idCiudadDestino = idCiudadDestino;
	}

	public List<Dominio> getCiudadesDestino() {
		return ciudadesDestino;
	}

	public void setCiudadesDestino(List<Dominio> ciudadesDestino) {
		this.ciudadesDestino = ciudadesDestino;
	}

	public List<SelectItem> getListaCiudadesDestinoDom() {
		return listaCiudadesDestinoDom;
	}

	public void setListaCiudadesDestinoDom(List<SelectItem> listaCiudadesDestinoDom) {
		this.listaCiudadesDestinoDom = listaCiudadesDestinoDom;
	}

	public Double getComision() {
		return comision;
	}

	public void setComision(Double comision) {
		this.comision = comision;
	}

	public Double getPrecioconcomision() {
		return precioconcomision;
	}

	public void setPrecioconcomision(Double precioconcomision) {
		this.precioconcomision = precioconcomision;
	}
	
	
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void buscarCliente(){

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Persona per = personaService.getPersonaXusername(username);
		User user = userService.getUser(username);
		Role rol = user.getRole();
		//plantacion 3 cliente 4  carguera 5
		if(rol.getId() == 3){
			this.idCliente=0;
			
		}else if(rol.getId() == 4){
			this.idCliente=per.getIdCliente();
			Cliente cliente = clienteService.getClienteXId(this.idCliente);
			this.nombreCliente = cliente.getNombre();
		}else if(rol.getId() == 5){
			this.idCliente=0;
		}
	}
	
	public List<Producto> getProductos2filtrado() {
		return productos2filtrado;
	}

	public void setProductos2filtrado(List<Producto> productos2filtrado) {
		this.productos2filtrado = productos2filtrado;
	}
	
	

	public Double getTotalBoxes() {
		return totalBoxes;
	}

	public void setTotalBoxes(Double totalBoxes) {
		this.totalBoxes = totalBoxes;
	}

	public Integer getItem() {
		return item;
	}

	public void setItem(Integer item) {
		this.item = item;
	}
	
	

	public UtilsManagedBean getUtils() {
		return utils;
	}

	public void setUtils(UtilsManagedBean utils) {
		this.utils = utils;
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
		  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("pedidocotMB");
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
