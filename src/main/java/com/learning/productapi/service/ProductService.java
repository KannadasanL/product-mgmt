package com.learning.productapi.service;

import java.util.List;

import com.learning.productapi.domain.Product;

public interface ProductService {
	String getMessage();
	List<Product> getAllProducts();
	Product getProductById(int id);
	Product getProductByName(String name);
	Product saveProduct(Product product);
	void updateProduct(Product product);
	void deleteProductById(int id);
	void deleteAllProducts();
	boolean isProductExists(Product product);
}
