package com.produtos.apirest.models;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Solicitud   {
	private float costoTotal;
	public float getCostoTotal() {
		return costoTotal;
	}
	public void setCostoTotal(float costoTotal) {
		this.costoTotal = costoTotal;
	}
	public Factura getFactura() {
		return factura;
	}
	public void setFactura(Factura factura) {
		this.factura = factura;
	}
	private Factura factura;
	private String examenes_solicitados_de_solicitud;
	public String getExamenes_solicitados_de_solicitud() {
		return examenes_solicitados_de_solicitud;
	}
	public void setExamenes_solicitados_de_solicitud(String examenes_solicitados_de_solicitud) {
		this.examenes_solicitados_de_solicitud = examenes_solicitados_de_solicitud;
	}
	private String cedula_paciente;
	public String getCedula_paciente() {
		return cedula_paciente;
	}
	public void setCedula_paciente(String cedula_paciente) {
		this.cedula_paciente = cedula_paciente;
	}
	private String estado_solicitud;
	public String getEstado_solicitud() {
		return estado_solicitud;
	}
	public void setEstado_solicitud(String estado_solicitud) {
		this.estado_solicitud = estado_solicitud;
	}
	private String cedula_usuario;
	/*
	List<Examen> examenes;*/
	public String getCedula_usuario() {
		return cedula_usuario;
	}
	public void setCedula_usuario(String cedula_personal) {
		this.cedula_usuario = cedula_personal;
	}
	/*
	public List<Examen> getExamenes() {
		return examenes;
	}
	public void setExamenes(List<Examen> examenes) {
		this.examenes = examenes;
	}
	

*/

 public int getCod_solicitud() {
		return cod_solicitud;
	}
	public void setCod_solicitud(int cod_solicitud) {
		this.cod_solicitud = cod_solicitud;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String resultados) {
		this.estado = resultados;
	}
private Paciente paciente;
private int gestion;
public int getGestion() {
	return gestion;
}
public void setGestion(int gestion) {
	this.gestion = gestion;
}
public Paciente getPaciente() {
	return paciente;
}
public Institucion getInstitucion() {
	return institucion;
}
public void setInstitucion(Institucion institucion) {
	this.institucion = institucion;
}
public void setPaciente(Paciente paciente) {
	this.paciente = paciente;
}
private String fecha;
private String fecha_entrega;
public String getFecha() {
	return fecha;
}
public void setFecha(String fecha) {
	this.fecha = fecha;
}
public String getFecha_entrega() {
	return fecha_entrega;
}
public void setFecha_entrega(String fecha_entrega) {
	this.fecha_entrega = fecha_entrega;
}
private Persona doctor_solicitante;
public Persona getDoctor_solicitante() {
	return doctor_solicitante;
}
public void setDoctor_solicitante(Persona doctor_solicitante) {
	this.doctor_solicitante = doctor_solicitante;
}
private int cod_solicitud;
private Institucion institucion;
private List<Examen_solicitado> examenes_solicitados;

public List<Examen_solicitado> getExamenes_solicitados_con_resultados_actualizados() {
	return examenes_solicitados_con_resultados_actualizados;
}
public void setExamenes_solicitados_con_resultados_actualizados(
		List<Examen_solicitado> examenes_solicitados_con_resultados_actualizados) {
	this.examenes_solicitados_con_resultados_actualizados = examenes_solicitados_con_resultados_actualizados;
}
private List<Examen_solicitado> examenes_solicitados_con_resultados_actualizados;
public List<Examen_solicitado> getExamenes_solicitados() {
	return examenes_solicitados;
}
public void setExamenes_solicitados(List<Examen_solicitado> examenes_solicitados) {
	this.examenes_solicitados = examenes_solicitados;
}
private String estado;

}
