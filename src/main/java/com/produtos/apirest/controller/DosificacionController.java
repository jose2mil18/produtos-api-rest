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
import com.produtos.apirest.models.Dosificacion;
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
@Api(value="API REST Dosificaciones")
public class DosificacionController {
	@Autowired
	ServicioDosificacion servicioDosificacion;

	//@Autowired
	//PacienteRepository pacienteRepository;

	

	@ApiOperation(value="Retorna uma lista de dosificaciones")
	@GetMapping("/dosificaciones")
	public List<Dosificacion> lista_dosificaciones(){
		
		return servicioDosificacion.listar();
			
	}
	@ApiOperation(value="registar dosificacion")
	@PostMapping("dosificacion")
	public void dosificaionregistrar(@RequestBody @Valid Dosificacion dosificacion) {
		servicioDosificacion.registrar(dosificacion);
		
	}


}
