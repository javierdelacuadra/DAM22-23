package ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.xml.bind.JAXBException;
import model.Objeto;
import services.ServiciosObjetoXML;

import java.time.LocalDate;

public class MainXML {
    public static void main(String[] args) throws JAXBException {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServiciosObjetoXML servicios = container.select(ServiciosObjetoXML.class).get();
        servicios.getObjetos().get().forEach(System.out::println);
        Objeto objeto = new Objeto(1, "nombre", "apellido", LocalDate.now());
        servicios.saveObjeto(objeto);
        servicios.getObjetos().get().forEach(System.out::println);
        servicios.deleteObjeto(objeto);
        servicios.getObjetos().get().forEach(System.out::println);
        container.close();
    }
}
