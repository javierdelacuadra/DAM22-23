package ui.consola;

import com.google.gson.Gson;
import dao.DaoJugadores;
import di.ApiProducer;
import servicios.ServiciosJugadores;

public class VerUnEquipo {
    public static void main(String[] args) {
        ApiProducer api = new ApiProducer();
        ServiciosJugadores serviciosJugadores = new ServiciosJugadores(new DaoJugadores(api.getEquiposApi(api.getRetrofit()), new Gson()));
        String nombreEquipo = "Brasil";
        serviciosJugadores.verUnEquipo(nombreEquipo).blockingSubscribe(jugadores ->
                {
                    if (jugadores.isRight()) {
                        System.out.println(jugadores.get().toString());
                    } else {
                        System.out.println(jugadores.getLeft());
                    }
                }
        );
    }
}
