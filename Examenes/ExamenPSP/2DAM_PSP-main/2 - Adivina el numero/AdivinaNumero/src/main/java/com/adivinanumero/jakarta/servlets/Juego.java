package com.adivinanumero.jakarta.servlets;

import com.adivinanumero.jakarta.common.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@WebServlet(name = "Juego", value = "/Juego")
public class Juego extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        functionality(request, response);
    }

    private void functionality(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int vidas = JakartaConstants.ORIGIN_VIDAS;
        List<Integer> lista;
        if (session.getAttribute(JakartaConstants.VIDAS) == null) {
            // Comienza el juego
            Random random = new Random(); //SEGUIR POR ACA
            lista = new ArrayList<>();
            session.setAttribute(JakartaConstants.LISTA, lista);
            session.setAttribute(JakartaConstants.NUM_RANDOM, random.nextInt(JakartaConstants.ORIGIN_NUMBER, JakartaConstants.BOUND_NUMBER));
            session.setAttribute(JakartaConstants.VIDAS, vidas);
            request.getRequestDispatcher(JakartaConstants.JUEGO_INDEX_JSP).forward(request, response);
        } else {
            //Sigue jugando
            vidas = (int) session.getAttribute(JakartaConstants.VIDAS);
            String number = request.getParameter(JakartaConstants.NUMBER);
            if (isNumeric(number)) {
                int num = Integer.parseInt(number);
                int numRandom = (int) session.getAttribute(JakartaConstants.NUM_RANDOM);
                if (num == numRandom) {
                    // Termina el juego - Gano
                    session.setAttribute(JakartaConstants.VIDASLEFT, vidas);
                    session.setAttribute(JakartaConstants.VIDAS, null);
                    request.getRequestDispatcher(JakartaConstants.JUEGO_GANASTE_JSP).forward(request, response);
                } else {
                    // Continua el juego
                    vidas--;
                    if (vidas >= JakartaConstants.BOUND_VIDAS) {
                        lista = (List<Integer>) session.getAttribute(JakartaConstants.LISTA);
                        lista.add(num);
                        session.setAttribute(JakartaConstants.LISTA, lista);
                        session.setAttribute(JakartaConstants.VIDAS, vidas);
                        request.getRequestDispatcher(JakartaConstants.JUEGO_INDEX_JSP).forward(request, response);

                    } else {
                        // Termina el juego - Perdio
                        session.setAttribute(JakartaConstants.VIDAS, null);
                        request.getRequestDispatcher(JakartaConstants.JUEGO_PERDISTE_JSP).forward(request, response);
                    }
                }
            } else {
                // Numero ingresado no valido
                request.getRequestDispatcher(JakartaConstants.ERROR_HTML).forward(request, response);
            }
        }
    }

    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        functionality(request, response);
    }
}
