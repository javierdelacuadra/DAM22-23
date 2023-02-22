package org.cliente.ui.createPlayer;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.cliente.domain.servicios.ServiciosEquipos;
import org.cliente.domain.servicios.impl.ServiciosEquiposImpl;
import org.utils.domain.modelo.Jugador;

public class MainCreatePlayer {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        ServiciosEquipos serviciosEquipos = container.select(ServiciosEquiposImpl.class).get();

        // UPDATE
        int idValido = 100;
        int idNoValido = -10;
        String nombreValido = "Oscar Novillo";
        String nombreNoValido="";
        String equipoValido = "España";
        String equipoNoValido="chile";

        Jugador j = new Jugador(idValido, nombreValido, equipoValido);

        serviciosEquipos.createPlayer(j)
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
