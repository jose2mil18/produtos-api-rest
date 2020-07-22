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
import com.produtos.apirest.models.Rol;
import com.produtos.apirest.Services.ServicioPaciente;
import com.produtos.apirest.Services.ServicioUsuario.UsuarioRowMapper;
import com.produtos.apirest.Services.ServicioMenu;
import com.produtos.apirest.varios.*;

import com.produtos.apirest.Services.ServicioPaciente;
@Service
public class ServicioRol extends Conexion {

	@Autowired
	Rol rol;
	@Autowired
	ServicioMenu servicioMenu;
	String sql;
	public class RolRowMapper implements RowMapper<Rol> {
		@Override
		public Rol mapRow(ResultSet rs, int arg1) throws SQLException {
			Rol rol=new Rol();
			rol.setCod_rol(rs.getInt("cod_rol"));
			rol.setNombre(rs.getString("nombre"));
	
			rol.setMenus(servicioMenu.listarMenusDeRol(rs.getInt("cod_rol")));
			
			return rol;
		}
	}
	

public Rol buscarRolDeUsuario(int cod_rol){
	
	String sql="select * from rol where cod_rol="+cod_rol+"; ";
	return  db.queryForObject(sql, new RolRowMapper());
}
public List<Rol> listar(){
	
	String sql="select * from rol;";
	return  db.query(sql, new RolRowMapper());
}



}
