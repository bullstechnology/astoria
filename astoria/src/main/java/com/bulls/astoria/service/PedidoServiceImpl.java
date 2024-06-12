package com.bulls.astoria.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bulls.astoria.dao.ClienteDao;
import com.bulls.astoria.dao.PedidoDao;
import com.bulls.astoria.persistence.Cliente;
import com.bulls.astoria.persistence.Composicion;
import com.bulls.astoria.persistence.ComposicionConciliado;
import com.bulls.astoria.persistence.ComposicionFacturado;
import com.bulls.astoria.persistence.Cotizacion;
import com.bulls.astoria.persistence.DetallePedido;
import com.bulls.astoria.persistence.DetallePedidoConciliado;
import com.bulls.astoria.persistence.DetallePedidoFacturado;
import com.bulls.astoria.persistence.Pedido;
import com.bulls.astoria.persistence.PedidoConciliado;
import com.bulls.astoria.persistence.PedidoFacturado;
import com.bulls.astoria.persistence.ProductoCotizacion;


@Service("PedidoService")
@Transactional
public class PedidoServiceImpl implements  PedidoService, Serializable{
	
	@Autowired
    private PedidoDao pedidoDAO;
	
	@Autowired
    private DominioService dominioService;
	
	@Transactional
	public void savePedido(Pedido pedido){
		pedidoDAO.savePedido(pedido);
	}
	
	@Transactional
	public void editPedido(Pedido pedido){
		pedidoDAO.editPedido(pedido);
	}

	public Pedido getPedidoXId(Integer idPedido){
		return pedidoDAO.getPedidoXId(idPedido);
	}
	public PedidoConciliado getPedidoConciliadoXId(Integer idPedido){
		return pedidoDAO.getPedidoConciliadoXId(idPedido);
	}
	public PedidoFacturado getPedidoFacturadoXId(Integer idPedido){
		return pedidoDAO.getPedidoFacturadoXId(idPedido);
	}
	public List<Pedido> getPedidosEstado(String estado){
		return pedidoDAO.getPedidosEstado(estado);
	}
	public List<PedidoConciliado> getPedidosConciliadoEstado(String estado){
		return pedidoDAO.getPedidosConciliadoEstado(estado);
	}
	public List<PedidoFacturado> getPedidosFacturadoEstado(String estado){
		return pedidoDAO.getPedidosFacturadoEstado(estado);
	}
	public List<Pedido> getPedidosAll(){
		return pedidoDAO.getPedidosAll();
	}
	public List<Pedido> getPedidoXFecha(Date rangoinicial, Date rangofinal){
		return pedidoDAO.getPedidoXFecha(rangoinicial, rangofinal);
	}
	public List<DetallePedido> getDetallesPedido(Integer idPedido){
		return pedidoDAO.getDetallesPedido(idPedido);
	}
	public List<DetallePedidoConciliado> getDetallesPedidoConciliado(Integer idPedido){
		return pedidoDAO.getDetallesPedidoConciliado(idPedido);
	}
	public List<DetallePedidoFacturado> getDetallesPedidoFacturado(Integer idPedido){
		return pedidoDAO.getDetallesPedidoFacturado(idPedido);
	}
	
	public List<Pedido> getPedidos(Integer cliente,String estado,Date fechainicial,Date fechafinal,Integer idPedido){
		return pedidoDAO.getPedidos(cliente,estado,fechainicial,fechafinal,idPedido);
	}
	public List<Pedido> getPedidosAutorizar(Integer cliente,String estado,Date fechainicial,Date fechafinal,Integer idPedido){
		return pedidoDAO.getPedidosAutorizar(cliente,estado,fechainicial,fechafinal,idPedido);
	}
	
	public void editPedidoConciliado(PedidoConciliado pedido){
		 pedidoDAO.editPedidoConciliado(pedido);
	}
	public void editPedidoDespachado(PedidoConciliado pedido){
		 pedidoDAO.editPedidoDespachado(pedido);
	}
	public void editPedidoEnFacturacion(PedidoFacturado pedido){
		 pedidoDAO.editPedidoEnFacturacion(pedido);
	}
	@Transactional
	public void editPedidoFacturado(PedidoFacturado pedido){
		 pedidoDAO.editPedidoFacturado(pedido);
	}
	
	public  Double getPrecioProducto (Integer idPlantacion, Integer idProducto,Date fecha){
		return pedidoDAO.getPrecioProducto (idPlantacion, idProducto,fecha);
	}
	public List<Composicion> getComposiciones(Integer idDetalle){
		return pedidoDAO.getComposiciones (idDetalle);
	}
	public List<ComposicionConciliado> getComposicionesConciliado(Integer idDetalle){
		return pedidoDAO.getComposicionesConciliado (idDetalle);
	}
	public List<ComposicionFacturado> getComposicionesFacturado(Integer idDetalle){
		return pedidoDAO.getComposicionesFacturado (idDetalle);
	}
	public void setEstadoPedido(Integer idPedido , Character estado){
		pedidoDAO.setEstadoPedido(idPedido, estado);
	}
	public void autorizar(Integer idPedido,String usuario){
		pedidoDAO.autorizar(idPedido, usuario);
	}
	public List<Pedido> getPedidosPorDespachar(Date fechainicial,Date fechafinal){
		return pedidoDAO.getPedidosPorDespachar(fechainicial,fechafinal);
	}
	
	public void editarEncabezado(Pedido encabezado, Date fechafactura){
		pedidoDAO.editarEncabezado(encabezado,fechafactura);
	}
	
	public List<PedidoFacturado> getPedidosXAWB(String awb){
		return pedidoDAO.getPedidosXAWB(awb);
	}
	
	public void cotizar(Cotizacion cotizacion){
		pedidoDAO.cotizar(cotizacion);
	}
	public void crearItemsCotizacion(List <ProductoCotizacion> listaProductos){
		pedidoDAO.crearItemsCotizacion(listaProductos);
	}
	
	public List <Cotizacion> getCotizaciones(String estado){
		return pedidoDAO.getCotizaciones(estado);
	}
	
	public List <ProductoCotizacion> getProductosCotizacion(Integer idCotizacion){
		return pedidoDAO.getProductosCotizacion(idCotizacion);
	}
	
}
