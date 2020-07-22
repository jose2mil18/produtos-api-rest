package com.produtos.apirest.models;
import org.springframework.stereotype.Component;
@Component
public class Proceso {
private int cod_proceso;
private String nombre;
private String enlace;
public int getCod_proceso() {
	return cod_proceso;
}
public void setCod_proceso(int cod_proceso) {
	this.cod_proceso = cod_proceso;
}
public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}
public String getEnlace() {
	return enlace;
}
public void setEnlace(String enlace) {
	this.enlace = enlace;
}

}
