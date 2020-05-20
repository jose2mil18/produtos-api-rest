package com.produtos.apirest.Services;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

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

import com.produtos.apirest.models.*;
import com.produtos.apirest.Services.ServicioPaciente;
import com.produtos.apirest.Services.ServicioPrecio_examen.Precio_examenRowMapper;
import com.produtos.apirest.Services.ServicioUsuario.UsuarioRowMapper;
import com.produtos.apirest.Services.ServicioMenu;
import com.produtos.apirest.varios.*;

import com.produtos.apirest.Services.ServicioPaciente;
@Service
public class ServicioReporte_examen_mensual extends Conexion {
	int sum=0;
	@Autowired
	ServicioPrecio_examen servicioPrecio_examen;
	@Autowired
	ServicioReporte_mensual servicioReporte_anual;
	@Autowired
	ServicioInstitucion servicioInstitucion;
	String sql;
	public class Reporte_examen_mensualRowMapper implements RowMapper<Reporte_examen_mensual> {
		@Override
		public Reporte_examen_mensual mapRow(ResultSet rs, int arg1) throws SQLException {
			Reporte_examen_mensual r=new Reporte_examen_mensual();
			r.setNro_prestaciones(rs.getInt("count"));
			r.setPrecio_examen(servicioPrecio_examen.getById(rs.getInt("cod_precio_examen")));
			r.setInstitucion(servicioInstitucion.Institucion(rs.getString("cod_institucion")));
			r.setMes(rs.getInt("mes"));
			r.setAnio(rs.getInt("anio"));
			float costo_total_examen=r.getPrecio_examen().getCosto()*r.getNro_prestaciones();
			r.setCosto_total_examen(costo_total_examen);
			//r.setNro_total_prestaciones();
		sum+=1;
		System.out.println(sum);
			return r;
		}
	}
	public List<Reporte_examen_mensual> reportes(Reporte_examen_mensual re){
		Object datos[]={re.getInstitucion().getCod_institucion(), re.getMes(), re.getAnio()};
		String sql="SELECT  soe.cod_precio_examen ,pe.cod_institucion, extract(month from s.fecha) as mes,extract(year from s.fecha) as anio ,count(soe.cod_precio_examen)  FROM sol_exam soe, solicitud s, precio_examen pe     WHERE pe.cod_precio_examen=soe.cod_precio_examen and soe.cod_solicitud=s.cod_solicitud and pe.cod_institucion=?  and extract(month from s.fecha)=? and extract(year from s.fecha)=?  group by 1, 2, 3, 4	";
				return  db.query(sql,datos, new Reporte_examen_mensualRowMapper());
		
		
		 
	}
	
	public List<Reporte_examen_mensual> reportes_mes(String cod_institucion, int mes, int anio){
		Object datos[]={cod_institucion, mes, anio};	String sql="SELECT  soe.cod_precio_examen ,pe.cod_institucion, extract(month from s.fecha) as mes,extract(year from s.fecha) as anio ,count(soe.cod_precio_examen)  FROM sol_exam soe, solicitud s, precio_examen pe     WHERE  pe.cod_precio_examen=soe.cod_precio_examen and   soe.cod_sol_exam=(select max(cod_sol_exam) from sol_exam  where cod_precio_examen=soe.cod_precio_examen and cod_solicitud=soe.cod_solicitud) and soe.cod_solicitud=s.cod_solicitud and pe.cod_institucion=?  and extract(month from s.fecha)=? and extract(year from s.fecha)=?  and  soe.cod_sol_exam=(select max(cod_sol_exam) from sol_exam  where cod_precio_examen=soe.cod_precio_examen and cod_solicitud=soe.cod_solicitud) group by 1, 2, 3, 4	";
				return  db.query(sql,datos, new Reporte_examen_mensualRowMapper());
		
		
		 
	}
	public int  Nro_total_prestaciones(String cod_insti, int me, int an){
		Object datos[]={cod_insti, me, an};
		String sql=" SELECT  count(soe.cod_precio_examen) FROM sol_exam soe, solicitud s, precio_examen pe     WHERE pe.cod_precio_examen=soe.cod_precio_examen and soe.cod_solicitud=s.cod_solicitud and pe.cod_institucion=? and extract(month from s.fecha)=? and extract(year from s.fecha)=?  ";
					return  db.queryForObject(sql,datos, Integer.class);
		
		
		 
	}

}
