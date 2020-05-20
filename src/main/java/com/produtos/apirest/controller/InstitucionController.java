package com.produtos.apirest.controller;

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

import com.produtos.apirest.models.*;
import com.produtos.apirest.Services.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api")
@Api(value="API RESTarea")
public class InstitucionController {
	
	@Autowired
	ServicioInstitucion servicioInstitucion;
	
	@ApiOperation(value="Retorna uma lista de areas")
	@GetMapping("postas")
	public List<Institucion> listapostas(){
		return  servicioInstitucion.listapostas();
	}
	
	@ApiOperation(value="Retorna uma fad")
	@GetMapping("institucionesFaltantes")
	public List<Institucion> institucionesfaltantes(){
		return  servicioInstitucion.institucionesaFaltantes();
	}
	@ApiOperation(value="Retorna uma ffadf")
	@GetMapping("instituciones-examenes")
	public List<Institucion> institucionesconprecio(){
		return  servicioInstitucion.institucionesconprecio();
	}
	
	@ApiOperation(value="Retorna una institucion dado el codigo")
	@PostMapping("institucion")
	public Institucion obtenerexamendadoelcodigo(@RequestBody Map<String, String> body){
	
		System.out.println(body.get("cod_institucion"));
		//return servicioExamen.obtener_examen(body.get("cod_examen"));
		return servicioInstitucion.Institucion(body.get("cod_institucion"));
	
	
}

}
	