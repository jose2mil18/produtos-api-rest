package com.produtos.apirest.models;
import java.util.List;

import org.springframework.stereotype.Component;
@Component
public class Rol {
	private int cod_rol;
private String nombre;
private List<Menu> menus;
public int getCod_rol() {
	return cod_rol;
}
public List<Menu> getMenus() {
	return menus;
}
public void setMenus(List<Menu> menus) {
	this.menus = menus;
}
public void setCod_rol(int cod_rol) {
	this.cod_rol = cod_rol;
}
public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}
}
