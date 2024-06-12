package com.bulls.astoria.magedbean;

import java.io.Serializable;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

////import org.primefaces.context.RequestContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bulls.astoria.persistence.Auditoria;
import com.bulls.astoria.persistence.Cliente;
import com.bulls.astoria.persistence.Pedido;
import com.bulls.astoria.persistence.PedidoConciliado;
import com.bulls.astoria.persistence.PedidoFacturado;
import com.bulls.astoria.persistence.Permiso;
import com.bulls.astoria.persistence.Role;
import com.bulls.astoria.persistence.User;
import com.bulls.astoria.pojo.EncabezadoPedidoBean;
import com.bulls.astoria.pojo.PedidoBean;
import com.bulls.astoria.service.ClienteService;
import com.bulls.astoria.service.DominioService;
import com.bulls.astoria.service.PedidoService;
import com.bulls.astoria.service.UserService;
import com.bulls.astoria.utils.Convertidor;


@ManagedBean (name="generalMB")
@SessionScoped
public class GeneralManagedBean implements Serializable{

	private ResourceBundle bundle ;
	private List <Permiso> listaPermisos;
	private String usuario;
	private Integer numcotizaciones = 0;
	
	@ManagedProperty(value="#{DominioService}")
	DominioService dominioService;
	@ManagedProperty(value="#{UserService}")
	UserService userService;
	
	
	@PostConstruct
	public void GeneralManagedBean(){
		
		usuario = SecurityContextHolder.getContext().getAuthentication().getName();
		User usuario2 = this.getUserService().getUser(usuario);
		Role role = usuario2.getRole();
		listaPermisos = this.getDominioService().getPermisos(role.getId());
		
	}
	
	public boolean getPermiso(Integer idProceso, String tipo){
		//opcion : C create , R read , U update and D delete
		boolean ok = false;
		for (Permiso permiso : listaPermisos){
			if(permiso.getIdproceso().intValue() == idProceso.intValue() && permiso.getTipo().equalsIgnoreCase(tipo)){
				ok= true;
			}
		}
		
		return ok;
	}
	
	
	public void putAuditoria(String proceso,String accion,String comentarios){
		Auditoria auditoria = new Auditoria();
		auditoria.setUsuario(this.usuario);
		auditoria.setProceso(proceso);
		auditoria.setAccion(accion);
		auditoria.setComentarios(comentarios);
		auditoria.setFecha(Calendar.getInstance().getTime());
		this.getDominioService().putAuditoria(auditoria);
	}
	
	

	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	public List<Permiso> getListaPermisos() {
		return listaPermisos;
	}

	public void setListaPermisos(List<Permiso> listaPermisos) {
		this.listaPermisos = listaPermisos;
	}

	public DominioService getDominioService() {
		return dominioService;
	}

	public void setDominioService(DominioService dominioService) {
		this.dominioService = dominioService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
	

}
