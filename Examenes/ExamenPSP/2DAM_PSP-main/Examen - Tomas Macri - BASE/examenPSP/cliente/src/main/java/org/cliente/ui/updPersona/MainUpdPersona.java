package org.cliente.ui.updPersona;

import io.reactivex.rxjava3.core.Single;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.cliente.domain.modelo.PersonaCliente;
import org.cliente.domain.servicios.impl.ServiciosPersona;

import java.time.LocalDate;
import java.util.Scanner;

public class MainUpdPersona {

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
                System.out.println("Introduce el nombre de la persona a actualizar");
                String nombre = sc.nextLine();
                System.out.println("Introduce el nacimiento de la persona a actualizar");
                LocalDate fechaNac = getFechaValida(sc);

                System.out.println("Introduce la contraseña de la persona a actualizar");
                String pass = sc.nextLine();
                System.out.println("Confirma la contraseña de la persona a actualizar");
                String passConf = sc.nextLine();

                PersonaCliente p = new PersonaCliente(id, nombre, fechaNac, pass, passConf);
                // LLAMAR A SERVICIO
                Single.just(serviciosPersona.updateSingleEnUI(p))
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
        }while (id > 0);
    }

    private static int getIdPositivoONegativoParaSalir(Scanner sc, int id) {
        do {
            try {
                // PEDIR ID MIENTRAS NO SEA NUMERICO
                System.out.println("Introduce el id de la persona a actualizar (mas de 0) o negativo para salir");
                id = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("El id debe ser un número \n");
            }
        } while (id == 0);
        return id;
    }

    private static LocalDate getFechaValida(Scanner sc) {
        LocalDate fechaNac;
        do {
            try {
                fechaNac = LocalDate.parse(sc.nextLine());
            } catch (Exception e) {
                System.out.println("La fecha debe tener el formato yyyy-MM-dd");
                fechaNac = null;
            }
        } while (fechaNac == null);
        return fechaNac;
    }
}
