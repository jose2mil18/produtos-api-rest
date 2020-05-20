package com.produtos.apirest.controller;

import java.util.ArrayList;
import java.util.List;



import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
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
import com.produtos.apirest.Services.ServicioPrecio_examen;
import com.produtos.apirest.Services.ServicioResultados_examen;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api")
@Api(value="API RESTexamen")
public class Precio_examenController {
	
	@Autowired
	ServicioPrecio_examen servicioPrecio_examen;
	
	@Autowired
	Precio_examen precio_examen;
	@Autowired
	ServicioResultados_examen servicioresultados_examen;
	@ApiOperation(value="Retorna uma lista de examenes por areas y cod institucion")
	@PostMapping("examenes-por-area-institucion")
	public List<Precio_examen> listaExmaenesPorAreainstitucion(@RequestBody @Valid Precio_examen pe){
		System.out.println(pe.getCod_institucion()+" "+pe.getExamen().getArea().getCod_area());

		return servicioPrecio_examen.listarporareacodinstitucion(pe);
	}

	

}