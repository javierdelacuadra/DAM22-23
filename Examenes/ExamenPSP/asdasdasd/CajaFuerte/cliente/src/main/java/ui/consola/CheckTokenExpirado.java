package ui.consola;

import data.DaoInformes;
import io.vavr.control.Either;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import modelo.Informe;
import modelo.Raton;
import modelo.User;
import servicios.ServiciosLogin;

import java.time.LocalDate;
import java.util.List;

public class CheckTokenExpirado {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        DaoInformes dao = container.select(DaoInformes.class).get();
        ServiciosLogin serviciosLogin = container.select(ServiciosLogin.class).get();
        serviciosLogin.login(new User("usuario3", "pass3")).subscribe(System.out::println);
        Raton raton = new Raton("Raton 1", 12);
        Raton raton2 = new Raton("Raton 2", 12);
        Informe informe = new Informe("Informe 1", LocalDate.now(), "NIVEL1", List.of(raton, raton2));
        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Either<String, Informe> resultado = dao.add(informe).blockingGet();
        if (resultado.isRight()) {
            System.out.println("Informe añadido con éxito");
        } else {
            System.out.println("Error: " + resultado.getLeft());
        }
    }
}
