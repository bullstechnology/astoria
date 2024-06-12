package com.bulls.astoria.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.hibernate.mapping.Array;
import org.primefaces.PrimeFaces;
//import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.bulls.astoria.enumeracion.EnuDominio;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.Plantacion;
import com.bulls.astoria.persistence.Proveedor;
import com.bulls.astoria.pojo.Producto;
import com.bulls.astoria.service.DominioService;
import com.bulls.astoria.service.PlantacionService;
import com.bulls.astoria.utils.Convertidor;
import com.bulls.astoria.utils.Validador;


@ManagedBean (name="plantacionMB")
@SessionScoped
public class PlantacionMagedBean extends GeneralManagedBean implements Serializable{
	
	
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
	private String recomendado = "R";
	
	private Integer cantidadxfull;
	private Integer padre;
	private Integer variedad;
	
	
	/*private Integer idColor;
	private Integer idGrado;
	*/
	
	List <Dominio> paises;
	List <Dominio> ciudades;
	//List <Dominio> grados;
	//List <Dominio> colores;
	List <SelectItem> listaPaisesDom;
	List <SelectItem> listaCiudadesDom;
	List <SelectItem> listaProductosSelect;
	//List <SelectItem> listaGradosSelect;
	//List <SelectItem> listaColoresSelect;
	List <Producto> listaProductos = new ArrayList <Producto>();
	List <Map> listaProductosMap;
	//List <Dominio> listaGrados;
	
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
	List <Proveedor> proveedores;
	
	
	@ManagedProperty(value="#{DominioService}")
	DominioService dominioService;
	
	@ManagedProperty(value="#{PlantacionService}")
	PlantacionService plantacionService;
	
	
	
	@PostConstruct
	public void PlantacionManagedBean(){
		
		borrarSession();
		bundle =  ResourceBundle.getBundle("messages");
		paises = dominioService.getDominios(EnuDominio.PAISES.getIdTipoDominio());
		ciudades = dominioService.getDominios(EnuDominio.CIUDADES.getIdTipoDominio());
		tiposFlor = dominioService.getDominios(EnuDominio.FLORES.getIdTipoDominio());
		//colores = dominioService.getDominios(EnuDominio.COLORES.getIdTipoDominio());
		//grados = dominioService.getDominios(EnuDominio.GRADOS.getIdTipoDominio());
		listaPaisesDom = Convertidor.dominiosToSelectdItems(paises);
		//listaColoresSelect = Convertidor.dominiosToSelectdItems(paises);
		//listaGradosSelect = Convertidor.dominiosToSelectdItems(paises);
		//listaCiudadesDom = Convertidor.dominiosToSelectdItems(ciudades);
		// es el que esta funcionando *****************  listaProductosMap = dominioService.getProductos();
		//listaProductos= Convertidor.mapProductoToProducto(listaProductosMap);
		// es el que esta funcionando *****************  listaProductosSelect = Convertidor.mapToSelectdItems(listaProductosMap);
		
		//INICIO NUEVO
		// estaba bien    listaProductosMap = dominioService.getProductosCompleto();
		// estaba bien   productos = getProductosmap(listaProductosMap);
		// estaba bien   productos2 = getProductosmap(listaProductosMap);
		// estaba bien   listaProductosSelect = Convertidor.ProductoPojoToSelectdItems(productos);
		selectedGrados = new ArrayList<Dominio>();
		listaTiposFlor = Convertidor.dominiosToSelectdItems(tiposFlor);
		//FIN NUEVO
		
		
		//listaGrados = dominioService.getDominios(EnuDominio.GRADOS.getIdTipoDominio());		
		//listaRecomendados = dominioService.getDominios(EnuDominio.RECOMIENDA.getIdTipoDominio());
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
    
    public void getVariedades(){
    	listaVariedades = Convertidor.dominiosToSelectdItems(dominioService.getDominiosXPadre(this.padre));
    	return;
    }
    
    public void getProductos3(){
    	List lista = dominioService.getProductosCompleto(this.variedad);
    	productos2 = getProductosmap(lista);
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

	/*public List<Dominio> getListaGrados() {
		return listaGrados;
	}

	public void setListaGrados(List<Dominio> listaGrados) {
		this.listaGrados = listaGrados;
	}*/

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
		
		
		
		public List<Producto> getProductos2() {
			return productos2;
		}

		public void setProductos2(List<Producto> productos2) {
			this.productos2 = productos2;
		}
        
		
		public Integer getPadre() {
			return padre;
		}

		public void setPadre(Integer padre) {
			this.padre = padre;
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

	

		
		public Integer getVariedad() {
			return variedad;
		}

		public void setVariedad(Integer variedad) {
			this.variedad = variedad;
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

		public void onRowSelect(SelectEvent event) {
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
	    }
	 
	    public void onRowUnselect(UnselectEvent event) {

	    }	

	public void subir(){
		System.out.println("SUBIENDOOOOO::::");
		if(this.idProducto != null){
				
		if(validarSubirProducto()){
			
			Dominio domproducto = dominioService.getDominio(this.idProducto);
			//Dominio domcolor = dominioService.getDominio(this.idColor);
			//Dominio domgrado = dominioService.getDominio(this.idGrado);
			//Producto producto = new Producto(domproducto.getDominiopadre(),"",this.idProducto,domproducto.getNomcorto(),domcolor.getId(),domcolor.getNomcorto(),domgrado.getId(),domgrado.getNomcorto());
			Producto producto = new Producto(domproducto.getDominiopadre(),"",this.idProducto,domproducto.getNomcorto(),this.recomendado,true);
			producto.setRecomendado(this.recomendado);
			listaProductos.add(producto);
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
	
	public void eliminar(Producto producto){
	
		/*for (Producto pro :listaProductos){
			if(pro.getIdProducto().intValue() == producto.getIdProducto().intValue() ){
				listaProductos.remove(pro);
				break;
			}
		}*/
		for (Producto pro :listaProductos){
			if(pro.getId().intValue() == producto.getId().intValue() ){
				listaProductos.remove(pro);
				
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), "El producto se elimino correctamente");
				//RequestContext.getCurrentInstance().showMessageInDialog(message);
				PrimeFaces.current().dialog().showMessageDynamic(message);
				break;
			}
		}
	}
	
	
	public List <com.bulls.astoria.persistence.Producto> productoToDominio(List <Producto> listaProductos){

		List <com.bulls.astoria.persistence.Producto> lista = new ArrayList<com.bulls.astoria.persistence.Producto>();
		for (Producto pro :listaProductos){
			com.bulls.astoria.persistence.Producto propersitir = new com.bulls.astoria.persistence.Producto();
			propersitir.setId(pro.getId());
			lista.add(propersitir);
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
		plantacion.setSaldofinal(0.0);//inicia ceros para no usar this...
		plantacion.setSaldoinicial(0.0);
		plantacion.setSkypealterno(this.skypealterno);
		plantacion.setSkypeprop(this.skypeprop);
		plantacion.setSkypeven(this.skypeven);
		plantacion.setTel1(this.tel1);
		plantacion.setTel2(this.tel2);
		plantacion.setWeb(this.web);
		plantacion.setCantidadxfull(this.cantidadxfull);
		return plantacion;
	}
	
	public String aceptar (){
		
		List <com.bulls.astoria.persistence.Producto> productos = this.productoToDominio(listaProductos);
		List <Dominio> grados = this.selectedGrados;
		Plantacion plantacion = this.setPlantacion();
		if (validarPlantacion()){
			plantacionService.crearPlantacion(plantacion, productos, grados);
			this.putAuditoria("Crear Plantación", "C", "Creo la plantación así : - " + plantacion.toString());
			abrirConfirmacion();
			return "listaplantaciones";
		}
		return null;
		
		
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
	
	 private void abrirConfirmacion(){
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmacrearplantacion"));
			//RequestContext.getCurrentInstance().showMessageInDialog(message);
			PrimeFaces.current().dialog().showMessageDynamic(message);
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
				int idproducto= ((Integer) pro.get("idproducto"));
	            String nomvariedad = ((String) pro.get("nomhijo"));
	            String nomflor = ((String) pro.get("nompadre"));
	            String nomgrado =  ((String) pro.get("nomgrado"));
	            boolean enabled = ((Boolean) pro.get("enabled"));
	            String codigoprod = ((String) pro.get("codigo")); 
	           /* estaba descomentariado com.bulls.astoria.persistence.Producto procod = dominioService.getProductoVariedadGrado(variedad, grado);
	            String codigo = null; 
	            boolean enabled = false;
	            if(procod!= null){
	            	codigo = procod.getCodigo(); 
	                enabled = procod.isEnabled();
	            }*/
				Producto productosalida = new Producto (idproducto,flor,nomflor,variedad,nomvariedad,grado,nomgrado,codigoprod,enabled);
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
			 // FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("plantacionMB");
			  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("preciosMB");
			  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("productoMB");
			  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("truckMB");
			  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuarioMB");
			  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("variedadMB");

		   }



	
	
}
