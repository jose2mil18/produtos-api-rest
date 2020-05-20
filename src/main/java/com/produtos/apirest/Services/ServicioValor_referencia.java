package com.produtos.apirest.Services;

import java.sql.ResultSet;
import java.util.List;
import org.springframework.jdbc.object.*;
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;
import com.produtos.apirest.varios.*;

import com.produtos.apirest.Services.ServicioPaciente;
import com.produtos.apirest.models.Examen;
import com.produtos.apirest.models.Valor_referencia;;
@Service
public class ServicioValor_referencia extends Conexion {
	@Autowired
	Valor_referencia valor_referencia;
	String sql;
	public class Valor_referenciaRowMapper implements RowMapper<Valor_referencia> {
		@Override
		public Valor_referencia mapRow(ResultSet rs, int arg1) throws SQLException {
			Valor_referencia v=new Valor_referencia();
			v.setCod_examen(rs.getInt("cod_examen"));
			v.setValor_inicial(rs.getDouble("valor_inicial"));
			v.setValor_final(rs.getDouble("valor_final"));
			if(rs.getString("tipo_persona")== null)
					{
			v.setTipo_persona("");
					}
			else
			{
			v.setTipo_persona(rs.getString("tipo_persona"));
			}
			v.setCod_valor_referencia(rs.getInt("cod_valor_referencia"));
			v.setEstado(rs.getBoolean("estado"));
			return v;
		}
	}
	

	public List<Valor_referencia> listar_valor_referencia(int cod_examen){
		String sql="select * from valor_referencia where cod_examen="+cod_examen+" and estado=true";
		return  db.query(sql,new Valor_referenciaRowMapper());
		
		 
	}
	public  List<Valor_referencia>  cambiarEstadoValorReferencia(int cod_examen, int cod_v){
		System.out.println("cod_v"+cod_v);
		String sql="update valor_referencia set estado=false where cod_examen="+cod_examen+" and cod_valor_referencia="+cod_v+";";
		db.update(sql);
		return  db.query("select * from valor_referencia where cod_examen="+cod_examen+" and estado=true;", new Valor_referenciaRowMapper());
		
		 
	}
	public void registrar(Valor_referencia v) {
		
			
		Object[] datos2={v.getCod_examen(), v.getValor_inicial(), v.getValor_final(), v.getTipo_persona()};
		String sql2="insert into valor_referencia(cod_examen, valor_inicial, valor_final, tipo_persona) values(?,?, ?, ?)";
		db.update(sql2,datos2);
		
		
	}
	public void modificarOregistrar(Examen e) {
		for(Valor_referencia v:e.getValores_referencia())
		{
			v.setCod_examen(e.getCod_examen());
			if(v.getCod_valor_referencia()!=0)
			{
		Object[] datos2={ v.getValor_inicial(), v.getValor_final(), v.getTipo_persona(),v.getEstado(), e.getCod_examen(), v.getCod_valor_referencia()};
		String sql2="update valor_referencia set valor_inicial=?, valor_final=?, tipo_persona=?, estado=? where cod_examen=? and cod_valor_referencia=?";
		db.update(sql2,datos2);
			}
			else
			{
				registrar(v);
			}
		
		}
	}

}
