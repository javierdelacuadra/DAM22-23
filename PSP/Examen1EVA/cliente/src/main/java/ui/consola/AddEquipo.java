package ui.consola;

import com.google.gson.Gson;
import dao.DaoJugadores;
import di.ApiProducer;
import modelo.Equipo;
import servicios.ServiciosJugadores;

import java.util.ArrayList;

public class AddEquipo {

    public static void main(String[] args) {
        ApiProducer api = new ApiProducer();
        ServiciosJugadores serviciosJugadores = new ServiciosJugadores(new DaoJugadores(api.getEquiposApi(api.getRetrofit()), new Gson()));
        Equipo equipo = new Equipo("prueba", "entrenador prueba", new ArrayList<>());
        serviciosJugadores.addEquipo(equipo).blockingSubscribe(equipos ->
                {
                    if (equipos.isRight()) {
                        System.out.println(equipos.get().toString());
                    } else {
                        System.out.println(equipos.getLeft());
                    }
                }
        );
        serviciosJugadores.verTodosLosEquiposSinJugadores().forEach(System.out::println);
    }
}
