package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "juego", value = "/juego")
public class ServletJuego extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String snumero = req.getParameter("numero");
        String mensaje = "";
        var session = req.getSession();
        var intentos = (Integer) session.getAttribute("intentos");
        var numeroSecreto = (Integer) session.getAttribute("numeroSecreto");
        if (snumero != null && !snumero.isEmpty()) {
            try {
                var numero = Integer.parseInt(snumero);
                if (numeroSecreto == null || intentos == null) {
                    numeroSecreto = (int) (Math.random() * 100) + 1;
                    intentos = 10;
                    session.setAttribute("numeroSecreto", numeroSecreto);
                    session.setAttribute("intentos", intentos);
                }
                if (numero == numeroSecreto) {
                    mensaje = "¡Has acertado!";session.setAttribute("numeroSecreto", null);
                    session.setAttribute("intentos", null);
                } else if (numero < numeroSecreto) {
                    intentos--;
                    session.setAttribute("intentos", intentos);
                    mensaje = "El número secreto es mayor";
                } else {
                    intentos--;
                    session.setAttribute("intentos", intentos);
                    mensaje = "El número secreto es menor";
                }
                if (intentos == 0) {
                    mensaje = "¡Has perdido! El número secreto era " + numeroSecreto;
                    session.setAttribute("numeroSecreto", null);
                    session.setAttribute("intentos", null);
                }
            } catch (NumberFormatException e) {
                mensaje = "El número introducido no es válido";
            }
            session.setAttribute("mensaje", mensaje);
            resp.sendRedirect("juego.jsp");
        }
    }
}