package ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import servicios.ServicesItems;
import servicios.modelo.hibernate.ItemsEntity;

import java.util.List;

public class Ejercicio2 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesItems services = container.select(ServicesItems.class).get();
        List<ItemsEntity> items = services.getPurchaseByClientId(3);
        if (items.isEmpty()) {
            System.out.println("Este cliente no ha realizado ninguna compra");
        } else {
            items.forEach(System.out::println);
        }
    }
}