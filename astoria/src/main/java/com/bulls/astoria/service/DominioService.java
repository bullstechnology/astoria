package com.bulls.astoria.service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bulls.astoria.persistence.Auditoria;
import com.bulls.astoria.persistence.Comision;
import com.bulls.astoria.persistence.Empresa;
import com.bulls.astoria.persistence.Aerolinea;
import com.bulls.astoria.persistence.AgenciaCarga;
import com.bulls.astoria.persistence.Destino;
import com.bulls.astoria.persistence.Dominio;
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

public interface DominioService {
	
	Empresa getEmpresa();
	void editEmpresa(Empresa empresa);
	void saveDominio(Dominio dominio); 
	Dominio getDominio(Integer idDominio); 
	List <Dominio> getDominios(Integer idTipoDominio);
	TipoDominio getTipoDominio(Integer idTipoDominio);
	List <Dominio> getDominiosXNombre(String nombre);
	void editarDominio(Dominio dominio);
	List <Dominio> getDominiosXPadre(Integer idDominioPadre);
	List <Truck> getTrucks();
	void saveTruck(Truck truck);
	void editarTruck (Truck truck);
	List <Truck> getTruckXCodigo(String codigo);
	List <Handler> getHandlers();
	void saveHandler(Handler handler);
	void editarHandler (Handler handler);
	List <Handler> getHandlerXCodigo(String codigo);
	Handler getHandlerXId(Integer id);
	Truck getTruckXId(Integer id);
	void saveHandlerTruck(HandlerTruck ht);
	void deleteHandlerTruck(HandlerTruck ht);
	List <HandlerTruck> getHandlerTruck(Integer idHandler,Integer idTrucker);
	List  getProductos();
	List  getProductosCompleto();
	void crearAgencia (AgenciaCarga agencia);
	void editarAgencia(AgenciaCarga agencia);
	List <AgenciaCarga> getAgenciasCarga();
	AgenciaCarga getAgenciaXId(Integer idAgencia);
	AgenciaCarga getAgenciaXCod(String codigo);
	void crearAerolinea(Aerolinea aerolinea);
	List <Aerolinea> getAerolineas();
	Aerolinea getAerolineaXId(Integer id);
	void editarAerolinea(Aerolinea aerolinea);
	Destino getDestino(Integer idAgencia, Integer idOrigen,Integer idDestino,Integer kilogramos);
	Aerolinea getAerolineaXCodigo(String codigo);
	Aerolinea getAerolineaXPrefijo(String prefijo);
	void saveTipoFlor(Dominio dominio , List<Dominio> grados);
	void editarTipoFlor(Dominio dominio , List<Dominio> grados);
	List <ProductoGrados> getGradosFlor (Integer idFlor);
	void saveVariedad(Dominio dominio , InputStream foto,String carpeta,String extencion);
	void editarVariedad(Dominio dominio , InputStream foto,String carpeta,String extencion);
	com.bulls.astoria.persistence.Producto getProductoVariedadGrado(Integer idvariedad,Integer idgrado);
	List <com.bulls.astoria.persistence.Producto> getProductosAll ();
	List <com.bulls.astoria.persistence.Producto> getProductosEnabled ();
	
	void actualizarProducto(Integer idvariedad,Integer idgrado, String codigo, boolean enabled);
	void crearGradoMultiple(Dominio dominio,List <Dominio> grados);
	List <GradoMultiple> getGradosMultiples(Integer idGrado);
	void eliminarGradoMultiple(Dominio dominio);
	
	void crearRuta(Ruta ruta);
	void eliminarRuta(Integer idciudadorigen, Integer idciudaddestino);
	
	List <Ruta> getListaRutas();
	Ruta getRutaXId(Integer id);
	com.bulls.astoria.persistence.Producto getProductoXId(Integer idProducto);
	
	Dominio getDominiosXCodigo(String codigo,Integer tipoDominio);
	Dominio getDominiosXCodigoId(String codigo,Integer id,Integer tipoDominio);
	
	List getProductosCompletoPlantacion(Integer idPlantacion);
	com.bulls.astoria.persistence.Producto getProductoXCodigo(String codigo);
	
	List <Map> getAgenciasDestino(Integer idPaisOrigen,Integer idPaisDestino,Integer idCiudadOrigen,Integer idCiudadDestino,Integer numdia);
	List <Map> getAerolineasAgenciaDestino(Integer idAgencia,Integer idPaisOrigen,Integer idPaisDestino,Integer idCiudadOrigen,Integer idCiudadDestino,Integer numdia);
	public List <Destino> getDestino(Integer idAgencia,Integer idAerolinea,Integer idPaisOrigen,Integer idPaisDestino,Integer idCiudadOrigen,Integer idCiudadDestino,Integer numdia);
	
	public List <Map> getProductosTipoFlor(Integer idTipoFlor);
	public Comision getComisionProductoPais(Integer idtipoflorgrados,Integer idPais);
	void editComision(Comision comision);
	
	void crearNota(Nota nota);
	List <Nota> getNotas();
	List <Nota> getNotasAprobadas();
	List <Nota> getNotasRechazadas();
	List <Nota> getNotasCliente(Integer id);
	List <Nota> getNotasAprobadasCliente(Integer id);
	List <Nota> getNotasRechazadasCliente(Integer id);
	List <Nota> getNotasPlantacion(Integer id);
	List <Nota> getNotasAprobadasPlantacion(Integer id);
	List <Nota> getNotasRechazadasPlantacion(Integer id);
	List <Nota> getNotasAgencia(Integer id);
	List <Nota> getNotasAprobadasAgencia(Integer id);
	List <Nota> getNotasRechazadasAgencia(Integer id);
	List <Nota> getNotasGenericoCliente(Integer idCliente,Integer tipo,Integer estado);
	List <Nota> getNotasGenericoPlantacion(Integer idPlantacion,Integer tipo,Integer estado);
	List <Nota> getNotasGenericoAgencia(Integer idAgencia,Integer tipo,Integer estado);
	List <Nota> getNotasGenericoHandler(Integer idHandler,Integer tipo,Integer estado);
	Nota getNotaxId(Integer id);
	void editNota(Nota nota);
	ProductoGrados getTipoFlorGrados(Integer idTipoFlor,Integer idGrado);
    Comision getComision(Integer idProducto,Integer idPais);
    List <Map> getVariedadesFlorColor(Integer idTipoFlor,Integer idColor);
    List <Nota> getEstadoDeCuenta(Integer idEntidad,String entidad, Date fechaHasta);
    List <Nota> getEstadoDeCuentaGeneral(Integer idEntidad,String entidad,Date fechaInicial,Date fechaFinal);
    List <Nota> getEstadoDeCuentaGeneralPendiente(Integer idEntidad,String entidad);
    Double getSaldo(Integer idEntidad,String entidad, String tiposaldo);
    Double getSaldoFecha(Integer idEntidad,String entidad, String tiposaldo,Date fecha);
    List <Map> getSaldoEntidadFecha(String entidad,Date fecha);
    List <Map> getConsolidadoPedido(Date fechadeDesde,Date fechaHasta,String tipo);
    void agregarGrado(Integer idTipoFlor,Integer idGrado);
    Permiso getPermiso(Integer idProceso, String tipo, Integer idRole);
    List<Permiso> getPermisos(Integer idRole);
    public void putAuditoria(Auditoria auditoria);
    public List <Auditoria> getAuditorias(String usuario, String proceso, String accion);
    public List  getProductosCompleto(Integer idTipoFlor);
    public Dominio getDominioPadreCodigo(Integer idPadre,String codigo);
    public Dominio getDominioPadreNombre(Integer idPadre,String nombre);
    public List <Nota> getNotasPedido(Integer idpedido);
    public Double getSaldoInicial(Integer idEntidad,String entidad, String tiposaldo,Date fecha);
    
    
    public List  getProductosCompleto(Integer idTipoFlor, Integer idVariedad);
    public List  getProductosCompletoFlor(Integer idTipoFlor);
    
    
 }

