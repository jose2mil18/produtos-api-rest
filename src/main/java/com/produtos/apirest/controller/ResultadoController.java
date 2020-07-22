package com.produtos.apirest.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.produtos.apirest.models.Solicitud;
import com.produtos.apirest.models.Examen;
import com.produtos.apirest.models.Examen_solicitado;
import com.produtos.apirest.models.Paciente;
import com.produtos.apirest.models.Persona;

import com.produtos.apirest.models.Resultados_examen;

import com.produtos.apirest.models.Usuario;
import com.produtos.apirest.Services.ServicioResultados_examen;

import com.produtos.apirest.Services.ServicioExamen;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;


import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api")
@Api(value="API REST solicitud")
public class ResultadoController {
	@Autowired
	ServicioResultados_examen servicioResultados_examen;
	@Autowired
	ServicioExamen servicioExamen;
	@Autowired
	Examen_solicitado examen_solicitado;
	@Autowired
	Paciente paciente;
	@Autowired
	Resultados_examen resultados_examen;
	
	
	@ApiOperation(value="guardar resultados de examenes")
	@PostMapping("/resultado")
	public Solicitud guardar_resultadoss(@RequestBody @Valid  Solicitud solicitud){
         
		
           //servicioResultado.actualizarResultadosDeExamenes(solicitud);
		//servicioresultados_examen.guardar_resultados_examen(solicitud);
		 return solicitud;
			
	}

	@ApiOperation(value="guardar resultados de examenes")
	@PostMapping("guardar-resultados")
	public Solicitud registrar_resultadoss(@RequestBody @Valid Examen_solicitado examen_solicitado){
           //servicioResultados_examen.guardar_resultados_examen(solicitud);
		
            
		 return    servicioResultados_examen.registrar(examen_solicitado);
			
			
	}
	@ApiOperation(value="guardar resultados de examenes")
	@PostMapping("modificar-resultados")
	public Solicitud modificar_resultadoss(@RequestBody @Valid Examen_solicitado examen_solicitado){
           //servicioResultados_examen.guardar_resultados_examen(solicitud);
		
            
		 return    servicioResultados_examen.modificar(examen_solicitado);
			
			
	}
	/*
	@ApiOperation(value="guardar resultados de examenes")
	@PostMapping("guardar-resultados-modificados")
	public Solicitud actualizar_resultadoss(@RequestBody @Valid  Solicitud solicitud){
         
		
           //servicioResultado.actualizarResultadosDeExamenes(solicitud);
		 return solicitud;
			
	}
	*/
	/*
	@ApiOperation(value="Retorna examenes de solicitud y quita un examen de solicitud")
	@PostMapping("eliminarexamendesolicitud")
	public List<Examen> eliminar_exmendesolicitud(@RequestBody Map<String, Integer> body){
	servicioResultado.quitarExamen(body.get("cod_solicitud"), body.get("cod_examen"));
	return servicioExamen.listarexamenesdesolicitud(body.get("cod_solicitud"));
	}
	@ApiOperation(value="listar resultados de solicitud")
	@PostMapping("resultados")
	public List<Resultados_examen> listar_resultados(@RequestBody Map<String, Integer> body){
       
			
            
		 //return servicioresultados_examen.obtener_resultados_examen_solicitud(body.get("cod_solicitud"));
			
	}
	
	*/
	
	
}
