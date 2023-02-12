package com.minicursoadsfg.minicursoadsfg;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MinicursoadsfgApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinicursoadsfgApplication.class, args);
	}
	
	/*
	@Bean
	public DataSource dataSource(){
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
	        dataSource.setUrl("jdbc:mysql://localhost:3306/biblioteca?createDatabaseIfNotExist=true&serverTimezone=UTC");
	        dataSource.setUsername("root");
	        dataSource.setPassword("root");
	        return dataSource;
	}
	*/
}
