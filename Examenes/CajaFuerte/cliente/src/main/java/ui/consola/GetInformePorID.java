package ui.consola;

import data.DaoInformes;
import io.vavr.control.Either;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import modelo.Informe;
import modelo.User;
import servicios.ServiciosLogin;

public class GetInformePorID {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        DaoInformes dao = container.select(DaoInformes.class).get();
        ServiciosLogin serviciosLogin = container.select(ServiciosLogin.class).get();
        serviciosLogin.login(new User("usuario3", "pass3")).subscribe(System.out::println);
        Either<String, Informe> resultado = dao.getInforme("informe1").blockingGet();
        if (resultado.isRight()) {
            System.out.println(resultado.get());
        } else {
            System.out.println("Error: " + resultado.getLeft());
        }
        Either<String, Informe> resultado2 = dao.getInforme("informe2").blockingGet();
        if (resultado2.isRight()) {
            System.out.println(resultado2.get());
        } else {
            System.out.println("Error: " + resultado2.getLeft());
        }
        Either<String, Informe> resultado3 = dao.getInforme("informe3").blockingGet();
        if (resultado3.isRight()) {
            System.out.println(resultado3.get());
        } else {
            System.out.println("Error: " + resultado3.getLeft());
        }
    }
}
