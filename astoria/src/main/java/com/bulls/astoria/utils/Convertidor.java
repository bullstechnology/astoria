package com.bulls.astoria.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import com.bulls.astoria.persistence.AgenciaCarga;
import com.bulls.astoria.persistence.Cliente;
import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.Handler;
import com.bulls.astoria.persistence.Persona;
import com.bulls.astoria.persistence.Plantacion;
import com.bulls.astoria.persistence.PlantacionProducto;
import com.bulls.astoria.persistence.Truck;
import com.bulls.astoria.pojo.CatalogoBean;
import com.bulls.astoria.pojo.Producto;
import com.bulls.astoria.pojo.ProductoPrecio;


public final class Convertidor {
	
	public static List<SelectItem> dominiosToSelectdItems(List <Dominio> lista){
		
		List<SelectItem> generico = new ArrayList<SelectItem>();
		generico = new ArrayList<SelectItem>();
		for(Dominio dominio : lista){
		   generico.add(new SelectItem(dominio.getId(), dominio.getNomcorto()));
		}
		return generico;
	}
	
public static List<SelectItem> personaToSelectdItems(List <Persona> lista){
		
		List<SelectItem> generico = new ArrayList<SelectItem>();
		generico = new ArrayList<SelectItem>();
		for(Persona persona : lista){
		   generico.add(new SelectItem(persona.getId(), persona.getNombre()+" " + persona.getSegnombre() + " " +persona.getApellido()+" " +persona.getSegapellido() ));
		}
		return generico;
	}

public static List<SelectItem> clientesToSelectdItems(List <Cliente> lista){
	
	List<SelectItem> generico = new ArrayList<SelectItem>();
	generico = new ArrayList<SelectItem>();
	for(Cliente cliente : lista){
	   generico.add(new SelectItem(cliente.getId(), cliente.getCodigo() + " " +cliente.getNombre()));
	}
	return generico;
}

public static List<SelectItem> agenciasToSelectdItems(List <AgenciaCarga> lista){
	
	List<SelectItem> generico = new ArrayList<SelectItem>();
	generico = new ArrayList<SelectItem>();
	for(AgenciaCarga agencia : lista){
	   generico.add(new SelectItem(agencia.getId(), agencia.getCodigo() + " " +agencia.getNombre()));
	}
	return generico;
}

public static List<SelectItem> handlersToSelectdItems(List <Handler> lista){
	
	List<SelectItem> generico = new ArrayList<SelectItem>();
	generico = new ArrayList<SelectItem>();
	for(Handler handler : lista){
	   generico.add(new SelectItem(handler.getId(), handler.getCodigo() + " " +handler.getNombre()));
	}
	return generico;
}
	
/*public static List<SelectItem> dominiosToSelectdItems2(List <Dominio> lista){
		
		List<SelectItem> generico = new ArrayList<SelectItem>();
		generico = new ArrayList<SelectItem>();
		for(Dominio dominio : lista){ap
			
		   generico.add(new SelectItem(Integer.toString(dominio.getId().intValue()), dominio.getNomcorto()));
		}
		return generico;
	}*/
	
public static List<SelectItem> mapToSelectdItems(List <Map> lista){
		
		List<SelectItem> generico = new ArrayList<SelectItem>();
		generico = new ArrayList<SelectItem>();
		Iterator ite = lista.iterator();
				while ( ite.hasNext() ) {
					
					Map o = (Map) ite.next();
			        Object pp =   o.get("idpadre");
			        Object pp2 =  o.get("nompadre");
			        Object pp3 =  o.get("idhijo");
			        Object pp4 =  o.get("nomhijo");
			        
			        
			        
			        generico.add(new SelectItem((Integer) pp3, (String) pp2 +  "-" + (String) pp4));
		        
				}
		
		
		return generico;
	}

public static List<SelectItem> mapToSelectdItemsIdNombre(List <Map> lista){
	
	List<SelectItem> generico = new ArrayList<SelectItem>();
	generico = new ArrayList<SelectItem>();
	Iterator ite = lista.iterator();
	boolean ok=false;
			while ( ite.hasNext() ) {
				ok=false;
				Map o = (Map) ite.next();
		        Object pp =   o.get("id");
		        Object pp2 =  o.get("nombre");
		        
		        for(SelectItem st:generico){
		        	if(((Integer)st.getValue()).intValue() == ((Integer)pp).intValue())
		        		ok=true;
		        }
		        if(!ok)
		        	generico.add(new SelectItem((Integer) pp, (String) pp2));
			}
	
	
	return generico;
}

public static List<SelectItem> ProductoPojoToSelectdItems(List <Producto> lista){
	
	List<SelectItem> generico = new ArrayList<SelectItem>();
	generico = new ArrayList<SelectItem>();
	
	for(Producto pro:lista){
		
		generico.add(new SelectItem(pro.getCodigo(),pro.getNombrePadre()+"-"+pro.getNombreProducto() + "-" + pro.getNombregrado()));
	}
	
	return generico;
}


public static List<Producto> mapProductoToProducto(List <Map> lista){
	
	List<Producto> generico = new ArrayList<Producto>();
	generico = new ArrayList<Producto>();
	Iterator ite = lista.iterator();
			while ( ite.hasNext() ) {
				
				Map o = (Map) ite.next();
		        Object pp =   o.get("idpadre");
		        Object pp2 =  o.get("nompadre");
		        Object pp3 =  o.get("idhijo");
		        Object pp4 =  o.get("nomhijo");
		        Producto producto = new Producto((Integer) pp,(String) pp2,(Integer) pp3,(String) pp4);
		        generico.add(producto);
	        
			}
	
	return generico;
}


public static List<SelectItem>  handlerToSelectdItems(List <Handler> lista){
	List<SelectItem> generico = new ArrayList<SelectItem>();
	generico = new ArrayList<SelectItem>();
	for(Handler handler : lista){
	   generico.add(new SelectItem(handler.getId(), handler.getCodigo() + " " + handler.getNombre()));
	}
	return generico;
}

public static List<SelectItem>  truckToSelectdItems(List <Truck> lista){
	List<SelectItem> generico = new ArrayList<SelectItem>();
	generico = new ArrayList<SelectItem>();
	for(Truck truck : lista){
	   generico.add(new SelectItem(truck.getId(), truck.getCodigo() + " " + truck.getNombre()));
	}
	return generico;
}


public static List<SelectItem> plantacionToSelectdItems(List <Plantacion> lista){
	
	List<SelectItem> generico = new ArrayList<SelectItem>();
	generico = new ArrayList<SelectItem>();
	for(Plantacion plantacion : lista){
	   generico.add(new SelectItem(plantacion.getId(), plantacion.getCodigo()+" " + plantacion.getNombre()));
	}
	return generico;
}

public static List<SelectItem> productoPrecioToSelectdItems(List <ProductoPrecio> lista){
	
	List<SelectItem> generico = new ArrayList<SelectItem>();
	generico = new ArrayList<SelectItem>();
	
	for(ProductoPrecio producto : lista){
	   generico.add(new SelectItem(producto.getIdProducto(), producto.getNombreProducto()));
	}
	return generico;
}

public static List<CatalogoBean> mapVariedadToCatalogoBean(List <Map> lista){
	
	List<CatalogoBean> generico = new ArrayList<CatalogoBean>();
	Iterator ite = lista.iterator();
			while ( ite.hasNext() ) {
				
				Map o = (Map) ite.next();
		        Object idTipoFlor =   o.get("idTipoFlor");
		        Object idVariedad =  o.get("idVariedad");
		        Object idColor =  o.get("idColor");
		        Object nombreflor =  o.get("nombretipoflor");
		        Object nombrevariedad =  o.get("nombrevariedad");
		        Object nombrecolor =  o.get("nombrecolor");
		        Object url =  o.get("url");
		        CatalogoBean catalogo = new CatalogoBean((Integer) idTipoFlor,(Integer) idVariedad,(Integer) idColor,(String) nombreflor,(String) nombrevariedad,(String) nombrecolor,(String) url);
		        generico.add(catalogo);
	        
			}
	
	return generico;
}
 






}
