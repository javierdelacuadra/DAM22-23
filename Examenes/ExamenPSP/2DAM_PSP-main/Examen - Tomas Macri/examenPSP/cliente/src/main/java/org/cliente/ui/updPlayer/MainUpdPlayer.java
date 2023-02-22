package org.cliente.ui.updPlayer;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.cliente.domain.servicios.ServiciosEquipos;
import org.cliente.domain.servicios.impl.ServiciosEquiposImpl;
import org.utils.domain.modelo.Jugador;

public class MainUpdPlayer {

    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        ServiciosEquipos serviciosEquipos = container.select(ServiciosEquiposImpl.class).get();

        // UPDATE
        int idNoValido = 800;
        int idValido = 1;
        String nombreValido = "El mejor de la historia";
        String nombreNoValido="";
        String equipoValido = "Argentina";
        String equipoNoValido="chile";

        Jugador j = new Jugador(idValido, nombreValido, equipoValido);

        serviciosEquipos.updatePlayer(j)
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
