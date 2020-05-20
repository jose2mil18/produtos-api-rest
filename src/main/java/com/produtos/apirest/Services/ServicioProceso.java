package com.produtos.apirest.Services;

import java.sql.ResultSet;
import java.util.List;
import org.springframework.jdbc.object.*;
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;
import com.produtos.apirest.varios.*;

import com.produtos.apirest.Services.ServicioPaciente;
import com.produtos.apirest.models.Proceso;
@Service
public class ServicioProceso extends Conexion {
	@Autowired
	Proceso proceso;
	String sql;
	public class ProcesoRowMapper implements RowMapper<Proceso> {
		@Override
		public Proceso mapRow(ResultSet rs, int arg1) throws SQLException {
			Proceso proceso=new Proceso();
			proceso.setCod_proceso(rs.getInt("cod_proceso"));
			proceso.setNombre(rs.getString("nombre"));
			proceso.setEnlace(rs.getString("enlace"));
	
			
			return proceso;
		}
	}
	

	public List<Proceso> listar(){
		String sql="select * from proceso order by cod_proceso";
		return  db.query(sql,new ProcesoRowMapper());
		
		
		 
	}


	public List<Proceso> listarprocesosdemenu(int codm){
		String sql="select p.cod_proceso, p.nombre, p.enlace from proceso p, menu m where m.cod_menu=? and m.cod_menu=p.cod_menu;";
		return  db.query(sql,new ProcesoRowMapper(), codm);
		
		
		 
	}

}
