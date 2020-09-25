package com.produtos.apirest;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;
@SpringBootApplication
@ComponentScan
// Using a root package also allows the @ComponentScan annotation to be used without needing to specify a basePackage attribute
public class ApiRestApplication  {
/*
extends SpringBootServletInitializer {

    @Autowired
    private DataSource dataSource;
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(ApiRestApplication.class);
	}
	*/
	
	public static void main(String[] args) {
		SpringApplication.run(ApiRestApplication.class, args);
	}

	

}
