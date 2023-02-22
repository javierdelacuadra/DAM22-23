package org.cliente.ui.getAllTodos;


import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.cliente.domain.servicios.ServiciosEquipos;
import org.cliente.domain.servicios.impl.ServiciosEquiposImpl;

public class GetAllTodos {
    public static void main(String[] args) {

        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        ServiciosEquipos serviciosEquipos = container.select(ServiciosEquiposImpl.class).get();

        serviciosEquipos.getAllEquipos()
                .blockingSubscribe(
                        equipo -> {
                            if (equipo.isLeft()) {
                                System.out.println(equipo.getLeft());
                            } else {
                                System.out.println(equipo.get());
                            }
                        },
                        error -> System.out.println(error.getMessage())
                );

    }

}