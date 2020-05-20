package com.produtos.apirest.models;

import java.util.List;

public class Reporte_examenes_solicitados {
Precio_examen precio_examen;
public Precio_examen getPrecio_examen() {
	return precio_examen;
}
public void setPrecio_examen(Precio_examen precio_examen) {
	this.precio_examen = precio_examen;
}
public int getNro_prestaciones() {
	return nro_prestaciones;
}
public void setNro_prestaciones(int nro_prestaciones) {
	this.nro_prestaciones = nro_prestaciones;
}
public List<Solicitud> getSolicitudes() {
	return solicitudes;
}
public void setSolicitudes(List<Solicitud> solicitudes) {
	this.solicitudes = solicitudes;
}
int nro_prestaciones;
List<Solicitud> solicitudes;
}
