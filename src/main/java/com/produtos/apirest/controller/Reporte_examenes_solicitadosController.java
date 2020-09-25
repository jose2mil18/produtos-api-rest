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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.produtos.apirest.models.Reporte_examen_mensual;
import com.produtos.apirest.models.Reporte_examenes_solicitados;
import com.produtos.apirest.models.Persona;
import com.produtos.apirest.models.Precio_examen;
import com.produtos.apirest.models.Usuario;
import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Paciente;

import com.produtos.apirest.models.Solicitud;
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
@Api(value="API REST reporte de examenes solicitados")
public class Reporte_examenes_solicitadosController {
	@Autowired
	ServicioReporte_examenes_solicitados servicioReporte_examenes_solicitados;

	@Autowired
	ServicioSolicitud servicioSolicitud;
	@ApiOperation(value="Retorna uma lista de areas")
	@GetMapping("reporte-examenes-solicitados")//
	@ResponseBody
	public List<Reporte_examenes_solicitados> listaReportess(@RequestParam(required=false, defaultValue="") String cedula, @RequestParam(required=false, defaultValue="") String nombre_area, @RequestParam(required=false, defaultValue="") String caracter_nombre_examen, @RequestParam(required=false, defaultValue="") String fecha_solicitud, @RequestParam(required=false, defaultValue="") String fecha_inicio, @RequestParam(required=false, defaultValue="") String fecha_fin, @RequestParam(required=false, defaultValue="") String estado_solicitud,@RequestParam(required=false, defaultValue="") String agrupador,@RequestParam(required=false, defaultValue="") String seleccionador){
		List<Reporte_examenes_solicitados> re=new ArrayList<Reporte_examenes_solicitados>();
		 re=servicioReporte_examenes_solicitados.buscar(nombre_area, fecha_inicio, fecha_fin, agrupador, seleccionador, fecha_solicitud);
		 int i=0;
/*
			if(!fecha_inicio.equals("") && !fecha_fin.equals("")) {
		for(Reporte_examenes_solicitados r: re)
		{
			List<Solicitud> soli=new ArrayList<Solicitud>();
		soli=servicioSolicitud.listar_solicitudes_de_un_mismo_examen_entre_Periodo(r.getPrecio_examen().getCod_precio_examen(), fecha_inicio, fecha_fin);
		re.get(i).setSolicitudes(soli);
		i++;
		}
			}
			*/
		return re;
		
		
	}
}
