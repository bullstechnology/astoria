package com.bulls.astoria.service;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bulls.astoria.dao.DominioDao;
import com.bulls.astoria.persistence.Aerolinea;
import com.bulls.astoria.persistence.AgenciaCarga;
import com.bulls.astoria.persistence.Auditoria;
import com.bulls.astoria.persistence.Comision;
import com.bulls.astoria.persistence.Destino;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.Empresa;
import com.bulls.astoria.persistence.GradoMultiple;
import com.bulls.astoria.persistence.Handler;
import com.bulls.astoria.persistence.HandlerTruck;
import com.bulls.astoria.persistence.Nota;
import com.bulls.astoria.persistence.Permiso;
import com.bulls.astoria.persistence.Plantacion;
import com.bulls.astoria.persistence.ProductoGrados;
import com.bulls.astoria.persistence.Ruta;
import com.bulls.astoria.persistence.TipoDominio;
import com.bulls.astoria.persistence.Truck;
import com.bulls.astoria.pojo.Producto;
import com.bulls.astoria.utils.ArchivoUtils;


@Service ("DominioService")
@Transactional

public class DominioServiceImpl implements DominioService,Serializable{
	
	@Autowired
    private DominioDao dominioDAO;
	
	
	public void editEmpresa (Empresa empresa){
		dominioDAO.editEmpresa(empresa);
	}
	
	public Empresa getEmpresa (){
		return dominioDAO.getEmpresa();
	}
	
	@Transactional
	public void saveDominio(Dominio dominio){
		dominioDAO.saveDominio(dominio);
	}
	
	
	@Transactional
	public Dominio getDominio(Integer idDominio){
		return dominioDAO.getDominio(idDominio);
	}
	
	@Transactional
	public List <Dominio> getDominios(Integer idTipoDominio){
		return dominioDAO.getDominios(idTipoDominio);
	}
	
	public TipoDominio getTipoDominio(Integer idTipoDominio){
		return dominioDAO.getTipoDominio(idTipoDominio);
	}
	
	public List <Dominio> getDominiosXNombre(String nombre){
		return dominioDAO.getDominiosXNombre(nombre);
	}
	
	public void editarDominio(Dominio dominio){
		dominioDAO.editarDominio(dominio);
	}
	
	public List <Dominio> getDominiosXPadre(Integer idDominioPadre){
		return dominioDAO.getDominiosXPadre(idDominioPadre);
	}
	
	public List <Truck> getTrucks(){
		return dominioDAO.getTrucks();
	}
	
	public void saveTruck(Truck truck){
		
		dominioDAO.saveTruck(truck);
	}
	
	public void editarTruck (Truck truck){
		dominioDAO.editarTruck(truck);
	}
	
	public List <Truck> getTruckXCodigo(String codigo){
		return dominioDAO.getTruckXCodigo(codigo);
	}
	
	public List <Handler> getHandlers(){
		return dominioDAO.getHandlers();
	}
	
	public void saveHandler(Handler handler){
		
		dominioDAO.saveHandler(handler);
	}
	
	public void editarHandler (Handler handler){
		dominioDAO.editarHandler(handler);
	}
	
	public List <Handler> getHandlerXCodigo(String codigo){
		return dominioDAO.getHandlerXCodigo(codigo);
	}
	
	
	public Handler getHandlerXId(Integer id){
		return dominioDAO.getHandlerXId(id);
	}
	
	public Truck getTruckXId(Integer id){
		return dominioDAO.getTruckXId(id);
	}
	
	public void saveHandlerTruck(HandlerTruck ht){
		dominioDAO.saveHandlerTruck(ht);
	}
	public void deleteHandlerTruck(HandlerTruck ht){
		dominioDAO.deleteHandlerTruck(ht);
	}
	
	public List <HandlerTruck> getHandlerTruck(Integer idHandler,Integer idTrucker){
		return dominioDAO.getHandlerTruck(idHandler,idTrucker);
	}
	
	public List  getProductos(){
		return dominioDAO.getProductos();
	}
	
	public List  getProductosCompleto(){
		return dominioDAO.getProductosCompleto();
	}
	
	public List  getProductosCompleto(Integer idTipoFlor, Integer idVariedad){
		return dominioDAO.getProductosCompleto(idTipoFlor,idVariedad);
	}
	
	public List  getProductosCompletoFlor(Integer idTipoFlor){
		return dominioDAO.getProductosCompletoFlor(idTipoFlor);
	}
	
	public void crearAgencia (AgenciaCarga agencia){
		dominioDAO.crearAgencia(agencia);
	}
	
	public void editarAgencia(AgenciaCarga agencia){
		dominioDAO.editarAgencia(agencia);
	}
	
	public List <AgenciaCarga> getAgenciasCarga(){
		return dominioDAO.getAgenciasCarga();
	}
	public AgenciaCarga getAgenciaXId(Integer idAgencia){
		return dominioDAO.getAgenciaXId(idAgencia);
	}
	public AgenciaCarga getAgenciaXCod(String codigo){
		return dominioDAO.getAgenciaXCod(codigo);
	}
	public void crearAerolinea(Aerolinea aerolinea){
		dominioDAO.crearAerolinea(aerolinea);
		return;
	}
	
	public List <Aerolinea> getAerolineas(){

		return dominioDAO.getAerolineas();
		
	}
	
	public Aerolinea getAerolineaXId(Integer id){
		return dominioDAO.getAerolineaXId(id);
	}
	
	public void editarAerolinea(Aerolinea aerolinea){
		dominioDAO.editarAerolinea(aerolinea);
		return;
	}
	
	public Destino getDestino(Integer idAgencia, Integer idOrigen,Integer idDestino,Integer kilogramos){
		
		return dominioDAO.getDestino(idAgencia,idOrigen,idDestino,kilogramos);
	}
	
	
	public Aerolinea getAerolineaXCodigo(String codigo){
		return dominioDAO.getAerolineaXCodigo(codigo);
	}
	
	public Aerolinea getAerolineaXPrefijo(String prefijo){
		return dominioDAO.getAerolineaXPrefijo(prefijo);
		
	}
	
	public void saveTipoFlor(Dominio dominio, List <Dominio> grados){
		dominioDAO.saveTipoFlor(dominio,grados);
	}
	
	public void editarTipoFlor(Dominio dominio, List <Dominio> grados){
		dominioDAO.editarTipoFlor(dominio,grados);
	}
	
	public List <ProductoGrados> getGradosFlor (Integer idFlor){
		return dominioDAO.getGradosFlor (idFlor);
	}
	
	@Transactional
	public void saveVariedad(Dominio dominio , InputStream foto,String carpeta,String extencion){
		dominioDAO.saveDominio(dominio);
		if(foto != null && extencion != null){
			ArchivoUtils.crearArchivo(carpeta + dominio.getId() + extencion,foto);
			dominio.setUrl(dominio.getId() + extencion);
			dominioDAO.editarDominio(dominio);
		}else {
			dominio.setUrl("default.jpg");
			dominioDAO.editarDominio(dominio);
		}
		//crea el producto
		List <ProductoGrados> grados = dominioDAO.getGradosFlor(dominio.getDominiopadre());
		for(ProductoGrados productoGrado:grados){
			com.bulls.astoria.persistence.Producto pro = new com.bulls.astoria.persistence.Producto();
			pro.setCodigo(this.getDominio(dominio.getDominiopadre()).getCodigo() + dominio.getCodigo()+this.getDominio(productoGrado.getIdgrado()).getNomcorto());
			pro.setIdgrado(productoGrado.getIdgrado());
			pro.setIdvariedad(dominio.getId());
			dominioDAO.crearEditarProducto(pro);
		}
	}
	
	
	@Transactional
	public void editarVariedad(Dominio dominio , InputStream foto,String carpeta,String extencion){
		dominioDAO.editarDominio(dominio);
		if(foto != null && extencion != null){
			ArchivoUtils.crearArchivo(carpeta + dominio.getId() + extencion,foto);
			dominio.setUrl(dominio.getId() + extencion);
			dominioDAO.editarDominio(dominio);
		}	else {
			dominioDAO.editarDominio(dominio);
		}
	}
	
	public com.bulls.astoria.persistence.Producto getProductoVariedadGrado(Integer idvariedad,Integer idgrado){
		return dominioDAO.getProductoVariedadGrado(idvariedad, idgrado);
	}
	public List <com.bulls.astoria.persistence.Producto> getProductosAll (){
		return dominioDAO.getProductosAll();
	}
	public List <com.bulls.astoria.persistence.Producto> getProductosEnabled (){
		return dominioDAO.getProductosEnabled();
		
	}
	
	public void actualizarProducto(Integer idvariedad,Integer idgrado, String codigo, boolean enabled){
		com.bulls.astoria.persistence.Producto producto = dominioDAO.getProductoVariedadGrado(idvariedad, idgrado);
		if(producto!= null){
		   
			producto.setCodigo(codigo);
			producto.setEnabled(enabled);
			producto.setIdgrado(idgrado);
			producto.setIdvariedad(idvariedad);
			dominioDAO.crearEditarProducto(producto);
		}else {
			com.bulls.astoria.persistence.Producto productonuevo = new com.bulls.astoria.persistence.Producto();
			productonuevo.setCodigo(codigo);
			productonuevo.setEnabled(enabled);
			productonuevo.setIdgrado(idgrado);
			productonuevo.setIdvariedad(idvariedad);
			dominioDAO.crearEditarProducto(productonuevo);
		}
		
	}
	
	public void crearGradoMultiple(Dominio dominio,List <Dominio> grados){
		dominioDAO.crearGradoMultiple(dominio, grados);
		return;
	}
	
	public List <GradoMultiple> getGradosMultiples(Integer idGrado){
		return dominioDAO.getGradosMultiples(idGrado);
	}
	
	public void eliminarGradoMultiple(Dominio dominio){
		dominioDAO.eliminarGradoMultiple(dominio);
		return;
	}
	
	public void crearRuta(Ruta ruta){
		dominioDAO.crearRuta(ruta);
	}
	public void eliminarRuta(Integer idciudadorigen, Integer idciudaddestino){
		dominioDAO.eliminarRuta(idciudadorigen, idciudaddestino);
	}
	
	public List <Ruta> getListaRutas(){
		return dominioDAO.getListaRutas();
	}
	
	public Ruta getRutaXId(Integer id){
		return dominioDAO.getRutaXId(id);
	}
	public com.bulls.astoria.persistence.Producto getProductoXId(Integer idProducto){
		return dominioDAO.getProductoXId(idProducto);
	}
	
	public Dominio getDominiosXCodigo(String codigo,Integer tipoDominio){
		return dominioDAO.getDominiosXCodigo(codigo,tipoDominio);
	}
	public Dominio getDominiosXCodigoId(String codigo,Integer id,Integer tipoDominio){
		return dominioDAO.getDominiosXCodigoId(codigo,id,tipoDominio);
	}
	
	public List getProductosCompletoPlantacion(Integer idPlantacion){
		return dominioDAO.getProductosCompletoPlantacion(idPlantacion);
	}
	
	public com.bulls.astoria.persistence.Producto getProductoXCodigo(String codigo){
		return dominioDAO.getProductoXCodigo(codigo);
	}
	
	public List <Map> getAgenciasDestino(Integer idPaisOrigen,Integer idPaisDestino,Integer idCiudadOrigen,Integer idCiudadDestino,Integer numdia){
		return dominioDAO.getAgenciasDestino(idPaisOrigen, idPaisDestino, idCiudadOrigen, idCiudadDestino, numdia); 
	}
	public List <Map> getAerolineasAgenciaDestino(Integer idAgencia,Integer idPaisOrigen,Integer idPaisDestino,Integer idCiudadOrigen,Integer idCiudadDestino,Integer numdia){
		return dominioDAO.getAerolineasAgenciaDestino(idAgencia, idPaisOrigen, idPaisDestino, idCiudadOrigen, idCiudadDestino, numdia);
	}
	
	public List <Destino> getDestino(Integer idAgencia,Integer idAerolinea,Integer idPaisOrigen,Integer idPaisDestino,Integer idCiudadOrigen,Integer idCiudadDestino,Integer numdia){
		return dominioDAO.getDestino(idAgencia, idAerolinea, idPaisOrigen, idPaisDestino, idCiudadOrigen, idCiudadDestino, numdia);
	}
	
	public List <Map> getProductosTipoFlor(Integer idTipoFlor){
		return dominioDAO.getProductosTipoFlor(idTipoFlor);
	}
	
	public Comision getComisionProductoPais(Integer idtipoflorgrados,Integer idPais){
		return dominioDAO.getComisionProductoPais(idtipoflorgrados, idPais);
	}
	
	public void editComision(Comision comision){
		dominioDAO.editComision(comision);
	}
	
	public void crearNota(Nota nota){
		dominioDAO.crearNota(nota);
	}
	public List <Nota> getNotas(){
		return dominioDAO.getNotas();
	}
	
	public List <Nota> getNotasAprobadas(){
		return dominioDAO.getNotasAprobadas();
	}
	public List <Nota> getNotasRechazadas(){
		return dominioDAO.getNotasRechazadas();
	}
	public List <Nota> getNotasCliente(Integer id){
		return dominioDAO.getNotasCliente(id);
	}
	public List <Nota> getNotasAprobadasCliente(Integer id){
		return dominioDAO.getNotasAprobadasCliente(id);
	}
	public List <Nota> getNotasRechazadasCliente(Integer id){
		return dominioDAO.getNotasRechazadasCliente(id);
	}
	public List <Nota> getNotasPlantacion(Integer id){
		return dominioDAO.getNotasPlantacion(id);
	}
	public List <Nota> getNotasAprobadasPlantacion(Integer id){
		return dominioDAO.getNotasAprobadasPlantacion(id);
	}
	public List <Nota> getNotasRechazadasPlantacion(Integer id){
		return dominioDAO.getNotasRechazadasPlantacion(id);
	}
	public List <Nota> getNotasAgencia(Integer id){
		return dominioDAO.getNotasAgencia(id);
	}
	public List <Nota> getNotasAprobadasAgencia(Integer id){
		return dominioDAO.getNotasAprobadasAgencia(id);
	}
	public List <Nota> getNotasRechazadasAgencia(Integer id){
		return dominioDAO.getNotasRechazadasAgencia(id);
	}
	public List <Nota> getNotasGenericoCliente(Integer idCliente,Integer tipo,Integer estado){
		return dominioDAO.getNotasGenericoCliente(idCliente,tipo,estado);
	}
	public List <Nota> getNotasGenericoPlantacion(Integer idPlantacion,Integer tipo,Integer estado){
		return dominioDAO.getNotasGenericoPlantacion(idPlantacion,tipo,estado);
	}
	public List <Nota> getNotasGenericoAgencia(Integer idAgencia,Integer tipo,Integer estado){
		return dominioDAO.getNotasGenericoAgencia(idAgencia,tipo,estado);
	}
	public List <Nota> getNotasGenericoHandler(Integer idHandler,Integer tipo,Integer estado){
		return dominioDAO.getNotasGenericoHandler(idHandler,tipo,estado);
	}
	
	public Nota getNotaxId(Integer id){
		return dominioDAO.getNotaxId(id);
	}
	public void editNota(Nota nota){
		dominioDAO.editNota(nota);
	}
	public List <Nota> getNotasPedido(Integer idpedido){
		return dominioDAO.getNotasPedido(idpedido);
	}
	public ProductoGrados getTipoFlorGrados(Integer idTipoFlor,Integer idGrado){
		return dominioDAO.getTipoFlorGrados(idTipoFlor, idGrado);
	}
	
	public Comision getComision(Integer idProducto, Integer idPais) {
		com.bulls.astoria.persistence.Producto pro= this.getProductoXId(idProducto);
    	Dominio dom = this.getDominio(pro.getIdvariedad());
    	ProductoGrados pg = this.getTipoFlorGrados(dom.getDominiopadre(),pro.getIdgrado());
    	Comision com = this.getComisionProductoPais(pg.getId() , idPais);
    	return com;
	}
	
	public List <Map> getVariedadesFlorColor(Integer idTipoFlor,Integer idColor){
		return dominioDAO.getVariedadesFlorColor(idTipoFlor, idColor);
	}
	
	public List <Nota> getEstadoDeCuenta(Integer idEntidad,String entidad, Date fechaHasta){
		return dominioDAO.getEstadoDeCuenta(idEntidad, entidad, fechaHasta);
	}
	public List <Nota> getEstadoDeCuentaGeneral(Integer idEntidad,String entidad,Date fechaInicial,Date fechaFinal){
		return dominioDAO.getEstadoDeCuentaGeneral(idEntidad,entidad,fechaInicial,fechaFinal);
	}
	public List <Nota> getEstadoDeCuentaGeneralPendiente(Integer idEntidad,String entidad){
		return dominioDAO.getEstadoDeCuentaGeneralPendiente(idEntidad,entidad);
	}
	
	public Double getSaldo(Integer idEntidad,String entidad, String tiposaldo){
		return dominioDAO.getSaldo(idEntidad,entidad,tiposaldo);
	}
	
	public Double getSaldoFecha(Integer idEntidad,String entidad, String tiposaldo, Date fecha){
		return dominioDAO.getSaldoFecha(idEntidad,entidad,tiposaldo,fecha);
	}
	public List <Map> getSaldoEntidadFecha(String entidad,Date fecha){
		return dominioDAO.getSaldoEntidadFecha(entidad,fecha);
	}
	public List <Map> getConsolidadoPedido(Date fechadeDesde,Date fechaHasta,String tipo){
		return dominioDAO.getConsolidadoPedido(fechadeDesde, fechaHasta, tipo);
	}
	public void agregarGrado(Integer idTipoFlor,Integer idGrado){
		dominioDAO.agregarGrado(idTipoFlor, idGrado);
	}
	
	public Permiso getPermiso(Integer idProceso, String tipo, Integer idRole){
		return dominioDAO.getPermiso(idProceso, tipo, idRole);
	}
    public List<Permiso> getPermisos(Integer idRole){
    	return dominioDAO.getPermisos(idRole);
    }
    
    public void putAuditoria(Auditoria auditoria){
    	dominioDAO.putAuditoria(auditoria);
    	return;
    }
    
    public List <Auditoria> getAuditorias(String usuario, String proceso, String accion){
    	return dominioDAO.getAuditorias(usuario, proceso, accion);
    }
    public List  getProductosCompleto(Integer idTipoFlor){
    	return dominioDAO.getProductosCompleto(idTipoFlor);
    }
    
    public Dominio getDominioPadreCodigo(Integer idPadre,String codigo){
    	return dominioDAO.getDominioPadreCodigo(idPadre, codigo);
    }
    public Dominio getDominioPadreNombre(Integer idPadre,String nombre){
    	return dominioDAO.getDominioPadreNombre(idPadre, nombre);
    }
    public Double getSaldoInicial(Integer idEntidad,String entidad, String tiposaldo,Date fecha){
    	return dominioDAO.getSaldoInicial(idEntidad,entidad,tiposaldo,fecha);
    }
}
