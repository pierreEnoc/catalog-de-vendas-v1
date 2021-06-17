package com.pierre.dsvendas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pierre.dsvendas.entities.services.S3Service;

@SpringBootApplication
public class DsvendasApplication  {
	
	
	public static void main(String[] args) {
		SpringApplication.run(DsvendasApplication.class, args);
	}

	

}
