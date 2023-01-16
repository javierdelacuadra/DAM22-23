package jakarta.servlets;

import domain.servicios.ServiciosLogin;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlets.common.ConstantesServlets;

import java.io.IOException;

@WebServlet(name = ConstantesServlets.SERVLET_RECUPERAR_PASSWORD, urlPatterns = {ConstantesServlets.PASSWORD_RECOVERY})
public class ServletRecuperarPassword extends HttpServlet {

    private final ServiciosLogin serviciosLogin;

    @Inject
    public ServletRecuperarPassword(ServiciosLogin serviciosLogin) {
        this.serviciosLogin = serviciosLogin;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter(ConstantesServlets.PASSWORD);
        String code = req.getParameter(ConstantesServlets.CODE);
        if (password != null && code != null) {
            serviciosLogin.crearNuevaPassword(password, code);
            resp.sendRedirect(ConstantesServlets.SUCCESS_JSP);
        } else {
            resp.sendRedirect(ConstantesServlets.ERROR_JSP);
        }
    }

}