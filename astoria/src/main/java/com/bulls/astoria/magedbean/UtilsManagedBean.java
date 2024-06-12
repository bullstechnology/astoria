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

//import org.primefaces.context.RequestContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bulls.astoria.persistence.Cliente;
import com.bulls.astoria.persistence.Pedido;
import com.bulls.astoria.persistence.PedidoConciliado;
import com.bulls.astoria.persistence.PedidoFacturado;
import com.bulls.astoria.persistence.Permiso;
import com.bulls.astoria.pojo.EncabezadoPedidoBean;
import com.bulls.astoria.pojo.PedidoBean;
import com.bulls.astoria.service.ClienteService;
import com.bulls.astoria.service.DominioService;
import com.bulls.astoria.service.PedidoService;
import com.bulls.astoria.utils.Convertidor;


@ManagedBean (name="uMB")
@SessionScoped
public class UtilsManagedBean implements Serializable{

	private ResourceBundle bundle ;

	@PostConstruct
	public void UtilsManagedBean(){
		bundle =  ResourceBundle.getBundle("parametros");
	}
	
	public Double format (Double numero){
		Double salida = null;
		if(numero!= null){
			BigDecimal big = new BigDecimal(numero); 
			big = big.setScale(3, RoundingMode.HALF_UP);
			salida = big.doubleValue();
		}
		return salida;
	}
	
	public Double formatDecimal (BigDecimal numero){
		Double salida = null;
		if(numero!= null){
			BigDecimal big = new BigDecimal(numero.doubleValue()); 
			big = big.setScale(3, RoundingMode.HALF_UP);
			salida = big.doubleValue();
		}
		return salida;
	}
	
	public void eviarCorreo(String mensajecorreo){
		 // La dirección de envío (to)
	    String para = bundle.getString("correopara");

	    // La dirección de la cuenta de envío (from)
	    final String de = bundle.getString("correode");
	    final String password =  bundle.getString("passwordcorreo");

	    // El servidor (host). En este caso usamos localhost
	    String host = bundle.getString("servidordecorreo");
	    String puerto = bundle.getString("puertodecorreo");

	    // Obtenemos las propiedades del sistema
	    Properties propiedades = System.getProperties();

	    // Configuramos el servidor de correo
	    propiedades.put("mail.smtp.host", host);
	    propiedades.put("mail.smtp.starttls.enabled","true");
	   /* propiedades.put("mail.smtp.socketFactory.port", puerto);
	    propiedades.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");*/
	    propiedades.put("mail.smtp.auth", "true");
	    propiedades.put("mail.smtp.port", puerto);





	    // Obtenemos la sesión por defecto
	    
	   
	    javax.mail.Session session = Session.getInstance(
	    		propiedades,
			       new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(de, password);
				}
			});
	    //Session sesion = Session.getDefaultInstance(propiedades);
	    
	    
	    System.out.println("SE AUTENTICO");

	    try{
	      // Creamos un objeto mensaje tipo MimeMessage por defecto.
	      MimeMessage mensaje = new MimeMessage(session);

	      // Asignamos el “de o from” al header del correo.
	      mensaje.setFrom(new InternetAddress(de));

	      // Asignamos el “para o to” al header del correo.
	      mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(para));

	      // Asignamos el asunto
	      mensaje.setSubject("Ha llegado un nuevo pedido");

	      // Asignamos el contenido HTML, tan grande como nosotros queramos
	      mensaje.setContent("<h1>" + mensajecorreo + "</h1>","text/html" );

	      // Enviamos el correo
	      Transport.send(mensaje);
	      System.out.println("Mensaje enviado");
	    } catch (MessagingException e) {
	      e.printStackTrace();
	    }
	  }
	
		

}
