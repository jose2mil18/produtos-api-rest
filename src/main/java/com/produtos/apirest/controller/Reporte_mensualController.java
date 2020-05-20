package com.produtos.apirest.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.produtos.apirest.models.Reporte_mensual;
import com.produtos.apirest.models.Persona;
import com.produtos.apirest.models.Precio_examen;
import com.produtos.apirest.models.Usuario;
import com.produtos.apirest.models.Paciente;
//import com.produtos.apirest.repository.PacienteRepository;
import com.produtos.apirest.Services.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;


import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api")
@Api(value="API REST reporteanual")
public class Reporte_mensualController {
	@Autowired
	ServicioReporte_mensual servicio_reporte_mensual;
	
	@Autowired
	Reporte_mensual reporte_anual;
	@Autowired
	ServicioResultados_examen servicioresultados_examen;
	@ApiOperation(value="Retorna uma lista de examenes por areas y cod institucion")
	@PostMapping("reportemes")
	public Reporte_mensual reporte_anio_mes(@RequestBody @Valid Reporte_mensual re){
		
System.out.println("HO");
		return servicio_reporte_mensual.reportes(re);
	}
	@ApiOperation(value="Retorna uma lista de examenes por areas y cod institucion")
	@PostMapping("reporte-todos-meses")
	public List<Reporte_mensual> reporte_todos_meses(@RequestBody Map<String, String> body){
		
System.out.println("HO");
		return servicio_reporte_mensual.reporte_todos_meses(body.get("cod_institucion"), Integer.parseInt(body.get("anio")));
	}
}
