package com.produtos.apirest.controller;

import java.util.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.produtos.apirest.models.Examen_solicitado;
import com.produtos.apirest.models.Solicitud;

import com.produtos.apirest.Services.ServicioResultados_examen;


import com.produtos.apirest.Services.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api")
@Api(value="API REST solicitud")
public class SolicitudController {
	@Autowired
	ServicioSolicitud servicioSolicitud;
	@Autowired
	ServicioResultados_examen servicioresultados_examen;
	@Autowired
	ServicioExamen servicioExamen;
	
	@Autowired
	ServicioExamen_solicitado servicioExamen_solicitado;
	
	@ApiOperation(value="Retorna uma lista de solicitudes de examenes clinicos de un paciente por tal area")
	@PostMapping("/filtrar-solicitud-por-cedula-paciente-y-area")
	public List<Solicitud> filtrar_solicitudes_cedula_paciente_y_area(@RequestBody Map<String, String> body){

		//a=servicioSolicitud.buscarSolicitudPorCedulaPaciente(body.get("cedula"));
				
		return servicioSolicitud.listar();
			
	}
	
	
	@ApiOperation(value="Retorna uma lista de solicitudes de examenes clinicos")
	@GetMapping("/solicitudes")
	@ResponseBody
	public List<Solicitud> lista_solicitud(@RequestParam(required=false, defaultValue="") String cedula, @RequestParam(required=false, defaultValue="") String nombre_area, @RequestParam(required=false, defaultValue="") String caracter_nombre_examen, @RequestParam(required=false, defaultValue="") String fecha_solicitud, @RequestParam(required=false, defaultValue="") String fecha_inicio, @RequestParam(required=false, defaultValue="") String fecha_fin, @RequestParam(required=false, defaultValue="") String estado_solicitud, @RequestParam(required=false, defaultValue="") String resultados){
		System.out.println(cedula+" "+fecha_solicitud+" "+fecha_inicio+" "+fecha_fin+" "+estado_solicitud+" "+resultados+"kljkjljkjkjkjkljkljkjkjkjkjkjkjkljkjkjkjkjkjkjkjkjlkjkjkljkjkjkjkjkjkjkjkjkjkjkjkjkjkjkjkjkjkñj-----------------------------------------------------------------------------------------------------------------------------");
		
		return servicioSolicitud.buscar(cedula,nombre_area, caracter_nombre_examen, fecha_solicitud, fecha_inicio, fecha_fin, estado_solicitud, resultados);
			
	}
	
	/*
	@ApiOperation(value="Retorna uma lista de solicitudes de examenes clinicos")
	@GetMapping("/examenes-solicitados")
	public List<Historial_clinico> lista_examenes_solicitados(){
		return servicioHistorial_clinico.examenes_solicitados();
			
	}
	*/
	@ApiOperation(value="Retorna uma solicitud unica")
	@GetMapping("/solicitud/{id}")
	public Solicitud listasolicitudUnica(@PathVariable(value="id") int id){
	
		return servicioSolicitud.buscarPorCodigo(id);
	}

	@GetMapping("/solicitud")
	@ResponseBody
	public Solicitud listasolicitudUnicas(@RequestParam(required=false, defaultValue="") int cod_solicitud){
		
		return servicioSolicitud.buscarPorCodigo(cod_solicitud);
	}
	

	
	@GetMapping("/solicitud-con-resultados-actualizados/{id}")
	public Solicitud listasolicitudconresultadosactualizados(@PathVariable(value="id") int id){
	
		return servicioSolicitud.listarSolicitudConResultadosModificados(id);
	}
	
	@ApiOperation(value="Retorna uma lista de solicitudes de examenes clinicos que no tiene resultados---------------------")
	@GetMapping("/solicitudesSinResultados")
	public List<Solicitud> lista_solicitud_sin_resultado(@RequestParam(required=false, defaultValue="") String cedula, @RequestParam(required=false, defaultValue="") String nombre_area, @RequestParam(required=false, defaultValue="") String caracter_nombre_examen, @RequestParam(required=false, defaultValue="") String fecha_solicitud, @RequestParam(required=false, defaultValue="") String fecha_inicio, @RequestParam(required=false, defaultValue="") String fecha_fin, @RequestParam(required=false, defaultValue="") String estado_solicitud, @RequestParam(required=false, defaultValue="") String resultados){
		
		return servicioSolicitud.listarAnalisisSinResultados(cedula,nombre_area, caracter_nombre_examen, fecha_solicitud, fecha_inicio, fecha_fin, estado_solicitud);
			
	}
	
	@ApiOperation(value="Retorna uma lista de solicitudes de examenes clinicos con  resultados")
	@GetMapping("/solicitudesConResultados")
	public List<Solicitud> lista_solicitud_con_resultado(){
		return servicioSolicitud.listarAnalisisConResultados();
			
	}

	@ApiOperation(value="Retorna uma solicitud unicacon resultados")
	@GetMapping("/solicitudConResultados/{id}") 
	public Solicitud listasolicitudUnicaconresultados(@PathVariable(value="id") int id){
	
		return servicioSolicitud.listarSolicitudporcodigoConResultados(id);
	}
	
	@ApiOperation(value="filtra solicitudes de examenes clinicos por fecha Y nombres de pacientes o solo nombre de pacientes")
	@PostMapping("/filtrar-solicitud")
	public List<Solicitud> filtrar_solicitudes(@RequestBody Map<String, String> body){
	
		return servicioSolicitud.filtrarSolicitudesPorPaciente(body.get("id"));
			
	}
	/*
	@ApiOperation(value="filtra solicitudes de examenes clinicos por cedula del paciente")
	@PostMapping("/filtrar-solicitud-por-cedula-paciente")
	public List<Solicitud> filtrar_solicitudes_cedula_paciente(@RequestBody Map<String, String> body){
		
		return servicioSolicitud.buscarSolicitudPorCedulaPaciente(body.get("cedula"));
			
	}
	*/
	@ApiOperation(value="filtra solicitudes de examenes clinicos por fecha ")
	@PostMapping("/filtrar-por-fecha-de-solicitud")
	public List<Solicitud> filtrar_solicitudes_por_fecha(@RequestBody Map<String, String> body){
		System.out.println(body.get("fecha"));
		return servicioSolicitud.filtrarSolicitudesPorFecha(body.get("fecha"));
			
	}
	

	
	@ApiOperation(value="filtra solicitudes de examenes clinicos por rango de fecha ")
	@PostMapping("/filtrar-por-rango-fecha")
	public List<Solicitud> filtrar_solicitudes_por_rango_fecha(@RequestBody Map<String, String> body){
		System.out.println(body.get("fecha"));
		return servicioSolicitud.filtrarSolicitudesPorRangoFecha(body.get("fe_in"), body.get("fe_fin"));
			
	}
	@ApiOperation(value="contar el numerode solicitudes")
	@GetMapping("/contar-solicitudes")
	public Integer contar_solicitudes(){
		
		return new Integer(servicioSolicitud.num_solicitudes());
			
	}
	@GetMapping("/minima-fecha-solicitud")
	public String minimafechadesolicitudess(){
		
		return new String(servicioSolicitud.minimafechadesolicitud());
			
	}
	
	@PostMapping("/pruebita")
	public String minimafechadesolicitus(@RequestBody  @Valid Solicitud solicitud){
		System.out.println("fechaklp"+solicitud.getFecha()+" "+solicitud.getFecha_entrega());
		return "hola";
			
	}
	
	
	@PostMapping("/actualizar-estado")
	public List<Solicitud> minimafechadesolicitusfasdfasd(@RequestBody  @Valid Solicitud solicitud){
		System.out.println("fechaklp"+solicitud.getFecha()+" "+solicitud.getFecha_entrega());
		return servicioSolicitud.actualizarEstadoSolicitud(solicitud);
			
	}
	@ApiOperation(value="contar el numerode solicitudes pendientes")
	@GetMapping("/contar-solicitudes-pendientes")
	public Integer contar_solicitudes_pendientes(){
		
		return new Integer(servicioSolicitud.num_solicitudes_pendientes());
			
	}
	/*kljk
	@ApiOperation(value="")
	@PostMapping("/solicitud-con-resultados-examenes")
	public List<Solicitud> solicitudconresultadosexamenes(@RequestBody Solicitud solicitud){
		
		//return servicioSolicitud.solicitud_con_resultados_examenes(solicitud.getCod_solicitud());
			return new List<Solicitud>;
	}*/
	@ApiOperation(value="")
	@PostMapping("/generar-factura")
	public Solicitud solicitudconresultadosexamenes(@RequestBody Solicitud solicitud){
		
		//return servicioSolicitud.solicitud_con_resultados_examenes(solicitud.getCod_solicitud());
			return servicioSolicitud.generarFactura(solicitud);
	}
	@ApiOperation(value="Retorna una solicitud dado el codigo de solicitud")
	@GetMapping("/solicitudSinResultado/{cod_solicitud}")
	public Solicitud lista_solicitud_sin_resultado_por_codigo(@PathVariable(value="cod_solicitud") int cod_solicitud){
		System.out.println(cod_solicitud);
		return servicioSolicitud.listarAnalisisSinResultadosporcodigo(cod_solicitud);
			
	}
	
	
	@ApiOperation(value="registrar una solicitud")
	@PostMapping("/solicitud")
	public Solicitud registrar_solicitud(@RequestBody Solicitud solicitud){
	
		System.out.println("--------------------------------------------------------------"+solicitud.getDoctor_solicitante());
servicioSolicitud.registrar(solicitud);

		for(Examen_solicitado examen : solicitud.getExamenes_solicitados()){
			System.out.println("--------------------------estado"+examen.getEstado());
			if(examen.getEstado().equals("Sin Registrar"))
			{
		 System.out.println(examen.getEstado().equals("Sin Registrar"));
			}
		
		}
           	return solicitud;
		 
			
	}

	@ApiOperation(value="modificar una solicitud")
	@PutMapping("/solicitud")
	public Solicitud modificarr_solicitud(@RequestBody  @Valid Solicitud solicitud){
System.out.println("fecha_wntre"+solicitud.getFecha_entrega());

return servicioSolicitud.modificar(solicitud);

      
			
	}
	
	
	
	

	
}