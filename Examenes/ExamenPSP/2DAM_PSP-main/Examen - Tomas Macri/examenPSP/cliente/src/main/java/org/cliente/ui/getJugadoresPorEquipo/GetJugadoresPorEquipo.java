package org.cliente.ui.getJugadoresPorEquipo;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.cliente.domain.servicios.ServiciosEquipos;
import org.cliente.domain.servicios.impl.ServiciosEquiposImpl;

public class GetJugadoresPorEquipo {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        ServiciosEquipos serviciosEquipos = container.select(ServiciosEquiposImpl.class).get();

        String nombreValidoJugadoresVacios = "qatar";
        String nombreValido = "argentina";
        String nombreNoValido="chile";
        serviciosEquipos.getJugadoresPorEquipo(nombreValido)
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
