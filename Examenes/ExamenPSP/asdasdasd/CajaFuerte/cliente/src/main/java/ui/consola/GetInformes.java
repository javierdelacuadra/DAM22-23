package ui.consola;

import data.DaoInformes;
import io.vavr.control.Either;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import modelo.Informe;
import modelo.User;
import servicios.ServiciosLogin;

import java.util.List;

public class GetInformes {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        DaoInformes dao = container.select(DaoInformes.class).get();
        ServiciosLogin serviciosLogin = container.select(ServiciosLogin.class).get();
        serviciosLogin.login(new User("usuario3", "pass3")).subscribe(System.out::println);
        Either<String, List<Informe>> resultado = dao.getAll().blockingGet();
        if (resultado.isRight()) {
            resultado.get().forEach(System.out::println);
        } else {
            System.out.println("Error: " + resultado.getLeft());
        }
    }
}
