package com.produtos.apirest.models;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class Examen  {
private boolean estado;
	public boolean getEstado() {
	return estado;
}
public void setEstado(boolean estado) {
	this.estado = estado;
}
	private int cod_examen;
	public int getCod_examen() {
		return cod_examen;
	}
	public void setCod_examen(int cod_examen) {
		this.cod_examen = cod_examen;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getCod_area() {
		return cod_area;
	}
	public void setCod_area(int cod_area) {
		this.cod_area = cod_area;
	}
	public String getUnidades() {
		return unidades;
	}
	public void setUnidades(String unidades) {
		this.unidades = unidades;
	}
	private String nombre;
	private int  cod_area;
private String unidades="";
private String valor_referencia;
public String getValor_referencia() {
	return valor_referencia;
}
public void setValor_referencia(String valor_referencia) {
	this.valor_referencia = valor_referencia;
}
private List<Valor_referencia> valores_referencia;
public List<Valor_referencia> getValores_referencia() {
	return valores_referencia;
}
public void setValores_referencia(List<Valor_referencia> valores_referencia) {
	this.valores_referencia = valores_referencia;
}
private List<Examen> subexamenes;
private List<Resultados_por_defecto> resultados_por_defecto;
public List<Resultados_por_defecto> getResultados_por_defecto() {
	return resultados_por_defecto;
}
public void setResultados_por_defecto(List<Resultados_por_defecto> resultados_por_defecto) {
	this.resultados_por_defecto = resultados_por_defecto;
}
public List<Examen> getSubexamenes() {
	return subexamenes;
}
public void setSubexamenes(List<Examen> subexamenes) {
	this.subexamenes = subexamenes;
}

private List<Precio_examen> precios;
public List<Precio_examen> getPrecios() {
	return precios;
}
public void setPrecios(List<Precio_examen> precio) {
	this.precios = precio;
}
private int num_subexamenes;
public int getNum_subexamenes() {
	return num_subexamenes;
}
public void setNum_subexamenes(int num_subexamenes) {
	this.num_subexamenes = num_subexamenes;
}
private Area area;
public Area getArea() {
	return area;
}
public void setArea(Area area) {
	this.area = area;
}

}
