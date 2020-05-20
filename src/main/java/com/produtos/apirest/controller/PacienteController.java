package com.produtos.apirest.controller;

import org.springframework.web.bind.annotation.RequestParam;	
import org.springframework.web.bind.annotation.ResponseBody;

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


import com.produtos.apirest.models.Persona;

import com.produtos.apirest.models.Usuario;
import com.produtos.apirest.models.Examen;
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
@Api(value="API REST Pacientes")
public class PacienteController {
	
	@Autowired
	ServicioPaciente servicioPaciente;

	//@Autowired
	//PacienteRepository pacienteRepository;

	

	@ApiOperation(value="Retorna uma lista de pacientes")
	@GetMapping("/pacientes")
	public List<Paciente> lista_pacientes(){
		
	/*	for(Paciente paciente : pacienteRepository.findAll())
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			  Date fecha=paciente.getFnac();
			  String fechaCadena = sdf.format(fecha);
			     paciente.setEdad(calcularEdad(fechaCadena));
		}
		*/
		servicioPaciente.listar();
		return servicioPaciente.listar();
			
	}


	@ApiOperation(value="Retorna uma lista de pacientes buscando por el sexo y procedencia")
	@GetMapping("/paciente")		
	@ResponseBody		
	public List<Paciente> examen(@RequestParam(required=false, defaultValue="") String procedencia, @RequestParam(required=false, defaultValue="") String sexo) {			

		return servicioPaciente.buscar(procedencia, sexo);
		}
	
	@ApiOperation(value="busca pacientes")
	@GetMapping("pacientes/{procedencia}/{sexo}")
	public List<Paciente> obtenerexamendadoelcodigo(@PathVariable(value="procedencia") String procedencia, @PathVariable(value="sexo") String sexo){
			
	/*	for(Paciente paciente : pacienteRepository.findAll())
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			  Date fecha=paciente.getFnac();
			  String fechaCadena = sdf.format(fecha);
			     paciente.setEdad(calcularEdad(fechaCadena));
		}
		*/
	
		return servicioPaciente.buscar(procedencia, sexo);
			
	}
	@ApiOperation(value="registrar un paciente")
	@PostMapping("paciente")
	public Paciente registrar_paciente(@RequestBody @Valid Paciente paciente){
		  System.out.println("fechanacimientooooo"+paciente.getFnac());    
		
		
		 // String fechaCadena = sdf.format(fecha);
		     //paciente.setEdad(calcularEdad(paciente.getFnac()));
		     System.out.println(paciente.getFnac());
		    servicioPaciente.registrar(paciente); ;
             return paciente ;
		 }

	@ApiOperation(value="Atualiza um paciente")
	@PutMapping("/paciente")
	public Paciente atualizaPaciente(@RequestBody  @Valid  Paciente p) {
System.out.println("fecha_naciientoklp"+p.getFnac());
	
		     //p.setEdad(calcularEdad(p.getFnac()));
return servicioPaciente.update(p);
	}
	
	@ApiOperation(value="Retorna um paciente unico ")
	@PostMapping("/buscarpaciente")
	public Paciente buscapaciente(@RequestBody Map<String, String> body){
	System.out.println(body.get("cedula"));
		//return servicioPaciente.buscar_por_cedula(body.get("cedula"))
	return servicioPaciente.buscar_por_cedula(body.get("cedula"));
	}
	@ApiOperation(value="Retorna toodos los pacientes por caracter de nombre/ap/am")
	@PostMapping("/buscar-paciente-por-caracter-de-nombres")
	public List<Paciente> buscapacientesporcaracterdenombre(@RequestBody Map<String, String> body){
	
		//return servicioPaciente.buscar_por_cedula(body.get("cedula"));
	return servicioPaciente.buscar_pacientes_que_hicieron_solicitudes_de_examenes(body.get("id"), body.get("resultados"));
	}
	
	 public Integer calcularEdad(String fecha){
		   Date fechaNac=null;
		       try {
		           fechaNac = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
		       } catch (Exception ex) {
		           System.out.println("Error:"+ex);
		       }
		       Calendar fechaNacimiento = Calendar.getInstance();
		       //Se crea un objeto con la fecha actual
		       Calendar fechaActual = Calendar.getInstance();
		       //Se asigna la fecha recibida a la fecha de nacimiento.
		       fechaNacimiento.setTime(fechaNac);
		       //Se restan la fecha actual y la fecha de nacimiento
		       int año = fechaActual.get(Calendar.YEAR)- fechaNacimiento.get(Calendar.YEAR);
		       int mes =fechaActual.get(Calendar.MONTH)- fechaNacimiento.get(Calendar.MONTH);
		       int dia = fechaActual.get(Calendar.DATE)- fechaNacimiento.get(Calendar.DATE);
		       //Se ajusta el año dependiendo el mes y el día
		       if(mes<0 || (mes==0 && dia<0)){
		           año--;
		       }
		       //Regresa la edad en base a la fecha de nacimiento
		       return año;
		   }
		@ApiOperation(value="conta pacientes")
		@GetMapping("/contar-pacientes")
		public Integer contar_pacientes(){
			
		/*	for(Paciente paciente : pacienteRepository.findAll())
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				  Date fecha=paciente.getFnac();
				  String fechaCadena = sdf.format(fecha);
				     paciente.setEdad(calcularEdad(fechaCadena));
			}
			*/
			return new Integer(servicioPaciente.contarpacientes());
				
		}
		@GetMapping("/proce")
		public String proce(){
			
		/*	for(Paciente paciente : pacienteRepository.findAll())
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				  Date fecha=paciente.getFnac();
				  String fechaCadena = sdf.format(fecha);
				     paciente.setEdad(calcularEdad(fechaCadena));
			}
			*/
			return servicioPaciente.proce();
				
		}
		
			
	}
