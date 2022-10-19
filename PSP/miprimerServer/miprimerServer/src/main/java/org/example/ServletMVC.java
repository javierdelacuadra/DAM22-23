package org.example;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ServletMVC", value = "/ServletMVC")
public class ServletMVC extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String parameter = request.getParameter("numero");
        List<Integer> list = new ArrayList<>();
        if (parameter == null) {
            parameter = "0";
        }
        int numero = Integer.parseInt(parameter);
        for (int i = 0; i < numero; i++) {
            list.add(i+1);
        }
        request.setAttribute("list", list);
        request.getRequestDispatcher("primer.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
