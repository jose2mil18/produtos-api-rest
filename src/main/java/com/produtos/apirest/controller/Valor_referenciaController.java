package com.produtos.apirest.controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.co.dreamsdoor.beans.Student;
import in.co.dreamsdoor.beans.Teacher;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import java.util.*;
import java.util.List;

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

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Examen;
import com.produtos.apirest.models.Valor_referencia;
import com.produtos.apirest.Services.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api")
@Api(value="API RESTavalorreferenciacontroller")
public class Valor_referenciaController {
	@Autowired
	ServicioValor_referencia servicioValor_referencia;
	
	@ApiOperation(value="Retorna uma lista de areas")
	@PostMapping("cambiar-estado-de-valor-referencia")//
	public List<Valor_referencia> estadodefadklfa(@RequestBody Map<String, Integer> body){
		return servicioValor_referencia.cambiarEstadoValorReferencia(body.get("cod_examen"), body.get("cod_valor_referencia"));
	}
	
}
