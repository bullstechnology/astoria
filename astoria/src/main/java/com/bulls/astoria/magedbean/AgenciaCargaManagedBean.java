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

////import org.primefaces.context.RequestContext;

import com.bulls.astoria.enumeracion.EnuDominio;
import com.bulls.astoria.persistence.Aerolinea;
import com.bulls.astoria.persistence.AgenciaCarga;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.Ruta;
import com.bulls.astoria.service.DominioService;
import com.bulls.astoria.utils.Convertidor;
import com.bulls.astoria.utils.Validador;



@ManagedBean (name="cargaMB")
@SessionScoped
public class AgenciaCargaManagedBean extends GeneralManagedBean implements Serializable{

	
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
	private String contacto1;
	private String contacto2;
	private String skype1;
	private String skype2;
	private Double cupo;
	private String desccupo;
	private Double saldoinicial;
	private Double saldofinal;
	private String descsaldofinal;
    private String observaciones;
    private String infobancaria;
    private boolean enabled = true;
	
	ResourceBundle bundle ;
	
	List <Dominio> paises;
	List <Dominio> ciudades;
	List <Dominio> tiposDocumento;
	List <SelectItem> listaPaisesDom;
	List <SelectItem> listaCiudadesDom;
	List <SelectItem> listaCiudadesOrigenDom;
	List <SelectItem> listaCiudadesDestinoDom;
	List<SelectItem> listaTipodocumentoDom;
	List <Aerolinea> listaerolineas;
	private List <com.bulls.astoria.pojo.Destino> listaDestinos;
	private List <com.bulls.astoria.pojo.Destino> listaDestinostmp;
	
	
	private Integer idorigen;
	private Integer iddestino;
	private Integer idciudadorigen;
	private Integer idciudaddestino;
	private Integer rangoinicial;
	private Integer rangofinal;
	private Double tarifacliente;
	private Double tarifaastoria;
	private Double tarifaguia;
	
	private Integer rangoinicial2;
	private Integer rangofinal2;
	private Double tarifacliente2;
	private Double tarifaastoria2;
	private Double tarifaguia2;
	
	private Integer rangoinicial3;
	private Integer rangofinal3;
	private Double tarifacliente3;
	private Double tarifaastoria3;
	private Double tarifaguia3;
    private com.bulls.astoria.pojo.Destino selectedDestino;
	private String destino;
	private String aerolinea;
	
	
	private boolean lunes;
	private boolean martes;
	private boolean miercoles;
	private boolean jueves;
	private boolean viernes;
	private boolean sabado;
	private boolean domingo;
	
	private String concepto1;
	private Double valor1;
	private Double valorast1;
	private String concepto2;
	private Double valor2;
	private Double valorast2;
	private String concepto3;
	private Double valor3;
	private Double valorast3;
	private String concepto4;
	private Double valor4;
	private Double valorast4;
	String observacionesdestino;
	
	
	
	@ManagedProperty(value="#{DominioService}")
	DominioService dominioService;
	
	
	@PostConstruct
	public void AgenciaCargaManagedBean(){
		
		borrarSession();
		
		bundle =  ResourceBundle.getBundle("messages");
		paises = dominioService.getDominios(EnuDominio.PAISES.getIdTipoDominio());
		ciudades = dominioService.getDominios(EnuDominio.CIUDADES.getIdTipoDominio());
		listaPaisesDom = Convertidor.dominiosToSelectdItems(paises);
		listaerolineas = dominioService.getAerolineas();
		//listaCiudadesDom = Convertidor.dominiosToSelectdItems(ciudades);
		
		
		tiposDocumento = dominioService.getDominios(EnuDominio.TIPOS_DOCUMENTO.getIdTipoDominio());
		listaTipodocumentoDom = Convertidor.dominiosToSelectdItems(tiposDocumento);
		listaDestinos = new ArrayList <com.bulls.astoria.pojo.Destino> ();
		listaDestinostmp = new ArrayList <com.bulls.astoria.pojo.Destino> ();
		getDestinos();
	}
	
	public void getDestinos (){
		
		
		listaDestinos = new ArrayList <com.bulls.astoria.pojo.Destino> ();
		List <Ruta> rutas = dominioService.getListaRutas();
		for(Ruta ruta:rutas){
			com.bulls.astoria.pojo.Destino destino = new com.bulls.astoria.pojo.Destino();
			
			destino.setId(ruta.getId());
			destino.setIddestino(ruta.getIdpaisdestino());
    		destino.setIdorigen(ruta.getIdpaisorigen());
    		destino.setIdciudaddestino(ruta.getIdciudaddestino());
    		destino.setIdciudadorigen(ruta.getIdciudadorigen());
    	
    		destino.setNombreorigen(dominioService.getDominio(ruta.getIdpaisorigen()).getNomcorto());
    		destino.setNombrefinal(dominioService.getDominio(ruta.getIdpaisdestino()).getNomcorto());
    		
    		destino.setNombreciudad(dominioService.getDominio(ruta.getIdciudadorigen()).getNomcorto());
    		destino.setNombreciudadfinal(dominioService.getDominio(ruta.getIdciudaddestino()).getNomcorto());
    	
    		listaDestinos.add(destino);
		}

	return;	
 }
	
	
	public void getCiudadesPais(){

    	ciudades= dominioService.getDominiosXPadre(this.pais);
    	listaCiudadesDom = Convertidor.dominiosToSelectdItems(ciudades);
    }
    public void getCiudadesPaisOrigen(){

    	ciudades= dominioService.getDominiosXPadre(this.idorigen);
    	listaCiudadesOrigenDom = Convertidor.dominiosToSelectdItems(ciudades);
    }
    public void getCiudadesPaisDestino(){

    	ciudades= dominioService.getDominiosXPadre(this.iddestino);
    	listaCiudadesDestinoDom = Convertidor.dominiosToSelectdItems(ciudades);
    }
    
    public String crearAgencia(){
    	
   	  AgenciaCarga agencia = new AgenciaCarga();
   	  
   	  agencia.setCel1(cel1);
   	  agencia.setCel2(cel2);
   	  agencia.setCiudad(ciudad);
   	  agencia.setCodigo(codigo);
   	  agencia.setContacto1(contacto1);
   	  agencia.setContacto2(contacto2);
   	  agencia.setCorreo1(correo1);
   	  agencia.setCorreo2(correo2);
   	  agencia.setCupo(cupo);
   	  agencia.setDesccupo(desccupo);
	  agencia.setDescsaldofinal(descsaldofinal);
	  agencia.setDireccion(direccion);
	  agencia.setFax(fax);
	  agencia.setNombre(nombre);
	  agencia.setNumident(numident);
	  agencia.setObservaciones(observaciones);
	  agencia.setPais(pais);
	  agencia.setSaldofinal(0.0);//para no usar this...
	  agencia.setSaldoinicial(0.0);
	  agencia.setSkype1(skype1);
	  agencia.setSkype2(skype2);
	  agencia.setTel1(tel1);
	  agencia.setTel2(tel2);
	  agencia.setTipoident(tipoident);
	  agencia.setWeb(web);
	  agencia.setEnabled(enabled);
	  agencia.setInfobancaria(infobancaria);
	
    		
    		
    		if(validarAgencia()){
    			agencia.setDestino(putDestinos(agencia));
    			dominioService.crearAgencia(agencia);
    			this.putAuditoria("Crear Agencia de carga", "C", "Creo la agencia así : - " + agencia.toString());
    			abrirConfirmacion();
    			return "listaagencias";
    		}
    		

    		
    		return null;
    		
    	
    	
    }
    
    public void subir (){
		
    	
    	if(validarSubir()){
    	
    		com.bulls.astoria.pojo.Destino destino = new com.bulls.astoria.pojo.Destino();
    		Ruta ruta = dominioService.getRutaXId(Integer.parseInt(this.destino));

    		destino.setId(ruta.getId());
    		destino.setIddestino(ruta.getIdpaisdestino());
    		destino.setIdorigen(ruta.getIdpaisorigen());
    		destino.setIdciudaddestino(ruta.getIdciudaddestino());
    		destino.setIdciudadorigen(ruta.getIdciudadorigen());
    		destino.setTarifaguia(this.tarifaguia);
    		destino.setRangofinal(this.rangofinal);
    		destino.setRangoinicial(this.rangoinicial);
    		destino.setTarifaastoria(this.tarifaastoria);
    		destino.setTarifacliente(this.tarifacliente);
    		
    		destino.setRangofinal2(this.rangofinal2);
    		destino.setRangoinicial2(this.rangoinicial2);
    		destino.setTarifaastoria2(this.tarifaastoria2);
    		destino.setTarifacliente2(this.tarifacliente2);
    		
    		
    		destino.setRangofinal3(this.rangofinal3);
    		destino.setRangoinicial3(this.rangoinicial3);
    		destino.setTarifaastoria3(this.tarifaastoria3);
    		destino.setTarifacliente3(this.tarifacliente3);
    		
    		
    		
    		
    	
    		destino.setNombreorigen(dominioService.getDominio(ruta.getIdpaisorigen()).getNomcorto());
    		destino.setNombrefinal(dominioService.getDominio(ruta.getIdpaisdestino()).getNomcorto());
    		
    		destino.setNombreciudad(dominioService.getDominio(ruta.getIdciudadorigen()).getNomcorto());
    		destino.setNombreciudadfinal(dominioService.getDominio(ruta.getIdciudaddestino()).getNomcorto());
    		
    		destino.setAerolinea(Integer.parseInt(aerolinea));
    		destino.setNombreaerolinea(dominioService.getAerolineaXId(Integer.parseInt(aerolinea)).getNombre());
    		destino.setLunes(this.lunes);
    		destino.setMartes(this.martes);
    		destino.setMiercoles(this.miercoles);
    		destino.setJueves(this.jueves);
    		destino.setViernes(this.viernes);
    		destino.setSabado(this.sabado);
    		destino.setDomingo(this.domingo);
    		destino.setConcepto1(this.concepto1);
    		destino.setValor1(this.valor1);
    		destino.setValorast1(this.valorast1);
    		destino.setConcepto2(this.concepto2);
    		destino.setValor2(this.valor2);
    		destino.setValorast2(this.valorast2);
    		destino.setConcepto3(this.concepto3);
    		destino.setValor3(this.valor3);
    		destino.setValorast3(this.valorast3);
    		destino.setConcepto4(this.concepto4);
    		destino.setValor4(this.valor4);
    		destino.setValorast4(this.valorast4);
            destino.setObservaciones(this.observacionesdestino);
    	
    		listaDestinostmp.add(destino);
    	}
    	return;	
     }
    
   public  void eliminar(com.bulls.astoria.pojo.Destino destino){
	   listaDestinostmp.remove(destino);
    }
    
    public boolean validarSubir(){
  	  
  	  boolean ok = true;
  	  int auxfinal=0;
  	  
  	    	  
  	  //valida rangos
  	  if(this.rangoinicial.intValue() < 0 || this.rangofinal.intValue() < 0 || this.rangoinicial2.intValue() < 0 || this.rangofinal2.intValue() < 0 || this.rangoinicial3.intValue() < 0 || this.rangofinal3.intValue() < 0){
  		  
  		  ok = false;
  		  FacesContext.getCurrentInstance().addMessage(null,
		  new FacesMessage(FacesMessage.SEVERITY_ERROR,"Rangos","Los rangos deben ser valores positivos" ));
  		  return ok;
  	  }
  	  
  	  if (this.rangofinal.intValue() == 0 ){
  		  auxfinal++;
	  }
	  if (this.rangofinal2.intValue() == 0 && this.rangoinicial2.intValue() > 0){
		  auxfinal++;
 	  }
	  if (this.rangofinal3.intValue() == 0 && this.rangoinicial3.intValue() > 0){
		  auxfinal++;
 	  }
	  if(auxfinal > 1){
		  ok = false;
	  }
	  
	  auxfinal = 0;
	  
	  if ((this.rangoinicial.intValue() >= this.rangofinal.intValue()) && (this.rangofinal.intValue() != 0) ){
  		  auxfinal++;
	  }
	  
	  if ((this.rangoinicial2.intValue() >= this.rangofinal2.intValue()) && (this.rangofinal2.intValue() != 0) ){
  		  auxfinal++;
	  }
	  
	  if ((this.rangoinicial3.intValue() >= this.rangofinal3.intValue()) && (this.rangofinal3.intValue() != 0) ){
  		  auxfinal++;
	  }
	  
	  if(auxfinal > 0){
		  ok = false;
	  }
	  
	  if (this.rangoinicial2.intValue() != this.rangofinal.intValue()){
		  ok=false;
	  }
	  if (this.rangoinicial3.intValue() != this.rangofinal2.intValue()){
		  ok = false;
	  }
	  
	  if(!ok){
		  FacesContext.getCurrentInstance().addMessage(null,
		  new FacesMessage(FacesMessage.SEVERITY_ERROR,"Rangos","Error en los rangos revise por favor" ));
	  }else {
		  if (this.rangofinal.intValue() == 0 ){
	  		  this.rangofinal = 999999999;
	  	  }else if (this.rangofinal2.intValue() == 0 ){
	   		 this.rangofinal2 = 999999999;
	   	  }else if (this.rangofinal3.intValue() == 0 ){
	    	 this.rangofinal3 = 999999999;
	      }
	  }
	  
  	
  	  
  	  //fin valida rangos
  	  
    	  for (com.bulls.astoria.pojo.Destino pojo : listaDestinostmp){
  		     if((pojo.getAerolinea().intValue() == Integer.parseInt(this.aerolinea)) && (pojo.getId() == Integer.parseInt(this.destino))){
  		    	ok=false;
  		    	FacesContext.getCurrentInstance().addMessage(null,
  		    		  new FacesMessage(FacesMessage.SEVERITY_ERROR,"Destino","ya existen datos de ese destino para la aerolinea" ));
  		    	ok=false;
  		     }
    	  }
    	  
    	  return ok;
  	  }
  	  
  	 

    private void abrirConfirmacion(){
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("tituloconfirmacion"), bundle.getString("confirmacrearagencia"));
		//RequestContext.getCurrentInstance().showMessageInDialog(message);
		PrimeFaces.current().dialog().showMessageDynamic(message);
		return;

	}
    
    
    private boolean validarAgencia(){
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
		
		if (!Validador.esLongitudCorrecta(this.contacto1, 0, 100)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Contacto uno: "," Debe tener entre 1 y 100 caracteres" ));
			ok = false;
		}
		
		if (!Validador.esLongitudCorrecta(this.contacto2, 0, 100)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Contacto dos: "," Debe tener entre 1 y 100 caracteres" ));
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
		
		if(this.pais== 0){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"País: "," Debe escoger un país" ));
			ok = false;
		}
		
		
		
		if (!Validador.esLongitudCorrecta(this.skype1, 0, 45)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Otro Skype prop: "," Debe tener entre 1 y 45 caracteres" ));
			ok = false;
		}
		if (!Validador.esLongitudCorrecta(this.skype2, 0, 45)){
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
		if (tipoident.intValue() == 0){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Tipo Identificación: "," Debe escoger un tipo de Identificación" ));
			ok = false;
		}
		

		
		return ok;
	}
    
    
    
private Set<com.bulls.astoria.persistence.Destino> putDestinos(AgenciaCarga agencia){
		
		List <com.bulls.astoria.persistence.Destino> listasalida = new ArrayList <com.bulls.astoria.persistence.Destino>();
		
		for (com.bulls.astoria.pojo.Destino destino : listaDestinostmp){
			
			com.bulls.astoria.persistence.Destino destinofinal = new com.bulls.astoria.persistence.Destino ();
			
			destinofinal.setIddestino(destino.getIddestino());
			destinofinal.setIdorigen(destino.getIdorigen());
			destinofinal.setIdciudadorigen(destino.getIdciudadorigen());
			destinofinal.setIdciudaddestino(destino.getIdciudaddestino());
			destinofinal.setTarifaguia(destino.getTarifaguia());
			destinofinal.setRangofinal(destino.getRangofinal());
			destinofinal.setRangoinicial(destino.getRangoinicial());
			destinofinal.setTarifaastoria(destino.getTarifaastoria());
			destinofinal.setTarifacliente(destino.getTarifacliente());
			
			destinofinal.setRangofinal2(destino.getRangofinal2());
			destinofinal.setRangoinicial2(destino.getRangoinicial2());
			destinofinal.setTarifaastoria2(destino.getTarifaastoria2());
			destinofinal.setTarifacliente2(destino.getTarifacliente2());
			
			destinofinal.setRangofinal3(destino.getRangofinal3());
			destinofinal.setRangoinicial3(destino.getRangoinicial3());
			destinofinal.setTarifaastoria3(destino.getTarifaastoria3());
			destinofinal.setTarifacliente3(destino.getTarifacliente3());
			
			destinofinal.setIdruta(destino.getId());
			destinofinal.setIdaerolinea(destino.getAerolinea());
			
			
			
			destinofinal.setLunes(destino.isLunes());
			destinofinal.setMartes(destino.isMartes());
			destinofinal.setMiercoles(destino.isMiercoles());
			destinofinal.setJueves(destino.isJueves());
			destinofinal.setViernes(destino.isViernes());
			destinofinal.setSabado(destino.isSabado());
			destinofinal.setDomingo(destino.isDomingo());
			destinofinal.setConcepto1(destino.getConcepto1());
			destinofinal.setValor1(destino.getValor1());
			destinofinal.setValorast1(destino.getValorast1());
			destinofinal.setConcepto2(destino.getConcepto2());
			destinofinal.setValor2(destino.getValor2());
			destinofinal.setValorast2(destino.getValorast2());
			destinofinal.setConcepto3(destino.getConcepto3());
			destinofinal.setValor3(destino.getValor3());
			destinofinal.setValorast3(destino.getValorast3());
			destinofinal.setConcepto4(destino.getConcepto4());
			destinofinal.setValor4(destino.getValor4());
			destinofinal.setValorast4(destino.getValorast4());
            destinofinal.setObservaciones(destino.getObservaciones());
			
			destinofinal.setAgencia(agencia);
			listasalida.add(destinofinal);
		}
		
		Set<com.bulls.astoria.persistence.Destino> targeDestinos = new HashSet<com.bulls.astoria.persistence.Destino>(listasalida);
		return targeDestinos;
		
		
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

	public String getContacto1() {
		return contacto1;
	}

	public void setContacto1(String contacto1) {
		this.contacto1 = contacto1;
	}

	public String getContacto2() {
		return contacto2;
	}

	public void setContacto2(String contacto2) {
		this.contacto2 = contacto2;
	}

	public String getSkype1() {
		return skype1;
	}

	public void setSkype1(String skype1) {
		this.skype1 = skype1;
	}

	public String getSkype2() {
		return skype2;
	}

	public void setSkype2(String skype2) {
		this.skype2 = skype2;
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

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
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

	public DominioService getDominioService() {
		return dominioService;
	}

	public void setDominioService(DominioService dominioService) {
		this.dominioService = dominioService;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Integer getIdorigen() {
		return idorigen;
	}

	public void setIdorigen(Integer idorigen) {
		this.idorigen = idorigen;
	}

	public Integer getIddestino() {
		return iddestino;
	}

	public void setIddestino(Integer iddestino) {
		this.iddestino = iddestino;
	}

	public Integer getRangoinicial() {
		return rangoinicial;
	}

	public void setRangoinicial(Integer rangoinicial) {
		this.rangoinicial = rangoinicial;
	}

	public Integer getRangofinal() {
		return rangofinal;
	}

	public void setRangofinal(Integer rangofinal) {
		this.rangofinal = rangofinal;
	}

	public Double getTarifacliente() {
		return tarifacliente;
	}

	public void setTarifacliente(Double tarifacliente) {
		this.tarifacliente = tarifacliente;
	}

	public Double getTarifaastoria() {
		return tarifaastoria;
	}

	public void setTarifaastoria(Double tarifaastoria) {
		this.tarifaastoria = tarifaastoria;
	}

	public Double getTarifaguia() {
		return tarifaguia;
	}

	public void setTarifaguia(Double tarifaguia) {
		this.tarifaguia = tarifaguia;
	}

	public List<com.bulls.astoria.pojo.Destino> getListaDestinos() {
		return listaDestinos;
	}

	public void setListaDestinos(List<com.bulls.astoria.pojo.Destino> listaDestinos) {
		this.listaDestinos = listaDestinos;
	}

	public Integer getIdciudadorigen() {
		return idciudadorigen;
	}

	public void setIdciudadorigen(Integer idciudadorigen) {
		this.idciudadorigen = idciudadorigen;
	}

	public Integer getIdciudaddestino() {
		return idciudaddestino;
	}

	public void setIdciudaddestino(Integer idciudaddestino) {
		this.idciudaddestino = idciudaddestino;
	}


	public List<SelectItem> getListaCiudadesOrigenDom() {
		return listaCiudadesOrigenDom;
	}


	public void setListaCiudadesOrigenDom(List<SelectItem> listaCiudadesOrigenDom) {
		this.listaCiudadesOrigenDom = listaCiudadesOrigenDom;
	}


	public List<SelectItem> getListaCiudadesDestinoDom() {
		return listaCiudadesDestinoDom;
	}


	public void setListaCiudadesDestinoDom(List<SelectItem> listaCiudadesDestinoDom) {
		this.listaCiudadesDestinoDom = listaCiudadesDestinoDom;
	}

	public List<Aerolinea> getListaerolineas() {
		return listaerolineas;
	}

	public void setListaerolineas(List<Aerolinea> listaerolineas) {
		this.listaerolineas = listaerolineas;
	}

	public List<com.bulls.astoria.pojo.Destino> getListaDestinostmp() {
		return listaDestinostmp;
	}

	public void setListaDestinostmp(
			List<com.bulls.astoria.pojo.Destino> listaDestinostmp) {
		this.listaDestinostmp = listaDestinostmp;
	}

	

	public String getAerolinea() {
		return aerolinea;
	}

	public void setAerolinea(String aerolinea) {
		this.aerolinea = aerolinea;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
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

	public String getConcepto1() {
		return concepto1;
	}

	public void setConcepto1(String concepto1) {
		this.concepto1 = concepto1;
	}

	public Double getValor1() {
		return valor1;
	}

	public void setValor1(Double valor1) {
		this.valor1 = valor1;
	}

	public String getConcepto2() {
		return concepto2;
	}

	public void setConcepto2(String concepto2) {
		this.concepto2 = concepto2;
	}

	public Double getValor2() {
		return valor2;
	}

	public void setValor2(Double valor2) {
		this.valor2 = valor2;
	}

	public String getConcepto3() {
		return concepto3;
	}

	public void setConcepto3(String concepto3) {
		this.concepto3 = concepto3;
	}

	public Double getValor3() {
		return valor3;
	}

	public void setValor3(Double valor3) {
		this.valor3 = valor3;
	}

	public String getConcepto4() {
		return concepto4;
	}

	public void setConcepto4(String concepto4) {
		this.concepto4 = concepto4;
	}

	public Double getValor4() {
		return valor4;
	}

	public void setValor4(Double valor4) {
		this.valor4 = valor4;
	}

	public Integer getRangoinicial2() {
		return rangoinicial2;
	}

	public void setRangoinicial2(Integer rangoinicial2) {
		this.rangoinicial2 = rangoinicial2;
	}

	public Integer getRangofinal2() {
		return rangofinal2;
	}

	public void setRangofinal2(Integer rangofinal2) {
		this.rangofinal2 = rangofinal2;
	}

	public Double getTarifacliente2() {
		return tarifacliente2;
	}

	public void setTarifacliente2(Double tarifacliente2) {
		this.tarifacliente2 = tarifacliente2;
	}

	public Double getTarifaastoria2() {
		return tarifaastoria2;
	}

	public void setTarifaastoria2(Double tarifaastoria2) {
		this.tarifaastoria2 = tarifaastoria2;
	}

	public Double getTarifaguia2() {
		return tarifaguia2;
	}

	public void setTarifaguia2(Double tarifaguia2) {
		this.tarifaguia2 = tarifaguia2;
	}

	public Integer getRangoinicial3() {
		return rangoinicial3;
	}

	public void setRangoinicial3(Integer rangoinicial3) {
		this.rangoinicial3 = rangoinicial3;
	}

	public Integer getRangofinal3() {
		return rangofinal3;
	}

	public void setRangofinal3(Integer rangofinal3) {
		this.rangofinal3 = rangofinal3;
	}

	public Double getTarifacliente3() {
		return tarifacliente3;
	}

	public void setTarifacliente3(Double tarifacliente3) {
		this.tarifacliente3 = tarifacliente3;
	}

	public Double getTarifaastoria3() {
		return tarifaastoria3;
	}

	public void setTarifaastoria3(Double tarifaastoria3) {
		this.tarifaastoria3 = tarifaastoria3;
	}

	public Double getTarifaguia3() {
		return tarifaguia3;
	}

	public void setTarifaguia3(Double tarifaguia3) {
		this.tarifaguia3 = tarifaguia3;
	}

	public String getObservacionesdestino() {
		return observacionesdestino;
	}

	public void setObservacionesdestino(String observacionesdestino) {
		this.observacionesdestino = observacionesdestino;
	}

	public com.bulls.astoria.pojo.Destino getSelectedDestino() {
		return selectedDestino;
	}

	public void setSelectedDestino(com.bulls.astoria.pojo.Destino selectedDestino) {
		this.selectedDestino = selectedDestino;
	}

	public String getInfobancaria() {
		return infobancaria;
	}

	public void setInfobancaria(String infobancaria) {
		this.infobancaria = infobancaria;
	}
	
	
    
	public Double getValorast1() {
		return valorast1;
	}

	public void setValorast1(Double valorast1) {
		this.valorast1 = valorast1;
	}

	public Double getValorast2() {
		return valorast2;
	}

	public void setValorast2(Double valorast2) {
		this.valorast2 = valorast2;
	}

	public Double getValorast3() {
		return valorast3;
	}

	public void setValorast3(Double valorast3) {
		this.valorast3 = valorast3;
	}

	public Double getValorast4() {
		return valorast4;
	}

	public void setValorast4(Double valorast4) {
		this.valorast4 = valorast4;
	}

	public void borrarSession(){
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("aerolineaMB");
		  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("cargaMB");
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
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("plantacionMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("preciosMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("productoMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("truckMB");
		  //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuarioMB");
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("variedadMB");

	   }
	
	
	
}
