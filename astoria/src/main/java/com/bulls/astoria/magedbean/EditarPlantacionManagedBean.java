package com.bulls.astoria.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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

import org.hibernate.Query;
import org.primefaces.PrimeFaces;
//import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.bulls.astoria.enumeracion.EnuDominio;
import com.bulls.astoria.persistence.Cliente;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.Plantacion;
import com.bulls.astoria.persistence.PlantacionPacking;
import com.bulls.astoria.persistence.PlantacionProducto;
import com.bulls.astoria.persistence.Proveedor;
import com.bulls.astoria.pojo.Grado;
import com.bulls.astoria.pojo.Producto;
import com.bulls.astoria.service.DominioService;
import com.bulls.astoria.service.PlantacionService;
import com.bulls.astoria.utils.Convertidor;
import com.bulls.astoria.utils.Validador;


@ManagedBean (name="editarPlantacionMB")
@SessionScoped
public class EditarPlantacionManagedBean extends GeneralManagedBean implements Serializable{

	
	private Integer id;
	private Integer idproveedor;
	private String codigo;
	private String nit;
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
	
	
	private Integer idProducto;
	private String recomendado;
	private Integer cantidad;
	private String idGrado;
	private Plantacion plantacionEditar;
	private Integer cantidadxfull;
	
	private Integer padre;
	private Integer variedad;
	
	
	List <Dominio> paises;
	List <Dominio> ciudades;
	List <SelectItem> listaPaisesDom;
	List <SelectItem> listaCiudadesDom;
	List <SelectItem> listaProductosSelect;
	List <SelectItem> listaGradosSelect;
	List <Producto> listaProductos = new ArrayList <Producto>();
	List <Map> listaProductosMap;
	//List <Dominio> listaGrados;
	List <Grado> listaGradosFinal;
	List <Plantacion> listaPlantaciones;
	
	private List<Producto> selectedProductos;
	private List<Dominio> selectedGrados;
	private List <Dominio> listaRecomendados ;
	
	transient ResourceBundle bundle ;
	
	List <Producto> productos;
	List <Producto> productos2;
	List <Producto> productos2filtrado;
	private Producto selectedProducto;
	
	List <Dominio> tiposFlor;
	List<SelectItem> listaTiposFlor;
	List<SelectItem> listaVariedades;
	List<Proveedor> proveedores;
	
	
	@ManagedProperty(value="#{DominioService}")
	DominioService dominioService;
	
	@ManagedProperty(value="#{PlantacionService}")
	PlantacionService plantacionService;
	
	
	
	@PostConstruct
	public void EditarPlantacionManagedBean(){
		
		borrarSession();
		
		bundle =  ResourceBundle.getBundle("messages");
		
		
		paises = dominioService.getDominios(EnuDominio.PAISES.getIdTipoDominio());
		ciudades = dominioService.getDominios(EnuDominio.CIUDADES.getIdTipoDominio());
		listaPaisesDom = Convertidor.dominiosToSelectdItems(paises);
		tiposFlor = dominioService.getDominios(EnuDominio.FLORES.getIdTipoDominio());
		//listaCiudadesDom = Convertidor.dominiosToSelectdItems(ciudades);
		//listaProductosMap = dominioService.getProductos();
		//listaProductos= Convertidor.mapProductoToProducto(listaProductosMap);
		//listaProductosSelect = Convertidor.mapToSelectdItems(listaProductosMap);
		//listaGrados = dominioService.getDominios(EnuDominio.GRADOS.getIdTipoDominio());		
		//listaGradosSelect = Convertidor.dominiosToSelectdItems(listaGrados);
		//listaRecomendados = dominioService.getDominios(EnuDominio.RECOMIENDA.getIdTipoDominio());
		listaPlantaciones = plantacionService.getPlantaciones();
		
		
		//INICIO NUEVO
			//	listaProductosMap = dominioService.getProductosCompleto();
			//	productos = getProductosmap(listaProductosMap);
			//	productos2 = getProductosmap(listaProductosMap);
			//	listaProductosSelect = Convertidor.ProductoPojoToSelectdItems(productos);
				//FIN NUEVO
		listaTiposFlor = Convertidor.dominiosToSelectdItems(tiposFlor);
		proveedores = plantacionService.getProveedores();

	}
	
    public void getCiudadesPais(){

    	ciudades= dominioService.getDominiosXPadre(this.pais);
    	listaCiudadesDom = Convertidor.dominiosToSelectdItems(ciudades);
    }
    
    public void crearPlantacion(){
    	
    	Plantacion plantacion = new Plantacion();
    	plantacion.setIdproveedor(this.idproveedor);
    	plantacion.setCel1(this.cel1);
    	plantacion.setCel2(this.cel2);
    	plantacion.setCiudad(this.ciudad);
    	plantacion.setCodigo(this.codigo);
    	plantacion.setContactoinvoice(this.contactoinvoice);
    	plantacion.setContactoprop(this.contactoprop);
    	plantacion.setContactoven(this.contactoven);
    	plantacion.setCorreo1(this.correo1);
    	plantacion.setCorreo2(this.correo2);
    	plantacion.setCupo(this.cupo);
    	plantacion.setDesccupo(this.desccupo);
    	plantacion.setDescsaldofinal(this.descsaldofinal);
    	plantacion.setDireccion(this.direccion);
    	plantacion.setFax(this.fax);
    	//plantacion.setGrados(this.grados);
    	plantacion.setNit(this.nit);
    	plantacion.setNombre(this.nombre);
    	plantacion.setObservaciones(this.observaciones);
    	plantacion.setPais(this.pais);
    	//plantacion.setProductos(this.productos);
    	plantacion.setSaldofinal(this.saldofinal);
    	plantacion.setSaldoinicial(this.saldoinicial);
    	plantacion.setSkypealterno(this.skypealterno);
    	plantacion.setSkypeprop(this.skypeprop);
    	plantacion.setSkypeven(this.skypeven);
    	plantacion.setTel1(this.tel1);
    	plantacion.setTel2(this.tel2);
    	plantacion.setWeb(this.web);
    	plantacion.setCantidadxfull(this.cantidadxfull);
    	
    	
    }
    
    public List getProductos(){
    	return dominioService.getProductos();
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
	public String getNit() {
		return nit;
	}
	public void setNit(String nit) {
		this.nit = nit;
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
	public DominioService getDominioService() {
		return dominioService;
	}
	public void setDominioService(DominioService dominioService) {
		this.dominioService = dominioService;
	}
	public String getContactoven() {
		return contactoven;
	}
	public void setContactoven(String contactoven) {
		this.contactoven = contactoven;
	}

	public List<Producto> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public List<Map> getListaProductosMap() {
		return listaProductosMap;
	}

	public void setListaProductosMap(List<Map> listaProductosMap) {
		this.listaProductosMap = listaProductosMap;
	}

	public List<Producto> getSelectedProductos() {
		return selectedProductos;
	}

	public void setSelectedProductos(List<Producto> selectedProductos) {
		this.selectedProductos = selectedProductos;
	}

	
	public List<Dominio> getSelectedGrados() {
		return selectedGrados;
	}

	public void setSelectedGrados(List<Dominio> selectedGrados) {
		this.selectedGrados = selectedGrados;
	}

	public List<Dominio> getListaRecomendados() {
		return listaRecomendados;
	}

	public void setListaRecomendados(List<Dominio> listaRecomendados) {
		this.listaRecomendados = listaRecomendados;
	}

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public String getRecomendado() {
		return recomendado;
	}

	public void setRecomendado(String recomendado) {
		this.recomendado = recomendado;
	}

	public List<SelectItem> getListaProductosSelect() {
		return listaProductosSelect;
	}

	public void setListaProductosSelect(List<SelectItem> listaProductosSelect) {
		this.listaProductosSelect = listaProductosSelect;
	}
	
	
	
	public PlantacionService getPlantacionService() {
		return plantacionService;
	}

	public void setPlantacionService(PlantacionService plantacionService) {
		this.plantacionService = plantacionService;
	}
	
	

	public Integer getPadre() {
		return padre;
	}

	public void setPadre(Integer padre) {
		this.padre = padre;
	}

	public Integer getVariedad() {
		return variedad;
	}

	public void setVariedad(Integer variedad) {
		this.variedad = variedad;
	}

	public void subir(){
		System.out.println("SUBIENDOOOOO::::");
		if(this.idProducto != null){
				
		if(validarSubirProducto()){
			
			Dominio dominio = dominioService.getDominio(this.idProducto);
			Producto producto = new Producto(dominio.getDominiopadre(),"",this.idProducto,dominio.getNomcorto());
			producto.setRecomendado(this.recomendado);
			listaProductos.add(producto);
		}	
		}
	}
	
	public void subirGrado(){
		System.out.println("SUBIENDOOOOO::::");
		if(this.idGrado != null){
				
		if(validarSubirGrado()){
			
			Dominio dominio = dominioService.getDominio(new Integer(this.idGrado));
			Grado grado = new Grado(new Integer(this.idGrado), dominio.getNomcorto(),dominio.getNomlargo(), dominio.getDescripcion(), this.cantidad);
			listaGradosFinal.add(grado);
		}	
		}
	}
	
	
	
	public boolean validarSubirProducto(){
		boolean ok = true;
		
		for (Producto pro :listaProductos){
			if(pro.getIdProducto().intValue() == this.idProducto.intValue() ){
				listaProductos.remove(pro);
				ok=false;
				break;
			}
		}
		return ok;
	}
	
	
	public boolean validarSubirGrado(){
		boolean ok = true;
		if (!Validador.esEntero(Integer.toString(this.cantidad))){
	   		FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Cantidad: "," Debe ser Número entero " ));
			ok = false;
			return ok;
	   		
	   	}
		for (Grado gra :listaGradosFinal){
			if(gra.getId().intValue() == new Integer(this.idGrado).intValue() ){
				ok=false;
				break;
			}
		}
		return ok;
	}
	
	public void eliminar(Producto producto){
		System.out.println("ELIMINANNNNDDDDDOOOOO::::");
		
		for (Producto pro :listaProductos){
			/*if(pro.getIdProducto().intValue() == producto.getIdProducto().intValue() ){
				listaProductos.remove(pro);
				break;
			}*/
			
			if(pro.getId().intValue() == producto.getId().intValue() ){
				listaProductos.remove(pro);
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), "El producto se elimino correctamente");
				//RequestContext.getCurrentInstance().showMessageInDialog(message);
				PrimeFaces.current().dialog().showMessageDynamic(message);
				break;
			}
		}
	}
	
	public void eliminarGrado(Grado grado){
		System.out.println("ELIMINANNNNDDDDDOOOOO::::");
		
		for (Grado gra :listaGradosFinal){
			if(gra.getId().intValue() == grado.getId().intValue() ){
				listaGradosFinal.remove(gra);
				break;
			}
		}
	}
	
	public List <com.bulls.astoria.persistence.Producto> productoToDominio(List <Producto> listaProductos){

		List <com.bulls.astoria.persistence.Producto> lista = new ArrayList<com.bulls.astoria.persistence.Producto>();
		for (Producto pro :listaProductos){
			com.bulls.astoria.persistence.Producto propersitir = new com.bulls.astoria.persistence.Producto();
			propersitir.setId(pro.getId());
			propersitir.setRecomendado(pro.getRecomendado());
			lista.add(propersitir);
		}
		return lista;
		
	}
	
	public List <Dominio> gradoToDominio(List <Grado> listaGrados){
		System.out.println("en aceptar");
		List <Dominio> listaDominio = new ArrayList<Dominio>();
		for (Grado gra :listaGrados){
			Dominio dominio = dominioService.getDominio(gra.getId());
			listaDominio.add(dominio);
		}
		return listaDominio;
		
	}
	
	public List <Producto> dominioToProducto(List <Dominio> listaProductos){
		List <Producto> lista = new ArrayList<Producto>();
		for (Dominio dom :listaProductos){
			Producto pro = new Producto (null,null,dom.getId(),dom.getNomcorto(),null,true);
			lista.add(pro);
		}
		return lista;
		
	}
	
	public List <Producto> plantacionProductoToProducto(List <PlantacionProducto> listaProductos){
		List <Producto> lista = new ArrayList<Producto>();
		for (PlantacionProducto pro :listaProductos){
			com.bulls.astoria.persistence.Producto productoper = dominioService.getProductoXId(pro.getIdproducto());
			Dominio dom = dominioService.getDominio(productoper.getIdvariedad());
			Dominio dompadre = dominioService.getDominio(dom.getDominiopadre());
			Dominio domgrado = dominioService.getDominio(productoper.getIdgrado());
			Producto producto = new Producto (productoper.getId(),dompadre.getId(),dompadre.getNomcorto(),productoper.getIdvariedad(),dom.getNomcorto(),domgrado.getNomcorto(),Character.toString(pro.getCaracteristica()),pro.isEstado());
			lista.add(producto);
		}
		return lista;
		
	}
	
	public List <Grado> dominioToGrado(List <Dominio> listaGrados){
		List <Grado> lista = new ArrayList<Grado>();
		for (Dominio dom :listaGrados){
			Grado grado = new Grado (dom.getId(),dom.getNomcorto(),dom.getNomlargo(),dom.getDescripcion(),null);
			lista.add(grado);
		}
		return lista;
		
	}
	
	public List <Grado> plantacionPackingToGrado(List <PlantacionPacking> listaGrados){
		List <Grado> lista = new ArrayList<Grado>();
		for (PlantacionPacking packing :listaGrados){
			Dominio dom = dominioService.getDominio(packing.getIdgrado());
			Grado grado = new Grado (dom.getId(),dom.getNomcorto(),dom.getNomlargo(),dom.getDescripcion(),packing.getCantidad());
			lista.add(grado);
		}
		return lista;
		
	}
	
	
	public Plantacion setPlantacion(){
		Plantacion plantacion = new Plantacion();
		plantacion.setIdproveedor(this.idproveedor);
		plantacion.setCel1(this.cel1);
		plantacion.setCel2(this.cel2);
		plantacion.setCiudad(this.ciudad);
		plantacion.setCodigo(this.codigo);
		plantacion.setContactoinvoice(this.contactoinvoice);
		plantacion.setContactoprop(this.contactoprop);
		plantacion.setContactoven(this.contactoven);
		plantacion.setCorreo1(this.correo1);
		plantacion.setCorreo2(this.correo2);
		plantacion.setCupo(this.cupo);
		plantacion.setDesccupo(this.desccupo);
		plantacion.setDescsaldofinal(this.descsaldofinal);
		plantacion.setDireccion(this.direccion);
		plantacion.setFax(this.fax);
		plantacion.setNit(this.nit);
		plantacion.setNombre(this.nombre);
		plantacion.setObservaciones(this.observaciones);
		plantacion.setPais(this.pais);
		plantacion.setSaldofinal(this.saldofinal);
		plantacion.setSaldoinicial(this.saldoinicial);
		plantacion.setSkypealterno(this.skypealterno);
		plantacion.setSkypeprop(this.skypeprop);
		plantacion.setSkypeven(this.skypeven);
		plantacion.setTel1(this.tel1);
		plantacion.setTel2(this.tel2);
		plantacion.setWeb(this.web);
		plantacion.setCantidadxfull(this.cantidadxfull);
		return plantacion;
	}
	
	private boolean validarPlantacion(){
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
		
		if (this.ciudad == 0){
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
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Vendedor: "," Debe tener entre 1 y 100 caracteres" ));
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
		
		if (!Validador.esLongitudCorrecta(this.nit, 1, 25)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nit"," Debe tener entre 1 y 25 caracteres" ));
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
		
		if(this.pais== 0){
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
	
	public String editarPlantacion (){
		
		List <com.bulls.astoria.persistence.Producto> productos = this.productoToDominio(listaProductos);
		List <Grado> grados = this.listaGradosFinal;
		List <Dominio> listaGradosFinal = this.gradoToDominio(grados);
		Plantacion plantacion = this.setPlantacion();
		plantacion.setId(plantacionEditar.getId());
		if(validarPlantacion()){
			plantacionService.editarPlantacion(plantacion, productos,productos,listaGradosFinal, grados);
			this.putAuditoria("Editar plantación", "U", "Edito plantación así : - " + plantacion.toString());
			listaPlantaciones = plantacionService.getPlantaciones();
			abrirConfirmacion();
			return "listaplantaciones";
		}
		return null;

	}	
	
	
	public void putPlantacionEditar(){
		
        //plantacion editar ya vienne en la session
		this.idproveedor=plantacionEditar.getIdproveedor();
		this.cel1=plantacionEditar.getCel1();
		this.cel2=plantacionEditar.getCel2();
		this.ciudad=plantacionEditar.getCiudad();
		this.codigo=plantacionEditar.getCodigo();
		this.contactoinvoice=plantacionEditar.getContactoinvoice();
		this.contactoprop=plantacionEditar.getContactoprop();
		this.contactoven=plantacionEditar.getContactoven();
		this.correo1=plantacionEditar.getCorreo1();
		this.correo2=plantacionEditar.getCorreo2();
		this.cupo=plantacionEditar.getCupo();
		this.desccupo=plantacionEditar.getDesccupo();
		this.descsaldofinal=plantacionEditar.getDescsaldofinal();
		this.direccion=plantacionEditar.getDireccion();
		this.fax=plantacionEditar.getFax();
		this.nit=plantacionEditar.getNit();
		this.nombre=plantacionEditar.getNombre();
		this.observaciones=plantacionEditar.getObservaciones();
		this.pais=plantacionEditar.getPais();
		this.saldofinal=plantacionEditar.getSaldofinal();
		this.saldoinicial=plantacionEditar.getSaldoinicial();
		this.skypealterno=plantacionEditar.getSkypealterno();
		this.skypeprop=plantacionEditar.getSkypeprop();
		this.skypeven=plantacionEditar.getSkypeven();
		this.tel1=plantacionEditar.getTel1();
		this.tel2=plantacionEditar.getTel2();
		this.web=plantacionEditar.getWeb();
		this.cantidadxfull= plantacionEditar.getCantidadxfull();
		getCiudadesPais();

		//poner lista productos y lista grados
		listaProductos = getProductosPlantacion(plantacionEditar.getId());
		listaGradosFinal = getGradosPlantacion(plantacionEditar.getId());
		
	}
	
		
    public String editar (Plantacion plantacion){
        plantacionEditar = plantacionService.getPlantacion(plantacion.getId());
	    this.putPlantacionEditar();
		return "editarplantacion";
		
	}
    
    List <Producto> getProductosPlantacion(Integer idPlantacion){
    	List <PlantacionProducto> productos =  plantacionService.getProductosPlantacion(idPlantacion);
      	return this.plantacionProductoToProducto(productos);	
    }
    
   List <Grado> getGradosPlantacion(Integer idPlantacion){
    	List <PlantacionPacking> grados = plantacionService.getGradosPlantacion(idPlantacion);
     	return this.plantacionPackingToGrado(grados);
    }
   
   public void onCellEdit(CellEditEvent event) {
       Object oldValue = event.getOldValue();
       Object newValue = event.getNewValue();
        
      System.out.println("viejo valor " + oldValue);
      System.out.println("nuevo valor " + newValue);
      
      for (Grado grado:listaGradosFinal){
    	  System.out.println("Actualizacion de lista : " + grado.getCantidad());
      }
   }
   
   public void getVariedades(){
   	listaVariedades = Convertidor.dominiosToSelectdItems(dominioService.getDominiosXPadre(this.padre));
   	return;
   }
   
   public void getProductos3(){
   	List lista = dominioService.getProductosCompleto(this.variedad);
   	productos2 = getProductosmap(lista);
   }
   
   public void onRowSelect(SelectEvent event) {
	   
	   System.out.println("SUBIENDO OTRO PRODUCTO *******************************************");
	   
    	//String cod  = ((Producto)event.getObject()).getCodigo();
   	//this.codigo= cod;    
   	
   	/*Dominio domproducto = dominioService.getDominio(this.idProducto);
		//Dominio domcolor = dominioService.getDominio(this.idColor);
		//Dominio domgrado = dominioService.getDominio(this.idGrado);
		//Producto producto = new Producto(domproducto.getDominiopadre(),"",this.idProducto,domproducto.getNomcorto(),domcolor.getId(),domcolor.getNomcorto(),domgrado.getId(),domgrado.getNomcorto());
		Producto producto = new Producto(domproducto.getDominiopadre(),"",this.idProducto,domproducto.getNomcorto(),this.recomendado,true);
		producto.setRecomendado(this.recomendado);*/
		Producto producto = (Producto)event.getObject();
		for (Producto pro :listaProductos){
			if(pro.getId().intValue() == producto.getId().intValue()){
				listaProductos.remove(pro);
				break;
			}
		}
		producto.setRecomendado(this.recomendado);
		listaProductos.add(producto);
		/*FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), "Se ha adicionado temporalmente el producto : " + producto.getCodigo());
		RequestContext.getCurrentInstance().showMessageInDialog(message);*/
   }
   
   public Double getSaldoPlantacion(Integer idPlantacion){
	   Double saldo = null;
	   Map mapa = plantacionService.getProveedorPlantacion(idPlantacion);
	   if(mapa != null){
		   Object pp =   mapa.get("saldo");
		   saldo = (Double)pp;
	   }
	   return saldo;
   }
   

   
   public Double getSaldoProveedor(Integer idProveedor){
		Double saldo = 0.0;
		saldo = dominioService.getSaldo(idProveedor, "P","A");
		if(saldo==null){
			saldo = 0.0;
		}
		return saldo;
	}
   
   public String getNombreProveedorPlantacion(Integer idPlantacion){
	   String nombre = null;
	   Map mapa = plantacionService.getProveedorPlantacion(idPlantacion);
	   if(mapa != null){
		   Object pp =   mapa.get("nombre");
		   nombre = (String)pp;
	   }
	   return nombre;
   }

   public void onRowUnselect(UnselectEvent event) {

   }	

	public List<Plantacion> getListaPlantaciones() {
		return listaPlantaciones;
	}

	public void setListaPlantaciones(List<Plantacion> listaPlantaciones) {
		this.listaPlantaciones = listaPlantaciones;
	}

	public Plantacion getPlantacionEditar() {
		return plantacionEditar;
	}

	public void setPlantacionEditar(Plantacion plantacionEditar) {
		this.plantacionEditar = plantacionEditar;
	}

	public List<Grado> getListaGradosFinal() {
		return listaGradosFinal;
	}

	public void setListaGradosFinal(List<Grado> listaGradosFinal) {
		this.listaGradosFinal = listaGradosFinal;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getIdGrado() {
		return idGrado;
	}

	public void setIdGrado(String idGrado) {
		this.idGrado = idGrado;
	}

	public List<SelectItem> getListaGradosSelect() {
		return listaGradosSelect;
	}

	public void setListaGradosSelect(List<SelectItem> listaGradosSelect) {
		this.listaGradosSelect = listaGradosSelect;
	}
	
	
	
	 public Integer getCantidadxfull() {
		return cantidadxfull;
	}

	public void setCantidadxfull(Integer cantidadxfull) {
		this.cantidadxfull = cantidadxfull;
	}

	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}
    
	public List<Producto> getProductos2() {
		return productos2;
	}

	public void setProductos2(List<Producto> productos2) {
		this.productos2 = productos2;
	}

	public List<Producto> getProductos2filtrado() {
		return productos2filtrado;
	}

	public void setProductos2filtrado(List<Producto> productos2filtrado) {
		this.productos2filtrado = productos2filtrado;
	}

	public Producto getSelectedProducto() {
		return selectedProducto;
	}

	public void setSelectedProducto(Producto selectedProducto) {
		this.selectedProducto = selectedProducto;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	
	public List<Dominio> getTiposFlor() {
		return tiposFlor;
	}

	public void setTiposFlor(List<Dominio> tiposFlor) {
		this.tiposFlor = tiposFlor;
	}

	public List<SelectItem> getListaTiposFlor() {
		return listaTiposFlor;
	}

	public void setListaTiposFlor(List<SelectItem> listaTiposFlor) {
		this.listaTiposFlor = listaTiposFlor;
	}

	public List<SelectItem> getListaVariedades() {
		return listaVariedades;
	}

	public void setListaVariedades(List<SelectItem> listaVariedades) {
		this.listaVariedades = listaVariedades;
	}
	
	

	public Integer getIdproveedor() {
		return idproveedor;
	}

	public void setIdproveedor(Integer idproveedor) {
		this.idproveedor = idproveedor;
	}

	public List<Proveedor> getProveedores() {
		return proveedores;
	}

	public void setProveedores(List<Proveedor> proveedores) {
		this.proveedores = proveedores;
	}

	private void abrirConfirmacion(){
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmaeditarplantacion"));
			//RequestContext.getCurrentInstance().showMessageInDialog(message);
			PrimeFaces.current().dialog().showMessageDynamic(message);
			return;

	}
	private void abrirConfirmacionProducto(){
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmaeditarplantacion"));
		//RequestContext.getCurrentInstance().showMessageInDialog(message);
		PrimeFaces.current().dialog().showMessageDynamic(message);
		return;

}
	
	public void nada(){
		
		
		return;

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
				Producto productosalida = new Producto (procod.getId(),flor,nomflor,variedad,nomvariedad,grado,nomgrado,codigo,enabled);
				productossalida.add(productosalida);

			}
			return productossalida;
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
			  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("editarPlantacionMB");
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

