package jakarta.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "pagina", value = {"/pagina"})
public class ServletColores extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String color = req.getParameter("colorUsuario");

        var session = req.getSession();
        var colorUsuario = session.getAttribute("colorUsuario");
        var colorGlobal = session.getAttribute("colorGlobal");

        if (color != null && !color.isEmpty()) {
            session.setAttribute("colorUsuario", colorUsuario);
            if (colorGlobal == null) {
                session.setAttribute("colorGlobal", "green");
            }
        }
        resp.sendRedirect("pagina.jsp");
    }


}
