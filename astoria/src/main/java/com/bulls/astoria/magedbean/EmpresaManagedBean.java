package com.bulls.astoria.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
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

import com.bulls.astoria.enumeracion.EnuDominio;
import com.bulls.astoria.persistence.Aerolinea;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.Empresa;
import com.bulls.astoria.service.DominioService;
import com.bulls.astoria.utils.Convertidor;
import com.bulls.astoria.utils.Validador;



@ManagedBean (name="empresaMB")
@SessionScoped
public class EmpresaManagedBean extends GeneralManagedBean implements Serializable{
	
	private Integer id;
	private String prefijo;
	private String nombre;
	private String nit;
	private String ib;
	private String bb;
	private String fb;
	private String direccionib;
	private String swiftib;
	private String cuentaib;
	private String direccionbb;
	private String swiftbb;
	private String direccionfb;
	private String cuentafb;
	private String direccion;
	private String telefono;
	private String celular;
	private String web;
	private String correo;
	private String fax;
	private String ciudadpais;
	private String replegal;
	
	
	private Empresa empresa;
	transient ResourceBundle bundle ;
	
	
	@ManagedProperty(value="#{DominioService}")
	DominioService dominioService;
	
	
	
	@PostConstruct
	public void EmpresaManagedBean(){
		
		borrarSession();
		bundle =  ResourceBundle.getBundle("messages");
		empresa= dominioService.getEmpresa();
		getEmpresa(empresa);

	}
	private void getEmpresa(Empresa empresa){
		
		if(empresa != null){
		
		this.bb=empresa.getBb();
		this.celular=empresa.getCelular();
		this.ciudadpais=empresa.getCiudadpais();
		this.correo=empresa.getCorreo();
		this.cuentafb=empresa.getCuentafb();
		this.cuentaib=empresa.getCuentaib();
		this.direccion=empresa.getDireccion();
		this.direccionbb=empresa.getDireccionbb();
		this.direccionfb=empresa.getDireccionfb();
		this.direccionib=empresa.getDireccionib();
		this.fax=empresa.getFax();
		this.fb=empresa.getFb();
		this.ib=empresa.getIb();
		this.id=empresa.getId();
		this.nit=empresa.getNit();
		this.nombre=empresa.getNombre();
		this.prefijo=empresa.getPrefijo();
		this.replegal=empresa.getReplegal();
		this.swiftbb=empresa.getSwiftbb();
		this.swiftib=empresa.getSwiftib();
		this.telefono=empresa.getTelefono();
		this.web=empresa.getWeb();
		}
		
		return;
	}
	
	
	
	public String crearEmpresa(){

		empresa =  new Empresa(); 
		if(validarCrear()){
			empresa.setBb(this.bb);
			empresa.setCelular(this.celular);
			empresa.setCiudadpais(this.ciudadpais);
			empresa.setCorreo(this.correo);
			empresa.setCuentafb(this.cuentafb);
			empresa.setCuentaib(this.cuentaib);
			empresa.setDireccion(this.direccion);
			empresa.setDireccionbb(this.direccionbb);
			empresa.setDireccionfb(this.direccionfb);
			empresa.setDireccionib(this.direccionib);
			empresa.setFax(this.fax);
			empresa.setFb(this.fb);
			empresa.setIb(this.ib);
			empresa.setId(this.id);
			empresa.setNit(this.nit);
			empresa.setNombre(this.nombre);
			empresa.setPrefijo(this.prefijo);
			empresa.setReplegal(this.replegal);
			empresa.setSwiftbb(this.swiftbb);
			empresa.setSwiftib(this.swiftib);
			empresa.setTelefono(this.telefono);
			empresa.setWeb(this.web);
			
			dominioService.editEmpresa(empresa);
			this.putAuditoria("Editar", "U", "Edito la empresa así : - " + empresa.toString());
			abrirConfirmacion();
		}
		return null;
	}
	
	public boolean validarCrear(){
		  boolean ok = true;
		  
		  if(this.nombre.equalsIgnoreCase("")){
     		  ok = false;
			  FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nombre","Debe digitar un nombre para la empresa" ));
			  
		  }
		  if(this.prefijo.equalsIgnoreCase("")){
     		  ok = false;
			  FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Prefijo","Debe dijitar un prefijo para la empresa" ));
			  
		  }
		  
		  return ok;
	  }
	
	private void abrirConfirmacion(){
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmacrearempresa"));
		//RequestContext.getCurrentInstance().showMessageInDialog(message);
		PrimeFaces.current().dialog().showMessageDynamic(message);
		return;

    }



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getPrefijo() {
		return prefijo;
	}



	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getNit() {
		return nit;
	}



	public void setNit(String nit) {
		this.nit = nit;
	}



	public String getIb() {
		return ib;
	}



	public void setIb(String ib) {
		this.ib = ib;
	}



	public String getBb() {
		return bb;
	}



	public void setBb(String bb) {
		this.bb = bb;
	}



	public String getFb() {
		return fb;
	}



	public void setFb(String fb) {
		this.fb = fb;
	}



	public String getDireccionib() {
		return direccionib;
	}



	public void setDireccionib(String direccionib) {
		this.direccionib = direccionib;
	}



	public String getSwiftib() {
		return swiftib;
	}



	public void setSwiftib(String swiftib) {
		this.swiftib = swiftib;
	}



	public String getCuentaib() {
		return cuentaib;
	}



	public void setCuentaib(String cuentaib) {
		this.cuentaib = cuentaib;
	}



	public String getDireccionbb() {
		return direccionbb;
	}



	public void setDireccionbb(String direccionbb) {
		this.direccionbb = direccionbb;
	}



	public String getSwiftbb() {
		return swiftbb;
	}



	public void setSwiftbb(String swiftbb) {
		this.swiftbb = swiftbb;
	}



	public String getDireccionfb() {
		return direccionfb;
	}



	public void setDireccionfb(String direccionfb) {
		this.direccionfb = direccionfb;
	}



	public String getCuentafb() {
		return cuentafb;
	}



	public void setCuentafb(String cuentafb) {
		this.cuentafb = cuentafb;
	}



	public String getDireccion() {
		return direccion;
	}



	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}



	public String getTelefono() {
		return telefono;
	}



	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}



	public String getCelular() {
		return celular;
	}



	public void setCelular(String celular) {
		this.celular = celular;
	}



	public String getWeb() {
		return web;
	}



	public void setWeb(String web) {
		this.web = web;
	}



	public String getCorreo() {
		return correo;
	}



	public void setCorreo(String correo) {
		this.correo = correo;
	}



	public String getFax() {
		return fax;
	}



	public void setFax(String fax) {
		this.fax = fax;
	}



	public String getCiudadpais() {
		return ciudadpais;
	}



	public void setCiudadpais(String ciudadpais) {
		this.ciudadpais = ciudadpais;
	}



	public String getReplegal() {
		return replegal;
	}



	public void setReplegal(String replegal) {
		this.replegal = replegal;
	}



	public Empresa getEmpresa() {
		return empresa;
	}



	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
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
		  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("empresaMB");
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
		  // FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuarioMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("variedadMB");

	   }
	
}
