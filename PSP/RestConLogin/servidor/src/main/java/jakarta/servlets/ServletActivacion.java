package jakarta.servlets;

import domain.servicios.ServiciosLogin;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "ServletMail", urlPatterns = {"/activar"})
public class ServletActivacion extends HttpServlet {

    private final ServiciosLogin serviciosLogin;

    @Inject
    public ServletActivacion(ServiciosLogin serviciosLogin) {
        this.serviciosLogin = serviciosLogin;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        serviciosLogin.activarUsuario(code);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}