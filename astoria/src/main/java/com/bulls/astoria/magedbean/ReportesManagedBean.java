package com.bulls.astoria.magedbean;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.io.ByteArrayOutputStream;
//import com.mysql.jdbc.Connection;
import java.io.FileNotFoundException; 
import java.io.InputStream; 
import java.io.Serializable; 
import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale; 
import java.util.Map; 

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.bulls.astoria.pojo.CostosAdicionalesBean;
import com.bulls.astoria.pojo.ListaPreciosFinal;
import com.bulls.astoria.pojo.PedidoBean;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException; 
import net.sf.jasperreports.engine.JRExporter; 
import net.sf.jasperreports.engine.JRExporterParameter; 
import net.sf.jasperreports.engine.JasperCompileManager; 
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager; 
import net.sf.jasperreports.engine.JasperPrint; 
import net.sf.jasperreports.engine.JasperReport; 
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter; 
import net.sf.jasperreports.engine.export.JRPdfExporterParameter; 
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter; 
import net.sf.jasperreports.engine.export.JRXlsExporter; 
import net.sf.jasperreports.engine.export.JRXlsExporterParameter; 
import net.sf.jasperreports.engine.util.JRSaver; 
import net.sf.jasperreports.view.JasperViewer;


@ManagedBean (name="reportesMB")
@SessionScoped
public class ReportesManagedBean extends GeneralManagedBean implements Serializable{
	
	private JasperReport reporte; 
	private JasperPrint print; 
	static Connection conn = null;
	ResourceBundle bundle ;
	ResourceBundle bundleparam ;
	
	private String url;
	private String user;
	private String pass;
	private String carpetajrxml;
	
	@PostConstruct
	public void ReportesManagedBean(){
	
		bundle =  ResourceBundle.getBundle("persistence");
		bundleparam =  ResourceBundle.getBundle("parametros");
		url = bundle.getString("jdbc.url");
		user = bundle.getString("jdbc.user");
		pass = bundle.getString("jdbc.pass");
		carpetajrxml = bundleparam.getString("rutajrxml");
	 
	} 
	//exportar reporte a axcel 
	
	public void reporteExcelImpresion(InputStream rutaJrxml,String rutaArchivoXLS,Map<String, Object> parametros,Connection conexion) throws JRException, FileNotFoundException { 
		this.reporte=JasperCompileManager.compileReport(rutaJrxml); 
		//luego ponemos los parametros que necesitamos: 
		print = JasperFillManager.fillReport(this.reporte, parametros, conexion); 
		JRXlsExporter exportador = new JRXlsExporter(); 
		exportador.setParameter(JRExporterParameter.JASPER_PRINT,print);
		exportador.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,rutaArchivoXLS);
		exportador.setParameter(JRExporterParameter.IGNORE_PAGE_MARGINS,true);
		exportador.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, false);
		exportador.setParameter(JRXlsAbstractExporterParameter.IS_IGNORE_CELL_BORDER,false);
		exportador.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS,true);
		exportador.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE ,true);
		exportador.setParameter(JRXlsExporterParameter.IS_FONT_SIZE_FIX_ENABLED,true);
		exportador.exportReport(); 
		
	} 
	//metodo para generar el reporte en pdf si que se puedan copiar las imagenes ni el texto 
	public boolean jasperReport(String ruta, InputStream dataSourceName, Map<String, Object> params,Connection conn) throws ClassNotFoundException, JRException { 
		this.reporte=JasperCompileManager.compileReport(dataSourceName);  
		this.print = JasperFillManager.fillReport(this.reporte, params, conn); 
		if(this.print.getPages().isEmpty()){ 
			return false; 
	    } 
		//int permisos =PdfWriter.ALLOW_PRINTING; //Esta clase es la encargada de exportar el archivo a pdf 
		final JRExporter jtrtf= new JRPdfExporter();  
		jtrtf.setParameter(JRPdfExporterParameter.IS_ENCRYPTED, Boolean.TRUE); 
		jtrtf.setParameter(JRPdfExporterParameter.IS_128_BIT_KEY, Boolean.TRUE); 
		//jtrtf.setParameter(JRPdfExporterParameter.PERMISSIONS, permisos); 
		jtrtf.setParameter(JRExporterParameter.JASPER_PRINT, this.print);  
		//Gurdamos una copia en el computador Ejemplo c:/reportes.jrprint 
		JRSaver.saveObject(this.print,ruta+".jrprint"); 
		//Gurdamos una copia en el computador Ejemplo c:/reportes.pdf 
		jtrtf.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, ruta+".pdf"); 
		//este metodo exporta a los diferentes formatos en este caso pdf 
		jtrtf.exportReport(); 
		//Metodo que se encarga de mostrar el reporte en la pantalla 
		JasperViewer.viewReport(this.print,false,Locale.getDefault()); 
		return true; 
	}
	
public void generarPDF(Map parameters,List <PedidoBean> lista ,String filejrxml,String tipo){	
	try {
		System.out.println("********************   entro en generarPDF");
	      Class.forName("com.mysql.jdbc.Driver");
	      try {
		      conn = DriverManager.getConnection(url,user, pass);
		      conn.setAutoCommit(false);
		    }
		    catch (SQLException e) {
		      System.out.println("Error de conexión: " + e.getMessage());
		      System.exit(4);
		    }
	    }
	    catch (ClassNotFoundException e) {
	      System.out.println("MySQL JDBC Driver not found.");
	      System.exit(1);
	    }
	    //Para iniciar el Logger.
	    //inicializaLogger();
 
	    try {

	    	

	      JRBeanCollectionDataSource ds =new JRBeanCollectionDataSource(lista);
	      JasperReport report = JasperCompileManager.compileReport(carpetajrxml + filejrxml);
	      JasperPrint print = JasperFillManager.fillReport(report, parameters, ds);
	      
	      HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	      if(tipo.equalsIgnoreCase("P")){
	      // Exporta el informe a PDF
	      // lo hacia bien JasperExportManager.exportReportToPdfFile(print, carpetajrxml + "prueva2pdf.pdf");
	      //Para visualizar el pdf directamente desde java
	      
	     // Runtime.getRuntime().exec("cmd /c start "+"I:\\temp\\prueva2pdf.pdf");
	      
	      
	/*  HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	     response.addHeader("Content-disposition", "attachment; filename=invoice.pdf"); 
	      ServletOutputStream stream = response.getOutputStream();
	      JasperExportManager.exportReportToPdfStream(print, stream);
	      stream.flush();
	      stream.close();
	      FacesContext.getCurrentInstance().responseComplete();*/
	 
	      
	      ByteArrayOutputStream baos =new ByteArrayOutputStream();
	      response.setContentType("application/pdf");
	      JasperExportManager.exportReportToPdfStream(print, baos);
	      response.setContentLength(baos.size());
	      ServletOutputStream out1 = response.getOutputStream();
	      baos.writeTo(out1);
	      out1.flush();  
	      
	      }
	     
	      if(tipo.equalsIgnoreCase("E")){
	     /* ByteArrayOutputStream baos =new ByteArrayOutputStream();
	      response.setContentType("application/vnd.ms-excel");
	      JRXlsExporter exporter = new JRXlsExporter();
	      exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
	      exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,baos );
	      exporter.exportReport();
	      response.setContentLength(baos.size());
	      ServletOutputStream out1 = response.getOutputStream();
	      baos.writeTo(out1);
	      out1.flush(); */
	      
	      
	//HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		     response.addHeader("Content-disposition", "attachment; filename=invoice.xls"); 
		      ServletOutputStream stream = response.getOutputStream();
		      JRXlsExporter exporter = new JRXlsExporter();
		      exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		      exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,stream );
		      exporter.exportReport();
		      stream.flush();
		      stream.close();
		      FacesContext.getCurrentInstance().responseComplete(); 
	      }  
	      this.putAuditoria("Generar reporte", "C", "Genero reporte : - " + filejrxml);
	      
	      
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	    finally {
	      /*
	       * Cleanup antes de salir
	       */
	      try {
	        if (conn != null) {
	          conn.rollback();
	          System.out.println("ROLLBACK EJECUTADO");
	          conn.close();
	        }
	      }
	      catch (Exception e) {
	        e.printStackTrace();
	      }
	    }
	 
	  }


public void generarPDFListaGeneralPrecios(Map parameters,List <ListaPreciosFinal> lista ,String filejrxml,String tipo){	
	try {
	      Class.forName("com.mysql.jdbc.Driver");
	      try {
		      conn = DriverManager.getConnection(url,user, pass);
		      conn.setAutoCommit(false);
		    }
		    catch (SQLException e) {
		      System.out.println("Error de conexión: " + e.getMessage());
		      System.exit(4);
		    }
	    }
	    catch (ClassNotFoundException e) {
	      System.out.println("MySQL JDBC Driver not found.");
	      System.exit(1);
	    }
	    //Para iniciar el Logger.
	    //inicializaLogger();
 
	    try {

	    	

	      JRBeanCollectionDataSource ds =new JRBeanCollectionDataSource(lista);
	      JasperReport report = JasperCompileManager.compileReport(carpetajrxml + filejrxml);
	      JasperPrint print = JasperFillManager.fillReport(report, parameters, ds);
	      
	      HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	      if(tipo.equalsIgnoreCase("P")){
	    	  System.out.println("ENTRA EN EXPORTAR A pdf");
	      // Exporta el informe a PDF
	      // lo hacia bien JasperExportManager.exportReportToPdfFile(print, carpetajrxml + "prueva2pdf.pdf");
	      //Para visualizar el pdf directamente desde java
	      
	     // Runtime.getRuntime().exec("cmd /c start "+"I:\\temp\\prueva2pdf.pdf");
	      
	      
	/*  HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	     response.addHeader("Content-disposition", "attachment; filename=invoice.pdf"); 
	      ServletOutputStream stream = response.getOutputStream();
	      JasperExportManager.exportReportToPdfStream(print, stream);
	      stream.flush();
	      stream.close();
	      FacesContext.getCurrentInstance().responseComplete();*/
	 
	      
	      ByteArrayOutputStream baos =new ByteArrayOutputStream();
	      response.setContentType("application/pdf");
	      JasperExportManager.exportReportToPdfStream(print, baos);
	      response.setContentLength(baos.size());
	      ServletOutputStream out1 = response.getOutputStream();
	      baos.writeTo(out1);
	      out1.flush();  
	      }
	     
	      if(tipo.equalsIgnoreCase("E")){
	    	  HttpServletResponse response2 = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	     /* ByteArrayOutputStream baos =new ByteArrayOutputStream();
	      response.setContentType("application/vnd.ms-excel");
	      JRXlsExporter exporter = new JRXlsExporter();
	      exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
	      exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,baos );
	      exporter.exportReport();
	      response.setContentLength(baos.size());
	      ServletOutputStream out1 = response.getOutputStream();
	      baos.writeTo(out1);
	      out1.flush(); */
	      
	      
	//HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	    	  response2.addHeader("Content-disposition", "attachment; filename=listaprecios.xls"); 
		      ServletOutputStream stream = response2.getOutputStream();
		      JRXlsExporter exporter = new JRXlsExporter();
		      exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		      exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,stream );
		      exporter.exportReport();
		      stream.flush();
		      stream.close();
		      FacesContext.getCurrentInstance().responseComplete(); 
	      }  
	      this.putAuditoria("Generar reporte", "C", "Genero reporte lista precios : - " + filejrxml);
	      
	      
	    }
	    catch (Exception e) {
	    	System.out.println("OCURRIO EXCEPTION ++++++++++++++++++++++++++++++++++++");
	      e.printStackTrace();
	    }
	    finally {
	      /*
	       * Cleanup antes de salir
	       */
	      try {
	        if (conn != null) {
	          conn.rollback();
	          System.out.println("ROLLBACK EJECUTADO");
	          conn.close();
	        }
	      }
	      catch (Exception e) {
	        e.printStackTrace();
	      }
	    }
	 
	  }


public void generarPDFGeneral(Map parameters,List lista ,String filejrxml,String tipo){	
	try {
	      Class.forName("com.mysql.jdbc.Driver");
	      try {
		      conn = DriverManager.getConnection(url,user, pass);
		      conn.setAutoCommit(false);
		    }
		    catch (SQLException e) {
		      System.out.println("Error de conexión: " + e.getMessage());
		      System.exit(4);
		    }
	    }
	    catch (ClassNotFoundException e) {
	      System.out.println("MySQL JDBC Driver not found.");
	      System.exit(1);
	    }
	    //Para iniciar el Logger.
	    //inicializaLogger();
 
	    try {

	    	

	      JRBeanCollectionDataSource ds =new JRBeanCollectionDataSource(lista);
	      JasperReport report = JasperCompileManager.compileReport(carpetajrxml + filejrxml);
	      JasperPrint print = JasperFillManager.fillReport(report, parameters, ds);
	      
	      HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	      if(tipo.equalsIgnoreCase("P")){
	      // Exporta el informe a PDF
	      // lo hacia bien JasperExportManager.exportReportToPdfFile(print, carpetajrxml + "prueva2pdf.pdf");
	      //Para visualizar el pdf directamente desde java
	      
	     // Runtime.getRuntime().exec("cmd /c start "+"I:\\temp\\prueva2pdf.pdf");
	      
	      
	/*  HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	     response.addHeader("Content-disposition", "attachment; filename=invoice.pdf"); 
	      ServletOutputStream stream = response.getOutputStream();
	      JasperExportManager.exportReportToPdfStream(print, stream);
	      stream.flush();
	      stream.close();
	      FacesContext.getCurrentInstance().responseComplete();*/
	 
	      
	      ByteArrayOutputStream baos =new ByteArrayOutputStream();
	      response.setContentType("application/pdf");
	      JasperExportManager.exportReportToPdfStream(print, baos);
	      response.setContentLength(baos.size());
	      ServletOutputStream out1 = response.getOutputStream();
	      baos.writeTo(out1);
	      out1.flush();  
	      
	      }
	     
	      if(tipo.equalsIgnoreCase("E")){
	     /* ByteArrayOutputStream baos =new ByteArrayOutputStream();
	      response.setContentType("application/vnd.ms-excel");
	      JRXlsExporter exporter = new JRXlsExporter();
	      exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
	      exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,baos );
	      exporter.exportReport();
	      response.setContentLength(baos.size());
	      ServletOutputStream out1 = response.getOutputStream();
	      baos.writeTo(out1);
	      out1.flush(); */
	      
	      
	//HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		     response.addHeader("Content-disposition", "attachment; filename=invoice.xls"); 
		      ServletOutputStream stream = response.getOutputStream();
		      JRXlsExporter exporter = new JRXlsExporter();
		      exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		      exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,stream );
		      exporter.exportReport();
		      stream.flush();
		      stream.close();
		      FacesContext.getCurrentInstance().responseComplete(); 
	      }  
	      this.putAuditoria("Generar reporte", "C", "Genero reporte : - " + filejrxml);
	      
	      
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	    finally {
	      /*
	       * Cleanup antes de salir
	       */
	      try {
	        if (conn != null) {
	          conn.rollback();
	          System.out.println("ROLLBACK EJECUTADO");
	          conn.close();
	        }
	      }
	      catch (Exception e) {
	        e.printStackTrace();
	      }
	    }
	 
	  }



public JasperReport getReporte() {
	return reporte;
}

public void setReporte(JasperReport reporte) {
	this.reporte = reporte;
}

public JasperPrint getPrint() {
	return print;
}

public void setPrint(JasperPrint print) {
	this.print = print;
}

public static Connection getConn() {
	return conn;
}

public static void setConn(Connection conn) {
	ReportesManagedBean.conn = conn;
}

public ResourceBundle getBundle() {
	return bundle;
}

public void setBundle(ResourceBundle bundle) {
	this.bundle = bundle;
}

public ResourceBundle getBundleparam() {
	return bundleparam;
}

public void setBundleparam(ResourceBundle bundleparam) {
	this.bundleparam = bundleparam;
}

public String getUrl() {
	return url;
}

public void setUrl(String url) {
	this.url = url;
}

public String getUser() {
	return user;
}

public void setUser(String user) {
	this.user = user;
}

public String getPass() {
	return pass;
}

public void setPass(String pass) {
	this.pass = pass;
}

public String getCarpetajrxml() {
	return carpetajrxml;
}

public void setCarpetajrxml(String carpetajrxml) {
	this.carpetajrxml = carpetajrxml;
}
	
}
