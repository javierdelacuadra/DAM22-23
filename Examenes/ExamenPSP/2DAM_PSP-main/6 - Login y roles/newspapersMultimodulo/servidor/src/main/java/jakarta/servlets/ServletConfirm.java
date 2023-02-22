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

@WebServlet(name = RestConstants.CONFIRM_SERVLET, value = RestConstants.PATH_ACTIVATE)
public class ServletConfirm extends HttpServlet {

    private final LoginServicesImpl loginServices;

    @Inject
    public ServletConfirm(LoginServicesImpl loginServices) {
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
            if (loginServices.validate(token)) {
                template = RestConstants.EXITO;
                context.setVariable(RestConstants.MENSAJE_EXITO, RestConstants.CUENTA_VALIDADA_CON_EXITO_YA_PUEDES_HACER_LOGIN_NORMALMENTE);
            }
        } catch (Exception e) {
            context.setVariable(RestConstants.ERROR_MESSAGE, e.getMessage());
            template = RestConstants.ERROR;
        }
        templateEngine.process(template, context, response.getWriter());
    }

}
