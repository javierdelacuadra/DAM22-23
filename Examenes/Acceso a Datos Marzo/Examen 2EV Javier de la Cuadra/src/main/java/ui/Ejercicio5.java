package ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import servicios.ServicesCustomers;

public class Ejercicio5 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesCustomers services = container.select(ServicesCustomers.class).get();
        services.aggregation().forEach(System.out::println);
    }
}
