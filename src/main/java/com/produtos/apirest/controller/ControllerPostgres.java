package com.produtos.apirest.controller;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.util.FileCopyUtils;
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
import com.produtos.apirest.models.Paciente;
import com.produtos.apirest.Services.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api")
@Api(value="API RESTarea")
public class ControllerPostgres {
	 private static String upload_folder = ".//src//main//resources//archivos//";
	@GetMapping("/backup")
	public String obtenerexamendadoelcodigo(){
		Calendar c = Calendar.getInstance();
	String  dia = Integer.toString(c.get(Calendar.DATE));
		String mes = Integer.toString(c.get(Calendar.MONTH)+1);
		String annio = Integer.toString(c.get(Calendar.YEAR));
		
String path=".//src//main//resources//archivos//copia"+dia+"-"+mes+"-"+annio+".backup";
	        realizaBackup(path);  
	        return "copia"+dia+"-"+mes+"-"+annio+".backup";
	
	
	
}
	@ApiOperation(value="realiza restauracion de postgres ")
	@PostMapping("/restaurar")
	public String restaurarcopiadeseguridad(@RequestBody Map<String, String> body){

		//return servicioPaciente.buscar_por_cedula(body.get("cedula"))
		System.out.println(body.get("path"));
	PostgresRestore(body.get("path"));
	return body.get("path");
	}
	
	  public static void realizaBackup(String p) {      
          final List<String> comandos = new ArrayList<String>();   
          String dir = "C:/BKPCECOM";  
                //comandos.add("C:\\Program Files (x86)\\PostgreSQL\\9.4\\bin\\pg_dump.exe"); //cecom
               //comandos.add("C:\\Arquivos de programas\\PostgreSQL\\9.2\\bin\\pg_dump.exe"); 
               comandos.add("C:\\Program Files\\PostgreSQL\\12\\bin\\pg_dump.exe");

                      //comandos.add("-i");      
                      comandos.add("-h");      
                      comandos.add("localhost");
                      //comandos.add("192.168.0.25");
                      comandos.add("-p");      
                      comandos.add("5432");      
                      comandos.add("-U");      
                      comandos.add("postgres");      
                      comandos.add("-F");      
                      comandos.add("c");      
                      comandos.add("-b");      
                      comandos.add("-v");      
                      comandos.add("-f"); 

                      //comandos.add("C:\\TesteHib4\\Backups do Banco de Dados\\"+JOptionPane.showInputDialog(null,"Digite o nome do Backup")+".backup");   // eu utilizei meu C:\ e D:\ para os testes e gravei o backup com sucesso.  
                      //comandos.add("C:\\TesteHib4\\Backups do Banco de Dados\\"+(Character.getNumericValue(recebe)+1)+" "+getDateTime()+".backup");   // eu utilizei meu C:\ e D:\ para os testes e gravei o backup com sucesso.  
                      comandos.add(p);   // eu utilizei meu C:\ e D:\ para os testes e gravei o backup com sucesso.  D:\\backup taller3\\backup-base-datos\\teste.backup
                      comandos.add("LIS");      
                      ProcessBuilder pb = new ProcessBuilder(comandos);      

                      pb.environment().put("PGPASSWORD", "postgres");              

                      try {      
                          final Process process = pb.start();      

                          final BufferedReader r = new BufferedReader(      
                              new InputStreamReader(process.getErrorStream()));      
                          String line = r.readLine();      
                          while (line != null) {      
                          System.err.println(line);      
                          line = r.readLine();      
                          }      
                          r.close();      

                          process.waitFor();    
                          process.destroy(); 
                          System.out.println("backup realizado com sucesso.");  

                      } catch (IOException e) {      
                          e.printStackTrace();      
                      } catch (InterruptedException ie) {      
                          ie.printStackTrace();      
                      } 
    }
	    public static void PostgresRestore(String nomeDoArquivo)
	    {
	   
	               //String pathDoArquivo = arq.toString(); //aqui pega o caminho do backup  
	                      //aqui vocÃª testa se a string recebeu o caminho do arquivo   
	           final List<String> comandos = new ArrayList<String>();      
	           comandos.add("C:\\Program Files\\PostgreSQL\\12\\bin\\pg_restore.exe");
	           //comandos.add("C:\\Program Files (x86)\\PostgreSQL\\9.4\\bin\\pg_restore.exe");
	           //comandos.add("C:\\Program Files\\PostgreSQL\\9.2\\bin\\pg_restore.exe");  //cecom win 7
	           //comandos.add("C:\\Arquivos de programas\\PostgreSQL\\9.4\\bin\\pg_restore.exe");  // windows XP notebook
	           
	           //comandos.add("C:\\Arquivos de programas\\PostgreSQL\\9.2\\bin\\pg_restore.exe");
	           //comandos.add("DROP SCHEMA public CASCADE;"); 
	           //comandos.add("-i");      
	           comandos.add("-h");      
	           comandos.add("localhost");      
	           comandos.add("-p");      
	           comandos.add("5432");      
	           comandos.add("-U");      
	           comandos.add("postgres");
	           comandos.add("-c");
	           comandos.add("-d");
	           comandos.add("LIS");     
	          // comandos.add("-v");      
	             
	           //comandos.add("C:\\BOHib3.6.4\\Backups do Banco de Dados\\bkpBolOcor04102012.backup");   // eu utilizei meu C:\ e D:\ para os testes e gravei o backup com sucesso.  
	           System.out.println(nomeDoArquivo);
	           comandos.add(upload_folder+nomeDoArquivo);
	           ProcessBuilder pb = new ProcessBuilder(comandos);      
	           pb.environment().put("PGPASSWORD", "postgres");        
	           try {      
	               final Process process = pb.start();      
	               final BufferedReader r = new BufferedReader(      
	                   new InputStreamReader(process.getErrorStream()));      
	               String line = r.readLine();      
	               while (line != null) {      
	               System.err.println(line);      
	               line = r.readLine();    
	               
	               }      
	               r.close();      
	               
	               process.waitFor();    
	               process.destroy();                
	         System.out.println("RestauraÃ§Ã£o realizado com sucesso!");
	           } catch (IOException e) {      
	               e.printStackTrace();      
	           } catch (InterruptedException ie) {      
	               ie.printStackTrace();      
	           }         

	    }
	    
	   
	  
}
