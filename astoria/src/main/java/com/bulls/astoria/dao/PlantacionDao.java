package com.bulls.astoria.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bulls.astoria.persistence.DetalleListaPrecio;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.FranjaPrecios;
import com.bulls.astoria.persistence.ListaPrecio;
import com.bulls.astoria.persistence.Plantacion;
import com.bulls.astoria.persistence.PlantacionPacking;
import com.bulls.astoria.persistence.PlantacionProducto;
import com.bulls.astoria.persistence.Proveedor;
import com.bulls.astoria.pojo.Grado;
import com.bulls.astoria.pojo.ListaPreciosFinal;
import com.bulls.astoria.pojo.Producto;
import com.bulls.astoria.pojo.ProductoPrecio;

public interface PlantacionDao {
	
	void crearPlantacion(Plantacion plantacion);
	List <Plantacion> getPlantaciones();
	Plantacion getPlantacion(Integer idPlantacion);
	public List <PlantacionProducto> getProductosPlantacion (Integer idPlantacion);
	public List <PlantacionPacking>  getGradosPlantacion (Integer idPlantacion);
	public void editarPlantacion (Plantacion plantacion,List<com.bulls.astoria.persistence.Producto> listaProductos ,List<Grado> listaGrados);
	void crearFranjaPrecios(FranjaPrecios franja);
	void editarFranjaPrecios(FranjaPrecios franja);
	List <FranjaPrecios> getFranjasPrecios(Integer idPlantacion);
	void eliminarFranjaPrecios(FranjaPrecios franja);
	void crearLista(ListaPrecio lista);
	void editarLista(ListaPrecio lista,Integer idLista);
	FranjaPrecios getFranja(Integer idPlantacion,Date fechaInicial, Date FechaFinal);
	List <ListaPrecio> getListaPreciosAll();
	List <ListaPrecio> getListaPrecios(Integer idPlantacion);
	List <DetalleListaPrecio> getDetalleListaPrecios(Integer idLista);
	DetalleListaPrecio getDetalleListaPreciosProducto(Integer idLista,Integer idProducto);
	FranjaPrecios getFranja(Integer idFranja);
	void eliminarFranjaPrecios(Integer idFranja);
	void eliminarListaPrecios(Integer id);
	public List <Plantacion> getPlantacionesPais(Integer idPais);
	public List <Map> getPaisesPlantaciones();
	public void crearProveedor(Proveedor proveedor);
	public void editarProveedor(Proveedor proveedor);
	public Proveedor getProveedor(Integer idproveedor);
	public List <Proveedor> getProveedores();
	public Map getProveedorPlantacion(Integer idPlantacion);
	public ListaPrecio getListaPreciosXId(Integer idLista);
	public List <Map> getListaPreciosTotal();
}
