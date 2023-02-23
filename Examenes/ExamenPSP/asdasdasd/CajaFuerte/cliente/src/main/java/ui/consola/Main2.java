package ui.consola;

import data.DaoRatones;
import io.vavr.control.Either;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import modelo.Raton;
import modelo.User;
import servicios.ServiciosLogin;

public class Main2 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        DaoRatones dao = container.select(DaoRatones.class).get();
        ServiciosLogin serviciosLogin = container.select(ServiciosLogin.class).get();
        serviciosLogin.login(new User("usuario1", "pass1")).subscribe(System.out::println);
        Raton raton = new Raton("Raton 1", 12);
        Either<String, Raton> resultado = dao.add(raton).blockingGet();
        if (resultado.isRight()) {
            System.out.println("Raton añadido con éxito");
        } else {
            System.out.println("Error: " + resultado.getLeft());
        }
    }
}
