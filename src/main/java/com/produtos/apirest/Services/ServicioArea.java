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
import org.springframework.dao.DataAccessException;
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

import com.produtos.apirest.Services.ServicioPaciente;
@Service
public class ServicioArea extends Conexion {

	@Autowired
	Area area;
	@Autowired
	ServicioArea servicioArea;
	String sql;
	public class AreaRowMapper implements RowMapper<Area> {
		@Override
		public Area mapRow(ResultSet rs, int arg1) throws SQLException {
			Area a=new Area();
			a.setCod_area(rs.getInt("cod_area"));
			a.setNombre(rs.getString("nombre"));
	
		
			return a;
		}
	}
	
/*
public List<Rol> listarroldeusuario(String cedula){
	kjlk
	String sql="select r.cod_rol, r.nombre from rol r, usuario u, usurol ur where r.cod_rol=ur.cod_rol and u.cedula=ur.cedula_usuario  and u.cedula='"+cedula+"' order by ur.cod_rol;";
	return  db.query(sql, new RolRowMapper());
}
*/
	public Area buscarAreaDeExamen(int cod_examen){
	
Area area=new Area();
		Object[] datos={cod_examen};
		String sql="";
		
			
		
		try {
			sql = "select a.cod_area, a.nombre from area a, examen e where a.cod_area=e.cod_area and e.cod_examen=?;";

			area=db.queryForObject(sql, datos,new AreaRowMapper());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  area;
		
		
		
		
		 
	}
public List<Area> listar(){
	
	String sql="select * from area;";
	return  db.query(sql, new AreaRowMapper());
}
public Area buscarPorCodigo(int cod_area){
	int numero=0;
	String sql="select * from area where cod_area="+cod_area+";";
	 try {
		numero=db.queryForObject("select cod_area from area  where cod_area="+cod_area+";", Integer.class);
	} catch (DataAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("cod_area"+numero);
	cod_area=1;
	sql="select * from area where cod_area="+cod_area+";";
	return  db.queryForObject(sql, new AreaRowMapper());
}



}