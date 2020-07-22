package com.produtos.apirest.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

//@Entity
//@Table(name="usuario")
@Component
public class Usuario{
	private Rol rol;
	public Rol getRol() {
		return rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}

	private String cedula;
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	private Personal personal_laboratorio;
	
	
	
	
	public Personal getPersonal_laboratorio() {
		return personal_laboratorio;
	}
	public void setPersonal_laboratorio(Personal personal) {
		this.personal_laboratorio = personal;
	}

	private  String login;
	//@NotNull
	//@Column(name="password")
	private String password;
	//@NotNull
	//@Column(name="estado")
	private String estado;

}
