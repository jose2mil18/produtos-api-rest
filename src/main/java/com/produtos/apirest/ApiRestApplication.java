package com.produtos.apirest;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;
@SpringBootApplication
@ComponentScan
// Using a root package also allows the @ComponentScan annotation to be used without needing to specify a basePackage attribute
public class ApiRestApplication  implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;
	public static void main(String[] args) {
		SpringApplication.run(ApiRestApplication.class, args);
	}
	  @Override
	    public void run(String... args) throws Exception {
	        System.out.println("Connection Polling datasource : "+ dataSource);  // check connection pooling
	    }
	  
	
}
