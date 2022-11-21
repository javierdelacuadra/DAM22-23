package ui.consola;

import com.google.gson.Gson;
import dao.DaoJugadores;
import di.ApiProducer;
import servicios.ServiciosJugadores;

public class VerJugadoresDeUnEquipo {

    public static void main(String[] args) {
        ApiProducer api = new ApiProducer();
        ServiciosJugadores serviciosJugadores = new ServiciosJugadores(new DaoJugadores(api.getEquiposApi(api.getRetrofit()), new Gson()));
        String nombreEquipo = "Brasil";
        serviciosJugadores.verJugadoresDeUnEquipo(nombreEquipo).blockingSubscribe(equipos ->
                {
                    if (equipos.isRight()) {
                        System.out.println(equipos.get().toString());
                    } else {
                        System.out.println(equipos.getLeft());
                    }
                }
        );
    }
}
