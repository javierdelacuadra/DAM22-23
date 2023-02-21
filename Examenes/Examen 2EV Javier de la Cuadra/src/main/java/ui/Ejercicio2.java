package ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import servicios.ServicesItems;

public class Ejercicio2 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesItems services = container.select(ServicesItems.class).get();
        services.getPurchaseByClientId(3).forEach(System.out::println);
    }
}