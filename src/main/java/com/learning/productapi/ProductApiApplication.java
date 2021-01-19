package com.learning.productapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductApiApplication {

	public static void main(String[] args) {
		System.out.println("*****************Staring product-api*************");
		SpringApplication.run(ProductApiApplication.class, args);
		System.out.println("*****************product-api started successfully in port 8080*************");
		
	}

}
