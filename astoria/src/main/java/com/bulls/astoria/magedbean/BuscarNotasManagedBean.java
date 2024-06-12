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
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bulls.astoria.enumeracion.EnuDominio;
import com.bulls.astoria.persistence.Cliente;
import com.bulls.astoria.persistence.Composicion;
import com.bulls.astoria.persistence.DetallePedido;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.Handler;
import com.bulls.astoria.persistence.Nota;
import com.bulls.astoria.persistence.Pedido;
import com.bulls.astoria.persistence.Persona;
import com.bulls.astoria.persistence.Plantacion;
import com.bulls.astoria.persistence.PlantacionProducto;
import com.bulls.astoria.persistence.Proveedor;
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
import com.bulls.astoria.utils.Validador;


@ManagedBean (name="buscarNotasMB")
@SessionScoped
public class BuscarNotasManagedBean extends GeneralManagedBean implements Serializable{
	
	
	private Integer idCliente;
	private Integer idPlantacion;
	private Integer idDebitoCredito;
	private Integer idAgencia;
	private Integer idHandler;
	private Integer idEstado;
	private Date fecha = new Date();
	private List <NotaBean> listanotas;
	private Double valor;
	private String descripcion;
	private Integer idConcepto;
	private String factura;
	private String awb;
	private String username;
	
	private NotaBean notabeaneditar;

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
	
	@ManagedProperty(value="#{UserService}")
	UserService userService;
	
	
	
	
	@PostConstruct
	public void BuscarNotasManagedBean(){
		
		
		borrarSession();
		
		
		bundle =  ResourceBundle.getBundle("messages");
		
		
		listaPlantaciones = plantacionService.getPlantaciones();
		listaPlantacionesSelect = Convertidor.plantacionToSelectdItems(listaPlantaciones);	
		
		clientes = clienteService.getClientes();
		clientesSelect = Convertidor.clientesToSelectdItems(clientes);
		
		conceptos = dominioService.getDominios(EnuDominio.CONCEPTOSNOTAS.getIdTipoDominio());
		listaConceptosSelect = Convertidor.dominiosToSelectdItems(conceptos);
		
		estados = dominioService.getDominios(EnuDominio.ESTADOSNOTAS.getIdTipoDominio());
		listaEstadosSelect = Convertidor.dominiosToSelectdItems(estados);
		
		listaAgenciasSelect = Convertidor.agenciasToSelectdItems(dominioService.getAgenciasCarga());

		proveedores = plantacionService.getProveedores();  
		handlers = dominioService.getHandlers();
		}
	
	
	public String editar(NotaBean notabean){
		Nota nota = dominioService.getNotaxId(notabean.getId());
	
		this.setFecha(nota.getFecha());
		this.setIdCliente(nota.getIdCliente());
		this.setIdPlantacion(nota.getIdPlantacion());
		this.setIdAgencia(nota.getIdAgencia());
		this.setIdHandler(nota.getIdHandler());
		this.idDebitoCredito=nota.getIdDebitoCredito();
		this.setUsername(nota.getUsername());
		this.setValor(nota.getValor());
		this.setDescripcion(nota.getConcepto());
		this.setAwb(nota.getAwb());
		this.setFactura(nota.getFactura());
		this.setIdEstado(nota.getIdEstado());
		this.setIdConcepto(nota.getIdConcepto());
		notabeaneditar= notabean;
		return "editarnota";
   }
	
	
	public String editarNota(){

		        if(validarEditar(notabeaneditar)){
		        	Nota nota = dominioService.getNotaxId(notabeaneditar.getId());
		        	nota.setIdEstado(this.idEstado);
		        	nota.setFecha(this.fecha);
		        	nota.setValor(this.valor);
		        	nota.setIdCliente(this.idCliente);
		        	nota.setIdPlantacion(idPlantacion);
		        	nota.setIdAgencia(idAgencia);
		        	nota.setIdHandler(idHandler);
		        	//debitocredito
		        	nota.setIdConcepto(idConcepto);
		        	nota.setFactura(factura);
		        	nota.setAwb(awb);
		        	nota.setConcepto(descripcion);
		        	dominioService.editNota(nota);
		        	this.putAuditoria("Editar Nota", "U", "Edito Nota asi : - " + nota.toString());
		        	abrirConfirmacion();
		        	listanotas = new ArrayList <NotaBean>();	        
		      	    return "buscarnota";
		        }
		        listanotas = new ArrayList <NotaBean>();	        
		  	  return null;    
	}
	
	public void buscar(){
		listanotas = new ArrayList <NotaBean>();
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Persona per = personaService.getPersonaXusername(username);
		User user = userService.getUser(username);
		Role rol = user.getRole();
		//plantacion 3 cliente 4  carguera 5
		if(rol.getId() == 3){
			this.idPlantacion=per.getIdPlantacion();
			this.idCliente=null;
			this.idAgencia=null;
			this.idHandler=null;
			
		}else if(rol.getId() == 4){
			this.idPlantacion=null;
			this.idCliente=per.getIdCliente();
			this.idAgencia=null;
			this.idHandler=null;
		}else if(rol.getId() == 5){
			this.idPlantacion=null;
			this.idCliente=null;
			this.idAgencia=per.getIdAgencia();
			this.idHandler=null;
		}else if(rol.getId() == 9){
			this.idPlantacion=null;
			this.idCliente=null;
			this.idAgencia=null;
			this.idHandler=per.getIdHandler();
		}
		if(validar()){
			
			if(this.idCliente != null){
				getNotas(dominioService.getNotasGenericoCliente(this.idCliente,this.idDebitoCredito,this.idEstado));
			}else if(this.idPlantacion != null){
				getNotas(dominioService.getNotasGenericoPlantacion(this.idPlantacion,this.idDebitoCredito,this.idEstado));
			}else if(this.idAgencia != null){
				getNotas(dominioService.getNotasGenericoAgencia(this.idAgencia,this.idDebitoCredito,this.idEstado));
			}else if(this.idHandler != null){
				getNotas(dominioService.getNotasGenericoHandler(this.idHandler,this.idDebitoCredito,this.idEstado));
			}
		}
	}
	

	private void abrirConfirmacion(){
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmaeditarnota"));
		//RequestContext.getCurrentInstance().showMessageInDialog(message);
		PrimeFaces.current().dialog().showMessageDynamic(message);
		return;

	}
	
	public void getNotas(List <Nota> lista){
		
		listanotas = new ArrayList <NotaBean>();
		for(Nota nota:lista){
			NotaBean notaaux = new NotaBean();
			
			notaaux.setFecha(nota.getFecha());
			notaaux.setIdcliente(nota.getIdCliente());
			notaaux.setIdplantacion(nota.getIdPlantacion());
			notaaux.setIdagencia(nota.getIdAgencia());
			notaaux.setIdhandler(nota.getIdHandler());
			if(nota.getIdCliente()!= null){
				notaaux.setNombrecliente(clienteService.getClienteXId(nota.getIdCliente()).getNombre());
				notaaux.setTitular(notaaux.getNombrecliente());
			}	
			if(nota.getIdPlantacion()!= null){
				//notaaux.setNombreplantacion(plantacionService.getProveedor(nota.getIdPlantacion()).getNombre());
				// edtaba sin comentarear hasta 04 de abril de 2020 buscaba una plantacion con el id realmente del provvedor y si no existia pues se toteaba notaaux.setNombreplantacion(plantacionService.getPlantacion(nota.getIdPlantacion()).getNombre());
				// KEB este estaba antes de 27 mayo traia el nombre tirular el de plantacion se cambia para proveedor notaaux.setTitular(notaaux.getNombreplantacion());
				//calcula nombre proveedor
				String nomproveedor = new String ();
				nomproveedor = plantacionService.getProveedor(nota.getIdPlantacion()).getNombre();
				notaaux.setTitular(nomproveedor);
				//notaaux.setTitular(notaaux.getNombreplantacion());
				
			}
			if(nota.getIdAgencia()!= null){
				notaaux.setNombreagencia(dominioService.getAgenciaXId(nota.getIdAgencia()).getNombre());
				notaaux.setTitular(notaaux.getNombreagencia());
			}
			if(nota.getIdHandler()!= null){
				notaaux.setNombrehandler(dominioService.getHandlerXId(nota.getIdHandler()).getNombre());
				notaaux.setTitular(notaaux.getNombrehandler());
			}
			if(nota.getIdDebitoCredito() == -1){
				notaaux.setTipo("Crédito");
			}else {
				notaaux.setTipo("Debito");
			}
			
			notaaux.setValortipo(nota.getIdDebitoCredito());
			notaaux.setUsername(nota.getUsername());
			notaaux.setValor(nota.getValor());
			notaaux.setDescripcion(nota.getConcepto());
			notaaux.setAwb(nota.getAwb());
			notaaux.setFactura(nota.getFactura());
			notaaux.setId(nota.getId());
			notaaux.setIdconcepto(nota.getIdConcepto());
			notaaux.setIdestado(nota.getIdEstado());
			listanotas.add(notaaux);
		}
	}

	private Boolean validar(){
		boolean ok = true;
		
		if(this.idCliente != null  && this.idPlantacion != null && this.idAgencia != null && this.idHandler != null){
			 
 			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.titularnota") + ":",bundle.getString("errordebetitular")));
 			 ok = false;
 			 return ok;
		}
		if(this.idCliente == null  && this.idPlantacion == null && this.idAgencia == null && this.idHandler == null){
			 
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.titularnota") + ":",bundle.getString("errordebetitular")));
 			 ok = false;
 			 return ok;
		}
		
		if(this.idEstado == null){
			 
 			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.estado") + ":",bundle.getString("errordebeestado")));
 			 ok = false;
 			 return ok;
		}
		
		return ok;
	}
	
	private Boolean validarEditar(NotaBean nota){
		boolean ok = true;
		
		if(this.idEstado < nota.getIdestado() ){
			 
 			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.estado") + ":",bundle.getString("errorseguimientonota")));
 			 ok = false;
		}
		/*if(nota.getIdestado() == 84 || nota.getIdestado() == 85){
			 
 			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.estado") + ":",bundle.getString("errorseguimientonota")));
 			 ok = false;
		}*/
		if(this.idCliente == null  && this.idPlantacion == null && this.idAgencia == null && this.idHandler == null){
			 
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.titularnota") + ":",bundle.getString("errordebetitular")));
 			 ok = false;
		}
		
		if(this.idEstado == null){
			 
 			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.estado") + ":",bundle.getString("errordebeestado")));
 			 ok = false;
		}
		
		if(this.fecha == null){
			 
 			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,bundle.getString("label.fecha") + ":","Debe seleccionar una fecha"));
 			 ok = false;
		}
		
		return ok;
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




	
	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public Double getValor() {
		return valor;
	}


	public void setValor(Double valor) {
		this.valor = valor;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public List <NotaBean> getListanotas() {
		return listanotas;
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




	public PedidoService getPedidoService() {
		return pedidoService;
	}




	public void setPedidoService(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
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


	public void setListanotas(List<NotaBean> listanotas) {
		this.listanotas = listanotas;
	}


	public Integer getIdAgencia() {
		return idAgencia;
	}


	public void setIdAgencia(Integer idAgencia) {
		this.idAgencia = idAgencia;
	}


	public List<SelectItem> getListaAgenciasSelect() {
		return listaAgenciasSelect;
	}


	public void setListaAgenciasSelect(List<SelectItem> listaAgenciasSelect) {
		this.listaAgenciasSelect = listaAgenciasSelect;
	}


	public Integer getIdEstado() {
		return idEstado;
	}


	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
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


	public UserService getUserService() {
		return userService;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
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


	public void borrarSession(){
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("aerolineaMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("cargaMB");
		  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("buscarNotasMB");
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
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("variedadMB");

	   }
    
}
