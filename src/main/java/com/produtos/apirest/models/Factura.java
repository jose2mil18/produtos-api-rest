package com.produtos.apirest.models;


import java.util.Date;

import Utilidades.UFormat;

public class Factura {
	private int cod_solicitud;
	public int getCod_solicitud() {
		return cod_solicitud;
	}
	public void setCod_solicitud(int cod_solicitud) {
		this.cod_solicitud = cod_solicitud;
	}
	private int cod_factura;
	public int getCod_factura() {
		return cod_factura;
	}
	public void setCod_factura(int cod_factura) {
		this.cod_factura = cod_factura;
	}
	public String getCod_control() {
		return cod_control;
	}
	public void setCod_control(String cod_control) {
		this.cod_control = cod_control;
	}
	public int getCod_dosificacion() {
		return cod_dosificacion;
	}
	public void setCod_dosificacion(int cod_dosificacion) {
		this.cod_dosificacion = cod_dosificacion;
	}
	private String cod_control;
	private int cod_dosificacion;
	private Dosificacion dosificacion;
	public Dosificacion getDosificacion() {
		return dosificacion;
	}
	public void setDosificacion(Dosificacion dosificacion) {
		this.dosificacion = dosificacion;
	}
}
