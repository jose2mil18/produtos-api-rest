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
public class ServicioReporte_mensual extends Conexion {
	int sum=0;
	@Autowired
	ServicioPrecio_de_examen servicioPrecio_examen;
	@Autowired
	ServicioInstitucion servicioInstitucion;
	@Autowired
	ServicioReporte_examen_mensual servicioReporte_examen_mensual;
	String sql;
	boolean todos=false;
	public class Reporte_mensualRowMapper implements RowMapper<Reporte_mensual> {
		String meses[]={"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto","Septiembre","Octubre", "Noviembre", "Diciembre"};
		@Override
		public Reporte_mensual mapRow(ResultSet rs, int arg1) throws SQLException {
			Reporte_mensual r=new Reporte_mensual();
			//r.setPrecio_examen(servicioPrecio_examen.getById(rs.getInt("cod_precio_examen")));
			if(todos)
			{
				r.setInstitucion(new Institucion());
				System.out.println("cod_____________---institucion"+r.getInstitucion().getCod_institucion());
			}
			else
			{
				System.out.println("klppppppppppppp");
			r.setInstitucion(servicioInstitucion.buscarPorCodigo(rs.getString("cod_institucion")));
			}
			System.out.println("codigo de institucion"+r.getInstitucion().getCod_institucion());
			r.setMes(meses[rs.getInt("mes")-1]);
			r.setAnio(rs.getInt("anio"));
	r.setMonto(rs.getFloat("monto"));
			r.setNro_prestaciones(rs.getInt("nro_prestaciones"));
			//r.setNro_total_prestaciones(n"ro_total_prestaciones);
			System.out.println("mes"+rs.getInt("mes")+"anio"+r.getAnio());
			r.setReportes_examenes_mensual(servicioReporte_examen_mensual.reporteDeTodosLosExamenes(r.getInstitucion().getCod_institucion(), rs.getInt("mes"), r.getAnio()));
		
			return r;
		}
	}
	public Reporte_mensual buscar(Reporte_mensual re){
		Reporte_mensual reporte_mensual=new Reporte_mensual();
		System.out.println(re.getInstitucion().getCod_institucion()+" "+re.getMes()+" "+re.getAnio());
		System.out.println(re.getInstitucion().getCod_institucion().getClass());
		System.out.println(re.getMes().getClass());
		String cod_institucion="%"+re.getInstitucion().getCod_institucion()+"%";
		
				if(re.getInstitucion().getCod_institucion().equals(""))
				{
					todos=true;
					System.out.println("estado de todos los akjfajfñajf"+todos);
					 Object datos[]={ Integer.parseInt(re.getMes()), re.getAnio()};
						String sql="SELECT   extract(month from s.fecha) as mes,extract(year from s.fecha) as anio ,count(soe.cod_precio_examen) as nro_prestaciones, sum(pe.costo) as monto \n" + 
								"FROM sol_exam soe, solicitud s, precio_examen pe     \n" + 
								"WHERE  soe.cod_sol_exam=(select max(cod_sol_exam) from sol_exam  where cod_precio_examen=soe.cod_precio_examen and cod_solicitud=soe.cod_solicitud) and pe.cod_precio_examen=soe.cod_precio_examen and soe.cod_solicitud=s.cod_solicitud  and extract(month from s.fecha)=? and\n" + 
								"extract(year from s.fecha)=? group by 1, 2";
						
					
					try {
						reporte_mensual=db.queryForObject(sql, datos, new Reporte_mensualRowMapper());
					} catch (DataAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{

					System.out.println("klppppppjkjkjkjñklpklpklp");
					todos=false;
		System.out.println("klppppppjkjkjkjñklpklpklp");

					 Object datos2[]={ re.getInstitucion().getCod_institucion(),Integer.parseInt(re.getMes()), re.getAnio()};
		String sql2=" SELECT  pe.cod_institucion, extract(month from s.fecha) as mes,extract(year from s.fecha) as anio ,count(soe.cod_precio_examen) as nro_prestaciones, sum(pe.costo) as monto FROM sol_exam soe, solicitud s, precio_examen pe     WHERE  soe.cod_sol_exam=(select max(cod_sol_exam) from sol_exam  where cod_precio_examen=soe.cod_precio_examen and cod_solicitud=soe.cod_solicitud) and pe.cod_precio_examen=soe.cod_precio_examen and soe.cod_solicitud=s.cod_solicitud and pe.cod_institucion = ?  and extract(month from s.fecha)=? and extract(year from s.fecha)=? group by 1, 2, 3";
		
		try {
			reporte_mensual=db.queryForObject(sql2, datos2, new Reporte_mensualRowMapper());
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				}
	
		return  reporte_mensual;
		
		
		 
	}
	public  Reporte_mensual monto_y_Pretaciones_Totales(String cod_insti, int me, int an){
		Object datos[]={cod_insti, me, an};
		String sql=" SELECT  pe.cod_institucion, extract(month from s.fecha) as mes,extract(year from s.fecha) as anio ,count(soe.cod_precio_examen) as nro_prestaciones, sum(pe.costo) as monto FROM sol_exam soe, solicitud s, precio_examen pe   WHERE pe.cod_precio_examen=soe.cod_precio_examen and soe.cod_solicitud=s.cod_solicitud and pe.cod_institucion=? and extract(month from s.fecha)=? and extract(year from s.fecha)=?  group by 1, 2, 3	";
		return  db.queryForObject(sql,datos, new Reporte_mensualRowMapper());
		
		
		 
	}
	public  List<Reporte_mensual> listarTodosLosMesesDeAcuerdoALaInstitucion(String cod_insti,  int an){
		todos=false;
		Object datos[]={cod_insti,  an};
		System.out.println("cod_insti"+cod_insti+"año}2"+an);
		String sql="SELECT  pe.cod_institucion, extract(month from s.fecha) as mes,extract(year from s.fecha) as anio ,count(soe.cod_precio_examen) as nro_prestaciones, sum(pe.costo) as monto   FROM sol_exam soe, solicitud s, precio_examen pe     WHERE pe.cod_precio_examen=soe.cod_precio_examen  and   soe.cod_sol_exam=(select max(cod_sol_exam) from sol_exam  where cod_precio_examen=soe.cod_precio_examen and cod_solicitud=soe.cod_solicitud)  and soe.cod_solicitud=s.cod_solicitud and pe.cod_institucion = ? and extract(year from s.fecha)=?  group by 1, 2, 3 order by mes asc";
		return  db.query(sql,datos, new Reporte_mensualRowMapper());
		
		
		 
	}
	public  List<Reporte_mensual> listarTodosLosMeses(String cod_insti,  int an){
		todos=true;
		cod_insti="%"+cod_insti+"%";
		Object datos[]={ an};
		System.out.println("este es ela ño del reporte anual"+an);
		String sql="SELECT  extract(month from s.fecha) as mes,extract(year from s.fecha) as anio ,count(soe.cod_precio_examen) as nro_prestaciones, sum(pe.costo) as monto   FROM sol_exam soe, solicitud s, precio_examen pe     WHERE pe.cod_precio_examen=soe.cod_precio_examen  and   soe.cod_sol_exam=(select max(cod_sol_exam) from sol_exam  where cod_precio_examen=soe.cod_precio_examen and cod_solicitud=soe.cod_solicitud)  and soe.cod_solicitud=s.cod_solicitud  and extract(year from s.fecha)=?  group by 1, 2 order by mes asc";
		return  db.query(sql,datos, new Reporte_mensualRowMapper());
		
		
		 
	}

}