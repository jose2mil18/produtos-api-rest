package com.produtos.apirest.Services;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.ParseException; 
import java.text.SimpleDateFormat;

import java.text.ParsePosition;
import java.util.GregorianCalendar;

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
import com.produtos.apirest.Services.ServicioPaciente;

import com.produtos.apirest.Services.ServicioResultados_examen;
import com.produtos.apirest.Services.ServicioExamen_solicitado.Examen_solicitadoRowMapper;
import com.produtos.apirest.Services.*;

import com.produtos.apirest.varios.*;

import Utilidades.ControlCode;

@Service
public class ServicioSolicitud extends Conexion {

	@Autowired
	Solicitud solicitud;
	@Autowired
	ServicioResultados_examen servicioresultados_examen;
	
	@Autowired
	ServicioPaciente servicioPaciente;
	@Autowired
	ServicioPersona servicioPersona;
	@Autowired
	ServicioDosificacion servicioDosificacion;
	@Autowired
	Persona persona;
	@Autowired
	Institucion institucion;
	@Autowired
	ServicioExamen servicioExamen;
	@Autowired
	ServicioSolicitud servicioSolicitud;
	@Autowired
	ServicioInstitucion servicioInstitucion;
	@Autowired
	ServicioExamen_solicitado servicioExamen_solicitado;
	@Autowired
	ServicioFactura servicioFactura;

	@Autowired
	ServicioValor_referencia servicioValor_referencia;
	public class SolicitudRowMapper implements RowMapper<Solicitud> {
		@Override
		public Solicitud mapRow(ResultSet rs, int arg1) throws SQLException {
			Solicitud s=new Solicitud();
			//s.setCedula(rs.getString("cedula_solicitud"));
			s.setCod_solicitud(rs.getInt("cod_solicitud"));
		//s.setCod_factura(rs.getInt(("cod_factura")));
		
		
		s.setGestion(rs.getInt("gestion"));
			s.setInstitucion(servicioInstitucion.buscarPorCodigo(rs.getString("cod_institucion")));
		
		s.setEstado_solicitud(rs.getString("estado_solicitud"));

	    SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");

			s.setFecha(dmyFormat.format(rs.getDate("fecha")));
			s.setFecha_entrega(dmyFormat.format(rs.getDate("fecha_entrega")));
			
			System.out.println("cod_doctorsolicituante"+rs.getInt("cod_doctor_solicitante"));
			if(rs.getInt("cod_doctor_solicitante")!=0)
			{
			s.setDoctor_solicitante(servicioPersona.obtener_doctor_solicitante(rs.getInt("cod_doctor_solicitante")));
			}
s.setExamenes_solicitados(servicioExamen_solicitado.listarExamenesSolicitadosDeSolicitud(s.getCod_solicitud()));

String examenes="";
float costo=0;
for(Examen_solicitado examen_solicitado:s.getExamenes_solicitados())
{
	examenes=examenes+examen_solicitado.getPrecio_examen().getExamen().getNombre()+"\n";
	costo=costo+examen_solicitado.getPrecio_examen().getCosto();
	
	if(examen_solicitado.getPrecio_examen().getExamen().getNum_subexamenes()==0)
	{
	
	      if(examen_solicitado.getPrecio_examen().getExamen().getValores_referencia().size()>0)
            {
		       System.out.println("---------------------------------------numero de valores de referencia de examen"+examen_solicitado.getPrecio_examen().getExamen().getValores_referencia().size());
     	       for(Valor_referencia v : examen_solicitado.getPrecio_examen().getExamen().getValores_referencia()) {
     		       int cod_examen=examen_solicitado.getPrecio_examen().getExamen().getCod_examen();
     		       List<Valor_referencia> valoresDeReferencia = new ArrayList<Valor_referencia>();
     		       if(examen_solicitado.getEstado().equals("Sin Registrar"))
     		       {
     		        valoresDeReferencia=servicioValor_referencia.listarValoresDeReferenciaDeExamenSolicitadoVigente(cod_examen);
     		      }
     		     else
     		       {
     		    valoresDeReferencia=servicioValor_referencia.listarValoresDeReferenciaDeExamenSolicitadoAntiguo(cod_examen, examen_solicitado.getFecha().toString());
     			  
     		        }
     		     examen_solicitado.getPrecio_examen().getExamen().setValores_referencia(valoresDeReferencia);
     		  
     		  
     	       }
           }
	}
	else
	{

		agregarValoresDeReferenciaAlExamenSolicitado(examen_solicitado.getPrecio_examen().getExamen(),examen_solicitado);
	}
	
}
s.setCostoTotal(costo);
s.setExamenes_solicitados_de_solicitud(examenes);
s.setExamenes_solicitados_con_resultados_actualizados(servicioExamen_solicitado.examenes_solicitados_con_resultados_modificados(s.getCod_solicitud()));
int i=0;

s.setEstado(rs.getString("estado"));

for(Examen_solicitado examen_solicitado:s.getExamenes_solicitados())
{
	//examen_solicitado.getPrecio_examen().getExamen().setSubexamenes(servicioExamen.listarSubExamenes(examen_solicitado.getPrecio_examen().getExamen().getCod_examen()));
	//if(servicioSolicitud.estadoExamenSolicitado(s.getCod_solicitud(), examen_solicitado.getPrecio_examen().getExamen().getCod_examen()).equals("Registrado"))
	if(examen_solicitado.getEstado().equals("Registrado") || examen_solicitado.getEstado().equals("Actualizado"))
			
	{
	i++;
	
	          
	
			}
	
}

System.out.println("-------------------------------------------------------------"+s.getCod_solicitud()+"-------------------------------------------- ---------------------------------------");
if(s.getExamenes_solicitados().size() == i)
{

	s.setEstado("Registrado");
	System.out.println(s.getExamenes_solicitados().size()+"fkdsf"+i+""+s.getEstado());
	cambiarEstado(s.getCod_solicitud(), s.getEstado());
}
else if(i == 0)
{
	System.out.println("sin registrar");
	s.setEstado("Sin Registrar");
}
else {
	System.out.println("pendiente");
	s.setEstado("Pendiente");
	cambiarEstado(s.getCod_solicitud(), s.getEstado());
}



			s.setCedula_usuario(rs.getString("cedula_usuario"));
			s.setCedula_paciente(rs.getString("cedula_paciente"));
	s.setPaciente(servicioPaciente.buscar_paciente_de_solicitud(rs.getString("cedula_paciente")));	
	
			if(s.getEstado().equals("Registrado"))
			{
				s.setFactura(servicioFactura.buscarFacturaDeSolicitud(s.getCod_solicitud()));
				
			}
			return s;
		}
	}

	


public void agregarValoresDeReferenciaAlExamenSolicitado(Examen e, Examen_solicitado examen_solicitado) {
if(e.getSubexamenes().size() !=0)
{	
	for(Examen su: e.getSubexamenes()){
	
          if(su.getSubexamenes().size() !=0) {
        	  agregarValoresDeReferenciaAlExamenSolicitado(su,examen_solicitado);
          }
          else
          {
        	  
        	  if(su.getValores_referencia().size()>0)
              {
  		       System.out.println("---------------------------------------numero de valores de referencia de examen"+e.getValores_referencia().size());
       	       for(Valor_referencia v : su.getValores_referencia()) {
       		       int cod_examen=su.getCod_examen();
       		       List<Valor_referencia> valoresDeReferencia = new ArrayList<Valor_referencia>();
       		       if(examen_solicitado.getEstado().equals("Sin Registrar"))
       		       {
       		        valoresDeReferencia=servicioValor_referencia.listarValoresDeReferenciaDeExamenSolicitadoVigente(cod_examen);
       		      }
       		     else
       		       {
       		    valoresDeReferencia=servicioValor_referencia.listarValoresDeReferenciaDeExamenSolicitadoAntiguo(cod_examen, examen_solicitado.getFecha().toString());
       			  
       		        }
       		     su.setValores_referencia(valoresDeReferencia);
       		  
       		  
       	       }
             }
        	  
        	  
        	  
        	  
          }
	}
}

}

public void cambiarEstado(int cod_solicitud, String estado) {
	db.update("update solicitud set estado='"+estado+"' where cod_solicitud="+cod_solicitud+";");
}

public List<Solicitud> listar(){
	String sql="select * from solicitud  order by fecha desc;";
	return  db.query(sql, new SolicitudRowMapper());
	
	
	 
}
public String minimafechadesolicitud(){
	String sql="select min(fecha) from solicitud";
	return  db.queryForObject(sql, String.class);
	
	
	 
}
public List<Solicitud> listar_solicitudes_de_un_mismo_examen(String grupo, String seleccionador)
{
	System.out.println(seleccionador);
	String sql="select s.cedula_paciente, s.cod_institucion, s.cod_doctor_solicitante, s.fecha, \n" + 
			"s.fecha_entrega, s.estado, s.cedula_usuario, estado_solicitud, s.gestion  \n" + 
			"from solicitud s, sol_exam soe, precio_examen pe, examen e, area a\n" + 
			"where a.cod_area=e.cod_area and soe.cod_precio_examen=pe.cod_precio_examen and e.cod_examen=pe.cod_examen  and (s.fecha>='2019-01-01' and s.fecha<='2020-07-20') and  "+seleccionador+"='"+grupo+"' and  soe.cod_sol_exam=(select max(cod_sol_exam) from sol_exam \n" + 
			"where cod_precio_examen=soe.cod_precio_examen and cod_solicitud=soe.cod_solicitud)\n" + 
			"and soe.cod_solicitud=s.cod_solicitud";
	return  db.query(sql, new SolicitudesRowMapper());
	
	
	 
}

public List<Solicitud> listar_solicitudes_de_un_mismo_examen_entre_Periodo(int cod_precio_examen, String fecha_inicio, String fecha_fin)
{
	  java.sql.Date fecha_in=java.sql.Date.valueOf(fecha_inicio);

		java.sql.Date fecha_final=java.sql.Date.valueOf(fecha_fin);
	System.out.println(cod_precio_examen);
	String sql=" select s.cedula_paciente, s.cod_institucion, s.cod_doctor_solicitante, s.fecha, s.fecha_entrega, s.estado, s.cedula_usuario, estado_solicitud, s.gestion  from solicitud s, sol_exam soe where  (s.fecha>='"+fecha_in+"' and s.fecha<='"+fecha_final+"') and  soe.cod_precio_examen= "+cod_precio_examen+" and  soe.cod_sol_exam=(select max(cod_sol_exam) from sol_exam  where cod_precio_examen=soe.cod_precio_examen and cod_solicitud=soe.cod_solicitud) and soe.cod_solicitud=s.cod_solicitud";
	return  db.query(sql, new SolicitudesRowMapper());
	
	
	 
}
public Solicitud buscarPorCodigo(int cod_solicitud){
	Object[] datos={cod_solicitud};
	String sql="select * from solicitud where cod_solicitud=?";
	return db.queryForObject(sql, datos,new SolicitudRowMapper()
			);
}

public Solicitud listarSolicitudporcodigoConResultados(int cod_solicitud){
	Object[] datos={cod_solicitud};
	String sql="select * from solicitud where cod_solicitud=?";
	return db.queryForObject(sql, datos,new SolicitudRowMapper()
			);
}
public Solicitud listarSolicitudporcodigos(int cod_solicitud){
	Object[] datos={cod_solicitud};
	String sql="select s.cedula_paciente, s.cod_institucion, s.cod_doctor_solicitante, s.fecha, s.fecha_entrega, s.estado, s.cedula_usuario, estado_solicitud, s.gestion from solicitud s where s.cod_solicitud=?";
	Solicitud s=new Solicitud();
	s=db.queryForObject(sql, datos,new SolicitudesRowMapper()
			);
	return s;
}
public Solicitud listarSolicitudConResultadosModificados(int cod_solicitud){
	Object[] datos={cod_solicitud};
	String sql="select * from solicitud where cod_solicitud=?";
	return db.queryForObject(sql, datos,new SolicitudRowMapper()
			);
}

/*
public List<Solicitud> filtrarSolicitudesPorPacienteYFecha(String id){

	/*
	if(fechaString  == null)
	{

		String sql="select  DISTINCT s.cod_solicitud, s.cedula_paciente,  s.cod_institucion, s.cod_doctor_solicitante, s.fecha, s.fecha_entrega, s.estado, s.cedula_usuario, s.gestion  from solicitud s, pacientes p, examen e, sol_exam soe, persona pe where p.cedula=s.cedula_paciente and soe.cod_solicitud=s.cod_solicitud and p.cod_persona=pe.cod_persona and (pe.nombre ilike ? or pe.ap ilike ? or pe.am ilike ?  )    order by s.fecha desc;";
		return  db.query(sql, new SolicitudRowMapper(),id, id, id);
	}
	
	else{

		System.out.println("este la fecha"+fechaString);
		java.sql.Date fe=java.sql.Date.valueOf(fechaString);
	SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
	
	java.util.Date fecha = null;
	try {

	fecha = formatoDelTexto.parse(fechaString);

	} catch (ParseException ex) {

	ex.printStackTrace();

	}

	String sql="select  DISTINCT s.cod_solicitud, s.cedula_paciente,  s.cod_institucion, s.cod_doctor_solicitante, s.fecha, s.fecha_entrega, s.estado, s.cedula_usuario, s.gestion  from solicitud s, pacientes p, examen e, sol_exam soe, persona pe where p.cedula=s.cedula_paciente and soe.cod_solicitud=s.cod_solicitud and p.cod_persona=pe.cod_persona and (pe.nombre ilike ? or pe.ap ilike ? or pe.am ilike ?  )   and s.fecha='"+fe+"'  order by s.fecha desc;";
			return  db.query(sql, new SolicitudRowMapper(),id, id, id);
	}
	
	
	 
}
*/

public List<Solicitud> filtrarSolicitudesPorPaciente(String id){

	List<Solicitud> a=new ArrayList<Solicitud>();
	System.out.println("este la paciente"+id);
	

		String sql;
		try {
			sql = "select  DISTINCT s.cod_solicitud, s.cedula_paciente,  s.cod_institucion, s.cod_doctor_solicitante, s.fecha, s.fecha_entrega, s.estado, s.cedula_usuario, s.gestion, s.estado_solicitud  from solicitud s, pacientes p, examen e, sol_exam soe, persona pe where p.cedula=s.cedula_paciente and soe.cod_solicitud=s.cod_solicitud and p.cod_persona=pe.cod_persona and s.cedula_paciente='"+id+"'    order by s.fecha desc;";
			a= db.query(sql, new SolicitudRowMapper());
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	return  a;

}
	

public List<Solicitud> buscar(String cedula, String area, String caracter_nombre_examen, String fecha_solicitud, String fecha_inicio, String fecha_fin, String estado_solicitud, String resultados){
System.out.println("------------------------------"+resultados);
	String sql="";

	if(!fecha_inicio.equals("") && !fecha_fin.equals("")) {
		System.out.println("este la fe inicio"+fecha_inicio);
		System.out.println("este la fe fin"+fecha_fin);
		System.out.println("resultados"+resultados);
		  java.sql.Date fecha_in=java.sql.Date.valueOf(fecha_inicio);

			java.sql.Date fecha_final=java.sql.Date.valueOf(fecha_fin);
		
		
	sql="select * from solicitud where (fecha>='"+fecha_in+"' and fecha<='"+fecha_final+"') and cedula_paciente ilike '%"+cedula+"%' and estado ilike '%"+resultados+"%' and estado_solicitud ilike '%"+estado_solicitud+"%' order by fecha desc;";
			
		      
		    
		 
	}


	if(!fecha_solicitud.equals("")) {
		java.sql.Date fecha=java.sql.Date.valueOf(fecha_solicitud);
		sql="select * from solicitud s where s.fecha='"+fecha+"' and cedula_paciente ilike '%"+cedula+"%' and estado ilike '%"+resultados+"%' and estado_solicitud ilike '%"+estado_solicitud+"%' ;";
	}
		if(fecha_inicio.equals("") && fecha_fin.equals("") && fecha_solicitud.equals(""))
		{
			System.out.println("cedulakl"+cedula);
			sql="select * from solicitud s where  s.estado_solicitud ilike '%"+estado_solicitud+"%'  and estado ilike '%"+resultados+"%' and cedula_paciente ilike '%"+cedula+"%'  order by s.fecha desc";


		}
	
		
		
		Object[] datos={area, cedula, caracter_nombre_examen};
		System.out.println(area+"    "+cedula+"  "+caracter_nombre_examen);
		//String sql="select se.cod_sol_exam, se.cod_solicitud, se.cod_examen, se.estado, se.fecha, se.precio, se.cedula_usuario, se.cod_precio_examen from solicitud s, sol_exam se where s.cod_solicitud=se.cod_solicitud  and se.cod_solicitud="+cod_solicitud+" ;";
		
		return  db.query(sql,   new SolicitudRowMapper());
	}
public List<Solicitud> filtrarSolicitudesPorFecha(String fechaString){

	System.out.println("este la fe"+fechaString);
	java.sql.Date fecha=java.sql.Date.valueOf(fechaString);
	String sql="select * from solicitud s where s.fecha='"+fecha+"';";
	return  db.query(sql, new SolicitudRowMapper());
	
	
	 
}
public List<Solicitud> todaslassolicitudes(){


	String sql="select * from solicitud";
	return  db.query(sql, new SolicitudRowMapper());
	
	
	 
}
public List<Solicitud> filtrarSolicitudesPorRangoFecha(String fecha_inicio, String fecha_fin){

	System.out.println("este la fe inicio"+fecha_inicio);
	java.sql.Date fecha_in=java.sql.Date.valueOf(fecha_inicio);

	java.sql.Date fecha_final=java.sql.Date.valueOf(fecha_fin);
	String sql="select * from solicitud where (fecha>='"+fecha_in+"' and fecha<='"+fecha_final+"');";
	return  db.query(sql, new SolicitudRowMapper());
	
	
	 
}

public List<Solicitud> listarAnalisisSinResultados(String cedula, String area, String caracter_nombre_examen, String fecha_solicitud, String fecha_inicio, String fecha_fin, String estado_solicitud){

	String sql="";

	if(!fecha_inicio.equals("") && !fecha_fin.equals("")) {
		System.out.println("este la fe inicio"+fecha_inicio);
		java.sql.Date fecha_in=java.sql.Date.valueOf(fecha_inicio);

		java.sql.Date fecha_final=java.sql.Date.valueOf(fecha_fin);
		sql="select * from solicitud where (fecha>='"+fecha_in+"' and fecha<='"+fecha_final+"') and cedula_paciente ilike '%"+cedula+"%' and (estado='Sin Registrar' or estado='Pendiente');";
	}


	if(!fecha_solicitud.equals("")) {
		java.sql.Date fecha=java.sql.Date.valueOf(fecha_solicitud);
		sql="select * from solicitud s where s.fecha='"+fecha+"' and cedula_paciente ilike '%"+cedula+"%' and (estado='Sin Registrar' or estado='Pendiente');";
	}
		if(fecha_inicio.equals("") && fecha_fin.equals("") && fecha_solicitud.equals(""))
		{
			sql="select * from solicitud where cedula_paciente='"+cedula+"' and (estado='Sin Registrar' or estado='Pendiente');";

		}
		if(fecha_inicio.equals("") && fecha_fin.equals("") && fecha_solicitud.equals("") && cedula.equals(""))
		{
			sql="select * from solicitud s where  s.estado_solicitud ilike '%"+estado_solicitud+"%'  and (estado='Sin Registrar' or estado='Pendiente') order by s.fecha desc";

		}
		
		
		Object[] datos={area, cedula, caracter_nombre_examen};
		System.out.println(area+"    "+cedula+"  "+caracter_nombre_examen);
		//String sql="select se.cod_sol_exam, se.cod_solicitud, se.cod_examen, se.estado, se.fecha, se.precio, se.cedula_usuario, se.cod_precio_examen from solicitud s, sol_exam se where s.cod_solicitud=se.cod_solicitud  and se.cod_solicitud="+cod_solicitud+" ;";
		
		return  db.query(sql,   new SolicitudRowMapper());
	}

public List<Solicitud> listarAnalisisConResultados(){
	
	String sql="select  * from solicitud where estado='Registrado' order by fecha desc";
	return  db.query(sql, new SolicitudRowMapper());
}
public Solicitud listarAnalisisSinResultadosporcodigo(int cod_solicitud){
	Object[] datos={cod_solicitud};
	String sql="select  DISTINCT s.cod_solicitud, s.cedula_paciente, s.cod_institucion, s.cod_doctor_solicitante, s.fecha, s.fecha_entrega, s.estado, s.cedula_usuario, estado_solicitud from solicitud s, pacientes p, examen e, sol_exam soe where p.cedula=s.cedula_paciente and soe.cod_solicitud=s.cod_solicitud and soe.cod_examen=e.cod_examen  and s.estado='Sin Registrar' and s.cod_solicitud="+cod_solicitud+";";
	return db.queryForObject(sql, new SolicitudRowMapper()
			);
}

public Solicitud listarAnalisisSinResultadosporcodigoregistrado(int cod_solicitud){
	Object[] datos={cod_solicitud};
	String sql="select  DISTINCT s.cod_solicitud, s.cedula_paciente, s.institucion, s.cod_doctor_solicitante, s.fecha, s.fecha_entrega, s.estado, s.cedula_usuario, estado_solicitud from solicitud s, pacientes p, examen e, sol_exam soe where p.cedula=s.cedula_paciente and soe.cod_solicitud=s.cod_solicitud and soe.cod_examen=e.cod_examen  and s.resultados='Registrado' and s.cod_solicitud="+cod_solicitud+";";
	return db.queryForObject(sql, new SolicitudRowMapper()
			);
}
public Solicitud maxima_solicitud(int cod_solicitud){
	Object[] datos={cod_solicitud};
	String sql="select max(cod_solicitud) from solicitud";
	return db.queryForObject(sql, datos, new SolicitudRowMapper()
			);
}
public String estadoExamenSolicitado(int cod_solicitud, int cod_examen){
	String sql="select se.estado from solicitud s, sol_exam se where s.cod_solicitud=se.cod_solicitud and se.cod_examen="+cod_examen+" and se.cod_solicitud="+cod_solicitud+";";
	return db.queryForObject(sql, String.class);
}

public Integer num_solicitudes(){
	String sql="select count(*) from solicitud";
	return db.queryForObject(sql, Integer.class);
}

public Integer num_solicitudes_pendientes(){
	String sql="select count(*) from solicitud where estado='Sin Registrar'";
	return db.queryForObject(sql, Integer.class);
}

public void registrarRelacionSolicitudPosta(String institucion){
	int cod_posta=Integer.parseInt(institucion);
	int cod_solicitud=db.queryForObject("select max(cod_solicitud) from solicitud", Integer.class);
	Object[] datos={cod_posta, cod_solicitud};
	db.update("insert into posta_soli values(?,?)", datos);
}

public void modificarRelacionSolicitudPosta(int cod_solicitud, String institucion){
	int cod_posta=Integer.parseInt(institucion);
	Object[] datos={cod_posta, cod_solicitud};
	System.out.println(cod_solicitud+"  "+institucion);
	try {
		db.update("delete from posta_soli where cod_solicitud="+cod_solicitud+";");
		//db.update("update posta_soli set cod_posta=?, cod_solicitud=? where cod_solicitud="+cod_solicitud+";", datos);
		db.update("insert into posta_soli values(?,?)", datos);
		//db.update("insert into posta_soli values(?,?)", datos);
	} catch (DataAccessException e) {
		// TODO Auto-generated catch block
		System.out.println(e.getMessage());

		//db.update("update posta_soli set cod_posta=?, cod_solicitud=? where cod_solicitud="+cod_solicitud+";", datos);
	}
}
public void eliminarExamenesSolicitados(int cod_solicitud){
	//int cod_posta=Integer.parseInt(institucion);
	Object[] datos={cod_solicitud};
	db.update("delete from sol_exam where cod_solicitud=?;", datos);
}
public void registrar(Solicitud s){
	java.util.Date fecha=ParseFecha(s.getFecha());
	
	java.util.Date fecha_entrega=ParseFecha(s.getFecha_entrega());
	
Persona doctor=s.getDoctor_solicitante();
Object[] datos={s.getInstitucion().getCod_institucion(), null, fecha, fecha_entrega, s.getPaciente().getCedula(), "Sin Registrar", s.getCedula_usuario(), s.getGestion()};

if(!(s.getDoctor_solicitante() == null))
{
    doctor.setTipo("Doctor_solicitante");

	
	int cod_doctor_solicitante=0;
	try {
		cod_doctor_solicitante = db.queryForObject("select cod_persona from persona where nombre='"+doctor.getNombre()+"' and ap='"+doctor.getAp()+"' and am='"+doctor.getAm()+"' and tipo='"+doctor.getTipo()+"';", Integer.class);
	} catch (DataAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if(cod_doctor_solicitante >0){
		datos[1]=cod_doctor_solicitante;
	}
	else
	{
		datos[1]=servicioPersona.registrar(doctor).getCod_persona();
	
	}
	System.out.println("soli");
	
}

	
	db.update("insert into solicitud(cod_institucion, cod_doctor_solicitante, fecha, fecha_entrega, cedula_paciente,estado, cedula_usuario, gestion)  values(?,?,?,?,?,?,?,?)", datos);

	
	int cod_solicitud=db.queryForObject("select max(cod_solicitud) from solicitud", Integer.class);
	System.out.println("soli"+cod_solicitud);
	for(Examen_solicitado examen : s.getExamenes_solicitados()){
		System.out.println("--------------------------estado"+examen.getEstado());
		if(examen.getEstado().equals("Sin Registrar"))
		{
	
		Object[] datos2={cod_solicitud, "Sin Registrar", examen.getPrecio_examen().getCod_precio_examen()};
	
			db.update("insert into sol_exam(cod_solicitud, estado, cod_precio_examen) values(?,?,?)", datos2);
		}
	
	}
}
public Solicitud modificar(Solicitud s){
System.out.println("fecha de entrega"+s.getFecha_entrega());
java.util.Date fecha=ParseFecha(s.getFecha());

java.util.Date fecha_entrega=ParseFecha(s.getFecha_entrega());

System.out.println("nro de examenes de solicitud"+s.getExamenes_solicitados().size());
Institucion institucion=new Institucion();
institucion=s.getInstitucion();
Persona doctor = new Persona();
doctor=s.getDoctor_solicitante();
String insti_padre="vacio";
//eliminarExamenesSolicitados(s.getCod_solicitud());
if(!(s.getDoctor_solicitante() == null))
{
s.getDoctor_solicitante().setTipo("Doctor_solicitante");

Object[] datos={s.getInstitucion().getCod_institucion(), s.getDoctor_solicitante().getCod_persona(), fecha, fecha_entrega,s.getPaciente().getCedula(), s.getEstado(), s.getCedula_usuario(), s.getGestion(),s.getEstado_solicitud(), s.getCod_solicitud()};

int cod_doctor_solicitante=0;
try {
	cod_doctor_solicitante = db.queryForObject("select cod_persona from persona where nombre='"+doctor.getNombre()+"' and ap='"+doctor.getAp()+"' and am='"+doctor.getAm()+"' and tipo='"+doctor.getTipo()+"';", Integer.class);
} catch (DataAccessException e) {
		e.printStackTrace();
}
if(cod_doctor_solicitante >0){
	datos[1]=cod_doctor_solicitante;
	db.update("update solicitud set cod_institucion=?, cod_doctor_solicitante=?, fecha=?, fecha_entrega=?, cedula_paciente=?,estado=?, cedula_usuario=?, gestion=?, estado_solicitud=?  where cod_solicitud=?", datos);
	}
else
{
	datos[1]=servicioPersona.registrar(doctor).getCod_persona();
	db.update("update solicitud set cod_institucion=?, cod_doctor_solicitante=?, fecha=?, fecha_entrega=?, cedula_paciente=?,estado=?, cedula_usuario=?, gestion=?  where cod_solicitud=?", datos);

}
}
else {
	System.out.println("sin doctor solicitante");
	Object[] datos10={s.getInstitucion().getCod_institucion(), null, fecha, fecha_entrega,s.getPaciente().getCedula(), s.getEstado(), s.getCedula_usuario(), s.getGestion(), s.getCod_solicitud()};
	db.update("update solicitud set cod_institucion=?, cod_doctor_solicitante=?, fecha=?, fecha_entrega=?, cedula_paciente=?,estado=?, cedula_usuario=?, gestion=?  where cod_solicitud=?", datos10);

}
for(Examen_solicitado examen : s.getExamenes_solicitados())
{
	System.out.println("estado_examen"+examen.getEstado());
	if(examen.getCod_sol_exam() ==0 && examen.getEstado().equals("Sin Registrar"))
	{
	Object[] datos2={s.getCod_solicitud(), "Sin Registrar", examen.getPrecio_examen().getCod_precio_examen()};
	try {
		db.update("insert into sol_exam(cod_solicitud, estado, cod_precio_examen) values(?,?,?)", datos2);
		
	} catch (DataAccessException e) {	// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	if(examen.getCod_sol_exam() !=0){
		Object[] datos2={examen.getEstado(), s.getCod_solicitud(), examen.getPrecio_examen().getCod_precio_examen()};
		try {
			db.update("update sol_exam set estado=? where cod_solicitud=? and cod_precio_examen=?", datos2);
			
		} catch (DataAccessException e) {	// update sol_exam set estado=? where cod_solicitud=? and cod_precio_examen=?
			e.printStackTrace();
		}
	}
}




	System.out.println("actuaizaion exitosamente");
return buscarPorCodigo(s.getCod_solicitud());

}

public Solicitud generarFactura(Solicitud s){
	System.out.println("cedula_paciente"+s.getCedula_paciente()+"costototal"+s.getCostoTotal());
	try {
		ControlCode controlCode = new ControlCode();
		List<Dosificacion> dosificaciones=servicioDosificacion.listarDosificacionVigente();
		System.out.println("---------------------------dosificciones tamaño"+dosificaciones.size()+"---------------------");
		Dosificacion dosificacion=dosificaciones.get(0);
		String autorizacion = dosificacion.getAutorizacion();

		String numeroFactura = Integer.toString(servicioFactura.NumeroDeFacturas()+1);
		SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date(System.currentTimeMillis());
		String fecha = formatter.format(date);
		String llave = dosificacion.getLlave();
		String costo_total=String.valueOf(s.getCostoTotal());
		System.out.println(autorizacion+" "+numeroFactura+" "+s.getCedula_paciente()+" "+fecha+" "+s.getCostoTotal()+" "+llave);
		//genera codigo de control
		String cc = controlCode.generate(dosificacion.getAutorizacion(), numeroFactura, s.getCedula_paciente(), fecha.replace("-", ""), costo_total, llave);
Object[] datos= {cc, dosificacion.getCod_dosificacion(), s.getCod_solicitud()};

db.update("insert into factura(cod_control, cod_dosificacion, cod_solicitud) values(?,?,?)", datos);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
s.setFactura(servicioFactura.buscarFacturaDeSolicitud(s.getCod_solicitud()));
return s;
}

public void actualizarEstadoSolicitud(int cod_solicitud)
{
	db.update("update solicitud set estado='Registrado' where cod_solicitud="+cod_solicitud+";");
}
public static java.sql.Date ConvertirFecha(String fecha){
	return java.sql.Date.valueOf(fecha.substring(0,2)+"-"+fecha.substring(3,5)+"-"+fecha.substring(6,10));
}
public Date convertirStringDate(String fec, String formato){
	SimpleDateFormat format = new java.text.SimpleDateFormat(formato, new Locale("es", "ES"));
	Date fecha=null;
	try {
	    fecha = new java.sql.Date(format.parse(fec).getTime());
	} catch (Exception ex) {ex.printStackTrace();}
	return fecha;
}
public static java.util.Date ParseFecha(String fecha)
{
    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
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



public class SolicitudesRowMapper implements RowMapper<Solicitud> {
	@Override
	public Solicitud mapRow(ResultSet rs, int arg1) throws SQLException {
		Solicitud s=new Solicitud();
		//s.setCedula(rs.getString("cedula_solicitud"));
		
	s.setGestion(rs.getInt("gestion"));
		s.setInstitucion(servicioInstitucion.buscarPorCodigo(rs.getString("cod_institucion")));
	
	s.setEstado_solicitud(rs.getString("estado_solicitud"));

	    SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");

		s.setFecha(dmyFormat.format(rs.getDate("fecha")));
		s.setFecha_entrega(dmyFormat.format(rs.getDate("fecha_entrega")));
		
		System.out.println("cod_doctorsolicitante"+rs.getInt("cod_doctor_solicitante"));
		if(rs.getInt("cod_doctor_solicitante")!=0)
		{
		s.setDoctor_solicitante(servicioPersona.obtener_doctor_solicitante(rs.getInt("cod_doctor_solicitante")));
		}
int i=0;

s.setEstado(rs.getString("estado"));

System.out.println("FAdsfdfadsfasfjakfjkajfkajdfkajdfkajkdfjakjfkadjfkadjfjasdkfjakdjfkajfkadjfkasjfkasjfksajfkadjfk");




		s.setCedula_usuario(rs.getString("cedula_usuario"));
s.setPaciente(servicioPaciente.buscar_paciente_de_solicitud(rs.getString("cedula_paciente")));	
s.setCedula_paciente(rs.getString("cedula_paciente"));
		
		return s;
	}
}







}