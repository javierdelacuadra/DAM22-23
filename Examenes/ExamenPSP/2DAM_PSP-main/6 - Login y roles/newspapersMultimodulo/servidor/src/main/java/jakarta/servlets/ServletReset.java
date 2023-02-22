package jakarta.servlets;

import domain.servicios.impl.LoginServicesImpl;
import jakarta.common.RestConstants;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

import static jakarta.listeners.ThymeLeafListener.TEMPLATE_ENGINE_ATTR;

@WebServlet(name = RestConstants.SERVLET_RESET, value = RestConstants.PATH_RESET)
public class ServletReset extends HttpServlet {


    private final LoginServicesImpl loginServices;

    @Inject
    public ServletReset(LoginServicesImpl loginServices) {
        this.loginServices = loginServices;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        funtionality(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        funtionality(request, response);
    }

    private void funtionality(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(
                TEMPLATE_ENGINE_ATTR);
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        WebContext context = new WebContext(webExchange);
        HttpSession session = request.getSession();
        String template;
        // me guardo el token que viene por parametro
        String token = request.getParameter(RestConstants.TOKEN);
        if (token != null && session.getAttribute(RestConstants.TOKEN) == null) {
            session.setAttribute(RestConstants.TOKEN,token);
        } else {
            token = session.getAttribute(RestConstants.TOKEN).toString();
        }
        if (token != null) {
            Object pass = request.getParameter(RestConstants.NEW_PASS);
            if (pass == null){
                // aun no la reinicio, lo mando al template
                template = RestConstants.RESET;
            } else {
                // la guardo
                loginServices.resetPass(token, pass.toString());
                context.setVariable(RestConstants.MENSAJE_EXITO, RestConstants.SE_HA_REINICIADO_LA_CONTRASENA_YA_PUEDES_ACCEDER_CON_TU_NUEVA_CONTRASENA);
                template = RestConstants.EXITO;
            }
        } else {
            context.setVariable(RestConstants.ERROR_MESSAGE, RestConstants.NO_SE_HA_PODIDO_VALIDAR_EL_TOKEN);
            template = RestConstants.ERROR;
        }
        templateEngine.process(template, context, response.getWriter());
    }
}
