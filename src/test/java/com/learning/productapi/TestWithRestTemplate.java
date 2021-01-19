package com.learning.productapi;

import java.util.HashMap;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.learning.productapi.domain.Product;


public class TestWithRestTemplate {

	public static final String URI = "http://localhost:8080/product-mgmt";

	// get message
	private static void getMessage() {
		System.out.println("******Testing Health for this application************");
		String message = new RestTemplate().getForObject(URI + "/health", String.class);
		System.out.println("Message::::::::" + message);
		System.out.println("******Successfully tested the health for this application************");
	}

	// get products

	private static void testGetAllProducts() {
		System.out.println("******testGetAllProducts - start************");	
		HttpEntity<String> requestEntity = new HttpEntity<>("",buildHttpHeaders());
		ResponseEntity<Object> response = new RestTemplate().exchange(URI+"/products", HttpMethod.GET, requestEntity, Object.class, new HashMap<>());
		System.out.println("product list ::: " + response.getBody());
		System.out.println("******testGetAllProducts - end************");
	}

	// get product by id

	private static void testGetProductById() {

		System.out.println("******testGetProductById - start************");
		Product product = new RestTemplate().getForObject(URI + "/products/1", Product.class);
		System.out.println("product ::: " + product);
		System.out.println("******testGetProductById - end************");
	}

	//create product
	
	private static void testCreateProduct() {
		System.out.println("******testCreateProduct - start************");
		Product product = new Product(1, "iphone4s", "apple", "india", 200);
		ResponseEntity<Product> responseProduct = new RestTemplate().postForEntity(URI+"/products", product, Product.class);
		System.out.println("product saved ::: " + responseProduct.getBody());
		System.out.println("******testCreateProduct - end************");
	}
	
	//update product
	
	private static void testUpdateProduct() {
		System.out.println("******testUpdateProduct - start************");
		Product product = new Product(5, "iphone6s", "apple", "india", 850);
		HttpEntity<Product> requestEntity = buildRequestEntity(product);
		ResponseEntity<Product> responseProduct = new RestTemplate().exchange(URI+"/products/1", HttpMethod.PUT, requestEntity, Product.class);
		System.out.println("product saved ::: "+responseProduct.getBody());
		System.out.println("******testUpdateProduct - end************");
	}
	
	
	//delete product by id
	
	private static void testDeleteProductById() {
		System.out.println("******testDeleteProductById - start************");
		new RestTemplate().delete(URI+"/products/1");
		System.out.println("******testDeleteProductById - end************");
	}
	
	//delete productall
	
	private static void testDeleteAllProduct() {
		System.out.println("******testDeleteAllProduct - start************");
		new RestTemplate().delete(URI+"/products");
		System.out.println("******testDeleteAllProduct - end************");
	}
	
	
	
	private static HttpEntity<Product> buildRequestEntity(Product product) {
		return new HttpEntity<>(product, buildHttpHeaders()); 
	}

	private static HttpHeaders buildHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", "application/json");
		headers.set("Accept", "application/json");
		return headers;
	}

	public static void main(String[] args) {
		getMessage();
		
		try {
			testCreateProduct();
		}catch(Exception e) {
			System.out.println(e);
		}
		try {
			testGetAllProducts();
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
			testGetProductById();
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
			testUpdateProduct();
		}catch(Exception e) {
			System.out.println(e);
		}
		try {
			testDeleteProductById();
		}catch(Exception e) {
			System.out.println(e);
		}
		try {
			testDeleteAllProduct();
		}catch(Exception e) {
			System.out.println(e);
		}
	}

}
