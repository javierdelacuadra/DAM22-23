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

@WebServlet(name = ConstantesServlets.SERVLET_MAIL, urlPatterns = {ConstantesServlets.ACTIVAR})
public class ServletActivacion extends HttpServlet {


    private final ServiciosLogin serviciosLogin;

    @Inject
    public ServletActivacion(ServiciosLogin serviciosLogin) {
        this.serviciosLogin = serviciosLogin;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter(ConstantesServlets.CODE);
        serviciosLogin.activarUsuario(code);
        resp.getWriter().write(ConstantesServlets.ACTIVATED);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}