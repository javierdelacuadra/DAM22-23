package org.example;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.common.Constantes;

import java.io.IOException;

@WebServlet(name = "juego", value = "/juego")
public class ServletJuego extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String snumero = req.getParameter(Constantes.NUMERO);
        String mensaje;
        var session = req.getSession();
        var intentos = (Integer) session.getAttribute(Constantes.INTENTOS);
        var numeroSecreto = (Integer) session.getAttribute(Constantes.NUMERO_SECRETO);
        if (snumero != null && !snumero.isEmpty()) {
            try {
                var numero = Integer.parseInt(snumero);
                if (numero > 100 || numero < 1) {
                    mensaje = Constantes.EL_NUMERO_DEBE_ESTAR_ENTRE_1_Y_100;
                } else {
                    if (numeroSecreto == null || intentos == null) {
                        numeroSecreto = (int) (Math.random() * 100) + 1;
                        intentos = 10;
                        session.setAttribute(Constantes.NUMERO_SECRETO, numeroSecreto);
                        session.setAttribute(Constantes.INTENTOS, intentos);
                    }
                    if (numero == numeroSecreto) {
                        mensaje = Constantes.HAS_ACERTADO;
                        session.setAttribute(Constantes.NUMERO_SECRETO, null);
                        session.setAttribute(Constantes.INTENTOS, null);
                    } else if (numero < numeroSecreto) {
                        intentos--;
                        session.setAttribute(Constantes.INTENTOS, intentos);
                        mensaje = Constantes.EL_NUMERO_SECRETO_ES_MAYOR_TE_QUEDAN + intentos + Constantes.INTENTOS_CON_ESPACIO;
                    } else {
                        intentos--;
                        session.setAttribute(Constantes.INTENTOS, intentos);
                        mensaje = Constantes.EL_NUMERO_SECRETO_ES_MENOR_TE_QUEDAN + intentos + Constantes.INTENTOS_CON_ESPACIO;
                    }
                    if (intentos == 0) {
                        mensaje = Constantes.HAS_PERDIDO_EL_NUMERO_SECRETO_ERA + numeroSecreto;
                        session.setAttribute(Constantes.NUMERO_SECRETO, null);
                        session.setAttribute(Constantes.INTENTOS, null);
                    }
                }
            } catch (NumberFormatException e) {
                mensaje = Constantes.EL_NUMERO_INTRODUCIDO_NO_ES_VALIDO;
            }
        } else {
            mensaje = Constantes.ESCRIBE_UN_NUMERO_PARA_EMPEZAR_A_JUGAR;
        }
        session.setAttribute(Constantes.MENSAJE, mensaje);
        resp.sendRedirect(Constantes.JUEGO_JSP);
    }
}