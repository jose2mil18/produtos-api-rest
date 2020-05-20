package com.produtos.apirest.Services;


import java.text.ParseException; 
import java.text.SimpleDateFormat; 
import java.sql.Date;
import java.sql.ResultSet;
import java.util.List;
import org.springframework.jdbc.object.*;
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;

import com.produtos.apirest.Services.ServicioExamen.ExamenRowMapper;
import com.produtos.apirest.models.*;
import com.produtos.apirest.varios.Conexion;

@Service
public class ServicioPersona  extends Conexion {
	@Autowired
	Paciente paciente;
	String sql;
	public class PersonaRowMapper implements RowMapper<Persona> {
		@Override
		public Persona mapRow(ResultSet rs, int arg1) throws SQLException {
			Persona p=new Persona();
		p.setCod_persona(rs.getInt("cod_persona"));
			p.setNombre(rs.getString("nombre"));
			p.setAp(rs.getString("ap"));
			p.setAm(rs.getString("am"));
			p.setTipo(rs.getString("tipo"));
			return p;
		}
	}
	public List<Persona> listar(){
		String sql="select pe.cod_persona, pe.nombre, pe.ap, pe.am from persona pe, pacientes pa where  pe.cod_persona=pa.cod_persona;";
		
		return  db.query(sql, new PersonaRowMapper());
		
		
		 
	}
	
	public String obtener_ap_paciente(String coda){
		
		Object[] datos={coda};
		sql="SELECT ap FROM pacientes WHERE cedula=? ";
		
		return db.queryForObject(sql, datos, String.class);
	}
public String obtener_am_paciente(String coda){
		
		Object[] datos={coda};
		sql="SELECT am FROM pacientes WHERE cedula=? ";
		
		return db.queryForObject(sql, datos, String.class);
	}
public String obtener_nombre_paciente(String coda){
	
	Object[] datos={coda};
	sql="SELECT nombre FROM pacientes WHERE cedula=? ";
	
	return db.queryForObject(sql, datos, String.class);
}

public Paciente buscar_por_cedula(String cedula){
	
	Object[] datos={cedula};
	sql="SELECT *  FROM pacientes WHERE cedula=? ";
	
	return db.queryForObject(sql, datos, Paciente.class
			);
}
	
public Persona buscar_por_cod_persona(int cod_persona){
	
	Object[] datos={cod_persona};
	sql="SELECT *  FROM persona WHERE cod_persona=? ";
	
	return db.queryForObject(sql, datos,new PersonaRowMapper());
}
	
public Persona obtener_persona(int cedula){
	
	//obtener persona de paciente

	Object[] datos={cedula};
	String sql="select pe.cod_persona, pe.nombre, pe.ap, pe.am, pe.tipo from persona pe, pacientes pa where  pe.cod_persona=pa.cod_persona and pa.cod_persona=?;";
	return  db.queryForObject(sql, datos,new PersonaRowMapper());
	
	
	
	 
}

public Persona obtener_persona_de_usuario(int cod_persona){
	
	//int a=Integer.parseInt(cod_examen);

	Object[] datos={cod_persona};
	String sql="select pe.cod_persona, pe.nombre, pe.ap, pe.am, pe.tipo from persona pe, personal u where  pe.cod_persona=u.cod_persona and pe.cod_persona=?;";
	return  db.queryForObject(sql, datos,new PersonaRowMapper());
	
	
	
	 
}

public Persona obtener_doctor_solicitante(int cod_doctor_solicitante){
	
	//int a=Integer.parseInt(cod_examen);

	Object[] datos={cod_doctor_solicitante};
	String sql="select * from persona where cod_persona=?;";
	return  db.queryForObject(sql, datos,new PersonaRowMapper());
	
	
	
	 
}

public Integer contarpacientes(){
	
	sql="select count(*) from pacientes;";
	
	return db.queryForObject(sql,Integer.class);
}

public Persona registrar(Persona persona){
	Object[] datos={persona.getNombre(), persona.getAp(), persona.getAm(), persona.getTipo()};
	db.update("insert into persona(nombre, ap, am, tipo) values(?, ?, ?, ?);", datos);
	return db.queryForObject("select * from persona order by cod_persona desc limit 1;", new PersonaRowMapper());
}
}


