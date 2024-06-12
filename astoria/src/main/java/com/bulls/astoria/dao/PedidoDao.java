package com.bulls.astoria.dao;

import java.util.Date;
import java.util.List;

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

public interface PedidoDao {
	
	public void savePedido(Pedido pedido);
	public void editPedido(Pedido pedido);
	public Pedido getPedidoXId(Integer idPedido);
	public PedidoConciliado getPedidoConciliadoXId(Integer idPedido);
	public PedidoFacturado getPedidoFacturadoXId(Integer idPedido);
	public List<Pedido> getPedidosEstado(String estado);
	public List<PedidoConciliado> getPedidosConciliadoEstado(String estado);
	public List<PedidoFacturado> getPedidosFacturadoEstado(String estado);
	public List<Pedido> getPedidosAll();
	public List<Pedido> getPedidoXFecha(Date rangoinicial, Date rangofinal);
	public List<DetallePedido> getDetallesPedido(Integer idPedido);
	public List<DetallePedidoConciliado> getDetallesPedidoConciliado(Integer idPedido);
	public List<DetallePedidoFacturado> getDetallesPedidoFacturado(Integer idPedido);
	public List<Pedido> getPedidos(Integer cliente,String estado,Date fechainicial,Date fechafinal,Integer idPedido);
	public List<Pedido> getPedidosAutorizar(Integer cliente,String estado,Date fechainicial,Date fechafinal,Integer idPedido);
	public void editPedidoConciliado(PedidoConciliado pedido);
	public void editPedidoDespachado(PedidoConciliado pedido);
	public void editPedidoEnFacturacion(PedidoFacturado pedido);
	public void editPedidoFacturado(PedidoFacturado pedido);
	public  Double getPrecioProducto (Integer idPlantacion, Integer idProducto,Date fecha);
	public List<Composicion> getComposiciones(Integer idDetalle);
	public List<ComposicionConciliado> getComposicionesConciliado(Integer idDetalle);
	public List<ComposicionFacturado> getComposicionesFacturado(Integer idDetalle);
	public void setEstadoPedido(Integer idPedido , Character estado);
	public void autorizar(Integer idPedido,String usuario);
	public List<Pedido> getPedidosPorDespachar(Date fechainicial,Date fechafinal);
	public void editarEncabezado (Pedido encabezado, Date fechafactura);
	public List<PedidoFacturado> getPedidosXAWB(String awb);
	public void cotizar(Cotizacion cotizacion);
	public void crearItemsCotizacion(List <ProductoCotizacion> listaProductos);
	public List <Cotizacion> getCotizaciones(String estado);
	public List <ProductoCotizacion> getProductosCotizacion(Integer idCotizacion);

}
