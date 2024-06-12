package com.bulls.astoria.magedbean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import com.bulls.astoria.persistence.ComposicionConciliado;
import com.bulls.astoria.persistence.ComposicionFacturado;
import com.bulls.astoria.persistence.Destino;
import com.bulls.astoria.persistence.DetallePedido;
import com.bulls.astoria.persistence.DetallePedidoConciliado;
import com.bulls.astoria.persistence.DetallePedidoFacturado;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.Empresa;
import com.bulls.astoria.persistence.Handler;
import com.bulls.astoria.persistence.Pedido;
import com.bulls.astoria.persistence.PedidoConciliado;
import com.bulls.astoria.persistence.PedidoFacturado;
import com.bulls.astoria.persistence.Persona;
import com.bulls.astoria.persistence.Plantacion;
import com.bulls.astoria.persistence.PlantacionProducto;
import com.bulls.astoria.persistence.Truck;
import com.bulls.astoria.pojo.ConsolidadoVentasBean;
import com.bulls.astoria.pojo.EncabezadoPedidoBean;
import com.bulls.astoria.pojo.PedidoBean;
import com.bulls.astoria.pojo.PedidoPorDespacharBean;
import com.bulls.astoria.pojo.Producto;
import com.bulls.astoria.pojo.ProductoPrecio;
import com.bulls.astoria.pojo.ColumnasDespachoBean;
import com.bulls.astoria.service.ClienteService;
import com.bulls.astoria.service.DominioService;
import com.bulls.astoria.service.PedidoService;
import com.bulls.astoria.service.PersonaService;
import com.bulls.astoria.service.PlantacionService;
import com.bulls.astoria.utils.Convertidor;

@ManagedBean (name="consolidadoVentasMB")
@SessionScoped
public class ConsolidadoPedidoManagedBean  extends GeneralManagedBean implements Serializable{
	
	
	
	
	private String dlunes;
	private String dmartes;
	private String dmiercoles;
	private String djueves;
	private String dviernes;
	private String dsabado;
	private String ddomingo;
	
	
	private Date fechaDesde;
	private Date fechaHasta;
	private String tipo;
	
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
	
	private Character estado;
	
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
	List <SelectItem> productosPlantacionSelect;
	List<Cliente> clientes;
	List <SelectItem> clientesSelect;
	
	List <PedidoBean> detallesPedido;
	List <PedidoBean> detallesPedidoOriginal;
	List <PedidoBean> detallesTemporal;
	

	List <Dominio> colores;
	List <Dominio> grados;
	List <SelectItem>  gradosSelect;
	List <SelectItem> coloresSelect;
	
    List <ConsolidadoVentasBean> lista;
	
	
	//productos
	
	List <Map> productosMap;
	List <Producto> productos;
	List <Producto> productos2;
	List <Producto> productos2filtrado;
	
	List <PedidoPorDespacharBean> listapordespachar ;
	
	private Producto selectedProducto;
	
    String codigo; 
    String codigoaux;
    Integer cantidad;
    String nombreproducto;

    private Integer totalUnidades;
    private Double totalPrecio;
    private Integer totalUnidadesOriginal;
    private Double totalPrecioOriginal;
    private Double totalBoxesOriginal;
    private Double totalBoxes;
    
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
    private Integer item;
    
    String autorizo;
    
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
	
	@ManagedProperty(value="#{uMB}")
	private UtilsManagedBean utils;
	
	
	@PostConstruct
	public void ConsolidadoPedidoManagedBean(){
		borrarSession();
		
		bundle =  ResourceBundle.getBundle("messages");
        empresa= dominioService.getEmpresa();
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
		
		clientes = clienteService.getClientes();
		clientesSelect = Convertidor.clientesToSelectdItems(clientes);
		
		colores = dominioService.getDominios(EnuDominio.COLORES.getIdTipoDominio());
		grados =   dominioService.getDominios(EnuDominio.GRADOS.getIdTipoDominio());
		
		coloresSelect = Convertidor.dominiosToSelectdItems(colores);
		gradosSelect = Convertidor.dominiosToSelectdItems(grados);
		
		detallesPedido = new ArrayList<PedidoBean>();
		detallesPedidoOriginal = new ArrayList<PedidoBean>();
		
		//productos
		productosMap = dominioService.getProductosCompleto();
		detallesTemporal = new ArrayList<PedidoBean>();
	}
	
	
    public void getConsolidado(){
    	List <Map> listaPedidos = dominioService.getConsolidadoPedido(this.fechaDesde, this.fechaHasta, this.tipo);
    	
    	this.procesarLista(listaPedidos);
    }
    
    public void procesarLista(List <Map> listaPedidos){
    	List <ConsolidadoVentasBean> ventas = new ArrayList <ConsolidadoVentasBean>();
    	Calendar calendar = Calendar.getInstance();
    	calendar.setFirstDayOfWeek( Calendar.MONDAY);
    	calendar.setMinimalDaysInFirstWeek( 4 );
    	
    	  
    	for(Map map : listaPedidos){
    		int idpedido= ((Integer) map.get("idpedido"));
    		
    		if(this.tipo.equalsIgnoreCase("P")){
    			Pedido pedido = pedidoService.getPedidoXId(idpedido);
    			List <DetallePedido> detalles  = pedidoService.getDetallesPedido(idpedido);
    			for(DetallePedido detalle : detalles){
    				List <Composicion> composiciones = pedidoService.getComposiciones(detalle.getId());
    				int i =0;
    				for (Composicion composicion : composiciones){
    					//llenado del bean para la tabla
    					ConsolidadoVentasBean venta = new ConsolidadoVentasBean(); 
    					venta.setDiligenciador(pedido.getDiligenciador());
    					venta.setFechaDespacho(pedido.getFechadespacho());
    					venta.setIdpedido(pedido.getId());
    					venta.setEstado(getEstadoPedido(pedido.getEstado()));
    					venta.setPais(pedido.getIdpais());
    					venta.setNombrepais(dominioService.getDominio(pedido.getIdpais()).getNomcorto());
    					calendar.setTime(pedido.getFechadespacho());
    					venta.setSemana(calendar.get(Calendar.WEEK_OF_YEAR));
    					venta.setIdCliente(pedido.getIdcliente());
    					venta.setNombreCliente(clienteService.getClienteXId(pedido.getIdcliente()).getNombre());
    					venta.setCodigoCliente(dominioService.getEmpresa().getPrefijo()+clienteService.getClienteXId(pedido.getIdcliente()).getCodigo());

    					if(i == 0){
        					venta.setCajas(detalle.getCantidadfull());
            				venta.setPacking(detalle.getCantidadporfull());
            				venta.setTotalTallosCaja(detalle.getCantidadtotal());
            				venta.setComposicion(composiciones.size()>1 ? "Mixto":"Solido");

    					//datos propios de la factura
    					/*venta.setTotalFacturaClienteFlor(totalFacturaClienteFlor);
    					venta.setTotalFacturaPlantacionFlor(totalFacturaPlantacionFlor);
    					venta.setValorTransporteAstoria(valorTransporteAstoria);
    					venta.setValorTransporteCliente(valorTransporteCliente);
    					venta.setNumeroFacturaCliente(numeroFacturaCliente);
    					venta.setNumeroFacturaPlantacion(numeroFacturaPlantacion);
    					venta.setGananciaPorFactura(gananciaPorFactura);
    					venta.setGananciaPorTransporte(gananciaPorTransporte);
    					venta.setDescuentoPorPrepago(descuentoPorPrepago);*/
    					}
    					
   					    venta.setIdPlantacion(detalle.getIdplantacion());
    					venta.setNombrePlantacion(plantacionService.getPlantacion(detalle.getIdplantacion()).getNombre());
    					
    					com.bulls.astoria.persistence.Producto productop = dominioService.getProductoXId(composicion.getIdproducto());
                       	Dominio producto= dominioService.getDominio(productop.getIdvariedad());
                       	Dominio grado = dominioService.getDominio(productop.getIdgrado());
    					venta.setIdTipo(producto.getDominiopadre());
    					venta.setNombreTipo(dominioService.getDominio(producto.getDominiopadre()).getNomcorto());
    					venta.setIdVariedad(producto.getId());
    					venta.setNombreVariedad(producto.getNomcorto());
    					venta.setGrado(grado.getId());
    					venta.setNombreGrado(grado.getNomcorto());
    					
    					venta.setTotalTallosvariedad(composicion.getCantidadporfull());
    					venta.setValorCliente(utils.format((composicion.getPrecio() + composicion.getComision())));
    					venta.setValorPlantacion(utils.format(composicion.getPrecio()));
    					
    					venta.setTotalFacturaClienteFlor(utils.format((composicion.getPrecio() + composicion.getComision()) * composicion.getCantidadporfull()));
    					venta.setTotalFacturaPlantacionFlor(utils.format(composicion.getPrecio() * composicion.getCantidadporfull()));
    					venta.setIngresoPorFlor(utils.format(venta.getTotalFacturaClienteFlor() - venta.getTotalFacturaPlantacionFlor()));
     					ventas.add(venta);
    					i++;	
    				}
    			}	
    			
    		}
    		if(this.tipo.equalsIgnoreCase("C")){
    			PedidoConciliado pedido = pedidoService.getPedidoConciliadoXId(idpedido);
    			List <DetallePedidoConciliado> detalles  = pedidoService.getDetallesPedidoConciliado(idpedido);
    			for(DetallePedidoConciliado detalle : detalles){
    				List <ComposicionConciliado> composiciones = pedidoService.getComposicionesConciliado(detalle.getId());
    				int i =0;
    				for (ComposicionConciliado composicion : composiciones){
    					//llenado del bean para la tabla
    					ConsolidadoVentasBean venta = new ConsolidadoVentasBean(); 
    					venta.setDiligenciador(pedido.getDiligenciador());
    					venta.setFechaDespacho(pedido.getFechadespacho());
    					venta.setIdpedido(pedido.getId());
    					venta.setEstado(getEstadoPedido(pedido.getEstado()));
    					venta.setPais(pedido.getIdpais());
    					venta.setNombrepais(dominioService.getDominio(pedido.getIdpais()).getNomcorto());
    					calendar.setTime(pedido.getFechadespacho());
    					venta.setSemana(calendar.get(Calendar.WEEK_OF_YEAR));
    					venta.setIdCliente(pedido.getIdcliente());
    					venta.setNombreCliente(clienteService.getClienteXId(pedido.getIdcliente()).getNombre());
    					venta.setCodigoCliente(dominioService.getEmpresa().getPrefijo()+clienteService.getClienteXId(pedido.getIdcliente()).getCodigo());
    					
    					if(i == 0){
        					venta.setCajas(detalle.getCantidadfull());
            				venta.setPacking(detalle.getCantidadporfull());
            				venta.setTotalTallosCaja(detalle.getCantidadtotal());
            				venta.setComposicion(composiciones.size()>1 ? "Mixto":"Solido");

    					//datos propios de la factura
    					/*venta.setTotalFacturaClienteFlor(totalFacturaClienteFlor);
    					venta.setTotalFacturaPlantacionFlor(totalFacturaPlantacionFlor);
    					venta.setValorTransporteAstoria(valorTransporteAstoria);
    					venta.setValorTransporteCliente(valorTransporteCliente);
    					venta.setNumeroFacturaCliente(numeroFacturaCliente);
    					venta.setNumeroFacturaPlantacion(numeroFacturaPlantacion);
    					venta.setGananciaPorFactura(gananciaPorFactura);
    					venta.setGananciaPorTransporte(gananciaPorTransporte);
    					venta.setDescuentoPorPrepago(descuentoPorPrepago);*/
    					}
   					    venta.setIdPlantacion(detalle.getIdplantacion());
    					venta.setNombrePlantacion(plantacionService.getPlantacion(detalle.getIdplantacion()).getNombre());
    					
    					com.bulls.astoria.persistence.Producto productop = dominioService.getProductoXId(composicion.getIdproducto());
                       	Dominio producto= dominioService.getDominio(productop.getIdvariedad());
                       	Dominio grado = dominioService.getDominio(productop.getIdgrado());
    					venta.setIdTipo(producto.getDominiopadre());
    					venta.setNombreTipo(dominioService.getDominio(producto.getDominiopadre()).getNomcorto());
    					venta.setIdVariedad(producto.getId());
    					venta.setNombreVariedad(producto.getNomcorto());
    					venta.setGrado(grado.getId());
    					venta.setNombreGrado(grado.getNomcorto());
    					
    					venta.setTotalTallosvariedad(composicion.getCantidadporfull());
    					venta.setValorCliente(utils.format((composicion.getPrecio() + composicion.getComision())));
    					venta.setValorPlantacion(utils.format(composicion.getPrecio()));
    					
    					venta.setTotalFacturaClienteFlor(utils.format((composicion.getPrecio() + composicion.getComision()) * composicion.getCantidadporfull()));
    					venta.setTotalFacturaPlantacionFlor(utils.format(composicion.getPrecio() * composicion.getCantidadporfull()));
    					venta.setIngresoPorFlor(utils.format(venta.getTotalFacturaClienteFlor() - venta.getTotalFacturaPlantacionFlor()));
     					ventas.add(venta);
    					i++;	
    				}
    			}	
    		}
    		if(this.tipo.equalsIgnoreCase("F") || this.tipo.equalsIgnoreCase("E")){
    			PedidoFacturado pedido = pedidoService.getPedidoFacturadoXId(idpedido);
    			List <DetallePedidoFacturado> detalles  = pedidoService.getDetallesPedidoFacturado(idpedido);
    			for(DetallePedidoFacturado detalle : detalles){
    				List <ComposicionFacturado> composiciones = pedidoService.getComposicionesFacturado(detalle.getId());
    				int i = 0;
    				for (ComposicionFacturado composicion : composiciones){
    					
    					//llenado del bean para la tabla
    					ConsolidadoVentasBean venta = new ConsolidadoVentasBean(); 
    					venta.setDiligenciador(pedido.getDiligenciador());
    					//datos propios de la factura
    					if(i == 0){
    					venta.setCajas(detalle.getCantidadfull());
        				venta.setPacking(detalle.getCantidadporfull());
        				venta.setTotalTallosCaja(detalle.getCantidadtotal());
        				venta.setComposicion(composiciones.size()>1 ? "Mixto":"Solido");
    					venta.setAwb(pedido.getAwb());
    					venta.setNumeroFacturaCliente(pedido.getInvoice());
    					venta.setNumeroFacturaPlantacion(detalle.getInvoice());
    					venta.setValorTransporteAstoria(utils.format(getTotalTransPorteAstoria(pedido)));
    					venta.setValorTransporteCliente(utils.format(getTotalTransPorte(pedido)));
    					venta.setGananciaPorFactura(null);
    					venta.setGananciaPorTransporte(utils.format(venta.getValorTransporteCliente()-venta.getValorTransporteAstoria()));
    					venta.setDescuentoPorPrepago(utils.format(pedido.getValorconceppre()));
    					venta.setTotalFacturaCliente(utils.format(getTotalFlor(composiciones) +  getTotalTransPorte(pedido) + getTotalOtros(pedido)));
    					
    					
    					}
    					//propios del pedido
    					venta.setFechaDespacho(pedido.getFechadespacho());
    					venta.setIdpedido(pedido.getId());
    					venta.setEstado(getEstadoPedido(pedido.getEstado()));
    					venta.setPais(pedido.getIdpais());
    					venta.setNombrepais(dominioService.getDominio(pedido.getIdpais()).getNomcorto());
    					calendar.setTime(pedido.getFechadespacho());
    					venta.setSemana(calendar.get(Calendar.WEEK_OF_YEAR));
    					venta.setIdCliente(pedido.getIdcliente());
    					venta.setNombreCliente(clienteService.getClienteXId(pedido.getIdcliente()).getNombre());
    					venta.setCodigoCliente(dominioService.getEmpresa().getPrefijo()+clienteService.getClienteXId(pedido.getIdcliente()).getCodigo());
    					venta.setPesoAstoria(pedido.getPesoastoria());
    					venta.setPesoCliente(pedido.getPesocliente());
    					venta.setValorKgAstoria(pedido.getTarifaastoria());
    					venta.setValorKgCliente(pedido.getTarifacliente());

    					
    					//propios de la composicion
    					
    					
    					
    					
    					
   					    venta.setIdPlantacion(detalle.getIdplantacion());
    					venta.setNombrePlantacion(plantacionService.getPlantacion(detalle.getIdplantacion()).getNombre());
    					venta.setBodega(detalle.getBodega());
    					com.bulls.astoria.persistence.Producto productop = dominioService.getProductoXId(composicion.getIdproducto());
                       	Dominio producto= dominioService.getDominio(productop.getIdvariedad());
                       	Dominio grado = dominioService.getDominio(productop.getIdgrado());
    					venta.setIdTipo(producto.getDominiopadre());
    					venta.setNombreTipo(dominioService.getDominio(producto.getDominiopadre()).getNomcorto());
    					venta.setIdVariedad(producto.getId());
    					venta.setNombreVariedad(producto.getNomcorto());
    					venta.setGrado(grado.getId());
    					venta.setNombreGrado(grado.getNomcorto());
    					
    					venta.setTotalTallosvariedad(composicion.getCantidadporfull());
    					
    					venta.setValorCliente(utils.format((composicion.getPrecio() + composicion.getComision())));
    					venta.setValorPlantacion(utils.format(composicion.getPrecio()));
    					
    					venta.setTotalFacturaClienteFlor(utils.format((composicion.getPrecio() + composicion.getComision()) * composicion.getCantidadporfull()));
    					venta.setTotalFacturaPlantacionFlor(utils.format(composicion.getPrecio() * composicion.getCantidadporfull()));
    					venta.setIngresoPorFlor(utils.format(venta.getTotalFacturaClienteFlor() - venta.getTotalFacturaPlantacionFlor()));
     					ventas.add(venta);
    					i++;	
    				}
    			}
    		}
    	}
    	
    	lista = ventas;
        return;
    	
    }
    
    public Double getTotalFlor(List <ComposicionFacturado> composiciones){
    	Double total = 0.0;
    	for (ComposicionFacturado composicion : composiciones){
    		total = total + (composicion.getCantidadporfull() * (composicion.getComision() + composicion.getPrecio()));
    	}
    	return total;
    }
    
    public Double getTotalTransPorte(PedidoFacturado pedido){
    	Double total = 0.0;
    	total = total + (pedido.getPesocliente() * pedido.getTarifacliente());
    	Double valconceptotrans1 = 0.0;
    	Double valconceptotrans2 = 0.0;
    	Double valconceptotrans3 = 0.0;
    	Double valconceptotrans4 = 0.0;
    	
    	if (pedido.getValconceptotrans1() != null){
    		valconceptotrans1 = pedido.getValconceptotrans1();
    	}
    	if (pedido.getValconceptotrans2() != null){
    		valconceptotrans2 = pedido.getValconceptotrans2();
    	}
    	if (pedido.getValconceptotrans3() != null){
    		valconceptotrans3 = pedido.getValconceptotrans3();
    	}	
    	if (pedido.getValconceptotrans4() != null){
    		valconceptotrans4 = pedido.getValconceptotrans4();
    	}
    	//total = total + pedido.getValconceptotrans1() + pedido.getValconceptotrans2() + pedido.getValconceptotrans3() + pedido.getValconceptotrans4();
    	total = total + valconceptotrans1 + valconceptotrans2 + valconceptotrans3 + valconceptotrans4;

    	return total;
    }
    
    public Double getTotalTransPorteAstoria(PedidoFacturado pedido){
    	Double total = 0.0;
    	System.out.println("PEDIDO ******************  " + pedido.getId());
    	total = total + (pedido.getPesoastoria() * pedido.getTarifaastoria());
    	Double valconceptotransast1 = 0.0;
    	Double valconceptotransast2 = 0.0;
    	Double valconceptotransast3 = 0.0;
    	Double valconceptotransast4 = 0.0;
    	
    	if (pedido.getValconceptotransast1() != null){
    		valconceptotransast1 = pedido.getValconceptotransast1();
    	}
    	if (pedido.getValconceptotransast2() != null){
    		valconceptotransast2 = pedido.getValconceptotransast2();
    	}
    	if (pedido.getValconceptotransast3() != null){
    		valconceptotransast3 = pedido.getValconceptotransast3();
    	}	
    	if (pedido.getValconceptotransast4() != null){
    		valconceptotransast4 = pedido.getValconceptotransast4();
    	}
    	//total = total + pedido.getValconceptotransast1() + pedido.getValconceptotransast2() + pedido.getValconceptotransast3() + pedido.getValconceptotransast4();
    	total = total + valconceptotransast1 + valconceptotransast2 + valconceptotransast3 + valconceptotransast4;
    	
    	return total;
    }
    public Double getTotalOtros(PedidoFacturado pedido){
    	Double total = 0.0;
    	Double valconcepto = 0.0;
    	Double valconcepto1 = 0.0;
    	Double valconcepto2 = 0.0;
    	Double valconceptopre = 0.0;
    	if (pedido.getValorconcepto() != null){
    		valconcepto = pedido.getValorconcepto();
    	}
    	if (pedido.getValorconcepto1() != null){
    		valconcepto1 = pedido.getValorconcepto1();
    	}
    	if (pedido.getValorconcepto2() != null){
    		valconcepto2= pedido.getValorconcepto2();
    	}	
    	if (pedido.getValorconceppre() != null){
    		valconceptopre= pedido.getValorconceppre();
    	}
    	total = total + valconcepto + valconcepto1+ valconcepto2 + valconceptopre;
    	return total;
    }
    
    
    
public void getPedido(Integer id){
    	
    	Pedido pedido = pedidoService.getPedidoXId(id);
    	
    	this.idPedido=pedido.getId();
    	this.idCliente = pedido.getIdcliente();
    	this.idPais= pedido.getIdpais();
    	this.idPaisDestino= pedido.getIdpaisdestino();
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
    	
    	
    	getComposiciones(pedidoService.getDetallesPedido(id));
    	
    }

public void getComposiciones(List <DetallePedido> detalles){
    
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
               	pedidoaux.setNombretipo((dominioService.getDominio(dominioService.getDominio(producto.getId()).getDominiopadre())).getNomcorto());
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
      	
       }
		
  	return;
	
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
	
	public int generarAleatorio(){
		Random  rnd = new Random();
		return rnd.nextInt();
		
	}

	public Character getEstado() {
		return estado;
	}

	public void setEstado(Character estado) {
		this.estado = estado;
	}

	public List<PedidoBean> getDetallesPedidoOriginal() {
		return detallesPedidoOriginal;
	}

	public void setDetallesPedidoOriginal(List<PedidoBean> detallesPedidoOriginal) {
		this.detallesPedidoOriginal = detallesPedidoOriginal;
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
	
	public Integer getTotalUnidadesOriginal() {
		return totalUnidadesOriginal;
	}

	public void setTotalUnidadesOriginal(Integer totalUnidadesOriginal) {
		this.totalUnidadesOriginal = totalUnidadesOriginal;
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

	public Double getTotalPrecioOriginal() {
		return totalPrecioOriginal;
	}

	public void setTotalPrecioOriginal(Double totalPrecioOriginal) {
		this.totalPrecioOriginal = totalPrecioOriginal;
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
	
	

	public Double getTotalBoxesOriginal() {
		return totalBoxesOriginal;
	}

	public void setTotalBoxesOriginal(Double totalBoxesOriginal) {
		this.totalBoxesOriginal = totalBoxesOriginal;
	}

	public Double getTotalBoxes() {
		return totalBoxes;
	}

	public void setTotalBoxes(Double totalBoxes) {
		this.totalBoxes = totalBoxes;
	}
	
	
	
	public UtilsManagedBean getUtils() {
		return utils;
	}

	public void setUtils(UtilsManagedBean utils) {
		this.utils = utils;
	}

	public void imprimir(String tipo){
		
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
		parameters.put("TOTPRECIOFLOR", utils.format(this.totalPrecio));
		parameters.put("TOTTRANS", utils.format(this.preciototaltransporte));
		parameters.put("TOTAL", utils.format(this.preciototalcompleto));
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

	public Date getFechaDesde() {
	return fechaDesde;
}


public void setFechaDesde(Date fechaDesde) {
	this.fechaDesde = fechaDesde;
}


public Date getFechaHasta() {
	return fechaHasta;
}


public void setFechaHasta(Date fechaHasta) {
	this.fechaHasta = fechaHasta;
}


public String getTipo() {
	return tipo;
}


public void setTipo(String tipo) {
	this.tipo = tipo;
}


public List<ConsolidadoVentasBean> getLista() {
	return lista;
}


public void setLista(List<ConsolidadoVentasBean> lista) {
	this.lista = lista;
}

public void getListaPorDespachar(){
	GregorianCalendar cal = new GregorianCalendar();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Integer dia = null;
	listapordespachar = new ArrayList<PedidoPorDespacharBean>();
	List <Pedido> listaPedidos = pedidoService.getPedidosPorDespachar(this.fechaDesde, this.fechaHasta);
	
    Calendar calendarl = Calendar.getInstance();
    calendarl.setTime(this.fechaDesde); 
    calendarl.add(Calendar.DAY_OF_YEAR, 0);  
    Calendar calendarm = Calendar.getInstance();
    calendarm.setTime(this.fechaDesde); 
    calendarm.add(Calendar.DAY_OF_YEAR, 1);  
    Calendar calendarc = Calendar.getInstance();
    calendarc.setTime(this.fechaDesde); 
    calendarc.add(Calendar.DAY_OF_YEAR, 2);  
    Calendar calendarj = Calendar.getInstance();
    calendarj.setTime(this.fechaDesde); 
    calendarj.add(Calendar.DAY_OF_YEAR, 3);  
    Calendar calendarv = Calendar.getInstance();
    calendarv.setTime(this.fechaDesde); 
    calendarv.add(Calendar.DAY_OF_YEAR, 4);  
    Calendar calendars = Calendar.getInstance();
    calendars.setTime(this.fechaDesde); 
    calendars.add(Calendar.DAY_OF_YEAR, 5);  
    Calendar calendard = Calendar.getInstance();
    calendard.setTime(this.fechaDesde); 
    calendard.add(Calendar.DAY_OF_YEAR, 6);  
	
	if (!validarSemana())
		return;
	
	dlunes = dateFormat.format(calendarl.getTime());
	dmartes = dateFormat.format(calendarm.getTime());
	dmiercoles = dateFormat.format(calendarc.getTime());
	djueves = dateFormat.format(calendarj.getTime());
	dviernes = dateFormat.format(calendarv.getTime());
	dsabado = dateFormat.format(calendars.getTime());
	ddomingo = dateFormat.format(calendard.getTime());
	
	for(Cliente cliente :clientes){
		for(Pedido pedido :listaPedidos){
			if(pedido.getIdcliente().intValue() == cliente.getId().intValue()){
				PedidoPorDespacharBean despacho = new PedidoPorDespacharBean();
				String pais = this.getPais(pedido.getIdpais());
				String nombreCliente = cliente.getNombre();
				String codigoCliente = empresa.getPrefijo() + cliente.getCodigo();
				cal.setTime(pedido.getFechadespacho());
				dia = cal.get(Calendar.DAY_OF_WEEK);	
				
				despacho.setCliente(nombreCliente);
				despacho.setCodigo(codigoCliente);
				
				if(dia.intValue() == 2){
					if(pais.equalsIgnoreCase("C")){
						despacho.setLcol("X");
					}else if(pais.equalsIgnoreCase("E")){
						despacho.setLecu("X");
					}else if(pais.equalsIgnoreCase("K")){
						despacho.setLkenya("X");
					}
				}else if(dia.intValue() == 3){
					if(pais.equalsIgnoreCase("C")){
						despacho.setMcol("X");
					}else if(pais.equalsIgnoreCase("E")){
						despacho.setMecu("X");
					}else if(pais.equalsIgnoreCase("K")){
						despacho.setMkenya("X");
					}
				}else if(dia.intValue() == 4){
					if(pais.equalsIgnoreCase("C")){
						despacho.setCcol("X");
					}else if(pais.equalsIgnoreCase("E")){
						despacho.setCecu("X");
					}else if(pais.equalsIgnoreCase("K")){
						despacho.setCkenya("X");
					}
				}else if(dia.intValue() == 5){
					if(pais.equalsIgnoreCase("C")){
						despacho.setJcol("X");
					}else if(pais.equalsIgnoreCase("E")){
						despacho.setJecu("X");
					}else if(pais.equalsIgnoreCase("K")){
						despacho.setJkenya("X");
					}
				}else if(dia.intValue() == 6){
					if(pais.equalsIgnoreCase("C")){
						despacho.setVcol("X");
					}else if(pais.equalsIgnoreCase("E")){
						despacho.setVecu("X");
					}else if(pais.equalsIgnoreCase("K")){
						despacho.setVkenya("X");
					}
				}else if(dia.intValue() == 7){
					if(pais.equalsIgnoreCase("C")){
						despacho.setScol("X");
					}else if(pais.equalsIgnoreCase("E")){
						despacho.setSecu("X");
					}else if(pais.equalsIgnoreCase("K")){
						despacho.setSkenya("X");
					}
				}else if(dia.intValue() == 1){
					if(pais.equalsIgnoreCase("C")){
						despacho.setDcol("X");
					}else if(pais.equalsIgnoreCase("E")){
						despacho.setDecu("X");
					}else if(pais.equalsIgnoreCase("K")){
						despacho.setDkenya("X");
					}
				}
				listapordespachar.add(despacho);
				
			}
		}

	}
	// HCER LA LOGICA DE ESTABLECER LA FECHA Y EL SETEO DE LA LISTA DE SALIDA  listapordespachar 
	//HACER UN LOOP SOBRE CLIENTES , PREGUNTAR SI TIENEN PEDIDOS EN ESAS FECHAS 
	//PARA CADA UNO DE LOS PEDIDOS RESULTANTES VER LA FECHA Y VER SI ES LUNES MARTES O MIC ETC
	//LUEGO VER LA PLANTACION PARA VER PAIS O MEJOR Y LISTO GRACIAS
}

public boolean validarSemana(){
	boolean ok = true;
	final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día
	GregorianCalendar cal = new GregorianCalendar();
	GregorianCalendar cal2 = new GregorianCalendar();
	cal.setTime(this.fechaDesde);
	cal2.setTime(this.fechaHasta);
	int dia = cal.get(Calendar.DAY_OF_WEEK);
	int dia2 = cal2.get(Calendar.DAY_OF_WEEK);
	
	long diferencia = ( this.fechaHasta.getTime() - this.fechaDesde.getTime() )/MILLSECS_PER_DAY; 
	if(this.fechaDesde.compareTo(this.fechaHasta) > 0){
		//meensaje de rango fechas maluco
		 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errorrangofechas"));
		 //RequestContext.getCurrentInstance().showMessageInDialog(message);
		 PrimeFaces.current().dialog().showMessageDynamic(message);
		ok = false;
		return ok;
	}else if(dia != 2 || dia2 != 1){
		//mensaje no es semana
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errorsemana"));
		// RequestContext.getCurrentInstance().showMessageInDialog(message);
		PrimeFaces.current().dialog().showMessageDynamic(message);
		ok = false;
		return ok;
	}else if(diferencia > 6){
		//mensaje no es semana
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("tituloerror"), bundle.getString("errorsemana"));
		 //RequestContext.getCurrentInstance().showMessageInDialog(message);
		PrimeFaces.current().dialog().showMessageDynamic(message);
		ok = false;
		return ok;
	}
	return ok;
}

public String getPais(Integer idPais){
	String codigo = null;
	for (Dominio pais :paises){
		if(pais.getId().intValue()== idPais.intValue()){
			codigo = pais.getCodigo();
			break;
		}
	}
	return codigo;
}

String getEstadoPedido(Character estado){
	
	String estadoPedido ="";
	if(estado.charValue()=='C'){
		estadoPedido = "CONCILIADO";
	}else if (estado.charValue()=='F'){
		estadoPedido = "EN FACTURACION";
	}else if (estado.charValue()=='E'){
		estadoPedido = "ENTREGADO";
	}else if (estado.charValue()=='X'){
		estadoPedido = "CANCELADO";
	}else if (estado.charValue()=='R'){
		estadoPedido = "REGISTRADO";
	}else if (estado.charValue()=='D'){
		estadoPedido = "DESPACHADO";
	}
	return estadoPedido;
}


public List<PedidoPorDespacharBean> getListapordespachar() {
	return listapordespachar;
}


public void setListapordespachar(List<PedidoPorDespacharBean> listapordespachar) {
	this.listapordespachar = listapordespachar;
}




public String getDlunes() {
	return dlunes;
}


public void setDlunes(String dlunes) {
	this.dlunes = dlunes;
}


public String getDmartes() {
	return dmartes;
}


public void setDmartes(String dmartes) {
	this.dmartes = dmartes;
}


public String getDmiercoles() {
	return dmiercoles;
}


public void setDmiercoles(String dmiercoles) {
	this.dmiercoles = dmiercoles;
}


public String getDjueves() {
	return djueves;
}


public void setDjueves(String djueves) {
	this.djueves = djueves;
}


public String getDviernes() {
	return dviernes;
}


public void setDviernes(String dviernes) {
	this.dviernes = dviernes;
}


public String getDsabado() {
	return dsabado;
}


public void setDsabado(String dsabado) {
	this.dsabado = dsabado;
}


public String getDdomingo() {
	return ddomingo;
}


public void setDdomingo(String ddomingo) {
	this.ddomingo = ddomingo;
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
	  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("consolidadoVentasMB");
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

