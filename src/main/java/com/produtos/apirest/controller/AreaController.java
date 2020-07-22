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
import com.produtos.apirest.Services.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api")
@Api(value="API RESTarea")
public class AreaController {
	
	@Autowired
	ServicioArea servicioArea;
	@PostMapping("area")
	public Area todossugexamenes(@RequestBody Map<String, String> body){
		
	return servicioArea.buscarPorCodigo(Integer.parseInt(body.get("cod_area")));
	}
	@ApiOperation(value="Retorna uma lista de areas")
	@GetMapping("areas")//
	public List<Area> listaAreas(){
		System.out.println("ana");
		/*
try {jk
	ana();ffdf
} catch (FileNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (JRException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
*/
		return servicioArea.listar();
	}
	public void ana() throws JRException, FileNotFoundException {


		String fileName = "src/main/resources/JasperDesign.jrxml";
	 String outFile = "src/main/resources/Reports.pdf";
		List<Student> studentList = new ArrayList<Student>();
		List<Teacher> teacherList = new ArrayList<Teacher>();

		Map<String, Object> parameter = new HashMap<String, Object>();

		studentList.add(new Student(1, "Student 1", "FY BSc"));
		studentList.add(new Student(2, "Student 2", "TY BSc"));
		studentList.add(new Student(3, "Student 3", "TY BBA"));

		teacherList.add(new Teacher(1, "Teacher 1", 50000.2));
		teacherList.add(new Teacher(2, "Teacher 2", 45000.0));
		teacherList.add(new Teacher(3, "Teacher 3", 65000.0));

		JRBeanCollectionDataSource studentCollectionDataSource = new JRBeanCollectionDataSource(studentList);
		JRBeanCollectionDataSource teacherCollectionDataSource = new JRBeanCollectionDataSource(teacherList);
		
		parameter.put("studentDataSource", studentCollectionDataSource);
		parameter.put("teacherDataSource", teacherCollectionDataSource);
		parameter.put("title", new String("Hi, I am Title"));

		JasperReport jasperDesign = JasperCompileManager.compileReport(fileName);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperDesign, parameter, new JREmptyDataSource());
		 JasperViewer ver=new JasperViewer(jasperPrint, false);
	        ver.setVisible(true);
	        ver.setTitle("klp");
		File file = new File(outFile);
		OutputStream outputSteam = new FileOutputStream(file);
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputSteam);

		System.out.println("Report Generated!");
	}
}
	 