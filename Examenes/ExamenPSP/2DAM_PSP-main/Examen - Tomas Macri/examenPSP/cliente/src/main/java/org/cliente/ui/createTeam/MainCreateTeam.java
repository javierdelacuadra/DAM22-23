package org.cliente.ui.createTeam;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.cliente.domain.servicios.ServiciosEquipos;
import org.cliente.domain.servicios.impl.ServiciosEquiposImpl;
import org.utils.domain.modelo.Equipo;
import org.utils.domain.modelo.Jugador;

import java.util.ArrayList;
import java.util.List;

public class MainCreateTeam {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        ServiciosEquipos serviciosEquipos = container.select(ServiciosEquiposImpl.class).get();

        // UPDATE
        String nombreValido = "Oscar Novillo";
        String nombreNoValido="";
        String equipoValido = "Ecuador";
        String equipoNoValido="España";


        // puede estar vacia tambien (no null)
        List<Jugador> jugadorsVacia = new ArrayList<>();

        List<Jugador> jugadorsLlena =new ArrayList<>();
        jugadorsLlena.add(new Jugador(22, "Daniel Gonzalez", equipoValido));
        jugadorsLlena.add(new Jugador(2000, "Juan Gonzalez", equipoValido));

        //Esta devuelve error
        List<Jugador> jugadorsNula = null;

        Equipo e = new Equipo(equipoValido, nombreValido,jugadorsLlena);

        serviciosEquipos.createTeam(e)
                .blockingSubscribe(
                        persona -> {
                            if (persona.isLeft()) {
                                System.out.println(persona.getLeft());
                            } else {
                                System.out.println(persona.get());
                            }
                        },
                        error -> System.out.println(error.getMessage())
                );

    }

}
