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
			menu.setProcesos(servicioproceso.listarProcesosDeMenu(rs.getInt("cod_menu")));
			
			return menu;
		}
	}
	


/*
public void modificar(String Codp,String nombre, String estado){
	int codMenu=new Integer(Integer.parseInt(Codp));
	int estad=Integer.parseInt(estado);
	Object[] datos={ codMenu};
		String sql = "Update Menus set  nombre=?, estado=? WHERE codp=?;";
		
		db.update(sql, datos);
		
		
	}*/






public List<Menu> listarMenusDeRol(Integer codr){
	String sql="select m.cod_menu, m.nombre from menu m, rolme rm, rol r where rm.cod_rol=r.cod_rol and m.cod_menu=rm.cod_menu and r.cod_rol="+codr+"  order by m.nombre;";


	return  db.query(sql,new MenuRowMapper());
	
	
	 
}




	}

