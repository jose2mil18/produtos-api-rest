package com.produtos.apirest.controller;
import org.springframework.web.bind.annotation.RequestParam;	
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.List;



import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

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

import com.produtos.apirest.models.Examen;
import com.produtos.apirest.models.Paciente;
import com.produtos.apirest.models.Precio_examen;
import com.produtos.apirest.models.Solicitud;
import com.produtos.apirest.Services.ServicioExamen;

import com.produtos.apirest.Services.ServicioResultados_examen;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api")
@Api(value="API RESTexamen")
public class ExamenController {
	
	@Autowired
	ServicioExamen servicioExamen;
	
	@Autowired
	Precio_examen precio_examen;
	
	@ApiOperation(value="Retorna uma lista de areas")
	@PostMapping("examenesporarea")
	public List<Examen> listaAreas(@RequestBody @Valid Precio_examen pe){

		return servicioExamen.listarporareacodinstitucion(pe);
	}

	@ApiOperation(value="Retorna uma lista de areas")
	@PostMapping("actualizavalorreferencia")
	public void actualizavalorrrreferecns(@RequestBody Map<String, String> body){
		
		servicioExamen.actualizavalorreferencia(body.get("valor_referencia"),body.get("cod_examen"));
	}
	
	@ApiOperation(value="Retorna uma lista de areas")
	@PostMapping("sub-examenes-por-caracter")
	public List<Examen> todossugexamenes(@RequestBody Map<String, String> body){
		
		return servicioExamen.listartodosSubExamenes(body.get("caracter"), Integer.parseInt(body.get("cod_area")));
	}
	@ApiOperation(value="Retorna uma lista de areas")
	@PostMapping("sub-examenes-sin-subexamenes-por-caracter")
	public List<Examen> todossugexamenessinsubexamenes(@RequestBody Map<String, String> body){
		
		return servicioExamen.listartodosSubExamenesSinSubexamenes(body.get("caracter"), Integer.parseInt(body.get("cod_area")));
	}
	@ApiOperation(value="Retorna uma lista de examenes")
	@GetMapping("/examenes")
	@ResponseBody
	public List<Examen> lista_pacientes(@RequestParam(required=false, defaultValue="") String nombre_area, @RequestParam(required=false, defaultValue="") String caracter_nombre_examen){
		
	/*	for(Paciente paciente : pacienteRepository.findAll())
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			  Date fecha=paciente.getFnac();
			  String fechaCadena = sdf.format(fecha);
			     paciente.setEdad(calcularEdad(fechaCadena));
		}
		*/
		return servicioExamen.listar(nombre_area, caracter_nombre_examen);
			
	}
	
	
	@ApiOperation(value="Retorna un examen dado el codigo")
	@GetMapping("examen/{id}")
	public Examen obtenerexamendadoelcodigo(@PathVariable(value="id") int id){
	
		//return servicioExamen.obtener_examen(body.get("cod_examen"));
		return servicioExamen.obtener_examen(id);
	
	
}

	
	
	@GetMapping("/welcome")		
	@ResponseBody		
	public String welcome(@RequestParam(name="name", required=false, defaultValue="") String name, @RequestParam(required=false, defaultValue="0") int age) {			
name="Name:"+name+"\n"+"age"+age;
		return name;		
		}
	
	@GetMapping("/exameness")		
	@ResponseBody		
	public int exam_eneh_jhjkde_area(@RequestParam(required=false, defaultValue="1") int cod_area) {				
		System.out.println(cod_area);
		return cod_area;
		}
	

	@GetMapping("/examen")		
	@ResponseBody		
	public Examen examen(@RequestParam(required=false, defaultValue="1") int id) {			

		return servicioExamen.obtener_examen(id);	
		}
	/*
	@GetMapping("/examenes-de-area")		
	@ResponseBody		
	public List<Examen> examendearea(@RequestParam(required=false, defaultValue="0") int cod_area) {			
System.out.println(cod_area);
		return servicioExamen.listarexamenesdearea(cod_area);	
		}
	*/
	@ApiOperation(value="registrar un EXAMENfad")
	@PostMapping("examen")
	public Examen registrar_examen(@RequestBody @Valid Examen e){
		 
		System.out.println("cod_area"+e.getCod_area());
	
		  return  servicioExamen.registrar(e);
             
		 }
	
	@ApiOperation(value="modificar un EXAMENfad")
	@PutMapping("examen")
	public Examen modificar_examen(@RequestBody @Valid Examen e){
		 
		System.out.println("cod_area"+e.getCod_area());
	
		  return  servicioExamen.modificar(e);
             
		 }

}