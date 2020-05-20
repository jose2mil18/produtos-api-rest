package com.produtos.apirest.models;

import java.util.List;

import org.springframework.stereotype.Component;
@Component
public class Precio_examen {
	boolean estado;
public boolean getEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
String cod_institucion;
public String getCod_institucion() {
	return cod_institucion;
}
public void setCod_institucion(String cod_precioexamen) {
	this.cod_institucion = cod_precioexamen;
}
int cod_precio_examen;
public int getCod_precio_examen() {
	return cod_precio_examen;
}
public void setCod_precio_examen(int cod_precio_examen) {
	this.cod_precio_examen = cod_precio_examen;
}
public int getCod_examen() {
	return cod_examen;
}
public void setCod_examen(int cod_examen) {
	this.cod_examen = cod_examen;
}
public float getCosto() {
	return costo;
}
public void setCosto(float valor) {
	this.costo = valor;
}
int cod_examen;
float costo;
Examen examen;
public Examen getExamen() {
	return examen;
}
public void setExamen(Examen examen) {
	this.examen = examen;
}
}
