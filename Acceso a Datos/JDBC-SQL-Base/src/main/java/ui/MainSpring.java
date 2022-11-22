package ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import model.Objeto;
import services.ServiciosObjetoSpring;

import java.time.LocalDate;

public class MainSpring {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServiciosObjetoSpring servicios = container.select(ServiciosObjetoSpring.class).get();
        servicios.getObjetos().forEach(System.out::println);
        Objeto objeto = new Objeto(1, "nombre", "apellido", LocalDate.now());
        servicios.addObjeto(objeto);
        servicios.getObjetos().forEach(System.out::println);
        objeto.setNombre("nombre4");
        servicios.updateObjeto(objeto);
        servicios.getObjetos().forEach(System.out::println);
        servicios.deleteObjeto(5);
        servicios.getObjetos().forEach(System.out::println);
        container.close();
    }
}