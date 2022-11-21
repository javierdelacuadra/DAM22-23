package ui.consola;

import com.google.gson.Gson;
import dao.DaoJugadores;
import di.ApiProducer;
import servicios.ServiciosJugadores;

public class BorrarEquipo {

    public static void main(String[] args) {
        ApiProducer api = new ApiProducer();
        ServiciosJugadores serviciosJugadores = new ServiciosJugadores(new DaoJugadores(api.getEquiposApi(api.getRetrofit()), new Gson()));
        String nombre = "Argentina";
        serviciosJugadores.borrarEquipo(nombre).blockingSubscribe(equipo ->
                {
                    if (equipo.isRight()) {
                        System.out.println(equipo.get().toString());
                    } else {
                        System.out.println(equipo.getLeft());
                    }
                }
        );
    }
}
