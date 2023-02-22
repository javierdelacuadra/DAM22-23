package jakarta.servlets;

import domain.servicios.impl.LoginServicesImpl;
import jakarta.common.RestConstants;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

import static jakarta.listeners.ThymeLeafListener.TEMPLATE_ENGINE_ATTR;

@WebServlet(name = RestConstants.RESEND_SERVLET, value = RestConstants.PATH_RESEND)
public class ServletResend extends HttpServlet {

    private final LoginServicesImpl loginServices;

    @Inject
    public ServletResend(LoginServicesImpl loginServices) {
        this.loginServices = loginServices;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        functionality(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        functionality(req, resp);
    }

    private void functionality(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(
                TEMPLATE_ENGINE_ATTR);
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        WebContext context = new WebContext(webExchange);
        String token = request.getParameter(RestConstants.TOKEN);
        String template = RestConstants.INDEX;
        try {
            if (loginServices.resetTime(token)) {
                context.setVariable(RestConstants.MENSAJE_EXITO, RestConstants.SE_HA_RENOVADO_EL_TIEMPO_DE_VALIDACION_SE_HA_ENVIADO_UN_MAIL_PARA_QUE_TE_VALIDES);
                template = RestConstants.EXITO;
            }
        } catch (Exception e) {
            context.setVariable(RestConstants.ERROR_MESSAGE, e.getMessage());
            template = RestConstants.ERROR;
        }
        templateEngine.process(template, context, response.getWriter());
    }



}