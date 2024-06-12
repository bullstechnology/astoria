package com.bulls.astoria.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

import com.bulls.astoria.enumeracion.EnuDominio;
import com.bulls.astoria.persistence.Cliente;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.Handler;
import com.bulls.astoria.persistence.Persona;
import com.bulls.astoria.persistence.Truck;
import com.bulls.astoria.pojo.Producto;
import com.bulls.astoria.service.ClienteService;
import com.bulls.astoria.service.DominioService;
import com.bulls.astoria.service.PersonaService;
import com.bulls.astoria.utils.Convertidor;
import com.bulls.astoria.utils.Validador;

@ManagedBean (name="editarClienteMB")
@SessionScoped
public class EditarClienteMB extends GeneralManagedBean implements Serializable{

	
	private Integer id;
	private String codigo;
	private Integer tipoident;
	private String numident;
	private String nombre;
	private Integer pais;
	private Integer ciudad;
	private String direccion;
	private String tel1;
	private String tel2;
	private String fax;
	private String cel1;
	private String cel2;
	private String web;
	private String correo1;
	private String correo2;
	private String contactoven;
	private String contactoprop;
	private String contactoinvoice;
	private String skypeven;
	private String skypeprop;
	private String skypealterno;
	private String observaciones;
	private Double cupo;
	private String desccupo;
	private Double saldoinicial;
	private Double saldofinal;
	private String descsaldofinal;
	private Integer idVendedor ;
	private Integer idComprador ;
	private Integer idVendedor2 ;
	private boolean reqFactura;
	private boolean reqFito;
	
	private boolean lunes;
	private boolean martes;
	private boolean miercoles;
	private boolean jueves;
	private boolean viernes;
	private boolean sabado;
	private boolean domingo;
	
	
	ResourceBundle bundle ;
	private Integer idTruck;
	private Integer idHandler;
	
	List <Dominio> paises;
	List <Dominio> ciudades;
	List <Dominio> tiposDocumento;
	List <Persona> empleados;
	List <Persona> clienteVendedores = new ArrayList<Persona>();
	List <Persona> clienteCompradores = new ArrayList<Persona>();;
	List <Truck> trucks;
	List <Handler> handlers;
	List <Cliente> clientes;
	List<Truck> selectedTrucks;
	List<Handler> selectedHandlers;
	List <Truck> clienteTrucks;
	List <Handler> clienteHandlers;
	List <SelectItem> listaPaisesDom;
	List <SelectItem> listaCiudadesDom;
	List<SelectItem> listaTipodocumentoDom;
	List<SelectItem> listaEmpleados;
	List<SelectItem> listaHandlersSelect;
	List<SelectItem> listaTrucksSelect;
	


	
	
	@ManagedProperty(value="#{DominioService}")
	DominioService dominioService;
	
	@ManagedProperty(value="#{PersonaService}")
    PersonaService personaService;
	
	@ManagedProperty(value="#{ClienteService}")
    ClienteService clienteService;
	

	
	
	
	@PostConstruct
	public void EditarClienteMB(){
		borrarSession();
		
		
		bundle =  ResourceBundle.getBundle("messages");
		paises = dominioService.getDominios(EnuDominio.PAISES.getIdTipoDominio());
		ciudades = dominioService.getDominios(EnuDominio.CIUDADES.getIdTipoDominio());
		listaPaisesDom = Convertidor.dominiosToSelectdItems(paises);
		//listaCiudadesDom = Convertidor.dominiosToSelectdItems(ciudades);
		empleados = personaService.getEmpleados();
		trucks = dominioService.getTrucks();
		handlers = dominioService.getHandlers();
		clientes = clienteService.getClientes();
		
		
		tiposDocumento = dominioService.getDominios(EnuDominio.TIPOS_DOCUMENTO.getIdTipoDominio());
		listaTipodocumentoDom = Convertidor.dominiosToSelectdItems(tiposDocumento);
		listaEmpleados = Convertidor.personaToSelectdItems(empleados);
		
		listaHandlersSelect = Convertidor.handlerToSelectdItems(handlers);
		listaTrucksSelect = Convertidor.truckToSelectdItems(trucks);
	}
	
    public void getCiudadesPais(){

    	ciudades= dominioService.getDominiosXPadre(this.pais);
    	listaCiudadesDom = Convertidor.dominiosToSelectdItems(ciudades);
    }
    
    public String editar(Cliente clienteEditar){
    	
		//Set<Truck> targeTrucks = new HashSet<Truck>(selectedTrucks);
		// Set<Handler> targeHandlers = new HashSet<Handler>(selectedHandlers);
		Integer id =  clienteEditar.getId();
		
		Cliente cliente = clienteService.getClienteXId(id);
    	
		clienteTrucks = clienteService.getTrucks(cliente.getId());
		clienteHandlers = clienteService.getHandlers(cliente.getId());
		
	 	clienteVendedores = clienteService.getVendedores(cliente.getId());
		clienteCompradores = clienteService.getCompradores(cliente.getId());
	
		this.setId(cliente.getId());
		this.setCel1(cliente.getCel1());
		this.setCel2(cliente.getCel2());
		this.setCiudad(cliente.getCiudad());
		this.setCodigo(cliente.getCodigo());
		this.setDesccupo(cliente.getDesccupo());
		this.setDescsaldofinal(cliente.getDescsaldofinal());
		this.setDireccion(cliente.getDireccion());
		this.setCorreo1(cliente.getEmail());
		this.setCorreo2(cliente.getEmail2());
		//this.setEstado(true);
		this.setFax(cliente.getFax());
		this.setIdVendedor(cliente.getIdvendedor());
		this.setCupo(cliente.getLimitecredito());
		this.setContactoinvoice(cliente.getNominvoice());
		this.setNombre(cliente.getNombre());
		this.setContactoprop(cliente.getNomprop());
		this.setContactoven(cliente.getNomventas());
		//this.setNotas(null);
		this.setNumident(cliente.getNumident());
		this.setObservaciones(cliente.getObservaciones());
		this.setPais(cliente.getPais());
		this.setReqFactura(cliente.isReqfactura());
		this.setReqFito(cliente.isReqfito());
		this.setSaldofinal(cliente.getSaldofinal());
		this.setSaldoinicial(cliente.getSaldoinicial());
		this.setSkypealterno(cliente.getSkypealt());
		this.setSkypeprop(cliente.getSkypeprop());
		this.setSkypeven(cliente.getSkypeven());
		this.setTel1(cliente.getTel1());
		this.setTel2(cliente.getTel2());
		this.setTipoident(cliente.getTipoident());
		this.setWeb(cliente.getWeb());
		
	
		this.setLunes(cliente.isLunes());
		this.setMartes(cliente.isMartes());
		this.setMiercoles(cliente.isMiercoles());
		this.setJueves(cliente.isJueves());
		this.setViernes(cliente.isViernes());
		this.setSabado(cliente.isSabado());
		this.setDomingo(cliente.isDomingo());

		
		//Actualiza las ciudades del pais
		
		getCiudadesPais();

		return "editarcliente";
		
	
	
}
    
    public String editarCliente(){
    	
    		Set<Truck> targeTrucks = new HashSet<Truck>(clienteTrucks);
    		Set<Handler> targeHandlers = new HashSet<Handler>(clienteHandlers);
    		
    		Set<Persona> targeVendedores = new HashSet<Persona>(clienteVendedores);
    		Set<Persona> targeCompradores = new HashSet<Persona>(clienteCompradores);
    	
    		Cliente cliente = new Cliente();
    		cliente.setId(this.id);
    		cliente.setCel1(this.cel1);
    		cliente.setCel2(this.cel2);
    		cliente.setCiudad(this.ciudad);
    		cliente.setCodigo(this.codigo);
    		cliente.setDesccupo(this.desccupo);
    		cliente.setDescsaldofinal(this.descsaldofinal);
    		cliente.setDireccion(this.direccion);
    		cliente.setEmail(this.correo1);
    		cliente.setEmail2(this.correo2);
    		cliente.setEstado(true);
    		cliente.setFax(this.fax);
    		cliente.setIdvendedor(this.idVendedor);
    		cliente.setLimitecredito(this.cupo);
    		cliente.setNominvoice(this.contactoinvoice);
    		cliente.setNombre(this.nombre);
    		cliente.setNomprop(this.contactoprop);
    		cliente.setNomventas(this.contactoven);
    		cliente.setNotas(null);
    		cliente.setNumident(this.numident);
    		cliente.setObservaciones(this.observaciones);
    		cliente.setPais(this.pais);
    		cliente.setReqfactura(this.reqFactura);
    		cliente.setReqfito(this.reqFito);
    		cliente.setSaldofinal(this.saldofinal);
    		cliente.setSaldoinicial(this.saldoinicial);
    		cliente.setSkypealt(this.skypealterno);
    		cliente.setSkypeprop(this.skypeprop);
    		cliente.setSkypeven(this.skypeven);
    		cliente.setTel1(this.tel1);
    		cliente.setTel2(this.tel2);
    		cliente.setTipoident(this.tipoident);
    		cliente.setWeb(this.web);
    		
    		cliente.setLunes(this.lunes);
    		cliente.setMartes(this.martes);
    		cliente.setMiercoles(this.miercoles);
    		cliente.setJueves(this.jueves);
    		cliente.setViernes(this.viernes);
    		cliente.setSabado(this.sabado);
    		cliente.setDomingo(this.domingo);
    		
    		cliente.setTrucks(targeTrucks);
    		cliente.setHandlers(targeHandlers);
    		
    		cliente.setVendedores(targeVendedores);
    		cliente.setCompradores(targeCompradores);
    		
    		if(validarCliente()){
    			clienteService.editarCliente(cliente);
    			this.putAuditoria("Editar Cliente", "U", "Edito el cliente así : - " + cliente.toString());
    			clientes = clienteService.getClientes();
        		abrirConfirmacion();
        		return "listaclientes";
    		}
    		
    		return null;
     	
    }
    
    
    private void abrirConfirmacion(){
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmaeditarcliente"));
		//RequestContext.getCurrentInstance().showMessageInDialog(message);
		PrimeFaces.current().dialog().showMessageDynamic(message);
		return;

	}

    
    public void subirHandler(){
		System.out.println("SUBIENDOOOOO::::");
		if(this.idHandler != null){
				
		if(validarSubirHandler()){
        	Handler han = dominioService.getHandlerXId(this.idHandler);
        	clienteHandlers.add(han);
		}	
		}
	}
    
    public void subirTruck(){
		System.out.println("SUBIENDOOOOO::::");
		if(this.idTruck != null){
				
		if(validarSubirTruck()){
        	Truck truck = dominioService.getTruckXId(this.idTruck);
        	clienteTrucks.add(truck);
		}	
		}
	}
    
    public void subirVendedor(){
		System.out.println("SUBIENDOOOOO::::");
		if(validarSubirVendedor()){
				
		
        	Persona persona = personaService.getPersonaXId(this.idVendedor2);
        	clienteVendedores.add(persona);
	
		}
	}
    
    public void subirComprador(){
		System.out.println("SUBIENDOOOOO::::");
		if(validarSubirComprador()){
				
		
        	Persona persona = personaService.getPersonaXId(this.idComprador);
        	clienteCompradores.add(persona);
	
		}
	}
    
    
    public void eliminarVendedor(Persona persona){
    	
				clienteVendedores.remove(persona);
			
    	
	}
    
    public void eliminarComprador(Persona persona){
    	
		clienteCompradores.remove(persona);
	

}
    public void eliminarTruck(Truck tru){
    	
				clienteTrucks.remove(tru);
			
    	
	}
    
    public void eliminarHandler(Handler han){
    				clienteHandlers.remove(han);
	
	}
    public boolean validarSubirHandler(){
		boolean ok = true;
		
		for (Handler handler :clienteHandlers){
			if(handler.getId().intValue() == this.idHandler.intValue() ){
				ok=false;
				break;
			}
		}
		return ok;
	}
    
    public boolean validarSubirTruck(){
		boolean ok = true;
		
		for (Truck truck :clienteTrucks){
			if(truck.getId().intValue() == this.idTruck.intValue() ){
				ok=false;
				break;
			}
		}
		return ok;
	}
    
    public boolean validarSubirVendedor(){
		boolean ok = true;
		
		for (Persona persona :clienteVendedores){
			if(persona.getId() == this.idVendedor2.intValue() ){
				ok=false;
				break;
			}
		}
		return ok;
	}
    
    public boolean validarSubirComprador(){
		boolean ok = true;
		
		for (Persona persona :clienteCompradores){
			if(persona.getId() == this.idComprador.intValue() ){
				ok=false;
				break;
			}
		}
		return ok;
	}
  
    
    private boolean validarCliente(){
		boolean ok = true;
		
		if (!Validador.esLongitudCorrecta(this.cel1, 0, 25)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Celular: "," Debe tener entre 1 y 25 caracteres" ));
			ok = false;
		}
		
		if (!Validador.esLongitudCorrecta(this.cel2, 0, 25)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Otro Celular: "," Debe tener entre 1 y 25 caracteres" ));
			ok = false;
		}
		
		if (this.ciudad == null){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ciudad: "," Debe escoger una ciudad" ));
			ok = false;
		}
		
		if (!Validador.esLongitudCorrecta(this.codigo, 1, 15)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Codigo: "," Debe tener entre 1 y 15 caracteres" ));
			ok = false;
		}
		
		if (!Validador.esLongitudCorrecta(this.contactoinvoice, 0, 100)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Facturador: "," Debe tener entre 1 y 100 caracteres" ));
			ok = false;
		}
		
		if (!Validador.esLongitudCorrecta(this.contactoprop, 0, 100)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Propietario: "," Debe tener entre 1 y 100 caracteres" ));
			ok = false;
		}
		if (!Validador.esLongitudCorrecta(this.contactoven, 0, 100)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Comprador: "," Debe tener entre 1 y 100 caracteres" ));
			ok = false;
		}
		if (!Validador.esCorreoCorrecto(this.correo1)){
	   		FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Email ppal: "," Debe ser un formato de email valido " ));
			ok = false;
	   		
	   	}
	   	
		if (!Validador.esCorreoCorrecto(this.correo2)){
	   		FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Email alt: "," Debe ser un formato de email valido " ));
			ok = false;
	   		
	   	}
		
			
		if(!Validador.esDecimal(Double.toString(this.cupo))){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Cupo: "," Debe ser un numero decimal valido" ));
			ok = false;
		}
		
		if (!Validador.esLongitudCorrecta(this.desccupo, 0, 500)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Descripción: "," Debe tener entre 1 y 500 caracteres" ));
			ok = false;
		}
		
		if (!Validador.esLongitudCorrecta(this.direccion, 1, 100)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Dirección: "," Debe tener entre 1 y 100 caracteres" ));
			ok = false;
		}
		
		if (!Validador.esLongitudCorrecta(this.fax, 0, 25)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fax: "," Debe tener entre 1 y 25 caracteres" ));
			ok = false;
		}
		
		if (!Validador.esLongitudCorrecta(this.numident, 1, 25)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Número Documento"," Debe tener entre 1 y 25 caracteres" ));
			ok = false;
		}
		
		if (!Validador.esLongitudCorrecta(this.nombre, 1, 150)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nombre: "," Debe tener entre 1 y 150 caracteres" ));
			ok = false;
		}
		if (!Validador.esLongitudCorrecta(this.observaciones, 0, 500)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Observaciones: "," Debe tener entre 1 y 500 caracteres" ));
			ok = false;
		}
		
		if(this.pais== null){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"País: "," Debe escoger un país" ));
			ok = false;
		}
		
		if(!Validador.esDecimal(Double.toString(this.saldofinal))){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Saldo Fin: "," Debe ser un numero decimal valido" ));
			ok = false;
		}
		if(!Validador.esDecimal(Double.toString(this.saldoinicial))){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Saldo Ini: "," Debe ser un numero decimal valido" ));
			ok = false;
		}
		
		if (!Validador.esLongitudCorrecta(this.skypealterno, 0, 45)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Otro Skype: "," Debe tener entre 1 y 45 caracteres" ));
			ok = false;
		}
		if (!Validador.esLongitudCorrecta(this.skypeprop, 0, 45)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Otro Skype prop: "," Debe tener entre 1 y 45 caracteres" ));
			ok = false;
		}
		if (!Validador.esLongitudCorrecta(this.skypeven, 0, 45)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Otro Skype ven: "," Debe tener entre 1 y 45 caracteres" ));
			ok = false;
		}
		
		if (!Validador.esLongitudCorrecta(this.tel1, 0, 25)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Teléfono Fijo: "," Debe tener entre 1 y 25 caracteres" ));
			ok = false;
		}
		if (!Validador.esLongitudCorrecta(this.tel2, 0, 25)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Otro Teléfono Fijo: "," Debe tener entre 1 y 25 caracteres" ));
			ok = false;
		}
		if (!Validador.esLongitudCorrecta(this.web, 0, 150)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Web: "," Debe tener entre 1 y 150 caracteres" ));
			ok = false;
		}
		
		

		
		return ok;
	}
    
    
    
    public Double getSaldo(Integer idEntidad,String entidad,String tipoSaldo){
		Double saldo = 0.0;
		saldo = dominioService.getSaldo(idEntidad, entidad,tipoSaldo);
		if(saldo==null){
			saldo = 0.0;
		}
		return saldo;
	}
    

    
    public Double getSaldoCliente(Cliente cliente){
		Double saldo = 0.0;
		saldo = dominioService.getSaldo(cliente.getId(), "C","A");
		if(saldo==null){
			saldo = 0.0;
		}
		return saldo;
	}
    

    
public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public String getCodigo() {
	return codigo;
}

public void setCodigo(String codigo) {
	this.codigo = codigo;
}

public Integer getTipoident() {
	return tipoident;
}

public void setTipoident(Integer tipoident) {
	this.tipoident = tipoident;
}

public String getNumident() {
	return numident;
}

public void setNumident(String numident) {
	this.numident = numident;
}

public String getNombre() {
	return nombre;
}

public void setNombre(String nombre) {
	this.nombre = nombre;
}

public Integer getPais() {
	return pais;
}

public void setPais(Integer pais) {
	this.pais = pais;
}

public Integer getCiudad() {
	return ciudad;
}

public void setCiudad(Integer ciudad) {
	this.ciudad = ciudad;
}

public String getDireccion() {
	return direccion;
}

public void setDireccion(String direccion) {
	this.direccion = direccion;
}

public String getTel1() {
	return tel1;
}

public void setTel1(String tel1) {
	this.tel1 = tel1;
}

public String getTel2() {
	return tel2;
}

public void setTel2(String tel2) {
	this.tel2 = tel2;
}

public String getFax() {
	return fax;
}

public void setFax(String fax) {
	this.fax = fax;
}

public String getCel1() {
	return cel1;
}

public void setCel1(String cel1) {
	this.cel1 = cel1;
}

public String getCel2() {
	return cel2;
}

public void setCel2(String cel2) {
	this.cel2 = cel2;
}

public String getWeb() {
	return web;
}

public void setWeb(String web) {
	this.web = web;
}

public String getCorreo1() {
	return correo1;
}

public void setCorreo1(String correo1) {
	this.correo1 = correo1;
}

public String getCorreo2() {
	return correo2;
}

public void setCorreo2(String correo2) {
	this.correo2 = correo2;
}

public String getContactoven() {
	return contactoven;
}

public void setContactoven(String contactoven) {
	this.contactoven = contactoven;
}

public String getContactoprop() {
	return contactoprop;
}

public void setContactoprop(String contactoprop) {
	this.contactoprop = contactoprop;
}

public String getContactoinvoice() {
	return contactoinvoice;
}

public void setContactoinvoice(String contactoinvoice) {
	this.contactoinvoice = contactoinvoice;
}

public String getSkypeven() {
	return skypeven;
}

public void setSkypeven(String skypeven) {
	this.skypeven = skypeven;
}

public String getSkypeprop() {
	return skypeprop;
}

public void setSkypeprop(String skypeprop) {
	this.skypeprop = skypeprop;
}

public String getSkypealterno() {
	return skypealterno;
}

public void setSkypealterno(String skypealterno) {
	this.skypealterno = skypealterno;
}

public String getObservaciones() {
	return observaciones;
}

public void setObservaciones(String observaciones) {
	this.observaciones = observaciones;
}

public Double getCupo() {
	return cupo;
}

public void setCupo(Double cupo) {
	this.cupo = cupo;
}

public String getDesccupo() {
	return desccupo;
}

public void setDesccupo(String desccupo) {
	this.desccupo = desccupo;
}

public Double getSaldoinicial() {
	return saldoinicial;
}

public void setSaldoinicial(Double saldoinicial) {
	this.saldoinicial = saldoinicial;
}

public Double getSaldofinal() {
	return saldofinal;
}

public void setSaldofinal(Double saldofinal) {
	this.saldofinal = saldofinal;
}

public String getDescsaldofinal() {
	return descsaldofinal;
}

public void setDescsaldofinal(String descsaldofinal) {
	this.descsaldofinal = descsaldofinal;
}

public Integer getIdVendedor() {
	return idVendedor;
}

public void setIdVendedor(Integer idVendedor) {
	this.idVendedor = idVendedor;
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

public List<Dominio> getTiposDocumento() {
	return tiposDocumento;
}

public void setTiposDocumento(List<Dominio> tiposDocumento) {
	this.tiposDocumento = tiposDocumento;
}

public DominioService getDominioService() {
	return dominioService;
}

public void setDominioService(DominioService dominioService) {
	this.dominioService = dominioService;
}

public boolean isReqFactura() {
	return reqFactura;
}

public void setReqFactura(boolean reqFactura) {
	this.reqFactura = reqFactura;
}

public boolean isReqFito() {
	return reqFito;
}

public void setReqFito(boolean reqFito) {
	this.reqFito = reqFito;
}

public List<Persona> getEmpleados() {
	return empleados;
}

public void setEmpleados(List<Persona> empleados) {
	this.empleados = empleados;
}

public PersonaService getPersonaService() {
	return personaService;
}

public void setPersonaService(PersonaService personaService) {
	this.personaService = personaService;
}

public List<SelectItem> getListaEmpleados() {
	return listaEmpleados;
}

public void setListaEmpleados(List<SelectItem> listaEmpleados) {
	this.listaEmpleados = listaEmpleados;
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

public ClienteService getClienteService() {
	return clienteService;
}

public void setClienteService(ClienteService clienteService) {
	this.clienteService = clienteService;
}

public ResourceBundle getBundle() {
	return bundle;
}

public void setBundle(ResourceBundle bundle) {
	this.bundle = bundle;
}

public List<Cliente> getClientes() {
	return clientes;
}

public void setClientes(List<Cliente> clientes) {
	this.clientes = clientes;
}

public List<Truck> getClienteTrucks() {
	return clienteTrucks;
}

public void setClienteTrucks(List<Truck> clienteTrucks) {
	this.clienteTrucks = clienteTrucks;
}

public List<Handler> getClienteHandlers() {
	return clienteHandlers;
}

public void setClienteHandlers(List<Handler> clienteHandlers) {
	this.clienteHandlers = clienteHandlers;
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

public List<SelectItem> getListaHandlersSelect() {
	return listaHandlersSelect;
}

public void setListaHandlersSelect(List<SelectItem> listaHandlersSelect) {
	this.listaHandlersSelect = listaHandlersSelect;
}

public List<SelectItem> getListaTrucksSelect() {
	return listaTrucksSelect;
}

public void setListaTrucksSelect(List<SelectItem> listaTrucksSelect) {
	this.listaTrucksSelect = listaTrucksSelect;
}

public Integer getIdComprador() {
	return idComprador;
}

public void setIdComprador(Integer idComprador) {
	this.idComprador = idComprador;
}

public Integer getIdVendedor2() {
	return idVendedor2;
}

public void setIdVendedor2(Integer idVendedor2) {
	this.idVendedor2 = idVendedor2;
}

public List<Persona> getClienteVendedores() {
	return clienteVendedores;
}

public void setClienteVendedores(List<Persona> clienteVendedores) {
	this.clienteVendedores = clienteVendedores;
}

public List<Persona> getClienteCompradores() {
	return clienteCompradores;
}

public void setClienteCompradores(List<Persona> clienteCompradores) {
	this.clienteCompradores = clienteCompradores;
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
	 // FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("editarClienteMB");
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



