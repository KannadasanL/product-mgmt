package com.learning.productapi.domain;

import java.io.Serializable;

import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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

}
