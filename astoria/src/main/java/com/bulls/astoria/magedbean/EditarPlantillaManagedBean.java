package com.bulls.astoria.magedbean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
//import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.bulls.astoria.enumeracion.EnuDominio;
import com.bulls.astoria.persistence.Aerolinea;
import com.bulls.astoria.persistence.AgenciaCarga;
import com.bulls.astoria.persistence.Cliente;
import com.bulls.astoria.persistence.Comision;
import com.bulls.astoria.persistence.Composicion;
import com.bulls.astoria.persistence.Destino;
import com.bulls.astoria.persistence.DetallePedido;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.Empresa;
import com.bulls.astoria.persistence.Handler;
import com.bulls.astoria.persistence.Pedido;
import com.bulls.astoria.persistence.Persona;
import com.bulls.astoria.persistence.Plantacion;
import com.bulls.astoria.persistence.PlantacionProducto;
import com.bulls.astoria.persistence.Truck;
import com.bulls.astoria.pojo.EncabezadoPedidoBean;
import com.bulls.astoria.pojo.PedidoBean;
import com.bulls.astoria.pojo.Producto;
import com.bulls.astoria.pojo.ProductoPrecio;
import com.bulls.astoria.service.ClienteService;
import com.bulls.astoria.service.DominioService;
import com.bulls.astoria.service.PedidoService;
import com.bulls.astoria.service.PersonaService;
import com.bulls.astoria.service.PlantacionService;
import com.bulls.astoria.utils.Convertidor;

@ManagedBean (name="editarPlantillaMB")
@SessionScoped
public class EditarPlantillaManagedBean extends GeneralManagedBean implements Serializable{
	
	
	private Integer idPedido; 
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
	ResourceBundle bundleparam ;
	private String carpetajrxml;
	
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
	List <SelectItem> listaPlantacionesSelect2;
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
    private Double totalBoxes;
    private Double totalPrecio;
    
    private Double comision;
    private Double precioconcomision;
    
    private String concepto1trans;
    private String concepto2trans;
    private String concepto3trans;
    private String concepto4trans;
    private Double valorconcepto1trans;
    private Double valorconcepto2trans;
    private Double valorconcepto3trans;
    private Double valorconcepto4trans;
    
    private Double valorconcepto1transast;
    private Double valorconcepto2transast;
    private Double valorconcepto3transast;
    private Double valorconcepto4transast;
    
    Double preciototalflor = 0.0;
    Double preciototaltransporte = 0.0;
    Double preciototalcompleto = 0.0;
    
    Integer item;
    
    private String autorizo;
    
    private Empresa empresa;
    String boxesid;
    String client;
 //informacion bancaria
    
    String ib;
    String fb;
    String bb;
    
    String ibaddress;
    String ibswift;
    String ibaccount;
    
    
    String bbaddress;
    String bbswift;
    
    String fbaddress;
    String fbaccount;
    

	
	
	@ManagedProperty(value="#{DominioService}")
	DominioService dominioService;
	
	@ManagedProperty(value="#{PersonaService}")
    PersonaService personaService;
	
	@ManagedProperty(value="#{ClienteService}")
    ClienteService clienteService;
	
	@ManagedProperty(value="#{PlantacionService}")
	PlantacionService plantacionService;
	
	@ManagedProperty(value="#{PedidoService}")
	PedidoService pedidoService;
	
	@ManagedProperty(value="#{reportesMB}")
	private ReportesManagedBean reportes;
	
	
	@PostConstruct
	public void EditarPlantillaManagedBean(){
		
		borrarSession();
		empresa= dominioService.getEmpresa();
		
		bundle =  ResourceBundle.getBundle("messages");
		bundleparam =  ResourceBundle.getBundle("parametros");
		carpetajrxml = bundleparam.getString("rutajrxml");
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
		listaPlantacionesSelect2 = Convertidor.plantacionToSelectdItems(listaPlantaciones);
		
		clientes = clienteService.getClientes();
		clientesSelect = Convertidor.clientesToSelectdItems(clientes);
		
		colores = dominioService.getDominios(EnuDominio.COLORES.getIdTipoDominio());
		grados =   dominioService.getDominios(EnuDominio.GRADOS.getIdTipoDominio());
		
		coloresSelect = Convertidor.dominiosToSelectdItems(colores);
		gradosSelect = Convertidor.dominiosToSelectdItems(grados);
		
		detallesPedido = new ArrayList<PedidoBean>();
		
		//productos
		productosMap = dominioService.getProductosCompleto();
		productos = getProductosmap(productosMap);
		productos2 = getProductosmap(productosMap);
		detallesTemporal = new ArrayList<PedidoBean>();
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
    
    public String getNombreProducto(String codigo){
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
    
    public void subirDetalle(){

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
    	Double dev = this.packing * this.cantidadfull;
    	pedido.setUnidades(dev.intValue());
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
            	//pedido.setNombrecolor(color.getNomcorto());
            	itemcomposicion.setNombregrado(grado.getNomcorto());
            	if(plantacion != null ){
            		itemcomposicion.setNombreplantacion(plantacion.getNombre());
            		//la solucion itemcomposicion.setNombreplantacion(plantacion.getId() + "-" + plantacion.getNombre());
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
    			item = ++item;
        		pedidomodnuevo.setNum(item);
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
    
public void subirGeneral(List <DetallePedido> detalles){
	     
	     detallesPedido = new ArrayList<PedidoBean>();
    	 for(DetallePedido detalle:detalles){
    		 PedidoBean pedido = new PedidoBean();
    	     pedido.setDetalle(true);
    		 pedido.setCantidadfull(detalle.getCantidadfull());
    		 pedido.setPacking(detalle.getCantidadporfull());
    		 pedido.setCantidadtotal(detalle.getCantidadtotal());
    		 pedido.setPreciototal(detalle.getPrecio());
    		 pedido.setIddetalle(detalle.getId());
    		 pedido.setObservaciones(detalle.getObservaciones());
    		 Integer unidades = 0;
    		    List <PedidoBean> detallesPedidoAux = new ArrayList<PedidoBean>();
            	//consultamos las composiciones
            	List <Composicion> composiciones = pedidoService.getComposiciones(detalle.getId());
            	for(Composicion comp:composiciones){
            		
            		PedidoBean pedidoaux = new PedidoBean();
            		comp.getId();
            		comp.getIdproducto();
            		comp.getPrecio();
            		unidades = unidades +comp.getCantidadporfull();
            		
            		com.bulls.astoria.persistence.Producto productop = dominioService.getProductoXId(comp.getIdproducto());
            		
            		for(Producto productoaux:productos){
                    	
                    	if(productop.getCodigo().equalsIgnoreCase(productoaux.getCodigo())){
                    	
                    	Dominio producto= dominioService.getDominio(productop.getIdvariedad());
                    	Dominio grado = dominioService.getDominio(productop.getIdgrado());
                    	//Dominio color = dominioService.getDominio(productoaux.getColor());
                    	Plantacion plantacion = null;
                    	if(detalle.getIdplantacion() != null){
                    		plantacion = plantacionService.getPlantacion(detalle.getIdplantacion());
                    	}
                        
                    	//pedidoaux.setCodigo(comp.get);
                    	pedidoaux.setIdcolor(productoaux.getColor());
                    	pedidoaux.setIdgrado(grado.getId());
                    	pedidoaux.setIdvariedad(producto.getId());
                    	pedidoaux.setNombrevariedad(producto.getNomcorto());
                    	//pedido.setNombrecolor(color.getNomcorto());
                    	pedidoaux.setNombregrado(grado.getNomcorto());
                    	if(plantacion != null ){
                    		pedidoaux.setIdplantacion(plantacion.getId());
                    		pedidoaux.setNombreplantacion(plantacion.getNombre());
                    		
                    		//la solucion pedidoaux.setNombreplantacion(plantacion.getId() + "-" + plantacion.getNombre());
                    	}
                    	pedidoaux.setUnidades(comp.getCantidadporfull());
                    	pedidoaux.setTallosporfull(comp.getCantidadporfull());
                    	pedidoaux.setPrecio(comp.getPrecio());
                    	pedidoaux.setComision(comp.getComision());
                    	pedidoaux.setObservaciones(comp.getObservaciones());
                    	pedidoaux.setPreciototal(( comp.getPrecio() + comp.getComision() ) * comp.getCantidadporfull());
                    	pedidoaux.setIddetalle(detalle.getId());
                    	detallesPedidoAux.add(pedidoaux);
                    	break;
                    	}
                    }
            		pedido.setTallosporfull(unidades);
            	}
            	if(composiciones.size() >1){
        			pedido.setTipocomposicion("M");
        			pedido.setNombrecomposicion("Mixto");
        		}else {
        			pedido.setTipocomposicion("S");
        			pedido.setNombrecomposicion("Solido");
        		}
            	
            	
            	detallesPedido.add(pedido);
            	detallesPedido.addAll(detallesPedidoAux);
            	//getTotales(detallesPedido);
            	this.getNuevaLista();
            	
            }
    		
       	return;
    	
    }
    
public void multiplica(){
	  if(this.packing != null && this.cantidadfull != null){
		  Double dev = this.packing * this.cantidadfull;
		  	this.cantidad = dev.intValue();
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
	/*   if(this.codigo == null){
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
	 */if (!validarPacking(detallesTemporal)){
		   ok=false;
		   FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.packing") + ":","(Packing * Boxes) debe ser igual al total de unidades")); 
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
    
public String onFlowProcess(FlowEvent event) {
		
		boolean ok = true;
		
		if(this.idCliente.intValue() == 0){
			 
 			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.cliente") + ":",bundle.getString("errordebecliente")));
 			 ok = false;
		}
		if(this.idPais.intValue() == 0){
			 
 			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.pais") + ":",bundle.getString("errordebepais")));
 			 ok = false;
		}
		if(this.idCiudad.intValue() == 0){
			 
 			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.ciudad") + ":",bundle.getString("errordebeciudad")));
 			 ok = false;
		}
		if(this.idPaisDestino.intValue() == 0){
			 
 			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.paisdestino") + ":",bundle.getString("errordebepais")));
 			 ok = false;
		}
		if(this.idCiudadDestino.intValue() == 0){
			 
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
		}	*/
		if(ok){
			
			return event.getNewStep();
			
		}else {
			return "generales";
		}
		
	}

public String onFlowProcessver(FlowEvent event) {
	
	boolean ok = true;
	
	if(ok){
		
		return event.getNewStep();
		
	}else {
		return "generales";
	}
	
}
    
public String crearPedido(){
	
	Pedido pedido = new Pedido();
	pedido.setId(this.idPedido);
	pedido.setComprador(null);
	pedido.setEstado('O');//Recibido conciliado despachado cancelado
	pedido.setFechadespacho(this.fechadespacho);
	pedido.setFechallegada(this.fechallegada);
	pedido.setFechallegadafinal(this.fechallegadafinal);
	pedido.setFechapedido(this.fechapedido);
	pedido.setIdciudad(this.idCiudad);
	pedido.setIdpais(this.idPais);
	pedido.setIdpaisdestino(this.idPaisDestino);
	pedido.setIdciudaddestino(this.idCiudadDestino);
	pedido.setIdcliente(this.idCliente);
	pedido.setObservaciones(this.observaciones);
	pedido.setVendedor(null);
	
	pedido.setProgramado(true);
	pedido.setLunes(this.lunes);
	pedido.setMartes(this.martes);
	pedido.setMiercoles(this.miercoles);
	pedido.setJueves(this.jueves);
	pedido.setViernes(this.viernes);
	pedido.setSabado(this.sabado);
	pedido.setDomingo(this.domingo);
	pedido.setAutorizo(this.autorizo);
	
	
	List <PedidoBean> detallesPedidoAux = detallesPedido;
	List <DetallePedido> listasalida = new ArrayList <DetallePedido>();
	
	DetallePedido detalle = new DetallePedido();
	for(PedidoBean pedidoBean:detallesPedido){

 		if(pedidoBean.isDetalle()){
			detalle = new DetallePedido();
			detalle.setCantidadfull(pedidoBean.getCantidadfull());
    		detalle.setCantidadporfull(pedidoBean.getPacking());
    		//detalle.setCantidadtotal(pedidoBean.getCantidadtotal());
    		Double dev = pedidoBean.getCantidadfull() * pedidoBean.getPacking();
    		detalle.setCantidadtotal(dev.intValue());
    		if(pedidoBean.getIdplantacion()!= null)
    		detalle.setIdplantacion(pedidoBean.getIdplantacion());
    		detalle.setPrecio(pedidoBean.getPrecio());
    		detalle.setObservaciones(pedidoBean.getObservaciones());
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
		pedido.setAutorizacion(false);
		pedidoService.editPedido(pedido);
		this.putAuditoria("Editar pedido plantilla", "U", "Edito pedido plantilla as� : - " + pedido.toString());
		abrirConfirmacion();
	return null;
}

public Double getPrecioCarga(){
	
	Double precio = 0.0;
	Double precioCliente = 0.0;
	Double totalfb = 0.0;
	
	Double pesoProm =  dominioService.getDominio(this.idPais).getPesofullbox();
	if(pesoProm == null){
		pesoProm= 0.0;
	}
	
	//calculo de kilos
	
	for (PedidoBean pedido:detallesPedido){
		if(pedido.isDetalle()){
			totalfb = totalfb + pedido.getCantidadfull();
		}	
	}
	
	pesoProm = pesoProm * totalfb;
	//calculo de precios
	
	List <Destino> destinos = getDestinos();
	Destino destinohallado = null;
	Double tarifahallada= null;
	Double tarifahalladaCliente= null;
	
	for(Destino destino:destinos){
		
		if((destino.getRangoinicial() < pesoProm) &&  (destino.getRangofinal() >= pesoProm)){
			destinohallado = destino;
			tarifahallada= destino.getTarifaastoria();
			tarifahalladaCliente=destino.getTarifacliente();
			break;
		}else if((destino.getRangoinicial2() < pesoProm) &&  (destino.getRangofinal2() >= pesoProm) && destino.getRangofinal2() > 0){
			destinohallado = destino;
			tarifahallada= destino.getTarifaastoria2();
			tarifahalladaCliente=destino.getTarifacliente2();
			break;
		}else if((destino.getRangoinicial3() < pesoProm) &&  (destino.getRangofinal3() >= pesoProm) && destino.getRangofinal3() > 0){
			destinohallado = destino;
			tarifahallada= destino.getTarifaastoria3();
			tarifahalladaCliente=destino.getTarifacliente3();
			break;
		}
	}
	if(destinohallado != null){
		getOtrosConceptosTransporte(destinohallado);
		precioCliente = tarifahalladaCliente * pesoProm;
 	}
	this.preciototaltransporte = precioCliente + this.valorconcepto1trans + this.valorconcepto2trans + this.valorconcepto3trans + this.valorconcepto4trans;
	this.preciototalcompleto = this.totalPrecio + this.preciototaltransporte;
	    return precio;
	
}

public void getOtrosConceptosTransporte(Destino destino){

this.concepto1trans = destino.getConcepto1();
this.concepto2trans = destino.getConcepto2();
this.concepto3trans = destino.getConcepto3();
this.concepto4trans = destino.getConcepto4();
this.valorconcepto1trans = destino.getValor1();
this.valorconcepto2trans = destino.getValor2();
this.valorconcepto3trans = destino.getValor3();
this.valorconcepto4trans = destino.getValor4();
this.valorconcepto1transast = destino.getValorast1();
this.valorconcepto2transast = destino.getValorast2();
this.valorconcepto3transast = destino.getValorast3();
this.valorconcepto4transast = destino.getValorast4();

}

public List <Destino> getDestinos(){
   Integer idciudaddestino;
   Integer idpaisdestino;
   Destino destion; 
   //idpaisdestino = Integer.valueOf(bundle.getString("bd.idpaisdestino"));
   //idciudaddestino = Integer.valueOf(bundle.getString("bd.idciudaddestino"));
   idpaisdestino = this.idPaisDestino;
   idciudaddestino = this.idCiudadDestino;
   AgenciaCarga agencia = dominioService.getAgenciaXCod("GEN");
   Aerolinea aerolinea = dominioService.getAerolineaXPrefijo("GEN");
   List <Destino> destinos = dominioService.getDestino(agencia.getId(), aerolinea.getId(), this.idPais, idpaisdestino, this.idCiudad, idciudaddestino, null);
return destinos;
}

public boolean getNecesitaAuto(Pedido pedido){
	boolean ok;
	getPrecioCarga();
	Cliente cliente = clienteService.getClienteXId(pedido.getIdcliente());
	if(this.preciototalcompleto > cliente.getLimitecredito() + cliente.getSaldofinal()){
		ok = true;
	}else {
		ok = false;
	}
	return ok;
}

private void abrirNecesitaAuto(){
	
	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, bundle.getString("tituloalerta"), bundle.getString("alertacupo") + " " +  bundle.getString("confirmaeditarpedido"));
	//RequestContext.getCurrentInstance().showMessageInDialog(message);
	PrimeFaces.current().dialog().showMessageDynamic(message);

	return;

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
			composicion.setObservaciones(pedidoBean.getObservaciones());
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
    
    public String editar(EncabezadoPedidoBean pedidoeditar){
    	
    	Pedido pedido = pedidoService.getPedidoXId(pedidoeditar.getIdpedido());
    	
    	this.idPedido=pedido.getId();
    	this.idCliente = pedido.getIdcliente();
    	this.idPais= pedido.getIdpais();
    	this.idPaisDestino= pedido.getIdpaisdestino();
    	getCiudadesPais();
    	getCiudadesPaisDestino();
    	this.idCiudad = pedido.getIdciudad();
    	this.idCiudadDestino = pedido.getIdciudaddestino();
    	this.fechadespacho = pedido.getFechadespacho();
    	this.fechapedido = pedido.getFechapedido();
    	this.fechallegada = pedido.getFechallegada();
    	this.fechallegadafinal = pedido.getFechallegadafinal();
    	this.observaciones = pedido.getObservaciones();
    	this.programado=pedido.isProgramado();
    	this.lunes=pedido.isLunes();
    	this.martes=pedido.isMartes();
    	this.miercoles=pedido.isMiercoles();
    	this.jueves=pedido.isJueves();
    	this.viernes=pedido.isViernes();
    	this.sabado=pedido.isSabado();
    	this.domingo=pedido.isDomingo();
    	this.autorizo= pedido.getAutorizo();
    	
    	
    	subirGeneral(pedidoService.getDetallesPedido(pedidoeditar.getIdpedido()));
    	setDatosGenerales();
    	return "editarplantilla";
    	
    }
public String editarver(EncabezadoPedidoBean pedidoeditar){
    	
    	Pedido pedido = pedidoService.getPedidoXId(pedidoeditar.getIdpedido());
    	
    	this.idPedido=pedido.getId();
    	this.idCliente = pedido.getIdcliente();
    	this.idPais= pedido.getIdpais();
    	this.idPaisDestino= pedido.getIdpaisdestino();
    	getCiudadesPais();
    	getCiudadesPaisDestino();
    	this.idCiudad = pedido.getIdciudad();
    	this.idCiudadDestino = pedido.getIdciudaddestino();
    	this.fechadespacho = pedido.getFechadespacho();
    	this.fechapedido = pedido.getFechapedido();
    	this.fechallegada = pedido.getFechallegada();
    	this.fechallegadafinal = pedido.getFechallegadafinal();
    	this.observaciones = pedido.getObservaciones();
    	this.programado=pedido.isProgramado();
    	this.lunes=pedido.isLunes();
    	this.martes=pedido.isMartes();
    	this.miercoles=pedido.isMiercoles();
    	this.jueves=pedido.isJueves();
    	this.viernes=pedido.isViernes();
    	this.sabado=pedido.isSabado();
    	this.domingo=pedido.isDomingo();
    	this.autorizo= pedido.getAutorizo();
    	
    	
    	subirGeneral(pedidoService.getDetallesPedido(pedidoeditar.getIdpedido()));
    	setDatosGenerales();
    	return "verplantilla";
    	
    }
    
    private void abrirConfirmacion(){
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmaeditarpedido"));
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
    	//getTotales(detallesPedido);
    	this.getNuevaLista();
    	
    	return;
    }
    
    public void retomar(PedidoBean pedido){
      	
  	  subirDetalleRetomar(pedido);
  	  eliminar2(pedido); 
        return;
      }
   
   public void subirDetalleRetomar(PedidoBean pedidoCabezera){
  	 
   	List <PedidoBean> detallesPedidoIndex = new ArrayList<PedidoBean>();
   	detallesTemporal = new ArrayList<PedidoBean>();
   	int index= 0;
   	int cantidad = 0;
   	for(PedidoBean pedidoaux:detallesPedido){
   		if(pedidoaux.getIddetalle().intValue() == pedidoCabezera.getIddetalle().intValue()){
   			if(pedidoaux.isDetalle() == false){
   				detallesPedidoIndex.add(pedidoaux);
   			}
   		}
   		index ++;
   	}
   	for (PedidoBean pedido :detallesPedidoIndex){
  		
  		PedidoBean pedidoaux = new PedidoBean();
  		com.bulls.astoria.persistence.Producto Productoper = dominioService.getProductoVariedadGrado(pedido.getIdvariedad(), pedido.getIdgrado());
   			
   		for(Producto productoaux:productos){
           	
           	if(Productoper.getCodigo().equalsIgnoreCase(productoaux.getCodigo())){
           	
           	Dominio producto= dominioService.getDominio(productoaux.getIdProducto());
           	Dominio grado = dominioService.getDominio(productoaux.getGrado());
           	//Dominio color = dominioService.getDominio(productoaux.getColor());
           	Plantacion plantacion = null;
           	if(pedido.getIdplantacion() != null){
           		plantacion = plantacionService.getPlantacion(pedido.getIdplantacion());
           	}
               
           	pedidoaux.setCodigo(Productoper.getCodigo());
           	pedidoaux.setIdcolor(productoaux.getColor());
           	pedidoaux.setIdgrado(grado.getId());
           	pedidoaux.setIdplantacion(pedido.getIdplantacion());
           	pedidoaux.setIdvariedad(pedido.getIdvariedad());
           	pedidoaux.setNombrevariedad(producto.getNomcorto());
           	//pedido.setNombrecolor(color.getNomcorto());
           	pedidoaux.setNombregrado(grado.getNomcorto());
           	if(plantacion != null ){
           		pedidoaux.setNombreplantacion(plantacion.getNombre());
           		this.idPlantacion = plantacion.getId(); //para llenar cabezera
           	}
           	pedidoaux.setUnidades(pedido.getUnidades());
           	pedidoaux.setTallosporfull(pedido.getUnidades());
           	pedidoaux.setPrecio(pedido.getPrecio());
           	pedidoaux.setComision(pedido.getComision());
           	pedidoaux.setPreciototal((pedido.getPrecio() + pedido.getComision()) * pedido.getTallosporfull());
           	cantidad = cantidad + pedido.getUnidades();
           	detallesTemporal.add(pedidoaux);
           	break;
           	}
           }
  	}
   	this.cantidadfull = pedidoCabezera.getCantidadfull();
   	this.packing =  pedidoCabezera.getPacking();
  	this.cantidad = cantidad;
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
	
	

	public Integer getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Integer idPedido) {
		this.idPedido = idPedido;
	}

	public void onCellEdit(CellEditEvent event) {

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
	
	
	public Integer getTotalUnidades() {
		return totalUnidades;
	}

	public void setTotalUnidades(Integer totalUnidades) {
		this.totalUnidades = totalUnidades;
	}

	public Double getTotalPrecio() {
		return totalPrecio;
	}

	public void setTotalPrecio(Double totalPrecio) {
		this.totalPrecio = totalPrecio;
	}
	
public void getProductos2Plantacion(Integer idPlantacion){
		
		productosMap = dominioService.getProductosCompletoPlantacion(idPlantacion);
		//productos = getProductosmap(productosMap);
		productos2 = getProductosmap(productosMap);
        return; 
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
	
	public void getTotales(List <PedidoBean> detallesPedido){
		double precio = 0.0;
		int cantidad = 0;
		double cantboxes = 0.0;
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
	
	
	public List<SelectItem> getListaPlantacionesSelect2() {
		return listaPlantacionesSelect2;
	}

	public void setListaPlantacionesSelect2(
			List<SelectItem> listaPlantacionesSelect2) {
		this.listaPlantacionesSelect2 = listaPlantacionesSelect2;
	}

	public String getConcepto1trans() {
		return concepto1trans;
	}

	public void setConcepto1trans(String concepto1trans) {
		this.concepto1trans = concepto1trans;
	}

	public String getConcepto2trans() {
		return concepto2trans;
	}

	public void setConcepto2trans(String concepto2trans) {
		this.concepto2trans = concepto2trans;
	}

	public String getConcepto3trans() {
		return concepto3trans;
	}

	public void setConcepto3trans(String concepto3trans) {
		this.concepto3trans = concepto3trans;
	}

	public String getConcepto4trans() {
		return concepto4trans;
	}

	public void setConcepto4trans(String concepto4trans) {
		this.concepto4trans = concepto4trans;
	}

	public Double getValorconcepto1trans() {
		return valorconcepto1trans;
	}

	public void setValorconcepto1trans(Double valorconcepto1trans) {
		this.valorconcepto1trans = valorconcepto1trans;
	}

	public Double getValorconcepto2trans() {
		return valorconcepto2trans;
	}

	public void setValorconcepto2trans(Double valorconcepto2trans) {
		this.valorconcepto2trans = valorconcepto2trans;
	}

	public Double getValorconcepto3trans() {
		return valorconcepto3trans;
	}

	public void setValorconcepto3trans(Double valorconcepto3trans) {
		this.valorconcepto3trans = valorconcepto3trans;
	}

	public Double getValorconcepto4trans() {
		return valorconcepto4trans;
	}

	public void setValorconcepto4trans(Double valorconcepto4trans) {
		this.valorconcepto4trans = valorconcepto4trans;
	}

	public Double getValorconcepto1transast() {
		return valorconcepto1transast;
	}

	public void setValorconcepto1transast(Double valorconcepto1transast) {
		this.valorconcepto1transast = valorconcepto1transast;
	}

	public Double getValorconcepto2transast() {
		return valorconcepto2transast;
	}

	public void setValorconcepto2transast(Double valorconcepto2transast) {
		this.valorconcepto2transast = valorconcepto2transast;
	}

	public Double getValorconcepto3transast() {
		return valorconcepto3transast;
	}

	public void setValorconcepto3transast(Double valorconcepto3transast) {
		this.valorconcepto3transast = valorconcepto3transast;
	}

	public Double getValorconcepto4transast() {
		return valorconcepto4transast;
	}

	public void setValorconcepto4transast(Double valorconcepto4transast) {
		this.valorconcepto4transast = valorconcepto4transast;
	}

	public Double getPreciototalflor() {
		return preciototalflor;
	}

	public void setPreciototalflor(Double preciototalflor) {
		this.preciototalflor = preciototalflor;
	}

	public Double getPreciototaltransporte() {
		return preciototaltransporte;
	}

	public void setPreciototaltransporte(Double preciototaltransporte) {
		this.preciototaltransporte = preciototaltransporte;
	}

	public Double getPreciototalcompleto() {
		return preciototalcompleto;
	}

	public void setPreciototalcompleto(Double preciototalcompleto) {
		this.preciototalcompleto = preciototalcompleto;
	}
	

	public List<Producto> getProductos2filtrado() {
		return productos2filtrado;
	}

	public void setProductos2filtrado(List<Producto> productos2filtrado) {
		this.productos2filtrado = productos2filtrado;
	}

	public String getAutorizo() {
		return autorizo;
	}

	public void setAutorizo(String autorizo) {
		this.autorizo = autorizo;
	}
	
	

	public Integer getItem() {
		return item;
	}

	public void setItem(Integer item) {
		this.item = item;
	}
	

	public Double getTotalBoxes() {
		return totalBoxes;
	}

	public void setTotalBoxes(Double totalBoxes) {
		this.totalBoxes = totalBoxes;
	}
	
	
	
public ResourceBundle getBundleparam() {
		return bundleparam;
	}

	public void setBundleparam(ResourceBundle bundleparam) {
		this.bundleparam = bundleparam;
	}

	public String getCarpetajrxml() {
		return carpetajrxml;
	}

	public void setCarpetajrxml(String carpetajrxml) {
		this.carpetajrxml = carpetajrxml;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getBoxesid() {
		return boxesid;
	}

	public void setBoxesid(String boxesid) {
		this.boxesid = boxesid;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getIb() {
		return ib;
	}

	public void setIb(String ib) {
		this.ib = ib;
	}

	public String getFb() {
		return fb;
	}

	public void setFb(String fb) {
		this.fb = fb;
	}

	public String getBb() {
		return bb;
	}

	public void setBb(String bb) {
		this.bb = bb;
	}

	public String getIbaddress() {
		return ibaddress;
	}

	public void setIbaddress(String ibaddress) {
		this.ibaddress = ibaddress;
	}

	public String getIbswift() {
		return ibswift;
	}

	public void setIbswift(String ibswift) {
		this.ibswift = ibswift;
	}

	public String getIbaccount() {
		return ibaccount;
	}

	public void setIbaccount(String ibaccount) {
		this.ibaccount = ibaccount;
	}

	public String getBbaddress() {
		return bbaddress;
	}

	public void setBbaddress(String bbaddress) {
		this.bbaddress = bbaddress;
	}

	public String getBbswift() {
		return bbswift;
	}

	public void setBbswift(String bbswift) {
		this.bbswift = bbswift;
	}

	public String getFbaddress() {
		return fbaddress;
	}

	public void setFbaddress(String fbaddress) {
		this.fbaddress = fbaddress;
	}

	public String getFbaccount() {
		return fbaccount;
	}

	public void setFbaccount(String fbaccount) {
		this.fbaccount = fbaccount;
	}

	public ReportesManagedBean getReportes() {
		return reportes;
	}

	public void setReportes(ReportesManagedBean reportes) {
		this.reportes = reportes;
	}

public void imprimir(String tipo){
	
	    this.getPrecioCarga();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		Cliente cliente = clienteService.getClienteXId(this.idCliente);
		Dominio paisdom = dominioService.getDominio(this.idPais);
		this.boxesid = empresa.getPrefijo()+cliente.getCodigo();
/*		AgenciaCarga agencia  = dominioService.getAgenciaXId(this.idAgencia);
		Aerolinea aerolinea= dominioService.getAerolineaXId(this.idAgencia);
		Handler handler = dominioService.getHandlerXId(this.idHandler);
		Truck truck = dominioService.getTruckXId(this.idTruck);*/
		
		
		Map parameters = new HashMap();
		parameters.put("BOXESID", this.boxesid.toUpperCase());
		parameters.put("FECHA", dateFormat.format(Calendar.getInstance().getTime()));
		parameters.put("CUSTOMER", cliente.getNombre().toUpperCase());
		parameters.put("CLIENT", cliente.getNombre().toUpperCase());
		parameters.put("INVOICE", Integer.toString(this.idPedido));
		parameters.put("PAIS", paisdom.getNomcorto().toUpperCase());
		
		//parameters.put("TOTPIEZAS",this.totalPiezas);
		//parameters.put("TOTPIEZASAD", this.totalPiezasAdicional);
		parameters.put("TOTBOXES", this.totalBoxes);
		parameters.put("TOTUNIUDADES", this.totalUnidades);
		//ASI ESTA EN FACTURA AQUI NO PUES NO SE CALCULA ES EL TOTAL DE LA TABLAparameters.put("TOTPRECIOFLOR", this.preciototalflor);
		parameters.put("TOTPRECIOFLOR", this.totalPrecio);
		parameters.put("TOTTRANS", this.preciototaltransporte);
		parameters.put("TOTAL", this.preciototalcompleto);
		//parameters.put("AWB", this.awb);
		/*if(aerolinea != null)
		parameters.put("AIRLAINE", aerolinea.getNombre());
		if(agencia != null)
		parameters.put("FORWARDER", agencia.getNombre());
		if(handler != null)
			parameters.put("HANDLER", handler.getNombre());
		if(truck != null)
		parameters.put("TRUCK", truck.getNombre())*/
		parameters.put("IBANK", this.ib);
		parameters.put("IADDRESS", this.ibaddress);
		parameters.put("ISWIFT", this.ibswift);
		parameters.put("IACCOUNT", this.ibaccount);
		parameters.put("BBANK", this.bb);
		parameters.put("BSWIFT", this.bbswift);
		parameters.put("BADDRESS", this.bbaddress);
		parameters.put("FBENEFICIARY", this.fb);
		parameters.put("FSAVINGSACCOUNT", this.fbaccount);
		parameters.put("FADDRESS", this.fbaddress);
		parameters.put("CARPETAIMAGEN", carpetajrxml);
		//parameters.put("COSTOS", this.getCostosAdicionales());
		//parameters.put("DESCUENTOPRE", this.valorconceppre);

		
		List <PedidoBean> lista = new ArrayList <PedidoBean>();
		String filejrxml = "preinvoice.jrxml";
		reportes.generarPDF(parameters,detallesPedido , filejrxml,tipo);
	}

private void setDatosGenerales(){
	String letraPais = dominioService.getDominio(this.idPais).getNomcorto().substring(0,1);
	Calendar cal = Calendar.getInstance();
	String dia = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
	String mes = String.valueOf(cal.get(Calendar.MONTH)+1); //se pone asi p�es esta retornando un valor menos
	String ano = String.valueOf(cal.get(Calendar.YEAR));
	String codigo = clienteService.getClienteXId(this.idCliente).getCodigo();
	
	if(dia.equalsIgnoreCase("1") || dia.equalsIgnoreCase("2") || dia.equalsIgnoreCase("3") || dia.equalsIgnoreCase("4") || dia.equalsIgnoreCase("5") || dia.equalsIgnoreCase("6") || dia.equalsIgnoreCase("7") || dia.equalsIgnoreCase("8") || dia.equalsIgnoreCase("9")){
		dia = "0" + dia; 
	}
	if(mes.equalsIgnoreCase("1") || mes.equalsIgnoreCase("2") || mes.equalsIgnoreCase("3") || mes.equalsIgnoreCase("4") || mes.equalsIgnoreCase("5") || mes.equalsIgnoreCase("6") || mes.equalsIgnoreCase("7") || mes.equalsIgnoreCase("8") || mes.equalsIgnoreCase("9")){
		mes = "0" + mes; 
	}
	
	//this.numinvoice=codigo+letraPais+ano+mes+dia;
    this.boxesid=empresa.getPrefijo()+codigo;
    
    
    this.ib= empresa.getIb();
    this.fb= empresa.getFb();
    this.bb= empresa.getBb();
    
    this.ibaddress= empresa.getDireccionib();
    this.ibswift= empresa.getSwiftib();
    this.ibaccount= empresa.getCuentaib();
    
    
    this.bbaddress= empresa.getDireccionbb();
    this.bbswift= empresa.getSwiftbb();
    
    this.fbaccount= empresa.getCuentafb();
    this.fbaddress= empresa.getDireccionfb();
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
	  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("editarPlantillaMB");
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

