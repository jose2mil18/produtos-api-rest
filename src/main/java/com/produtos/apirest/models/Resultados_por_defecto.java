package com.produtos.apirest.models;

import org.springframework.stereotype.Component;

@Component
public class Resultados_por_defecto {
public String getValor() {
	return valor;
}
public void setValor(String valor) {
	this.valor = valor;
}
private String valor;
private int cod_examen;
private int cod;
public int getCod() {
	return cod;
}
public void setCod(int cod) {
	this.cod = cod;
}
public int getCod_examen() {
	return cod_examen;
}
public void setCod_examen(int cod_examen) {
	this.cod_examen = cod_examen;
}
}
