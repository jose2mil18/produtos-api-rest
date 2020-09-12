
package com.produtos.apirest.Services;
import java.time.LocalDate;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;
import com.produtos.apirest.models.Resultados_examen;
import com.produtos.apirest.models.Rol;
import com.produtos.apirest.models.Solicitud;
import com.produtos.apirest.models.Usuario;
import com.produtos.apirest.models.Examen;
import com.produtos.apirest.Services.ServicioPaciente;
import com.produtos.apirest.models.*;
import com.produtos.apirest.Services.*;
import com.produtos.apirest.Services.ServicioRol.RolRowMapper;
import com.produtos.apirest.Services.ServicioUsuario.UsuarioRowMapper;
import com.produtos.apirest.Services.ServicioExamen;
import com.produtos.apirest.varios.*;

@Service
public class ServicioResultados_examen extends Conexion {

	@Autowired
	Resultados_examen resultados_examen;
	@Autowired
	ServicioResultados_por_defecto servicioResultados_por_defecto;
	@Autowired
	Valor valor;
	@Autowired
	ServicioResultados_examen servicioResultados_examen;
	@Autowired
	ServicioSolicitud servicioSolicitud;
	@Autowired
	ServicioPaciente servicioPaciente;
	@Autowired
	ServicioValor servicioValor;
	@Autowired
	ServicioExamen servicioExamen;
	
	public class Resultados_examenRowMapper implements RowMapper<Resultados_examen> {
		@Override
		public Resultados_examen mapRow(ResultSet rs, int arg1) throws SQLException {
			Resultados_examen r=new Resultados_examen();
			r.setCod_resultados_examen(rs.getInt("cod_resultados_examen"));
			System.out.println(r.getCod_resultados_examen());
		r.setCod_examen(rs.getInt("cod_examen"));
		//r.setCod_subexamen(rs.getInt("cod_subexamen"));
		r.setCod_sol_exam(rs.getInt("cod_sol_exam"));
		r.setResultados_examenes(servicioResultados_examen.obtener_resultados_examenes(r.getCod_resultados_examen()));
		r.setCod_resultados_examen_padre(rs.getInt("cod_resultados_examen_padre"));
		
			try {
				r.setExamen(servicioExamen.obtener_examen(r.getCod_examen()));
				//r.setValores(servicioValor.listavalores(r.getCod_resultados_examen()));ijoi
			} catch (Exception e) {
				// TODO: handle exceptionkl
			}
			r.setValores(servicioValor.listarValoresDelResultadoDelExamen(r.getCod_resultados_examen()));
/*
			if(r.getResultados_examenes().size()==0)
			{
			List<Resultados_examen> lista = new ArrayList<Resultados_examen>();
			for(int i=0; i<r.getExamen().getNum_subexamenes(); i++)
			{
				lista.add(new Resultados_examen());
			}
			for(Resultados_examen re: lista)
			{
				System.out.println(re.getCod_examen());
			}
			
			r.setResultados_examenes(lista);
			}
			*/
			return r;
		}
		
		
	}
	
	public Solicitud registrar(Examen_solicitado examen)
	{
		
		System.out.println("-------------------------------------------"+examen.getResultados_examen().getValores().get(0).getValor());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
		java.util.Date date=new java.util.Date();  
		System.out.println(dateFormat.format(date)); 
		java.util.Date fechaHoy=ParseFecha(dateFormat.format(date));


	int cod_resultado_examen_solicitado=0;

   if(examen.getEstado().equals("Sin Registrar"))
	{
	Object[] datos={ "Registrado", fechaHoy, examen.getCedula_usuario(), examen.getNota(),  examen.getCod_sol_exam()};
			//if(!(examen.getResultado_examen().getValores()).get(0).getValor().equals(""))
			//{
		db.update("update sol_exam set estado=?, fecha=?, cedula_usuario=?, nota=? where cod_sol_exam=?;", datos);
		
		Object[] datos2={examen.getPrecio_examen().getExamen().getCod_examen(),examen.getCod_sol_exam()};

		db.update("insert into resultados_examen(cod_examen,cod_sol_exam) values(?,?)",datos2);
		//cod_resultado_examen nivel1
		  cod_resultado_examen_solicitado=db.queryForObject("select max(cod_resultados_examen) from resultados_examen;", Integer.class);
          System.out.println(cod_resultado_examen_solicitado);
		if(examen.getPrecio_examen().getExamen().getNum_subexamenes() == 0)
		{
			  for(Valor valor:examen.getResultados_examen().getValores())
		       {
				   System.out.println(cod_resultado_examen_solicitado+"a" + valor.getCod()+"------------------------este es el valor------------------"+valor.getValor());
			     Object[] datos2_1={cod_resultado_examen_solicitado,valor.getCod()};
			  
			         if(valor.getCod()==0){
				     datos2_1[1]=servicioResultados_por_defecto.registrar(examen.getPrecio_examen().getExamen().getCod_examen(), valor.getValor()).getCod();
			         }
		             db.update("insert into valor(cod_resultados_examen,cod) values(?,?);",datos2_1);
		
		       }
		}
		else{
		for(Resultados_examen resultados_examen : examen.getResultados_examen().getResultados_examenes())
		{
		  Object[] datos3={resultados_examen.getCod_examen(), examen.getCod_sol_exam(), cod_resultado_examen_solicitado};
		
		  db.update("insert into resultados_examen(cod_examen,cod_sol_exam, cod_resultados_examen_padre) values(?,?,?)",datos3);

		       
		       
		    if(resultados_examen.getExamen().getNum_subexamenes() !=0)  
		    {
		    	
		  	  int cod_resultado_examen_solicitado2=db.queryForObject("select max(cod_resultados_examen) from resultados_examen;", Integer.class);
	             
		    	for(Resultados_examen resultados_examen2 : resultados_examen.getResultados_examenes())
				{
				  Object[] datos5={resultados_examen2.getCod_examen(), examen.getCod_sol_exam(), cod_resultado_examen_solicitado2};
				
				  db.update("insert into resultados_examen(cod_examen,cod_sol_exam, cod_resultados_examen_padre) values(?,?,?)",datos5);

				       for(Valor valor:resultados_examen2.getValores())
				       {
					   int cod_resultado_examen=db.queryForObject("select max(cod_resultados_examen) from resultados_examen;", Integer.class);
		                 Object[] datos6={cod_resultado_examen,valor.getCod()};
		                 if(valor.getCod()==0){
		   				  datos6[1]=servicioResultados_por_defecto.registrar(resultados_examen2.getExamen().getCod_examen(), valor.getValor()).getCod();
		   			  }
				       db.update("insert into valor(cod_resultados_examen,cod) values(?,?);",datos6);
				
				       }
				       
				  
				       
				       
			}
		    	
		    	
		    }
		    else
		    {
		    	for(Valor valor:resultados_examen.getValores())
			       {
				   int cod_resultado_examen=db.queryForObject("select max(cod_resultados_examen) from resultados_examen;", Integer.class);
	                 Object[] datos4={cod_resultado_examen,valor.getCod()};
	                 if(valor.getCod()==0){
		   				  datos4[1]=servicioResultados_por_defecto.registrar(resultados_examen.getExamen().getCod_examen(), valor.getValor()).getCod();
		   			  }
			       db.update("insert into valor(cod_resultados_examen,cod) values(?,?);",datos4);
			
			       }
		    }
		       
		       
		}
		
	}
}


System.out.println("-----------------------------------------------bientio------------------------------------------------------------------------------------------");
		return servicioSolicitud.buscarPorCodigo(examen.getCod_solicitud());
		
	}	
	public Solicitud modificar(Examen_solicitado examen)
	{
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
		java.util.Date date=new java.util.Date();  
		System.out.println(dateFormat.format(date)); 
		java.util.Date fechaHoy=ParseFecha(dateFormat.format(date));


	int cod_resultado_examen_solicitado=0;

   
	Object[] datos={ examen.getCod_solicitud(), "Actualizado", fechaHoy, examen.getCedula_usuario(),  examen.getPrecio_examen().getCod_precio_examen()};
			//if(!(examen.getResultado_examen().getValores()).get(0).getValor().equals(""))
			//{
	db.update("insert into sol_exam(cod_solicitud, estado, fecha, cedula_usuario, cod_precio_examen) values(?,?,?,?,?)", datos);
	examen.setCod_sol_exam(db.queryForObject("select max(cod_sol_exam) from sol_exam;", Integer.class));
		
		Object[] datos2={examen.getPrecio_examen().getExamen().getCod_examen(),examen.getCod_sol_exam()};

		db.update("insert into resultados_examen(cod_examen,cod_sol_exam) values(?,?)",datos2);
		//cod_resultado_examen nivel1
		  cod_resultado_examen_solicitado=db.queryForObject("select max(cod_resultados_examen) from resultados_examen;", Integer.class);
          System.out.println(cod_resultado_examen_solicitado);
		if(examen.getPrecio_examen().getExamen().getNum_subexamenes() == 0)
		{
			  for(Valor valor:examen.getResultados_examen().getValores())
		       {
				   System.out.println(cod_resultado_examen_solicitado+"a" + valor.getCod());
			  Object[] datos2_1={cod_resultado_examen_solicitado,valor.getCod()};
			  
			  if(valor.getCod()==0){
				  datos2_1[1]=servicioResultados_por_defecto.registrar(examen.getPrecio_examen().getExamen().getCod_examen(), valor.getValor()).getCod();
			  }
		       db.update("insert into valor(cod_resultados_examen,cod) values(?,?);",datos2_1);
		
		       }
		}
		else{
		for(Resultados_examen resultados_examen : examen.getResultados_examen().getResultados_examenes())
		{
		  Object[] datos3={resultados_examen.getCod_examen(), examen.getCod_sol_exam(), cod_resultado_examen_solicitado};
		
		  db.update("insert into resultados_examen(cod_examen,cod_sol_exam, cod_resultados_examen_padre) values(?,?,?)",datos3);

		       
		       
		    if(resultados_examen.getExamen().getNum_subexamenes() !=0)  
		    {
		    	
		  	  int cod_resultado_examen_solicitado2=db.queryForObject("select max(cod_resultados_examen) from resultados_examen;", Integer.class);
	             
		    	for(Resultados_examen resultados_examen2 : resultados_examen.getResultados_examenes())
				{
				  Object[] datos5={resultados_examen2.getCod_examen(), examen.getCod_sol_exam(), cod_resultado_examen_solicitado2};
				
				  db.update("insert into resultados_examen(cod_examen,cod_sol_exam, cod_resultados_examen_padre) values(?,?,?)",datos5);

				       for(Valor valor:resultados_examen2.getValores())
				       {
					   int cod_resultado_examen=db.queryForObject("select max(cod_resultados_examen) from resultados_examen;", Integer.class);
		                 Object[] datos6={cod_resultado_examen,valor.getCod()};
		                 if(valor.getCod()==0){
		                	 System.out.println("este es el valor del resultado del examen de hemoglobina ----------"+valor.getValor());
		   				  datos6[1]=servicioResultados_por_defecto.registrar(resultados_examen2.getExamen().getCod_examen(), valor.getValor()).getCod();
		   			  }
				       db.update("insert into valor(cod_resultados_examen,cod) values(?,?);",datos6);
				
				       }
				       
				  
				       
				       
			}
		    	
		    	
		    }
		    else
		    {
		    	for(Valor valor:resultados_examen.getValores())
			       {
				   int cod_resultado_examen=db.queryForObject("select max(cod_resultados_examen) from resultados_examen;", Integer.class);
	                 Object[] datos4={cod_resultado_examen,valor.getCod()};
	                 if(valor.getCod()==0){
		   				  datos4[1]=servicioResultados_por_defecto.registrar(resultados_examen.getExamen().getCod_examen(), valor.getValor()).getCod();
		   			  }
			       db.update("insert into valor(cod_resultados_examen,cod) values(?,?);",datos4);
			
			       }
		    }
		       
		       
		}
		
	
}


System.out.println("bientio");
		return servicioSolicitud.buscarPorCodigo(examen.getCod_solicitud());
		
	}	
	public static java.util.Date ParseFecha(String fecha)
	{
	    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
	    java.util.Date fechaDate = null;
	    try {
	        fechaDate = formato.parse(fecha);
	    } 
	    catch (ParseException ex) 
	    {
	        System.out.println(ex);
	    }
	    return fechaDate;
	}

	public List<Resultados_examen> obtener_resultados_examenes(int cod_resultados_examen){
		
		String sql="select * from resultados_examen where cod_resultados_examen_padre="+cod_resultados_examen+" and cod_resultados_examen_padre is not null;";
			return  db.query(sql, new Resultados_examenRowMapper());
	}
public Resultados_examen buscarResultadosDeExamenSolicitado(int cod_sol_exam){
		Object datos[]={cod_sol_exam};
		String sql="select * from resultados_examen where cod_sol_exam=? and cod_resultados_examen_padre is null;";
			return  db.queryForObject(sql, datos, new Resultados_examenRowMapper());
}
}
