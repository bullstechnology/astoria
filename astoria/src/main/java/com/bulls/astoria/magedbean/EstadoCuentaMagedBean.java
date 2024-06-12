package com.bulls.astoria.magedbean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

//import org.primefaces.context.RequestContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bulls.astoria.enumeracion.EnuDominio;
import com.bulls.astoria.persistence.Aerolinea;
import com.bulls.astoria.persistence.AgenciaCarga;
import com.bulls.astoria.persistence.Cliente;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.Handler;
import com.bulls.astoria.persistence.Nota;
import com.bulls.astoria.persistence.Persona;
import com.bulls.astoria.persistence.Plantacion;
import com.bulls.astoria.persistence.Proveedor;
import com.bulls.astoria.persistence.Role;
import com.bulls.astoria.persistence.Truck;
import com.bulls.astoria.persistence.User;
import com.bulls.astoria.pojo.EstadoCuentaBean;
import com.bulls.astoria.pojo.NotaBean;
import com.bulls.astoria.pojo.PedidoBean;
import com.bulls.astoria.service.ClienteService;
import com.bulls.astoria.service.DominioService;
import com.bulls.astoria.service.PedidoService;
import com.bulls.astoria.service.PersonaService;
import com.bulls.astoria.service.PlantacionService;
import com.bulls.astoria.service.RoleService;
import com.bulls.astoria.service.UserService;
import com.bulls.astoria.utils.Convertidor;


@ManagedBean (name="estadoCuentaMB")
@SessionScoped
public class EstadoCuentaMagedBean extends GeneralManagedBean implements Serializable{
	
	
	private Double totalCredito;
	private Double totalDebito;
	private Integer idCliente;
	private Integer idPlantacion;
	private Integer idProveedor;
	private Integer idDebitoCredito;
	private Integer idAgencia;
	private Integer idHandler;
	private Integer idEstado;
	private Date fecha = new Date();
	private List <NotaBean> listanotas;
	private BigDecimal valor;
	private String descripcion;
	private Integer idConcepto;
	private String factura;
	private String awb;
	private String username;
	private Integer periodo;
	private Date fechaInicial;
	private Date fechaFinal;
	private BigDecimal saldocanje;
	private BigDecimal saldoactual;
	private BigDecimal saldoinicial = new BigDecimal(0.0);
	private String tipotitular;
	private String nomtitular;
	
	private NotaBean notabeaneditar;
	private String carpetajrxml;
	ResourceBundle bundleparam ;

	ResourceBundle bundle ;
	
	
	List <Plantacion>  listaPlantaciones;
	List <SelectItem> listaPlantacionesSelect;
	
	List<Cliente> clientes;
	List <SelectItem> clientesSelect;
	
	List<Dominio> conceptos;
	List <SelectItem> listaConceptosSelect;
	
	List<Dominio> estados;
	List <SelectItem> listaEstadosSelect;
	
	List <SelectItem> listaAgenciasSelect;
	
	
	List <Proveedor> proveedores;
	List <Handler> handlers;

	@ManagedProperty(value="#{ClienteService}")
    ClienteService clienteService;
	
	@ManagedProperty(value="#{PlantacionService}")
	PlantacionService plantacionService;
	
	@ManagedProperty(value="#{PedidoService}")
	PedidoService pedidoService;
	
	@ManagedProperty(value="#{PersonaService}")
    PersonaService personaService;
	
	@ManagedProperty(value="#{UserService}")
	UserService userService;
	
	@ManagedProperty(value="#{DominioService}")
	DominioService dominioService;
	
	@ManagedProperty(value="#{reportesMB}")
	private ReportesManagedBean reportes;
	
	
	private List <EstadoCuentaBean> lista;
	private List <EstadoCuentaBean> listaPendiente;
	
	Cliente cliente = null; 
	Plantacion plantacion = null;
	AgenciaCarga agencia = null;
	Handler handler = null;
	
	@PostConstruct
	public void EstadoCuentaMagedBean(){
		
		borrarSession();
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
        bundle =  ResourceBundle.getBundle("messages");
        lista = new ArrayList <EstadoCuentaBean> ();
		
		listaPlantaciones = plantacionService.getPlantaciones();
		listaPlantacionesSelect = Convertidor.plantacionToSelectdItems(listaPlantaciones);	
		
		clientes = clienteService.getClientes();
		clientesSelect = Convertidor.clientesToSelectdItems(clientes);
		
		conceptos = dominioService.getDominios(EnuDominio.CONCEPTOSNOTAS.getIdTipoDominio());
		listaConceptosSelect = Convertidor.dominiosToSelectdItems(conceptos);
		
		estados = dominioService.getDominios(EnuDominio.ESTADOSNOTAS.getIdTipoDominio());
		listaEstadosSelect = Convertidor.dominiosToSelectdItems(estados);
		
		listaAgenciasSelect = Convertidor.agenciasToSelectdItems(dominioService.getAgenciasCarga());
		personaService = getPersonaService();
		dominioService = getDominioService();
		proveedores = plantacionService.getProveedores();
		handlers = dominioService.getHandlers();
		Persona per = personaService.getPersonaXusername(username);
		bundleparam =  ResourceBundle.getBundle("parametros");
		carpetajrxml = bundleparam.getString("rutajrxml");
		
		
		//calcular periodo
		this.periodo=1; // mensual
		this.calculoPeriodo();
		
		if(per.isCliente()){
			getLista(dominioService.getEstadoDeCuentaGeneral(per.getIdCliente(), "C",this.fechaInicial,this.fechaFinal));
			getListaPendiente(dominioService.getEstadoDeCuentaGeneralPendiente(per.getIdCliente(), "C"));
			cliente = clienteService.getClienteXId(per.getIdCliente());
			nomtitular = cliente.getCodigo() + " " + cliente.getNombre();
			tipotitular = "Cliente";
			saldoactual = new BigDecimal(getSaldo(per.getIdCliente(), "C","A")* -1);
			saldocanje = new BigDecimal(getSaldo(per.getIdCliente(), "C","P")* -1);
		}else if(per.isPlantacion()){
			plantacion = plantacionService.getPlantacion(per.getIdPlantacion());
			getLista(dominioService.getEstadoDeCuentaGeneral(plantacion.getIdproveedor(), "P",this.fechaInicial,this.fechaFinal));
			getListaPendiente(dominioService.getEstadoDeCuentaGeneralPendiente(per.getIdPlantacion(), "P"));
			nomtitular = plantacionService.getProveedor(plantacion.getIdproveedor()).getNombre();
			tipotitular = "Proveedor";
			saldoactual = new BigDecimal(getSaldo(plantacion.getIdproveedor(), "P","A")* -1);
			saldocanje = new BigDecimal(getSaldo(plantacion.getIdproveedor(), "P","P")* -1);
		}else if(per.isAgencia()){
			getLista(dominioService.getEstadoDeCuentaGeneral(per.getIdAgencia(), "A",this.fechaInicial,this.fechaFinal));
			getListaPendiente(dominioService.getEstadoDeCuentaGeneralPendiente(per.getIdAgencia(), "A"));
			agencia = dominioService.getAgenciaXId(per.getIdAgencia());
			nomtitular = agencia.getNombre();
			tipotitular = "Agencia de Carga";
			saldoactual = new BigDecimal(getSaldo(per.getIdAgencia(), "A","A")* -1);
			saldocanje = new BigDecimal(getSaldo(per.getIdAgencia(), "A","P")* -1);
		}
		
	    this.fechaInicial=null;
	    this.fechaFinal=null;

				
    }
	
	public void getLista(List <Nota> listaNotas){
        lista = new ArrayList <EstadoCuentaBean> ();
        List <EstadoCuentaBean> listaaux = new ArrayList <EstadoCuentaBean> ();
		Double saldo = 0.0; 
		saldo =  saldoinicial.doubleValue();
		for(Nota nota : listaNotas){
			EstadoCuentaBean estado = new EstadoCuentaBean();
			saldo = saldo + nota.getValor() * (nota.getIdDebitoCredito() * -1);
			estado.setId(nota.getId());
			estado.setSaldo(saldo);
			estado.setConcepto(nota.getConcepto());
			estado.setFecha(nota.getFecha());
			estado.setAwb(nota.getAwb());
			estado.setFactura(nota.getFactura());
			estado.setInv(nota.getInv());
			estado.setCliente(nota.getCliente());
			if(nota.getIdDebitoCredito() == -1){
				estado.setCredito(nota.getValor());
			}else{
				estado.setDebito(nota.getValor());
			}
			
			listaaux.add(estado);

		}
		
		lista = listaaux;
		
		/* no la quieren desde el ultimo dia para atras sino al revesint i = listaaux.size();
		 
		while(i >0){
			lista.add(listaaux.get(i-1));
			i--;
		}*/
		
		
		//Collections.sort(lista, Collections.reverseOrder());
	}
	
	public void getListaPendiente(List <Nota> listaNotas){
		listaPendiente = new ArrayList <EstadoCuentaBean> ();
        List <EstadoCuentaBean> listaaux = new ArrayList <EstadoCuentaBean> ();
		Double saldo = 0.0; 
		for(Nota nota : listaNotas){
			EstadoCuentaBean estado = new EstadoCuentaBean();
			saldo = saldo + nota.getValor() * (nota.getIdDebitoCredito() * -1);
			estado.setId(nota.getId());
			estado.setSaldo(saldo);
			estado.setConcepto(nota.getConcepto());
			estado.setFecha(nota.getFecha());
			estado.setAwb(nota.getAwb());
			estado.setFactura(nota.getFactura());
			estado.setInv(nota.getInv());
			estado.setCliente(nota.getCliente());
			if(nota.getIdDebitoCredito() == -1){
				estado.setCredito(nota.getValor());
			}else{
				estado.setDebito(nota.getValor());
			}
			
			listaaux.add(estado);

		}
		
		listaPendiente = listaaux;
		
		/* no la quieren desde el ultimo dia para atras sino al revesint i = listaaux.size();
		 
		while(i >0){
			lista.add(listaaux.get(i-1));
			i--;
		}*/
		
		
		//Collections.sort(lista, Collections.reverseOrder());
	}
	
	public void buscar(){

		    this.calculoPeriodo();
			if(this.idCliente != null){
				saldoinicial =  (new BigDecimal(getSaldoInicial(this.idCliente, "C","A",this.fechaInicial) * -1)).setScale(3, BigDecimal.ROUND_CEILING);
				getLista(dominioService.getEstadoDeCuentaGeneral(this.idCliente, "C",this.fechaInicial,this.fechaFinal));
				getListaPendiente(dominioService.getEstadoDeCuentaGeneralPendiente(this.idCliente, "C"));
				cliente = clienteService.getClienteXId(this.idCliente);
				nomtitular = cliente.getCodigo() + " " + cliente.getNombre();
				tipotitular = "Cliente";
				saldoactual = new BigDecimal(getSaldo(this.idCliente, "C","A") * -1);
				saldocanje = new BigDecimal(getSaldo(this.idCliente, "C","P") * -1);
				
			}else if(this.idPlantacion != null){
				saldoinicial =  (new BigDecimal(getSaldoInicial(this.idPlantacion, "P","A",this.fechaInicial) * -1)).setScale(3, BigDecimal.ROUND_CEILING);
				getLista(dominioService.getEstadoDeCuentaGeneral(this.idPlantacion, "P",this.fechaInicial,this.fechaFinal));
				getListaPendiente(dominioService.getEstadoDeCuentaGeneralPendiente(this.idPlantacion, "P"));
				//plantacion = plantacionService.getProveedor(this.idPlantacion).getNombre();
				nomtitular = plantacionService.getProveedor(this.idPlantacion).getNombre();
				tipotitular = "Proveedor";
				saldoactual = new BigDecimal(getSaldo(this.idPlantacion, "P","A") * -1);
				saldocanje = new BigDecimal(getSaldo(this.idPlantacion, "P","P")* -1);
				
			}else if(this.idProveedor != null){
				saldoinicial =  (new BigDecimal(getSaldoInicial(this.idProveedor, "Q","A",this.fechaInicial) * -1)).setScale(3, BigDecimal.ROUND_CEILING);
				getLista(dominioService.getEstadoDeCuentaGeneral(this.idProveedor, "Q",this.fechaInicial,this.fechaFinal));
				getListaPendiente(dominioService.getEstadoDeCuentaGeneralPendiente(this.idProveedor, "Q"));
				//plantacion = plantacionService.getProveedor(this.idPlantacion).getNombre();
				nomtitular = plantacionService.getProveedor(this.idProveedor).getNombre();
				tipotitular = "Proveedor";
				saldoactual = new BigDecimal(getSaldo(this.idProveedor, "Q","A") * -1);
				saldocanje = new BigDecimal(getSaldo(this.idProveedor, "Q","P")* -1);
				
			}else if(this.idAgencia != null){
				saldoinicial =  (new BigDecimal(getSaldoInicial(this.idAgencia, "A","A",this.fechaInicial) * -1)).setScale(3, BigDecimal.ROUND_CEILING);
				getLista(dominioService.getEstadoDeCuentaGeneral(this.idAgencia, "A",this.fechaInicial,this.fechaFinal));
				getListaPendiente(dominioService.getEstadoDeCuentaGeneralPendiente(this.idAgencia, "A"));
				agencia = dominioService.getAgenciaXId(this.idAgencia);
				nomtitular = agencia.getNombre();
				tipotitular = "Agencia de Carga";
				saldoactual = new BigDecimal(getSaldo(this.idAgencia, "A","A")* -1);
				saldocanje = new BigDecimal(getSaldo(this.idAgencia, "A","P")* -1);
				
			}else if(this.idHandler != null){
				saldoinicial =  (new BigDecimal(getSaldoInicial(this.idHandler, "H","A",this.fechaInicial) * -1)).setScale(3, BigDecimal.ROUND_CEILING);
				getLista(dominioService.getEstadoDeCuentaGeneral(this.idHandler, "H",this.fechaInicial,this.fechaFinal));
				getListaPendiente(dominioService.getEstadoDeCuentaGeneralPendiente(this.idHandler, "H"));
				handler = dominioService.getHandlerXId(this.idHandler);
				nomtitular = handler.getNombre();
				tipotitular = "Handler";
				saldoactual = new BigDecimal(getSaldo(this.idHandler, "H","A")* -1);
				
			}
			
			
	}
	
public void buscarExternos (){
	String username = SecurityContextHolder.getContext().getAuthentication().getName();
	Persona per = personaService.getPersonaXusername(username);
	this.calculoPeriodo();
	if(per.isCliente()){
		getLista(dominioService.getEstadoDeCuentaGeneral(per.getIdCliente(), "C",this.fechaInicial,this.fechaFinal));
		getListaPendiente(dominioService.getEstadoDeCuentaGeneralPendiente(per.getIdCliente(), "C"));
		cliente = clienteService.getClienteXId(per.getIdCliente());
		nomtitular = cliente.getCodigo() + " " + cliente.getNombre();
		tipotitular = "Cliente";
		saldoactual = new BigDecimal(getSaldo(per.getIdCliente(), "C","A")* -1);
		saldocanje = new BigDecimal(getSaldo(per.getIdCliente(), "C","P")* -1);
	}else if(per.isPlantacion()){
		plantacion = plantacionService.getPlantacion(per.getIdPlantacion());
		getLista(dominioService.getEstadoDeCuentaGeneral(plantacion.getIdproveedor(), "P",this.fechaInicial,this.fechaFinal));
		getListaPendiente(dominioService.getEstadoDeCuentaGeneralPendiente(per.getIdPlantacion(), "P"));
		nomtitular = plantacionService.getProveedor(plantacion.getIdproveedor()).getNombre();
		tipotitular = "Proveedor";
		saldoactual = new BigDecimal(getSaldo(plantacion.getIdproveedor(), "P","A")* -1);
		saldocanje = new BigDecimal(getSaldo(plantacion.getIdproveedor(), "P","P")* -1);
	}else if(per.isAgencia()){
		getLista(dominioService.getEstadoDeCuentaGeneral(per.getIdAgencia(), "A",this.fechaInicial,this.fechaFinal));
		getListaPendiente(dominioService.getEstadoDeCuentaGeneralPendiente(per.getIdAgencia(), "A"));
		agencia = dominioService.getAgenciaXId(per.getIdAgencia());
		nomtitular = agencia.getNombre();
		tipotitular = "Agencia de Carga";
		saldoactual = new BigDecimal(getSaldo(per.getIdAgencia(), "A","A")* -1);
		saldocanje = new BigDecimal(getSaldo(per.getIdAgencia(), "A","P")* -1);
	}else if(per.isHandler()){
		getLista(dominioService.getEstadoDeCuentaGeneral(per.getIdHandler(), "H",this.fechaInicial,this.fechaFinal));
		getListaPendiente(dominioService.getEstadoDeCuentaGeneralPendiente(per.getIdHandler(), "H"));
		handler = dominioService.getHandlerXId(per.getIdHandler());
		nomtitular = handler.getNombre();
		tipotitular = "Handler";
		saldoactual = new BigDecimal(getSaldo(per.getIdHandler(), "H","A")* -1);
		saldocanje = new BigDecimal(getSaldo(per.getIdHandler(), "H","P")* -1);
	}
}
	
public void calculoPeriodo(){
        
	    Calendar calInicial = Calendar.getInstance();
	    Calendar calFinal = Calendar.getInstance();
	    calFinal.set(Calendar.HOUR,23);
	    calFinal.set(Calendar.MINUTE,59);
	    calFinal.set(Calendar.SECOND,59);
		if(this.periodo.intValue()==1){
			calInicial.add(Calendar.MONTH,-1);
		    calInicial.set(Calendar.HOUR,0);
		    calInicial.set(Calendar.MINUTE,0);
		    calInicial.set(Calendar.SECOND,0);
			this.fechaInicial = calInicial.getTime();
			this.fechaFinal = calFinal.getTime();
		}else if(this.periodo.intValue()==2){
			calInicial.add(Calendar.MONTH,-3);
		    calInicial.set(Calendar.HOUR,0);
		    calInicial.set(Calendar.MINUTE,0);
		    calInicial.set(Calendar.SECOND,0);
			this.fechaInicial = calInicial.getTime();
			this.fechaFinal = calFinal.getTime();
		}else if(this.periodo.intValue()==3){
			calInicial.add(Calendar.MONTH,-6);
		    calInicial.set(Calendar.HOUR,0);
		    calInicial.set(Calendar.MINUTE,0);
		    calInicial.set(Calendar.SECOND,0);
			this.fechaInicial = calInicial.getTime();
			this.fechaFinal = calFinal.getTime();
		}else if(this.periodo.intValue()==4){
			calInicial.add(Calendar.MONTH,-12);
		    calInicial.set(Calendar.HOUR,0);
		    calInicial.set(Calendar.MINUTE,0);
		    calInicial.set(Calendar.SECOND,0);
			this.fechaInicial = calInicial.getTime();
			this.fechaFinal = calFinal.getTime();
		}else if(this.periodo.intValue()==5){
			calInicial.add(Calendar.MONTH,-60);
		    calInicial.set(Calendar.HOUR,0);
		    calInicial.set(Calendar.MINUTE,0);
		    calInicial.set(Calendar.SECOND,0);
			this.fechaInicial = calInicial.getTime();
			this.fechaFinal = calFinal.getTime();
		}else if(this.periodo.intValue()==-1){
			Calendar cal3 = Calendar.getInstance();
			cal3.setTime(this.fechaFinal);
			cal3.set(Calendar.HOUR,23);
			cal3.set(Calendar.MINUTE,59);
			cal3.set(Calendar.SECOND,59);
			this.fechaFinal = cal3.getTime();
		}
		
	}

	private Double getSaldo(Integer idEntidad,String entidad,String tipoSaldo){
		Double saldo = 0.0;
		saldo = dominioService.getSaldo(idEntidad, entidad,tipoSaldo);
		if(saldo==null){
			saldo = 0.0;
		}
		return saldo;
	}
	
	private Double getSaldoInicial(Integer idEntidad,String entidad,String tipoSaldo,Date fecha){
		Double saldo = 0.0;
		saldo = dominioService.getSaldoInicial(idEntidad, entidad,tipoSaldo,fecha);
		if(saldo==null){
			saldo = 0.0;
		}
		return saldo;
	}

		
	public void resetcliente(){
		
		this.idPlantacion =null;
		this.idAgencia = null;
		this.idHandler =null;
    }
	
   public void resetplantacion(){
	   this.idCliente = null;
	   this.idAgencia = null;
	   this.idHandler =null;
   }
   
   public void resetcarguera(){
	   this.idCliente = null;
	   this.idPlantacion = null;		
	   this.idHandler =null;
   }
   
   public void resethandler (){
	   this.idCliente = null;
	   this.idPlantacion = null;		
	   this.idAgencia = null;
   }

	public PersonaService getPersonaService() {
		return personaService;
	}

	public void setPersonaService(PersonaService personaService) {
		this.personaService = personaService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	
	
	public List<EstadoCuentaBean> getLista() {
		return lista;
	}

	public void setLista(List<EstadoCuentaBean> lista) {
		this.lista = lista;
	}

	public DominioService getDominioService() {
		return dominioService;
	}

	public void setDominioService(DominioService dominioService) {
		this.dominioService = dominioService;
	}
	
	
	
	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public Integer getIdPlantacion() {
		return idPlantacion;
	}

	public void setIdPlantacion(Integer idPlantacion) {
		this.idPlantacion = idPlantacion;
	}

	public Integer getIdDebitoCredito() {
		return idDebitoCredito;
	}

	public void setIdDebitoCredito(Integer idDebitoCredito) {
		this.idDebitoCredito = idDebitoCredito;
	}

	public Integer getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(Integer idAgencia) {
		this.idAgencia = idAgencia;
	}

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public List<NotaBean> getListanotas() {
		return listanotas;
	}

	public void setListanotas(List<NotaBean> listanotas) {
		this.listanotas = listanotas;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getIdConcepto() {
		return idConcepto;
	}

	public void setIdConcepto(Integer idConcepto) {
		this.idConcepto = idConcepto;
	}

	public String getFactura() {
		return factura;
	}

	public void setFactura(String factura) {
		this.factura = factura;
	}

	public String getAwb() {
		return awb;
	}

	public void setAwb(String awb) {
		this.awb = awb;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public NotaBean getNotabeaneditar() {
		return notabeaneditar;
	}

	public void setNotabeaneditar(NotaBean notabeaneditar) {
		this.notabeaneditar = notabeaneditar;
	}

	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
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

	public List<Dominio> getConceptos() {
		return conceptos;
	}

	public void setConceptos(List<Dominio> conceptos) {
		this.conceptos = conceptos;
	}

	public List<SelectItem> getListaConceptosSelect() {
		return listaConceptosSelect;
	}

	public void setListaConceptosSelect(List<SelectItem> listaConceptosSelect) {
		this.listaConceptosSelect = listaConceptosSelect;
	}

	public List<Dominio> getEstados() {
		return estados;
	}

	public void setEstados(List<Dominio> estados) {
		this.estados = estados;
	}

	public List<SelectItem> getListaEstadosSelect() {
		return listaEstadosSelect;
	}

	public void setListaEstadosSelect(List<SelectItem> listaEstadosSelect) {
		this.listaEstadosSelect = listaEstadosSelect;
	}

	public List<SelectItem> getListaAgenciasSelect() {
		return listaAgenciasSelect;
	}

	public void setListaAgenciasSelect(List<SelectItem> listaAgenciasSelect) {
		this.listaAgenciasSelect = listaAgenciasSelect;
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

	public PedidoService getPedidoService() {
		return pedidoService;
	}

	public void setPedidoService(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}
	
	

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
	
	

	public Date getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	
	

	public BigDecimal getSaldocanje() {
		return saldocanje;
	}

	public void setSaldocanje(BigDecimal saldocanje) {
		this.saldocanje = saldocanje;
	}

	public BigDecimal getSaldoactual() {
		return saldoactual;
	}

	public void setSaldoactual(BigDecimal saldoactual) {
		this.saldoactual = saldoactual;
	}

	public String getTipotitular() {
		return tipotitular;
	}

	public void setTipotitular(String tipotitular) {
		this.tipotitular = tipotitular;
	}

	public String getNomtitular() {
		return nomtitular;
	}

	public void setNomtitular(String nomtitular) {
		this.nomtitular = nomtitular;
	}

	public List<EstadoCuentaBean> getListaPendiente() {
		return listaPendiente;
	}

	public void setListaPendiente(List<EstadoCuentaBean> listaPendiente) {
		this.listaPendiente = listaPendiente;
	}
	

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Plantacion getPlantacion() {
		return plantacion;
	}

	public void setPlantacion(Plantacion plantacion) {
		this.plantacion = plantacion;
	}

	public AgenciaCarga getAgencia() {
		return agencia;
	}

	public void setAgencia(AgenciaCarga agencia) {
		this.agencia = agencia;
	}
	
	
public ReportesManagedBean getReportes() {
		return reportes;
	}

	public void setReportes(ReportesManagedBean reportes) {
		this.reportes = reportes;
	}

public void imprimir(String tipo){
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String periodo = new String();
		String tipotitular = new String ();
		if (this.periodo.intValue()==1){
		    periodo = bundle.getString("label.ultimomes");
		}else if (this.periodo.intValue()==2){
			periodo = bundle.getString("label.ultimostresmeses");
		}else if (this.periodo.intValue()==3){
			periodo = bundle.getString("label.ultimosseismeses");
		}else if (this.periodo.intValue()==4){
			periodo = bundle.getString("label.ultimoano");
		}else if (this.periodo.intValue()==5){
			periodo = bundle.getString("label.ultimoscincoanos");
		}else if (this.periodo.intValue()==-1){
			periodo = dateFormat.format(this.fechaInicial)  + " - " + dateFormat.format(this.fechaFinal);
		}

		//Calcular totales.
		
		getTotales(lista);
	
		
		Map parameters = new HashMap();
		parameters.put("TITULAR", this.nomtitular);
		parameters.put("TIPOTITULAR", this.tipotitular);
		parameters.put("PERIODO", periodo);
		parameters.put("SALDOACTUAL", this.saldoactual);
		parameters.put("SALDOCANJE", this.saldocanje);
		parameters.put("SALDOINICIAL", this.saldoinicial.doubleValue());
		parameters.put("FECHA", dateFormat.format(new Date()));
		parameters.put("CARPETAIMAGEN", carpetajrxml);
		parameters.put("TOTALDEBITO", totalDebito);
		parameters.put("TOTALCREDITO", totalCredito);
		//List <PedidoBean> lista = new ArrayList <PedidoBean>();
		String filejrxml = "estadocuenta.jrxml";
		if(this.tipotitular.equalsIgnoreCase("Proveedor")){
			filejrxml = "estadocuentaprov.jrxml";
		}else {
			if(this.tipotitular.equalsIgnoreCase("Cliente")){
				/** aqui va el desarrollo filejrxml = "estadocuentacliente.jrxml"; **/
				//getListaCliente();//totaliza por awb
				filejrxml = "estadocuentaotros.jrxml"; //wate debe desaparecer cuando este arriba este
			}else {
				filejrxml = "estadocuentaotros.jrxml";
			}
			
		}
		reportes.generarPDFGeneral(parameters,lista , filejrxml,tipo);
	}

    private void getListaCliente(){
    	List <EstadoCuentaBean> listaaux = new ArrayList <EstadoCuentaBean> ();
    	for (EstadoCuentaBean estadocta:lista){
    		EstadoCuentaBean estadoNuevo = null;
    		if (estadocta.getAwb()!= null){
    			int i = 0;
    			boolean indicador = false;
    			for (EstadoCuentaBean estadoctaaux: listaaux){
    				if(estadoctaaux.getAwb().equalsIgnoreCase(estadocta.getAwb())){
    					estadoNuevo = estadoctaaux;
    					estadoNuevo.setDebito(estadoNuevo.getDebito() + estadocta.getDebito());
    					estadoNuevo.setCredito(estadoNuevo.getCredito() + estadocta.getCredito());
    					indicador = true;
    				}
    				i++;
    			}
    			if (indicador){
    				listaaux.remove(i-1);
    				listaaux.add(estadoNuevo);
    			}else {
    				listaaux.add(estadocta);
    			}
    		}
    		
    		
    	}
    	
    	lista = listaaux;
    }

    private void getTotales(List<EstadoCuentaBean> lista){
    	totalDebito = 0.0;
    	totalCredito = 0.0;
    	
    	for(EstadoCuentaBean estado:lista){
    		if(estado.getCredito()!= null)
    			totalCredito = totalCredito +estado.getCredito();
    		if(estado.getDebito()!= null)
    			totalDebito = totalDebito+estado.getDebito();
    	}
    	return;
    }

	public List<Proveedor> getProveedores() {
	return proveedores;
}

public void setProveedores(List<Proveedor> proveedores) {
	this.proveedores = proveedores;
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

public Handler getHandler() {
	return handler;
}

public void setHandler(Handler handler) {
	this.handler = handler;
}


	public Double getTotalCredito() {
	return totalCredito;
}

public void setTotalCredito(Double totalCredito) {
	this.totalCredito = totalCredito;
}

public Double getTotalDebito() {
	return totalDebito;
}

public void setTotalDebito(Double totalDebito) {
	this.totalDebito = totalDebito;
}

public BigDecimal getSaldoinicial() {
	return saldoinicial;
}

public void setSaldoinicial(BigDecimal saldoinicial) {
	this.saldoinicial = saldoinicial;
}



	public Integer getIdProveedor() {
	return idProveedor;
}

public void setIdProveedor(Integer idProveedor) {
	this.idProveedor = idProveedor;
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
		 // FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("estadoCuentaMB");
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
