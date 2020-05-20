package com.produtos.apirest.models;


import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;


@Component
public class Area {
	
	
	private int cod_area;
	

	private String nombre;

	public int getCod_area() {
		return cod_area;
	}

	public void setCod_area(int cod_area) {
		this.cod_area = cod_area;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	
	
	

}
