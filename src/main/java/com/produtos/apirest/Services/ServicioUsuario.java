package com.produtos.apirest.Services;

import java.nio.file.Path;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.sql.ResultSet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.object.*;
//import org.springframework.security.crypto.password.Md4PasswordEncoder;
//
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.produtos.apirest.models.*;
import com.produtos.apirest.Services.*;
import com.produtos.apirest.varios.*;
@Service
public class ServicioUsuario extends Conexion {

    private String upload_folder = ".//src//main//resources//imagenes//";
	@Autowired
	Usuario usuario;
	@Autowired
	ServicioPersona servicioPersona;
	@Autowired
	ServicioPersonal servicioPersonal;
	@Autowired
	ServicioRol servicioRol;
	public class UsuarioRowMapper implements RowMapper<Usuario> {
		@Override
		public Usuario mapRow(ResultSet rs, int arg1) throws SQLException {
			Usuario usuario = new Usuario();
			usuario.setCedula(rs.getString("cedula"));
			usuario.setLogin(rs.getString("login"));
			//usuario.setCedula(rs.getString("cedula"));
			usuario.setRol(servicioRol.buscarRolDeUsuario(rs.getInt("cod_rol")));
			usuario.setPassword(rs.getString("password"));
			usuario.setEstado(rs.getString("estado"));
			usuario.setPersonal_laboratorio(servicioPersonal.buscarPersonalDeUsuario(rs.getString("cedula")));
			return usuario;
		}
	}
	
public Usuario validar(String log, String cla) {
	String sql = "select   u.login, u.password, u.estado, u.cedula, u.cod_rol from usuario u where u.login=? and u.password=? and u.estado='habilitado';";

	usuario = db.queryForObject(sql, new UsuarioRowMapper(), log, cla);
	
	return usuario;
	
	
}
public void guardarImagen( MultipartFile file) throws IOException {
    if(!file.isEmpty()){
        byte[] bytes = file.getBytes();
        Path path = Paths.get(upload_folder + file.getOriginalFilename());
        Files.write(path,bytes);
    }
	
}
public Usuario buscarPorCodigo(String cedula) {
	String sql = "select  * from usuario where cedula=?;";
Usuario u=new Usuario();
	try {
		u= db.queryForObject(sql, new UsuarioRowMapper(), cedula);
	} catch (DataAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return u;
	
	
}
public void cambiar_contraseña(String cedula, String contraseña){
	Object[] datos={contraseña, cedula};
	String sql="update  usuario set password=? where cedula=?;";
	db.update(sql, datos);
}
public Integer num_doctores(){
	String sql="select count(*) from usuario;";

	return db.queryForObject(sql, Integer.class);
}

public String obtenernombre_usuario(String login){
	Object[] datos={login};
	String sql="select p.nombre from persona p, usuarios u where p.cedula=u.cedula and login=? and p.tipo='U';";

	return db.queryForObject(sql, datos, String.class);
}
public String obtenerap_usuario(String login){
	Object[] datos={login};
	String sql="select p.ap from persona p, usuarios u where p.cedula=u.cedula and login=? and p.tipo='U';";

	return db.queryForObject(sql, datos, String.class);
}
public String obteneram_usuario(String login){
	Object[] datos={login};
	String sql="select p.am from persona p, usuarios u where p.cedula=u.cedula and login=? and p.tipo='U';";

	return db.queryForObject(sql, datos, String.class);
}
public List<Usuario> listar(String estado, String rol, String cedula, String nombres){
	System.out.println("rol"+rol);
	System.out.println(cedula);
	String sql;
	if(estado.equals("") )
	{
sql="select  u.login, u.password, u.estado, u.cedula, u.cod_rol from usuario u, rol r, personal pel, persona per where u.cedula ilike '%"+cedula+"%' and u.estado ilike '%"+estado+"%' and u.cod_rol=r.cod_rol and r.nombre ilike '%"+rol+"%' and pel.cedula=u.cedula and per.cod_persona=pel.cod_persona and (per.nombre ilike '%"+nombres+"%' or per.ap ilike '%"+nombres+"%' or per.am ilike '%"+nombres+"%');";
	}
	else
	{

	sql="select  u.login, u.password, u.estado, u.cedula, u.cod_rol from usuario u, rol r, personal pel, persona per  where u.cedula ilike '%"+cedula+"%' and u.estado= '"+estado+"' and u.cod_rol=r.cod_rol and r.nombre ilike '%"+rol+"%'  and pel.cedula=u.cedula and per.cod_persona=pel.cod_persona and (per.nombre ilike '%"+nombres+"%' or per.ap ilike '%"+nombres+"%' or per.am ilike '%"+nombres+"%');";
	}
	return  db.query(sql, new UsuarioRowMapper());
	
	
	 
}
@SuppressWarnings("deprecation")
public Usuario registrar(Usuario u){

String tipo="Doctor";
Object[] datos1={u.getPersonal_laboratorio().getPersona().getNombre(), u.getPersonal_laboratorio().getPersona().getAp(), u.getPersonal_laboratorio().getPersona().getAm(), tipo};
	Object[] datos2={u.getLogin(), u.getPassword(), "habilitado",u.getCedula(), u.getRol().getCod_rol()};
String sql1="insert into persona(nombre, ap, am, tipo) values(?,?,?,?)";
	
//	login=new Md4PasswordEncoder().encode(login);
//	password=new Md4PasswordEncoder().encode(password);
	String sql2="insert into usuario(login, password, estado, cedula, cod_rol) values(?,?,?,?,?)";
	db.update(sql1, datos1);
	
	
	int cod_persona=db.queryForObject("select max(cod_persona) from persona", Integer.class);
	if(u.getPersonal_laboratorio().getFoto().equals(""))
	{
		System.out.println("sin foto");
	Object datos6[]={u.getCedula(),  u.getPersonal_laboratorio().getProfesion(), cod_persona};
	db.update("insert into personal(cedula,profesion,cod_persona) values(?,?,?);", datos6);
	
	}
	else
	{

		System.out.println("con foto");
		Object datos6[]={u.getCedula(), u.getPersonal_laboratorio().getFoto(), u.getPersonal_laboratorio().getProfesion(), cod_persona};
		db.update("insert into personal values(?,?,?,?);", datos6);
		
	}
	db.update(sql2, datos2);
	/*
	for(Rol rol : u.getRoles()){
		Object[] datos3={ u.getCedula(), rol.getCod_rol()};
	System.out.println(rol.getCod_rol());
		String sql4="insert into usurol values(?,?)";
		db.update(sql4, datos3);
	}
	*/
	System.out.println("succesfull");
	return buscarPorCodigo(u.getCedula());
}
public void modificarEstado(Usuario u){
	System.out.println(u.getEstado());
	Object[] datos1={ u.getEstado(), u.getCedula()};
	String sql="update usuario set  estado=? where cedula=?";
	db.update(sql, datos1);

}
public Usuario modificar(Usuario u){


Object[] datos1={ u.getPersonal_laboratorio().getPersona().getNombre(), u.getPersonal_laboratorio().getPersona().getAp(), u.getPersonal_laboratorio().getPersona().getAm(), u.getPersonal_laboratorio().getPersona().getCod_persona() };
	Object[] datos2={ u.getEstado(),u.getRol().getCod_rol(), u.getCedula()};
	//Object[] datos3={u.getRoles().get(0).getCod_rol(), u.getCedula()};
	Object[] datos4={u.getPersonal_laboratorio().getProfesion(),u.getPersonal_laboratorio().getFoto(), u.getCedula()};
System.out.println(u.getPersonal_laboratorio().getFoto());
	String sql1="update  persona set nombre=?, ap=?, am=? where cod_persona=?";

//	password=new Md4PasswordEncoder().encode(password);
	String sql2="update usuario set  estado=?, cod_rol=? where cedula=?";
	//String sql3="update usurol set  cod_rol=? where cedula_usuario=?";
	String sql4="update personal set profesion=?, foto=? where cedula=?";
	db.update(sql1, datos1);
	db.update(sql2, datos2);
	//db.update(sql3, datos3);

	db.update(sql4, datos4);
	System.out.println("succesfull");
	return buscarPorCodigo(u.getCedula());
	
}


public void modificar_password(String passwor, String logi){
	System.out.println(logi+" "+passwor);
	String sql="update usuarios set password='"+passwor+"' where login='"+logi+"';";
			db.update(sql);
			
}
	

public Usuario verificarCedulaquenoexista(String cedula){
	//System.out.println(logi+" "+passwor);
	String sql="select * from usuario where cedula='"+cedula+"';";
		return	db.queryForObject(sql, new UsuarioRowMapper() );
			
}
	


public void modificar(String login, String cedula, String password, Integer estado, String loginnew){
	System.out.println(login);
	Object[]  datos={cedula, password, estado, loginnew, login};
	db.update("update usuarios set cedula=?, password=?, estado=?, login=? where login=?;", datos);
	System.out.println("succesfull");
}


public  List<Usuario> filtrarlista(String cadena){
	   String tipo="U";
		cadena="%"+cadena+"%";
		String sql="select u.login, u.cedula, u.password, u.estado from usuarios u, personal p where (p.nombre ilike ? or p.ap ilike ? or p.am ilike ? )  and u.cedula=p.cedula and p.tipo='U';";
		return  db.query(sql, new UsuarioRowMapper(),cadena, cadena, cadena);
	
	}
	}
