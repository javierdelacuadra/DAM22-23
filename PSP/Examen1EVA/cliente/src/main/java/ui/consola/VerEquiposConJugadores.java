package ui.consola;

import com.google.gson.Gson;
import dao.DaoJugadores;
import di.ApiProducer;
import servicios.ServiciosJugadores;

public class VerEquiposConJugadores {

    public static void main(String[] args) {
        ApiProducer api = new ApiProducer();
        ServiciosJugadores serviciosJugadores = new ServiciosJugadores(new DaoJugadores(api.getEquiposApi(api.getRetrofit()), new Gson()));
        serviciosJugadores.verTodosLosEquipos().blockingSubscribe(lists -> lists.forEach(System.out::println));
    }
}
