package com.produtos.apirest.models;
import org.springframework.stereotype.Component;
import java.util.*;
@Component
public class Examen_solicitado {
	private int cod_precio_examen;
	public int getCod_precio_examen() {
		return cod_precio_examen;
	}
	public void setCod_precio_examen(int cod_precio_examen) {
		this.cod_precio_examen = cod_precio_examen;
	}
	private Solicitud solicitud;
public Solicitud getSolicitud() {
		return solicitud;
	}
	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}
private int cod_solicitud;
private String estado;
private Precio_examen precio_examen; 
public Precio_examen getPrecio_examen() {
	return precio_examen;
}
public void setPrecio_examen(Precio_examen precio_examen) {
	this.precio_examen = precio_examen;
}
private Date fecha;
private String cedula_usuario;
private Resultados_examen resultados_examen;
public Examen_solicitado() {


	this.resultados_examen = new Resultados_examen();
}
public Resultados_examen getResultados_examen() {
	return resultados_examen;
}
public void setResultados_examen(Resultados_examen resultados_examen) {
	this.resultados_examen = resultados_examen;
}
public String getCedula_usuario() {
	return cedula_usuario;
}
public int getCod_sol_exam() {
	return cod_sol_exam;
}
public void setCod_sol_exam(int cod_sol_exam) {
	this.cod_sol_exam = cod_sol_exam;
}
private int cod_sol_exam;
public void setCedula_usuario(String cedula_usuario) {
	this.cedula_usuario = cedula_usuario;
}
public Date getFecha() {
	return fecha;
}
public void setFecha(Date fecha) {
	this.fecha = fecha;
}
public int getCod_solicitud() {
	return cod_solicitud;
}
public void setCod_solicitud(int cod_solicitud) {
	this.cod_solicitud = cod_solicitud;
}
public String getEstado() {
	return estado;
}
public void setEstado(String estado) {
	this.estado = estado;
}
private int num_subexamenes;
public int getNum_subexamenes() {
	return num_subexamenes;
}
public void setNum_subexamenes(int num_subexamenes) {
	this.num_subexamenes = num_subexamenes;
}
public Usuario getUsuario() {
	return usuario;
}
public void setUsuario(Usuario usuario) {
	this.usuario = usuario;
}
private Usuario usuario;
}
