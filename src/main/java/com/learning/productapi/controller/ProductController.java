package com.learning.productapi.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.learning.productapi.domain.Product;
import com.learning.productapi.service.ProductService;
import com.learning.productapi.util.ProductCustomMessage;



@RestController
@RequestMapping("/product-mgmt")
public class ProductController {

	@Autowired
	private ProductService productService;	
	
	public static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ProductController.class);
	
	//public static final Logger logger = getLogg
	
	@GetMapping("/health")
	public ResponseEntity<String> getMessage() {
		String healthMessage = productService.getMessage();
		logger.info("healthMessage '{}' ", healthMessage);
		return new ResponseEntity<>(healthMessage, HttpStatus.OK);
	}
	
	@GetMapping("/products")
	public ResponseEntity<?> getAllProducts() {
		List<Product> productList = productService.getAllProducts();
		if(productList.size() <= 0) {
			logger.info("no products available");
			return new ResponseEntity<>(new ProductCustomMessage("No Products Available at this time"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(productList, HttpStatus.OK);
	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<?> getProductById(@PathVariable(value="id") int id) {
		Product product = productService.getProductById(id);
		if(null != product) {
			logger.info("Getting product for product id: {} and the product is: {}",id, product);
			return new ResponseEntity<>(product, HttpStatus.OK);
		}
		return new ResponseEntity<>(new ProductCustomMessage("No product available for this product id "+id), HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/products/")
	public ResponseEntity<?> getProductByName(@RequestParam(value="name") String name) {
		Product product = productService.getProductByName(name);		
		if(null != product) {
			logger.info("Getting product for product name: {} and the product is: {}",name, product);
			return new ResponseEntity<>(product, HttpStatus.OK);
		}
		return new ResponseEntity<>(new ProductCustomMessage("No product available for this product name "+name), HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/products")
	public ResponseEntity<?> saveProduct( @RequestBody Product product) {
		if (null == product) {
			logger.info("product is null!!");
			return new ResponseEntity<>(new ProductCustomMessage("product is empty or null"), HttpStatus.BAD_REQUEST);
		}
		if(!productService.isProductExists(product)) {
			Product prodt = productService.saveProduct(product);
			logger.info("product {} is saved successfully!!", product);
			return new ResponseEntity<>(prodt, HttpStatus.CREATED);
		}
		logger.error("product {} already existing!!!", product);
		return new ResponseEntity<>(new ProductCustomMessage("Product "+product.getName()+ " is already exists!!"), HttpStatus.CONFLICT);
	}
	
	@PutMapping("/products/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestBody Product product) {
		
		Product existingProduct = productService.getProductById(id);
		if(null!=existingProduct) {
			product.setId(id);
			productService.updateProduct(product);
			logger.info("product {} is updated", product);
			return new ResponseEntity<>(product, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(new ProductCustomMessage("Product is not existing to update!!"), HttpStatus.NOT_FOUND);
		
	}
	
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<?> deleteProductById(@PathVariable int id) {
		productService.deleteProductById(id);
		logger.info("product is deleted for id : {}", id);
		return new ResponseEntity<>("Product is deleted successfully!!", HttpStatus.OK);
	}
	
	
	@DeleteMapping("/products")
	public ResponseEntity<?> deleteProductById() {
		productService.deleteAllProducts();
		logger.info("All products deleted successfully!!");
		return new ResponseEntity<>("All Products are deleted successfully!!", HttpStatus.OK);
	}
	
	
	
	
	
}
