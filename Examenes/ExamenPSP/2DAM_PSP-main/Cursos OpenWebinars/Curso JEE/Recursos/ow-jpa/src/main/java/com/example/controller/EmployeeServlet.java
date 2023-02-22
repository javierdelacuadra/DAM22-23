package com.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.example.domain.Employee;
import com.example.service.EmployeeService;
import com.example.service.EmployeeServiceImpl;

/**
 * Servlet implementation class EmployeeServlet
 */
@WebServlet(name="EmployeeServlet", value ="/employees")
public class EmployeeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private EmployeeService service = new EmployeeServiceImpl();
	
	public EmployeeServlet() {}
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    	
//    	resp.setContentType("text/html");
//    	resp.getWriter().println("<html><head><title>Primer servlet</title></head>"
//				+ "<body bgcolor=\"#ffbe86\"><div style='color:white; text-align:center; position:absolute; top:50%;"
//				+ "left:50%; width:450px; margin-left:-225px; height:100px; margin-top:-50px; line-height:70px; border-style: groove;'>"
//				+ "<p><h3>HELLO WORLD!</h3></p></div></body></html>");
    	
    	resp.setContentType("application/json");
    	resp.getWriter().println("{ \"message\": \" Hola mundo desde servlet \"}");
    	
    	
//    	resp.getWriter().append("Hola mundo from employees");
    	
    	
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    	String email = req.getParameter("email");
    	String nif = req.getParameter("nif");
    	Integer age = Integer.valueOf(req.getParameter("age"));
    	
    	Employee employee = new Employee(null, email, nif, age);
    	this.service.create(employee);
    	
    	System.out.println(email);
    	System.out.println(nif);
    	
    }
    
    
    
}

