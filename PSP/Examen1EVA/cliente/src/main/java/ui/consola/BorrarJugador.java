package ui.consola;

import com.google.gson.Gson;
import dao.DaoJugadores;
import di.ApiProducer;
import servicios.ServiciosJugadores;

public class BorrarJugador {

    public static void main(String[] args) {
        ApiProducer api = new ApiProducer();
        ServiciosJugadores serviciosJugadores = new ServiciosJugadores(new DaoJugadores(api.getEquiposApi(api.getRetrofit()), new Gson()));
        String nombreJugador = "Messi";
        serviciosJugadores.borrarJugador(nombreJugador).blockingSubscribe(jugadors ->
                {
                    if (jugadors.isRight()) {
                        System.out.println("Jugador borrado");
                        System.out.println(jugadors.get().toString());
                    } else {
                        System.out.println(jugadors.getLeft());
                    }
                }
        );
    }
}
