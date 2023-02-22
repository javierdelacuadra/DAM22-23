package com.example;

import java.io.Serializable;

import jakarta.enterprise.inject.Model;

@Model
public class UserBean implements Serializable{

	private String email;
	private String password;
	public UserBean() {}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
}
