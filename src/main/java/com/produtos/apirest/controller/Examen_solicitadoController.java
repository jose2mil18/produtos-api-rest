package com.produtos.apirest.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.produtos.apirest.models.Resultados_examen;
import com.produtos.apirest.models.Examen;
import com.produtos.apirest.models.Paciente;
import com.produtos.apirest.models.Persona;
import com.produtos.apirest.models.Examen_solicitado;
import com.produtos.apirest.models.Solicitud;

import com.produtos.apirest.models.Usuario;
import com.produtos.apirest.Services.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;


import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api")
@Api(value="API REST solicitud")
public class Examen_solicitadoController {
	@Autowired
	ServicioSolicitud servicioSolicitud;

	@Autowired
	ServicioExamen servicioExamen;
	
	@Autowired
	ServicioExamen_solicitado servicioExamen_solicitado;
	
	
	@ApiOperation(value="filtra solicitudes de examenes clinicos por cedula de paciente y area ")
	@PostMapping("/filtrar-solicitudes-de-paciente-por-area-de-examen")
	public List<Examen_solicitado> filtrar_solicitudes_por_cedula_paciente_y_area(@RequestBody Map<String, String> body){
		
		return servicioExamen_solicitado.listar(body.get("cedula"), body.get("nombre_area"), body.get("caracter_nombre_examen"), body.get("fecha_solicitud"), body.get("fecha_inicio"), body.get("fecha_fin"), body.get("estado_solicitud"));
			
	}
	
	@ApiOperation(value="filtra examenes solicitados del paciente ")
	@GetMapping("/examenes-solicitados")
	@ResponseBody
	public List<Examen_solicitado> filtrar_solicitudes_por_cedula_paciente(@RequestParam(required=false, defaultValue="") String cedula, @RequestParam(required=false, defaultValue="") String nombre_area, @RequestParam(required=false, defaultValue="") String caracter_nombre_examen, @RequestParam(required=false, defaultValue="") String fecha_solicitud, @RequestParam(required=false, defaultValue="") String fecha_inicio, @RequestParam(required=false, defaultValue="") String fecha_fin, @RequestParam(required=false, defaultValue="") String estado_solicitud){
		
		return servicioExamen_solicitado.listar(cedula,nombre_area, caracter_nombre_examen, fecha_solicitud, fecha_inicio, fecha_fin, estado_solicitud);
			
	}
	@ApiOperation(value="filtra examenes solicitudes de una solicitud")
	@GetMapping("/filtrar-examenes-de-solicitud")
	@ResponseBody
	public List<Examen_solicitado> filtrar_examenes_de_solicitud(@RequestParam(required=false, defaultValue="") int cod_solicitud){
		
		return servicioExamen_solicitado.listarExamenesSolicitadosDeSolicitud(cod_solicitud);
			
	}
	
	

	
}