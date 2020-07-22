package com.produtos.apirest.models;

import java.util.Calendar;
import java.util.List;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
@Component
public class Paciente {
	private String nombres;
public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

private List<Examen_solicitado> examenes_solicitados;
public List<Examen_solicitado> getExamenes_solicitados() {
	return examenes_solicitados;
}
public void setExamenes_solicitados(List<Examen_solicitado> examenes_solicitados) {
	this.examenes_solicitados = examenes_solicitados;
}

private String correo_electronico;
public String getCorreo_electronico() {
	return correo_electronico;
}
public void setCorreo_electronico(String correo_electronico) {
	this.correo_electronico = correo_electronico;
}

private int cod_persona;
public int getCod_persona() {
	return cod_persona;
}
public void setCod_persona(int cod_persona) {
	this.cod_persona = cod_persona;
}

private String cedula;
private String procedencia;
private String sexo;
private int edad;
public String getCedula() {
	return cedula;
}
public void setCedula(String cedula) {
	this.cedula = cedula;
}
public String getProcedencia() {
	return procedencia;
}
public void setProcedencia(String procedencia) {
	this.procedencia = procedencia;
}
public String getSexo() {
	return sexo;
}
public void setSexo(String sexo) {
	this.sexo = sexo;
}
public int getEdad() {
	return edad;
}
private String fnac;
public String getFnac() {
	return fnac;
}
public void setFnac(String fnac) {
	this.fnac = fnac;
}
public void setEdad(int edad) {
	this.edad = edad;
}
private String cedula_usuario;
public String getCedula_usuario() {
	return cedula_usuario;
}
public void setCedula_usuario(String cedula_personal) {
	this.cedula_usuario = cedula_personal;
}

public Persona getPersona() {
	return persona;
}
public void setPersona(Persona persona) {
	this.persona = persona;
}
private Persona persona;
}
