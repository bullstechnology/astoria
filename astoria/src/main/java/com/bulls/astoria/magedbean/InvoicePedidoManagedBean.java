package com.bulls.astoria.magedbean;

import java.io.Serializable;
import java.math.BigDecimal;
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
import org.springframework.security.core.context.SecurityContextHolder;

import com.bulls.astoria.enumeracion.EnuDominio;
import com.bulls.astoria.persistence.Aerolinea;
import com.bulls.astoria.persistence.AgenciaCarga;
import com.bulls.astoria.persistence.Cliente;
import com.bulls.astoria.persistence.Comision;
import com.bulls.astoria.persistence.Composicion;
import com.bulls.astoria.persistence.ComposicionConciliado;
import com.bulls.astoria.persistence.ComposicionFacturado;
import com.bulls.astoria.persistence.Destino;
import com.bulls.astoria.persistence.DetallePedido;
import com.bulls.astoria.persistence.DetallePedidoConciliado;
import com.bulls.astoria.persistence.DetallePedidoFacturado;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.Empresa;
import com.bulls.astoria.persistence.Handler;
import com.bulls.astoria.persistence.Nota;
import com.bulls.astoria.persistence.Pedido;
import com.bulls.astoria.persistence.PedidoConciliado;
import com.bulls.astoria.persistence.PedidoFacturado;
import com.bulls.astoria.persistence.Persona;
import com.bulls.astoria.persistence.Plantacion;
import com.bulls.astoria.persistence.PlantacionProducto;
import com.bulls.astoria.persistence.Proveedor;
import com.bulls.astoria.persistence.Truck;
import com.bulls.astoria.pojo.CostosAdicionalesBean;
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

@ManagedBean (name="invoicePedidoMB")
@SessionScoped
public class InvoicePedidoManagedBean extends GeneralManagedBean implements Serializable{
	
	
	private Integer idPedido; 
	private Integer idCliente;
	private Integer idCliente2;
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
	private BigDecimal precio;
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
	
	private Integer idTruck;
	private Integer idHandler;
	
	ResourceBundle bundle ;
	ResourceBundle bundleparam ;
	private Empresa empresa;
	
	List <Dominio> paises;
	List <Dominio> ciudades;
	List <Dominio> ciudadesDestino;
	List <Dominio> tiposDocumento;
	List <Persona> empleados;

	
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
	List <PedidoBean> detallesPedidoConciliado;
	List <PedidoBean> detallesTemporal;
	

	List <Dominio> colores;
	List <Dominio> grados;
	List <SelectItem>  gradosSelect;
	List <SelectItem> coloresSelect;
	List <SelectItem> listaAgenciasSelect;
	List <SelectItem> listaAerolineasSelect;
	
	private BigDecimal comision;
    private BigDecimal precioconcomision;
	
	
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
    
    
    //encabezado de invoice
    
    Date fechafactura;
    String numinvoice;
    String client;
    String boxesid;
    String awb;
    
    
    //Agencia de carga
    
    private Integer idAgencia;
    private Integer idAerolinea;
   
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
    
    //totales
    BigDecimal preciototalflorast = new BigDecimal(0.0);
    BigDecimal preciototalflor = new BigDecimal(0.0);
    BigDecimal preciototaltransporte = new BigDecimal(0.0);
    BigDecimal preciototalcompleto = new BigDecimal(0.0);
    BigDecimal preciototaltransporteast = new BigDecimal(0.0);
    BigDecimal preciototalcompletoast = new BigDecimal(0.0);
    private BigDecimal valorconceppre = new BigDecimal(0.0);
    
    private Integer totalUnidades;
    private BigDecimal totalBoxes;
    private Integer totalPiezas;
    private Integer totalPiezasAdicional = 0;
    private Integer totalUnidadesConciliado;
    private BigDecimal totalPrecioConciliado;
    private BigDecimal totalBoxesConciliado;
    
    private String bodega;
    private BigDecimal pesoast = new BigDecimal(0.0);
    private BigDecimal valorkgast = new BigDecimal(0.0);
    private BigDecimal pesoacli = new BigDecimal(0.0);
    private BigDecimal valorkgcli = new BigDecimal(0.0);
    private String concepadd;
    private BigDecimal valorconcepadd = new BigDecimal(0.0);
    private String concepadd2;
    private BigDecimal valorconcepadd2 = new BigDecimal(0.0);
    private String concepadd3;
    private BigDecimal valorconcepadd3 = new BigDecimal(0.0);
    

    private BigDecimal valorconcepaddast = new BigDecimal(0.0);
    private BigDecimal valorconcepadd2ast = new BigDecimal(0.0);
    private BigDecimal valorconcepadd3ast = new BigDecimal(0.0);
    
    //otros conceptos por transporte ya establecidos en la agencia
    
    private String concepto1trans;
    private String concepto2trans;
    private String concepto3trans;
    private String concepto4trans;
    private BigDecimal valorconcepto1trans;
    private BigDecimal valorconcepto2trans;
    private BigDecimal valorconcepto3trans;
    private BigDecimal valorconcepto4trans;
    
    private BigDecimal valorconcepto1transast;
    private BigDecimal valorconcepto2transast;
    private BigDecimal valorconcepto3transast;
    private BigDecimal valorconcepto4transast;
    
    private BigDecimal preculinclie = new BigDecimal(0.0);
    private BigDecimal preculinast=new BigDecimal(0.0);
    
    private BigDecimal preciototalpreculinclie = new BigDecimal(0.0);
    private BigDecimal preciototalpreculinast = new BigDecimal(0.0);
    
    private String carpetajrxml;
    private Integer item ;
    
    List <Handler> handlers;
	List <Truck> trucks;
	List <Truck> listaTrucks;
	List <SelectItem> listaHandlersItem;
	List <SelectItem> listaTrucksItem;
	
	
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
	
	@ManagedProperty(value="#{uMB}")
	private UtilsManagedBean utils;
	
	
	
	@PostConstruct
	public void InvoicePedidoManagedBean(){
		
		borrarSession();
		
		empresa= dominioService.getEmpresa();
		fechafactura = Calendar.getInstance().getTime(); 
		
		bundle =  ResourceBundle.getBundle("messages");
		paises = dominioService.getDominios(EnuDominio.PAISES.getIdTipoDominio());
		ciudades = dominioService.getDominios(EnuDominio.CIUDADES.getIdTipoDominio());
		listaPaisesDom = Convertidor.dominiosToSelectdItems(paises);
		//listaCiudadesDom = Convertidor.dominiosToSelectdItems(ciudades);
		empleados = personaService.getEmpleados();
		listaAgenciasSelect = new ArrayList<SelectItem>();		
		listaAerolineasSelect = new ArrayList<SelectItem>();
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
		
		//productos
		/*productosMap = dominioService.getProductosCompleto();
		productos = getProductosmap(productosMap);
		productos2 = getProductosmap(productosMap);*/
		detallesTemporal = new ArrayList<PedidoBean>();
		
		bundleparam =  ResourceBundle.getBundle("parametros");
		carpetajrxml = bundleparam.getString("rutajrxml");
		
		    handlers = dominioService.getHandlers();
		    //trucks = dominioService.getTrucks();
		    listaHandlersItem = handlersToSelectdItems(handlers);
		    //listaTrucksItem = trucksToSelectdItems(trucks);
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
    		this.getPrecioView();
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
    
    public void subirDetalle(){

    	PedidoBean pedidoaux = new PedidoBean();
    		
    	com.bulls.astoria.persistence.Producto productodb = dominioService.getProductoXCodigo(this.codigo);
    	Dominio producto = dominioService.getDominio(productodb.getIdvariedad());
    	Dominio dominioPadre = dominioService.getDominio(producto.getDominiopadre());
    	Dominio grado = dominioService.getDominio(productodb.getIdgrado());
    		//for(Producto productoaux:productos){
            	
            //	if(this.codigo.equalsIgnoreCase(productoaux.getCodigo())){
            	
            	//Dominio producto= dominioService.getDominio(productoaux.getIdProducto());
            //	Dominio grado = dominioService.getDominio(productoaux.getGrado());
            	//Dominio color = dominioService.getDominio(productoaux.getColor());
            	Plantacion plantacion = null;
            	if(this.idPlantacion != null){
            		plantacion = plantacionService.getPlantacion(this.idPlantacion);
            	}
                
            	pedidoaux.setCodigo(this.codigo);
            	//pedidoaux.setIdcolor(productoaux.getColor());
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
            	pedidoaux.setPrecio(this.precio.doubleValue());
            	pedidoaux.setComision(this.comision.doubleValue());
            	pedidoaux.setPreciototal((this.precio.doubleValue() + this.comision.doubleValue()) * this.cantidadxfull);
            	detallesTemporal.add(pedidoaux);
            //	break;
            //	}
          //  }

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
    	double preciototal= 0.0;
    	double preciototalast= 0.0;
    	
    	
    	if(validarSubir()){
    	 	
    		for(PedidoBean detalletemporal: detallesTemporal){
    			int idComposicion = this.generarAleatorio();
    			PedidoBean itemcomposicion = new PedidoBean();
    			itemcomposicion.setIddetalle(idDetalle);
    			itemcomposicion.setIdcomposicion(idComposicion);
    			itemcomposicion.setCodigo(detalletemporal.getCodigo());
    			itemcomposicion.setUnidades(detalletemporal.getTallosporfull());

    			com.bulls.astoria.persistence.Producto productodb = dominioService.getProductoXCodigo(detalletemporal.getCodigo());
    	    	Dominio productoaux = dominioService.getDominio(productodb.getIdvariedad());
    	        Dominio dominioPadre = dominioService.getDominio(productoaux.getDominiopadre());
    	    	Dominio grado = dominioService.getDominio(productodb.getIdgrado());	
    		//for(Producto productoaux:productos){
            	
            //	if(detalletemporal.getCodigo().equalsIgnoreCase(productoaux.getCodigo())){
            	
            	//los datos de pedido,detalle y composición

            	
            	
            	Dominio producto= dominioService.getDominio(productoaux.getId());
            	//Dominio grado = dominioService.getDominio(productoaux.getId());
            	//Dominio color = dominioService.getDominio(productoaux.getColor());
            	Plantacion plantacion = null;
            	if(this.idPlantacion != null){
            		plantacion = plantacionService.getPlantacion(this.idPlantacion);
            	}
                
            	
            	//itemcomposicion.setIdcolor(productoaux.getColor());
            	itemcomposicion.setIdgrado(grado.getId());
            	itemcomposicion.setIdplantacion(this.idPlantacion);
            	itemcomposicion.setIdvariedad(producto.getId());
            	itemcomposicion.setNombrevariedad(producto.getNomcorto());
            	itemcomposicion.setNombretipo(dominioPadre.getNomcorto());
            	//pedido.setNombrecolor(color.getNomcorto());
            	itemcomposicion.setNombregrado(grado.getNomcorto());
            	if(plantacion != null ){
            		itemcomposicion.setNombreplantacion(plantacion.getNombre());
            	}
            	itemcomposicion.setUnidades(detalletemporal.getTallosporfull());
            	itemcomposicion.setTallosporfull(detalletemporal.getTallosporfull());
            	itemcomposicion.setPrecio(detalletemporal.getPrecio());
            	itemcomposicion.setComision(detalletemporal.getComision());
            	itemcomposicion.setPreciototal((detalletemporal.getPrecio() + detalletemporal.getComision())* detalletemporal.getUnidades());
            	itemcomposicion.setPreciototalast(detalletemporal.getPrecio()* detalletemporal.getUnidades());
            	preciototal = preciototal + ((detalletemporal.getPrecio() + detalletemporal.getComision())* detalletemporal.getUnidades());
            	preciototalast = preciototalast + (detalletemporal.getPrecio() * detalletemporal.getUnidades());
            	listaDetalles.add(itemcomposicion);
            	//pone en temporal para luego poner toda la lista al final del primer item totalizador
            //	break;
            //	}
           // }
    		}
    		pedido.setPreciototal(preciototal);
    		pedido.setPreciototalast(preciototalast);
    		if(listaDetalles.size() >1){
    			pedido.setTipocomposicion("M");
    			pedido.setNombrecomposicion("Mixto");
    		}else {
    			pedido.setTipocomposicion("S");
    			pedido.setNombrecomposicion("Solido");
    		}
    		detallesPedido.add(pedido);
    		detallesPedido.addAll(listaDetalles);
    		//getPrecioTotal();
    		this.getNuevaLista();
    		
    		reset();
    		
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
    		Double preciototalintast = new Double(0.0);
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
    					preciototalintast = preciototalintast + (pedidomodint.getTallosporfull() * pedidomodint.getPrecio());
    					unidades = unidades + pedidointnuevo.getTallosporfull();
    				}
    			}
    			//setea el precio total calculado
    			pedidomodnuevo.setPreciototal(preciototalint);
    			pedidomodnuevo.setPreciototalast(preciototalintast);
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
     	getPrecioTotalNuevo();
		
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
    	detallesTemporal = new ArrayList<PedidoBean>();
    	this.comision = null;
    	this.precioconcomision = null;
    	detallesTemporal = new ArrayList<PedidoBean>();
    }
    
public void subirGeneral(List <DetallePedidoConciliado> detalles){
	     
	     detallesPedido = new ArrayList<PedidoBean>();
	     item =0;
    	 for(DetallePedidoConciliado detalle:detalles){
    		 item=++item;
    		 PedidoBean pedido = new PedidoBean();
    	     pedido.setDetalle(true);
    		 pedido.setCantidadfull(detalle.getCantidadfull());
    		 pedido.setPacking(detalle.getCantidadporfull());
    		 pedido.setCantidadtotal(detalle.getCantidadtotal());
    		 pedido.setPreciototal(detalle.getPrecio());
    		 pedido.setPreciototalast(detalle.getPrecio());
    		 pedido.setObservaciones(detalle.getObservaciones());
    		 pedido.setIddetalle(detalle.getId());
    		 Integer unidades = 0;
    		 pedido.setNum(item);
    		    List <PedidoBean> detallesPedidoAux = new ArrayList<PedidoBean>();
            	//consultamos las composiciones
            	List <ComposicionConciliado> composiciones = pedidoService.getComposicionesConciliado(detalle.getId());
            	for(ComposicionConciliado comp:composiciones){
            		
            		PedidoBean pedidoaux = new PedidoBean();
            		comp.getId();
            		comp.getIdproducto();
            		comp.getPrecio();
            		unidades = unidades +comp.getCantidadporfull();
            		
            		com.bulls.astoria.persistence.Producto productop = dominioService.getProductoXId(comp.getIdproducto());
            		Dominio productoaux = dominioService.getDominio(productop.getIdvariedad());
        	    	Dominio dominioPadre = dominioService.getDominio(productoaux.getDominiopadre());
        	    	Dominio grado = dominioService.getDominio(productop.getIdgrado());
            		
            		//for(Producto productoaux:productos){
                    	
                    //	if(productop.getCodigo().equalsIgnoreCase(productoaux.getCodigo())){
                    	
                    	Dominio producto= dominioService.getDominio(productop.getIdvariedad());
                    //	Dominio grado = dominioService.getDominio(productop.getIdgrado());
                    	//Dominio color = dominioService.getDominio(productoaux.getColor());
                    	Plantacion plantacion = null;
                    	if(detalle.getIdplantacion() != null){
                    		plantacion = plantacionService.getPlantacion(detalle.getIdplantacion());
                    	}
                        
                    	//pedidoaux.setCodigo(comp.get);
                    	///pedidoaux.setIdcolor(productoaux.getColor());
                    	pedidoaux.setIdgrado(grado.getId());

                    	pedidoaux.setIdvariedad(producto.getId());
                    	pedidoaux.setNombrevariedad(producto.getNomcorto());
                    	pedidoaux.setNombretipo(dominioPadre.getNomcorto());
                    	//pedido.setNombrecolor(color.getNomcorto());
                    	pedidoaux.setNombregrado(grado.getNomcorto());
                    	if(plantacion != null ){
                    		pedidoaux.setNombreplantacion(plantacion.getNombre());
                        	pedidoaux.setIdplantacion(plantacion.getId());
                    	}
                    	pedidoaux.setUnidades(comp.getCantidadporfull());
                    	pedidoaux.setTallosporfull(comp.getCantidadporfull());
                    	pedidoaux.setPrecio(comp.getPrecio());
                    	pedidoaux.setComision(comp.getComision());
                    	pedidoaux.setObservaciones(comp.getObservaciones());
                    	pedidoaux.setPreciototal(( comp.getPrecio() + comp.getComision() ) * comp.getCantidadporfull());
                    	pedidoaux.setPreciototalast(( comp.getPrecio()  ) * comp.getCantidadporfull());
                    	pedidoaux.setIddetalle(detalle.getId());
                    	detallesPedidoAux.add(pedidoaux);
                    	//break;
                    	//}
                  //  }
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
            	
            }
    	 getPrecioTotal();
       	return;
    	
    }

public void subirGeneralFacturado(List <DetallePedidoFacturado> detalles){
    
    detallesPedido = new ArrayList<PedidoBean>();
    item=0;
	 for(DetallePedidoFacturado detalle:detalles){
		 item=++item;
		 PedidoBean pedido = new PedidoBean();
	     pedido.setDetalle(true);
		 pedido.setCantidadfull(detalle.getCantidadfull());
		 pedido.setPacking(detalle.getCantidadporfull());
		 pedido.setCantidadtotal(detalle.getCantidadtotal());
		 pedido.setPreciototal(detalle.getPrecio());
		 pedido.setPreciototalast(detalle.getPrecio());
		 pedido.setIddetalle(detalle.getId());
		 pedido.setBodega(detalle.getBodega());
		 pedido.setPiezas(detalle.getPiezas());
		 pedido.setAwb(detalle.getAwb());
		 pedido.setInvoice(detalle.getInvoice());
		 pedido.setObservaciones(detalle.getObservaciones());

		 Integer unidades = 0;
		 pedido.setNum(item);
		    List <PedidoBean> detallesPedidoAux = new ArrayList<PedidoBean>();
       	//consultamos las composiciones
       	List <ComposicionFacturado> composiciones = pedidoService.getComposicionesFacturado(detalle.getId());
       	for(ComposicionFacturado comp:composiciones){
       		
       		PedidoBean pedidoaux = new PedidoBean();
       		comp.getId();
       		comp.getIdproducto();
       		comp.getPrecio();
       		unidades = unidades +comp.getCantidadporfull();
       		
       		com.bulls.astoria.persistence.Producto productop = dominioService.getProductoXId(comp.getIdproducto());

    		Dominio productoaux = dominioService.getDominio(productop.getIdvariedad());
	    	Dominio dominioPadre = dominioService.getDominio(productoaux.getDominiopadre());
	    	Dominio grado = dominioService.getDominio(productop.getIdgrado());
       		
       		//for(Producto productoaux:productos){
               	
           //    	if(productop.getCodigo().equalsIgnoreCase(productoaux.getCodigo())){
               	
               	Dominio producto= dominioService.getDominio(productop.getIdvariedad());
               //	Dominio grado = dominioService.getDominio(productop.getIdgrado());
               	//Dominio color = dominioService.getDominio(productoaux.getColor());
               	Plantacion plantacion = null;
               	if(detalle.getIdplantacion() != null){
               		plantacion = plantacionService.getPlantacion(detalle.getIdplantacion());
               		pedidoaux.setIdplantacion(plantacion.getId());
               	}
                   
               	//pedidoaux.setCodigo(comp.get);
               	//pedidoaux.setIdcolor(productoaux.getColor());
               	pedidoaux.setIdgrado(grado.getId());
               	pedidoaux.setIdvariedad(producto.getId());
               	pedidoaux.setNombrevariedad(producto.getNomcorto());
               	pedidoaux.setNombretipo(dominioPadre.getNomcorto());
               	//pedido.setNombrecolor(color.getNomcorto());
               	pedidoaux.setNombregrado(grado.getNomcorto());
               	if(plantacion != null ){
               		pedidoaux.setNombreplantacion(plantacion.getNombre());
               	}
               	pedidoaux.setUnidades(comp.getCantidadporfull());
               	pedidoaux.setTallosporfull(comp.getCantidadporfull());
               	pedidoaux.setPrecio(comp.getPrecio());
               	pedidoaux.setComision(comp.getComision());
               	pedidoaux.setObservaciones(comp.getObservaciones());
            	pedidoaux.setPreciototal(( comp.getPrecio() + comp.getComision() ) * comp.getCantidadporfull());
            	pedidoaux.setPreciototalast(( comp.getPrecio() ) * comp.getCantidadporfull());
               	pedidoaux.setIddetalle(detalle.getId());
               	detallesPedidoAux.add(pedidoaux);
               //	break;
               	//}
            //   }
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
       	
       	
       }
	 getPrecioTotal();	
  	return;
	
}

public void subirGeneralConciliado(List <DetallePedidoConciliado> detalles){
    
    detallesPedidoConciliado = new ArrayList<PedidoBean>();
    item=0;
	 for(DetallePedidoConciliado detalle:detalles){
		 item=++item;
		 PedidoBean pedido = new PedidoBean();
	     pedido.setDetalle(true);
		 pedido.setCantidadfull(detalle.getCantidadfull());
		 pedido.setPacking(detalle.getCantidadporfull());
		 pedido.setCantidadtotal(detalle.getCantidadtotal());
		 pedido.setPreciototal(detalle.getPrecio());
		 pedido.setPreciototalast(detalle.getPrecio());
		 pedido.setObservaciones(detalle.getObservaciones());
		 pedido.setIddetalle(detalle.getId());
		 Integer unidades = 0;
		 pedido.setNum(item);
		    List <PedidoBean> detallesPedidoAux = new ArrayList<PedidoBean>();
       	//consultamos las composiciones
       	List <ComposicionConciliado> composiciones = pedidoService.getComposicionesConciliado(detalle.getId());
       	for(ComposicionConciliado comp:composiciones){
       		
       		PedidoBean pedidoaux = new PedidoBean();
       		comp.getId();
       		comp.getIdproducto();
       		comp.getPrecio();
       		unidades = unidades +comp.getCantidadporfull();
       		
       		com.bulls.astoria.persistence.Producto productop = dominioService.getProductoXId(comp.getIdproducto());
       		Dominio productoaux = dominioService.getDominio(productop.getIdvariedad());
	    	Dominio dominioPadre = dominioService.getDominio(productoaux.getDominiopadre());
	    	Dominio grado = dominioService.getDominio(productop.getIdgrado());
	    	
       		//for(Producto productoaux:productos){
               	
            //   	if(productop.getCodigo().equalsIgnoreCase(productoaux.getCodigo())){
               	
               	Dominio producto= dominioService.getDominio(productop.getIdvariedad());
           //    	Dominio grado = dominioService.getDominio(productop.getIdgrado());
               	//Dominio color = dominioService.getDominio(productoaux.getColor());
               	Plantacion plantacion = null;
               	if(detalle.getIdplantacion() != null){
               		plantacion = plantacionService.getPlantacion(detalle.getIdplantacion());
               	}
                   
               	//pedidoaux.setCodigo(comp.get);
               //	pedidoaux.setIdcolor(productoaux.getColor());
               	pedidoaux.setIdgrado(grado.getId());
               	pedidoaux.setIdvariedad(producto.getId());
               	pedidoaux.setNombrevariedad(producto.getNomcorto());
               	pedidoaux.setNombretipo(dominioPadre.getNomcorto());
               	//pedido.setNombrecolor(color.getNomcorto());
               	pedidoaux.setNombregrado(grado.getNomcorto());
               	if(plantacion != null ){
               		pedidoaux.setNombreplantacion(plantacion.getNombre());
               		pedidoaux.setIdplantacion(plantacion.getId());
               	}
               	pedidoaux.setUnidades(comp.getCantidadporfull());
               	pedidoaux.setTallosporfull(comp.getCantidadporfull());
               	pedidoaux.setPrecio(comp.getPrecio());
               	pedidoaux.setComision(comp.getComision());
               	pedidoaux.setObservaciones(comp.getObservaciones());
            	pedidoaux.setPreciototal(( comp.getPrecio() + comp.getComision() ) * comp.getCantidadporfull());
            	pedidoaux.setPreciototalast(( comp.getPrecio() ) * comp.getCantidadporfull());
               	pedidoaux.setIddetalle(detalle.getId());
               	detallesPedidoAux.add(pedidoaux);
               //	break;
               //	}
              // }
       		pedido.setTallosporfull(unidades);
       	}
       	if(composiciones.size() >1){
   			pedido.setTipocomposicion("M");
   			pedido.setNombrecomposicion("Mixto");
   		}else {
   			pedido.setTipocomposicion("S");
   			pedido.setNombrecomposicion("Solido");
   		}
       	
       	
       	detallesPedidoConciliado.add(pedido);
       	detallesPedidoConciliado.addAll(detallesPedidoAux);
       	getTotalesConciliado(detallesPedidoConciliado);
       }
		
  	return;
	
}
    
public void multiplica(){
	  if(this.packing != null && this.cantidadfull != null){
		  Double dev = this.packing * this.cantidadfull;
		  	this.cantidad =  dev.intValue();
	  }
	 return;
}

public void multiplica2(){
	  
	 return;
}
public void multiplica3(){
	 // this.precioconcomision =  this.precio + this.comision;
	 this.precioconcomision =  this.precio.add(this.comision);
}
 public boolean validarSubir(){
	   boolean ok = true;
	 /*  if(this.codigo == null){
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
	*/ if (!validarPacking(detallesTemporal)){
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
		
		if(this.fechadespacho == null || this.fechallegada == null || this.fechallegadafinal == null){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("Fechas") + ":","Las fechas deben tener valores estimados"));
 			 ok = false;
		}/*else {
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
			listaPlantaciones = plantacionService.getPlantacionesPais(this.idPais);
			listaPlantacionesSelect = Convertidor.plantacionToSelectdItems(listaPlantaciones);
			return event.getNewStep();
			
		}else {
			return "generales";
		}
		
	}
    
public String crearPedido(){
	
	
	
	if(this.idAgencia == null || this.idAerolinea == null){
			 
			 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), "Debe seleccionar Forwarder y Aerolinea");
				//RequestContext.getCurrentInstance().showMessageInDialog(message);
			 PrimeFaces.current().dialog().showMessageDynamic(message);
				return null;
		 
	}
	
	PedidoFacturado pedido = new PedidoFacturado();
	pedido.setId(this.idPedido);
	pedido.setComprador(null);
	pedido.setDiligenciador(SecurityContextHolder.getContext().getAuthentication().getName());
	if(this.programado){
		pedido.setEstado('P');//Recibido conciliado despachado cancelado
	}else{
		pedido.setEstado('F');//Recibido conciliado despachado cancelado
	}
	pedido.setFechadespacho(this.fechadespacho);
	pedido.setFechallegada(this.fechallegada);
	pedido.setFechallegadafinal(this.fechallegadafinal);
	pedido.setFechapedido(this.fechapedido);
	pedido.setIdciudad(this.idCiudad);
	pedido.setIdpais(this.idPais);
	pedido.setIdciudaddestino(this.idCiudadDestino);
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
	
	//datos de enabezado y carga
	
	pedido.setAwb(this.awb);
	pedido.setBoxesid(this.boxesid);
	pedido.setClient(this.client);
	pedido.setForwarder(this.idAgencia);
	pedido.setDate(this.fechafactura);
	pedido.setInvoice(this.numinvoice);
	pedido.setAirline(this.idAerolinea);
	pedido.setIdTruck(this.idTruck);
	pedido.setIdHandler(this.idHandler);
	pedido.setValorconceppre(this.valorconceppre.doubleValue());
	pedido.setPiezasadicionales(this.totalPiezasAdicional);
	
	pedido.setPesoastoria(this.pesoast.doubleValue());
	pedido.setPesocliente(this.pesoacli.doubleValue());
	pedido.setTarifaastoria(this.valorkgast.doubleValue());
	pedido.setTarifacliente(this.valorkgcli.doubleValue());
	
	pedido.setConcepto(this.concepadd);
	pedido.setConcepto1(this.concepadd2);
	pedido.setConcepto2(this.concepadd3);
	pedido.setValorconcepto(this.valorconcepadd.doubleValue());
	pedido.setValorconcepto1(this.valorconcepadd2.doubleValue());
	pedido.setValorconcepto2(this.valorconcepadd3.doubleValue());
	pedido.setValorconceptoast(this.valorconcepaddast.doubleValue());
	pedido.setValorconcepto1ast(this.valorconcepadd2ast.doubleValue());
	pedido.setValorconcepto2ast(this.valorconcepadd3ast.doubleValue());
	
	pedido.setConceptotrans1(this.concepto1trans);
	pedido.setConceptotrans2(this.concepto2trans);
	pedido.setConceptotrans3(this.concepto3trans);
	pedido.setConceptotrans4(this.concepto4trans);
	
	pedido.setValconceptotrans1(this.valorconcepto1trans.doubleValue());
	pedido.setValconceptotrans2(this.valorconcepto2trans.doubleValue());
	pedido.setValconceptotrans3(this.valorconcepto3trans.doubleValue());
	pedido.setValconceptotrans4(this.valorconcepto4trans.doubleValue());
	
	pedido.setValconceptotransast1(this.valorconcepto1transast.doubleValue());
	pedido.setValconceptotransast2(this.valorconcepto2transast.doubleValue());
	pedido.setValconceptotransast3(this.valorconcepto3transast.doubleValue());
	pedido.setValconceptotransast4(this.valorconcepto4transast.doubleValue());
	
	//preculin
	
	pedido.setPreculincli(this.preculinclie.doubleValue());
	pedido.setPreculinast(this.preculinast.doubleValue());
	pedido.setTotalpreculincli(this.preciototalpreculinclie.doubleValue());
	pedido.setTotalpreculinast(this.preciototalpreculinast.doubleValue());
	
	
	List <PedidoBean> detallesPedidoAux = detallesPedido;
	List <DetallePedidoFacturado> listasalida = new ArrayList <DetallePedidoFacturado>();
	
	DetallePedidoFacturado detalle = new DetallePedidoFacturado();
	for(PedidoBean pedidoBean:detallesPedido){

 		if(pedidoBean.isDetalle()){
			detalle = new DetallePedidoFacturado();
			detalle.setCantidadfull(pedidoBean.getCantidadfull());
    		detalle.setCantidadporfull(pedidoBean.getPacking());
    		//detalle.setCantidadtotal(pedidoBean.getCantidadtotal());
    		Double dev = pedidoBean.getCantidadfull() * pedidoBean.getPacking();
    		detalle.setCantidadtotal(dev.intValue());
    		//if(pedidoBean.getIdplantacion()!= null)
    		detalle.setIdplantacion(pedidoBean.getIdplantacion());
    		detalle.setPrecio(pedidoBean.getPrecio());
    		detalle.setPiezas(pedidoBean.getPiezas());
    		detalle.setBodega(pedidoBean.getBodega());
    		detalle.setAwb(pedidoBean.getAwb());
    		detalle.setInvoice(pedidoBean.getInvoice());
    		detalle.setObservaciones(pedido.getObservaciones());
    		//detalle.setIdproducto(dominioService.getProductoVariedadGrado(pedidoBean.getIdvariedad(), pedidoBean.getIdgrado()).getId());
    		detalle.setPedido(pedido);
    		Set set = new HashSet(getComposiciones(detallesPedidoAux,pedidoBean.getIddetalle(),detalle));
    		detalle.setComposiciones(set);
    		listasalida.add(detalle);
			
		}    		
	}
	Set<com.bulls.astoria.persistence.DetallePedidoFacturado> targeDetalles = new HashSet<com.bulls.astoria.persistence.DetallePedidoFacturado>(listasalida);
	pedido.setDetalles(targeDetalles);
	//salvar
	
	Pedido ped = pedidoService.getPedidoXId(this.idPedido);
	if (!ped.getEstado().equals('E')){
		pedidoService.editPedidoEnFacturacion(pedido);
		this.putAuditoria("Editar pedido facturar", "U", "Edito pedido facturar así : - " + pedido.toString());
		abrirConfirmacion();
	}
	//se comentarea para que quede en la misma pagina
	//return "listapedidos";
	return null;

}

public boolean crearPedidoAntesFacturar(){
	
	boolean ok = false;
	
	if(this.idAgencia == null || this.idAerolinea == null){
			 
			 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), "Debe seleccionar Forwarder y Aerolinea");
				//RequestContext.getCurrentInstance().showMessageInDialog(message);
			 PrimeFaces.current().dialog().showMessageDynamic(message);
				return ok;
		 
	}
	
	PedidoFacturado pedido = new PedidoFacturado();
	pedido.setId(this.idPedido);
	pedido.setComprador(null);
	pedido.setDiligenciador(SecurityContextHolder.getContext().getAuthentication().getName());
	if(this.programado){
		pedido.setEstado('P');//Recibido conciliado despachado cancelado
	}else{
		pedido.setEstado('F');//Recibido conciliado despachado cancelado
	}
	pedido.setFechadespacho(this.fechadespacho);
	pedido.setFechallegada(this.fechallegada);
	pedido.setFechallegadafinal(this.fechallegadafinal);
	pedido.setFechapedido(this.fechapedido);
	pedido.setIdciudad(this.idCiudad);
	pedido.setIdpais(this.idPais);
	pedido.setIdciudaddestino(this.idCiudadDestino);
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
	
	//datos de enabezado y carga
	
	pedido.setAwb(this.awb);
	pedido.setBoxesid(this.boxesid);
	pedido.setClient(this.client);
	pedido.setForwarder(this.idAgencia);
	pedido.setDate(this.fechafactura);
	pedido.setInvoice(this.numinvoice);
	pedido.setAirline(this.idAerolinea);
	pedido.setIdTruck(this.idTruck);
	pedido.setIdHandler(this.idHandler);
	pedido.setValorconceppre(this.valorconceppre.doubleValue());
	pedido.setPiezasadicionales(this.totalPiezasAdicional);
	
	pedido.setPesoastoria(this.pesoast.doubleValue());
	pedido.setPesocliente(this.pesoacli.doubleValue());
	pedido.setTarifaastoria(this.valorkgast.doubleValue());
	pedido.setTarifacliente(this.valorkgcli.doubleValue());
	
	pedido.setConcepto(this.concepadd);
	pedido.setConcepto1(this.concepadd2);
	pedido.setConcepto2(this.concepadd3);
	pedido.setValorconcepto(this.valorconcepadd.doubleValue());
	pedido.setValorconcepto1(this.valorconcepadd2.doubleValue());
	pedido.setValorconcepto2(this.valorconcepadd3.doubleValue());
	pedido.setValorconceptoast(this.valorconcepaddast.doubleValue());
	pedido.setValorconcepto1ast(this.valorconcepadd2ast.doubleValue());
	pedido.setValorconcepto2ast(this.valorconcepadd3ast.doubleValue());
	
	pedido.setConceptotrans1(this.concepto1trans);
	pedido.setConceptotrans2(this.concepto2trans);
	pedido.setConceptotrans3(this.concepto3trans);
	pedido.setConceptotrans4(this.concepto4trans);
	
	pedido.setValconceptotrans1(this.valorconcepto1trans.doubleValue());
	pedido.setValconceptotrans2(this.valorconcepto2trans.doubleValue());
	pedido.setValconceptotrans3(this.valorconcepto3trans.doubleValue());
	pedido.setValconceptotrans4(this.valorconcepto4trans.doubleValue());
	
	pedido.setValconceptotransast1(this.valorconcepto1transast.doubleValue());
	pedido.setValconceptotransast2(this.valorconcepto2transast.doubleValue());
	pedido.setValconceptotransast3(this.valorconcepto3transast.doubleValue());
	pedido.setValconceptotransast4(this.valorconcepto4transast.doubleValue());
	
	//preculin
	
	pedido.setPreculincli(this.preculinclie.doubleValue());
	pedido.setPreculinast(this.preculinast.doubleValue());
	pedido.setTotalpreculincli(this.preciototalpreculinclie.doubleValue());
	pedido.setTotalpreculinast(this.preciototalpreculinast.doubleValue());
	
	
	List <PedidoBean> detallesPedidoAux = detallesPedido;
	List <DetallePedidoFacturado> listasalida = new ArrayList <DetallePedidoFacturado>();
	
	DetallePedidoFacturado detalle = new DetallePedidoFacturado();
	for(PedidoBean pedidoBean:detallesPedido){

 		if(pedidoBean.isDetalle()){
			detalle = new DetallePedidoFacturado();
			detalle.setCantidadfull(pedidoBean.getCantidadfull());
    		detalle.setCantidadporfull(pedidoBean.getPacking());
    		//detalle.setCantidadtotal(pedidoBean.getCantidadtotal());
    		Double dev = pedidoBean.getCantidadfull() * pedidoBean.getPacking();
    		detalle.setCantidadtotal(dev.intValue());
    		//if(pedidoBean.getIdplantacion()!= null)
    		detalle.setIdplantacion(pedidoBean.getIdplantacion());
    		detalle.setPrecio(pedidoBean.getPrecio());
    		detalle.setPiezas(pedidoBean.getPiezas());
    		detalle.setBodega(pedidoBean.getBodega());
    		detalle.setAwb(pedidoBean.getAwb());
    		detalle.setInvoice(pedidoBean.getInvoice());
    		detalle.setObservaciones(pedido.getObservaciones());
    		//detalle.setIdproducto(dominioService.getProductoVariedadGrado(pedidoBean.getIdvariedad(), pedidoBean.getIdgrado()).getId());
    		detalle.setPedido(pedido);
    		Set set = new HashSet(getComposiciones(detallesPedidoAux,pedidoBean.getIddetalle(),detalle));
    		detalle.setComposiciones(set);
    		listasalida.add(detalle);
			
		}    		
	}
	Set<com.bulls.astoria.persistence.DetallePedidoFacturado> targeDetalles = new HashSet<com.bulls.astoria.persistence.DetallePedidoFacturado>(listasalida);
	pedido.setDetalles(targeDetalles);
	//salvar
	pedidoService.editPedidoEnFacturacion(pedido);
	//this.putAuditoria("Editar pedido facturar", "U", "Edito pedido facturar así : - " + pedido.toString());
	//abrirConfirmacion();
	ok = true;
	return ok;
}


public String facturarPedido(){
	
	if(this.idAgencia == null || this.idAerolinea == null){
		 
		 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), "Debe seleccionar Forwarder y Aerolinea");
			//RequestContext.getCurrentInstance().showMessageInDialog(message);
		 PrimeFaces.current().dialog().showMessageDynamic(message);
			return null;
	 
    }
	
	PedidoFacturado pedido = new PedidoFacturado();
	pedido.setId(this.idPedido);
	pedido.setComprador(null);
	pedido.setDiligenciador(SecurityContextHolder.getContext().getAuthentication().getName());
	if(this.programado){
		pedido.setEstado('P');//Recibido conciliado despachado cancelado
	}else{
		pedido.setEstado('F');//Recibido conciliado despachado cancelado
	}
	pedido.setFechadespacho(this.fechadespacho);
	pedido.setFechallegada(this.fechallegada);
	pedido.setFechallegadafinal(this.fechallegadafinal);
	pedido.setFechapedido(this.fechapedido);
	pedido.setIdciudad(this.idCiudad);
	pedido.setIdpais(this.idPais);
	pedido.setIdciudaddestino(this.idCiudadDestino);
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
	
	//datos de enabezado y carga
	
		pedido.setAwb(this.awb);
		pedido.setBoxesid(this.boxesid);
		pedido.setClient(this.client);
		pedido.setForwarder(this.idAgencia);
		pedido.setDate(this.fechafactura);
		pedido.setInvoice(this.numinvoice);
		pedido.setAirline(this.idAerolinea);
		pedido.setIdTruck(this.idTruck);
		pedido.setIdHandler(this.idHandler);
		pedido.setValorconceppre(this.valorconceppre.doubleValue());
		pedido.setPiezasadicionales(this.totalPiezasAdicional);
		
		pedido.setPesoastoria(this.pesoast.doubleValue());
		pedido.setPesocliente(this.pesoacli.doubleValue());
		pedido.setTarifaastoria(this.valorkgast.doubleValue());
		pedido.setTarifacliente(this.valorkgcli.doubleValue());
		
		pedido.setConcepto(this.concepadd);
		pedido.setConcepto1(this.concepadd2);
		pedido.setConcepto2(this.concepadd3);
		pedido.setValorconcepto(this.valorconcepadd.doubleValue());
		pedido.setValorconcepto1(this.valorconcepadd2.doubleValue());
		pedido.setValorconcepto2(this.valorconcepadd3.doubleValue());
		pedido.setValorconceptoast(this.valorconcepaddast.doubleValue());
		pedido.setValorconcepto1ast(this.valorconcepadd2ast.doubleValue());
		pedido.setValorconcepto2ast(this.valorconcepadd3ast.doubleValue());
		
		pedido.setConceptotrans1(this.concepto1trans);
		pedido.setConceptotrans2(this.concepto2trans);
		pedido.setConceptotrans3(this.concepto3trans);
		pedido.setConceptotrans4(this.concepto4trans);
		
		pedido.setValconceptotrans1(this.valorconcepto1trans.doubleValue());
		pedido.setValconceptotrans2(this.valorconcepto2trans.doubleValue());
		pedido.setValconceptotrans3(this.valorconcepto3trans.doubleValue());
		pedido.setValconceptotrans4(this.valorconcepto4trans.doubleValue());
		
		pedido.setValconceptotransast1(this.valorconcepto1transast.doubleValue());
		pedido.setValconceptotransast2(this.valorconcepto2transast.doubleValue());
		pedido.setValconceptotransast3(this.valorconcepto3transast.doubleValue());
		pedido.setValconceptotransast4(this.valorconcepto4transast.doubleValue());
		
		//preculin
		
		pedido.setPreculincli(this.preculinclie.doubleValue());
		pedido.setPreculinast(this.preculinast.doubleValue());
		pedido.setTotalpreculincli(this.preciototalpreculinclie.doubleValue());
		pedido.setTotalpreculinast(this.preciototalpreculinast.doubleValue());
	
	
	List <PedidoBean> detallesPedidoAux = detallesPedido;
	List <DetallePedidoFacturado> listasalida = new ArrayList <DetallePedidoFacturado>();
	
	DetallePedidoFacturado detalle = new DetallePedidoFacturado();
	for(PedidoBean pedidoBean:detallesPedido){

 		if(pedidoBean.isDetalle()){
			detalle = new DetallePedidoFacturado();
			detalle.setCantidadfull(pedidoBean.getCantidadfull());
    		detalle.setCantidadporfull(pedidoBean.getPacking());
    		//detalle.setCantidadtotal(pedidoBean.getCantidadtotal());
    		Double dev = pedidoBean.getCantidadfull() * pedidoBean.getPacking();
    		detalle.setCantidadtotal(dev.intValue());
    		//if(pedidoBean.getIdplantacion()!= null)
    		detalle.setIdplantacion(pedidoBean.getIdplantacion());
    		detalle.setPrecio(pedidoBean.getPrecio());

    		detalle.setPiezas(pedidoBean.getPiezas());
    		detalle.setBodega(pedidoBean.getBodega());
    		detalle.setAwb(pedidoBean.getAwb());
    		detalle.setInvoice(pedidoBean.getInvoice());
    		detalle.setObservaciones(pedidoBean.getObservaciones());
    		//detalle.setIdproducto(dominioService.getProductoVariedadGrado(pedidoBean.getIdvariedad(), pedidoBean.getIdgrado()).getId());
    		detalle.setPedido(pedido);
    		Set set = new HashSet(getComposiciones(detallesPedidoAux,pedidoBean.getIddetalle(),detalle));
    		detalle.setComposiciones(set);
    		listasalida.add(detalle);
			
		}    		
	}
	Set<com.bulls.astoria.persistence.DetallePedidoFacturado> targeDetalles = new HashSet<com.bulls.astoria.persistence.DetallePedidoFacturado>(listasalida);
	pedido.setDetalles(targeDetalles);
	//salvar
	Pedido ped = pedidoService.getPedidoXId(this.idPedido);
	if (!ped.getEstado().equals('E')){
		pedidoService.editPedidoFacturado(pedido);
		this.putAuditoria("Editar pedido facturado cerrar - c", "U", "Edito pedido facturado cerrar así : - " + pedido.toString());
		this.afectarBalances(listasalida,pedido);
		abrirConfirmacion();
	}
	return "listapedidos";
}


public void afectarBalances(List <DetallePedidoFacturado> listaDetalles, PedidoFacturado pedido){
	
	for(DetallePedidoFacturado detalle : listaDetalles){
            List<ComposicionFacturado> list = new ArrayList<ComposicionFacturado>(detalle.getComposiciones());
            Double precio = 0.0;
            for(ComposicionFacturado comp : list){
            	precio = precio + comp.getPrecio() * comp.getCantidadporfull();
            }

		    //notas plantacion
            Nota nota = new Nota ();
            nota.setIdpedido(this.idPedido);
            nota.setAwb(this.awb);
            nota.setFactura(this.numinvoice);
            nota.setConcepto("Pago INVOICE" + this.numinvoice);
            //nota.setInv(this.numinvoice);
            nota.setInv(detalle.getInvoice());
            nota.setFecha(this.fechadespacho);
            //averigua quien es proveedor
            Plantacion plant = plantacionService.getPlantacion(detalle.getIdplantacion());
            nota.setIdPlantacion(plant.getIdproveedor());
            nota.setValor(precio);
            nota.setIdEstado(84);
            nota.setUsername("Facturacion");
            nota.setIdDebitoCredito(-1);
            nota.setIdConcepto(76);
            nota.setCliente(empresa.getPrefijo() + clienteService.getClienteXId(pedido.getIdcliente()).getCodigo());
            dominioService.crearNota(nota);
	}
	
	//nota para cliente
	
	Nota nota = new Nota ();
	nota.setIdpedido(this.idPedido);
    nota.setAwb(this.awb);
    nota.setFactura(this.numinvoice);
    nota.setConcepto("Pago INVOICE" + this.numinvoice);
    nota.setFecha(this.fechadespacho);
    nota.setIdCliente(pedido.getIdcliente());
    nota.setValor(this.preciototalcompleto.doubleValue());
    nota.setIdEstado(84);
    nota.setUsername("Facturacion");
    nota.setIdDebitoCredito(1);
    nota.setIdConcepto(76);
    nota.setInv(this.numinvoice);
    nota.setCliente(empresa.getPrefijo() + clienteService.getClienteXId(pedido.getIdcliente()).getCodigo());
    dominioService.crearNota(nota);
    
    //nota para carguera
	  if(this.idAgencia != null){
  	  nota = new Nota ();
  	  nota.setIdpedido(this.idPedido);
      nota.setAwb(this.awb);
      nota.setFactura(this.numinvoice);
      nota.setConcepto("Pago INVOICE" + this.numinvoice);
      nota.setFecha(this.fechadespacho);
      nota.setIdAgencia(this.idAgencia);
      nota.setValor(this.preciototaltransporteast.doubleValue());
      nota.setIdEstado(84);
      nota.setUsername("Facturacion");
      nota.setIdDebitoCredito(-1);
      nota.setIdConcepto(76);
      nota.setInv(this.awb);
      nota.setCliente(empresa.getPrefijo() + clienteService.getClienteXId(pedido.getIdcliente()).getCodigo());
      dominioService.crearNota(nota);
	  }
	  
	  //nota para handler
	  if(this.idHandler != null){
		  Handler han = dominioService.getHandlerXId(this.idHandler);
		  if(this.preciototalpreculinast.doubleValue() != 0.0){
			  nota = new Nota ();
			  nota.setIdpedido(this.idPedido);
			  nota.setAwb(this.awb);
			  nota.setFactura(this.numinvoice);
			  nota.setConcepto("Pago INVOICE" + this.numinvoice);
			  nota.setFecha(this.fechadespacho);
			  nota.setIdHandler(this.idHandler);
			  nota.setValor(this.preciototalpreculinast.doubleValue());
			  nota.setIdEstado(84);
			  nota.setUsername("Facturacion");
			  nota.setIdDebitoCredito(-1);
			  nota.setIdConcepto(76);
			  nota.setInv(this.awb);
			  nota.setCliente(empresa.getPrefijo() + clienteService.getClienteXId(pedido.getIdcliente()).getCodigo());
			  dominioService.crearNota(nota);
		  } 
	  }	  
	
}

public List<ComposicionFacturado> getComposiciones(List<PedidoBean> detalles, Integer idDetalle,DetallePedidoFacturado detalle){
	List <ComposicionFacturado> composiciones = new ArrayList<ComposicionFacturado>();
	double  precio = 0.0;
	Integer unidades = 0;
	for(PedidoBean pedidoBean : detalles){
		
		if((pedidoBean.getIddetalle().intValue() == idDetalle.intValue()) && !pedidoBean.isDetalle()){
			
			ComposicionFacturado composicion = new ComposicionFacturado();
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
	detalle.setPrecio(precio);
	detalle.setCantidadtotal(unidades);
	
	return composiciones;
}
    
    public String facturar(EncabezadoPedidoBean pedidoeditar){
    	
    	PedidoConciliado pedido = pedidoService.getPedidoConciliadoXId(pedidoeditar.getIdpedido());
    	
    	if(pedido != null){
    	
    	this.idPedido=pedido.getId();
    	this.idCliente = pedido.getIdcliente();
    	this.idCliente2 = pedido.getIdcliente();
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
    	
    	
    	subirGeneral(pedidoService.getDetallesPedidoConciliado(pedidoeditar.getIdpedido()));
    	subirGeneralConciliado(pedidoService.getDetallesPedidoConciliado(pedidoeditar.getIdpedido()));
    	setDatosGenerales();
    	setAgencias();
    	//getAerolineas();
    	
    	}else {
    		// es solo carga no hay nada en conciliado
    		Pedido pedidoPeso = pedidoService.getPedidoXId(pedidoeditar.getIdpedido());
    		
    		this.idPedido=pedidoPeso.getId();
        	this.idCliente = pedidoPeso.getIdcliente();
        	this.idCliente2 = pedidoPeso.getIdcliente();
        	this.idPais= pedidoPeso.getIdpais();
        	this.idPaisDestino= pedidoPeso.getIdpaisdestino();
        	getCiudadesPais();
        	getCiudadesPaisDestino();
        	this.idCiudad = pedidoPeso.getIdciudad();
        	this.idCiudadDestino = pedidoPeso.getIdciudaddestino();
        	this.fechadespacho = pedidoPeso.getFechadespacho();
        	this.fechapedido = pedidoPeso.getFechapedido();
        	this.fechallegada = pedidoPeso.getFechallegada();
        	this.fechallegadafinal = pedidoPeso.getFechallegadafinal();
        	this.observaciones = pedidoPeso.getObservaciones();
        	this.programado=pedidoPeso.isProgramado();
        	this.lunes=pedidoPeso.isLunes();
        	this.martes=pedidoPeso.isMartes();
        	this.miercoles=pedidoPeso.isMiercoles();
        	this.jueves=pedidoPeso.isJueves();
        	this.viernes=pedidoPeso.isViernes();
        	this.sabado=pedidoPeso.isSabado();
        	this.domingo=pedidoPeso.isDomingo();
        	
        	//los detalles deben estar basios al no tener flor
        	
        	subirGeneral(new ArrayList<DetallePedidoConciliado>());
        	subirGeneralConciliado(new ArrayList<DetallePedidoConciliado>());
        	setDatosGenerales();
        	setAgencias();
    	}
    	System.out.println("FACTURANDO PEDIDO");
    	return "facturarpedido";
    	
    }
    
public String facturarEditar(EncabezadoPedidoBean pedidoeditar){
    	
    	PedidoFacturado pedido = pedidoService.getPedidoFacturadoXId(pedidoeditar.getIdpedido());
    	
    	this.idPedido=pedido.getId();
    	this.idCliente = pedido.getIdcliente();
    	this.idCliente2 = pedido.getIdcliente();
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
     	
    	
    	//datos de enabezado y carga
    	
    	this.awb = pedido.getAwb();
    	this.boxesid = pedido.getBoxesid();
    	this.client = pedido.getClient();
    	this.idAgencia = pedido.getForwarder();
    	this.fechafactura = pedido.getDate();
    	this.numinvoice = pedido.getInvoice();
    	this.idAerolinea = pedido.getAirline();
    	this.idHandler = pedido.getIdHandler();
    	this.idTruck = pedido.getIdTruck();
    	this.valorconceppre = new BigDecimal(pedido.getValorconceppre()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	
    	this.totalPiezasAdicional=pedido.getPiezasadicionales();
    	
    	this.concepadd = pedido.getConcepto();
    	this.concepadd2 = pedido.getConcepto1();
    	this.concepadd3 = pedido.getConcepto2();
    	this.valorconcepadd = new BigDecimal(pedido.getValorconcepto()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	this.valorconcepadd2 = new BigDecimal(pedido.getValorconcepto1()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	this.valorconcepadd3 = new BigDecimal(pedido.getValorconcepto2()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	this.valorconcepaddast = new BigDecimal(pedido.getValorconceptoast()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	this.valorconcepadd2ast = new BigDecimal(pedido.getValorconcepto1ast()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	this.valorconcepadd3ast = new BigDecimal(pedido.getValorconcepto2ast()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	this.pesoacli = new BigDecimal(pedido.getPesocliente()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	this.pesoast = new BigDecimal(pedido.getPesoastoria()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	this.valorkgcli = new BigDecimal(pedido.getTarifacliente()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	this.valorkgast = new BigDecimal(pedido.getTarifaastoria()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	
    	
    	this.concepto1trans = pedido.getConceptotrans1();
    	this.concepto2trans  = pedido.getConceptotrans2();
    	this.concepto3trans = pedido.getConceptotrans3();
    	this.concepto4trans = pedido.getConceptotrans4();
    	
    	this.valorconcepto1trans = new BigDecimal(pedido.getValconceptotrans1()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	this.valorconcepto2trans = new BigDecimal(pedido.getValconceptotrans2()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	this.valorconcepto3trans = new BigDecimal(pedido.getValconceptotrans3()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	this.valorconcepto4trans = new BigDecimal(pedido.getValconceptotrans4()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	this.valorconcepto1transast = new BigDecimal(pedido.getValconceptotransast1()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	this.valorconcepto2transast = new BigDecimal(pedido.getValconceptotransast2()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	this.valorconcepto3transast = new BigDecimal(pedido.getValconceptotransast3()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	this.valorconcepto4transast = new BigDecimal(pedido.getValconceptotransast4()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	
    	//preculin
    	this.preculinclie = new BigDecimal(pedido.getPreculincli()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	this.preculinast = new BigDecimal(pedido.getPreculinast()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	this.preciototalpreculinclie = new BigDecimal(pedido.getTotalpreculincli()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	this.preciototalpreculinast = new BigDecimal(pedido.getTotalpreculinast()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	
    	
    	subirGeneralFacturado(pedidoService.getDetallesPedidoFacturado(pedidoeditar.getIdpedido()));
    	subirGeneralConciliado(pedidoService.getDetallesPedidoConciliado(pedidoeditar.getIdpedido()));
    	setDatosGenerales();
    	setAgencias();
    	getAerolineas();
    	getTrucksHandlerEditar();
    	return "facturarpedido";
    	
    }

    public void getOtrosConceptosTransporte(Destino destino){
    	
    	this.concepto1trans = destino.getConcepto1();
    	this.concepto2trans = destino.getConcepto2();
    	this.concepto3trans = destino.getConcepto3();
    	this.concepto4trans = destino.getConcepto4();
    	this.valorconcepto1trans = (new BigDecimal(destino.getValor1())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
    	this.valorconcepto2trans = (new BigDecimal(destino.getValor2())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
    	this.valorconcepto3trans = (new BigDecimal(destino.getValor3())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
    	this.valorconcepto4trans = (new BigDecimal(destino.getValor4())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
    	this.valorconcepto1transast = (new BigDecimal(destino.getValorast1())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
    	this.valorconcepto2transast = (new BigDecimal(destino.getValorast2())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
    	this.valorconcepto3transast = (new BigDecimal(destino.getValorast3())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
    	this.valorconcepto4transast = (new BigDecimal(destino.getValorast4())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
    	
    }
    
    private void abrirConfirmacion(){
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmafacturarpedido"));
		//RequestContext.getCurrentInstance().showMessageInDialog(message);
		PrimeFaces.current().dialog().showMessageDynamic(message);
		return;

	}
    
    public void eliminar(PedidoBean pedido){
    	detallesTemporal.remove(pedido);
    	return;
    }
    public void eliminar2(PedidoBean pedido){
    	
    	List <PedidoBean> detallesPedidoIndex = new ArrayList<PedidoBean>();
    	for(PedidoBean pedidoaux:detallesPedido){
    		if(pedidoaux.getIddetalle().intValue() == pedido.getIddetalle().intValue()){
    			detallesPedidoIndex.add(pedidoaux);
    		}
    	}

    	detallesPedido.removeAll(detallesPedidoIndex);
        this.getNuevaLista();
    	
    	return;
    }
    
    public void retomar(PedidoBean pedido){
      	
  	  subirDetalleRetomar(pedido);
  	  eliminar2(pedido); 
  	  getProductos2Plantacion(this.idPlantacion);
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
  		Dominio producto = dominioService.getDominio(Productoper.getIdvariedad());
    	Dominio dominioPadre = dominioService.getDominio(producto.getDominiopadre());
    	Dominio grado = dominioService.getDominio(Productoper.getIdgrado());	
    	
   		//for(Producto productoaux:productos){
           	
        //   	if(Productoper.getCodigo().equalsIgnoreCase(productoaux.getCodigo())){
           	
         //  	Dominio producto= dominioService.getDominio(productoaux.getIdProducto());
        //   	Dominio grado = dominioService.getDominio(productoaux.getGrado());
           	//Dominio color = dominioService.getDominio(productoaux.getColor());
           	Plantacion plantacion = null;
           	if(pedido.getIdplantacion() != null){
           		plantacion = plantacionService.getPlantacion(pedido.getIdplantacion());
           	}
               
           	pedidoaux.setCodigo(Productoper.getCodigo());
           	//pedidoaux.setIdcolor(productoaux.getColor());
           	pedidoaux.setIdgrado(grado.getId());
           	pedidoaux.setIdplantacion(pedido.getIdplantacion());
           	pedidoaux.setIdvariedad(pedido.getIdvariedad());
           	pedidoaux.setNombrevariedad(producto.getNomcorto());
           	pedidoaux.setNombretipo((dominioService.getDominio(dominioService.getDominio(producto.getId()).getDominiopadre())).getNomcorto());
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
        //   	break;
        //   	}
        //   }
  	}
   	this.cantidadfull = pedidoCabezera.getCantidadfull();
   	this.packing =  pedidoCabezera.getPacking();
  	this.cantidad = cantidad;
   	return;
   }
    
    private void setDatosGenerales(){
    	
    	String letraPais = dominioService.getDominio(this.idPais).getNomcorto().substring(0,1);
    	Calendar cal = Calendar.getInstance();
    	//ponemos la fecha del despacho para la factura
    	
    	cal.setTime(this.fechadespacho);
    	
    	String dia = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
    	String mes = String.valueOf(cal.get(Calendar.MONTH)+1); //se pone asi pùes esta retornando un valor menos
    	String ano = String.valueOf(cal.get(Calendar.YEAR));
    	String codigo = clienteService.getClienteXId(this.idCliente).getCodigo();
    	
    	if(dia.equalsIgnoreCase("1") || dia.equalsIgnoreCase("2") || dia.equalsIgnoreCase("3") || dia.equalsIgnoreCase("4") || dia.equalsIgnoreCase("5") || dia.equalsIgnoreCase("6") || dia.equalsIgnoreCase("7") || dia.equalsIgnoreCase("8") || dia.equalsIgnoreCase("9")){
    		dia = "0" + dia; 
    	}
    	if(mes.equalsIgnoreCase("1") || mes.equalsIgnoreCase("2") || mes.equalsIgnoreCase("3") || mes.equalsIgnoreCase("4") || mes.equalsIgnoreCase("5") || mes.equalsIgnoreCase("6") || mes.equalsIgnoreCase("7") || mes.equalsIgnoreCase("8") || mes.equalsIgnoreCase("9")){
    		mes = "0" + mes; 
    	}
    	
    	this.numinvoice=codigo+letraPais+ano+mes+dia;
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
    
    public void setAgencias(){
    	
    	Integer idciudaddestino;
    	Integer idpaisdestino;
    	//idpaisdestino = Integer.valueOf(bundle.getString("bd.idpaisdestino"));
    	//idciudaddestino = Integer.valueOf(bundle.getString("bd.idciudaddestino"));
    	idpaisdestino = this.idPaisDestino;
    	idciudaddestino = this.idCiudadDestino;
    	List <Map> listaAgencias = dominioService.getAgenciasDestino(this.idPais, idpaisdestino, this.idCiudad, idciudaddestino, null);
    	listaAgenciasSelect = Convertidor.mapToSelectdItemsIdNombre(listaAgencias);
    }
   public void getAerolineas(){
    	
    	Integer idciudaddestino;
    	Integer idpaisdestino;
    	//idpaisdestino = Integer.valueOf(bundle.getString("bd.idpaisdestino"));
    	//idciudaddestino = Integer.valueOf(bundle.getString("bd.idciudaddestino"));
    	idpaisdestino = this.idPaisDestino;
    	idciudaddestino = this.idCiudadDestino;
    	List <Map> listaAgencias = dominioService.getAerolineasAgenciaDestino(this.idAgencia,this.idPais, idpaisdestino, this.idCiudad, idciudaddestino, null);
    	listaAerolineasSelect = Convertidor.mapToSelectdItemsIdNombre(listaAgencias);
    }
     
   public List <Destino> getDestinos(){
	   Integer idciudaddestino;
   	   Integer idpaisdestino;
   	   Destino destion; 
   	   //idpaisdestino = Integer.valueOf(bundle.getString("bd.idpaisdestino"));
   	   //idciudaddestino = Integer.valueOf(bundle.getString("bd.idciudaddestino"));
   	   idpaisdestino = this.idPaisDestino;
   	   idciudaddestino = this.idCiudadDestino;
	   List <Destino> destinos = dominioService.getDestino(this.idAgencia, this.idAerolinea, this.idPais, idpaisdestino, this.idCiudad, idciudaddestino, null);
       return destinos;
   }
    
    
    public Double getPrecioTotal(){
    	double total = 0.0;
    	double costoflor = 0.0;
    	for (PedidoBean pedido:detallesPedido){
    		if(!pedido.isDetalle()){
    			total = total + pedido.getPreciototal();
    			costoflor = costoflor + pedido.getPreciototalast();
    		}	
    	}
    	this.preciototalflor = new BigDecimal(total).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	this.preciototalflorast = new BigDecimal(costoflor).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	
    	this.getTotales(detallesPedido);
    	if(this.idAerolinea != null && this.idAgencia != null)
    		this.getPrecioCarga2();
    		
    	return  total;
    }
    
    public Double getPrecioTotalNuevo(){
    	double total = 0.0;
    	double costoflor = 0.0;
    	for (PedidoBean pedido:detallesPedido){
    		if(!pedido.isDetalle()){
    			total = total + pedido.getPreciototal();
    			costoflor = costoflor + pedido.getPreciototalast();
    		}	
    	}
    	this.preciototalflor = new BigDecimal(total).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	this.preciototalflorast = new BigDecimal(costoflor).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	
    	if(this.idAerolinea != null && this.idAgencia != null)
    		this.getPrecioCarga3();
    	this.getTotales(detallesPedido);	
    	return  total;
    }
    
     
    public Double getPrecioCarga(){
    	
    	Double precio = 0.0;
    	Double precioCliente = 0.0;
    	Double totalfb = 0.0;
    	Double preciokilo= 0.0;
    	Double preciootros= 0.0;
    	
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
    	    	
    	//calculo de valores del sistema pesos
    	
    	this.pesoast = new BigDecimal(pesoProm).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	this.pesoacli = new BigDecimal(pesoProm).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	
    					
    	
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
    		this.valorkgast= new BigDecimal(tarifahallada).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    		this.valorkgcli= new BigDecimal(tarifahalladaCliente).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    		
    		precio = tarifahallada * pesoProm;
    		precioCliente = tarifahalladaCliente * pesoProm;
    		getOtrosConceptosTransporte(destinohallado);
    		
         
    		
    	}
    	
    	//calculo de precios del preculin
    	this.preciototalpreculinclie = new BigDecimal(this.preculinclie.doubleValue() * this.pesoacli.doubleValue()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	this.preciototalpreculinast = new BigDecimal(this.preculinast.doubleValue() * this.pesoast.doubleValue()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	
    	//calculo de precios para cambio
    	
    	if(this.valorconcepadd == null)
	 		this.valorconcepadd = new BigDecimal(0.0);
	 	if(this.valorconcepadd2 == null)
	 		this.valorconcepadd2 = new BigDecimal(0.0);
	 	if(this.valorconcepadd3 == null)
	 		this.valorconcepadd3 = new BigDecimal(0.0);
	 	if(this.valorconcepto1trans == null)
	 		this.valorconcepto1trans = new BigDecimal(0.0);
	 	if(this.valorconcepto2trans == null)
	 		this.valorconcepto2trans = new BigDecimal(0.0);
	 	if(this.valorconcepto3trans == null)
	 		this.valorconcepto3trans = new BigDecimal(0.0);
	 	if(this.valorconcepto4trans == null)
	 		this.valorconcepto4trans = new BigDecimal(0.0);
    	
    	    	
    	this.preciototaltransporte = new BigDecimal(precioCliente.doubleValue() + this.valorconcepto1trans.doubleValue() + this.valorconcepto2trans.doubleValue() + this.valorconcepto3trans.doubleValue() + this.valorconcepto4trans.doubleValue()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	BigDecimal preciosadicionales = new BigDecimal(this.valorconcepadd.doubleValue() + this.valorconcepadd2.doubleValue() + this.valorconcepadd3.doubleValue()) ;
    	this.getDescuentoPorPrepago(this.preciototalflor.doubleValue() + this.preciototaltransporte.doubleValue() + preciosadicionales.doubleValue() + this.preciototalpreculinclie.doubleValue());
    	this.preciototalcompleto = new BigDecimal(this.preciototalflor.doubleValue() + this.preciototaltransporte.doubleValue() + preciosadicionales.doubleValue()+ this.valorconceppre.doubleValue() + this.preciototalpreculinclie.doubleValue()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	
        //totales Astoria   
    	
    	if(this.valorconcepaddast == null)
    		this.valorconcepaddast = new BigDecimal(0.0);
    	if(this.valorconcepadd2ast == null)
    		this.valorconcepadd2ast = new BigDecimal(0.0);
    	if(this.valorconcepadd3ast == null)
    		this.valorconcepadd3ast = new BigDecimal(0.0);
    	if(this.valorconcepto1transast == null)
	 		this.valorconcepto1transast = new BigDecimal(0.0);
	 	if(this.valorconcepto2transast == null)
	 		this.valorconcepto2transast = new BigDecimal(0.0);
	 	if(this.valorconcepto3transast == null)
	 		this.valorconcepto3transast = new BigDecimal(0.0);
	 	if(this.valorconcepto4transast == null)
	 		this.valorconcepto4transast = new BigDecimal(0.0);
    	
    	this.preciototaltransporteast = new BigDecimal((this.pesoast.doubleValue() * this.valorkgast.doubleValue()) + this.valorconcepto1transast.doubleValue() + this.valorconcepto2transast.doubleValue() + this.valorconcepto3transast.doubleValue() + this.valorconcepto4transast.doubleValue()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	BigDecimal preciosadicionalesast =new BigDecimal(this.valorconcepaddast.doubleValue() + this.valorconcepadd2ast.doubleValue() + valorconcepadd3ast.doubleValue());
    	this.preciototalcompletoast = new BigDecimal(this.preciototalflorast.doubleValue() + this.preciototaltransporteast.doubleValue() + preciosadicionalesast.doubleValue() + this.preciototalpreculinast.doubleValue()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
    	return precio;
    	
    }
    
 public BigDecimal getPrecioCarga2(){
	 
	 	this.preciototalpreculinclie = (new BigDecimal(this.preculinclie.doubleValue() * this.pesoacli.doubleValue())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
	 	this.preciototalpreculinast = (new BigDecimal(this.preculinast.doubleValue() * this.pesoast.doubleValue())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
    	
	 	//totales cliente   
		 if(this.valorconcepadd == null)
	 		this.valorconcepadd = new BigDecimal(0.0);
	 	if(this.valorconcepadd2 == null)
	 		this.valorconcepadd2 = new BigDecimal(0.0);
	 	if(this.valorconcepadd3 == null)
	 		this.valorconcepadd3 = new BigDecimal(0.0);
	 	if(this.valorconcepto1trans == null)
	 		this.valorconcepto1trans = new BigDecimal(0.0);
	 	if(this.valorconcepto2trans == null)
	 		this.valorconcepto2trans = new BigDecimal(0.0);
	 	if(this.valorconcepto3trans == null)
	 		this.valorconcepto3trans = new BigDecimal(0.0);
	 	if(this.valorconcepto4trans == null)
	 		this.valorconcepto4trans = new BigDecimal(0.0);
	    	
    	
    	this.preciototaltransporte = (new BigDecimal((this.pesoacli.doubleValue() * this.valorkgcli.doubleValue()) + this.valorconcepto1trans.doubleValue() + this.valorconcepto2trans.doubleValue() + this.valorconcepto3trans.doubleValue() + this.valorconcepto4trans.doubleValue())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
    	Double preciosadicionales = this.valorconcepadd.doubleValue() + this.valorconcepadd2.doubleValue() + this.valorconcepadd3.doubleValue();
    	this.getDescuentoPorPrepago(this.preciototalflor.doubleValue() + this.preciototaltransporte.doubleValue() + preciosadicionales.doubleValue() + this.preciototalpreculinclie.doubleValue());
    	this.preciototalcompleto = (new BigDecimal(this.preciototalflor.doubleValue() + this.preciototaltransporte.doubleValue() + preciosadicionales.doubleValue() + this.valorconceppre.doubleValue() + this.preciototalpreculinclie.doubleValue())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
    	
        //totales Astoria   
    	
    	if(this.valorconcepaddast == null)
    		this.valorconcepaddast = new BigDecimal(0.0);
    	if(this.valorconcepadd2ast == null)
    		this.valorconcepadd2ast = new BigDecimal(0.0);
    	if(this.valorconcepadd3ast == null)
    		this.valorconcepadd3ast = new BigDecimal(0.0);
    	if(this.valorconcepto1transast == null)
	 		this.valorconcepto1transast = new BigDecimal(0.0);
	 	if(this.valorconcepto2transast == null)
	 		this.valorconcepto2transast = new BigDecimal(0.0);
	 	if(this.valorconcepto3transast == null)
	 		this.valorconcepto3transast = new BigDecimal(0.0);
	 	if(this.valorconcepto4transast == null)
	 		this.valorconcepto4transast = new BigDecimal(0.0);
    	
    	this.preciototaltransporteast = (new BigDecimal((this.pesoast.doubleValue() * this.valorkgast.doubleValue()) + this.valorconcepto1transast.doubleValue() + this.valorconcepto2transast.doubleValue() + this.valorconcepto3transast.doubleValue() + this.valorconcepto4transast.doubleValue())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
    	Double preciosadicionalesast = this.valorconcepaddast.doubleValue() + this.valorconcepadd2ast.doubleValue() + this.valorconcepadd3ast.doubleValue();
    	this.preciototalcompletoast = (new BigDecimal(this.preciototalflorast.doubleValue() + this.preciototaltransporteast.doubleValue() + preciosadicionalesast.doubleValue() + this.preciototalpreculinast.doubleValue())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
    	if (precio != null){
    		return precio.setScale(3, BigDecimal.ROUND_HALF_EVEN);
    	}else{
    		return precio;
    	}
    	
    }
 
 public BigDecimal getPrecioCarga3(){
	 
	 this.preciototalpreculinclie = (new BigDecimal(this.preculinclie.doubleValue() * this.pesoacli.doubleValue())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
 	 this.preciototalpreculinast = (new BigDecimal(this.preculinast.doubleValue() * this.pesoast.doubleValue())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
	 
 	//totales cliente   
 	 if(this.valorconcepadd == null)
	 		this.valorconcepadd = new BigDecimal(0.0);
	 	if(this.valorconcepadd2 == null)
	 		this.valorconcepadd2 = new BigDecimal(0.0);
	 	if(this.valorconcepadd3 == null)
	 		this.valorconcepadd3 = new BigDecimal(0.0);
	 	if(this.valorconcepto1trans == null)
	 		this.valorconcepto1trans = new BigDecimal(0.0);
	 	if(this.valorconcepto2trans == null)
	 		this.valorconcepto2trans = new BigDecimal(0.0);
	 	if(this.valorconcepto3trans == null)
	 		this.valorconcepto3trans = new BigDecimal(0.0);
	 	if(this.valorconcepto4trans == null)
	 		this.valorconcepto4trans = new BigDecimal(0.0);
	 
 	
 	this.preciototaltransporte = (new BigDecimal((this.pesoacli.doubleValue() * this.valorkgcli.doubleValue()) + this.valorconcepto1trans.doubleValue() + this.valorconcepto2trans.doubleValue() + this.valorconcepto3trans.doubleValue() + this.valorconcepto4trans.doubleValue())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
 	Double preciosadicionales = this.valorconcepadd.doubleValue() + this.valorconcepadd2.doubleValue() + this.valorconcepadd3.doubleValue();
 	this.getDescuentoPorPrepago(this.preciototalflor.doubleValue() + this.preciototaltransporte.doubleValue() + preciosadicionales.doubleValue()  + this.preciototalpreculinclie.doubleValue());
 	this.preciototalcompleto = (new BigDecimal(this.preciototalflor.doubleValue() + this.preciototaltransporte.doubleValue() + preciosadicionales.doubleValue() + this.valorconceppre.doubleValue()  + this.preciototalpreculinclie.doubleValue())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
 	
     //totales Astoria   
 	
 	if(this.valorconcepaddast == null)
		this.valorconcepaddast = new BigDecimal(0.0);
	if(this.valorconcepadd2ast == null)
		this.valorconcepadd2ast = new BigDecimal(0.0);
	if(this.valorconcepadd3ast == null)
		this.valorconcepadd3ast = new BigDecimal(0.0);
	if(this.valorconcepto1transast == null)
 		this.valorconcepto1transast = new BigDecimal(0.0);
 	if(this.valorconcepto2transast == null)
 		this.valorconcepto2transast = new BigDecimal(0.0);
 	if(this.valorconcepto3transast == null)
 		this.valorconcepto3transast = new BigDecimal(0.0);
 	if(this.valorconcepto4transast == null)
 		this.valorconcepto4transast = new BigDecimal(0.0);
 	
 	this.preciototaltransporteast = (new BigDecimal((this.pesoast.doubleValue() * this.valorkgast.doubleValue()) + this.valorconcepto1transast.doubleValue() + this.valorconcepto2transast.doubleValue() + this.valorconcepto3transast.doubleValue() + this.valorconcepto4transast.doubleValue())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
 	Double preciosadicionalesast = this.valorconcepaddast.doubleValue() + this.valorconcepadd2ast.doubleValue() + this.valorconcepadd3ast.doubleValue();
 	this.preciototalcompletoast = (new BigDecimal(this.preciototalflorast.doubleValue() + this.preciototaltransporteast.doubleValue() + preciosadicionalesast.doubleValue() + this.preciototalpreculinast.doubleValue())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
 	if (precio != null){
		return precio.setScale(3, BigDecimal.ROUND_HALF_EVEN);
	}else{
		return precio;
	}
 	
 }
 
 public BigDecimal getPrecioCarga4(){
     
	 if(this.valorconcepadd == null)
	 		this.valorconcepadd = new BigDecimal(0.0);
	 	if(this.valorconcepadd2 == null)
	 		this.valorconcepadd2 = new BigDecimal(0.0);
	 	if(this.valorconcepadd3 == null)
	 		this.valorconcepadd3 = new BigDecimal(0.0);
	 	if(this.valorconcepto1trans == null)
	 		this.valorconcepto1trans = new BigDecimal(0.0);
	 	if(this.valorconcepto2trans == null)
	 		this.valorconcepto2trans = new BigDecimal(0.0);
	 	if(this.valorconcepto3trans == null)
	 		this.valorconcepto3trans = new BigDecimal(0.0);
	 	if(this.valorconcepto4trans == null)
	 		this.valorconcepto4trans = new BigDecimal(0.0);
	 
	 
	 this.preciototalpreculinclie = (this.preculinclie.multiply(this.pesoacli)).setScale(3, BigDecimal.ROUND_HALF_EVEN);
 	 this.preciototalpreculinast = (this.preculinast.multiply(this.pesoast)).setScale(3, BigDecimal.ROUND_HALF_EVEN);
	 	
	 	this.preciototaltransporte = (new BigDecimal((this.pesoacli.multiply(this.valorkgcli)).doubleValue() + this.valorconcepto1trans.doubleValue() + this.valorconcepto2trans.doubleValue() + this.valorconcepto3trans.doubleValue() + this.valorconcepto4trans.doubleValue())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
	 	Double preciosadicionales = this.valorconcepadd.doubleValue() + this.valorconcepadd2.doubleValue() + this.valorconcepadd3.doubleValue();
	 	this.preciototalcompleto = (new BigDecimal(this.preciototalflor.doubleValue() + this.preciototaltransporte.doubleValue() + preciosadicionales.doubleValue() + this.valorconceppre.doubleValue() +  this.preciototalpreculinclie.doubleValue())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
	 	this.getDescuentoPorPrepago(this.preciototalflor.doubleValue() + this.preciototaltransporte.doubleValue() + preciosadicionales.doubleValue());
	   
	 	if (precio != null){
    		return precio.setScale(3, BigDecimal.ROUND_HALF_EVEN);
    	}else{
    		return precio;
    	}
	 	
	 }
 
 public BigDecimal getPrecioCarga5(){
	 
	 
 	//totales cliente   
	 
	 if(this.valorconcepadd == null)
 		this.valorconcepadd = new BigDecimal(0.0);
 	if(this.valorconcepadd2 == null)
 		this.valorconcepadd2= new BigDecimal(0.0);
 	if(this.valorconcepadd3 == null)
 		this.valorconcepadd3 = new BigDecimal(0.0);
 	if(this.valorconcepto1trans == null)
	 		this.valorconcepto1trans = new BigDecimal(0.0);
	if(this.valorconcepto2trans == null)
	 		this.valorconcepto2trans = new BigDecimal(0.0);
	if(this.valorconcepto3trans == null)
	 		this.valorconcepto3trans = new BigDecimal(0.0);
	if(this.valorconcepto4trans == null)
	 		this.valorconcepto4trans = new BigDecimal(0.0);
	 
	 this.preciototalpreculinclie = (new BigDecimal(this.preculinclie.doubleValue() * this.pesoacli.doubleValue())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
 	 this.preciototalpreculinast = (new BigDecimal(this.preculinast.doubleValue() * this.pesoast.doubleValue())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
 	
 	this.preciototaltransporte = (new BigDecimal((this.pesoacli.doubleValue() * this.valorkgcli.doubleValue()) + this.valorconcepto1trans.doubleValue() + this.valorconcepto2trans.doubleValue() + this.valorconcepto3trans.doubleValue() + this.valorconcepto4trans.doubleValue())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
 	Double preciosadicionales = this.valorconcepadd.doubleValue() + this.valorconcepadd2.doubleValue() + this.valorconcepadd3.doubleValue();
 	this.getDescuentoPorPrepago(this.preciototalflor.doubleValue() + this.preciototaltransporte.doubleValue() + preciosadicionales.doubleValue()  + this.preciototalpreculinclie.doubleValue());
 	this.preciototalcompleto = (new BigDecimal(this.preciototalflor.doubleValue() + this.preciototaltransporte.doubleValue() + preciosadicionales.doubleValue() + this.valorconceppre.doubleValue()  + this.preciototalpreculinclie.doubleValue())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
 	
     //totales Astoria   
 	
 	if(this.valorconcepaddast == null)
		this.valorconcepaddast = new BigDecimal(0.0);
	if(this.valorconcepadd2ast == null)
		this.valorconcepadd2ast = new BigDecimal(0.0);
	if(this.valorconcepadd3ast == null)
		this.valorconcepadd3ast = new BigDecimal(0.0);
	if(this.valorconcepto1transast == null)
 		this.valorconcepto1transast = new BigDecimal(0.0);
 	if(this.valorconcepto2transast == null)
 		this.valorconcepto2transast = new BigDecimal(0.0);
 	if(this.valorconcepto3transast == null)
 		this.valorconcepto3transast = new BigDecimal(0.0);
 	if(this.valorconcepto4transast == null)
 		this.valorconcepto4transast = new BigDecimal(0.0);
 	
 	
 	
 	this.preciototaltransporteast = (new BigDecimal((this.pesoast.doubleValue() * this.valorkgast.doubleValue()) + this.valorconcepto1transast.doubleValue() + this.valorconcepto2transast.doubleValue() + this.valorconcepto3transast.doubleValue() + this.valorconcepto4transast.doubleValue())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
 	Double preciosadicionalesast = this.valorconcepaddast.doubleValue() + this.valorconcepadd2ast.doubleValue() + this.valorconcepadd3ast.doubleValue();
 	this.preciototalcompletoast = (new BigDecimal(this.preciototalflorast.doubleValue() + this.preciototaltransporteast.doubleValue() + preciosadicionalesast.doubleValue() + this.preciototalpreculinast.doubleValue())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
 	if (precio != null){
		return precio.setScale(3, BigDecimal.ROUND_HALF_EVEN);
	}else{
		return precio;
	}
 	
 }
 
 public Double getDescuentoPorPrepago(Double totalFactura){
	 Double descuento = 0.0;
	 Cliente cliente = clienteService.getClienteXId(this.idCliente2);
	 Dominio pais = dominioService.getDominio(this.idPais);
	 if(cliente.getSaldofinal() >= totalFactura){
		 descuento = (this.totalBoxes.doubleValue() * pais.getValor1().doubleValue()) * -1;
	 }
	 this.valorconceppre = new BigDecimal(descuento).setScale(3,BigDecimal.ROUND_HALF_EVEN);
	 return descuento;
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

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
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
	
	public int generarAleatorio(){
		Random  rnd = new Random();
		return rnd.nextInt();
		
	}

	public BigDecimal getPrecioProducto (Integer idPlantacion, Integer idProducto){
		if(pedidoService.getPrecioProducto(idPlantacion, idProducto,this.fechadespacho)== null){
			return null;
		}else {
			return (new BigDecimal(pedidoService.getPrecioProducto(idPlantacion, idProducto,this.fechadespacho))).setScale(3, BigDecimal.ROUND_HALF_EVEN);
		}	
	}
	
	public void getPrecioView(){
		boolean okprecio=false;
		boolean okcomision=false;
		Integer id = null;
		BigDecimal precio;
		Comision comision;
		com.bulls.astoria.persistence.Producto pro = dominioService.getProductoXCodigo(this.codigo);
		if(pro == null){
			//sale de una
			return;
		}
		if(this.idPlantacion!=null)
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
			this.comision = (new BigDecimal(comision.getComision())).setScale(3, BigDecimal.ROUND_HALF_EVEN);
    	}
		
		if(okprecio && okcomision){
			this.precioconcomision =  this.precio.add(this.comision);
		}
		
	}
	
	public void getTotales(List <PedidoBean> detallesPedido){
		double precio = 0.0;
		int cantidad = 0;
		int cantPiezas = 0;
		Double costoflor = 0.0;
		Double cantBoxes = 0.0;
		
		for(PedidoBean pedido: detallesPedido){
			if(pedido.getTipocomposicion()!= null){
			if(pedido.getTallosporfull() != null)
				cantidad = cantidad + pedido.getTallosporfull();
			if(pedido.getTallosporfull() != null){
				precio = precio + pedido.getPreciototal();
				costoflor = precio + pedido.getPreciototalast();
			}
			if(pedido.getPiezas()!= null){
				cantPiezas = cantPiezas + pedido.getPiezas();
			}
			if(pedido.getCantidadfull()!= null){
				cantBoxes = cantBoxes + pedido.getCantidadfull();
			}
					
		}
		}	
		//this.totalPrecio = precio;
		this.totalUnidades = cantidad;
		this.totalBoxes = new BigDecimal(cantBoxes).setScale(3,BigDecimal.ROUND_HALF_EVEN);
		this.totalPiezas = cantPiezas;
		
		
	}
	
public void getProductos2Plantacion(Integer idPlantacion){
		
		productosMap = dominioService.getProductosCompletoPlantacion(idPlantacion);
		//productos = getProductosmap(productosMap);
		productos2 = getProductosmap(productosMap);
        return; 
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
	
	public void getTotalesConciliado(List <PedidoBean> detallesPedido){
		BigDecimal precio = new BigDecimal(0.0);
		int cantidad = 0;
		double numboxes = 0;
		for(PedidoBean pedido: detallesPedido){
			if(pedido.getTipocomposicion()!= null){
			if(pedido.getTallosporfull() != null)
				cantidad = cantidad + pedido.getTallosporfull();
			if(pedido.getTallosporfull() != null)
				precio = precio.add(new BigDecimal(pedido.getPreciototal()).setScale(3,BigDecimal.ROUND_HALF_EVEN));
			}
			if(pedido.getCantidadfull() != null)
				numboxes = numboxes + pedido.getCantidadfull();
			}
					
		
		this.totalPrecioConciliado = precio;
		this.totalUnidadesConciliado = cantidad;
		this.totalBoxesConciliado = new BigDecimal(numboxes).setScale(3,BigDecimal.ROUND_HALF_EVEN);
		
	}

	public Integer getIdCliente2() {
		return idCliente2;
	}

	public void setIdCliente2(Integer idCliente2) {
		this.idCliente2 = idCliente2;
	}

	public Date getFechafactura() {
		return fechafactura;
	}

	public void setFechafactura(Date fechafactura) {
		this.fechafactura = fechafactura;
	}

	public String getNuminvoice() {
		return numinvoice;
	}

	public void setNuminvoice(String numinvoice) {
		this.numinvoice = numinvoice;
	}

	

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getBoxesid() {
		return boxesid;
	}

	public void setBoxesid(String boxesid) {
		this.boxesid = boxesid;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Integer getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(Integer idAgencia) {
		this.idAgencia = idAgencia;
	}

	
	public String getAwb() {
		return awb;
	}

	public void setAwb(String awb) {
		this.awb = awb;
	}

	public Integer getIdAerolinea() {
		return idAerolinea;
	}

	public void setIdAerolinea(Integer idAerolinea) {
		this.idAerolinea = idAerolinea;
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

	public BigDecimal getPreciototalflor() {
		return preciototalflor;
	}

	public void setPreciototalflor(BigDecimal preciototalflor) {
		this.preciototalflor = preciototalflor;
	}

	public BigDecimal getPreciototaltransporte() {
		return preciototaltransporte;
	}

	public void setPreciototaltransporte(BigDecimal preciototaltransporte) {
		this.preciototaltransporte = preciototaltransporte;
	}

	public BigDecimal getPreciototalcompleto() {
		return preciototalcompleto;
	}

	public void setPreciototalcompleto(BigDecimal preciototalcompleto) {
		this.preciototalcompleto = preciototalcompleto;
	}

	public List<SelectItem> getListaAgenciasSelect() {
		return listaAgenciasSelect;
	}

	public void setListaAgenciasSelect(List<SelectItem> listaAgenciasSelect) {
		this.listaAgenciasSelect = listaAgenciasSelect;
	}

	public List<SelectItem> getListaAerolineasSelect() {
		return listaAerolineasSelect;
	}

	public void setListaAerolineasSelect(List<SelectItem> listaAerolineasSelect) {
		this.listaAerolineasSelect = listaAerolineasSelect;
	}

	public Integer getTotalUnidadesConciliado() {
		return totalUnidadesConciliado;
	}

	public void setTotalUnidadesConciliado(Integer totalUnidadesConciliado) {
		this.totalUnidadesConciliado = totalUnidadesConciliado;
	}

	public BigDecimal getTotalPrecioConciliado() {
		return totalPrecioConciliado;
	}

	public void setTotalPrecioConciliado(BigDecimal totalPrecioConciliado) {
		this.totalPrecioConciliado = totalPrecioConciliado;
	}

	public List<PedidoBean> getDetallesPedidoConciliado() {
		return detallesPedidoConciliado;
	}

	public void setDetallesPedidoConciliado(
			List<PedidoBean> detallesPedidoConciliado) {
		this.detallesPedidoConciliado = detallesPedidoConciliado;
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

	public BigDecimal getComision() {
		return comision;
	}

	public void setComision(BigDecimal comision) {
		this.comision = comision;
	}

	public BigDecimal getPrecioconcomision() {
		return precioconcomision;
	}

	public void setPrecioconcomision(BigDecimal precioconcomision) {
		this.precioconcomision = precioconcomision;
	}
	
	
    
	public BigDecimal getPesoast() {
		return pesoast;
	}

	public void setPesoast(BigDecimal pesoast) {
		this.pesoast = pesoast;
	}

	public BigDecimal getValorkgast() {
		return valorkgast;
	}

	public void setValorkgast(BigDecimal valorkgast) {
		this.valorkgast = valorkgast;
	}

	public BigDecimal getPesoacli() {
		return pesoacli;
	}

	public void setPesoacli(BigDecimal pesoacli) {
		this.pesoacli = pesoacli;
	}

	public BigDecimal getValorkgcli() {
		return valorkgcli;
	}

	public void setValorkgcli(BigDecimal valorkgcli) {
		this.valorkgcli = valorkgcli;
	}

	public String getConcepadd() {
		return concepadd;
	}

	public void setConcepadd(String concepadd) {
		this.concepadd = concepadd;
	}

	public BigDecimal getValorconcepadd() {
		return valorconcepadd;
	}

	public void setValorconcepadd(BigDecimal valorconcepadd) {
		this.valorconcepadd = valorconcepadd;
	}

	public String getConcepadd2() {
		return concepadd2;
	}

	public void setConcepadd2(String concepadd2) {
		this.concepadd2 = concepadd2;
	}

	public BigDecimal getValorconcepadd2() {
		return valorconcepadd2;
	}

	public void setValorconcepadd2(BigDecimal valorconcepadd2) {
		this.valorconcepadd2 = valorconcepadd2;
	}

	public String getConcepadd3() {
		return concepadd3;
	}

	public void setConcepadd3(String concepadd3) {
		this.concepadd3 = concepadd3;
	}

	public BigDecimal getValorconcepadd3() {
		return valorconcepadd3;
	}

	public void setValorconcepadd3(BigDecimal valorconcepadd3) {
		this.valorconcepadd3 = valorconcepadd3;
	}
	
	

	public BigDecimal getPreciototaltransporteast() {
		return preciototaltransporteast;
	}

	public void setPreciototaltransporteast(BigDecimal preciototaltransporteast) {
		this.preciototaltransporteast = preciototaltransporteast;
	}

	public BigDecimal getPreciototalcompletoast() {
		return preciototalcompletoast;
	}

	public void setPreciototalcompletoast(BigDecimal preciototalcompletoast) {
		this.preciototalcompletoast = preciototalcompletoast;
	}

	public BigDecimal getValorconcepaddast() {
		return valorconcepaddast;
	}

	public void setValorconcepaddast(BigDecimal valorconcepaddast) {
		this.valorconcepaddast = valorconcepaddast;
	}

	public BigDecimal getValorconcepadd2ast() {
		return valorconcepadd2ast;
	}

	public void setValorconcepadd2ast(BigDecimal valorconcepadd2ast) {
		this.valorconcepadd2ast = valorconcepadd2ast;
	}

	public BigDecimal getValorconcepadd3ast() {
		return valorconcepadd3ast;
	}

	public void setValorconcepadd3ast(BigDecimal valorconcepadd3ast) {
		this.valorconcepadd3ast = valorconcepadd3ast;
	}
	
	

	public Integer getTotalUnidades() {
		return totalUnidades;
	}

	public void setTotalUnidades(Integer totalUnidades) {
		this.totalUnidades = totalUnidades;
	}
	
	

	public BigDecimal getTotalBoxes() {
		return totalBoxes;
	}

	public void setTotalBoxes(BigDecimal totalBoxes) {
		this.totalBoxes = totalBoxes;
	}

	public Integer getTotalPiezas() {
		return totalPiezas;
	}

	public void setTotalPiezas(Integer totalPiezas) {
		this.totalPiezas = totalPiezas;
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

	public BigDecimal getValorconcepto1trans() {
		return valorconcepto1trans;
	}

	public void setValorconcepto1trans(BigDecimal valorconcepto1trans) {
		this.valorconcepto1trans = valorconcepto1trans;
	}

	public BigDecimal getValorconcepto2trans() {
		return valorconcepto2trans;
	}

	public void setValorconcepto2trans(BigDecimal valorconcepto2trans) {
		this.valorconcepto2trans = valorconcepto2trans;
	}

	public BigDecimal getValorconcepto3trans() {
		return valorconcepto3trans;
	}

	public void setValorconcepto3trans(BigDecimal valorconcepto3trans) {
		this.valorconcepto3trans = valorconcepto3trans;
	}

	public BigDecimal getValorconcepto4trans() {
		return valorconcepto4trans;
	}

	public void setValorconcepto4trans(BigDecimal valorconcepto4trans) {
		this.valorconcepto4trans = valorconcepto4trans;
	}
	
	

	public BigDecimal getValorconcepto1transast() {
		return valorconcepto1transast;
	}

	public void setValorconcepto1transast(BigDecimal valorconcepto1transast) {
		this.valorconcepto1transast = valorconcepto1transast;
	}

	public BigDecimal getValorconcepto2transast() {
		return valorconcepto2transast;
	}

	public void setValorconcepto2transast(BigDecimal valorconcepto2transast) {
		this.valorconcepto2transast = valorconcepto2transast;
	}

	public BigDecimal getValorconcepto3transast() {
		return valorconcepto3transast;
	}

	public void setValorconcepto3transast(BigDecimal valorconcepto3transast) {
		this.valorconcepto3transast = valorconcepto3transast;
	}

	public BigDecimal getValorconcepto4transast() {
		return valorconcepto4transast;
	}

	public void setValorconcepto4transast(BigDecimal valorconcepto4transast) {
		this.valorconcepto4transast = valorconcepto4transast;
	}
	
	

	public Integer getTotalPiezasAdicional() {
		return totalPiezasAdicional;
	}

	public void setTotalPiezasAdicional(Integer totalPiezasAdicional) {
		this.totalPiezasAdicional = totalPiezasAdicional;
	}
	
	

	public List<Producto> getProductos2filtrado() {
		return productos2filtrado;
	}

	public void setProductos2filtrado(List<Producto> productos2filtrado) {
		this.productos2filtrado = productos2filtrado;
	}
	
	
	
	public UtilsManagedBean getUtils() {
		return utils;
	}

	public void setUtils(UtilsManagedBean utils) {
		this.utils = utils;
	}

	public void imprimir(String tipo){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		Cliente cliente = clienteService.getClienteXId(idCliente2);
		Dominio paisdom = dominioService.getDominio(this.idPais);
		AgenciaCarga agencia  = dominioService.getAgenciaXId(this.idAgencia);
		Aerolinea aerolinea= dominioService.getAerolineaXId(this.idAerolinea);
		Handler handler = dominioService.getHandlerXId(this.idHandler);
		Truck truck = dominioService.getTruckXId(this.idTruck);
		
		
		Map parameters = new HashMap();
		parameters.put("BOXESID", this.boxesid.toUpperCase());
		parameters.put("FECHA", dateFormat.format(this.fechafactura));
		parameters.put("CUSTOMER", cliente.getNombre().toUpperCase());
		parameters.put("CLIENT", this.client.toUpperCase());
		parameters.put("INVOICE", this.numinvoice);
		parameters.put("PAIS", paisdom.getNomcorto().toUpperCase());
		
		parameters.put("TOTPIEZAS",this.totalPiezas);
		parameters.put("TOTPIEZASAD", this.totalPiezasAdicional);
		parameters.put("TOTBOXES", this.totalBoxes.doubleValue());
		parameters.put("TOTUNIUDADES", this.totalUnidades);
		parameters.put("TOTPRECIOFLOR", utils.format(this.preciototalflor.doubleValue()));
		parameters.put("TOTTRANS", utils.format(this.preciototaltransporte.doubleValue()));
		parameters.put("TOTAL", utils.format(this.preciototalcompleto.doubleValue()));
		parameters.put("AWB", this.awb);
		if(aerolinea != null)
		parameters.put("AIRLAINE", aerolinea.getNombre());
		if(agencia != null)
		parameters.put("FORWARDER", agencia.getNombre());
		if(handler != null)
			parameters.put("HANDLER", handler.getNombre());
		if(truck != null)
			parameters.put("TRUCK", truck.getNombre());
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
		parameters.put("COSTOS", this.getCostosAdicionales());
		parameters.put("DESCUENTOPRE", this.valorconceppre.doubleValue());
		parameters.put("PRECOOLING", utils.format(this.preciototalpreculinclie.doubleValue()));
		//parameters.put("PRECOOLING", "lamaaa");
		parameters.put("PEDIDO",this.idPedido);

		
		List <PedidoBean> lista = new ArrayList <PedidoBean>();
		String filejrxml = "invoice.jrxml";
		reportes.generarPDF(parameters,regenerarDetalles(detallesPedido) , filejrxml,tipo);
	}
	
	public List <PedidoBean> regenerarDetalles(List <PedidoBean> detalles){
		List <PedidoBean> lista = new ArrayList <PedidoBean> ();
		for(PedidoBean detalle : detalles){
			if (detalle.getTipocomposicion() != null){
				detalle.setUnidades(null);
				detalle.setPreciototal(null);
			}
			detalle.setPreciototal(utils.format(detalle.getPreciototal()));
			detalle.setPrecio(utils.format(detalle.getPrecio()));
			detalle.setComision(utils.format(detalle.getComision()));
			if(detalle.getPrecio()!= null && detalle.getComision()!= null){
				detalle.setPreciototaltalloclie(utils.format(utils.format(detalle.getPrecio())+ utils.format(detalle.getComision())));
			}
			lista.add(detalle);
			
		}
		
		return lista;
	}
	
	public List <CostosAdicionalesBean> getCostosAdicionales(){
		List <CostosAdicionalesBean> lista = new ArrayList <CostosAdicionalesBean> ();
		CostosAdicionalesBean costo ;
		if(this.concepto1trans!= null){
			if(!this.concepto1trans.equalsIgnoreCase(""))
			lista.add(new CostosAdicionalesBean (this.concepto1trans.toUpperCase(),this.valorconcepto1trans.doubleValue()));
	    }
		if(this.concepto1trans!= null){
			if(!this.concepto1trans.equalsIgnoreCase(""))
			lista.add(new CostosAdicionalesBean (this.concepto2trans.toUpperCase(),this.valorconcepto2trans.doubleValue()));
	    }
		if(this.concepto1trans!= null){
			if(!this.concepto1trans.equalsIgnoreCase(""))
			lista.add(new CostosAdicionalesBean (this.concepto3trans.toUpperCase(),this.valorconcepto3trans.doubleValue()));
		}
		if(this.concepto1trans!= null){
			if(!this.concepto1trans.equalsIgnoreCase(""))
			lista.add(new CostosAdicionalesBean (this.concepto4trans.toUpperCase(),this.valorconcepto4trans.doubleValue()));
        }
		if(this.concepadd!= null){
			if(!this.concepadd.equalsIgnoreCase(""))
			lista.add(new CostosAdicionalesBean (this.concepadd.toUpperCase(),this.valorconcepadd.doubleValue()));
	    }
		if(this.concepadd2!= null){
			if(!this.concepadd2.equalsIgnoreCase(""))
			lista.add(new CostosAdicionalesBean (this.concepadd2.toUpperCase(),this.valorconcepadd2.doubleValue()));
	    }
		if(this.concepadd3!= null){
			if(!this.concepadd3.equalsIgnoreCase(""))
			lista.add(new CostosAdicionalesBean (this.concepadd3.toUpperCase(),this.valorconcepadd3.doubleValue()));
		}
		
		return lista;
	}
	
public void getTrucksHandler(){
		
		Handler handleraux = new Handler();
		if(this.idHandler!= null){
		handleraux = dominioService.getHandlerXId(this.idHandler);
	    if (handleraux!= null){
	    	listaTrucks = new ArrayList<Truck>(handleraux.getTrucks());
	    	this.preculinast = new BigDecimal(handleraux.getPreculinast()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
	    	this.preculinclie = new BigDecimal(handleraux.getPreculincli()).setScale(3,BigDecimal.ROUND_HALF_EVEN);
	    	this.preciototalpreculinclie = this.preculinclie.multiply(this.pesoacli).setScale(3,BigDecimal.ROUND_HALF_EVEN);
	    	this.preciototalpreculinast = this.preculinast.multiply(this.pesoast).setScale(3,BigDecimal.ROUND_HALF_EVEN);
	    }else {
	    	listaTrucks = null;
	    	this.preculinast = new BigDecimal(0.0);
	    	this.preculinclie = new BigDecimal(0.0);
	    	this.preciototalpreculinclie = new BigDecimal(0.0);
	    	this.preciototalpreculinast = new BigDecimal(0.0);
	    }
	    listaTrucksItem = trucksToSelectdItems(listaTrucks);
		}
		
	}

public void getTrucksHandlerEditar(){
	
	Handler handleraux = new Handler();
	if(this.idHandler!= null){
	handleraux = dominioService.getHandlerXId(this.idHandler);
    if (handleraux!= null){
    	listaTrucks = new ArrayList<Truck>(handleraux.getTrucks());
    	
    }else {
    	listaTrucks = null;
    	
    }
    listaTrucksItem = trucksToSelectdItems(listaTrucks);
	}
	
}

//splitmet
   public void splitmet(String awb){
	   List <PedidoFacturado> pedidos = pedidoService.getPedidosXAWB(awb);
	   Cliente cliente;
	   List <Cliente> clientes = getClientesSplitmet(pedidos);
	   
	   for(Cliente cli : clientes){
		   List <DetallePedidoFacturado> detalles = new  ArrayList <DetallePedidoFacturado>();
		   for (PedidoFacturado pedfac:pedidos){
		       if (cli.getId() == pedfac.getId() ){
		    	   detalles.addAll(getdetallesSplitmet(pedfac.getId()));
		       } 	   
		   }
		   //luego ve plantaciones de esos detalles
		   List <Plantacion> plantaciones = getPlantacionesSplitmet(detalles);
		   for (Plantacion plantacion:plantaciones ){
			   List <ComposicionFacturado> composiciones = new  ArrayList <ComposicionFacturado>();
			   for (DetallePedidoFacturado detalle:detalles){
				   idPlantacion =  detalle.getIdplantacion();
				   if(idPlantacion == plantacion.getId()){
					  List <ComposicionFacturado> composicionesaux = pedidoService.getComposicionesFacturado(detalle.getId());
					  composiciones.addAll(composicionesaux);
				   }
			   }
			   //ya tengo las composiciones de la plantacion
			   
		   }
		   
	   }
   }
   
   public List <Cliente> getClientesSplitmet(List <PedidoFacturado> pedidos){
	   List <Cliente> clientes = new  ArrayList <Cliente> ();
	   HashMap<Integer, String>  map = new HashMap();
	   Integer idcliente= 0;
	   for (PedidoFacturado pedfac:pedidos){
		   idCliente =  pedfac.getIdcliente();
		   if(map.get(pedfac.getIdcliente()).isEmpty()){
			   map.put(idCliente, Integer.toString(idCliente));
			   clientes.add(clienteService.getClienteXId(pedfac.getIdcliente()));
		   }
	   }
	   return clientes;
   }
   
   public List <Plantacion> getPlantacionesSplitmet(List <DetallePedidoFacturado> detalles){
	   List <Plantacion> plantaciones = new   ArrayList <Plantacion>();
	   HashMap<Integer, String>  map = new HashMap();
	   Integer idPlantacion= 0;
	   for (DetallePedidoFacturado detalle:detalles){
		   idPlantacion =  detalle.getIdplantacion();
		   if(map.get(detalle.getIdplantacion()).isEmpty()){
			   map.put(idPlantacion, Integer.toString(idPlantacion));
			   plantaciones.add(plantacionService.getPlantacion(detalle.getId()));
		   }
	   }
	   return plantaciones;
   }
   
   public List <DetallePedidoFacturado> getdetallesSplitmet(Integer idPedido){
	   List <DetallePedidoFacturado> detallesPedido = pedidoService.getDetallesPedidoFacturado(idPedido);
	   return detallesPedido;
   }
   
   
   
  
	

	public BigDecimal getPreciototalflorast() {
		return preciototalflorast;
	}

	public void setPreciototalflorast(BigDecimal preciototalflorast) {
		this.preciototalflorast = preciototalflorast;
	}

	public ReportesManagedBean getReportes() {
		return reportes;
	}

	public void setReportes(ReportesManagedBean reportes) {
		this.reportes = reportes;
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

	public Integer getItem() {
		return item;
	}

	public void setItem(Integer item) {
		this.item = item;
	}
	

	public BigDecimal getTotalBoxesConciliado() {
		return totalBoxesConciliado;
	}

	public void setTotalBoxesConciliado(BigDecimal totalBoxesConciliado) {
		this.totalBoxesConciliado = totalBoxesConciliado;
	}
	
	
public Integer getIdTruck() {
		return idTruck;
	}

	public void setIdTruck(Integer idTruck) {
		this.idTruck = idTruck;
	}

	public Integer getIdHandler() {
		return idHandler;
	}

	public void setIdHandler(Integer idHandler) {
		this.idHandler = idHandler;
	}

	public List<Handler> getHandlers() {
		return handlers;
	}

	public void setHandlers(List<Handler> handlers) {
		this.handlers = handlers;
	}

	public List<Truck> getTrucks() {
		return trucks;
	}

	public void setTrucks(List<Truck> trucks) {
		this.trucks = trucks;
	}

	public List<SelectItem> getListaHandlersItem() {
		return listaHandlersItem;
	}

	public void setListaHandlersItem(List<SelectItem> listaHandlersItem) {
		this.listaHandlersItem = listaHandlersItem;
	}

	public List<SelectItem> getListaTrucksItem() {
		return listaTrucksItem;
	}

	public void setListaTrucksItem(List<SelectItem> listaTrucksItem) {
		this.listaTrucksItem = listaTrucksItem;
	}

public static List<SelectItem> trucksToSelectdItems(List <Truck> lista){
		
		List<SelectItem> generico = new ArrayList<SelectItem>();
		generico = new ArrayList<SelectItem>();
		if(lista!= null){
		for(Truck truck : lista){
		   //generico.add(new SelectItem(truck.getId(), truck.getCodigo() + "-" + truck.getNombre()));
			generico.add(new SelectItem(truck.getId(),  truck.getNombre() + "-" + truck.getCodigo()));
		}
		}
		return generico;
	}
	public static List<SelectItem> handlersToSelectdItems(List <Handler> lista){
		
		List<SelectItem> generico = new ArrayList<SelectItem>();
		generico = new ArrayList<SelectItem>();
		for(Handler handler : lista){
		   generico.add(new SelectItem(handler.getId(), handler.getCodigo() + "-" + handler.getNombre()));
		}
		return generico;
	}

	 
	public BigDecimal getValorconceppre() {
		return valorconceppre;
	}

	public void setValorconceppre(BigDecimal valorconceppre) {
		this.valorconceppre = valorconceppre;
	}
	
	

	public BigDecimal getPreculinclie() {
		return preculinclie;
	}

	public void setPreculinclie(BigDecimal preculinclie) {
		this.preculinclie = preculinclie;
	}

	public BigDecimal getPreculinast() {
		return preculinast;
	}

	public void setPreculinast(BigDecimal preculinast) {
		this.preculinast = preculinast;
	}

	public BigDecimal getPreciototalpreculinclie() {
		return preciototalpreculinclie;
	}

	public void setPreciototalpreculinclie(BigDecimal preciototalpreculinclie) {
		this.preciototalpreculinclie = preciototalpreculinclie;
	}

	public BigDecimal getPreciototalpreculinast() {
		return preciototalpreculinast;
	}

	public void setPreciototalpreculinast(BigDecimal preciototalpreculinast) {
		this.preciototalpreculinast = preciototalpreculinast;
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
		  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("invoicePedidoMB");
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

