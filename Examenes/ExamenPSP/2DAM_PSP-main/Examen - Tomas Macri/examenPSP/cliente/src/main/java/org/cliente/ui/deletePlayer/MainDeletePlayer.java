package org.cliente.ui.deletePlayer;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.cliente.domain.servicios.ServiciosEquipos;
import org.cliente.domain.servicios.impl.ServiciosEquiposImpl;

public class MainDeletePlayer {
    public static void main(String[] args) {


        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        ServiciosEquipos serviciosEquipos = container.select(ServiciosEquiposImpl.class).get();

        int idValido = 9;
        int idNoValido = -1;


        serviciosEquipos.deletePlayer(idValido)
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
