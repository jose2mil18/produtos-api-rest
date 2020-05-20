package com.produtos.apirest.models;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
@Component
public class Valor {
int cod_resultados_examen;
public int getCod_resultados_examen() {
	return cod_resultados_examen;
}

public void setCod_resultados_examen(int cod_resultados_examen) {
	this.cod_resultados_examen = cod_resultados_examen;
}

int cod;
public int getCod() {
	return cod;
}

public void setCod(int cod) {
	this.cod = cod;
}
String valor;
public String getValor() {
	return valor;
}

public void setValor(String valor) {
	this.valor = valor;
}
}
