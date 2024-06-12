package com.bulls.astoria.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.bulls.astoria.persistence.Cliente;
import com.bulls.astoria.persistence.Pedido;
import com.bulls.astoria.persistence.PedidoConciliado;
import com.bulls.astoria.persistence.PedidoFacturado;
import com.bulls.astoria.pojo.EncabezadoPedidoBean;
import com.bulls.astoria.pojo.PedidoBean;
import com.bulls.astoria.service.ClienteService;
import com.bulls.astoria.service.DominioService;
import com.bulls.astoria.service.PedidoService;
import com.bulls.astoria.utils.Convertidor;


@ManagedBean (name="buscarPedidoDespachadoMB")
@SessionScoped
public class BuscarPedidoDespachado extends GeneralManagedBean implements Serializable{

	private Date fechainicial;
	private Date fechafinal;
	private Integer cliente;
	private Integer idPedido;
	private String estado;
	private List <Pedido> listaPedidos;
	private List <EncabezadoPedidoBean> listaPedidosSalida;
	private List <EncabezadoPedidoBean> listaPedidosRegulares;
	private List <EncabezadoPedidoBean> listaPedidosDespachados;
	private List <EncabezadoPedidoBean> listaPedidosFacturados;
	List<Cliente> clientes;
	List <SelectItem> clientesSelect;
	
	@ManagedProperty(value="#{DominioService}")
	DominioService dominioService;
	
	@ManagedProperty(value="#{PedidoService}")
	PedidoService pedidoService;
	
	@ManagedProperty(value="#{ClienteService}")
    ClienteService clienteService;
	
	@PostConstruct
	public void BuscarPedido(){
		borrarSession();
		listaPedidos = new ArrayList<Pedido>();
		listaPedidosSalida = new ArrayList <EncabezadoPedidoBean>();
		clientes = clienteService.getClientes();
		clientesSelect = Convertidor.clientesToSelectdItems(clientes);
   	    getPedidosRegularesBean(pedidoService.getPedidosEstado("P"));
   	    getPedidosDespachadosBean(pedidoService.getPedidosConciliadoEstado("D"));
   	    getPedidosFacturadosBean(pedidoService.getPedidosFacturadoEstado("D"));
	}
	
	public void buscar(){
		
	 if(validarBuscar()){	
	  Integer clienteaux = null;
	  if(this.cliente!=0)
		  clienteaux = this.cliente;
	  listaPedidos = pedidoService.getPedidos(clienteaux, estado, fechainicial, fechafinal,idPedido);
	  getPedidosBean(listaPedidos);
	 } 
	 
	// FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}
	
	private void getPedidosBean(List<Pedido> listaPedidos){
		listaPedidosSalida = new ArrayList <EncabezadoPedidoBean>();
		for(Pedido pedido : listaPedidos){
			EncabezadoPedidoBean pb = new EncabezadoPedidoBean();
			pb.setEstado(pedido.getEstado());
			pb.setIdCliente(pedido.getIdcliente());
			pb.setIdPais(pedido.getIdpais());
			pb.setIdCiudad(pedido.getIdciudad());
			pb.setIdpedido(pedido.getId());
			pb.setFechapedido(pedido.getFechapedido());
			pb.setFechadespacho(pedido.getFechadespacho());
			pb.setFechallegada(pedido.getFechallegada());
			pb.setProgramado(pedido.isProgramado());
			//nombres
			
			pb.setNombrepais((dominioService.getDominio(pedido.getIdpais()).getNomcorto()));
			pb.setNombreciudad((dominioService.getDominio(pedido.getIdciudad()).getNomcorto()));
			pb.setNombrecliente(clienteService.getClienteXId(pedido.getIdcliente()).getNombre());
			listaPedidosSalida.add(pb);
		}
	}
	
	private void getPedidosRegularesBean(List<Pedido> listaPedidos){
		listaPedidosRegulares = new ArrayList <EncabezadoPedidoBean>();
		for(Pedido pedido : listaPedidos){
			EncabezadoPedidoBean pb = new EncabezadoPedidoBean();
			pb.setEstado(pedido.getEstado());
			pb.setIdCliente(pedido.getIdcliente());
			pb.setIdPais(pedido.getIdpais());
			pb.setIdCiudad(pedido.getIdciudad());
			pb.setIdpedido(pedido.getId());
			pb.setFechapedido(pedido.getFechapedido());
			pb.setFechadespacho(pedido.getFechadespacho());
			pb.setFechallegada(pedido.getFechallegada());
			pb.setProgramado(pedido.isProgramado());
			pb.setLunes(pedido.isLunes());
			pb.setMartes(pedido.isMartes());
			pb.setMiercoles(pedido.isMiercoles());
			pb.setJueves(pedido.isJueves());
			pb.setViernes(pedido.isViernes());
			pb.setSabado(pedido.isSabado());
			pb.setDomingo(pedido.isDomingo());
			//nombres
			
			pb.setNombrepais((dominioService.getDominio(pedido.getIdpais()).getNomcorto()));
			pb.setNombreciudad((dominioService.getDominio(pedido.getIdciudad()).getNomcorto()));
			pb.setNombrecliente(clienteService.getClienteXId(pedido.getIdcliente()).getNombre());
			listaPedidosRegulares.add(pb);
		}
	}
	
	
	private void getPedidosDespachadosBean(List<PedidoConciliado> listaPedidos){
		listaPedidosDespachados= new ArrayList <EncabezadoPedidoBean>();
		for(PedidoConciliado pedido : listaPedidos){
			EncabezadoPedidoBean pb = new EncabezadoPedidoBean();
			pb.setEstado(pedido.getEstado());
			pb.setIdCliente(pedido.getIdcliente());
			pb.setIdPais(pedido.getIdpais());
			pb.setIdCiudad(pedido.getIdciudad());
			pb.setIdpedido(pedido.getId());
			pb.setFechapedido(pedido.getFechapedido());
			pb.setFechadespacho(pedido.getFechadespacho());
			pb.setFechallegada(pedido.getFechallegada());
			pb.setProgramado(pedido.isProgramado());
			pb.setLunes(pedido.isLunes());
			pb.setMartes(pedido.isMartes());
			pb.setMiercoles(pedido.isMiercoles());
			pb.setJueves(pedido.isJueves());
			pb.setViernes(pedido.isViernes());
			pb.setSabado(pedido.isSabado());
			pb.setDomingo(pedido.isDomingo());
			//nombres
			
			pb.setNombrepais((dominioService.getDominio(pedido.getIdpais()).getNomcorto()));
			pb.setNombreciudad((dominioService.getDominio(pedido.getIdciudad()).getNomcorto()));
			pb.setNombrecliente(clienteService.getClienteXId(pedido.getIdcliente()).getNombre());
			listaPedidosDespachados.add(pb);
		}
	}
	
	private void getPedidosFacturadosBean(List<PedidoFacturado> listaPedidos){
		listaPedidosFacturados= new ArrayList <EncabezadoPedidoBean>();
		for(PedidoFacturado pedido : listaPedidos){
			EncabezadoPedidoBean pb = new EncabezadoPedidoBean();
			pb.setEstado(pedido.getEstado());
			pb.setIdCliente(pedido.getIdcliente());
			pb.setIdPais(pedido.getIdpais());
			pb.setIdCiudad(pedido.getIdciudad());
			pb.setIdpedido(pedido.getId());
			pb.setFechapedido(pedido.getFechapedido());
			pb.setFechadespacho(pedido.getFechadespacho());
			pb.setFechallegada(pedido.getFechallegada());
			pb.setProgramado(pedido.isProgramado());
			pb.setLunes(pedido.isLunes());
			pb.setMartes(pedido.isMartes());
			pb.setMiercoles(pedido.isMiercoles());
			pb.setJueves(pedido.isJueves());
			pb.setViernes(pedido.isViernes());
			pb.setSabado(pedido.isSabado());
			pb.setDomingo(pedido.isDomingo());
			//nombres
			
			pb.setNombrepais((dominioService.getDominio(pedido.getIdpais()).getNomcorto()));
			pb.setNombreciudad((dominioService.getDominio(pedido.getIdciudad()).getNomcorto()));
			pb.setNombrecliente(clienteService.getClienteXId(pedido.getIdcliente()).getNombre());
			listaPedidosFacturados.add(pb);
		}
	}
	public void editar(EncabezadoPedidoBean pedido){
		
		pedidoService.getPedidoXId(pedido.getIdpedido());
		pedidoService.getDetallesPedido(pedido.getIdpedido());
		return;
	}
	
	private boolean validarBuscar(){
		boolean ok = true;
		if(this.cliente == 0 && this.estado.equalsIgnoreCase("") && (this.fechafinal == null || this.fechainicial == null)){
			ok = false;
			FacesContext.getCurrentInstance().addMessage(null,
		    		  new FacesMessage(FacesMessage.SEVERITY_ERROR,"Filtros","Debe usar un filtro por lo menos" ));
		}else if (this.fechafinal != null && this.fechainicial != null){
			if((this.fechainicial.compareTo(this.fechafinal) > 0)){
				ok = false;
					  FacesContext.getCurrentInstance().addMessage(null,
		    		  new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fiechas","Feacha inicial debe ser menor a fecha final" ));
			}			  
		}
		return ok;
	}

	public Date getFechainicial() {
		return fechainicial;
	}

	public void setFechainicial(Date fechainicial) {
		this.fechainicial = fechainicial;
	}

	public Date getFechafinal() {
		return fechafinal;
	}

	public void setFechafinal(Date fechafinal) {
		this.fechafinal = fechafinal;
	}

	public Integer getCliente() {
		return cliente;
	}

	public void setCliente(Integer cliente) {
		this.cliente = cliente;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<Pedido> getListaPedidos() {
		return listaPedidos;
	}

	public void setListaPedidos(List<Pedido> listaPedidos) {
		this.listaPedidos = listaPedidos;
	}

	public List<EncabezadoPedidoBean> getListaPedidosSalida() {
		return listaPedidosSalida;
	}

	public void setListaPedidosSalida(List<EncabezadoPedidoBean> listaPedidosSalida) {
		this.listaPedidosSalida = listaPedidosSalida;
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

	public PedidoService getPedidoService() {
		return pedidoService;
	}

	public void setPedidoService(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

	public ClienteService getClienteService() {
		return clienteService;
	}

	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	public List<EncabezadoPedidoBean> getListaPedidosRegulares() {
		return listaPedidosRegulares;
	}

	public void setListaPedidosRegulares(
			List<EncabezadoPedidoBean> listaPedidosRegulares) {
		this.listaPedidosRegulares = listaPedidosRegulares;
	}

	public List<EncabezadoPedidoBean> getListaPedidosDespachados() {
		return listaPedidosDespachados;
	}

	public void setListaPedidosDespachados(
			List<EncabezadoPedidoBean> listaPedidosDespachados) {
		this.listaPedidosDespachados = listaPedidosDespachados;
	}
	
	public Integer getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Integer idPedido) {
		this.idPedido = idPedido;
	}

	public void borrarSession(){
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("aerolineaMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("cargaMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("buscarNotasMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("buscarOrdenMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("buscarPedidoMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("buscarPedidoAutoMB");
		 //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("buscarPedidoDespachadoMB");
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
