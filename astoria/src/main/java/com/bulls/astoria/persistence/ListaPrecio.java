package com.bulls.astoria.persistence;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;



@Entity
@Table(name="listaprecios")
public class ListaPrecio implements Serializable{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "tipo", nullable = false)
	private Character tipo;
	@Column(name = "idfranja", nullable = true)
	private Integer idfranja;
	@Column(name = "idplantacion", nullable = false)
	private Integer idplantacion;
	
	
	 @OneToMany(fetch = FetchType.LAZY, mappedBy = "listaPrecio")
    private Set<DetalleListaPrecio> detalleListaPrecio;
	
	
	
	
	 public Set<DetalleListaPrecio> getDetalleListaPrecio() {
		 return detalleListaPrecio;
	 }
		public void setDetalleListaPrecio(Set<DetalleListaPrecio> detalleListaPrecio) {
			this.detalleListaPrecio = detalleListaPrecio;
		}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Character getTipo() {
		return tipo;
	}
	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}
	public Integer getIdfranja() {
		return idfranja;
	}
	public void setIdfranja(Integer idfranja) {
		this.idfranja = idfranja;
	}
	public Integer getIdplantacion() {
		return idplantacion;
	}
	public void setIdplantacion(Integer idplantacion) {
		this.idplantacion = idplantacion;
	}
	@Override
	public String toString() {
		return "ListaPrecio [id=" + id + ", tipo=" + tipo + ", idfranja="
				+ idfranja + ", idplantacion=" + idplantacion
				+ "]";
	}

	
	
	
	
	

}
