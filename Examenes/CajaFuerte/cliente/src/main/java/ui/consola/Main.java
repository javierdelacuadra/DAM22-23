package ui.consola;

import data.DaoRatones;
import io.vavr.control.Either;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import modelo.Raton;
import modelo.User;
import servicios.ServiciosLogin;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        DaoRatones dao = container.select(DaoRatones.class).get();
        ServiciosLogin serviciosLogin = container.select(ServiciosLogin.class).get();
        serviciosLogin.login(new User("usuario1", "pass1")).subscribe(System.out::println);
        Either<String, List<Raton>> ratones = dao.getAll().blockingGet();
        if (ratones.isRight()) {
            ratones.get().forEach(System.out::println);
        } else {
            System.out.println("Error: " + ratones.getLeft());
        }
    }
}
