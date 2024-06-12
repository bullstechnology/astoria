package com.bulls.astoria.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.PrimeFaces;
//import org.primefaces.context.RequestContext;
import org.springframework.dao.DataAccessException;

import com.bulls.astoria.enumeracion.EnuDominio;
import com.bulls.astoria.persistence.Cliente;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.Persona;
import com.bulls.astoria.persistence.Plantacion;
import com.bulls.astoria.persistence.Role;
import com.bulls.astoria.persistence.User;
import com.bulls.astoria.service.ClienteService;
import com.bulls.astoria.service.DominioService;
import com.bulls.astoria.service.PersonaService;
import com.bulls.astoria.service.PlantacionService;
import com.bulls.astoria.service.RoleService;
import com.bulls.astoria.service.UserService;
import com.bulls.astoria.utils.Convertidor;
import com.bulls.astoria.utils.Validador;


@ManagedBean (name="editarUsuarioMB")
@SessionScoped
public class EditarUsuarioManagedBean extends GeneralManagedBean implements Serializable{
	
private static final long serialVersionUID = 1L;
private static final String SUCCESS = "success";
private static final String ERROR   = "error";

@ManagedProperty(value="#{PersonaService}")
PersonaService personaService;

@ManagedProperty(value="#{DominioService}")
DominioService dominioService;

@ManagedProperty(value="#{UserService}")
UserService userService;

@ManagedProperty(value="#{RoleService}")
RoleService roleService;


@ManagedProperty(value="#{ClienteService}")
ClienteService clienteService;

@ManagedProperty(value="#{PlantacionService}")
PlantacionService plantacionService;


private int id;
private String nombre ;
private String segnombre ;
private String apellido;
private String segapellido;
private Integer tipodoc;
private String numdoc;
private String tel1;
private String tel2;
private String cel1;
private String cel2;
private String messenger;
private String skype;
private String email;
private String direccion;
private Integer pais;
private Integer ciudad;
private Integer tipopersona;
private Integer rol;
private String username;
private String password;
private boolean empleado;
private Integer cargo;
private Boolean escliente;
private Boolean esplantacion;
private Boolean escarguera;
private Boolean eshandler;
private Integer cliente;
private Integer plantacion;
private Integer carguera;
private Integer handler;
private boolean enabled;
String usernameaux ;

List<SelectItem> listaTipopersonaDom;
List<SelectItem> listaTipodocumentoDom;
List<SelectItem> listaRolDom;
List<SelectItem> listaCargosDom;
List<String> listaparam;

List <Plantacion>  listaPlantaciones;
List <SelectItem> listaPlantacionesSelect;

List<Cliente> clientes;
List <SelectItem> clientesSelect;



List <SelectItem> listaAgenciasSelect;
List <SelectItem> listaHandlersSelect;

User user ; 

transient ResourceBundle bundle ;


public List<SelectItem> getListaTipodocumentoDom() {
	return listaTipodocumentoDom;
}

public void setListaTipodocumentoDom(List<SelectItem> listaTipodocumentoDom) {
	this.listaTipodocumentoDom = listaTipodocumentoDom;
}

private String message ="";


List <Dominio> tiposDocumento;
List <Dominio> tiposPersona;
List <Role> tiposRol;
List <Dominio> listaCargos;
 
public String getMessage() {
    return message;
}

public void setMessage(String message) {
    this.message = message;
}

@PostConstruct
public void EditarUsuarioManagedBean(){
	
	borrarSession();
	personaService = getPersonaService();
	dominioService = getDominioService();
	roleService = getRoleService();
	userService = getUserService();
	
	tiposPersona = dominioService.getDominios(EnuDominio.TIPOS_PERSONA.getIdTipoDominio());
	tiposDocumento = dominioService.getDominios(EnuDominio.TIPOS_DOCUMENTO.getIdTipoDominio());
	tiposRol = roleService.getRoles();
	
	listaTipopersonaDom = Convertidor.dominiosToSelectdItems(tiposPersona);
	listaTipodocumentoDom = Convertidor.dominiosToSelectdItems(tiposDocumento);
	listaRolDom =  rolesToSelectdItems(tiposRol);
	bundle =  ResourceBundle.getBundle("messages");
	
	
	listaCargos = dominioService.getDominios(EnuDominio.CARGOS.getIdTipoDominio());
	listaCargosDom = Convertidor.dominiosToSelectdItems(listaCargos);
	
	listaPlantaciones = plantacionService.getPlantaciones();
	listaPlantacionesSelect = Convertidor.plantacionToSelectdItems(listaPlantaciones);	
	
	clientes = clienteService.getClientes();
	clientesSelect = Convertidor.clientesToSelectdItems(clientes);
	
	
	listaAgenciasSelect = Convertidor.agenciasToSelectdItems(dominioService.getAgenciasCarga());
	listaHandlersSelect = Convertidor.handlersToSelectdItems(dominioService.getHandlers());
	
	//ver si hay requerimiento de username
	

	Map<String, Object> map = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
	String username = (String) map.get("username");
	listaparam = new ArrayList <String>();
	listaparam.add(username);
	
	
    if(username != null){
    	Persona persona = personaService.getPersonaXusername(username);
    	User usuario = userService.getUser(username);
    	//setear tods los valores de perrsona la bena para la pagina.
    	user = usuario;
    	setPersona (persona,usuario);
    }
}


public int getId() {
	return id;
}


public void setId(int id) {
	this.id = id;
}


public String getNombre() {
	return nombre;
}


public void setNombre(String nombre) {
	this.nombre = nombre;
}


public String getSegnombre() {
	return segnombre;
}


public void setSegnombre(String segnombre) {
	this.segnombre = segnombre;
}


 public String addPersona() {
        try {
        	
        	Map map = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
        	String aux = (String)FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("username2");
        	//String aux2 = (String) event.getComponent().getAttributes().get("username");
        	userService = getUserService();
        	
        	if(this.validar()){
        		Persona persona = new Persona();
        		persona.setId(this.getId());
        		persona.setNombre(this.getNombre());
        		persona.setSegnombre(this.getSegnombre());
        		persona.setApellido(this.getApellido());
        		persona.setSegapellido(this.getSegapellido());
        		persona.setTipodoc(this.getTipodoc());
        		persona.setNumdoc(this.getNumdoc());
        		persona.setTel1(this.tel1);
        		persona.setTel2(this.tel2);
        		persona.setCel1(this.cel1);
        		persona.setCel2(this.cel2);
        		persona.setDireccion(this.direccion);
        		persona.setEmail(this.email);
        		persona.setSkype(this.skype);
        		persona.setMessenger(this.messenger);
        		persona.setTipopersona(this.tipopersona);
        		persona.setUsername(this.username);
        		persona.setEmpleado(this.isEmpleado());
        		persona.setCargo(this.getCargo());
        		persona.setCliente(this.escliente);
        		persona.setPlantacion(this.esplantacion);
        		persona.setAgencia(this.escarguera);
        		persona.setHandler(this.eshandler);
        		persona.setIdCliente(this.cliente);
        		persona.setIdPlantacion(this.plantacion);
        		persona.setIdAgencia(this.carguera);
        		persona.setIdHandler(this.carguera);
        		
        		
        		
        		Role role = new Role();
        		role = roleService.getRoleXId(this.rol);
        		user.setLogin(this.username);
        		user.setPassword(this.password);
        		user.setEnabled(this.isEnabled());
        		user.setRole(role);
        		getPersonaService().editarPersona(persona, user);
        		this.putAuditoria("Editar Usuario", "U", "Edito el usuario y lo dejo así : - " + persona.toString()  +   "  " + username.toString());
        		reset();
        		abrirConfirmacion();
        	}	
            return SUCCESS;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return ERROR;
    }


public PersonaService getPersonaService() {
	return personaService;
}


public void setPersonaService(PersonaService personaService) {
	this.personaService = personaService;
}

public void reset() {
    this.setId(0);
    this.setNombre("");
    this.setSegnombre("");
    this.setApellido(null);
    this.setSegapellido(null);
    this.setCel1(null);
    this.setCel2(null);
    this.setDireccion(null);
    this.setEmail(null);
    this.setMessenger(null);
    this.setNumdoc(null);
    this.setSkype(null);
    this.setTel1(null);
    this.setTel2(null);
    this.setTipodoc(null);
    this.setTipopersona(null);
    this.setUsername(null);
    this.setPassword(null);
    this.setCargo(null);
    this.setEmpleado(false);
    this.setCliente(null);
    this.setPlantacion(null);
    this.setCarguera(null);
    this.setHandler(null);
    this.setEscliente(false);
    this.setEsplantacion(false);
    this.setEscarguera(false);
    this.setEshandler(false);
    this.setEnabled(false);
}

private void setPersona(Persona persona, User usuario){
	this.setId(persona.getId());
    this.setNombre(persona.getNombre());
    this.setSegnombre(persona.getSegnombre());
    this.setApellido(persona.getApellido());
    this.setSegapellido(persona.getSegapellido());
    this.setCel1(persona.getCel1());
    this.setCel2(persona.getCel2());
    this.setDireccion(persona.getDireccion());
    this.setEmail(persona.getEmail());
    this.setMessenger(persona.getMessenger());
    this.setNumdoc(persona.getNumdoc());
    this.setSkype(persona.getSkype());
    this.setTel1(persona.getTel1());
    this.setTel2(persona.getTel2());
    this.setTipodoc(persona.getTipodoc());
    this.setTipopersona(persona.getTipopersona());
    this.setUsername(persona.getUsername());
    this.setPassword(usuario.getPassword());
    this.setEmpleado(persona.getEmpleado());
    this.setCargo(persona.getCargo());
    this.setRol(usuario.getRole().getId());
    this.setCargo(persona.getCargo());
    this.setEmpleado(persona.getEmpleado().booleanValue());
    this.setEscliente(persona.isCliente());
    this.setEsplantacion(persona.isPlantacion());
    this.setEscarguera(persona.isAgencia());
    this.setEshandler(persona.isHandler());
    this.setCliente(persona.getIdCliente());
    this.setPlantacion(persona.getIdPlantacion());
    this.setCarguera(persona.getIdAgencia());
    this.setHandler(persona.getIdHandler());
    
    
    
    this.setEnabled(usuario.isEnabled());
    this.usernameaux = usuario.getLogin();
    
    
   
   }



public String getApellido() {
	return apellido;
}

public void setApellido(String apellido) {
	this.apellido = apellido;
}

public String getSegapellido() {
	return segapellido;
}

public void setSegapellido(String segapellido) {
	this.segapellido = segapellido;
}



public Integer getTipodoc() {
	return tipodoc;
}

public void setTipodoc(Integer tipodoc) {
	this.tipodoc = tipodoc;
}

public String getNumdoc() {
	return numdoc;
}

public void setNumdoc(String numdoc) {
	this.numdoc = numdoc;
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

public String getMessenger() {
	return messenger;
}

public void setMessenger(String messenger) {
	this.messenger = messenger;
}

public String getSkype() {
	return skype;
}

public void setSkype(String skype) {
	this.skype = skype;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getDireccion() {
	return direccion;
}

public void setDireccion(String direccion) {
	this.direccion = direccion;
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

public Integer getTipopersona() {
	return tipopersona;
}

public void setTipopersona(Integer tipopersona) {
	this.tipopersona = tipopersona;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public boolean validar(){
	boolean ok = true;
	
		if (!Validador.esLongitudCorrecta(this.nombre, 1, 45)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nombre: "," Debe tener entre 1 y 45 caracteres" ));
			ok = false;
		}
	   	if (!Validador.esEntero(this.numdoc)){
	   		FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Número de Documento : "," Debe ser Numérico " ));
			ok = false;
	   		
	   	}
	   	if (!Validador.esCorreoCorrecto(this.email)){
	   		FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Correo Electrónico : "," Debe ser un formato de email valido " ));
			ok = false;
	   		
	   	}
	   	
		if(!getValidarChecks()){
	   		FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Tipo de usuario : "," Verifique el tipo de usuario" ));
			ok = false;
	   	}
	   	
	  /*	if (userService.getUser(this.username) != null){
	   		FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Usuario : "," Ya existe el usuario, cambielo por favor" ));
			ok = false;
	   	}*/

	return ok;
}

public List<Dominio> getTiposDocumento() {
	return tiposDocumento;
}

public void setTiposDocumento(List<Dominio> tiposDocumento) {
	this.tiposDocumento = tiposDocumento;
}

public List<Dominio> getTiposPersona() {
	return tiposPersona;
}

public void setTiposPersona(List<Dominio> tiposPersona) {
	this.tiposPersona = tiposPersona;
}

public DominioService getDominioService() {
	return dominioService;
}

public void setDominioService(DominioService dominioService) {
	this.dominioService = dominioService;
}


public List<SelectItem> getListaTipopersonaDom() {
	return listaTipopersonaDom;
}

public void setListaTipopersonaDom(List<SelectItem> listaTipopersonaDom) {
	this.listaTipopersonaDom = listaTipopersonaDom;
}

public Integer getRol() {
	return rol;
}

public void setRol(Integer rol) {
	this.rol = rol;
}

public List<SelectItem> getListaRolDom() {
	return listaRolDom;
}

public void setListaRolDom(List<SelectItem> listaRolDom) {
	this.listaRolDom = listaRolDom;
}



public boolean isEmpleado() {
	return empleado;
}

public void setEmpleado(boolean empleado) {
	this.empleado = empleado;
}

public Integer getCargo() {
	return cargo;
}

public void setCargo(Integer cargo) {
	this.cargo = cargo;
}

public List<SelectItem> getListaCargosDom() {
	return listaCargosDom;
}

public void setListaCargosDom(List<SelectItem> listaCargosDom) {
	this.listaCargosDom = listaCargosDom;
}



public boolean isEnabled() {
	return enabled;
}

public void setEnabled(boolean enabled) {
	this.enabled = enabled;
}

public UserService getUserService() {
	return userService;
}

public void setUserService(UserService userService) {
	this.userService = userService;
}

public RoleService getRoleService() {
	return roleService;
}

public void setRoleService(RoleService roleService) {
	this.roleService = roleService;
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

public Boolean getEscliente() {
	return escliente;
}

public void setEscliente(Boolean escliente) {
	this.escliente = escliente;
}

public Boolean getEsplantacion() {
	return esplantacion;
}

public void setEsplantacion(Boolean esplantacion) {
	this.esplantacion = esplantacion;
}

public Boolean getEscarguera() {
	return escarguera;
}

public void setEscarguera(Boolean escarguera) {
	this.escarguera = escarguera;
}

public Integer getCliente() {
	return cliente;
}

public void setCliente(Integer cliente) {
	this.cliente = cliente;
}

public Integer getPlantacion() {
	return plantacion;
}

public void setPlantacion(Integer plantacion) {
	this.plantacion = plantacion;
}

public Integer getCarguera() {
	return carguera;
}

public void setCarguera(Integer carguera) {
	this.carguera = carguera;
}

public String getUsernameaux() {
	return usernameaux;
}

public void setUsernameaux(String usernameaux) {
	this.usernameaux = usernameaux;
}

public List<String> getListaparam() {
	return listaparam;
}

public void setListaparam(List<String> listaparam) {
	this.listaparam = listaparam;
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

public List<SelectItem> getListaAgenciasSelect() {
	return listaAgenciasSelect;
}

public void setListaAgenciasSelect(List<SelectItem> listaAgenciasSelect) {
	this.listaAgenciasSelect = listaAgenciasSelect;
}

public List<Role> getTiposRol() {
	return tiposRol;
}

public void setTiposRol(List<Role> tiposRol) {
	this.tiposRol = tiposRol;
}

public List<Dominio> getListaCargos() {
	return listaCargos;
}

public void setListaCargos(List<Dominio> listaCargos) {
	this.listaCargos = listaCargos;
}

public static List<SelectItem> rolesToSelectdItems(List <Role> lista){
	
	List<SelectItem> generico = new ArrayList<SelectItem>();
	generico = new ArrayList<SelectItem>();
	for(Role rol : lista){
	   generico.add(new SelectItem(rol.getId(), rol.getRole()));
	}
	return generico;
}



public Boolean getEshandler() {
	return eshandler;
}

public void setEshandler(Boolean eshandler) {
	this.eshandler = eshandler;
}

public Integer getHandler() {
	return handler;
}

public void setHandler(Integer handler) {
	this.handler = handler;
}

public List<SelectItem> getListaHandlersSelect() {
	return listaHandlersSelect;
}

public void setListaHandlersSelect(List<SelectItem> listaHandlersSelect) {
	this.listaHandlersSelect = listaHandlersSelect;
}

private String abrirConfirmacion(){
	
	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirusuario"));
	//FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Confirmación", "Confirmación");
	//RequestContext.getCurrentInstance().showMessageInDialog(message);
	PrimeFaces.current().dialog().showMessageDynamic(message);
	Map mapsesion = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	mapsesion.remove("editarUsuarioMB");
	return "buscarusuario";

}

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

public void actualizarcheckempleado(){
	
	if(this.isEmpleado()){
		this.setEsplantacion(false);
		this.setEscarguera(false);
		this.setEscliente(false);
		this.setEshandler(false);
		
		this.setCliente(null);
		this.setPlantacion(null);
		this.setCarguera(null);
		this.setHandler(null);
	}
}

public void actualizarcheckcliente(){
	if(this.getEscliente()){
		this.setEsplantacion(false);
		this.setEscarguera(false);
		this.setEmpleado(false);
		
		this.setCargo(null);
		this.setPlantacion(null);
		this.setCarguera(null);
	}
}

public void actualizarcheckplantacion(){
	
	if(this.getEsplantacion()){
		this.setEscliente(false);
		this.setEscarguera(false);
		this.setEmpleado(false);
		
		this.setCargo(null);
		this.setCliente(null);
		this.setCarguera(null);
		
	}
}

public void actualizarcheckcarguera(){
	if(this.getEscarguera()){
		this.setEsplantacion(false);
		this.setEscliente(false);
		this.setEmpleado(false);
		
		this.setCargo(null);
		this.setPlantacion(null);
		this.setCliente(null);
		
	}
}

public void actualizarcheckhandler(){
	if(this.getEshandler()){
		this.setEsplantacion(false);
		this.setEscliente(false);
		this.setEmpleado(false);
		this.setEscarguera(false);
		
		this.setCargo(null);
		this.setPlantacion(null);
		this.setCliente(null);
		this.setCarguera(null);
		
	}
}
public boolean getValidarChecks(){
	
	boolean ok = true;
	int contadorcheck = 0;
	int contadortipos = 0;
	
	if(this.getEscliente() && this.getCliente() == null){
		ok=false;
		
	}else if(this.getEsplantacion() && this.getPlantacion()== null){
		ok=false;
		
	}else if(this.getEscarguera() && this.getCarguera()== null){
		ok=false;
		
	}else if(this.isEmpleado() && this.getCargo()== null){
		ok=false;
	}
	
	
	if(this.getEscliente()){
		contadorcheck++;
		
	}
	
	if(this.getEsplantacion()){
		contadorcheck++;
		
	}
	
	if(this.getEscarguera()){
		contadorcheck++;
		
	}
	
	if(this.isEmpleado()){
		contadorcheck++;
	}
	
	if(contadorcheck >1){
		ok=false;
	}
	
	if(this.getCliente()!= null){
		contadortipos++;
		
	}
	
	if(this.getCargo()!= null){
		contadortipos++;
		
	}
	
	if(this.getPlantacion()!= null){
		contadortipos++;
		
	}
	

	if(this.getCarguera()!= null){
		contadortipos++;
		
	}
	
	if(contadortipos > 1){
		ok=false;
	}
	
	return ok;
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
	  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("editarUsuarioMB");
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
