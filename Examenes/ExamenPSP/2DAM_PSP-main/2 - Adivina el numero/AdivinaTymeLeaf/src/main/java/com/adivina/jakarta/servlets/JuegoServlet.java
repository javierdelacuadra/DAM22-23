package com.adivina.jakarta.servlets;

import com.adivina.jakarta.common.JakartaConstants;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@WebServlet(name = JakartaConstants.JUEGO_SERVLET, value = JakartaConstants.JUEGO)
public class JuegoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        functionality(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        functionality(request, response);
    }
    private void functionality(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(
                JakartaConstants.TEMPLATE_ENGINE_ATTR);
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        WebContext context = new WebContext(webExchange);


        HttpSession session = request.getSession();

        String template = JakartaConstants.ERROR; // <-- Si no hay error con los params, se actualiza
        int vidas;
        if (session.getAttribute(JakartaConstants.VIDAS) == null) {
            template = beginGame(context, session);
        } else {
            //Ya había comenzado a jugar
            vidas = (int) session.getAttribute(JakartaConstants.VIDAS);
            String number = request.getParameter(JakartaConstants.NUMBER);
            if (isNumeric(number)) {
                int num = Integer.parseInt(number);
                int numRandom = (int) session.getAttribute(JakartaConstants.NUM_RANDOM);
                if (num == numRandom) {
                    // Termina el juego - Gano
                    context.setVariable(JakartaConstants.VIDAS, vidas);
                    context.setVariable(JakartaConstants.NUM_RANDOM, numRandom);
                    session.setAttribute(JakartaConstants.VIDAS, null);
                    template = JakartaConstants.TEMPLATE_GANASTE;
                } else {
                    template = noGano(context, session, vidas, num, numRandom);
                }
            } else {
                // Else hubo un error, no se actualiza el template
                // vidas a null para que se le regenere el juego
                session.setAttribute(JakartaConstants.VIDAS, null);
                context.setVariable(JakartaConstants.NUM_ERROR, number);
            }
        }
        // Redirigimos a la pantalla
        templateEngine.process(template, context, response.getWriter());
    }

    private static String noGano(WebContext context, HttpSession session, int vidas, int num, int numRandom) {
        String template;
        // Continua el juego
        vidas--;
        if (vidas >= JakartaConstants.BOUND_VIDAS) {
            List<Integer> lista = (List<Integer>) session.getAttribute(JakartaConstants.LISTA);
            lista.add(num);
            // VEMOS SI ES MAS ALTO
            String mensajeHelp = JakartaConstants.MENSAJE_HELP_MAS_BAJO;
            if (num < numRandom) {
                mensajeHelp = JakartaConstants.MENSAJE_HELP_MAS_ALTO;
            }

            context.setVariable(JakartaConstants.HELP, mensajeHelp);
            context.setVariable(JakartaConstants.LISTA, lista);
            context.setVariable(JakartaConstants.VIDAS, vidas);
            session.setAttribute(JakartaConstants.VIDAS, vidas);


            template = JakartaConstants.TEMPLATE_JUEGO;

        } else {
            // Termina el juego - Perdio
//            context.setVariable(JakartaConstants.VIDAS, null);
            session.setAttribute(JakartaConstants.VIDAS, null);
            context.setVariable(JakartaConstants.NUM_RANDOM, numRandom);
            template = JakartaConstants.TEMPLATE_PERDISTE;
        }
        return template;
    }

    private static String beginGame(WebContext context, HttpSession session) {
        String template;
        List<Integer> lista;
        int vidas;
        vidas = JakartaConstants.ORIGIN_VIDAS;
        // Comienza el juego
        Random random = new Random(); //SEGUIR POR ACA
        lista = new ArrayList<>();
        context.setVariable(JakartaConstants.LISTA, lista);
        int randomNum = random.nextInt(JakartaConstants.ORIGIN_NUMBER, JakartaConstants.BOUND_NUMBER);
        context.setVariable(JakartaConstants.NUM_RANDOM, randomNum);
        context.setVariable(JakartaConstants.VIDAS, vidas);
        session.setAttribute(JakartaConstants.LISTA, lista);
        session.setAttribute(JakartaConstants.NUM_RANDOM, randomNum);
        session.setAttribute(JakartaConstants.VIDAS, vidas);
        template = JakartaConstants.TEMPLATE_JUEGO;
        return template;
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


}
