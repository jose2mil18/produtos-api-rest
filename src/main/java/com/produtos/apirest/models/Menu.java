package com.produtos.apirest.models;

import java.util.List;

import org.springframework.stereotype.Component;
@Component
public class Menu {
	private int cod_menu;
	private String nombre;
	private List<Proceso> procesos;
	public int getCod_menu() {
		return cod_menu;
	}
	public List<Proceso> getProcesos() {
		return procesos;
	}
	public void setProcesos(List<Proceso> procesos) {
		this.procesos = procesos;
	}
	public void setCod_menu(int cod_menu) {
		this.cod_menu = cod_menu;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
