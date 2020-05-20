package com.produtos.apirest.Services;
import java.text.ParseException; 
import java.text.SimpleDateFormat; 
import java.sql.Date;
import java.text.ParseException; 
import java.text.SimpleDateFormat; 
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
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
import com.produtos.apirest.models.*;

import com.produtos.apirest.models.Solicitud;

import com.produtos.apirest.Services.ServicioResultados_examen;
import com.produtos.apirest.Services.ServicioSolicitud.SolicitudRowMapper;
import com.produtos.apirest.Services.ServicioUsuario.UsuarioRowMapper;
import com.produtos.apirest.Services.ServicioMenu;
import com.produtos.apirest.varios.*;
import com.produtos.apirest.Services.*;

import com.produtos.apirest.Services.ServicioPaciente;
@Service
public class ServicioExamen_solicitado extends Conexion {
	@Autowired
	ServicioPrecio_examen servicioPrecio_examen;
	@Autowired
	Examen_solicitado estado_resultado_examen;
	@Autowired
	ServicioExamen servicioExamen;
	@Autowired
	ServicioUsuario servicioUsuario;
	@Autowired
	ServicioSolicitud servicioSolicitud;
	@Autowired
ServicioResultados_examen servicioResultados_examen;
	
	String sql;
	public class Examen_solicitadoRowMapper implements RowMapper<Examen_solicitado> {
		@Override
		public Examen_solicitado mapRow(ResultSet rs, int arg1) throws SQLException {
			Examen_solicitado i=new Examen_solicitado();
			i.setCod_sol_exam(rs.getInt("cod_sol_exam"));
			i.setCod_solicitud(rs.getInt("cod_solicitud"));
			//i.setCod_examen(rs.getInt("cod_examen"));
			//System.out.println(rs.getInt("cod_precio_examen"));
		i.setPrecio_examen(servicioPrecio_examen.getById(rs.getInt("cod_precio_examen")));
		i.setCod_precio_examen(rs.getInt("cod_precio_examen"));
			i.setEstado(rs.getString("estado"));
			i.setFecha(rs.getDate("fecha"));
			i.setCedula_usuario(rs.getString("cedula_usuario"));
			System.out.println("fajkjasklfjasklfjkajfkasdjfkjkadsf"+i.getCedula_usuario());
					
			if(!(i.getCedula_usuario() == null))
			{
	i.setUsuario(servicioUsuario.obtener_usuario(i.getCedula_usuario()));
}
	else
		
	{
		i.setUsuario(new Usuario());
	}
			//i.setExamen(servicioExamen.obtener_examen(i.getCod_examen()));
			//i.setNum_subexamenes(i.getPrecio_examen().getExamen().getSubexamenes().size());
	//i.setEstado_resultado_examen_padre(servicioEstado_resultado_examen.Estado_resultado_examen(rs.getInt("cod_Estado_resultado_examen_padre")));
		//i.setResultados_examenes(servicioResultados_examen.obtener_resultados_examenes(i.getCod_sol_exam()));if
			if(i.getEstado().equals("Registrado")  ||  i.getEstado().equals("Actualizado") )
			{
		
	i.setResultados_examen(servicioResultados_examen.obtener_resultados_examen(i.getCod_sol_exam()));
			}
		
			try {
				i.setSolicitud(servicioSolicitud.listarSolicitudporcodigos(i.getCod_solicitud()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//i.getResultados_examen().setNum_resultados_examenes(i.getPrecio_examen().getExamen().getNum_subexamenes());
			return i;
		}
	}
	

public List<Examen_solicitado> examenes_solicitados(int cod_solicitud){
	System.out.println("esa"+cod_solicitud);
	//String sql="select se.cod_sol_exam, se.cod_solicitud, se.cod_examen, se.estado, se.fecha, se.precio, se.cedula_usuario, se.cod_precio_examen from solicitud s, sol_exam se where s.cod_solicitud=se.cod_solicitud  and se.cod_solicitud="+cod_solicitud+" ;";
	String sql="select *  from sol_exam so where so.cod_solicitud="+cod_solicitud+" and  so.cod_sol_exam=(select max(cod_sol_exam) from sol_exam  where cod_precio_examen=so.cod_precio_examen and cod_solicitud=so.cod_solicitud) ";
	
	return  db.query(sql,  new Examen_solicitadosRowMapper());
}

public List<Examen_solicitado> examenes_solicitados_de_paciente_por_area(String cedula, String area, String caracter_nombre_examen, String fecha_solicitud, String fecha_inicio, String fecha_fin, String estado_solicitud){
String sql="";


System.out.println("estado_solicitud"+estado_solicitud);
if(!fecha_inicio.equals("") && !fecha_fin.equals("")) {
	System.out.println("este la fe inicio"+fecha_inicio);
	java.sql.Date fecha_in=java.sql.Date.valueOf(fecha_inicio);

	java.sql.Date fecha_final=java.sql.Date.valueOf(fecha_fin);
	sql="select soe.cod_solicitud, soe.estado, soe.fecha, soe.cedula_usuario, soe.cod_sol_exam, soe.cod_precio_examen from  solicitud s, sol_exam soe, precio_examen pre_ex, examen ex, area a where a.cod_area=ex.cod_area  and soe.cod_sol_exam=(select max(cod_sol_exam)  from sol_exam  where cod_precio_examen=soe.cod_precio_examen and s.cod_solicitud=cod_solicitud) and soe.cod_precio_examen=pre_ex.cod_precio_examen and pre_ex.cod_examen=ex.cod_examen and  s.cod_solicitud=soe.cod_solicitud and a.nombre ilike '%"+area+"%' and s.cedula_paciente ilike '%"+cedula+"%' and ex.nombre ilike '%"+caracter_nombre_examen+"%' and (s.fecha>='"+fecha_in+"' and s.fecha<='"+fecha_final+"') and  s.estado_solicitud ilike '%"+estado_solicitud+"%' order by s.fecha desc";

}


if(!fecha_solicitud.equals("")) {
	java.sql.Date fecha=java.sql.Date.valueOf(fecha_solicitud);
	System.out.println(fecha_solicitud);
	sql="select soe.cod_solicitud, soe.estado, soe.fecha, soe.cedula_usuario, soe.cod_sol_exam, soe.cod_precio_examen from  solicitud s, sol_exam soe, precio_examen pre_ex, examen ex, area a where a.cod_area=ex.cod_area  and soe.cod_sol_exam=(select max(cod_sol_exam)  from sol_exam  where cod_precio_examen=soe.cod_precio_examen and s.cod_solicitud=cod_solicitud) and soe.cod_precio_examen=pre_ex.cod_precio_examen and pre_ex.cod_examen=ex.cod_examen and  s.cod_solicitud=soe.cod_solicitud and a.nombre ilike '%"+area+"%' and s.cedula_paciente ilike '%"+cedula+"%' and ex.nombre ilike '%"+caracter_nombre_examen+"%' and s.fecha='"+fecha+"' and  s.estado_solicitud ilike '%"+estado_solicitud+"%' order by s.fecha desc";
}
	if(fecha_inicio.equals("") && fecha_fin.equals("") && fecha_solicitud.equals(""))
	{

System.out.println("estado_solicitud"+estado_solicitud);
		sql="select soe.cod_solicitud, soe.estado, soe.fecha, soe.cedula_usuario, soe.cod_sol_exam, soe.cod_precio_examen from  solicitud s, sol_exam soe, precio_examen pre_ex, examen ex, area a where a.cod_area=ex.cod_area  and soe.cod_sol_exam=(select max(cod_sol_exam)  from sol_exam  where cod_precio_examen=soe.cod_precio_examen and s.cod_solicitud=cod_solicitud) and soe.cod_precio_examen=pre_ex.cod_precio_examen and pre_ex.cod_examen=ex.cod_examen and  s.cod_solicitud=soe.cod_solicitud and a.nombre ilike '%"+area+"%' and s.cedula_paciente ilike '%"+cedula+"%' and ex.nombre ilike '%"+caracter_nombre_examen+"%' and  s.estado_solicitud ilike '%"+estado_solicitud+"%' order by s.fecha desc";

	}
	if(fecha_inicio.equals("") && fecha_fin.equals("") && fecha_solicitud.equals("") && cedula.equals(""))
	{
		sql="select soe.cod_solicitud, soe.estado, soe.fecha, soe.cedula_usuario, soe.cod_sol_exam, soe.cod_precio_examen from  solicitud s, sol_exam soe, precio_examen pre_ex, examen ex, area a where a.cod_area=ex.cod_area  and soe.cod_sol_exam=(select max(cod_sol_exam)  from sol_exam  where cod_precio_examen=soe.cod_precio_examen and s.cod_solicitud=cod_solicitud) and soe.cod_precio_examen=pre_ex.cod_precio_examen and pre_ex.cod_examen=ex.cod_examen and  s.cod_solicitud=soe.cod_solicitud and a.nombre ilike '%"+area+"%'  and ex.nombre ilike '%"+caracter_nombre_examen+"%' and  s.estado_solicitud ilike '%"+estado_solicitud+"%' order by s.fecha desc";

	}
	Object[] datos={area, cedula, caracter_nombre_examen};
	System.out.println(area+"    "+cedula+"  "+caracter_nombre_examen);
	//String sql="select se.cod_sol_exam, se.cod_solicitud, se.cod_examen, se.estado, se.fecha, se.precio, se.cedula_usuario, se.cod_precio_examen from solicitud s, sol_exam se where s.cod_solicitud=se.cod_solicitud  and se.cod_solicitud="+cod_solicitud+" ;";
	
	return  db.query(sql,   new Examen_solicitadoRowMapper());
}

public List<Examen_solicitado> examenes_solicitados_de_paciente(String cedula){
	Object[] datos={cedula};
	//String sql="select se.cod_sol_exam, se.cod_solicitud, se.cod_examen, se.estado, se.fecha, se.precio, se.cedula_usuario, se.cod_precio_examen from solicitud s, sol_exam se where s.cod_solicitud=se.cod_solicitud  and se.cod_solicitud="+cod_solicitud+" ;";
	String sql="select   soe.cod_solicitud, soe.estado, soe.fecha, soe.cedula_usuario, soe.cod_sol_exam, soe.cod_precio_examen from  solicitud s, sol_exam soe, precio_examen pre_ex, examen ex, area a where a.cod_area=ex.cod_area  and soe.cod_sol_exam=(select max(cod_sol_exam)  from sol_exam  where cod_precio_examen=soe.cod_precio_examen and s.cod_solicitud=cod_solicitud) and soe.cod_precio_examen=pre_ex.cod_precio_examen and pre_ex.cod_examen=ex.cod_examen and  s.cod_solicitud=soe.cod_solicitud   and s.cedula_paciente=? order by s.fecha desc ";
	
	return  db.query(sql, datos, new Examen_solicitadoRowMapper());
}

public List<Examen_solicitado> examenes_solicitados_con_resultados_modificados(int cod_solicitud){
	System.out.println("esa"+cod_solicitud);
	//String sql="select se.cod_sol_exam, se.cod_solicitud, se.cod_examen, se.estado, se.fecha, se.precio, se.cedula_usuario, se.cod_precio_examen from solicitud s, sol_exam se where s.cod_solicitud=se.cod_solicitud  and se.cod_solicitud="+cod_solicitud+" ;";
	String sql="select *  from sol_exam so where so.cod_solicitud="+cod_solicitud+" and so.estado='Actualizado' ";
	
	return  db.query(sql,  new Examen_solicitadoRowMapper());
}
public void quitarExamen(int cod_solicitud, int cod_examen){
	 System.out.println(cod_solicitud+"  "+cod_examen);
	 String sql="delete from sol_exam where cod_solicitud="+cod_solicitud+" and cod_examen="+cod_examen+";";
	 db.update(sql);
}

public Examen_solicitado obtener_examen_solicitado_por_codigo(int cod_sol_exam){
	Object[] datos={cod_sol_exam};
	String sql="select * from sol_exam where cod_sol_exam=?";
	return db.queryForObject(sql, datos,new Examen_solicitadoRowMapper()
			);
}
public class Examen_solicitadosRowMapper implements RowMapper<Examen_solicitado> {
	@Override
	public Examen_solicitado mapRow(ResultSet rs, int arg1) throws SQLException {
		Examen_solicitado i=new Examen_solicitado();
		i.setCod_sol_exam(rs.getInt("cod_sol_exam"));
		i.setCod_solicitud(rs.getInt("cod_solicitud"));
		//i.setCod_examen(rs.getInt("cod_examen"));
		//System.out.println(rs.getInt("cod_precio_examen"));
	i.setPrecio_examen(servicioPrecio_examen.getById(rs.getInt("cod_precio_examen")));
	i.setCod_precio_examen(rs.getInt("cod_precio_examen"));
	i.setEstado(rs.getString("estado"));
		i.setFecha(rs.getDate("fecha"));
		i.setCedula_usuario(rs.getString("cedula_usuario"));
		System.out.println("fajkjasklfjasklfjkajfkasdjfkjkadsf"+i.getCedula_usuario());
				
		if(!(i.getCedula_usuario() == null))
		{
i.setUsuario(servicioUsuario.obtener_usuario(i.getCedula_usuario()));
}
else
	
{
	i.setUsuario(new Usuario());
}
		//i.setExamen(servicioExamen.obtener_examen(i.getCod_examen()));
		//i.setNum_subexamenes(i.getPrecio_examen().getExamen().getSubexamenes().size());
//i.setEstado_resultado_examen_padre(servicioEstado_resultado_examen.Estado_resultado_examen(rs.getInt("cod_Estado_resultado_examen_padre")));
	//i.setResultados_examenes(servicioResultados_examen.obtener_resultados_examenes(i.getCod_sol_exam()));if
		if(i.getEstado().equals("Registrado")  ||  i.getEstado().equals("Actualizado") )
		{
	
i.setResultados_examen(servicioResultados_examen.obtener_resultados_examen(i.getCod_sol_exam()));
		}
	
		
		//i.getResultados_examen().setNum_resultados_examenes(i.getPrecio_examen().getExamen().getNum_subexamenes());
		return i;
	}
}
}
