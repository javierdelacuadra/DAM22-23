package com.example;

import java.io.Serializable;

import jakarta.enterprise.inject.Model;

//@Named
//@RequestScoped

@Model
public class HelloBean implements Serializable{

	private String message;
	
	public HelloBean() {
		this.message = "Ejemplo bean";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String goToUserList() {
		return "user-list";
	}
}
