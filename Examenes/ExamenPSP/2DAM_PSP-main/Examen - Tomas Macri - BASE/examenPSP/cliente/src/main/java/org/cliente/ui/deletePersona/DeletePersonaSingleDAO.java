package org.cliente.ui.deletePersona;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.cliente.domain.servicios.impl.ServiciosPersona;

import java.util.Scanner;

public class DeletePersonaSingleDAO {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        ServiciosPersona serviciosPersona = container.select(ServiciosPersona.class).get();

        Scanner sc = new Scanner(System.in);
        // PEDIR DATOS DE NUEVA PERSONA
        int id = 0;
        do {
            id = getIdPositivoONegativoParaSalir(sc, id);
            if (id > 0) {
                int finalId = id;
                serviciosPersona.deleteSingleEnDAO(id)
                        .blockingSubscribe(responses ->
                                {
                                    if (responses.isRight()) {
                                        System.out.println("TODO OK -> Se ha eliminado la persona con el id " + finalId + "\n");
                                    } else {
                                        System.out.println("LA LLAMADA DEVOLVIO ERROR -> " + responses.getLeft() + "\n");
                                    }
                                }, error -> System.out.println("ERROR EXTERNO -> " + error.getMessage() + "\n")
                        );

            }
        } while (id > 0);
    }

    private static int getIdPositivoONegativoParaSalir(Scanner sc, int id) {
        do {
            try {
                // PEDIR ID MIENTRAS NO SEA NUMERICO
                System.out.println("Introduce el id de la persona a buscar (mas de 0) o negativo para salir");
                id = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("El id debe ser un número \n");
            }
        } while (id == 0);
        return id;
    }
}
