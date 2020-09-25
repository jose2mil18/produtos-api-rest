package com.produtos.apirest.models;
import org.springframework.stereotype.Component;
@Component
public class Institucion {
private String cod_institucion="";
public String getCod_institucion() {
	return cod_institucion;
}
public void setCod_institucion(String cod_institucion) {
	this.cod_institucion = cod_institucion;
}
public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}
private String nombre;
private String cod_institucion_padre;
public String getCod_institucion_padre() {
	return cod_institucion_padre;
}
public void setCod_institucion_padre(String cod_institucion_padre) {
	this.cod_institucion_padre = cod_institucion_padre;
}
}
