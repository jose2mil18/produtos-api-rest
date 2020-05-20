package com.produtos.apirest.models;
import java.util.List;

import org.springframework.stereotype.Component;
@Component
public class Reporte_examen_mensual {
	public Precio_examen getPrecio_examen() {
		return precio_examen;
	}
	public void setPrecio_examen(Precio_examen precio_examen) {
		this.precio_examen = precio_examen;
	}
	public int getNro_prestaciones() {
		return nro_prestaciones;
	}
	public void setNro_prestaciones(int nro_prestaciones) {
		this.nro_prestaciones = nro_prestaciones;
	}
	Precio_examen precio_examen;
	int nro_prestaciones;
	float costo_total_examen;
	public float getCosto_total_examen() {
		return costo_total_examen;
	}
	public void setCosto_total_examen(float costo_total_examen) {
		this.costo_total_examen = costo_total_examen;
	}
	int anio;
	int mes;
	Institucion institucion;
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public Institucion getInstitucion() {
		return institucion;
	}
	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

}
