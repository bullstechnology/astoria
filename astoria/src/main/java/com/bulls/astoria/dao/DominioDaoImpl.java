package com.bulls.astoria.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bulls.astoria.enumeracion.EnuDominio;
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
import com.bulls.astoria.persistence.ProductoGrados;
import com.bulls.astoria.persistence.Ruta;
import com.bulls.astoria.persistence.TipoDominio;
import com.bulls.astoria.persistence.Truck;
import com.bulls.astoria.pojo.Producto;


@Repository("dominioDao")
public class DominioDaoImpl extends AbstractDao implements DominioDao  {
	
	
	public void editEmpresa(Empresa empresa){
		getSession().saveOrUpdate(empresa);
		return;
	}
	
	public Empresa getEmpresa(){
		Query query = getSession().createQuery("from Empresa");
		return (Empresa) query.uniqueResult();
	}
	
	@Transactional
	public void saveDominio(Dominio dominio){
		persist(dominio);
	}
	
	@Transactional
	public Dominio getDominio(Integer idDominio){
		Query query = getSession().createQuery("from Dominio where id = :id");
		query.setInteger("id",idDominio);
		return (Dominio) query.uniqueResult();
	}
	
	@Transactional
	public List <Dominio> getDominios(Integer idTipoDominio){
		
		Query query = getSession().createQuery("from Dominio where tipodominio = :id");
		query.setInteger("id",idTipoDominio);
		return query.list();
	}
	
	public com.bulls.astoria.persistence.Producto getProductoXCodigo(String codigo){
		Query query = getSession().createQuery("from Producto where codigo = :codigo");
		query.setString("codigo",codigo);
		return (com.bulls.astoria.persistence.Producto) query.uniqueResult();
	}
	
	public TipoDominio getTipoDominio(Integer idTipoDominio){
		
		Query query = getSession().createQuery("from TipoDominio where id = :id");
		query.setInteger("id",idTipoDominio);
		return (TipoDominio) query.uniqueResult();

	}
	
	public List <Dominio> getDominiosXNombre(String nombre){
		
		Query query = getSession().createQuery("from Dominio where nomcorto = :nomcorto");
		query.setString("nomcorto",nombre);
		return query.list();
	}
	
	public void editarDominio(Dominio dominio){
		getSession().update(dominio);
		getSession().flush();
	}
	
	@Transactional
	public List <Dominio> getDominiosXPadre(Integer idDominioPadre){
		Query query = getSession().createQuery("from Dominio where dominiopadre = :idDominioPadre");
		query.setInteger("idDominioPadre",idDominioPadre);
		return query.list();
	}
	
	public List <Truck> getTrucks(){
		Query query = getSession().createQuery("from Truck");
		return query.list();
	}
	
	public void saveTruck(Truck truck){
		persist(truck);
	}
	
	public void editarTruck(Truck truck){
		getSession().update(truck);
		getSession().flush();
	}
	
	public List <Truck> getTruckXCodigo(String codigo){
		Query query = getSession().createQuery("from Truck where codigo = :codigo");
		query.setString("codigo",codigo);
		return query.list();
	}
	
	public List <Handler> getHandlers(){
		Query query = getSession().createQuery("from Handler");
		return query.list();
	}
	
	public void saveHandler(Handler handler){
		persist(handler);
	}
	
	public void editarHandler(Handler handler){
		getSession().update(handler);
		getSession().flush();
	}
	
	public List <Handler> getHandlerXCodigo(String codigo){
		Query query = getSession().createQuery("from Handler where codigo = :codigo");
		query.setString("codigo",codigo);
		return query.list();
	}
	
	public Handler getHandlerXId(Integer id){
		Query query = getSession().createQuery("from Handler where id = :id");
		query.setInteger("id",id);
		return (Handler) query.uniqueResult();
	}
	
	
	public Truck getTruckXId(Integer id){
		Query query = getSession().createQuery("from Truck where id = :id");
		query.setInteger("id",id);
		return (Truck) query.uniqueResult();
	}

	public void saveHandlerTruck(HandlerTruck ht){
		persist(ht);
	}
	public void deleteHandlerTruck(HandlerTruck ht){
		getSession().delete(ht);
		getSession().flush();
	}
	
	public List <HandlerTruck> getHandlerTruck(Integer idHandler,Integer idTruck){
		Query query = getSession().createQuery("from HandlerTruck where idhandler = :idHandler AND idtruck= :idTruck");
		query.setInteger("idHandler",idHandler);
		query.setInteger("idTruck",idTruck);
		return query.list();
	}
	
	public List  getProductos(){
		Query query = getSession().createQuery("select new map(p.id as idpadre,p.nomcorto as nompadre,h.id as idhijo ,h.nomcorto as nomhijo) from Dominio p, Dominio h where h.tipodominio = :idDominio and h.dominiopadre = p.id order by p.nomcorto");
		query.setInteger("idDominio",EnuDominio.VARIEDADES.getIdTipoDominio());
		return query.list();
	}
	
	public List  getProductosCompleto(){
		Query query = getSession().createQuery("select new map(pro.id as idproducto,pro.enabled as enabled,pro.codigo as codigo,flor.id as idpadre,flor.nomcorto as nompadre,var.id as idhijo ,var.nomcorto as nomhijo, gra.id as idgrado,gra.nomcorto as nomgrado) FROM Dominio flor ,Dominio var , Dominio gra , Producto pro where var.dominiopadre = flor.id and gra.id  = pro.idgrado and var.id = pro.idvariedad order by flor.nomcorto, var.nomcorto");
		return query.list();
	}
	public List  getProductosCompleto(Integer idVariedad){
		Query query = getSession().createQuery("select new map(pro.id as idproducto,pro.enabled as enabled,pro.codigo as codigo,flor.id as idpadre,flor.nomcorto as nompadre,var.id as idhijo ,var.nomcorto as nomhijo, gra.id as idgrado,gra.nomcorto as nomgrado) FROM Dominio flor ,Dominio var , Dominio gra , Producto pro where var.dominiopadre = flor.id and gra.id  = pro.idgrado and var.id = pro.idvariedad and var.id = :idVariedad order by flor.nomcorto, var.nomcorto");
		query.setInteger("idVariedad",idVariedad);
		return query.list();
	}
	
	public List  getProductosCompleto(Integer idTipoFlor, Integer idVariedad){
		Query query = getSession().createQuery("select new map(pro.id as idproducto,pro.enabled as enabled,pro.codigo as codigo,flor.id as idpadre,flor.nomcorto as nompadre,var.id as idhijo ,var.nomcorto as nomhijo, gra.id as idgrado,gra.nomcorto as nomgrado) FROM Dominio flor ,Dominio var , Dominio gra , Producto pro where var.dominiopadre = flor.id and gra.id  = pro.idgrado and var.id = pro.idvariedad and var.id = :idVariedad and var.dominiopadre = :idTipoFlor order by flor.nomcorto, var.nomcorto");
		query.setInteger("idTipoFlor",idTipoFlor);
		query.setInteger("idVariedad",idVariedad);
		return query.list();
	}
	
	public List  getProductosCompletoFlor(Integer idTipoFlor){
		Query query = getSession().createQuery("select new map(pro.id as idproducto,pro.enabled as enabled,pro.codigo as codigo,flor.id as idpadre,flor.nomcorto as nompadre,var.id as idhijo ,var.nomcorto as nomhijo, gra.id as idgrado,gra.nomcorto as nomgrado) FROM Dominio flor ,Dominio var , Dominio gra , Producto pro where var.dominiopadre = flor.id and gra.id  = pro.idgrado and var.id = pro.idvariedad and var.dominiopadre = :idTipoFlor order by flor.nomcorto, var.nomcorto");
		query.setInteger("idTipoFlor",idTipoFlor);
		return query.list();
	}

	
	public List  getProductosCompletoPlantacion(Integer idPlantacion){
		Query query = getSession().createQuery("select new map(flor.id as idpadre,flor.nomcorto as nompadre,var.id as idhijo ,var.nomcorto as nomhijo, gra.id as idgrado,gra.nomcorto as nomgrado,col.id as idcolor,col.nomcorto as nomcolor) FROM Dominio flor ,Dominio var , Dominio gra , Dominio col, Producto pro, PlantacionProducto pp where var.dominiopadre = flor.id and gra.id = pro.idgrado and var.idcolor = col.id and pp.idplantacion= :idplantacion and pp.idproducto = pro.id and var.id = pro.idvariedad order by flor.nomcorto, var.nomcorto");
		query.setInteger("idplantacion",idPlantacion);
		return query.list();
	}
	
	public com.bulls.astoria.persistence.Producto getProductoVariedadGrado(Integer idvariedad,Integer idgrado){
		Query query = getSession().createQuery("from Producto where idvariedad = :idvariedad and idgrado=:idgrado");
		query.setInteger("idvariedad",idvariedad);
		query.setInteger("idgrado",idgrado);
		return (com.bulls.astoria.persistence.Producto) query.uniqueResult();
	}
	
	
	public void crearAgencia(AgenciaCarga agencia){
		getSession().persist(agencia);
		List<Destino> list = new ArrayList<Destino>(agencia.getDestino());
        for(Destino destino : list){
        	destino.setAgencia(agencia);
        	getSession().persist(destino);
        }
        return;
	}
	
	
	public void editarAgencia(AgenciaCarga agencia){
		Query q = getSession().createQuery("delete Destino where idagencia = :id");
	    q.setInteger("id", agencia.getId());
	    q.executeUpdate();
	    getSession().flush();
		
		List<Destino> list = new ArrayList<Destino>(agencia.getDestino());
        for(Destino destino : list){
        	destino.setAgencia(agencia);
        	getSession().persist(destino);
        }
        getSession().flush();
        getSession().update(agencia);
        getSession().flush();
	}
	
	public List <AgenciaCarga> getAgenciasCarga(){
		Query query = getSession().createQuery("from AgenciaCarga");
		return query.list();
	}
	
	public AgenciaCarga getAgenciaXId(Integer idAgencia){
		Query query = getSession().createQuery("from AgenciaCarga where id = :id");
		query.setInteger("id",idAgencia);
		return (AgenciaCarga) query.uniqueResult();
	}
	public AgenciaCarga getAgenciaXCod(String codigo){
		Query query = getSession().createQuery("from AgenciaCarga where codigo = :codigo");
		query.setString("codigo",codigo);
		return (AgenciaCarga) query.uniqueResult();
	}
	
	@Transactional
	public void crearAerolinea(Aerolinea aerolinea){
		getSession().persist(aerolinea);
		return;
	}
	
	public List <Aerolinea> getAerolineas(){
		
		Query query = getSession().createQuery("from Aerolinea");
		return query.list();
		
	}
	
	public Aerolinea getAerolineaXId(Integer id){
		Query query = getSession().createQuery("from Aerolinea where id = :id");
		query.setInteger("id",id);
		return (Aerolinea) query.uniqueResult();
	}
	
	
	@Transactional
	public void editarAerolinea(Aerolinea aerolinea){
       getSession().update(aerolinea);
        getSession().flush();
	}
    
	public Destino getDestino(Integer idAgencia, Integer idOrigen,Integer idDestino,Integer kilogramos){
		
		Query query = getSession().createQuery("from Destino where idorigen =:idorigen and iddestino = :iddestino"
				+ " and rangoinicial < :kilogramos <= rangofinal and agencia.id = :idagencia");
		query.setInteger("idorigen",idOrigen);
		query.setInteger("iddestino",idDestino);
		query.setInteger("kilogramos",kilogramos);
		query.setInteger("idagencia",idAgencia);
		return (Destino) query.uniqueResult();
		
	}
	
	public Aerolinea getAerolineaXCodigo(String codigo){
		
		Query query = getSession().createQuery("from Aerolinea where codigo =:codigo");
		query.setString("codigo",codigo);
		return (Aerolinea) query.uniqueResult();
		
	}
	public Aerolinea getAerolineaXPrefijo(String prefijo){
		
		Query query = getSession().createQuery("from Aerolinea where prefijo =:prefijo");
		query.setString("prefijo",prefijo);
		return (Aerolinea) query.uniqueResult();
		
	}
	
	@Transactional
	public void saveTipoFlor(Dominio dominio,List <Dominio> grados){
		persist(dominio);
		for (Dominio grado : grados){
			ProductoGrados pro = new ProductoGrados();
			pro.setIdgrado(grado.getId());
			pro.setIdtipoflor(dominio.getId());
			persist(pro);
		}
		getSession().flush();
		return;
	}
	
	@Transactional
	public void editarTipoFlor(Dominio dominio,List <Dominio>grados){
		getSession().update(dominio);
		getSession().flush();
		

		/*for (Dominio grado : grados){
			
			
			Query q = getSession().createQuery("delete ProductoGrados where idtipoflor = :id and idgrado =:idgrado");
		    q.setInteger("id", dominio.getId());
		    q.setInteger("idgrado", grado.getId());
		    try {
		    q.executeUpdate();
		    getSession().flush();
		    System.out.println("*** se elimino grado " + grado.getCodigo());
			
			ProductoGrados pro = new ProductoGrados();
			pro.setIdgrado(grado.getId());
			pro.setIdtipoflor(dominio.getId());
			persist(pro);
			getSession().flush();
		    }catch (Exception e){
		    	System.out.println("*** se intento borrar un grado de un tipo de flor que tiene ya tiene constrain");
		    	e.printStackTrace();
		    }
		}*/
		
		return;
	}
	
	public List <ProductoGrados> getGradosFlor (Integer idFlor){
		Query query = getSession().createQuery("from ProductoGrados pg where pg.idtipoflor= :idFlor");
		query.setInteger("idFlor",idFlor);
		return query.list();
	}
	
	public List <com.bulls.astoria.persistence.Producto> getProductosAll (){
		Query query = getSession().createQuery("from Producto");
		return query.list();
	}
	
	public List <com.bulls.astoria.persistence.Producto> getProductosEnabled (){
		Query query = getSession().createQuery("from Producto pg where pg.enabled=:enabled");
		query.setBoolean("enabled", true);
		return query.list();
	}
	
	public void crearEditarProducto(com.bulls.astoria.persistence.Producto Producto){
		 getSession().saveOrUpdate(Producto);
	     getSession().flush();
	}
	
	@Transactional
	public void crearGradoMultiple(Dominio dominio,List <Dominio> grados){
		persist(dominio);
		for(Dominio dom: grados){
			GradoMultiple gm = new GradoMultiple();
			gm.setIdgrado(dominio.getId());
			gm.setIdgradocompone(dom.getId());
			persist(gm);
		}
	}
	
	public List <GradoMultiple> getGradosMultiples(Integer idGrado){
		Query query = getSession().createQuery("from GradoMultiple g where g.idgrado=:id");
		query.setInteger("id", idGrado);
		return query.list();
	}
	
	@Transactional
	public void eliminarGradoMultiple(Dominio dominio){
		getSession().delete(dominio);
		  Query q = getSession().createQuery("delete GradoMultiple where idgrado = :id");
		  q.setInteger("id", dominio.getId());
		  q.executeUpdate();
		  getSession().flush();
	}
	
	
	public void crearRuta(Ruta ruta){
		persist(ruta);
	}
	public void eliminarRuta(Integer idciudadorigen, Integer idciudaddestino){
		  Query q = getSession().createQuery("delete Ruta where idciudadorigen= :idciudadorigen and idciudaddestino =:idciudaddestino");
		  q.setInteger("idciudadorigen", idciudadorigen);
		  q.setInteger("idciudaddestino", idciudaddestino);
		  q.executeUpdate();
		  getSession().flush();
	}
	
	public List <Ruta> getListaRutas(){
		Query query = getSession().createQuery("from Ruta");
		return query.list();
	}
	
	public Ruta getRutaXId(Integer id){
		Query query = getSession().createQuery("from Ruta where id =:id");
		query.setInteger("id",id);
		return (Ruta) query.uniqueResult();
		
	}
	
	public com.bulls.astoria.persistence.Producto getProductoXId(Integer idProducto){
		Query query = getSession().createQuery("from Producto where id =:id");
		query.setInteger("id",idProducto);
		return (com.bulls.astoria.persistence.Producto) query.uniqueResult();
	}
	
	public Dominio getDominiosXCodigo(String codigo,Integer tipoDominio){
		Query query = getSession().createQuery("from Dominio where codigo=:codigo and tipodominio = :tipodominio");
		query.setString("codigo",codigo);
		query.setInteger("tipodominio",tipoDominio);
		return (Dominio) query.uniqueResult();
	}
	public Dominio getDominiosXCodigoId(String codigo,Integer id,Integer tipoDominio){
		Query query = getSession().createQuery("from Dominio where id <> :id and codigo = :codigo and tipodominio =:tipodominio" );
		query.setInteger("id",id);
		query.setString("codigo",codigo);
		query.setInteger("tipodominio",tipoDominio);
		return (Dominio) query.uniqueResult();
	}
	
	
	
	public List <Map> getAgenciasDestino(Integer idPaisOrigen,Integer idPaisDestino,Integer idCiudadOrigen,Integer idCiudadDestino,Integer numdia){
		StringBuffer strbuf= new StringBuffer("select new Map(ag.id as id,ag.nombre as nombre) from AgenciaCarga ag, Destino des where des.idorigen = :idorigen and des.iddestino = :iddestino and des.idciudadorigen = :idciudadorigen and des.idciudaddestino=:idciudaddestino and des.agencia.id = ag.id ");
		if(numdia!= null){
			if(Integer.valueOf(numdia).intValue() == 1)
				strbuf.append("and des.lunes =:dia");
			if(Integer.valueOf(numdia).intValue() == 2)
				strbuf.append("and des.martes =:dia");
			if(Integer.valueOf(numdia).intValue() == 3)
				strbuf.append("and des.miercoles =:dia");
			if(Integer.valueOf(numdia).intValue() == 4)
				strbuf.append("and des.jueves =:dia");
			if(Integer.valueOf(numdia).intValue() == 5)
				strbuf.append("and des.viernes =:dia");
			if(Integer.valueOf(numdia).intValue() == 6)
				strbuf.append("and des.sabado =:dia");
			if(Integer.valueOf(numdia).intValue() == 7)
				strbuf.append("and des.domingo =:dia");

			Query query = getSession().createQuery(strbuf.toString());
			query.setInteger("idorigen",idPaisOrigen);
			query.setInteger("iddestino",idPaisDestino);
			query.setInteger("idciudadorigen",idCiudadOrigen);
			query.setInteger("idciudaddestino",idCiudadDestino);
			query.setBoolean("dia",true);
			return query.list();	
		}else{
			Query query = getSession().createQuery(strbuf.toString());
			query.setInteger("idorigen",idPaisOrigen);
			query.setInteger("iddestino",idPaisDestino);
			query.setInteger("idciudadorigen",idCiudadOrigen);
			query.setInteger("idciudaddestino",idCiudadDestino);
			return query.list();
			
		}
		  
	
	}
	public List <Map> getAerolineasAgenciaDestino(Integer idAgencia, Integer idPaisOrigen,Integer idPaisDestino,Integer idCiudadOrigen,Integer idCiudadDestino,Integer numdia){
       StringBuffer strbuf= new StringBuffer("select new Map(al.id as id,al.nombre as nombre) from Destino des, Aerolinea al where des.idorigen = :idorigen and des.iddestino = :iddestino and des.idciudadorigen = :idciudadorigen and des.idciudaddestino=:idciudaddestino and des.agencia.id = :idagencia and des.idaerolinea = al.id");
       if(numdia!= null){
			if(Integer.valueOf(numdia).intValue() == 1)
				strbuf.append("and des.lunes =:dia");
			if(Integer.valueOf(numdia).intValue() == 2)
				strbuf.append("and des.martes =:dia");
			if(Integer.valueOf(numdia).intValue() == 3)
				strbuf.append("and des.miercoles =:dia");
			if(Integer.valueOf(numdia).intValue() == 4)
				strbuf.append("and des.jueves =:dia");
			if(Integer.valueOf(numdia).intValue() == 5)
				strbuf.append("and des.viernes =:dia");
			if(Integer.valueOf(numdia).intValue() == 6)
				strbuf.append("and des.sabado =:dia");
			if(Integer.valueOf(numdia).intValue() == 7)
				strbuf.append("and des.domingo =:dia");

			Query query = getSession().createQuery(strbuf.toString());
			query.setInteger("idorigen",idPaisOrigen);
			query.setInteger("iddestino",idPaisDestino);
			query.setInteger("idciudadorigen",idCiudadOrigen);
			query.setInteger("idciudaddestino",idCiudadDestino);
			query.setInteger("idagencia",idAgencia);
			query.setBoolean("dia",true);
			return query.list();	
		}else{
			Query query = getSession().createQuery(strbuf.toString());
			query.setInteger("idorigen",idPaisOrigen);
			query.setInteger("iddestino",idPaisDestino);
			query.setInteger("idciudadorigen",idCiudadOrigen);
			query.setInteger("idciudaddestino",idCiudadDestino);
			query.setInteger("idagencia",idAgencia);
			return query.list();
			
		}
	}
	
	public List <Destino> getDestino(Integer idAgencia,Integer idAerolinea,Integer idPaisOrigen,Integer idPaisDestino,Integer idCiudadOrigen,Integer idCiudadDestino,Integer numdia){
		StringBuffer strbuf= new StringBuffer("from Destino des where des.idorigen = :idorigen and des.iddestino = :iddestino and des.idciudadorigen = :idciudadorigen and des.idciudaddestino=:idciudaddestino and des.agencia.id = :idagencia and des.idaerolinea = :idaerolinea");
	       if(numdia!= null){
				if(Integer.valueOf(numdia).intValue() == 1)
					strbuf.append("and des.lunes =:dia");
				if(Integer.valueOf(numdia).intValue() == 2)
					strbuf.append("and des.martes =:dia");
				if(Integer.valueOf(numdia).intValue() == 3)
					strbuf.append("and des.miercoles =:dia");
				if(Integer.valueOf(numdia).intValue() == 4)
					strbuf.append("and des.jueves =:dia");
				if(Integer.valueOf(numdia).intValue() == 5)
					strbuf.append("and des.viernes =:dia");
				if(Integer.valueOf(numdia).intValue() == 6)
					strbuf.append("and des.sabado =:dia");
				if(Integer.valueOf(numdia).intValue() == 7)
					strbuf.append("and des.domingo =:dia");

				Query query = getSession().createQuery(strbuf.toString());
				query.setInteger("idorigen",idPaisOrigen);
				query.setInteger("iddestino",idPaisDestino);
				query.setInteger("idciudadorigen",idCiudadOrigen);
				query.setInteger("idciudaddestino",idCiudadDestino);
				query.setInteger("idagencia",idAgencia);
				query.setInteger("idaerolinea",idAerolinea);
				query.setBoolean("dia",true);
				return query.list();	
			}else{
				Query query = getSession().createQuery(strbuf.toString());
				query.setInteger("idorigen",idPaisOrigen);
				query.setInteger("iddestino",idPaisDestino);
				query.setInteger("idciudadorigen",idCiudadOrigen);
				query.setInteger("idciudaddestino",idCiudadDestino);
				query.setInteger("idagencia",idAgencia);
				query.setInteger("idaerolinea",idAerolinea);
				return query.list();
				
			}
	}
	
	public List <Map> getProductosTipoFlor(Integer idTipoFlor){
		String sql = "select distinct new Map(pro.id as idtipoflorgrados,domflor.id as idtipoflor, domflor.nomcorto as nombreflor , domgra.id as idgrado,domgra.nomcorto as nombregrado) from ProductoGrados pro, Dominio domflor ,Dominio domgra where pro.idtipoflor =:idTipoFlor and pro.idgrado = domgra.id and domflor.id=:idTipoFlor2"
				+ " and domgra.id=pro.idgrado";
		Query query = getSession().createQuery(sql);
		query.setInteger("idTipoFlor", idTipoFlor);
		query.setInteger("idTipoFlor2", idTipoFlor);
		return query.list();
	}
	
	public Comision getComisionProductoPais(Integer idtipoflorgrados,Integer idPais){
		
		String sql = "from Comision com where com.idtipoflorgrados=:idtipoflorgrados and com.idpais=:idPais";
		Query query = getSession().createQuery(sql);
		query.setInteger("idtipoflorgrados", idtipoflorgrados);
		query.setInteger("idPais", idPais);
		
		return (Comision) query.uniqueResult();
		
	}
	
	 public void editComision(Comision comision){
	    	getSession().saveOrUpdate(comision);
	 }
	 
	public  void crearNota(Nota nota){
		/*getSession().save(nota);
		getSession().flush();*/
		this.editNota(nota);
	}
	public  List <Nota> getNotas(){
		String sql = "from Nota where idEstado = 83";
		Query query = getSession().createQuery(sql);
		return query.list();
	}
	public  List <Nota> getNotasAprobadas(){
		String sql = "from Nota where idEstado = 84";
		Query query = getSession().createQuery(sql);
		return query.list();
	}
	public  List <Nota> getNotasRechazadas(){
		String sql = "from Nota where idEstado = 85";
		Query query = getSession().createQuery(sql);
		return query.list();
	}
	public  List <Nota> getNotasCliente(Integer idCliente){
		String sql = "from Nota where idEstado = 83 and idCliente:idCliente";
		Query query = getSession().createQuery(sql);
		query.setInteger("idCliente",idCliente);
		return query.list();
	}
	public  List <Nota> getNotasAprobadasCliente(Integer idCliente){
		String sql = "from Nota where idEstado = 84 and idCliente:idCliente";
		Query query = getSession().createQuery(sql);
		query.setInteger("idCliente",idCliente);
		return query.list();
	}
	public  List <Nota> getNotasRechazadasCliente(Integer idCliente){
		String sql = "from Nota where idEstado = 85 and idCliente:idCliente";
		Query query = getSession().createQuery(sql);
		query.setInteger("idCliente",idCliente);
		return query.list();
	}
	public  List <Nota> getNotasPlantacion(Integer idPlantacion){
		String sql = "from Nota where idEstado = 83 and idPlantacion:idPlantacion";
		Query query = getSession().createQuery(sql);
		query.setInteger("idPlantacion",idPlantacion);
		return query.list();
	}
	public  List <Nota> getNotasAprobadasPlantacion(Integer idPlantacion){
		String sql = "from Nota where idEstado = 84 and idPlantacion:idPlantacion";
		Query query = getSession().createQuery(sql);
		query.setInteger("idPlantacion",idPlantacion);
		return query.list();
	}
	public  List <Nota> getNotasRechazadasPlantacion(Integer idPlantacion){
		String sql = "from Nota where idEstado = 85 and idPlantacion:idPlantacion";
		Query query = getSession().createQuery(sql);
		query.setInteger("idPlantacion",idPlantacion);
		return query.list();
	}
	public  List <Nota> getNotasAgencia(Integer idAgencia){
		String sql = "from Nota where idEstado = 83 and idAgencia:idAgencia";
		Query query = getSession().createQuery(sql);
		query.setInteger("idAgencia",idAgencia);
		return query.list();
	}
	public  List <Nota> getNotasAprobadasAgencia(Integer idAgencia){
		String sql = "from Nota where idEstado = 84 and idAgencia:idAgencia";
		Query query = getSession().createQuery(sql);
		query.setInteger("idAgencia",idAgencia);
		return query.list();
	}
	public  List <Nota> getNotasRechazadasAgencia(Integer idAgencia){
		String sql = "from Nota where idEstado = 85 and idAgencia:idAgencia";
		Query query = getSession().createQuery(sql);
		query.setInteger("idAgencia",idAgencia);
		return query.list();
	}
	public  List <Nota> getNotasHandler(Integer idHandler){
		String sql = "from Nota where idEstado = 83 and idHandler:idHandler";
		Query query = getSession().createQuery(sql);
		query.setInteger("idHandler",idHandler);
		return query.list();
	}
	public  List <Nota> getNotasAprobadasHandler(Integer idHandler){
		String sql = "from Nota where idEstado = 84 and idAHandler:idHandler";
		Query query = getSession().createQuery(sql);
		query.setInteger("idHandler",idHandler);
		return query.list();
	}
	public  List <Nota> getNotasRechazadasHandler(Integer idHandler){
		String sql = "from Nota where idEstado = 85 and idAgencia:idHandler";
		Query query = getSession().createQuery(sql);
		query.setInteger("idHanlder",idHandler);
		return query.list();
	}
	
	public  List <Nota> getNotasGenericoCliente(Integer idCliente,Integer tipo,Integer estado){
		String sql = "from Nota where idEstado = :idEstado and idDebitoCredito = :tipo and idCliente = :idCliente";
		Query query = getSession().createQuery(sql);
		query.setInteger("idEstado",estado);
		query.setInteger("tipo",tipo);
		query.setInteger("idCliente",idCliente);
		return query.list();
	}
	public  List <Nota> getNotasGenericoPlantacion(Integer idPlantacion,Integer tipo,Integer estado){
		String sql = "from Nota where idEstado = :idEstado and idDebitoCredito = :tipo and idPlantacion = :idPlantacion";
		Query query = getSession().createQuery(sql);
		query.setInteger("idEstado",estado);
		query.setInteger("tipo",tipo);
		query.setInteger("idPlantacion",idPlantacion);
		return query.list();
	}
	public  List <Nota> getNotasGenericoAgencia(Integer idAgencia,Integer tipo,Integer estado){
		String sql = "from Nota where idEstado = :idEstado and idDebitoCredito = :tipo and idAgencia = :idAgencia";
		Query query = getSession().createQuery(sql);
		query.setInteger("idEstado",estado);
		query.setInteger("tipo",tipo);
		query.setInteger("idAgencia",idAgencia);
		return query.list();
	}
	public  List <Nota> getNotasGenericoHandler(Integer idHandler,Integer tipo,Integer estado){
		String sql = "from Nota where idEstado = :idEstado and idDebitoCredito = :tipo and idHandler = :idHandler";
		Query query = getSession().createQuery(sql);
		query.setInteger("idEstado",estado);
		query.setInteger("tipo",tipo);
		query.setInteger("idHandler",idHandler);
		return query.list();
	}
	
	public List <Nota> getNotasPedido(Integer idpedido){
		String sql = "from Nota where idpedido = :idpedido";
		Query query = getSession().createQuery(sql);
		query.setInteger("idpedido",idpedido);
		return query.list();
	}
	
	
	public List <Nota> getEstadoDeCuenta(Integer idEntidad,String entidad, Date fechaHasta){
		String sql = "from Nota where  ";
		if(entidad.equalsIgnoreCase("C")){
			sql = sql + "idCliente = :idEntidad";
		}else if(entidad.equalsIgnoreCase("P")){
			sql = sql + "idPlantacion = :idEntidad";
		}else if(entidad.equalsIgnoreCase("A")){
			sql = sql + "idAgencia = :idEntidad";
		}else if(entidad.equalsIgnoreCase("H")){
			sql = sql + "idHandler = :idHandler";
		}
		
		sql = sql + " and fecha <= :fecha and estado = 3 order by fecha desc";
		Query query = getSession().createQuery(sql);
		query.setInteger("idEntidad",idEntidad);
		query.setDate("fecha",fechaHasta);
		return query.list();
	}
	
	public List <Nota> getEstadoDeCuentaGeneral(Integer idEntidad,String entidad,Date fechaInicial,Date fechaFinal){
		
		
		
		if(idEntidad != null && entidad != null){  //verifica que no sea el consolidado, por el else va el consolidado
		
		
		
		if (!entidad.equalsIgnoreCase("Q")){
		Query query = null;
		String sql = "from Nota where  ";
		if(idEntidad != null && entidad != null){
			if(entidad.equalsIgnoreCase("C")){
				sql = sql + "idCliente = :idEntidad";
			}else if(entidad.equalsIgnoreCase("P")){
				sql = sql + "idPlantacion = :idEntidad";
			}else if(entidad.equalsIgnoreCase("A")){
				sql = sql + "idAgencia = :idEntidad";
			}else if(entidad.equalsIgnoreCase("H")){
				sql = sql + "idHandler = :idEntidad";
			}
			sql = sql + " and DATE(fecha) >=:fechaInicial and DATE(fecha) <= :fechaFinal";
			sql = sql + " and idEstado = 84 order by DATE(fecha) asc";
			query = getSession().createQuery(sql);
			query.setInteger("idEntidad",idEntidad);
			//query.setDate("fechaFinal",fechaFinal);
			query.setDate("fechaInicial",fechaInicial);
			query.setDate("fechaFinal",fechaFinal);

		}else {
				
			sql = sql + "DATE(fecha) >=:fechaInicial and DATE(fecha) <= :fechaFinal";
			sql = sql + " and idEstado = 84 order by DATE(fecha) asc";
			query = getSession().createQuery(sql);
			query.setDate("fechaInicial",fechaInicial);
			query.setDate("fechaFinal",fechaFinal);
			
		}
		
		return query.list();
		}else {
	    	String sql = "select pla.id from Plantacion pla where  pla.idproveedor = " + idEntidad;
	    	Query query = getSession().createQuery(sql);
	    	List <Integer >ids = query.list();
	    	
	    	String and = " idPlantacion in (";
	    	for(Integer id : ids){
	    		and = and + id +",";
	    	}
	    	and = and + "-1)";
	        sql = "from Nota where " + and ;
	    			
	        sql = sql + " and DATE(fecha) >=:fechaInicial and DATE(fecha) <= :fechaFinal";
			sql = sql + " and idEstado = 84 order by DATE(fecha) asc";
			query = getSession().createQuery(sql);
			//query.setDate("fechaFinal",fechaFinal);
			query.setTime("fechaInicial",fechaInicial);
			query.setTime("fechaFinal",fechaFinal);

			
			List list = query.list();
			
			return list;
			
	    }
		
		}else{ //opcion para el consolidado
			
			Query query = null;
			/*String sql = "from Nota where  ";
			sql = sql + "DATE(fecha) >=:fechaInicial and DATE(fecha) <= :fechaFinal";
			sql = sql + " and idEstado = 84 order by DATE(fecha) asc";
			
			Query query = getSession().createQuery(sql);
			//query = getSession().createQuery(sql);
			query.setTime("fechaInicial",fechaInicial);
			query.setTime("fechaFinal",fechaFinal);
			
            List list = query.list();*/
            
            
            String sql = "from Nota where  ";
            sql = sql + " fecha >=:fechaInicial and fecha <= :fechaFinal";
			sql = sql + " and idEstado = 84 order by fecha asc";
			query = getSession().createQuery(sql);
			//query.setInteger("idEntidad",idEntidad);
			//query.setDate("fechaFinal",fechaFinal);
			query.setDate("fechaInicial",fechaInicial);
			query.setDate("fechaFinal",fechaFinal);
			
			List list = query.list();
			
			return list;
		}
	    
	}
	
	public List <Nota> getEstadoDeCuentaGeneralPendiente(Integer idEntidad,String entidad){
		
		
		if(idEntidad != null && entidad != null){ //verifica opcion consolidado en el else va el consolidado
		
		if(!entidad.equalsIgnoreCase("Q")){
		Query query = null;
		String sql = "from Nota where  ";
		if(idEntidad != null && entidad != null){
			if(entidad.equalsIgnoreCase("C")){
				sql = sql + "idCliente = :idEntidad";
			}else if(entidad.equalsIgnoreCase("P")){
				sql = sql + "idPlantacion = :idEntidad";
			}else if(entidad.equalsIgnoreCase("A")){
				sql = sql + "idAgencia = :idEntidad";
			}else if(entidad.equalsIgnoreCase("H")){
				sql = sql + "idHandler = :idEntidad";
			}
			sql = sql + " and idEstado = 83 order by id";
			query = getSession().createQuery(sql);
			query.setInteger("idEntidad",idEntidad);
		}else {
			sql = sql + " idEstado = 83 order by id";
			query = getSession().createQuery(sql);
		}
		return query.list();
		
		}else {
			String sql = "select pla.id from Plantacion pla where  pla.idproveedor = " + idEntidad;
	    	Query query = getSession().createQuery(sql);
	    	List <Integer >ids = query.list();
	    	
	    	String and = " idPlantacion in (";
	    	for(Integer id : ids){
	    		and = and + id +",";
	    	}
	    	and = and + "-1)";
	    	
			query = null;
			sql = "from Nota where  " + and ;
			
			if(idEntidad != null && entidad != null){
				
				sql = sql + " and idEstado = 83 order by id";
			}else {
				sql = sql + " idEstado = 83 order by id";
				
			}
			
			query = getSession().createQuery(sql);
			return query.list();
		}
		}else{ //opcion consolidado
			Query query = null;
			String sql = "from Nota where  ";
			sql = sql + " idEstado = 83 order by id";
			query = getSession().createQuery(sql);
			return query.list();
		}
			
	}
	
	public Double getSaldo(Integer idEntidad,String entidad, String tiposaldo){
		
		String sql;
		if(!entidad.equalsIgnoreCase("Q")){
		sql = "select sum (n.valor * n.idDebitoCredito) from Nota n where  ";
		if(entidad.equalsIgnoreCase("C")){
			sql = sql + "n.idCliente = :idEntidad";
		}else if(entidad.equalsIgnoreCase("P")){
			sql = sql + "n.idPlantacion = :idEntidad";
		}else if(entidad.equalsIgnoreCase("A")){
			sql = sql + "n.idAgencia = :idEntidad";
		}else if(entidad.equalsIgnoreCase("H")){
			sql = sql + "n.idHandler = :idEntidad";
		}
		
		if(tiposaldo.equalsIgnoreCase("A")){
			sql = sql + " and idEstado = 84 order by id";
		}else if (tiposaldo.equalsIgnoreCase("P")){
			sql = sql + " and idEstado = 83 order by id";
		}
		Query query = getSession().createQuery(sql);
		query.setInteger("idEntidad",idEntidad);
		
		return (Double) query.uniqueResult();
		}else {
	    	sql = "select pla.id from Plantacion pla where  pla.idproveedor = " + idEntidad;
	    	Query query = getSession().createQuery(sql);
	    	List <Integer >ids = query.list();
	    	
	    	String and = " n.idPlantacion in (";
	    	for(Integer id : ids){
	    		and = and + id + ",";
	    	}
	    	and = and + "-1)";
	    	sql = "select sum (n.valor * n.idDebitoCredito) from Nota n where  " + and;

	    	if(tiposaldo.equalsIgnoreCase("A")){
				sql = sql + " and idEstado = 84 order by id";
			}else if (tiposaldo.equalsIgnoreCase("P")){
				sql = sql + " and idEstado = 83 order by id";
			}
			Query query2 = getSession().createQuery(sql);
			return (Double) query2.uniqueResult();
			
	    }
	}
public Double getSaldoInicial(Integer idEntidad,String entidad, String tiposaldo,Date fecha){
	
	    String sql;
		
	    if (!entidad.equalsIgnoreCase("Q")){
		sql = "select sum (n.valor * n.idDebitoCredito) from Nota n where  ";
		if(entidad.equalsIgnoreCase("C")){
			sql = sql + "n.idCliente = :idEntidad";
		}else if(entidad.equalsIgnoreCase("P")){
			sql = sql + "n.idPlantacion = :idEntidad";
		}else if(entidad.equalsIgnoreCase("A")){
			sql = sql + "n.idAgencia = :idEntidad";
		}else if(entidad.equalsIgnoreCase("H")){
			sql = sql + "n.idHandler = :idEntidad";
		}
		
		if(tiposaldo.equalsIgnoreCase("A")){
			sql = sql + " and DATE(n.fecha) < :fecha and idEstado = 84 order by id";
		}else if (tiposaldo.equalsIgnoreCase("P")){
			sql = sql + " and DATE(n.fecha) < :fecha and idEstado = 83 order by id";
		}
		Query query = getSession().createQuery(sql);
		query.setDate("fecha",fecha);
		query.setInteger("idEntidad",idEntidad);
		
		return (Double) query.uniqueResult();
		
	    }else {
	    	sql = "select pla.id from Plantacion pla where  pla.idproveedor = " + idEntidad;
	    	Query query = getSession().createQuery(sql);
	    	List <Integer >ids = query.list();
	    	
	    	String and = " n.idPlantacion in (";
	    	for(Integer id : ids){
	    		and = and + id + ",";
	    	}
	    	and = and + "-1)";
	    	sql = "select sum (n.valor * n.idDebitoCredito) from Nota n where " + and ;
	    	if(tiposaldo.equalsIgnoreCase("A")){
				sql = sql + " and DATE(n.fecha) < :fecha and idEstado = 84 order by id";
			}else if (tiposaldo.equalsIgnoreCase("P")){
				sql = sql + " and DATE(n.fecha) < :fecha and idEstado = 83 order by id";
			}
			Query query2 = getSession().createQuery(sql);
			query2.setDate("fecha",fecha);
			
			return (Double) query2.uniqueResult();
			
	    }
	    
		
	}
	
public List <Map> getSaldoEntidadFecha(String entidad,Date fecha){
	String sql = new String();
	if(entidad.equalsIgnoreCase("C")){
	sql = "select new map (n.idCliente id , c.nombre nombre sum (n.valor * n.idDebitoCredito) valor) from Nota n , Cliente c where  "
			+ "n.idCliente is not null and c.id = n.idCliente and DATE(fecha) <= :fecha group group  by n.idCliente";
	}else if(entidad.equalsIgnoreCase("A")){
		sql = "select new map (n.idAgencia id , c.nombre nombre sum (n.valor * n.idDebitoCredito) valor) from Nota n , AgenciaCarga c where  "
				+ "n.idAgencia is not null and c.id = n.idAgencia and DATE(fecha) <= :fecha group  by n.idAgencia";
	}else if(entidad.equalsIgnoreCase("P")){
		sql = "select new map (n.idPlantacion id , c.nombre nombre sum (n.valor * n.idDebitoCredito) valor) from Nota n , Plantacion c where  "
				+ "n.idPlantacion is not null and c.id = n.idPlantacion and DATE(fecha) <= :fecha group  by n.idPlantacion";
	}
	
	Query query = getSession().createQuery(sql);
	query.setDate("fecha",fecha);
	
	return query.list();
}
	
public Double getSaldoFecha(Integer idEntidad,String entidad, String tiposaldo, Date fecha){
		
		String sql = "select sum (n.valor * n.idDebitoCredito) from Nota n where  ";
		if(entidad.equalsIgnoreCase("C")){
			sql = sql + "n.idCliente = :idEntidad";
		}else if(entidad.equalsIgnoreCase("P")){
			sql = sql + "n.idPlantacion = :idEntidad";
		}else if(entidad.equalsIgnoreCase("A")){
			sql = sql + "n.idAgencia = :idEntidad";
		}else if(entidad.equalsIgnoreCase("H")){
			sql = sql + "n.idHandler = :idEntidad";
		}
		
		if(fecha!= null){
			sql = sql + " and n.fecha <= :fecha ";
		}
		
		if(tiposaldo.equalsIgnoreCase("A")){
			sql = sql + " and idEstado = 84 order by id";
		}else if (tiposaldo.equalsIgnoreCase("P")){
			sql = sql + " and idEstado = 83 order by id";
		}
		
		
		Query query = getSession().createQuery(sql);
		query.setInteger("idEntidad",idEntidad);
		if(fecha!= null){
			query.setDate("fecha",fecha);
		}
		
		return (Double) query.uniqueResult();
	}

    
	public Nota getNotaxId(Integer id){
		String sql = "from Nota where id = :id";
		Query query = getSession().createQuery(sql);
		query.setInteger("id",id);
		return (Nota) query.uniqueResult();
	}
	@Transactional
	public void editNota(Nota nota){
		Nota notaant = null;
		boolean esPrimera = true;
		if(nota.getId()!= null){
			notaant = this.getNotaxId(nota.getId());
			esPrimera = false;
			getSession().evict(notaant);
		}

		getSession().clear();
		getSession().flush();
		getSession().clear();
		//getSession().update(nota);
		
		getSession().saveOrUpdate(nota);
		if(!esPrimera){
		if(nota.getIdEstado().intValue() == 84 && notaant.getIdEstado().intValue()!= 84){
			if(nota.getIdCliente()!= null){
				  Query q = getSession().createQuery("update Cliente set saldofinal = (saldofinal + (:valor * :tipo * -1)) where id= :id");
				  q.setInteger("id", nota.getIdCliente());
				  q.setInteger("tipo", nota.getIdDebitoCredito());
				  q.setDouble("valor",nota.getValor());
				  q.executeUpdate();
				  getSession().flush();
				
			}else if(nota.getIdPlantacion()!= null){
				  Query q = getSession().createQuery("update Plantacion set saldofinal = (saldofinal + (:valor * :tipo * -1)) where id= :id");
				  q.setInteger("id", nota.getIdPlantacion());
				  q.setInteger("tipo", nota.getIdDebitoCredito());
				  q.setDouble("valor",nota.getValor());
				  q.executeUpdate();
				  getSession().flush();
				
			}else if(nota.getIdAgencia()!= null){
				Query q = getSession().createQuery("update AgenciaCarga set saldofinal = (saldofinal + (:valor * :tipo * -1)) where id= :id");
				  q.setInteger("id", nota.getIdAgencia());
				  q.setInteger("tipo", nota.getIdDebitoCredito());
				  q.setDouble("valor",nota.getValor());
				  q.executeUpdate();
				  getSession().flush();
			}else if(nota.getIdHandler()!= null){
				Query q = getSession().createQuery("update Handler set saldofinal = (saldofinal + (:valor * :tipo * -1)) where id= :id");
				  q.setInteger("id", nota.getIdHandler());
				  q.setInteger("tipo", nota.getIdDebitoCredito());
				  q.setDouble("valor",nota.getValor());
				  q.executeUpdate();
				  getSession().flush();
			}
		}
		}else{
			if(nota.getIdEstado().intValue() == 84){
			if(nota.getIdCliente()!= null){
				  Query q = getSession().createQuery("update Cliente set saldofinal = (saldofinal + (:valor * :tipo * -1)) where id= :id");
				  q.setInteger("id", nota.getIdCliente());
				  q.setInteger("tipo", nota.getIdDebitoCredito());
				  q.setDouble("valor",nota.getValor());
				  q.executeUpdate();
				  getSession().flush();
				
			}else if(nota.getIdPlantacion()!= null){
				  Query q = getSession().createQuery("update Plantacion set saldofinal = (saldofinal + (:valor * :tipo * -1)) where id= :id");
				  q.setInteger("id", nota.getIdPlantacion());
				  q.setInteger("tipo", nota.getIdDebitoCredito());
				  q.setDouble("valor",nota.getValor());
				  q.executeUpdate();
				  getSession().flush();
				
			}else if(nota.getIdAgencia()!= null){
				Query q = getSession().createQuery("update AgenciaCarga set saldofinal = (saldofinal + (:valor * :tipo * -1)) where id= :id");
				  q.setInteger("id", nota.getIdAgencia());
				  q.setInteger("tipo", nota.getIdDebitoCredito());
				  q.setDouble("valor",nota.getValor());
				  q.executeUpdate();
				  getSession().flush();
			}else if(nota.getIdHandler()!= null){
				Query q = getSession().createQuery("update Handler set saldofinal = (saldofinal + (:valor * :tipo * -1)) where id= :id");
				  q.setInteger("id", nota.getIdHandler());
				  q.setInteger("tipo", nota.getIdDebitoCredito());
				  q.setDouble("valor",nota.getValor());
				  q.executeUpdate();
				  getSession().flush();
			}
		}
		}
	}
	
	public ProductoGrados getTipoFlorGrados(Integer idTipoFlor,Integer idGrado){
		String sql = "from ProductoGrados where idtipoflor  = :idTipoFlor and idgrado=:idGrado";
		Query query = getSession().createQuery(sql);
		query.setInteger("idTipoFlor",idTipoFlor);
		query.setInteger("idGrado",idGrado);
		return (ProductoGrados) query.uniqueResult();
	}
	
	public List <Map> getVariedadesFlorColor(Integer idTipoFlor,Integer idColor){
		String sql = "select distinct new Map(domflor.id as idVariedad, domflor.nomcorto as nombrevariedad , domColor.id as idColor, domColor.nomcorto as nombrecolor,domtipo.id as idTipoflor,domtipo.nomcorto as nombretipoflor,domflor.url as url) from Dominio domtipo, Dominio domflor , Dominio domColor where domflor.dominiopadre =:idTipoFlor and domflor.dominiopadre = domtipo.id and domflor.idcolor = :idColor and domColor.id =:idColor";

		Query query = getSession().createQuery(sql);
		query.setInteger("idTipoFlor", idTipoFlor);
		query.setInteger("idColor", idColor);
		return query.list();
	}
	
	public Dominio getDominioPadreCodigo(Integer idPadre,String codigo){
		String sql = "from Dominio where dominiopadre=:idPadre and codigo=:codigo";

		Query query = getSession().createQuery(sql);
		query.setInteger("idPadre", idPadre);
		query.setString("codigo", codigo);
		return (Dominio) query.uniqueResult();
	}
	public Dominio getDominioPadreNombre(Integer idPadre,String nombre){
		String sql = "from Dominio where dominiopadre=:idPadre and nomcorto=:nombre";

		Query query = getSession().createQuery(sql);
		query.setInteger("idPadre", idPadre);
		query.setString("nombre", nombre);
		return (Dominio) query.uniqueResult();
	}
	
	public List <Map> getConsolidadoPedido(Date fechadeDesde,Date fechaHasta,String tipo){
		String sql = new String();
		if(tipo.equalsIgnoreCase("P")){
			sql = "select distinct new Map(ped.id as idpedido) "
				+ "from Pedido ped, DetallePedido det where det.pedido.id = ped.id and ped.fechadespacho >= :fechaDesde and ped.fechadespacho <= :fechaHasta";
		}
		if(tipo.equalsIgnoreCase("C")){
			sql = "select distinct new Map(ped.id as idpedido) "
				+ "from Pedido ped, DetallePedidoConciliado det where det.pedido.id = ped.id and ped.fechadespacho >= :fechaDesde and ped.fechadespacho <= :fechaHasta";
		}
		if(tipo.equalsIgnoreCase("F")||tipo.equalsIgnoreCase("E")){
			sql = "select distinct new Map(ped.id as idpedido) "
				+ "from Pedido ped, DetallePedidoFacturado det where det.pedido.id = ped.id and ped.fechadespacho >= :fechaDesde and ped.fechadespacho <= :fechaHasta";
		}
				
		Query query = getSession().createQuery(sql);
		query.setDate("fechaDesde", fechadeDesde);
		query.setDate("fechaHasta", fechaHasta);
		return query.list();
		
	}
	
	public void agregarGrado(Integer idTipoFlor,Integer idGrado){
		
		String sql = "from ProductoGrados where idtipoflor = " + idTipoFlor + " and idgrado = " + idGrado;
		Query query = getSession().createQuery(sql);
		if(query.uniqueResult() == null){
			ProductoGrados pg = new ProductoGrados();
			pg.setIdgrado(idGrado);
			pg.setIdtipoflor(idTipoFlor);
			persist(pg);
	
			List <Dominio> listaVariedades = this.getDominiosXPadre(idTipoFlor);
			for(Dominio variedad : listaVariedades){
				com.bulls.astoria.persistence.Producto pro = new com.bulls.astoria.persistence.Producto();
				pro.setCodigo(this.getDominio(idTipoFlor).getCodigo() + variedad.getCodigo()+this.getDominio(idGrado).getNomcorto());
				pro.setIdgrado(idGrado);
				pro.setIdvariedad(variedad.getId());
				crearEditarProducto(pro);
			}
		}else {
			System.out.println("Grado ya existe");
		}
		
		 getSession().flush();
	}
	
	
	public Permiso getPermiso(Integer idProceso, String tipo, Integer idRole){
		String sql = "from Permiso where idproceso = :idProceso and tipo=:tipo and idrole=:idRole";
		Query query = getSession().createQuery(sql);
		query.setInteger("idproceso", idProceso);
		query.setString("tipo", tipo);
		query.setInteger("idrole", idRole);
		return (Permiso)query.uniqueResult();
	}
	public List <Permiso> getPermisos(Integer idRole){
		String sql = "from Permiso where idrole=:idRole";
		Query query = getSession().createQuery(sql);
		query.setInteger("idRole", idRole);
		return query.list();
	}
	
	public void putAuditoria(Auditoria auditoria){
		getSession().persist(auditoria);
	}
	
	public List <Auditoria> getAuditorias(String usuario, String proceso, String accion){
		String where =" where ";
		String and = " and ";
		boolean paso = false;
		String sql = "from Auditoria ";
		Query query = null;
		if(!usuario.equalsIgnoreCase("")){
			paso = true;
			where = where + "upper (usuario) like upper(:usuario)";
		}
		
		if(!proceso.equalsIgnoreCase("")){
			if (paso)
				where = where + and;
			where = where + "upper (proceso) like upper(:proceso)";
			paso = true;
		}
		
		if(!accion.equalsIgnoreCase("")){
			if (paso)
				where = where + and;
			where = where + "upper (accion) like upper(:accion)";
			paso = true;
		}
		if (paso){
			query = getSession().createQuery(sql+where + " order by fecha desc");
		}else{
			query = getSession().createQuery(sql + " order by fecha desc");
		}
		
		
		if(!usuario.equalsIgnoreCase(""))
			query.setString("usuario", "%" + usuario + "%");
		if(!accion.equalsIgnoreCase(""))
			query.setString("accion", "%" + accion + "%");
		if(!proceso.equalsIgnoreCase(""))
			query.setString("proceso", "%" + proceso + "%");
		
		return query.list();
	}
	
}
 