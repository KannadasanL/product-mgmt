package com.learning.productapi.controller;

import com.learning.productapi.domain.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @LocalServerPort
    int randomPort;

    static Product p1;
    static Product p2;
    static Product p3;
    static Product p4;
    static Product p5;

    static String URI;


    @BeforeAll
    public static void init() {
        p1 = new Product("iphone6s","apple","china",850.23f);
        p2 = new Product("TV","sony","Japan",1200.50f);
        p3 = new Product("CellPhone","One+","china",900.80f);
        p4 = new Product("AirPurifier","DataSun","Korea",850.23f);
        p5 = new Product("MixerGrinder","Ultra","India",220.46f);
    }


    @Test
    void testGetMessage() {
        URI = "http://localhost:"+randomPort+"/product-mgmt";
        String message = testRestTemplate.getForObject(URI+"/health", String.class);
        assertThat(message.contains("Service is up and Running!!"));

    }

    @Test
    void testGetAllProducts() {
        URI = "http://localhost:"+randomPort+"/product-mgmt";

        HttpEntity<Product> httpEntityRequest1 = new HttpEntity<>(p1);
        HttpEntity<Product> httpEntityRequest2 = new HttpEntity<>(p2);
        HttpEntity<Product> httpEntityRequest3 = new HttpEntity<>(p3);
        HttpEntity<Product> httpEntityRequest4 = new HttpEntity<>(p4);
        HttpEntity<Product> httpEntityRequest5 = new HttpEntity<>(p5);

        ResponseEntity<Product> savedProduct1 = testRestTemplate.postForEntity(URI+"/products", httpEntityRequest1, Product.class);
        ResponseEntity<Product> savedProduct2 = testRestTemplate.postForEntity(URI+"/products", httpEntityRequest2, Product.class);
        ResponseEntity<Product> savedProduct3 = testRestTemplate.postForEntity(URI+"/products", httpEntityRequest3, Product.class);
        ResponseEntity<Product> savedProduct4 = testRestTemplate.postForEntity(URI+"/products", httpEntityRequest4, Product.class);
        ResponseEntity<Product> savedProduct5 = testRestTemplate.postForEntity(URI+"/products", httpEntityRequest5, Product.class);

        assertThat(savedProduct1.getStatusCode().equals(HttpStatus.CREATED));
        assertThat(savedProduct1.getBody().getName().equals("iphone6s"));
        assertThat(savedProduct2.getStatusCode().equals(HttpStatus.CREATED));
        assertThat(savedProduct2.getBody().getName().equals("TV"));
        assertThat(savedProduct3.getStatusCode().equals(HttpStatus.CREATED));
        assertThat(savedProduct3.getBody().getName().equals("CellPhone"));
        assertThat(savedProduct4.getStatusCode().equals(HttpStatus.CREATED));
        assertThat(savedProduct4.getBody().getName().equals("AirPurifier"));
        assertThat(savedProduct5.getStatusCode().equals(HttpStatus.CREATED));
        assertThat(savedProduct5.getBody().getName().equals("MixerGrinder"));



        HttpEntity<String> requestEntity = new HttpEntity<>("");
        ResponseEntity<Object> products =testRestTemplate.exchange(URI+"/products", HttpMethod.GET, requestEntity, Object.class, new HashMap<>());

        assertThat(products.getStatusCode().equals(HttpStatus.OK));
        //assertThat(products.getBody().)


        testRestTemplate.delete(URI+"/products/"+savedProduct1.getBody().getId());
        testRestTemplate.delete(URI+"/products/"+savedProduct2.getBody().getId());
        testRestTemplate.delete(URI+"/products/"+savedProduct3.getBody().getId());
        testRestTemplate.delete(URI+"/products/"+savedProduct4.getBody().getId());
        testRestTemplate.delete(URI+"/products/"+savedProduct5.getBody().getId());

    }

    @Test
    void testGetProductById() {
        URI = "http://localhost:"+randomPort+"/product-mgmt";
        HttpEntity<Product> httpEntityRequest2 = new HttpEntity<>(p2);
        ResponseEntity<Product> savedProduct2 = testRestTemplate.postForEntity(URI+"/products", httpEntityRequest2, Product.class);
        int id = savedProduct2.getBody().getId();
        Product product = testRestTemplate.getForObject(URI+"/products/"+id, Product.class);
        assertEquals(product.getId(), id);
        assertEquals(product.getBrand(), p2.getBrand());

        testRestTemplate.delete(URI+"/products/"+id);
    }

    @Test
    void testGetProductByIdNotFound() {
        URI = "http://localhost:"+randomPort+"/product-mgmt";
        HttpEntity<Product> httpEntityRequest2 = new HttpEntity<>(p2);
        ResponseEntity<Product> savedProduct2 = testRestTemplate.postForEntity(URI+"/products", httpEntityRequest2, Product.class);
        int id = savedProduct2.getBody().getId();
        HttpEntity<String> requestEntity = new HttpEntity<>("");
        ResponseEntity<Object> product =testRestTemplate.exchange(URI+"/products/2", HttpMethod.GET, requestEntity, Object.class, new HashMap<>());
        assertThat(product.getStatusCode().equals(HttpStatus.NOT_FOUND));
        testRestTemplate.delete(URI+"/products/"+id);
    }

    @Test
    void testGetProductByName() {
        URI = "http://localhost:"+randomPort+"/product-mgmt";
        HttpEntity<Product> httpEntityRequest3 = new HttpEntity<>(p3);
        ResponseEntity<Product> savedProduct3 = testRestTemplate.postForEntity(URI+"/products", httpEntityRequest3, Product.class);
        int id = savedProduct3.getBody().getId();
        String name = savedProduct3.getBody().getName();
        Product product = testRestTemplate.getForObject(URI+"/products/?name="+name, Product.class);
        assertEquals(product.getId(), id);
        assertEquals(product.getName(), p3.getName());

        testRestTemplate.delete(URI+"/products/"+id);
    }

    @Test
    void testSaveProduct() {
        URI = "http://localhost:"+randomPort+"/product-mgmt";

        HttpEntity<Product> httpEntityRequest1 = new HttpEntity<>(p1);
        HttpEntity<Product> httpEntityRequest2 = new HttpEntity<>(p2);
        HttpEntity<Product> httpEntityRequest3 = new HttpEntity<>(p3);
        HttpEntity<Product> httpEntityRequest4 = new HttpEntity<>(p4);
        HttpEntity<Product> httpEntityRequest5 = new HttpEntity<>(p5);

        ResponseEntity<Product> savedProduct1 = testRestTemplate.postForEntity(URI+"/products", httpEntityRequest1, Product.class);
        ResponseEntity<Product> savedProduct2 = testRestTemplate.postForEntity(URI+"/products", httpEntityRequest2, Product.class);
        ResponseEntity<Product> savedProduct3 = testRestTemplate.postForEntity(URI+"/products", httpEntityRequest3, Product.class);
        ResponseEntity<Product> savedProduct4 = testRestTemplate.postForEntity(URI+"/products", httpEntityRequest4, Product.class);
        ResponseEntity<Product> savedProduct5 = testRestTemplate.postForEntity(URI+"/products", httpEntityRequest5, Product.class);

        assertThat(savedProduct1.getStatusCode().equals(HttpStatus.CREATED));
        assertThat(savedProduct1.getBody().getName().equals("iphone6s"));

        testRestTemplate.delete(URI+"/products/"+savedProduct1.getBody().getId());

        assertThat(savedProduct2.getStatusCode().equals(HttpStatus.CREATED));
        assertThat(savedProduct2.getBody().getName().equals("TV"));

        testRestTemplate.delete(URI+"/products/"+savedProduct2.getBody().getId());

        assertThat(savedProduct3.getStatusCode().equals(HttpStatus.CREATED));
        assertThat(savedProduct3.getBody().getName().equals("CellPhone"));

        testRestTemplate.delete(URI+"/products/"+savedProduct3.getBody().getId());

        assertThat(savedProduct4.getStatusCode().equals(HttpStatus.CREATED));
        assertThat(savedProduct4.getBody().getName().equals("AirPurifier"));

        testRestTemplate.delete(URI+"/products/"+savedProduct4.getBody().getId());

        assertThat(savedProduct5.getStatusCode().equals(HttpStatus.CREATED));
        assertThat(savedProduct5.getBody().getName().equals("MixerGrinder"));

        testRestTemplate.delete(URI+"/products/"+savedProduct5.getBody().getId());

    }

    @Test
    void testUpdateProduct() {
        URI = "http://localhost:"+randomPort+"/product-mgmt";

        HttpEntity<Product> httpEntityRequest1 = new HttpEntity<>(p1);
        HttpEntity<Product> httpEntityRequest2 = new HttpEntity<>(p2);
        HttpEntity<Product> httpEntityRequest3 = new HttpEntity<>(p3);
        ResponseEntity<Product> savedProduct1 = testRestTemplate.postForEntity(URI+"/products", httpEntityRequest1, Product.class);
        ResponseEntity<Product> savedProduct2 = testRestTemplate.postForEntity(URI+"/products", httpEntityRequest2, Product.class);
        ResponseEntity<Product> savedProduct3 = testRestTemplate.postForEntity(URI+"/products", httpEntityRequest3, Product.class);

        assertThat(savedProduct1.getStatusCode().equals(HttpStatus.CREATED));
        assertThat(savedProduct1.getBody().getName().equals("iphone6s"));

        assertThat(savedProduct2.getStatusCode().equals(HttpStatus.CREATED));
        assertThat(savedProduct2.getBody().getName().equals("TV"));

        assertThat(savedProduct3.getStatusCode().equals(HttpStatus.CREATED));
        assertThat(savedProduct3.getBody().getName().equals("CellPhone"));

        Product p = new Product("iphone7plus","apple","india", 950f);
        HttpEntity<Product> requestEntity = new HttpEntity<>(p);

        ResponseEntity<Product> updatedProduct = testRestTemplate.exchange(URI+"/products/"+savedProduct1.getBody().getId(), HttpMethod.PUT, requestEntity, Product.class);

        assertThat(updatedProduct.getStatusCode().equals(HttpStatus.OK));
        assertThat(updatedProduct.getBody().getBrand().equals("india"));

        testRestTemplate.delete(URI+"/products/"+savedProduct1.getBody().getId());
        testRestTemplate.delete(URI+"/products/"+savedProduct2.getBody().getId());
        testRestTemplate.delete(URI+"/products/"+savedProduct3.getBody().getId());

    }

    @Test
    void testDeleteProductByIdInt() {
        URI = "http://localhost:"+randomPort+"/product-mgmt";

        HttpEntity<Product> httpEntityRequest1 = new HttpEntity<>(p1);
        ResponseEntity<Product> savedProduct1 = testRestTemplate.postForEntity(URI+"/products", httpEntityRequest1, Product.class);
        assertThat(savedProduct1.getStatusCode().equals(HttpStatus.CREATED));
        assertThat(savedProduct1.getBody().getName().equals("iphone6s"));
        testRestTemplate.delete(URI+"/products/"+savedProduct1.getBody().getId());
    }

    @Test
    void testDeleteProductById() {
        URI = "http://localhost:"+randomPort+"/product-mgmt";

        HttpEntity<Product> httpEntityRequest1 = new HttpEntity<>(p1);
        HttpEntity<Product> httpEntityRequest2 = new HttpEntity<>(p2);
        HttpEntity<Product> httpEntityRequest3 = new HttpEntity<>(p3);
        HttpEntity<Product> httpEntityRequest4 = new HttpEntity<>(p4);
        HttpEntity<Product> httpEntityRequest5 = new HttpEntity<>(p5);

        ResponseEntity<Product> savedProduct1 = testRestTemplate.postForEntity(URI+"/products", httpEntityRequest1, Product.class);
        ResponseEntity<Product> savedProduct2 = testRestTemplate.postForEntity(URI+"/products", httpEntityRequest2, Product.class);
        ResponseEntity<Product> savedProduct3 = testRestTemplate.postForEntity(URI+"/products", httpEntityRequest3, Product.class);
        ResponseEntity<Product> savedProduct4 = testRestTemplate.postForEntity(URI+"/products", httpEntityRequest4, Product.class);
        ResponseEntity<Product> savedProduct5 = testRestTemplate.postForEntity(URI+"/products", httpEntityRequest5, Product.class);

        assertThat(savedProduct1.getStatusCode().equals(HttpStatus.CREATED));
        assertThat(savedProduct1.getBody().getName().equals("iphone6s"));

        assertThat(savedProduct2.getStatusCode().equals(HttpStatus.CREATED));
        assertThat(savedProduct2.getBody().getName().equals("TV"));

        assertThat(savedProduct3.getStatusCode().equals(HttpStatus.CREATED));
        assertThat(savedProduct3.getBody().getName().equals("CellPhone"));

        assertThat(savedProduct4.getStatusCode().equals(HttpStatus.CREATED));
        assertThat(savedProduct4.getBody().getName().equals("AirPurifier"));

        assertThat(savedProduct5.getStatusCode().equals(HttpStatus.CREATED));
        assertThat(savedProduct5.getBody().getName().equals("MixerGrinder"));

        testRestTemplate.delete(URI+"/products");

    }

}
