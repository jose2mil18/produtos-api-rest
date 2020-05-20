package com.produtos.apirest.models;

import java.util.List;

public class Historial_clinico {
	public List<Examen_solicitado> getExamenes_solicitados() {
		return examenes_solicitados;
	}

	public void setExamenes_solicitados(List<Examen_solicitado> examenes_solicitados) {
		this.examenes_solicitados = examenes_solicitados;
	}

	List<Examen_solicitado> examenes_solicitados;
}
