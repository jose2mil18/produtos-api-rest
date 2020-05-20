package com.produtos.apirest.Services;


import java.sql.ResultSet;

import java.util.List;
import org.springframework.jdbc.object.*;
//import org.springframework.security.crypto.password.Md4PasswordEncoder;
//
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;
import com.produtos.apirest.models.*;
import com.produtos.apirest.Services.*;
import com.produtos.apirest.Services.ServicioPersona.PersonaRowMapper;
import com.produtos.apirest.varios.*;
@Service
public class ServicioPersonal extends Conexion {

	@Autowired
	Usuario usuario;
	@Autowired
	ServicioPersona servicioPersona;
	@Autowired
	ServicioRol servicioRol;
	public class PersonalRowMapper implements RowMapper<Personal> {
		@Override
		public Personal  mapRow(ResultSet rs, int arg1) throws SQLException {
			Personal  p = new Personal ();
			p.setCedula(rs.getString("cedula"));
			p.setCod_persona(rs.getInt("cod_persona"));
			p.setProfesion(rs.getString("profesion"));
			
			p.setFoto(rs.getString("foto"));
			if(p.getFoto().equals("")){
				p.setFoto("http://localhost:9898/api/file/photo_profile_user.png");
			}
			else {
				p.setFoto("http://localhost:9898/api/file/"+p.getFoto());
			}
			p.setPersona(servicioPersona.obtener_persona_de_usuario(p.getCod_persona()));
			return p;
		}
		
	
	}
	public Personal obtener_personal_de_usuario(String cedula) {
		Object[] datos={cedula};
		String sql="select * from personal where  cedula=?";
		return  db.queryForObject(sql, datos,new PersonalRowMapper());
		
		
	}
	
}
