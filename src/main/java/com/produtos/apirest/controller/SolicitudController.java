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

import com.produtos.apirest.Services.ServicioResultados_examen;
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

		List<Solicitud> a=new ArrayList<Solicitud>();
		//a=servicioSolicitud.buscarSolicitudPorCedulaPaciente(body.get("cedula"));
		
		
		return servicioSolicitud.listar();
			
	}
	@GetMapping("/lista-solicitudes")
	@ResponseBody
	public List<Solicitud> slfadfkasndf(@RequestParam(required=false, defaultValue="") String fecha_inicio){
System.out.println(java.sql.Date.valueOf(fecha_inicio));
		List<Solicitud> a=new ArrayList<Solicitud>();
		//a=servicioSolicitud.buscarSolicitudPorCedulaPaciente(body.get("cedula"));
		
		
		return servicioSolicitud.listar();
			
	}
	
	@ApiOperation(value="Retorna uma lista de solicitudes de examenes clinicos")
	@GetMapping("/solicitudes")
	@ResponseBody
	public List<Solicitud> filtrar_solicitudes(@RequestParam(required=false, defaultValue="") String cedula, @RequestParam(required=false, defaultValue="") String nombre_area, @RequestParam(required=false, defaultValue="") String caracter_nombre_examen, @RequestParam(required=false, defaultValue="") String fecha_solicitud, @RequestParam(required=false, defaultValue="") String fecha_inicio, @RequestParam(required=false, defaultValue="") String fecha_fin, @RequestParam(required=false, defaultValue="") String estado_solicitud, @RequestParam(required=false, defaultValue="") String resultados){
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
	
	@GetMapping("/prueba")
	public String listasolicitud(){
		System.out.println("imagen subida");
		return "";
	}
	/*--------------------------------------------------------------------------------------------------------------------------------------
	@GetMapping("/solicitudes-de-examen")
	@ResponseBody
	public List<Solicitud> listasolicitudesDeUnExamen(@RequestParam(required=false, defaultValue="") int cod_precio_examen){
		
		return servicioSolicitud.listar_solicitudes_de_un_mismo_examen(cod_precio_examen);
	}
	*/
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
		System.out.println("laputamadre");
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
		/*
		System.out.println(solicitud.getFecha()+" "+solicitud.getFecha_entrega());
DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date fecha=java.sql.Date.valueOf(solicitud.getFecha().toString());
		
		String fech=dateFormat.format(sumarRestarDiasFecha(fecha, 1));
		System.out.println(fech);
java.sql.Date fecha_entrega=java.sql.Date.valueOf(solicitud.getFecha_entrega().toString());

String fecha_entreg=dateFormat.format(sumarRestarDiasFecha(fecha_entrega, 1));
java.sql.Date a=java.sql.Date.valueOf(fech);
java.sql.Date b=java.sql.Date.valueOf(fecha_entreg);
solicitud.setFecha(fech);
java.sql.Date.valueOf(fech);
solicitud.setFecha_entrega(fecha_entreg);
System.out.println(a+" "+b);
solicitud.setEstado("Sin Registrar");
*/
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
	@ApiOperation(value="actualizar la columna resultados de la tabla solicitud")
	@PostMapping("actualizarEstadoResultadoSolicitud")
	public Solicitud actualizar_estado_resultados_solicitud(@RequestBody Map<String, String> body){
		System.out.println("sss");
          servicioSolicitud.actualizarEstadoSolicitud(Integer.parseInt(body.get("cod_solicitud")));
          System.out.println("sss");
            
		 return servicioSolicitud.listarAnalisisSinResultadosporcodigoregistrado(Integer.parseInt(body.get("cod_solicitud")));
			
	}
	@ApiOperation(value="modificar una solicitud")
	@PutMapping("/solicitud")
	public Solicitud modificarr_solicitud(@RequestBody  @Valid Solicitud solicitud){
System.out.println("fecha_wntre"+solicitud.getFecha_entrega());

return servicioSolicitud.modificar(solicitud);

	/*	
if(solicitud.getInstitucion().equals("LABORATORIO UNIVERSIDAD") || solicitud.getInstitucion().equals("PARTICULAR")){
	servicioSolicitud.modificar(solicitud.getExamenes(), solicitud.getInstitucion(), solicitud.getDoctor(), fech,fecha_entreg,solicitud.getPaciente().getCedula(), solicitud.getCedula_personal(), solicitud.getCod_solicitud());

	servicioSolicitud.eliminarRelacionSolicitudPosta(solicitud.getCod_solicitud(),solicitud.getInstitucion());
}
else
{
	servicioSolicitud.modificar(solicitud.getExamenes(), "SIS", solicitud.getDoctor(), fech, fecha_entreg,solicitud.getPaciente().getCedula(), solicitud.getCedula_personal(), solicitud.getCod_solicitud());

	servicioSolicitud.modificarRelacionSolicitudPosta(solicitud.getCod_solicitud(),solicitud.getInstitucion());
	
}
*/
	 
          
			
	}
	
	
	public static Date sumarRestarDiasFecha(Date fecha, int dias){

		  Calendar calendar = Calendar.getInstance();

		  calendar.setTime(fecha); // Configuramos la fecha que se recibe
		  calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0

		  return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos

		}
	@ApiOperation(value="Retorna examenes de solicitud y quita un examen de solicitud")
	@PostMapping("eliminar-examen-de-solicitud")
	public List<Examen_solicitado> eliminar_exmendesolicitud(@RequestBody Map<String, Integer> body){
	servicioExamen_solicitado.quitarExamen(body.get("cod_solicitud"), body.get("cod_examen"));
	return servicioExamen_solicitado.listarExamenesSolicitadosDeSolicitud(body.get("cod_solicitud"));
	}

	
}
