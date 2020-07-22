package com.produtos.apirest.models;
import java.util.Calendar;
import java.util.Date;

import Utilidades.UFormat;
public class Dosificacion {


	
	
	
 private int cod_dosificacion, nit, nro_emisiones;
	private String llave, autorizacion, leyenda;
	
	public String getFecha_registro() {
		return fecha_registro;
	}
	public void setFecha_registro(String fecha_registro) {
		this.fecha_registro = fecha_registro;
	}
	public String getFecha_limite_emision() {
		return fecha_limite_emision;
	}
	public void setFecha_limite_emision(String fecha_limite_emision) {
		this.fecha_limite_emision = fecha_limite_emision;
	}
	private String fecha_registro, fecha_limite_emision;
	
	public int getCod_dosificacion() {
		return cod_dosificacion;
	}
	public void setCod_dosificacion(int id_dosificacion) {
		this.cod_dosificacion = id_dosificacion;
	}
	public int getNit() {
		return nit;
	}
	public void setNit(int nit) {
		this.nit = nit;
	}
	public int getNro_emisiones() {
		return nro_emisiones;
	}
	public void setNro_emisiones(int nro_emisiones) {
		this.nro_emisiones = nro_emisiones;
	}
	public String getLlave() {
		return llave;
	}
	public void setLlave(String llave) {
		this.llave = llave;
	}
	public String getAutorizacion() {
		return autorizacion;
	}
	public void setAutorizacion(String autorizacion) {
		this.autorizacion = autorizacion;
	}
	public String getLeyenda() {
		return leyenda;
	}
	public void setLeyenda(String leyenda) {
		this.leyenda = leyenda;
	}


}
