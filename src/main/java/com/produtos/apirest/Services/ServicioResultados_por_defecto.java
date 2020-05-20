package com.produtos.apirest.Services;

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
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;

import com.produtos.apirest.Services.ServicioPersona.PersonaRowMapper;
import com.produtos.apirest.models.Resultados_por_defecto;
import com.produtos.apirest.varios.Conexion;

@Service
public class ServicioResultados_por_defecto  extends Conexion {
	@Autowired
	Resultados_por_defecto resultados_por_defecto;
	String sql;
	public class Resultados_por_defectoRowMapper implements RowMapper<Resultados_por_defecto> {
		@Override
		public Resultados_por_defecto mapRow(ResultSet rs, int arg1) throws SQLException {
			Resultados_por_defecto valores=new Resultados_por_defecto();
			valores.setCod_examen(rs.getInt("cod_examen"));
			valores.setValor(rs.getString("valor"));
			valores.setCod(rs.getInt("cod"));
			return valores;
		}
	}
	public List<Resultados_por_defecto> listar(int cod_examen, String caracter){
		//List<Resultados_por_defecto> lista=new  ArrayList<Resultados_por_defecto>();
caracter="%"+caracter+"%";
		Object[] datos={cod_examen, caracter};
		String sql="SELECT re_ex.cod, re_ex.cod_examen, re_po.valor	 FROM resultados_por_defecto re_po, resultados_examen_por_defecto re_ex, examen e where re_po.cod_resultados_por_defecto=re_ex.cod_resultados_por_defecto and  e.cod_examen=re_ex.cod_examen and e.cod_examen=? and re_po.valor ilike ?;";
		//db.query(sql, datos, new Resultados_por_defectoRowMapper());
		return  db.query(sql, datos, new Resultados_por_defectoRowMapper());
		
		
		 
	}
	
	public List<Resultados_por_defecto> lista(int cod_examen){
		
				Object[] datos={cod_examen};
				String sql="SELECT re_ex.cod, re_ex.cod_examen, re_po.valor	 FROM resultados_por_defecto re_po, resultados_examen_por_defecto re_ex, examen e where re_po.cod_resultados_por_defecto=re_ex.cod_resultados_por_defecto  and  e.cod_examen=re_ex.cod_examen and e.cod_examen=?;";

				return  db.query(sql, datos, new Resultados_por_defectoRowMapper());
				
				
				 
			}
	public Resultados_por_defecto registrar(int cod_examen, String valor){
		System.out.println(cod_examen+" 2"+valor);
		Object[] datos={valor};
		
		String sql="insert into resultados_por_defecto(valor) values(?);";

		String sql2="insert into resultados_examen_por_defecto(cod_examen, cod_resultados_por_defecto) values(?,?);";
	 try {
		db.update(sql, datos);
	} catch (DataAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("error");
	}
	 int cod_resultados_por_defecto=db.queryForObject("select cod_resultados_por_defecto from resultados_por_defecto where valor='"+valor+"';", Integer.class);

		Object[] datos2={cod_examen, cod_resultados_por_defecto};
	 db.update(sql2, datos2);
	 
		return  db.queryForObject("SELECT re_ex.cod, re_ex.cod_examen, re_po.valor	 FROM resultados_por_defecto re_po, resultados_examen_por_defecto re_ex, examen e where re_po.cod_resultados_por_defecto=re_ex.cod_resultados_por_defecto  and  e.cod_examen=re_ex.cod_examen and e.cod_examen="+cod_examen+" and re_po.cod_resultados_por_defecto="+cod_resultados_por_defecto+";", new Resultados_por_defectoRowMapper());
		
		
		 
	}
	public String obtener_ap_Posta(String coda){
		
		Object[] datos={coda};
		sql="SELECT ap FROM Postas WHERE cedula=? ";
		
		return db.queryForObject(sql, datos, String.class);
	}
public String obtener_am_Posta(String coda){
		
		Object[] datos={coda};
		sql="SELECT am FROM Postas WHERE cedula=? ";
		
		return db.queryForObject(sql, datos, String.class);
	}
public String obtener_nombre_Posta(String coda){
	
	Object[] datos={coda};
	sql="SELECT nombre FROM Postas WHERE cedula=? ";
	
	return db.queryForObject(sql, datos, String.class);
}
	
}


