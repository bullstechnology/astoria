package com.bulls.astoria.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bulls.astoria.enumeracion.EnuDominio;
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


@Repository("plantacionDao")
public class PlantacionDaoImpl extends AbstractDao implements PlantacionDao{
	
	@Transactional
	public void crearPlantacion(Plantacion plantacion){
		persist(plantacion);
	}
	public void crearProveedor(Proveedor proveedor){

		persist(proveedor);
	}
	
	public Proveedor getProveedor(Integer idproveedor){
		Query query = getSession().createQuery("from Proveedor p where idproveedor = :id ");
	    query.setInteger("id", idproveedor);
	    Proveedor proveedor  = (Proveedor) query.uniqueResult();
		return proveedor;
	}
	
	public List <Proveedor> getProveedores(){

		Query query = getSession().createQuery("from Proveedor p order by p.nombre");
		return query.list();
	}
	
	public List <Plantacion> getPlantaciones(){

		Query query = getSession().createQuery("from Plantacion p order by p.nombre");
		return query.list();
	}
	
	public Plantacion getPlantacion(Integer idPlantacion){

		Query query = getSession().createQuery("from Plantacion p where id = :id ");
	    query.setInteger("id", idPlantacion);
	    Plantacion plantacion  = (Plantacion) query.uniqueResult();
		return plantacion;
	}
	
	public List <PlantacionProducto> getProductosPlantacion (Integer idPlantacion){
		Query query = getSession().createQuery("from PlantacionProducto  p where p.idplantacion = :id ");
	    query.setInteger("id", idPlantacion);
	    return query.list();
	}
	

	public List <PlantacionPacking>  getGradosPlantacion (Integer idPlantacion){
		Query query = getSession().createQuery("from PlantacionPacking  p where p.idplantacion = :id ");
	    query.setInteger("id", idPlantacion);
	    return query.list();
	}

	@Transactional
	public void editarPlantacion(Plantacion plantacion,List<com.bulls.astoria.persistence.Producto> listaProductos, List<Grado> listaGrados) {
		
		// TODO Auto-generated method stub
		
		getSession().update(plantacion);
	    getSession().flush();
	    
	    Query q = getSession().createQuery("delete PlantacionProducto where idplantacion = :id");
	    q.setInteger("id", plantacion.getId());
	    q.executeUpdate();
	    getSession().flush();
	    
	    Query q2 = getSession().createQuery("delete PlantacionPacking where idplantacion = :id");
	    q2.setInteger("id", plantacion.getId());
	    q2.executeUpdate();
	    getSession().flush();
	    
	    for (com.bulls.astoria.persistence.Producto producto:listaProductos){
	    	
	    	PlantacionProducto pp = new PlantacionProducto();
	    	pp.setIdplantacion(plantacion.getId());
	    	pp.setIdproducto(producto.getId());
	    	pp.setCaracteristica(producto.getRecomendado().charAt(0));
	    	getSession().persist(pp);
		    getSession().flush();
	    }
	    
        for (Grado grado:listaGrados){
	    	
	    	PlantacionPacking pp = new PlantacionPacking();
	    	pp.setIdplantacion(plantacion.getId());
	    	pp.setIdgrado(grado.getId());
	    	pp.setCantidad(grado.getCantidad());
	    	getSession().persist(pp);
		    getSession().flush();
	    }
	    
        getSession().flush();
	
	}
	
	@Transactional
	public void editarProveedor(Proveedor proveedor) {
		
		// TODO Auto-generated method stub
		
		getSession().update(proveedor);
	    getSession().flush();

	
	}
	
	public void crearFranjaPrecios(FranjaPrecios franja){
		getSession().persist(franja);
	}
	
	public void editarFranjaPrecios(FranjaPrecios franja){
		getSession().update(franja);
		getSession().flush();
	}
	
	public List <FranjaPrecios> getFranjasPrecios(Integer idPlantacion){
		Query query = getSession().createQuery("from FranjaPrecios f where f.idplantacion = :idPlantacion");
	    query.setInteger("idPlantacion", idPlantacion);
	    return query.list();
	}
	
	public void eliminarFranjaPrecios(FranjaPrecios franja){
		getSession().delete(franja);
	}
	@Transactional
	public void crearLista(ListaPrecio lista){
		getSession().persist(lista);
		List<DetalleListaPrecio> detalles = new ArrayList<DetalleListaPrecio>(lista.getDetalleListaPrecio());
		for (DetalleListaPrecio detalle : detalles ){
			detalle.setListaPrecio(lista);
			getSession().persist(detalle);
		}
		return;
	}
	
	@Transactional
	public void editarLista(ListaPrecio lista,Integer idLista){
		
		Query q = getSession().createQuery("delete DetalleListaPrecio d where d.listaPrecio.id=:idLista");
	    q.setInteger("idLista", idLista);
	    q.executeUpdate();
	    getSession().flush();	
				
	    q = getSession().createQuery("delete ListaPrecio d where d.id=:idLista");
	    q.setInteger("idLista", idLista);
	    q.executeUpdate();
	    getSession().flush();			
				
				
		
		getSession().persist(lista);
		List<DetalleListaPrecio> detalles = new ArrayList<DetalleListaPrecio>(lista.getDetalleListaPrecio());
		for (DetalleListaPrecio detalle : detalles ){
			detalle.setListaPrecio(lista);
			getSession().persist(detalle);
		}
		return;
	}
	
	
	public FranjaPrecios getFranja(Integer idPlantacion,Date fechaInicial, Date fechaFinal){
		Query query = getSession().createQuery("from FranjaPrecios f where f.idplantacion = :idPlantacion and (f.fechaini <= :fechaInicial and f.fechafin >= :fechaInicial) or (f.fechaini <= :fechaFinal and f.fechafin >= :fechaFinal) ");
	    query.setDate("fechaInicial", fechaInicial);
	    query.setDate("fechaFinal", fechaFinal);
	    FranjaPrecios franja  = (FranjaPrecios) query.uniqueResult();
	    return franja;
	}
	public  List <ListaPrecio> getListaPreciosAll(){
		Query query = getSession().createQuery("from ListaPrecio");
	    return query.list();
		
	}
	public  List <ListaPrecio> getListaPrecios(Integer idPlantacion){
		Query query = getSession().createQuery("from ListaPrecio l where l.idplantacion = :idPlantacion");
	    query.setInteger("idPlantacion", idPlantacion);
	    return query.list();
	}
	public  List <DetalleListaPrecio> getDetalleListaPrecios(Integer idLista){
		/*Query query = getSession().createQuery("from DetalleListaPrecio d where d.idlista = :idLista");
	    query.setInteger("idLista", idLista);
	    return query.list();*/
		Query query = getSession().createQuery("from ListaPrecio d where d.id = :idLista");
	    query.setInteger("idLista", idLista);
	    ListaPrecio lp =  (ListaPrecio) query.uniqueResult();
	    List<DetalleListaPrecio> list = new ArrayList<DetalleListaPrecio>(lp.getDetalleListaPrecio());
	    return list;
	    
	}
	public  DetalleListaPrecio getDetalleListaPreciosProducto(Integer idLista,Integer idProducto){

		Query query = getSession().createQuery("from DetalleListaPrecio d where d.listaPrecio.id = :idLista and d.idproducto = :idproducto");
	    query.setInteger("idLista", idLista);
	    query.setInteger("idproducto", idProducto);

	    return (DetalleListaPrecio) query.uniqueResult();
	    
	}
	
	public  ListaPrecio getListaPreciosXId(Integer idLista){
		Query query = getSession().createQuery("from ListaPrecio d where d.id = :idLista");
	    query.setInteger("idLista", idLista);
	    return (ListaPrecio) query.uniqueResult();
	}
	
	public FranjaPrecios getFranja(Integer idFranja){
		Query query = getSession().createQuery("from FranjaPrecios f where f.id = :idFranja");
	    query.setInteger("idFranja", idFranja);
	    FranjaPrecios franja  = (FranjaPrecios) query.uniqueResult();
	    return franja;
	}
	
	public void eliminarFranjaPrecios(Integer idFranja){
		 Query q2 = getSession().createQuery("delete FranjaPrecios where id = :id");
		 q2.setInteger("id", idFranja);
		 q2.executeUpdate();
		 getSession().flush();
	}

	@Transactional 
	public void eliminarListaPrecios(Integer id){
		 Query q2 = getSession().createQuery("delete ListaPrecio where id = :id");
		 q2.setInteger("id", id);
		 q2.executeUpdate();
		 getSession().flush();
		 
		 
		 Query q3= getSession().createQuery("delete DetalleListaPrecio where listaPrecio.id = :id");
		 q3.setInteger("id", id);
		 q3.executeUpdate();
		 getSession().flush();
	}
	
	public List <Plantacion> getPlantacionesPais(Integer idPais){
		Query query = getSession().createQuery("from Plantacion p where p.pais = :idPais order by p.nombre");
		query.setInteger("idPais", idPais);
		return query.list();
	}
	
	
	public List <Map> getPaisesPlantaciones(){
		Query query = getSession().createQuery("select distinct new map(pais.id as idpais,pais.nomcorto as nompais,pais.codigo as codigo) from Plantacion pla, Dominio pais where pla.pais = pais.id order by pais.nomcorto");
		return query.list();
	}
	
	public Map getProveedorPlantacion(Integer idPlantacion){
		    String sql = "select distinct new Map(p.idproveedor as id, p.saldo as saldo, p.cupo as cupo, p.nombre as nombre) from Proveedor p , Plantacion pla  where p.idproveedor = pla.idproveedor and pla.id = :idplantacion";
			Query query = getSession().createQuery(sql);
			query.setInteger("idplantacion", idPlantacion);
			return (Map)query.uniqueResult();
	}
	
	public List <Map> getListaPreciosTotal(){
	    String sql = "select  new Map(dompais.nomcorto as pais, plantacion.nombre as plantacion,"
	    		+ "domvariedad.nomcorto as variedad ,domgrado.nomcorto as grado , domcolor.nomcorto as color,"
	    		+ "franjaprecios.titulo as franja,franjaprecios.fechaini as fecha_inicial,franjaprecios.fechafin as fecha_final, detallelistaprecios.precio as precio) from "
	    		
	    		+ "Dominio as dompais,Dominio as domvariedad, Dominio as domgrado, Dominio as domcolor,Plantacion as plantacion,ListaPrecio as listaprecios,"
	    		+ "FranjaPrecios as franjaprecios,DetalleListaPrecio detallelistaprecios,Producto as producto "
	    		+ "where  dompais.id = plantacion.pais and "
	    		+ "listaprecios.idplantacion = plantacion.id and "
	    		+ "listaprecios.idfranja= franjaprecios.id and "
	    		+ "detallelistaprecios.listaPrecio.id = listaprecios.id and "
	    		+ "producto.id = detallelistaprecios.idproducto and "
	    		+ "domvariedad.id = producto.idvariedad  and "
	    		+ "domgrado.id = producto.idgrado and "
	    		+ "domcolor.id = detallelistaprecios.idcolor ";
	    		
	    		Query query = getSession().createQuery(sql);
					
	    		System.out.println("XXXXXXXXXXXXXXXXXXX  llamando el query XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
	    		return query.list();
}
	
	
	

}
