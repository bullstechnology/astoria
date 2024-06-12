package com.bulls.astoria.persistence;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="empresa")
public class Empresa implements Serializable{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "prefijo", nullable = false)
	private String prefijo;
	@Column(name = "nombre", nullable = true)
	private String nombre;
	@Column(name = "nit", nullable = true)
	private String nit;
	@Column(name = "ib", nullable = true)
	private String ib;
	@Column(name = "bb", nullable = true)
	private String bb;
	@Column(name = "fb", nullable = true)
	private String fb;
	@Column(name = "direccionib", nullable = true)
	private String direccionib;
	@Column(name = "swiftib", nullable = true)
	private String swiftib;
	@Column(name = "cuentaib", nullable = true)
	private String cuentaib;
	@Column(name = "direccionbb", nullable = true)
	private String direccionbb;
	@Column(name = "swiftbb", nullable = true)
	private String swiftbb;
	@Column(name = "direccionfb", nullable = true)
	private String direccionfb;
	@Column(name = "cuentafb", nullable = true)
	private String cuentafb;
	@Column(name = "direccion", nullable = true)
	private String direccion;
	@Column(name = "telefono", nullable = true)
	private String telefono;
	@Column(name = "celular", nullable = true)
	private String celular;
	@Column(name = "web", nullable = true)
	private String web;
	@Column(name = "correo", nullable = true)
	private String correo;
	@Column(name = "fax", nullable = true)
	private String fax;
	@Column(name = "ciudadpais", nullable = true)
	private String ciudadpais;
	@Column(name = "replegal", nullable = true)
	private String replegal;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPrefijo() {
		return prefijo;
	}
	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}
	public String getNit() {
		return nit;
	}
	public void setNit(String nit) {
		this.nit = nit;
	}
	public String getIb() {
		return ib;
	}
	public void setIb(String ib) {
		this.ib = ib;
	}
	public String getBb() {
		return bb;
	}
	public void setBb(String bb) {
		this.bb = bb;
	}
	public String getFb() {
		return fb;
	}
	public void setFb(String fb) {
		this.fb = fb;
	}
	public String getDireccionib() {
		return direccionib;
	}
	public void setDireccionib(String direccionib) {
		this.direccionib = direccionib;
	}
	public String getSwiftib() {
		return swiftib;
	}
	public void setSwiftib(String swiftib) {
		this.swiftib = swiftib;
	}
	public String getCuentaib() {
		return cuentaib;
	}
	public void setCuentaib(String cuentaib) {
		this.cuentaib = cuentaib;
	}
	public String getDireccionbb() {
		return direccionbb;
	}
	public void setDireccionbb(String direccionbb) {
		this.direccionbb = direccionbb;
	}
	public String getSwiftbb() {
		return swiftbb;
	}
	public void setSwiftbb(String swiftbb) {
		this.swiftbb = swiftbb;
	}
	public String getDireccionfb() {
		return direccionfb;
	}
	public void setDireccionfb(String direccionfb) {
		this.direccionfb = direccionfb;
	}
	public String getCuentafb() {
		return cuentafb;
	}
	public void setCuentafb(String cuentafb) {
		this.cuentafb = cuentafb;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getCiudadpais() {
		return ciudadpais;
	}
	public void setCiudadpais(String ciudadpais) {
		this.ciudadpais = ciudadpais;
	}
	public String getReplegal() {
		return replegal;
	}
	public void setReplegal(String replegal) {
		this.replegal = replegal;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Override
	public String toString() {
		return "Empresa [id=" + id + ", prefijo=" + prefijo + ", nombre="
				+ nombre + ", nit=" + nit + ", ib=" + ib + ", bb=" + bb
				+ ", fb=" + fb + ", direccionib=" + direccionib + ", swiftib="
				+ swiftib + ", cuentaib=" + cuentaib + ", direccionbb="
				+ direccionbb + ", swiftbb=" + swiftbb + ", direccionfb="
				+ direccionfb + ", cuentafb=" + cuentafb + ", direccion="
				+ direccion + ", telefono=" + telefono + ", celular=" + celular
				+ ", web=" + web + ", correo=" + correo + ", fax=" + fax
				+ ", ciudadpais=" + ciudadpais + ", replegal=" + replegal + "]";
	}
	
	

}