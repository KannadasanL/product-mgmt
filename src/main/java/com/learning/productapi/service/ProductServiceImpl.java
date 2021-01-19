package com.learning.productapi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.learning.productapi.domain.Product;

@Service
public class ProductServiceImpl implements ProductService {

	private static HashMap<Integer, Product> products = new HashMap<>();
	private static HashMap<String, Integer> idNameMapping = new HashMap<>();

	private static int num = 0;


	public String getMessage() {
		return "Service is up and Running!!";
	}

	public List<Product> getAllProducts() {
		return new ArrayList<>(products.values());
	}

	public Product getProductById(int id) {
		Product product = products.get(id);
		return product;
	}

	public Product getProductByName(String name) {
		Product product = products.get(idNameMapping.get(name));
		return product;
	}

	public Product saveProduct(Product product) {
		synchronized (this) {
			product.setId(generateId());
			products.put(product.getId(), product);
			idNameMapping.put(product.getName(), product.getId());
			return product;
		}
	}

	private int generateId() {
		return ++num;
	}

	public void updateProduct(Product product) {
		synchronized (this) {
			products.put(product.getId(), product);
			idNameMapping.put(product.getName(), product.getId());
		}
	}

	@Override
	public void deleteProductById(int id) {
		synchronized (this) {
			idNameMapping.remove(products.get(id).getName());
			products.remove(id);
		}
	}

	@Override
	public void deleteAllProducts() {
		synchronized(this) {
			idNameMapping.clear();
			products.clear();
		}

	}

	public boolean isProductExists(Product product) {
		return (getProductByName(product.getName()) != null);
	}

}
