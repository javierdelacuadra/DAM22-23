package jakarta.servlets;

import domain.servicios.ServiciosLogin;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ServletRecuperarPassword", urlPatterns = {"/passwordRecovery"})
public class ServletRecuperarPassword extends HttpServlet {
    private final ServiciosLogin serviciosLogin;

    @Inject
    public ServletRecuperarPassword(ServiciosLogin serviciosLogin) {
        this.serviciosLogin = serviciosLogin;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password");
        String code = req.getParameter("code");
        if (password != null && code != null) {
            serviciosLogin.crearNuevaPassword(password, code);
            resp.sendRedirect("success.jsp");
        } else {
            resp.sendRedirect("error.jsp");
        }
    }

}