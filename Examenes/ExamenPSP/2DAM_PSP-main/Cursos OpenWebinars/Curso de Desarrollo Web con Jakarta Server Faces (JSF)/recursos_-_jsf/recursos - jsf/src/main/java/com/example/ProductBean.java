package com.example;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

@Model
public class ProductBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Product> products;

	
	public List<Product> getProducts() {
		return List.of(
				new Product(1L, "Silla", 49.99, new Date(), LocalDate.now(), LocalDateTime.now()),
				new Product(2L, "Mesa", 99.99, new Date(), LocalDate.now(), LocalDateTime.now()),
				new Product(3L, "Lámpara", 39.99, new Date(), LocalDate.now(), LocalDateTime.now())
				);
		
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	
	
	
}
