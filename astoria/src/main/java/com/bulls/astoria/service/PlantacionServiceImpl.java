package com.bulls.astoria.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bulls.astoria.dao.PlantacionDao;
import com.bulls.astoria.dao.UserDao;
import com.bulls.astoria.persistence.DetalleListaPrecio;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.FranjaPrecios;
import com.bulls.astoria.persistence.ListaPrecio;
import com.bulls.astoria.persistence.Persona;
import com.bulls.astoria.persistence.Plantacion;
import com.bulls.astoria.persistence.PlantacionPacking;
import com.bulls.astoria.persistence.PlantacionProducto;
import com.bulls.astoria.persistence.Proveedor;
import com.bulls.astoria.pojo.Grado;
import com.bulls.astoria.pojo.ListaPreciosFinal;
import com.bulls.astoria.pojo.Producto;
import com.bulls.astoria.pojo.ProductoPrecio;



@Service("PlantacionService")
@Transactional
public class PlantacionServiceImpl implements PlantacionService{
	
	
	@Autowired
    private PlantacionDao plantacionDao;
	
	@Transactional
	public void crearPlantacion(Plantacion plantacion, List <com.bulls.astoria.persistence.Producto> productos, List <Dominio> grados){
      
		//crearPlantacion
		
		
		Set<Dominio> targeGrados = new HashSet<Dominio>(grados);
		Set<com.bulls.astoria.persistence.Producto> targeProductos = new HashSet<com.bulls.astoria.persistence.Producto>(productos);
		plantacion.setProductos(targeProductos);
		plantacion.setGrados(targeGrados);
		plantacionDao.crearPlantacion(plantacion);
		
        
    }
	
	@Transactional
	public void crearProveedor(Proveedor proveedor){
		plantacionDao.crearProveedor(proveedor);
    }
	
	public List <Plantacion> getPlantaciones (){
		return plantacionDao.getPlantaciones();
	}
	
	public Plantacion getPlantacion (Integer idPlantacion){
	
		return plantacionDao.getPlantacion(idPlantacion);
	}
	
	public List <PlantacionProducto> getProductosPlantacion (Integer idPlantacion){
		return plantacionDao.getProductosPlantacion(idPlantacion);  
	}
	public List <PlantacionPacking>  getGradosPlantacion (Integer idPlantacion){
		return plantacionDao.getGradosPlantacion(idPlantacion); 
	}
	
	
	public void editarPlantacion (Plantacion plantacion,List<com.bulls.astoria.persistence.Producto> listaDomProductos ,List<com.bulls.astoria.persistence.Producto> listaProductos,List<Dominio> listaDomGrados ,List<Grado> listaGrados){
		
		Set<Dominio> targeGrados = new HashSet<Dominio>(listaDomGrados);
		Set<com.bulls.astoria.persistence.Producto> targeProductos = new HashSet<com.bulls.astoria.persistence.Producto>(listaDomProductos);
		plantacion.setProductos(targeProductos);
		plantacion.setGrados(targeGrados);
		plantacionDao.editarPlantacion(plantacion, listaProductos ,listaGrados);
		return;
	}
	public void editarProveedor (Proveedor proveedor){

		plantacionDao.editarProveedor(proveedor);
	}
	
	public Proveedor getProveedor (int idproveedor){
		return plantacionDao.getProveedor(idproveedor);
	}
	public List<Proveedor> getProveedores (){
		return plantacionDao.getProveedores();
	}
	
	public void crearFranjaPrecios(FranjaPrecios franja){
		plantacionDao.crearFranjaPrecios(franja);
	}
	public void editarFranjaPrecios(FranjaPrecios franja){
		plantacionDao.editarFranjaPrecios(franja);
	}
	
	public List <FranjaPrecios> getFranjasPrecios(Integer idPlantacion){
		return plantacionDao.getFranjasPrecios(idPlantacion);
	}
	
	public void eliminarFranjaPrecios(FranjaPrecios franja){
		plantacionDao.eliminarFranjaPrecios(franja);
	}
	
	public void crearLista(ListaPrecio lista){
		plantacionDao.crearLista(lista);
		return;
	}
	
	public void editarLista(ListaPrecio lista,Integer idLista){
		plantacionDao.editarLista(lista,idLista);
		return;
	}
	
	public FranjaPrecios getFranja(Integer idPlantacion,Date fechaInicial, Date FechaFinal){
		return plantacionDao.getFranja(idPlantacion, fechaInicial, FechaFinal);
	}
	public List <ListaPrecio> getListaPreciosAll(){
		return plantacionDao.getListaPreciosAll();
	}
	public List <ListaPrecio> getListaPrecios(Integer idPlantacion){
		return plantacionDao.getListaPrecios(idPlantacion);
	}
	public List <DetalleListaPrecio> getDetalleListaPrecios(Integer idLista){
		return plantacionDao.getDetalleListaPrecios(idLista);
	}
	public FranjaPrecios getFranja(Integer idFranja){
		return plantacionDao.getFranja(idFranja);
	}
	public void eliminarFranjaPrecios(Integer idFranja){
		plantacionDao.eliminarFranjaPrecios(idFranja);
	}
	public void eliminarListaPrecios(Integer id){
		plantacionDao.eliminarListaPrecios(id);
	}
	
	public DetalleListaPrecio getDetalleListaPreciosProducto(Integer idLista,Integer idProducto){
		return plantacionDao.getDetalleListaPreciosProducto(idLista, idProducto);
	}
	
	public List <Plantacion> getPlantacionesPais(Integer idPais){
		return plantacionDao.getPlantacionesPais(idPais);
	}
	public List <Map> getPaisesPlantaciones(){
		return plantacionDao.getPaisesPlantaciones();
	}
	
	public Map getProveedorPlantacion(Integer idPlantacion){
		return plantacionDao.getProveedorPlantacion(idPlantacion);
	}
	
	public ListaPrecio getListaPreciosXId(Integer idLista){
		return plantacionDao.getListaPreciosXId(idLista);
	}
	
	public List <Map> getListaPreciosTotal(){
		return plantacionDao.getListaPreciosTotal();
	}

}
