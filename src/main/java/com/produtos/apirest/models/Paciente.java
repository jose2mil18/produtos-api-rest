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
	String nombres;
public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

List<Examen_solicitado> examenes_solicitados;
public List<Examen_solicitado> getExamenes_solicitados() {
	return examenes_solicitados;
}
public void setExamenes_solicitados(List<Examen_solicitado> examenes_solicitados) {
	this.examenes_solicitados = examenes_solicitados;
}

String correo_electronico;
public String getCorreo_electronico() {
	return correo_electronico;
}
public void setCorreo_electronico(String correo_electronico) {
	this.correo_electronico = correo_electronico;
}

int cod_persona;
public int getCod_persona() {
	return cod_persona;
}
public void setCod_persona(int cod_persona) {
	this.cod_persona = cod_persona;
}

String cedula;
String procedencia;
String sexo;
Calendar fecha_nacimiento;
public Calendar getFecha_nacimiento() {
	return fecha_nacimiento;
}
public void setFecha_nacimiento(Calendar fecha_nacimiento) {
	this.fecha_nacimiento = fecha_nacimiento;
}

int edad;
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
String fnac;
public String getFnac() {
	return fnac;
}
public void setFnac(String fnac) {
	this.fnac = fnac;
}
public void setEdad(int edad) {
	this.edad = edad;
}
String cedula_usuario;
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
Persona persona;
}
