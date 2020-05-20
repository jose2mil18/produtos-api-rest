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

import com.produtos.apirest.models.*;
import com.produtos.apirest.Services.ServicioPaciente;
import com.produtos.apirest.Services.ServicioPersona.PersonaRowMapper;
import com.produtos.apirest.Services.ServicioUsuario.UsuarioRowMapper;
import com.produtos.apirest.Services.*;
import com.produtos.apirest.varios.*;

@Service
public class ServicioDosificacion extends Conexion{
	public class mapearDosificaciones implements RowMapper<Dosificacion>{
		@Override
		public Dosificacion mapRow(ResultSet rs, int arg1) throws SQLException {
			Dosificacion modelo = new Dosificacion();
			modelo.setCod_dosificacion( rs.getInt( "cod_dosificacion" ) );
			modelo.setLlave( rs.getString( "llave" ) );
			modelo.setAutorizacion( rs.getString( "autorizacion" ) );
			modelo.setNit( rs.getInt( "nit" ) );
			modelo.setNro_emisiones( rs.getInt( "nro_emisiones" ) );
			modelo.setFecha_registro( rs.getDate( "fecha_registro" ) );
			modelo.setFecha_limite_emision( rs.getDate( "fecha_limite_emision" ) );
			modelo.setLeyenda( rs.getString( "leyenda" ) );
			return modelo;
		}
	}
	
	public List<Dosificacion> listar_Dosificaciones(){
		String xsql="	select *	"+
					"	from dosificacion	";
		return db.query(xsql,new mapearDosificaciones());
	}
	
	public Dosificacion buscarLlave(String llave){
		String xsql="	select *	"+
					"	from dosificacion	"+
					"	where upper(llave)=?	";
		return db.queryForObject(xsql,new mapearDosificaciones(), llave);
	}
	
	public Dosificacion buscarAutorizacion(String autorizacion){
		String xsql="	select *	"+
					"	from dosificacion	"+
					"	where upper(autorizacion)=?	";
		return db.queryForObject(xsql,new mapearDosificaciones(), autorizacion);
	}
	
	public int adicionarDosificacion(String llave, String autorizacion, Date format_fecha_limite_emision, String leyenda){
		String xsql="	insert into dosificacion(llave,autorizacion,nit,fecha_limite_emision,leyenda) values(?,?,1654874680,?,?)	";
		return db.update(xsql,llave,autorizacion,format_fecha_limite_emision,leyenda);
	}
	
	public Dosificacion verDosificacion(int id_dosificacion){
		String xsql="	select *	"+
					"	from dosificacion	"+
					"	where cod_dosificacion=?	";
		return db.queryForObject(xsql,new mapearDosificaciones(),id_dosificacion);
	}
	
	public Dosificacion listarDosificacionVigente(){
		String xsql="	select distinct *	"+
					"	from dosificacion	"+
					"	where fecha_limite_emision > now()	";
		return db.queryForObject(xsql,new mapearDosificaciones());
	}
}
