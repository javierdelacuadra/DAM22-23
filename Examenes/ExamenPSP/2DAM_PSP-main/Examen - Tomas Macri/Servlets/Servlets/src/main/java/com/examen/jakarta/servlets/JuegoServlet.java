package com.examen.jakarta.servlets;

import com.examen.jakarta.common.JakartaConstants;
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

        String colorSession;

        session.getAttribute("colorS");
        if (session.getAttribute("colorS") == null){
            //primera vez que entra o si no lo pasa
            colorSession="red";
        } else {
            colorSession = request.getParameter("colorS");
            if (colorSession == null){
                colorSession = "red";
            }
        }

        String colorGeneral = request.getParameter("colorG");
        if (colorGeneral != null && !colorGeneral.isBlank()) {
            getServletContext().setAttribute("colorG", colorGeneral);
        }


        // si no se dejo vacio el parametro de la sesion
        if (!colorSession.isBlank()) {
            session.setAttribute("colorS", colorSession);
        } else {
            // si quedo vacio que coja la que tenia
            colorSession = session.getAttribute("colorS").toString();
        }
        context.setVariable("colorS", colorSession);



        context.setVariable("colorG", getServletContext().getAttribute("colorG"));

        // Redirigimos a la pantalla
        templateEngine.process("juego", context, response.getWriter());
    }


}
