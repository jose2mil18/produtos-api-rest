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
import com.produtos.apirest.models.Usuario;
import com.produtos.apirest.models.*;
import com.produtos.apirest.Services.*;
import com.produtos.apirest.Services.ServicioUsuario.UsuarioRowMapper;
import com.produtos.apirest.Services.ServicioMenu;
import com.produtos.apirest.varios.*;

import com.produtos.apirest.Services.ServicioPaciente;
@Service
public class ServicioValor extends Conexion {

	@Autowired
	Valor valor;
	@Autowired
	ServicioValor servicioValor;
	String sql;
	public class ValorRowMapper implements RowMapper<Valor> {
		@Override
		public Valor mapRow(ResultSet rs, int arg1) throws SQLException {
		Valor v=new Valor();
		v.setCod(rs.getInt("cod"));
		
		v.setCod_resultados_examen(rs.getInt("cod_resultados_examen"));

		System.out.println(v.getCod()+"abc"+ v.getCod_resultados_examen());
		v.setValor(new String(servicioValor.valor(v.getCod(), v.getCod_resultados_examen())));
		System.out.println(v.getValor());
		return v;
		}
	}
	



public List<Valor> listavalores(int cod_resultados_examen){
	
	String sql="select v.cod_resultados_examen,v.cod, re_po.valor  from resultados_por_defecto re_po, resultados_examen re, valor v, resultados_examen_por_defecto re_ex where re_po.cod_resultados_por_defecto=re_ex.cod_resultados_por_defecto and v.cod_resultados_examen=re.cod_resultados_examen and v.cod_resultados_examen="+cod_resultados_examen+" and re_ex.cod=v.cod;";
	return  db.query(sql, new ValorRowMapper());
}

public String valor(int cod, int cod_resultados_examen){
	Object datos[]={cod_resultados_examen, cod};
	String sql="select   re_po.valor  from resultados_por_defecto re_po, resultados_examen re, valor v, resultados_examen_por_defecto re_ex where re_po.cod_resultados_por_defecto=re_ex.cod_resultados_por_defecto and v.cod_resultados_examen=re.cod_resultados_examen and v.cod_resultados_examen=? and re_ex.cod=v.cod and v.cod=?;";
	System.out.println("kl");
	String a="";
	try {
		a=db.queryForObject(sql, datos, String.class);
	} catch (DataAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return  a;
}


}