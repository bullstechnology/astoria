package com.bulls.astoria.service;

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

public interface PlantacionService {
	
	public void crearPlantacion(Plantacion plantacion, List <com.bulls.astoria.persistence.Producto> productos, List <Dominio> grados);
	public void crearProveedor(Proveedor proveedor);
	public List <Plantacion> getPlantaciones ();
	public Plantacion getPlantacion (Integer idPlantacion);
	public List <PlantacionProducto> getProductosPlantacion (Integer idPlantacion);
	public List <PlantacionPacking>  getGradosPlantacion (Integer idPlantacion);
	public void editarPlantacion (Plantacion plantacion,List<com.bulls.astoria.persistence.Producto> listaDomProductos ,List<com.bulls.astoria.persistence.Producto> listaProductos,List<Dominio> listaDomGrados ,List<Grado> listaGrados);
	public Proveedor getProveedor (int idproveedor);
	public List<Proveedor> getProveedores ();
	public void editarProveedor (Proveedor proveedor);
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
	FranjaPrecios getFranja(Integer idFranja);
	void eliminarFranjaPrecios(Integer idFranja);
	void eliminarListaPrecios(Integer id);
	DetalleListaPrecio getDetalleListaPreciosProducto(Integer idLista,Integer idProducto);
	public List <Plantacion> getPlantacionesPais(Integer idPais);
	public List <Map> getPaisesPlantaciones();
	public Map getProveedorPlantacion(Integer idPlantacion);
	public ListaPrecio getListaPreciosXId(Integer idLista);
	public List <Map> getListaPreciosTotal();

}
