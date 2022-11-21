package ui.consola;

import com.google.gson.Gson;
import dao.DaoJugadores;
import di.ApiProducer;
import modelo.Jugador;
import servicios.ServiciosJugadores;

public class AddJugador {

    public static void main(String[] args) {
        ApiProducer api = new ApiProducer();
        ServiciosJugadores serviciosJugadores = new ServiciosJugadores(new DaoJugadores(api.getEquiposApi(api.getRetrofit()), new Gson()));
        Jugador jugador = new Jugador("Cristiano Ronaldo", "Portugal");
        serviciosJugadores.addJugador(jugador).blockingSubscribe(jugadors ->
                {
                    if (jugadors.isRight()) {
                        System.out.println(jugadors.get().toString());
                    } else {
                        System.out.println(jugadors.getLeft());
                    }
                }
        );
        serviciosJugadores.verJugadoresDeUnEquipo("Portugal").blockingSubscribe(lists -> lists.forEach(System.out::println));
    }
}
