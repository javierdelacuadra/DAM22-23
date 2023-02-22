package org.cliente.ui.getUnaPersona;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.cliente.domain.servicios.impl.ServiciosPersona;

import java.util.Scanner;

public class MainGetUnaPersonaSingleDAO {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        ServiciosPersona serviciosPersona = container.select(ServiciosPersona.class).get();

        Scanner sc = new Scanner(System.in);
        int id = 0;
        do {
            do {
                try {
                    // PEDIR ID MIENTRAS NO SEA NUMERICO
                    System.out.println("Introduce el id de la persona a buscar (mas de 0) o negativo para salir");
                    id = Integer.parseInt(sc.nextLine());
                } catch (Exception e) {
                    System.out.println("El id debe ser un número \n");
                }
            } while (id == 0);

            if (id > 0) {
                serviciosPersona.getUnaConSingleEnDAO(id)
                        .blockingSubscribe(
                                persona -> {
                                    if (persona.isLeft()) {
                                        System.out.println("LA LLAMADA DEVOLVIO ERROR -> " + persona.getLeft() + "\n");
                                    } else {
                                        System.out.println("TODO OK -> " + persona.get() + "\n");
                                    }
                                },
                                error -> System.out.println("ERROR EXTERNO -> " + error.getMessage() + "\n")
                        );

            }
        } while (id > 0);

    }


}
