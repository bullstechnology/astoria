package com.bulls.astoria.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name="detallelistaprecios")
public class DetalleListaPrecio implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	@Column(name = "idproducto", nullable = false)
	private Integer idproducto;
	
	@Column(name = "precio", nullable = true)
	private Double precio;
	
	
	@Column(name = "idcolor", nullable = false)
	private Integer idcolor;
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idlista", nullable = false)
    private ListaPrecio listaPrecio;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getIdproducto() {
		return idproducto;
	}
	public void setIdproducto(Integer idproducto) {
		this.idproducto = idproducto;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	

	public ListaPrecio getListaPrecio() {
		return listaPrecio;
	}
	public void setListaPrecio(ListaPrecio listaPrecio) {
		this.listaPrecio = listaPrecio;
	}
	
	public Integer getIdcolor() {
		return idcolor;
	}
	public void setIdcolor(Integer idcolor) {
		this.idcolor = idcolor;
	}
	@Override
	public String toString() {
		return "DetalleListaPrecio [id=" + id + ", idproducto=" + idproducto
				+ ", precio=" + precio + ", idcolor=" + idcolor
				+ "]";
	}
	
	
	
	

    
}
