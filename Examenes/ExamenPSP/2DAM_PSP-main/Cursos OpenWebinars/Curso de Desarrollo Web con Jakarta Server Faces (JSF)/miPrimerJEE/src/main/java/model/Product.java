package model;

import java.time.LocalDate;
import java.util.Date;

public class Product {

	private int id;
	private String name;
	private double price;
	private Date registerDate;
	private LocalDate expirationDate;


	public Product() {
		// TODO Auto-generated constructor stub
	}

	public Product(int id, String name, double price, Date registerDate, LocalDate expirationDate) {
		this.id = id;
		this.name = name;
		this.price = price; 
		this.registerDate = registerDate;
		this.expirationDate = expirationDate;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}
	
}
