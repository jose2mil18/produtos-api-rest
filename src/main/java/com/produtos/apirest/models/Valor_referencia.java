package com.produtos.apirest.models;


import org.springframework.stereotype.Component;

@Component
public class Valor_referencia {
	private boolean estado;
	public boolean getEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	private int cod_valor_referencia;
public int getCod_valor_referencia() {
		return cod_valor_referencia;
	}
	public void setCod_valor_referencia(int cod_valor_referencia) {
		this.cod_valor_referencia = cod_valor_referencia;
	}
private int cod_examen;
private double valor_inicial;
private double valor_final;
public int getCod_examen() {
	return cod_examen;
}
public void setCod_examen(int cod_examen) {
	this.cod_examen = cod_examen;
}
public double getValor_inicial() {
	return valor_inicial;
}
public void setValor_inicial(double valor_inicial) {
	this.valor_inicial = valor_inicial;
}
public double getValor_final() {
	return valor_final;
}
public void setValor_final(double valor_final) {
	this.valor_final = valor_final;
}
public String getTipo_persona() {
	return tipo_persona;
}
public void setTipo_persona(String tipo_persona) {
	this.tipo_persona = tipo_persona;
}
private String tipo_persona;
}
