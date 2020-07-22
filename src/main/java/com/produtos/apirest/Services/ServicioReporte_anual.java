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
public class ServicioReporte_anual extends Conexion {
	int sum=0;
	@Autowired
	ServicioPrecio_de_examen servicioPrecio_examen;
	@Autowired
	ServicioInstitucion servicioInstitucion;
	@Autowired
	ServicioReporte_mensual servicioReporte_mensual;

	String sql;
	public class Reporte_anualRowMapper implements RowMapper<Reporte_anual> {
		String meses[]={"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto","Septiembre"};
		@Override
		public Reporte_anual mapRow(ResultSet rs, int arg1) throws SQLException {
			Reporte_anual r=new Reporte_anual();
			//r.setPrecio_examen(servicioPrecio_examen.getById(rs.getInt("cod_precio_examen")));
			r.setInstitucion(servicioInstitucion.buscarPorCodigo(rs.getString("cod_institucion")));
		
			r.setAnio(rs.getInt("anio"));
	r.setMonto_total(rs.getDouble("monto_total"));
		r.setNro_prestaciones_total(rs.getInt("nro_prestaciones_total"));
		r.setReportes_mensuales(servicioReporte_mensual.listarTodosLosMesesDeAcuerdoALaInstitucion(r.getInstitucion().getCod_institucion(), r.getAnio()));
			return r;
		}
	}


	public  Reporte_anual buscar(Reporte_anual re){
		Reporte_anual reporte =new Reporte_anual();
		Object datos[]={re.getInstitucion().getCod_institucion(), re.getAnio()};
		String sql="SELECT  pe.cod_institucion,extract(year from s.fecha) as anio ,count(soe.cod_precio_examen) as nro_prestaciones_total, sum(pe.costo) as monto_total   FROM sol_exam soe, solicitud s, precio_examen pe      WHERE pe.cod_precio_examen=soe.cod_precio_examen  and   soe.cod_sol_exam=(select max(cod_sol_exam) from sol_exam  where cod_precio_examen=soe.cod_precio_examen and cod_solicitud=soe.cod_solicitud) and soe.cod_solicitud=s.cod_solicitud and pe.cod_institucion=?  and extract(year from s.fecha)=?  group by 1, 2 ";

		try {
			reporte=db.queryForObject(sql,datos, new Reporte_anualRowMapper());
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  reporte;
		
		
		 
	}

}