package com.produtos.apirest.Services;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

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
import com.produtos.apirest.Services.ServicioPrecio_de_examen.Precio_examenRowMapper;
import com.produtos.apirest.Services.ServicioUsuario.UsuarioRowMapper;
import com.produtos.apirest.Services.ServicioMenu;
import com.produtos.apirest.varios.*;

import com.produtos.apirest.Services.ServicioPaciente;
@Service
public class ServicioReporte_examenes_solicitados extends Conexion {
	int sum=0;
	@Autowired
	ServicioPrecio_de_examen servicioPrecio_examen;
	@Autowired
	ServicioPaciente servicioPaciente;
	@Autowired
	ServicioInstitucion servicioInstitucion;
	@Autowired
	ServicioExamen_solicitado servicioExamen_solicitado;
	@Autowired
	ServicioSolicitud servicioSolicitud;
	@Autowired
	ServicioReporte_examen_mensual servicioReporte_examen_mensual;
	String sql;
	public class Reporte_examenes_solicitadosRowMapper implements RowMapper<Reporte_examenes_solicitados> {
		String meses[]={"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto","Septiembre","Octubre", "Noviembre", "Diciembre"};
	String agrupador="";
	String seleccionador="";
	String fecha_inicio="";
	String fecha_fin="";
	String nombre_area="";
		public  Reporte_examenes_solicitadosRowMapper(String agrupador, String seleccionador, String fecha_inicio, String fecha_fin, String nombre_area) {
		this.agrupador=agrupador;
		this.seleccionador=seleccionador;
		this.fecha_inicio=fecha_inicio;
		this.fecha_fin=fecha_fin;
		this.nombre_area=nombre_area;
	}
		@Override
		public Reporte_examenes_solicitados mapRow(ResultSet rs, int arg1) throws SQLException {
			Reporte_examenes_solicitados r=new Reporte_examenes_solicitados();
		
			r.setNro_prestaciones(rs.getInt("nro_prestaciones"));
	
			r.setGrupo(rs.getString(agrupador));
	
			//r.setSolicitudes(servicioSolicitud.listar_solicitudes_de_un_mismo_examen(r.getGrupo(),seleccionador));
			r.setExamenes_solicitados(servicioExamen_solicitado.listarExamenesSolicitadosDeGrupo(r.getGrupo(), seleccionador, fecha_inicio, fecha_fin, nombre_area));
			//r.setNro_total_prestaciones(nro_total_prestaciones);
			if(agrupador.equals("cedula_paciente")) {
				r.setGrupo(servicioPaciente.nombresCompletos(rs.getString(agrupador)));
			}
			return r;
		}
	}
	public List<Reporte_examenes_solicitados> buscar(String nombre_area, String fecha_inicio, String fecha_fin, String agrupador, String seleccionador, String fecha_solicitud){
		System.out.println("agrupador"+agrupador);
		System.out.println("seleccionador"+seleccionador);
		System.out.println("area"+nombre_area);
		System.out.println("fecha"+fecha_solicitud);
	List<Reporte_examenes_solicitados> r=new ArrayList<Reporte_examenes_solicitados>();
		String sql="";
	
		if(!fecha_inicio.equals("") && !fecha_fin.equals("")) {
			System.out.println("este la fe inicio"+fecha_inicio);
			System.out.println("este la fe fin"+fecha_fin);
			  java.sql.Date fecha_in=java.sql.Date.valueOf(fecha_inicio);

				java.sql.Date fecha_final=java.sql.Date.valueOf(fecha_fin);
			//seleccionador=soe.cod_precio_examen
				//agrupador cod_precio_examen
				sql="SELECT   "+seleccionador+"  ,count("+seleccionador+") as nro_prestaciones   FROM sol_exam soe, solicitud s, precio_examen pe,examen e, area a     WHERE  (s.fecha >='"+fecha_in+"' and s.fecha<='"+fecha_final+"') and  pe.cod_examen=e.cod_examen and a.cod_area=e.cod_area and a.nombre ilike '%"+nombre_area+"%' and pe.cod_precio_examen=soe.cod_precio_examen and soe.cod_solicitud=s.cod_solicitud and pe.cod_institucion ilike '%%'    and  soe.cod_sol_exam=(select max(cod_sol_exam) from sol_exam  where cod_precio_examen=soe.cod_precio_examen and cod_solicitud=soe.cod_solicitud) group by 1";
		}
		else  {
			sql="SELECT  "+seleccionador+"   ,count("+seleccionador+") as nro_prestaciones   FROM sol_exam soe, solicitud s, precio_examen pe,examen e, area a     WHERE  pe.cod_examen=e.cod_examen and a.cod_area=e.cod_area and a.nombre ilike '%"+nombre_area+"%' and pe.cod_precio_examen=soe.cod_precio_examen and soe.cod_solicitud=s.cod_solicitud and pe.cod_institucion ilike '%%'    and  soe.cod_sol_exam=(select max(cod_sol_exam) from sol_exam  where cod_precio_examen=soe.cod_precio_examen and cod_solicitud=soe.cod_solicitud) group by 1";
			
		}
		
		try {
			r=db.query(sql, new Reporte_examenes_solicitadosRowMapper(agrupador, seleccionador, fecha_inicio, fecha_fin, nombre_area));
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  r;
		
		
		 
	}


}