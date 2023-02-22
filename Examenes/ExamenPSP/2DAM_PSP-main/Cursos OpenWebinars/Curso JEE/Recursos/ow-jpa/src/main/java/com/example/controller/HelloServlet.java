package com.example.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet(name = "HelloServlet", value = "/hello")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	

    /**
     * Default constructor. 
     */
    public HelloServlet() {
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		// 1 - cargar datos
    	request.setAttribute("mensaje", "Hola mundo!");
    	
    	// 2 - delegar hacia la vista
		request.getRequestDispatcher("hello.jsp").forward(request, response);
		
	}



}
