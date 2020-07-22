package com.produtos.apirest.models;

import java.util.List;

public class Reporte_examenes_solicitados {
	private List<Examen_solicitado> examenes_solicitados;

	public List<Examen_solicitado> getExamenes_solicitados() {
		return examenes_solicitados;
	}
	public void setExamenes_solicitados(List<Examen_solicitado> examenes_solicitados) {
		this.examenes_solicitados = examenes_solicitados;
	}
	private String grupo;
public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
public int getNro_prestaciones() {
	return nro_prestaciones;
}
public void setNro_prestaciones(int nro_prestaciones) {
	this.nro_prestaciones = nro_prestaciones;
}
private int nro_prestaciones;
}
