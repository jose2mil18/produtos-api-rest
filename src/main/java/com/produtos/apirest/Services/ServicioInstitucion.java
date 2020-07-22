package com.produtos.apirest.Services;
import java.text.ParseException; 
import java.text.SimpleDateFormat; 
import java.sql.Date;
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
import org.springframework.stereotype.Service;
import com.produtos.apirest.models.Usuario;
import com.produtos.apirest.models.*;
import com.produtos.apirest.Services.ServicioPaciente;
import com.produtos.apirest.Services.ServicioUsuario.UsuarioRowMapper;
import com.produtos.apirest.Services.ServicioMenu;
import com.produtos.apirest.varios.*;

import com.produtos.apirest.Services.ServicioPaciente;
@Service
public class ServicioInstitucion extends Conexion {

	@Autowired
	Institucion institucion;
	@Autowired
	ServicioInstitucion servicioinstitucion;
	String sql;
	public class InstitucionRowMapper implements RowMapper<Institucion> {
		@Override
		public Institucion mapRow(ResultSet rs, int arg1) throws SQLException {
			Institucion i=new Institucion();
			i.setCod_institucion(rs.getString("cod_institucion"));
			i.setNombre(rs.getString("nombre"));
			i.setCod_institucion_padre(rs.getString("cod_institucion_padre"));
	//i.setInstitucion_padre(servicioinstitucion.institucionPadre(rs.getInt("cod_institucion_padre")));
		
			return i;
		}
	}
	

public Institucion buscarInstitucionDeSolicitud(String cod_solicitud){
	
	//String sql="select  distinct i.cod_institucion, i.nombre, i.cod_institucion_padre from solicitud s, institucion i where i.cod_institucion=s.cod_institucion and s.cod_solicitud="+cod_solicitud+";";
	String sql="select * from institucion where cod_institucion='"+cod_solicitud+"';";
	
	return  db.queryForObject(sql,  new InstitucionRowMapper());
}
public List<Institucion> listapostas(){
	
	String sql="select * from institucion where cod_institucion_padre='SIS';";
	return  db.query(sql, new InstitucionRowMapper());
}

public Institucion buscarPorCodigo(String cod){
	
	String sql="select * from institucion where cod_institucion='"+cod+"';";
	return  db.queryForObject(sql, new InstitucionRowMapper());
}
public Institucion institucionPadre(int cod){
	
	String sql="select * from institucion where cod_institucion="+cod+";";
	return  db.queryForObject(sql, new InstitucionRowMapper());
}

public String nombre(int cod_institucion){
	
	String sql="select nombre from institucion where cod_institucion="+cod_institucion+";";
	return  db.queryForObject(sql, String.class);
}
public List<Institucion> institucionesaFaltantes(){
	
	String sql="select * from institucion where cod_institucion='PAR' OR cod_institucion='CO-UN' OR cod_institucion='CA-UN';";
	return  db.query(sql, new InstitucionRowMapper());
}
public List<Institucion> institucionesconprecio(){
	
	String sql=" select distinct i.cod_institucion, i.nombre, i.cod_institucion_padre from institucion i, precio_examen pe where i.cod_institucion=pe.cod_institucion ";
	return  db.query(sql, new InstitucionRowMapper());
}
}
