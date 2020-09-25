package com.produtos.apirest.Services;//
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*; 
import java.text.SimpleDateFormat; 
import java.sql.Date;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.List;
import org.springframework.jdbc.object.*;
import java.sql.SQLException;
import java.sql.Types;
import com.produtos.apirest.Services.*;
import com.produtos.apirest.Services.ServicioSolicitud.SolicitudRowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;
import com.produtos.apirest.models.Paciente;
import com.produtos.apirest.controller.*;
import com.produtos.apirest.models.Rol;
import com.produtos.apirest.varios.Conexion;

@Service
public class ServicioPaciente  extends Conexion {
	@Autowired
	Paciente paciente;
	@Autowired
	ServicioPersona servicioPersona;
	@Autowired
	ServicioSolicitud servicioSolicitud;
	@Autowired
	ServicioExamen_solicitado servicioExamen_solicitado;
	@Autowired
	PacienteController pacienteController;
	String sql;
	public class PacienteRowMapper implements RowMapper<Paciente> {
		@Override
		public Paciente mapRow(ResultSet rs, int arg1) throws SQLException {
			Paciente p=new Paciente();
		 PacienteController pa=new PacienteController();
			p.setCedula(rs.getString("cedula"));
			p.setCod_persona(rs.getInt("cod_persona"));
			p.setPersona(servicioPersona.obtener_persona(p.getCod_persona()));
			p.setProcedencia(rs.getString("procedencia"));
			p.setSexo(rs.getString("sexo"));
			
			p.setNombres(p.getPersona().getNombre()+" "+p.getPersona().getAp()+" "+p.getPersona().getAm());
			//p.setFecha_nacimiento(rs);

		     p.setEdad(pa.calcularEdad(rs.getString("fnac")));

			    SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");


							p.setFnac(dmyFormat.format(rs.getDate("fnac")));
			    // Format the date to Strings
			   
			
			 //p.setEdad(rs.getInt("edad"));hj
			p.setCedula_usuario(rs.getString("cedula_usuario"));
			//p.setSolicitudes_examenes(servicioSolicitud.buscarSolicitudPorCedulaPaciente(p.getCedula()));
			p.setExamenes_solicitados(servicioExamen_solicitado.listarExamenesSolicitadosDePaciente(p.getCedula()));
			p.setCorreo_electronico(rs.getString("correo_electronico"));
		
			return p;
		}
	}
	public List<Paciente> listar(){
		System.out.println("klñpfjalsjdflkjadsf");
		String sql="select * from pacientes";
		
		return  db.query(sql, new PacienteRowMapper());
		
		
		 
	}
	public String nombresCompletos(String cedula) {
		Paciente p=new Paciente();
		p=buscarPorCedula(cedula);
		return p.getPersona().getNombre()+" "+p.getPersona().getAp()+" "+p.getPersona().getAm();
	}
	public List<Paciente> buscar(String procedencia, String sexo, String eda, String cedula, String id){
		System.out.println("-----------------"+procedencia+" sexo"+sexo+"edad "+eda+"cedula"+cedula+"id"+id);
		
String nombre="";
String ap="";
String am="";
String operador="";
		String[] array=id.split(" ");
		System.out.println("array"+array.length);
		
		if(array.length==1)
		{
			operador="or";
		 nombre=id;
		 ap=id;
		 am=id;
		}
		if(array.length==2)
		{
			operador="and";
			nombre=array[0];
			ap=array[1];
			am="";
		}
		if(array.length==3)
		{
			operador="and";
			nombre=array[0];
			ap=array[1];
			am=array[2];
		}
		
		int edad=0;
		List<Paciente> pacientes = new ArrayList<Paciente>();
		System.out.println(eda);
		try {
			edad=Integer.parseInt(eda);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block20
			e.printStackTrace();
		}
		String sql="";
		if(edad !=-1)
		{
	sql="select  pa.cedula, pa.procedencia, pa.sexo, pa.cedula_usuario, pa.fnac, pa.correo_electronico, pa.cod_persona from pacientes pa, persona pe where pe.cod_persona=pa.cod_persona and  pa.procedencia ilike '%"+procedencia+"%' and pa.sexo ilike '%"+sexo+"%' and pa.cedula ilike '%"+cedula+"%'  and (pe.nombre ilike '%"+nombre+"%' "+operador+" pe.ap ilike '%"+ap+"%' "+operador+" pe.am ilike '%"+am+"%'  ) and EXTRACT(YEAR from AGE(pa.fnac))="+edad+"; ";
		}
		else {
		 sql="select  pa.cedula, pa.procedencia, pa.sexo, pa.cedula_usuario, pa.fnac, pa.correo_electronico, pa.cod_persona from pacientes pa, persona pe where pe.cod_persona=pa.cod_persona and  pa.procedencia ilike '%"+procedencia+"%' and pa.sexo ilike '%"+sexo+"%' and pa.cedula ilike '%"+cedula+"%'  and (pe.nombre ilike '%"+nombre+"%' "+operador+" pe.ap ilike '%"+ap+"%' "+operador+" pe.am ilike '%"+am+"%'  ) ; ";
			
		}
		pacientes=db.query(sql, new PacientesRowMapper());
	System.out.println("------------------------cantidaddepacientes"+pacientes.size());
		
		return  pacientes;
		
		
		
	}
	
public List<Paciente> buscar_pacientes_que_hicieron_solicitudes_de_examenes(String id, String resultados){
	String sql="";
	String[] array=id.split(" ");
		if(!id.equals(""))
		{
		
			System.out.println("klkklklkk"+resultados);
		sql="select   distinct pa.cedula, pa.procedencia, pa.sexo, pa.cedula_usuario, pa.fnac, pa.correo_electronico, pa.cod_persona from pacientes pa, persona pe, solicitud s where s.cedula_paciente=pa.cedula and pe.cod_persona=pa.cod_persona and (pe.nombre ilike '%"+id+"%' or pe.ap ilike '%"+id+"%' or pe.am ilike '%"+id+"%'  ) and estado ilike '%"+resultados+"%' ";
		
		if(array.length == 2) {
			sql="select   distinct pa.cedula, pa.procedencia, pa.sexo, pa.cedula_usuario, pa.fnac, pa.correo_electronico, pa.cod_persona from pacientes pa, persona pe, solicitud s where s.cedula_paciente=pa.cedula and pe.cod_persona=pa.cod_persona and (pe.nombre ilike '%"+array[0]+"%' and pe.ap ilike '%"+array[1]+"%' ) and estado ilike '%"+resultados+"%' ";
			
		}
        if(array.length == 3) {
	    sql="select   distinct pa.cedula, pa.procedencia, pa.sexo, pa.cedula_usuario, pa.fnac, pa.correo_electronico, pa.cod_persona from pacientes pa, persona pe, solicitud s where s.cedula_paciente=pa.cedula and pe.cod_persona=pa.cod_persona and (pe.nombre ilike '%"+array[0]+"%' and pe.ap ilike '%"+array[1]+"%' and pe.am ilike '%"+array[2]+"%'  ) and estado ilike '%"+resultados+"%' ";

			
		}
		return  db.query(sql, new PacientesRowMapper());
		}
		else
		{
			return new ArrayList<Paciente>();
		}
		 
	}
public void registrar(Paciente p){

		System.out.println("fnactipo cadena"+p.getFnac());
		Object[] datos1={ p.getPersona().getNombre(), p.getPersona().getAp(), p.getPersona().getAm(), "Paciente"};
		java.util.Date fnac=ParseFecha(p.getFnac());
	

	    
	String sql1="insert into persona(nombre, ap, am, tipo) values(?,?,?,?)";
		db.update(sql1, datos1);
		int codigodepersona=db.queryForObject("select max(cod_persona) from persona;", Integer.class) ;
//			login=new Md4PasswordEncoder().encode(login);
//			password=new Md4PasswordEncoder().encode(password);
		Object[] datos2={p.getCedula(), p.getProcedencia(), p.getSexo(), p.getCedula_usuario(), fnac, p.getCorreo_electronico(), codigodepersona};
		
			String sql2="insert into pacientes values(?,?,?,?,?,?,?)";
		
			db.update(sql2, datos2);
		
			System.out.println("succesfull");
		}
	public Paciente modificar(Paciente p){
		System.out.println("fechade nacimiento"+p.getFnac());
		java.util.Date fnac=ParseFecha(p.getFnac());
	
			Object[] datos2={ p.getProcedencia(), p.getSexo(), p.getCedula_usuario(), fnac, p.getCorreo_electronico(), p.getCedula()};
		
//			login=new Md4PasswordEncoder().encode(login);
//			password=new Md4PasswordEncoder().encode(password);
			String sql2="update pacientes set procedencia=?, sexo=?, cedula_usuario=?, fnac=?, correo_electronico=?  where cedula=?";
		servicioPersona.modificar(p.getPersona());
			db.update(sql2, datos2);
		
			System.out.println("succesfull");
			return buscarPorCedula(p.getCedula());
		
		
		}


public Paciente buscarPorCedula(String cedula){
	Paciente p=new Paciente();
	System.out.println("esa es la cedula"+cedula);
	Object[] datos={cedula};
	sql="SELECT *  FROM pacientes WHERE cedula=? ";
	
	
	try {
		p=db.queryForObject(sql, datos, new PacienteRowMapper());
	} catch (DataAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return p;
}
	

public Paciente buscar_paciente_de_solicitud(String cedula){
	System.out.println("--------ananana------------------------------------------------------------------------------------------------------");
	Object[] datos={cedula};
	sql="select distinct p.cedula, p.cod_persona,  p.procedencia, p.sexo, p.cedula_usuario, p.fnac, p.correo_electronico from pacientes p, solicitud s where s.cedula_paciente=p.cedula and s.cedula_paciente=?;";
	
	return db.queryForObject(sql, datos,  new PacientesRowMapper()
			);
}
	
public Integer contarpacientes(){
	
	sql="select count(*) from pacientes;";
	
	return db.queryForObject(sql,Integer.class);
}

public static java.util.Date ParseFecha(String fecha)
{
    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
    java.util.Date fechaDate = null;
    try {
        fechaDate = formato.parse(fecha);
    } 
    catch (ParseException ex) 
    {
        System.out.println(ex);
    }
    return fechaDate;
}
public Integer calcularEdad(String fecha){
	   java.util.Date fechaNac=null;
	       try {
	           /**Se puede cambiar la mascara por el formato de la fecha 
	           que se quiera recibir, por ejemplo año mes día "yyyy-MM-dd"
	           en este caso es día mes año*/
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
public class PacientesRowMapper implements RowMapper<Paciente> {
	@Override
	public Paciente mapRow(ResultSet rs, int arg1) throws SQLException {
		Paciente p=new Paciente();
	 PacienteController pa=new PacienteController();
		p.setCedula(rs.getString("cedula"));
		p.setCod_persona(rs.getInt("cod_persona"));
		p.setPersona(servicioPersona.obtener_persona(p.getCod_persona()));
		//p.setProcedencia(rs.getString("procedencia"));
		//p.setSexo(rs.getString("sexo"));
		p.setProcedencia(rs.getString("procedencia"));
		p.setSexo(rs.getString("sexo"));
		
		p.setNombres(p.getPersona().getNombre()+" "+p.getPersona().getAp()+" "+p.getPersona().getAm());
		//p.setFecha_nacimiento(rs);

	     p.setEdad(pa.calcularEdad(rs.getString("fnac")));
		
		//p.setFecha_nacimiento(rs);

	     //p.setEdad(pa.calcularEdad(rs.getString("fnac")));
 
		    //SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MM-yyyy");

			//p.setFnac(dmyFormat.format(rs.getDate("fnac")));
		    // Format the date to Strings
		    SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");


			p.setFnac(dmyFormat.format(rs.getDate("fnac")));
		
		 //p.setEdad(rs.getInt("edad"));hj
		p.setCedula_usuario(rs.getString("cedula_usuario"));
		//p.setSolicitudes_examenes(servicioSolicitud.buscarSolicitudPorCedulaPaciente(p.getCedula()));
		//p.setExamenes_solicitados(servicioExamen_solicitado.examenes_solicitados_de_paciente(p.getCedula()));
		p.setCorreo_electronico(rs.getString("correo_electronico"));
	
		return p;
	}
}

}


