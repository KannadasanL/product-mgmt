package com.learning.productapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;


@Configuration
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String brand;
	private String madein;
	private float price;

	public Product(String name, String brand, String madein, float price) {
		this.name = name;
		this.brand = brand;
		this.madein = madein;
		this.price = price;
	}

}
