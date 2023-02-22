package com.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Double price;
	private Date registerDate;
	private LocalDate creationDate;
	private LocalDateTime creationTime;
	
	public Product() {
	}

	



	public Product(Long id, String name, Double price, Date registerDate, LocalDate creationDate,
			LocalDateTime creationTime) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.registerDate = registerDate;
		this.creationDate = creationDate;
		this.creationTime = creationTime;
	}





	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}





	public Date getRegisterDate() {
		return registerDate;
	}





	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}





	public LocalDate getCreationDate() {
		return creationDate;
	}





	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}





	public LocalDateTime getCreationTime() {
		return creationTime;
	}





	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}
	
	
}




