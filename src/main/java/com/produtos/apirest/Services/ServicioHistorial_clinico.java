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
import com.produtos.apirest.Services.ServicioUsuario.UsuarioRowMapper;
import com.produtos.apirest.Services.ServicioMenu;
import com.produtos.apirest.varios.*;
import com.produtos.apirest.Services.*;

import com.produtos.apirest.Services.ServicioPaciente;
@Service
public class ServicioHistorial_clinico extends Conexion {
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
	ServicioPaciente servicioPaciente;
	@Autowired
	ServicioArea servicioArea;
	@Autowired
	ServicioExamen_solicitado servicioExamen_solicitado;
	@Autowired
ServicioResultados_examen servicioResultados_examen;
	
	String sql;
	public class Historial_clinicoRowMapper implements RowMapper<Historial_clinico> {
		@Override
		public Historial_clinico mapRow(ResultSet rs, int arg1) throws SQLException {
			Historial_clinico i=new Historial_clinico();
			
		return i;
		}
	}
	


public List<Historial_clinico> examenes_solicitados_de_paciente_por_area(String cedula, String area){
	Object[] datos={area, cedula};
	System.out.println(area+"    "+cedula);
	//String sql="select se.cod_sol_exam, se.cod_solicitud, se.cod_examen, se.estado, se.fecha, se.precio, se.cedula_usuario, se.cod_precio_examen from solicitud s, sol_exam se where s.cod_solicitud=se.cod_solicitud  and se.cod_solicitud="+cod_solicitud+" ;";
	String sql="select soe.cod_solicitud, soe.estado, soe.fecha, soe.cedula_usuario, soe.cod_sol_exam, soe.cod_precio_examen from  solicitud s, sol_exam soe, precio_examen pre_ex, examen ex, area a where a.cod_area=ex.cod_area  and soe.cod_sol_exam=(select max(cod_sol_exam)  from sol_exam  where cod_precio_examen=soe.cod_precio_examen and s.cod_solicitud=cod_solicitud) and soe.cod_precio_examen=pre_ex.cod_precio_examen and pre_ex.cod_examen=ex.cod_examen and  s.cod_solicitud=soe.cod_solicitud and a.nombre='Serologia' and s.cedula_paciente='7777777'";
	
	return  db.query(sql,  new Historial_clinicoRowMapper());
}
public List<Historial_clinico> examenes_solicitados(){
	
	//String sql="select se.cod_sol_exam, se.cod_solicitud, se.cod_examen, se.estado, se.fecha, se.precio, se.cedula_usuario, se.cod_precio_examen from solicitud s, sol_exam se where s.cod_solicitud=se.cod_solicitud  and se.cod_solicitud="+cod_solicitud+" ;";
	String sql="	 select   soe.cod_sol_exam, s.cod_solicitud from  solicitud s, sol_exam soe, precio_examen pre_ex, examen ex, area a where a.cod_area=ex.cod_area  and soe.cod_sol_exam=(select max(cod_sol_exam)  from sol_exam  where cod_precio_examen=soe.cod_precio_examen and s.cod_solicitud=cod_solicitud) and soe.cod_precio_examen=pre_ex.cod_precio_examen and pre_ex.cod_examen=ex.cod_examen and  s.cod_solicitud=soe.cod_solicitud   order by s.fecha desc";
	
	return  db.query(sql,  new Historial_clinicoRowMapper());
}

}