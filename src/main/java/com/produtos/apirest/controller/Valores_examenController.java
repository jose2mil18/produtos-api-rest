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

import com.produtos.apirest.models.Paciente;
import com.produtos.apirest.models.Resultados_por_defecto;

import com.produtos.apirest.Services.ServicioResultados_por_defecto;
import com.produtos.apirest.Services.ServicioValor;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api")
@Api(value="API REST Postas")
public class Valores_examenController {
	
	@Autowired
	ServicioResultados_por_defecto servicioValores_examen;
	@Autowired
	ServicioValor servicioValor;

	@ApiOperation(value="Retorna uma lista de Postas")
	@PostMapping("/obtener-valores-examenes")
	public List<Resultados_por_defecto> listaValoresExamen(@RequestBody Map<String, String> body){
	System.out.println(Integer.parseInt(body.get("cod_examen"))+" "+ body.get("caracter"));
		return servicioValores_examen.listar(Integer.parseInt(body.get("cod_examen")), body.get("caracter"));
	}
	@PostMapping("/obtener-valor")
	public String valor(@RequestBody Map<String, Integer> body){
	//System.out.println(Integer.parseInt(body.get("cod_examen"))+" "+ body.get("caracter"));
		return servicioValor.valor(body.get("cod_resultados_examen"), body.get("cod"));
	}
}
