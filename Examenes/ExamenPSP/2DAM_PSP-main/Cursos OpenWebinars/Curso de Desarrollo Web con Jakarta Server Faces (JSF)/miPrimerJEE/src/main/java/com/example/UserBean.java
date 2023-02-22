package com.example;

import jakarta.enterprise.inject.Model;
import model.User;

@Model
public class UserBean {
	private User user;
	private String email;
	private String password;
	
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
