package ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import model.Objeto;
import services.ServiciosObjetoJDBC;

import java.time.LocalDate;

public class MainJDBC {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServiciosObjetoJDBC servicios = container.select(ServiciosObjetoJDBC.class).get();
        servicios.getObjetos().forEach(System.out::println);
        Objeto objeto = new Objeto(1, "nombre", "apellido", LocalDate.now());
        servicios.addObjeto(objeto);
        servicios.getObjetos().forEach(System.out::println);
        objeto.setNombre("nombre2");
        servicios.updateObjeto(objeto);
        servicios.getObjetos().forEach(System.out::println);
        servicios.deleteObjeto(5);
        servicios.getObjetos().forEach(System.out::println);
        container.close();
    }
}
