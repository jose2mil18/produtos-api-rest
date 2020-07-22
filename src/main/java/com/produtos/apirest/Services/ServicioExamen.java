
package com.produtos.apirest.Services;
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

import com.produtos.apirest.Services.ServicioUsuario.UsuarioRowMapper;
import com.produtos.apirest.models.*;
import com.produtos.apirest.Services.*;
import com.produtos.apirest.Services.ServicioResultados_examen;
import com.produtos.apirest.varios.*;

@Service
public class ServicioExamen extends Conexion {
	@Autowired
	Examen examen;
	@Autowired
	ServicioExamen servicioexamen;
	@Autowired
	ServicioValor_referencia servicioValor_referencia;
	@Autowired
	ServicioArea servicioArea;
	@Autowired
	Solicitud solicitud;
	@Autowired
	
	ServicioPrecio_de_examen servicioPrecio_examen;
	@Autowired
	ServicioResultados_por_defecto servicioResultados_por_defecto;
	
	public class ExamenRowMapper implements RowMapper<Examen> {
		@Override
		public Examen mapRow(ResultSet rs, int arg1) throws SQLException {
		Examen e=new Examen();
			e.setCod_examen(rs.getInt("cod_examen"));
			e.setNombre(rs.getString("nombre"));
			
	
			e.setCod_area(rs.getInt("cod_area"));
			e.setUnidades(rs.getString("unidades"));
			e.setSubexamenes(listarSubExamenes(rs.getInt("cod_examen")));
			e.setNum_subexamenes(e.getSubexamenes().size());
		e.setPrecios(servicioPrecio_examen.buscarPrecioDeExamen(e.getCod_examen()));
		
			//e.sebbtResultados_examen(servicioresultados_examen.obtener_resultados_examen_solicitud(rs.getInt("cod_examen")));
		e.setResultados_por_defecto(servicioResultados_por_defecto.listarResultadosPorDefectoDeExamen(rs.getInt("cod_examen")));
		try {
			e.setArea(servicioArea.buscarAreaDeExamen(rs.getInt("cod_examen")));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		e.setValores_referencia(servicioValor_referencia.listarValoresDeReferenciaDeExamen(e.getCod_examen()));
		
		String val="";
		for(Valor_referencia v:e.getValores_referencia())
		{
			val=val+v.getTipo_persona()+" "+v.getValor_inicial()+"-"+v.getValor_final()+" ";
		}
		e.setValor_referencia(val);
		
		return e;
		}
	}
	public List<Examen> listar(String area, String nombre_examen){
		String sql="select e.cod_examen, e.nombre,  e.cod_area, e.unidades from examen e, area a where e.cod_area=a.cod_area and (e.cod_examen not in (select cod_subexamen from examen_subexamen)) and a.nombre ilike '%"+area+"%' and e.nombre ilike '%"+nombre_examen+"%'  order by a.cod_area";
		return  db.query(sql, new ExamenRowMapper());
		
		
		 
	}
	
	public List<Examen> listarSubExamenes(int cod_examen){
	
		String sql=" select s.cod_examen, s.nombre,  s.cod_area, s.unidades, es.estado from examen e,examen s, examen_subexamen es where  e.cod_examen=es.cod_examen and es.cod_subexamen=s.cod_examen and e.cod_examen="+cod_examen+" and es.estado=true order by cod_examen";
		return  db.query(sql, new ExamenesRowMapper());
		
		
		 
	}
	
	public List<Examen> listartodosSubExamenes(String caracter, int cod_area){
		
		String sql="select distinct s.cod_examen, s.nombre,  s.cod_area, s.unidades, es.estado from examen e,examen s, examen_subexamen es where  e.cod_examen=es.cod_examen and es.cod_subexamen=s.cod_examen and s.nombre ilike '%"+caracter+"%' and s.cod_area="+cod_area+" and es.estado=true  order by cod_examen";
		return  db.query(sql, new ExamenesRowMapper());
		
		
		 
	}
	public List<Examen> listartodosSubExamenesSinSubexamenes(String caracter, int cod_area){
		
		String sql=" select distinct s.cod_examen, s.nombre,  s.cod_area, s.unidades, es.estado from examen e,examen s, examen_subexamen es where  e.cod_examen=es.cod_examen and es.cod_subexamen=s.cod_examen and s.nombre ilike '%"+caracter+"%' and s.cod_area="+cod_area+" and es.estado=true and  es.cod_subexamen not in (select cod_examen from examen_subexamen)    order by cod_examen";
		return  db.query(sql, new ExamenesRowMapper());
		
		
		 
	}
	public Examen obtener_examen(int cod_examen){
		
		//int a=Integer.parseInt(cod_examen);

		Object[] datos={cod_examen};
		String sql="select * from examen where cod_examen=?;";
		return  db.queryForObject(sql, datos,new ExamenRowMapper());
		
		
		
		 
	}
	public void regitrar_subexamen(Examen su, int cod_examen) {
		Object[] datos6={su.getNombre(), su.getCod_area(), su.getUnidades()};
		String sql6="insert into examen(nombre, cod_area, unidades) values(?, ?, ?)";
		db.update(sql6, datos6);
		su.setCod_examen(db.queryForObject("select max(cod_examen) from examen", Integer.class));
	      servicioValor_referencia.agregarValoresDeReferenciaAExamen(su);
		Object[] datos7={cod_examen, su.getCod_examen()};
		String sql7="insert into examen_subexamen values(?,?)";
		db.update(sql7, datos7);
	}
	public void agregarSubexamenes(Examen e)
	{
		for(Examen su: e.getSubexamenes()){
			su.setCod_area(e.getCod_area());
              if(su.getSubexamenes().size() !=0) {
            	  agregarSubexamenes(su);
              }
              
              if(su.getCod_examen()!=0)
              {
			Object[] datos6={su.getNombre(), e.getCod_area(), su.getUnidades(), su.getCod_examen()};
			String sql6="update examen set nombre=?, cod_area=?, unidades=? where cod_examen=?";
			db.update(sql6, datos6);
			//su.setCod_examen(db.queryForObject("select max(cod_examen) from examen", Integer.class));
			servicioValor_referencia.agregarValoresDeReferenciaAExamen(su);
			
			int subexamencambiado=db.queryForObject("select count(*) from examen_subexamen where cod_examen="+e.getCod_examen()+" and cod_subexamen="+su.getCod_examen()+"", Integer.class);
			
			   if(subexamencambiado==1)
			     {
			   Object[] datos7={su.getEstado(), e.getCod_examen(), su.getCod_examen()};
			   System.out.println("estado de subexamen"+su.getEstado());
			   String sql7="update examen_subexamen set estado=? where cod_examen=? and cod_subexamen=? ";
			   db.update(sql7, datos7);
			       }
			     else 
			    {
				Object[] datos8={e.getCod_examen(), su.getCod_examen()};
				System.out.println("se añadio el subexman registrado en el sitema al examen nuevo"+su.getCod_examen());
				String sql8="insert into examen_subexamen values(?,?)";
				db.update(sql8, datos8);
			     }
              
              }
              else {
            	  System.out.println("este es elcodigodeexamenpadre"+e.getCod_examen());
            	  regitrar_subexamen(su,e.getCod_examen());
              }
			
		}
	}
	
public Examen registrar(Examen e){
		
		//int a=Integer.parseInt(cod_examen);

		Object[] datos1={e.getNombre(), e.getCod_area(), e.getUnidades()};
		String sql1="insert into examen(nombre, cod_area, unidades) values(?, ?, ?)";
		db.update(sql1, datos1);
		e.setCod_examen(db.queryForObject("select max(cod_examen) from examen", Integer.class));
		servicioValor_referencia.agregarValoresDeReferenciaAExamen(e);//
servicioPrecio_examen.agregarPreciosAExamen(e);
agregarSubexamenes(e);

		String sql="select * from examen where cod_examen="+e.getCod_examen()+";";
		return  db.queryForObject(sql, new ExamenRowMapper());
		
		
		
		 
	}

public Examen modificar(Examen e){
	Object[] datos1={e.getNombre(), e.getCod_area(), e.getUnidades(), e.getCod_examen()};
	String sql1="update examen set nombre=?, cod_area=?, unidades=? where cod_examen=? ";
	db.update(sql1, datos1);
	servicioValor_referencia.agregarValoresDeReferenciaAExamen(e);//
servicioPrecio_examen.agregarPreciosAExamen(e);
agregarSubexamenes(e);

	String sql="select * from examen where cod_examen="+e.getCod_examen()+";";
	return  db.queryForObject(sql, new ExamenRowMapper());
		
		
		 
	}
	
	public void actualizavalorreferencia(String valor_referencia,String cod_examen){
		
		//int a=Integer.parseInt(cod_examen);
System.out.println(valor_referencia+" "+cod_examen);
		Object[] datos={valor_referencia,Integer.parseInt(cod_examen)};
		String sql="update examen set valor_referencia=? where cod_examen=?";
		db.update(sql,datos);
		
		
		
		
		 
	}
	public List<Examen> listarporarea(String cod_area){
		
		int cod_areanumero=Integer.parseInt(cod_area);
		String sql="select e.cod_examen, e.nombre,  e.cod_area, e.unidades from examen e, area a where e.cod_area=a.cod_area and a.cod_area="+cod_areanumero+" and (e.cod_examen not in (select cod_subexamen from examen_subexamen))   ";
		return  db.query(sql, new ExamenRowMapper());
		
		
		 
	}
	public List<Examen> listarporareacodinstitucion(Precio_examen pe){
		
		Object[] datos={pe.getCod_institucion(), pe.getExamen().getArea().getCod_area()};
		String sql="select e.cod_examen, e.nombre, e.unidades,e.cod_area from examen e, precio_examen pe where e.cod_examen=pe.cod_examen and pe.cod_institucion=? and e.cod_area=?   and (e.cod_examen not in (select cod_subexamen from examen_subexamen)) ";
		return  db.query(sql,datos, new ExamenRowMapper());
		
		
		 
	}
	
	public String obtener_nombre_examen(int cod_solicitud){

		Object[] datos={cod_solicitud};
		
		String sql="select e.nombre from examen e, sol_exam soe, solicitud s where s.cod_solicitud=soe.cod_solicitud and soe.cod_examen=e.cod_examen and soe.cod_solicitud=?;";

		return db.queryForObject(sql, datos, String.class);
	}

	public String obtener_nombre_examen_dado_codigo(String cod_solicitud){
int codigo=Integer.parseInt(cod_solicitud);
		Object[] datos={codigo};
		
		String sql="select nombre from examen where cod_examen=?;";

		return db.queryForObject(sql, datos, String.class);
	}
	public int obtener_cod_examen_de_subexamen(int cod_subexamen){

				Object[] datos={cod_subexamen};
				
				String sql="select e.cod_examen from examen e, examen_subexamen es, subexamen s where e.cod_examen=es.cod_examen and s.cod_subexamen=es.cod_subexamen and s.cod_subexamen=?;";

				return db.queryForObject(sql, datos, Integer.class).intValue();
			}
	

	public List<Examen> listarexamenesdesolicitud(int cod_solicitud){
		String sql="select e.cod_examen, e.nombre, e.cod_area, e.unidades  from examen e, sol_exam es, solicitud s where e.cod_examen=es.cod_examen and es.cod_solicitud=s.cod_solicitud and s.cod_solicitud="+cod_solicitud+" ";
	
		return  db.query(sql, new ExamenRowMapper());
		
		
		 
	}
	 public void quitarExamen(int cod_solicitud, int cod_examen){
		 System.out.println(cod_solicitud+"  "+cod_examen);
		 String sql="delete from sol_exam where cod_solicitud="+cod_solicitud+" and cod_examen="+cod_examen+";";
		 db.update(sql);
	 }
	 

		public class ExamenesRowMapper implements RowMapper<Examen> {
			@Override
			public Examen mapRow(ResultSet rs, int arg1) throws SQLException {
			Examen e=new Examen();
				e.setCod_examen(rs.getInt("cod_examen"));
				e.setNombre(rs.getString("nombre"));
				
		e.setEstado(rs.getBoolean("estado"));
				e.setCod_area(rs.getInt("cod_area"));
				e.setUnidades(rs.getString("unidades"));
				e.setSubexamenes(listarSubExamenes(rs.getInt("cod_examen")));
				e.setNum_subexamenes(e.getSubexamenes().size());
			//e.setPrecios(servicioPrecio_examen.listarPrecios(e.getCod_examen()));
			
				//e.sebbtResultados_examen(servicioresultados_examen.obtener_resultados_examen_solicitud(rs.getInt("cod_examen")));
			e.setResultados_por_defecto(servicioResultados_por_defecto.listarResultadosPorDefectoDeExamen(rs.getInt("cod_examen")));
			try {
				e.setArea(servicioArea.buscarAreaDeExamen(rs.getInt("cod_examen")));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.setValores_referencia(servicioValor_referencia.listarValoresDeReferenciaDeExamen(e.getCod_examen()));
			return e;
			}
		}
	
}