package com.produtos.apirest.models;

import org.springframework.stereotype.Component;

@Component
public class Persona{
	private int cod_persona;
private String tipo;
	public String getTipo() {
	return tipo;
}


public void setTipo(String tipo) {
	this.tipo = tipo;
}


	public int getCod_persona() {
		return cod_persona;
	}


	public void setCod_persona(int cod_persona) {
		this.cod_persona = cod_persona;
	}


	private String nombre;

	private String ap;
	
	
	private String am;


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getAp() {
		return ap;
	}


	public void setAp(String ap) {
		this.ap = ap;
	}


	public String getAm() {
		return am;
	}


	public void setAm(String am) {
		this.am = am;
	}
	

}
