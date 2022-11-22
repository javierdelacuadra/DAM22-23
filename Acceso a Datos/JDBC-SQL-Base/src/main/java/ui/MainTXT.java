package ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import model.Objeto;
import services.ServiciosObjetoTXT;

import java.time.LocalDate;

public class MainTXT {

    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServiciosObjetoTXT servicios = container.select(ServiciosObjetoTXT.class).get();
        servicios.getObjetos().forEach(System.out::println);
        servicios.saveObjeto(new Objeto(1, "nombre", "apellido", LocalDate.now()));
        servicios.getObjetos().forEach(System.out::println);
        container.close();
    }
}