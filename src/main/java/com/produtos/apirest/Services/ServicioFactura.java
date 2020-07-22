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
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;

import com.produtos.apirest.models.*;
import com.produtos.apirest.Services.ServicioPersona.PersonaRowMapper;
import com.produtos.apirest.Services.ServicioUsuario.UsuarioRowMapper;
import com.produtos.apirest.Services.*;
import com.produtos.apirest.varios.*;

import Utilidades.ControlCode;


@Service
public class ServicioFactura extends Conexion {
	@Autowired
	ServicioDosificacion servicioDosificacion;
	public class mapearFacturaVenta implements RowMapper<Factura>{
		@Override
		public Factura mapRow(ResultSet rs, int arg1) throws SQLException {
			Factura modelo = new Factura();
			modelo.setCod_solicitud(rs.getInt("cod_solicitud"));
			modelo.setCod_factura( rs.getInt("cod_factura") );
			System.out.println("dos");
			modelo.setCod_control( rs.getString( "cod_control" ) );
		modelo.setCod_dosificacion(rs.getInt("cod_dosificacion"));
		modelo.setDosificacion(servicioDosificacion.verDosificacion(modelo.getCod_dosificacion()));
			return modelo;
		}
	}
	
	public Factura buscarFacturaDeSolicitud(int cod_solicitud){
		Factura f=new Factura();
		String xsql="select * from factura where cod_solicitud=?";
		try {
			f=db.queryForObject(xsql,new mapearFacturaVenta(),cod_solicitud);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return f;
	}
	public Factura generarFactura(Solicitud s) {

		try {
			ControlCode controlCode = new ControlCode();
			Dosificacion dosificacion=servicioDosificacion.listarDosificacionVigente();
			String autorizacion = dosificacion.getAutorizacion();

			String numeroFactura = Integer.toString(NumeroDeFacturas()+1);
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date(System.currentTimeMillis());
			String fecha = formatter.format(date);
			String llave = dosificacion.getLlave();
			String costo_total=String.valueOf(s.getCostoTotal());
			System.out.println(autorizacion+" "+numeroFactura+" "+s.getCedula_paciente()+" "+fecha+" "+s.getCostoTotal()+" "+llave);
			//genera codigo de control
			String cc = controlCode.generate(dosificacion.getAutorizacion(), numeroFactura, s.getCedula_paciente(), fecha.replace("-", ""), costo_total, llave);
    Object[] datos= {cc, dosificacion.getCod_dosificacion(), s.getCod_solicitud()};
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //db.update("insert into factura(cod_control, cod_dosificacion, cod_solicitud) values(?,?,?)", datos);
		//return db.queryForObject("select * from factura where cod_factura="+numeroFactura+";", new mapearFacturaVenta());
        return buscarFacturaDeSolicitud(s.getCod_solicitud());
	}
	public int NumeroDeFacturas(){
		String xsql="select max(cod_factura) as id_factura	from factura";
		return db.queryForObject(xsql,Integer.class);
	}
}
