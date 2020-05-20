package com.produtos.apirest.models;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class Resultados_examen {
	int cod_resultados_examen_padre;
	public int getCod_resultados_examen_padre() {
		return cod_resultados_examen_padre;
	}
	public void setCod_resultados_examen_padre(int cod_resultados_examen_padre) {
		this.cod_resultados_examen_padre = cod_resultados_examen_padre;
	}
	int cod_resultados_examen;
	public int getCod_resultados_examen() {
		return cod_resultados_examen;
	}
	public void setCod_resultados_examen(int cod_resultados_examen) {
		this.cod_resultados_examen = cod_resultados_examen;
	}
	int cod_sol_exam;
	public int getCod_sol_exam() {
		return cod_sol_exam;
	}
	public void setCod_sol_exam(int cod_sol_exam) {
		this.cod_sol_exam = cod_sol_exam;
	}
	int cod_examen;
public int getCod_examen() {
	return cod_examen;
}
public void setCod_examen(int cod_subexamen) {
	this.cod_examen = cod_subexamen;
}
List<Valor> valores;
public List<Valor> getValores() {
	return valores;
}
public void setValores(List<Valor> valores) {
	this.valores = valores;
}
Examen examen;
List<Resultados_examen> resultados_examenes;
public List<Resultados_examen> getResultados_examenes() {
	return resultados_examenes;
}
public void setResultados_examenes(List<Resultados_examen> resultados_examenes) {
	this.resultados_examenes = resultados_examenes;
}
public Examen getExamen() {
	return examen;
}
public void setExamen(Examen subexamen) {
	this.examen = subexamen;
}

int num_resultados_examenes;
public int getNum_resultados_examenes() {
	return num_resultados_examenes;
}
public void setNum_resultados_examenes(int num_resultados_examenes) {
	this.num_resultados_examenes = num_resultados_examenes;
}
public Resultados_examen() {


	this.resultados_examenes = new ArrayList<Resultados_examen>();
	

}


}
