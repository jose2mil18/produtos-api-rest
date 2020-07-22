package com.produtos.apirest.models;

import java.util.List;

import org.springframework.stereotype.Component;
@Component
public class Reporte_mensual {
	private String mes;
	private float monto;
	public float getMonto() {
		return monto;
	}
	public void setMonto(float monto) {
		this.monto = monto;
	}
	private int nro_prestaciones;
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public int getNro_prestaciones() {
		return nro_prestaciones;
	}
	public void setNro_prestaciones(int nro_prestaciones) {
		this.nro_prestaciones = nro_prestaciones;
	}
	public Institucion getInstitucion() {
		return institucion;
	}
	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}
	private Institucion institucion;
	private int anio;
public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}
	private List<Reporte_examen_mensual> reportes_examenes_mensual;
	public List<Reporte_examen_mensual> getReportes_examenes_mensual() {
		return reportes_examenes_mensual;
	}
	public void setReportes_examenes_mensual(List<Reporte_examen_mensual> reportes_examenes_mensual) {
		this.reportes_examenes_mensual = reportes_examenes_mensual;
	}

}
