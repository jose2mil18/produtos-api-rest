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

import com.produtos.apirest.models.*;
import com.produtos.apirest.Services.ServicioPaciente;
import com.produtos.apirest.Services.ServicioExamen.ExamenRowMapper;
import com.produtos.apirest.Services.ServicioMenu.MenuRowMapper;
import com.produtos.apirest.Services.ServicioUsuario.UsuarioRowMapper;
import com.produtos.apirest.Services.ServicioMenu;
import com.produtos.apirest.varios.*;

import com.produtos.apirest.Services.ServicioPaciente;
@Service
public class ServicioPrecio_de_examen extends Conexion {

	@Autowired
	Precio_examen precio_examen;
	@Autowired
	ServicioExamen servicioExamen;
	@Autowired
	ServicioInstitucion servicioinstitucion;
	String sql;
	public class Precio_examenRowMapper implements RowMapper<Precio_examen> {
		@Override
		public Precio_examen mapRow(ResultSet rs, int arg1) throws SQLException {
			Precio_examen i=new Precio_examen();
			i.setCod_institucion(rs.getString("cod_institucion"));
			i.setCod_examen(rs.getInt("cod_examen"));
			i.setCosto(rs.getFloat("costo"));
		i.setEstado(rs.getBoolean(("estado")));
i.setCod_precio_examen(rs.getInt("cod_precio_examen"));
			i.setExamen(servicioExamen.obtener_examen(i.getCod_examen()));
	//i.setInstitucion_padre(servicioinstitucion.Institucion(rs.getInt("cod_institucion_padre")));
		
			return i;
		}
	}
	public List<Precio_examen> buscarPrecioDeExamen(int cod_examen){
		String sql="select pe.estado, pe.cod_precio_examen, pe.cod_institucion, pe.cod_examen, pe.costo from precio_examen pe, examen e where pe.cod_examen=e.cod_examen and pe.cod_examen="+cod_examen+" and estado=true;";
		return  db.query(sql,new Precio_examenesRowMapper());
		
		
		 
	}
	public Precio_examen buscarPorCodigo(int cod_precio_examen){
		
		//int a=Integer.parseInt(cod_examen);

		Object[] datos={cod_precio_examen};
		String sql="select * from precio_examen where cod_precio_examen=? and estado=true;";
		return  db.queryForObject(sql, datos,new Precio_examenRowMapper());
		
		
		
		 
	}
	public List<Precio_examen> listarporareacodinstitucion(Precio_examen pe){
		
		Object[] datos={pe.getCod_institucion(), pe.getExamen().getArea().getCod_area()};
		
		String sql="select e.cod_examen, e.nombre, e.unidades,e.cod_area, pe.cod_precio_examen, pe.cod_institucion, pe.costo, pe.estado from examen e, precio_examen pe where e.cod_examen=pe.cod_examen and pe.cod_institucion=? and e.cod_area=?   and (e.cod_examen not in (select cod_subexamen from examen_subexamen)) and estado=true";
		return  db.query(sql,datos, new Precio_examenRowMapper());
		
		
		 
	}
	
	public void registrar(Precio_examen p) {
		Object[] datos3={p.getCod_institucion(),p.getCod_examen(), p.getCosto()};
		String sql3="insert into precio_examen(cod_institucion, cod_examen, costo) values(?,?, ?)";
		db.update(sql3,datos3);
		
	}
	public void modificar(Precio_examen p) {
		Object[] datos3={p.getCod_institucion(), p.getCosto(), p.getEstado(), p.getCod_examen(), p.getCod_precio_examen()};
		String sql3="update precio_examen set cod_institucion=?, costo=?, estado=? where cod_examen=? and cod_precio_examen=? ";
		db.update(sql3,datos3);
		
	}
	public void agregarPreciosAExamen(Examen e) {
	for(Precio_examen p:e.getPrecios()){
		p.setCod_examen(e.getCod_examen());
			if(p.getCod_precio_examen()!=0)
			{
		modificar(p);
			}
			else {
				registrar(p);
			}
			}
	}
	public class Precio_examenesRowMapper implements RowMapper<Precio_examen> {
		@Override
		public Precio_examen mapRow(ResultSet rs, int arg1) throws SQLException {
			Precio_examen i=new Precio_examen();
			i.setCod_institucion(rs.getString("cod_institucion"));
			i.setCod_examen(rs.getInt("cod_examen"));
			i.setCosto(rs.getFloat("costo"));

			i.setEstado(rs.getBoolean(("estado")));
i.setCod_precio_examen(rs.getInt("cod_precio_examen"));
i.setInstitucion(servicioinstitucion.buscarPorCodigo(i.getCod_institucion()));
			//i.setExamen(servicioExamen.obtener_examen(i.getCod_examen()));
	//i.setInstitucion_padre(servicioinstitucion.Institucion(rs.getInt("cod_institucion_padre")));
		
			return i;
		}
	}
}
