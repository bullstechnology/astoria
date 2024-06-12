package com.bulls.astoria.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bulls.astoria.persistence.AgenciaCarga;
import com.bulls.astoria.persistence.Composicion;
import com.bulls.astoria.persistence.ComposicionConciliado;
import com.bulls.astoria.persistence.ComposicionFacturado;
import com.bulls.astoria.persistence.Cotizacion;
import com.bulls.astoria.persistence.Destino;
import com.bulls.astoria.persistence.DetallePedido;
import com.bulls.astoria.persistence.DetallePedidoConciliado;
import com.bulls.astoria.persistence.DetallePedidoFacturado;
import com.bulls.astoria.persistence.Handler;
import com.bulls.astoria.persistence.Pedido;
import com.bulls.astoria.persistence.PedidoConciliado;
import com.bulls.astoria.persistence.PedidoFacturado;
import com.bulls.astoria.persistence.ProductoCotizacion;
import com.bulls.astoria.pojo.Producto;


@Repository ("pedidoDao")
public class PedidoDaoImpl extends AbstractDao implements PedidoDao{

	@Transactional
	public void savePedido(Pedido pedido){
		getSession().persist(pedido);
	/*	List<DetallePedido> list = new ArrayList<DetallePedido>(pedido.getDetalles());
        for(DetallePedido detalle : list){
        	List<Composicion> composiciones = new ArrayList<Composicion>(detalle.getComposiciones());
        	detalle.setPedido(pedido);
        	getSession().persist(detalle);
        	for(Composicion comp:composiciones){
        		getSession().persist(detalle);
        	}
        }*/
        return;
	}
	
	@Transactional
	public void editPedido(Pedido pedido){

		
		
		
		//Query q = getSession().createQuery("delete from Composicion  where detalle.pedido.id = :id");
		Query q = getSession().createQuery("delete from Composicion F where F.detalle.id in (select detalle.id from DetallePedido as detalle where detalle.pedido.id= :id)");
	    q.setInteger("id", pedido.getId());
	    q.executeUpdate();
	    getSession().flush();
		
		Query q2 = getSession().createQuery("delete from DetallePedido where idpedido = :id");
	    q2.setInteger("id", pedido.getId());
	    q2.executeUpdate();
	    getSession().flush();
	    
		
		getSession().saveOrUpdate(pedido);
		List<DetallePedido> list = new ArrayList<DetallePedido>(pedido.getDetalles());
        for(DetallePedido detalle : list){
        	List<Composicion> composiciones = new ArrayList<Composicion>(detalle.getComposiciones());
        	detalle.setPedido(pedido);
        	getSession().saveOrUpdate(detalle);
        	for(Composicion comp:composiciones){
        		getSession().persist(comp);
        	}
        }
        return;
	}
	
	public Pedido getPedidoXId(Integer idPedido){
		Query query = getSession().createQuery("from Pedido where id = :idPedido");
		query.setInteger("idPedido",idPedido);
		return (Pedido) query.uniqueResult();
	}
	public PedidoConciliado getPedidoConciliadoXId(Integer idPedido){
		Query query = getSession().createQuery("from PedidoConciliado where id = :idPedido");
		query.setInteger("idPedido",idPedido);
		return (PedidoConciliado) query.uniqueResult();
	}
	public PedidoFacturado getPedidoFacturadoXId(Integer idPedido){
		Query query = getSession().createQuery("from PedidoFacturado where id = :idPedido");
		query.setInteger("idPedido",idPedido);
		return (PedidoFacturado) query.uniqueResult();
	}
	public List<Pedido> getPedidosEstado(String estado){
		Query query = getSession().createQuery("from Pedido where estado = :estado");
		query.setString("estado",estado);
		return query.list();
	}
	
	public List<PedidoConciliado> getPedidosConciliadoEstado(String estado){
		Query query = getSession().createQuery("from PedidoConciliado where estado = :estado");
		query.setString("estado",estado);
		return query.list();
	}
	
	public List<PedidoFacturado> getPedidosFacturadoEstado(String estado){
		Query query = getSession().createQuery("from PedidoFacturado where estado = :estado");
		query.setString("estado",estado);
		return query.list();
	}
	public List<Pedido> getPedidosAll(){
		Query query = getSession().createQuery("from Pedido");
		return query.list();
	}
	public List<Pedido> getPedidoXFecha(Date rangoinicial, Date rangofinal){
		Query query = getSession().createQuery("from Pedido where fechapedido >= rangoinicial AND fechapedido <= rangofinal");
		return query.list();
	}
	public List<DetallePedido> getDetallesPedido(Integer idPedido){
		
		/*Query query = getSession().createQuery("from Pedido where id = :idPedido");
		query.setInteger("idPedido",idPedido);
		Pedido pedido = (Pedido) query.uniqueResult();*/
		//para ordenar
		// como estaba List<DetallePedido> list = new ArrayList<DetallePedido>(pedido.getDetalles());
		Query query = getSession().createQuery("from DetallePedido detalle where detalle.pedido.id = :idPedido order by detalle.idplantacion");
		query.setInteger("idPedido",idPedido);
		
		//List<DetallePedido> list = new ArrayList<DetallePedido>(pedido.getDetalles());
		return query.list();
		
	}
	
	public List<Composicion> getComposiciones(Integer idDetalle){
		
		Query query = getSession().createQuery("from Composicion comp where comp.detalle.id = :idDetalle");
		query.setInteger("idDetalle",idDetalle);
		return query.list();
		
	}
	
	public List<ComposicionConciliado> getComposicionesConciliado(Integer idDetalle){
		
		Query query = getSession().createQuery("from ComposicionConciliado comp where comp.detalle.id = :idDetalle");
		query.setInteger("idDetalle",idDetalle);
		return query.list();
		
	}
	
	
	public List<DetallePedidoConciliado> getDetallesPedidoConciliado(Integer idPedido){
		
		/*Query query = getSession().createQuery("from PedidoConciliado where id = :idPedido ");
		query.setInteger("idPedido",idPedido);
		PedidoConciliado pedido = (PedidoConciliado) query.uniqueResult();*/
		Query query = getSession().createQuery("from DetallePedidoConciliado detalle where detalle.pedido.id = :idPedido order by detalle.idplantacion");
		query.setInteger("idPedido",idPedido);
		//List<DetallePedidoConciliado> list = new ArrayList<DetallePedidoConciliado>(pedido.getDetalles());
		return query.list();
		
	}
	
public List<ComposicionFacturado> getComposicionesFacturado(Integer idDetalle){
		
		Query query = getSession().createQuery("from ComposicionFacturado comp where comp.detalle.id = :idDetalle");
		query.setInteger("idDetalle",idDetalle);
		return query.list();
		
	}
	
	
	public List<DetallePedidoFacturado> getDetallesPedidoFacturado(Integer idPedido){
		
		/*Query query = getSession().createQuery("from PedidoFacturado where id = :idPedido");
		query.setInteger("idPedido",idPedido);
		PedidoFacturado pedido = (PedidoFacturado) query.uniqueResult();
		List<DetallePedidoFacturado> list = new ArrayList<DetallePedidoFacturado>(pedido.getDetalles());*/
		
		Query query = getSession().createQuery("from DetallePedidoFacturado detalle where detalle.pedido.id = :idPedido order by detalle.idplantacion");
		query.setInteger("idPedido",idPedido);
		return query.list();
		
	}
	
	@Transactional
	public void editPedidoConciliado(PedidoConciliado pedido){
		Query q = getSession().createQuery("delete from ComposicionConciliado F where F.detalle.id in (select detalle.id from DetallePedidoConciliado as detalle where detalle.pedido.id= :id)");
	    q.setInteger("id", pedido.getId());
	    q.executeUpdate();
	    getSession().flush();
		
		Query q2 = getSession().createQuery("delete from DetallePedidoConciliado where idpedido = :id");
	    q2.setInteger("id", pedido.getId());
	    q2.executeUpdate();
	    getSession().flush();
	    
		
		getSession().saveOrUpdate(pedido);
		List<DetallePedidoConciliado> list = new ArrayList<DetallePedidoConciliado>(pedido.getDetalles());
        for(DetallePedidoConciliado detalle : list){
        	List<ComposicionConciliado> composiciones = new ArrayList<ComposicionConciliado>(detalle.getComposiciones());
        	detalle.setPedido(pedido);
        	getSession().saveOrUpdate(detalle);
        	for(ComposicionConciliado comp:composiciones){
        		getSession().persist(comp);
        	}
        }
        if(pedido.getEstado()!= 'P'){
        	setEstadoPedido(pedido.getId(),'C');
        }	
        return;
	}
	
	@Transactional
	public void editPedidoDespachado(PedidoConciliado pedido){
		Query q = getSession().createQuery("delete from ComposicionConciliado F where F.detalle.id in (select detalle.id from DetallePedidoConciliado as detalle where detalle.pedido.id= :id)");
	    q.setInteger("id", pedido.getId());
	    q.executeUpdate();
	    getSession().flush();
		
		Query q2 = getSession().createQuery("delete from DetallePedidoConciliado where idpedido = :id");
	    q2.setInteger("id", pedido.getId());
	    q2.executeUpdate();
	    getSession().flush();
	    
	    
		getSession().saveOrUpdate(pedido);
		List<DetallePedidoConciliado> list = new ArrayList<DetallePedidoConciliado>(pedido.getDetalles());
        for(DetallePedidoConciliado detalle : list){
        	List<ComposicionConciliado> composiciones = new ArrayList<ComposicionConciliado>(detalle.getComposiciones());
        	detalle.setPedido(pedido);
        	getSession().saveOrUpdate(detalle);
        	for(ComposicionConciliado comp:composiciones){
        		getSession().persist(comp);
        	}
        }
        if(pedido.getEstado()!= 'P'){
        	setEstadoPedido(pedido.getId(),'D');
        }	
        setEstadoPedidoConciliado(pedido.getId(),'D');
        return;
	}
	@Transactional
	public void editPedidoEnFacturacion(PedidoFacturado pedido){
		Query q = getSession().createQuery("delete from ComposicionFacturado F where F.detalle.id in (select detalle.id from DetallePedidoFacturado as detalle where detalle.pedido.id= :id)");
	    q.setInteger("id", pedido.getId());
	    q.executeUpdate();
	    getSession().flush();
		
		Query q2 = getSession().createQuery("delete from DetallePedidoFacturado where idpedido = :id");
	    q2.setInteger("id", pedido.getId());
	    q2.executeUpdate();
	    getSession().flush();
	    
	    
		getSession().saveOrUpdate(pedido);
		List<DetallePedidoFacturado> list = new ArrayList<DetallePedidoFacturado>(pedido.getDetalles());
        /*for(DetallePedidoFacturado detalle : list){
        	List<ComposicionFacturado> composiciones = new ArrayList<ComposicionFacturado>(detalle.getComposiciones());
        	detalle.setPedido(pedido);
        	getSession().saveOrUpdate(detalle);
        	for(ComposicionFacturado comp:composiciones){
        		getSession().persist(comp);
        	}
        }*/
		getSession().flush();
        if(pedido.getEstado()!= 'P'){
        	setEstadoPedido(pedido.getId(),'F');
        }
        return;
	}
	@Transactional
	public void editPedidoFacturado(PedidoFacturado pedido){
		Query q = getSession().createQuery("delete from ComposicionFacturado F where F.detalle.id in (select detalle.id from DetallePedidoFacturado as detalle where detalle.pedido.id= :id)");
	    q.setInteger("id", pedido.getId());
	    q.executeUpdate();
	    getSession().flush();
		
		Query q2 = getSession().createQuery("delete from DetallePedidoFacturado where idpedido = :id");
	    q2.setInteger("id", pedido.getId());
	    q2.executeUpdate();
	    getSession().flush();
	    
	    
		getSession().saveOrUpdate(pedido);
		List<DetallePedidoFacturado> list = new ArrayList<DetallePedidoFacturado>(pedido.getDetalles());
       /* for(DetallePedidoFacturado detalle : list){
        	List<ComposicionFacturado> composiciones = new ArrayList<ComposicionFacturado>(detalle.getComposiciones());
        	detalle.setPedido(pedido);
        	getSession().saveOrUpdate(detalle);
        	for(ComposicionFacturado comp:composiciones){
        		getSession().persist(comp);
        	}
        }*/
        if(pedido.getEstado()!= 'P'){
        	setEstadoPedido(pedido.getId(),'E');
        }
        getSession().flush();
        return;
	}
	public void setEstadoPedido(Integer idPedido , Character estado){
		
		 getSession().clear();
		
		  Query q = getSession().createQuery("update Pedido set estado = :estado where id= :idpedido");
		  q.setInteger("idpedido", idPedido);
		  q.setCharacter("estado", estado);
		  q.executeUpdate();
		  getSession().flush();
	}
	public void setEstadoPedidoConciliado(Integer idPedido , Character estado){
		
		 getSession().clear();
		
		  Query q = getSession().createQuery("update PedidoConciliado set estado = :estado where id= :idpedido");
		  q.setInteger("idpedido", idPedido);
		  q.setCharacter("estado", estado);
		  q.executeUpdate();
		  getSession().flush();
	}
	public void setEstadoPedidoFacturado(Integer idPedido , Character estado){
		
		 getSession().clear();
		
		  Query q = getSession().createQuery("update PedidoFacturado set estado = :estado where id= :idpedido");
		  q.setInteger("idpedido", idPedido);
		  q.setCharacter("estado", estado);
		  q.executeUpdate();
		  getSession().flush();
	}
	public List<Pedido> getPedidos(Integer cliente,String estado,Date fechainicial,Date fechafinal,Integer idPedido){
		
		StringBuffer buffer = new StringBuffer();
		boolean pasocliente = false;
		boolean pasoestado = false;
		boolean pasofecha = false;
		boolean pasopedido = false;
		buffer.append("from Pedido where ");
		if(cliente != null){
			buffer.append("idcliente = :cliente ");
			pasocliente = true;
		}
		if(estado != null){
			pasoestado = true;
			if(pasocliente){
				buffer.append("and estado = :estado ");
			}else {
				buffer.append("estado = :estado ");
			}
			
		}
		if(idPedido != null){
			pasopedido = true;
			if(pasocliente || pasoestado){
				buffer.append("and id = :id ");
			}else {
				buffer.append("id = :id ");
			}
			
		}
		if(fechainicial != null && fechainicial !=  null ){
			pasofecha = true;
			if(pasocliente || pasoestado || pasopedido){
				buffer.append("and fechapedido >= :fechainicial and fechapedido >= :fechafinal");
			}else {
				buffer.append("fechapedido >= :fechainicial and fechapedido <= :fechafinal");
			}
			
		}
		Query query = getSession().createQuery(buffer.toString());
		
		
		if(pasocliente){
			query.setInteger("cliente",cliente);
		}
		if(pasoestado){
			query.setString("estado",estado);
		}
		if(pasopedido){
			query.setInteger("id",idPedido);
		}
		if(pasofecha){
			query.setDate("fechainicial",fechainicial);
			query.setDate("fechafinal",fechafinal);
		}
		
		return query.list();
		
	}
	
public List<Pedido> getPedidosAutorizar(Integer cliente,String estado,Date fechainicial,Date fechafinal,Integer idPedido){
		
		StringBuffer buffer = new StringBuffer();
		boolean pasocliente = false;
		boolean pasoestado = false;
		boolean pasofecha = false;
		boolean pasopedido = false;
		Integer id;
		List<Pedido> listaSalida = new ArrayList <Pedido>();
		
		
		buffer.append("from Pedido where estado!=:estado1 and estado!=:estado2 and estado!=:estado3 and estado!=:estado4 and autorizo is null and ");
		if(cliente != null){
			buffer.append("idcliente = :cliente ");
			pasocliente = true;
		}
		if(estado != null){
			pasoestado = true;
			if(pasocliente){
				buffer.append("and estado = :estado ");
			}else {
				buffer.append("estado = :estado ");
			}
			
		}
		if(idPedido != null){
			pasopedido = true;
			if(pasocliente || pasoestado){
				buffer.append("and id = :id ");
			}else {
				buffer.append("id = :id ");
			}
			
		}
		if(fechainicial != null && fechainicial !=  null ){
			pasofecha = true;
			if(pasoestado){
				buffer.append("and fechapedido >= :fechainicial and fechapedido >= :fechafinal");
			}else {
				buffer.append("fechapedido >= :fechainicial and fechapedido <= :fechafinal");
			}
			
		}
		
		Query query = getSession().createQuery(buffer.toString());
		
		
		if(pasocliente){
			query.setInteger("cliente",cliente);
		}
		if(pasoestado){
			query.setString("estado",estado);
		}
		if(pasopedido){
			query.setInteger("id",idPedido);
		}
		if(pasofecha){
			query.setDate("fechainicial",fechainicial);
			query.setDate("fechafinal",fechafinal);
		}
		
		query.setString("estado1","X");
		query.setString("estado2","F");
		query.setString("estado3","D");
		query.setString("estado4","E");
		
		List <Pedido> lista = query.list();
		
		for(Pedido pedido : lista){
			
			id = pedido.getId();
			query = getSession().createQuery("from Pedido p where p.id =:id and p.autorizacion=:autorizacion");
			query.setInteger("id", id);
			query.setBoolean("autorizacion", true);
			Pedido pe = (Pedido)query.uniqueResult();
			if(pe != null){
				listaSalida.add(pe);
			}else {	
				query = getSession().createQuery("from PedidoConciliado p where p.id =:id and p.autorizacion=:autorizacion");
				query.setInteger("id", id);
				query.setBoolean("autorizacion", true);
				PedidoConciliado pc = (PedidoConciliado)query.uniqueResult();
				if(pc != null)
				listaSalida.add(pe);
			}	
		}
		return listaSalida;
		
	}
	
	
	public  Double getPrecioProducto (Integer idPlantacion, Integer idProducto,Date fecha){
		  Double preciosalida = null ;
		  Double preciosalidaEspecial = null ;
		  if(idPlantacion!= null ){
			  
			  StringBuffer queriBuffer = new StringBuffer();
			  Query q = getSession().createQuery("select new map(de.precio as precio, li.tipo as tipo) from FranjaPrecios fr, ListaPrecio li,  DetalleListaPrecio de where fr.idplantacion = :idPlantacion and fr.fechaini <= :fecha and fr.fechafin >= :fecha and fr.id = li.idfranja and de.listaPrecio.id = li.id and de.idproducto = :idProducto");
			  q.setInteger("idPlantacion",idPlantacion);
			  q.setInteger("idProducto",idProducto);
			  q.setDate("fecha",fecha);
			  List <Map> lista = q.list();
			  Iterator ite = lista.iterator();
			  while ( ite.hasNext() ) {
					
					Map o = (Map) ite.next();
			        Object precio =   o.get("precio");
			        Object tipo =  o.get("tipo");
                    if(((Character) tipo).equals(new Character('E'))){
                    	preciosalidaEspecial = (Double) precio;
                    }else {
                    	preciosalida = (Double) precio;
                    }
		        
				}
			  if(preciosalidaEspecial!= null){
				  preciosalida =  preciosalidaEspecial;
			  }
		  }else {
			  //elpromedio detodos los precios que haya
			  Query q = getSession().createQuery("select avg(de.precio) from FranjaPrecios fr, ListaPrecio li,  DetalleListaPrecio de where fr.fechaini <= :fecha and fr.fechafin >= :fecha and fr.id = li.idfranja and de.listaPrecio.id = li.id and de.idproducto = :idProducto");
			  q.setInteger("idProducto",idProducto);
			  q.setDate("fecha",fecha);
			  preciosalida = (Double) q.uniqueResult();
		  }
		  

		  return preciosalida; 
	}
	
	public void autorizar(Integer idPedido,String usuario){
		  getSession().clear();
		
		  Query q = getSession().createQuery("update Pedido set autorizo = :usuario where id= :idpedido");
		  q.setInteger("idpedido", idPedido);
		  q.setString("usuario", usuario);
		  q.executeUpdate();
		  getSession().flush();
		  
		  q = getSession().createQuery("update PedidoConciliado set autorizo = :usuario where id= :idpedido");
		  q.setInteger("idpedido", idPedido);
		  q.setString("usuario", usuario);
		  q.executeUpdate();
		  getSession().flush();
	}
	
	public List<Pedido> getPedidosPorDespachar(Date fechainicial,Date fechafinal){
		Query query = getSession().createQuery("from Pedido where fechadespacho >= :fechainicial and fechadespacho <= :fechafinal");
		query.setDate("fechainicial",fechainicial);
		query.setDate("fechafinal",fechafinal);
		return query.list();
	}
	
	public void editarEncabezado (Pedido encabezado, Date fechafactura){
		  Query q = getSession().createQuery("update Pedido set fechadespacho = :fechadespacho, fechallegada = :fechallegada , fechallegadafinal = :fechallegadafinal where id= :idpedido");
		  q.setInteger("idpedido", encabezado.getId());
		  q.setDate("fechadespacho", encabezado.getFechadespacho());
		  q.setDate("fechallegada", encabezado.getFechallegada());
		  q.setDate("fechallegadafinal", encabezado.getFechallegadafinal());
		  q.executeUpdate();
		  getSession().flush();
		  
		  Query q2 = getSession().createQuery("update PedidoConciliado set fechadespacho = :fechadespacho, fechallegada = :fechallegada , fechallegadafinal = :fechallegadafinal where id= :idpedido");
		  q2.setInteger("idpedido", encabezado.getId());
		  q2.setDate("fechadespacho", encabezado.getFechadespacho());
		  q2.setDate("fechallegada", encabezado.getFechallegada());
		  q2.setDate("fechallegadafinal", encabezado.getFechallegadafinal());
		  q2.executeUpdate();
		  getSession().flush();
		  
		  Query q3 = getSession().createQuery("update PedidoFacturado set fechadespacho = :fechadespacho, fechallegada = :fechallegada , fechallegadafinal = :fechallegadafinal , date = :fechafactura,notafinal =:notafinal where id= :idpedido");
		  q3.setInteger("idpedido", encabezado.getId());
		  q3.setDate("fechadespacho", encabezado.getFechadespacho());
		  q3.setDate("fechallegada", encabezado.getFechallegada());
		  q3.setDate("fechallegadafinal", encabezado.getFechallegadafinal());
		  q3.setDate("fechafactura", fechafactura);
		  q3.setString("notafinal", encabezado.getObservaciones());
		  q3.executeUpdate();
		  getSession().flush();
		  

	}
	
	public List<PedidoFacturado> getPedidosXAWB(String awb){
		Query query = getSession().createQuery("from PedidoFacturado where awb =:awb");
		query.setString("awb", awb);
		return query.list();
	}
	
	public void cotizar(Cotizacion cotizacion){
		getSession().saveOrUpdate(cotizacion);
	}
	
	public void crearItemsCotizacion(List <ProductoCotizacion> listaProductos){
		for(ProductoCotizacion producto:listaProductos){
			getSession().persist(producto);
		}
	}
	
	public List <Cotizacion> getCotizaciones(String estado){
		Query query = getSession().createQuery("from Cotizacion where estado =:estado order by idcotizacion desc");
		query.setString("estado", estado);
		return query.list();
	}
	
	public List <ProductoCotizacion> getProductosCotizacion(Integer idCotizacion){
		Query query = getSession().createQuery("from ProductoCotizacion where idcotizacion =:idcotizacion");
		query.setInteger("idcotizacion", idCotizacion);
		return query.list();
	}
	


}
