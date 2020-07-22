package com.produtos.apirest.models;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;
public class Personal {
	private String link_foto;
public String getLink_foto() {
		return link_foto;
	}
	public void setLink_foto(String link_foto) {
		this.link_foto = link_foto;
	}
private File archivo;
	public File getArchivo() {
		return archivo;
	}
	public void setArchivo(File archivo) {
		this.archivo = archivo;
	}
	private String foto;
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	private String cedula;
private int cod_persona;
private String profesion;
private Persona persona;
public Persona getPersona() {
	return persona;
}
public void setPersona(Persona persona) {
	this.persona = persona;
}
public String getCedula() {
	return cedula;
}
public void setCedula(String cedula) {
	this.cedula = cedula;
}
public int getCod_persona() {
	return cod_persona;
}
public void setCod_persona(int cod_persona) {
	this.cod_persona = cod_persona;
}
public String getProfesion() {
	return profesion;
}
public void setProfesion(String profesion) {
	this.profesion = profesion;
}
}
