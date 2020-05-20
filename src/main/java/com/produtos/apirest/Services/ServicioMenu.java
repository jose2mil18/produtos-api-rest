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

import com.produtos.apirest.Services.ServicioProceso;
import com.produtos.apirest.models.Proceso;

import com.produtos.apirest.models.Menu;
@Service
public class ServicioMenu extends Conexion {
	@Autowired
	Menu menu;
	@Autowired
	ServicioProceso servicioproceso;
	String sql;
	public class MenuRowMapper implements RowMapper<Menu> {
		@Override
		public Menu mapRow(ResultSet rs, int arg1) throws SQLException {
			Menu menu=new Menu();
			menu.setCod_menu(rs.getInt("cod_menu"));
			menu.setNombre(rs.getString("nombre"));
			menu.setProcesos(servicioproceso.listarprocesosdemenu(rs.getInt("cod_menu")));
			
			return menu;
		}
	}
	
	public int insertar_menu(String nombr, Integer estado) {
		
		

			  int max=db.queryForObject("select max(codm) from menus",Integer.class);
			max=max+1;
			Object[] datos={max, nombr, estado};
			if(estado==0 || estado==1 ){
		
			db.update("insert into menus values(?,?,?)",datos);
			
		
			}
			return max;
		
	}
	public List<Menu> listar(){
		String sql="select * from menus order by codm";
		return  db.query(sql,new MenuRowMapper());
		
		
		 
	}
	public void borrar(Integer codm){

		
		String sql = "UPDATE menus SET estado=0 WHERE codm=?;";
		db.update(sql, codm);
	}

public String obtener(String Codp){
	int codMenu=Integer.parseInt(Codp);
	Object[] datos={new Integer(codMenu)};
	sql="SELECT * FROM Menus WHERE codp=?";
	
	return db.queryForObject(sql, datos, String.class);
	
}

/*
public void modificar(String Codp,String nombre, String estado){
	int codMenu=new Integer(Integer.parseInt(Codp));
	int estad=Integer.parseInt(estado);
	Object[] datos={ codMenu};
		String sql = "Update Menus set  nombre=?, estado=? WHERE codp=?;";
		
		db.update(sql, datos);
		
		
	}*/

public void modificar_menu(Integer codm, String nombre, Integer estado){
	
	Object[] datos={nombre, estado, codm};
	
		String sql = "Update menus set  nombre=?, estado=? WHERE codm=?;";
		
		db.update(sql,datos);
		
}
//obtien un objetode la clase Menu cuyo codpseleccioamos
public Menu obtener_por_Codm(Integer codm){
	String sql = "SELECT * FROM menus WHERE codm=?;";
	return db.queryForObject(sql, new MenuRowMapper(), codm);
}
public int obtenercodm(String nombremenu){
	String sql="select codm from menus where nombre=?;";
	Object[] datos={nombremenu};
	System.out.println(sql);
	return db.queryForObject(sql, datos, Integer.class);

}

public void insertarcodigodemenuyrol(Integer codr, Integer codm){
	db.update("insert into rolme values(?,?)", codr, codm);
	System.out.println("succesfull");
}

public void borrarmenuderol(Integer codr, Integer codm){
	String sql="delete from rolme where codr="+codr+"and codm="+codm+";";
	db.update(sql);
	System.out.println("succesfull");
}
public List<Menu> listarmenusderol(Integer codr){
	String sql="select m.cod_menu, m.nombre from menu m, rolme rm, rol r where rm.cod_rol=r.cod_rol and m.cod_menu=rm.cod_menu and r.cod_rol="+codr+"  order by m.nombre;";


	return  db.query(sql,new MenuRowMapper());
	
	
	 
}

public void borrarprocesosdemenu(Integer codm){
	String sql="delete from mepro where codm=?";
	db.update(sql, codm);
}
public List<Menu> listarmenudeusuario(Integer codr){
	String sql="select m.codm, m.nombre, m.estado from menus m, roles r, rolme rm where m.codm=rm.codm and r.codr=rm.codr and r.codr='"+codr+"';";


	return  db.query(sql,new MenuRowMapper());
	
	
	 
}

	}

