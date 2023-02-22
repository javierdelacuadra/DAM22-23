package com.example;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.enterprise.inject.Model;
import model.Product;

@Model
public class ProductBean implements Serializable{
	private List<Product> products;
	
	public ProductBean() {
		products = new ArrayList<Product>();
		
		products.add(new Product(1, "prod1", 100f, new Date(), LocalDate.now().plusDays(5)));
		products.add(new Product(2, "prod2", 200f, new Date(), LocalDate.now().plusDays(10)));
		products.add(new Product(3, "prod3", 300f, new Date(), LocalDate.now().plusDays(15)));
	}
	
	public List<Product> getProducts() {
		return products;
	}
}
