package com.produtos.apirest.models;

import java.util.List;

import org.springframework.stereotype.Component;
@Component
public class Reporte_anual {
	private Institucion institucion;
	private double monto_total;
	private int anio;
	public Institucion getInstitucion() {
		return institucion;
	}
	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}
	public double getMonto_total() {
		return monto_total;
	}
	public void setMonto_total(double monto_total) {
		this.monto_total = monto_total;
	}
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio ){
		this.anio = anio;
	}
	public int getNro_prestaciones_total() {
		return nro_prestaciones_total;
	}
	public void setNro_prestaciones_total(int nro_prestaciones_total) {
		this.nro_prestaciones_total = nro_prestaciones_total;
	}
	private int nro_prestaciones_total;
	private List<Reporte_mensual> reportes_mensuales;
	public List<Reporte_mensual> getReportes_mensuales() {
		return reportes_mensuales;
	}
	public void setReportes_mensuales(List<Reporte_mensual> reportes_mensuales) {
		this.reportes_mensuales = reportes_mensuales;
	}

}
