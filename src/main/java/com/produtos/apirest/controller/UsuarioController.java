package com.produtos.apirest.controller;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.produtos.apirest.models.CSVData;
import com.produtos.apirest.models.Examen;
import com.produtos.apirest.models.Persona;

import com.produtos.apirest.models.Rol;
import com.produtos.apirest.Services.*;
import com.produtos.apirest.models.Usuario;
import com.produtos.apirest.Services.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;


import javax.servlet.http.HttpServletRequest;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api")
@Api(value="API REST Produtos")
public class UsuarioController {
	

	private static final String EXTERNAL_FILE_PATH = "C:/fileDownloadExample/";


	


    private String upload_folder = ".//src//main//resources//imagenes//";
	@Autowired
	ServicioUsuario servicioUsuario;
	@Autowired
	ServicioRol servicioRol;
	
	
	
	@RequestMapping(value="/download", method=RequestMethod.GET) 
	public ResponseEntity<Object> downloadFile() throws IOException  {
		FileWriter filewriter =  null;
		try {
		CSVData csv1 = new CSVData();
		csv1.setId("1");
		csv1.setName("talk2amareswaran");
		csv1.setNumber("5601");
		
		CSVData csv2 = new CSVData();
		csv2.setId("2");
		csv2.setName("Amareswaran");
		csv2.setNumber("8710");
		
		List<CSVData> csvDataList = new ArrayList<>();
		csvDataList.add(csv1);
		csvDataList.add(csv2);
		
		StringBuilder filecontent = new StringBuilder("ID, NAME, NUMBER\n");
		for(CSVData csv:csvDataList) {
			filecontent.append(csv.getId()).append(",").append(csv.getName()).append(",").append(csv.getNumber()).append("\n");
		}
		
		String filename = ".//src//main//resources//imagenes//Aula-Virtual-02.jpg";
		
		
		File file = new File(filename);
		
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		
		ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/txt")).body(resource);
		return responseEntity;
		} catch (Exception e ) {
			return new ResponseEntity<>("error occurred", HttpStatus.INTERNAL_SERVER_ERROR);	
		} finally {
			if(filewriter!=null)
				filewriter.close();
		}
	}
	@RequestMapping("/file/{fileName}")
	public void downloadPDFResource(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("fileName") String fileName) throws IOException {
		
	    Path path = Paths.get(upload_folder +fileName);
		File file = new File(path.toString());
		System.out.println(path.toString());
		if (file.exists()) {

			//get the mimetype
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				//unknown mimetype so set the mimetype to application/octet-stream
				mimeType = "application/octet-stream";
			}

			response.setContentType(mimeType);

			/**
			 * In a regular HTTP response, the Content-Disposition response header is a
			 * header indicating if the content is expected to be displayed inline in the
			 * browser, that is, as a Web page or as part of a Web page, or as an
			 * attachment, that is downloaded and saved locally.
			 * 
			 */

			/**
			 * Here we have mentioned it to show inline
			 */
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

			 //Here we have mentioned it to show as attachment
			 //response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));

			response.setContentLength((int) file.length());

			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

			FileCopyUtils.copy(inputStream, response.getOutputStream());

		}
	}
	
	
	
	
	@ApiOperation(value="Retorna uma lista de Produtos")
	@PostMapping("/validarusuarios")
	public Usuario validarusuario(HttpServletRequest request, @RequestBody Map<String, String> body){
		System.out.println(body.get("login")+"bfad"+body.get("password"));
		Usuario usuario= servicioUsuario.validar_usuario(body.get("login"), body.get("password"));

		
			return servicioUsuario.validar_usuario(body.get("login"), body.get("password"));
		 
			
	}
	@ApiOperation(value="Retorna uma lista de usuario")
	@GetMapping("/usuarios")
	public List<Usuario> lista_usuarios(){
		System.out.println("lpmklp");
		return servicioUsuario.listar();
			
	}
	@ApiOperation(value="Retorna uma listade roles")
	@GetMapping("/roles")
	public List<Rol> lista_roles(){
		return servicioRol.roles();
			
	}
	@PostMapping("/actualizar-password")
	public String obtenerexamendadoelcodigo(@RequestBody Map<String, String> body){
		System.out.println(body.get("cedula"));
		//return servicioExamen.obtener_examen(body.get("cod_examen"));
		servicioUsuario.cambiar_contraseña(body.get("cedula"), body.get("password"));
		return body.get("cedula");
	
	
}
	@PostMapping("/verificar-cedula")
	public Usuario veriajdlkfjlad(@RequestBody Map<String, String> body){
		System.out.println(body.get("cedula"));
		//return servicioExamen.obtener_examen(body.get("cod_examen"));
		return servicioUsuario.verificarCedulaquenoexista(body.get("cedula"));
		
	
	
}
	@ApiOperation(value="cambiar la contraseña del usuario")
	@PutMapping("/cambiar-contraseña")
	public String cambiar_contraseña(@RequestBody Map<String, String> body){
		System.out.println(body.get("cedula"));
			return new String(body.get("cedula"));
	}
	@ApiOperation(value="Retorna los numeros dde doctores")
	@GetMapping("/num-doctores")
	public Integer numero_doctores(){
		return new Integer(servicioUsuario.num_doctores());
			
	}
	/*
	@ApiOperation(value="Retorna uma lista de Produtos")
	@GetMapping("/personales/{id}")
	public Persona listaPersonal(@PathVariable(value="id") String id){
		return servicioPersonal.obtener_por_Cedula(id);
	}

	*/
	@ApiOperation(value="registrar un  usuario")
	@PostMapping("usuario")
	public Usuario registrar_usuario(@RequestBody @Valid Usuario usuario){
             servicioUsuario.registrar(usuario);
             
             
			
             return usuario;
		 
			
	}
	@ApiOperation(value="actualizar un  usuario")
	@PutMapping("actualizar-usuario")
	public Usuario actualizar_usuario(@RequestBody @Valid Usuario usuario){
		//String foto=usuario.getPersonal_laboratorio().getFoto();
//System.out.println("kjkj"+parametro);
           servicioUsuario.actualizar(usuario);
			
            return usuario;
		 
			
	}	
	
	@GetMapping("/get-text")
	public @ResponseBody String getText() {
	    return "Hello world";
	}
	@ApiOperation(value="actualizar un  usuario")
	@PostMapping("upload-imagen")
	public void subirimagen( @RequestParam("nombreImagen") String nombreImagen, @RequestParam("imagen")  MultipartFile imagen){
		//String foto=usuario.getPersonal_laboratorio().getFoto();
System.out.println("kjkj"+nombreImagen);
System.out.println(imagen.getOriginalFilename());

try {
    servicioUsuario.guardarImagen(imagen);
} catch (IOException e) {
    e.printStackTrace();
}
          // servicioUsuario.actualizar(usuario);
			
            // return usuario;
		 
			
	}
	
}
