package ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import servicios.ServicesCustomers;
import servicios.ServicesMenuItems;
import servicios.modelo.hibernate.CustomersEntity;
import servicios.modelo.hibernate.MenuItemsEntity;

import java.util.List;

public class Ejercicio2 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesMenuItems services = container.select(ServicesMenuItems.class).get();
        ServicesCustomers servicesClients = container.select(ServicesCustomers.class).get();

        CustomersEntity john = servicesClients.getCustomerByName("John", "Doe");

        List<MenuItemsEntity> items = services.getItemsPurchasedByClientID(john.getId());
        if (items.isEmpty()) {
            System.out.println("The client hasn't made any order");
        } else {
            items.forEach(System.out::println);
        }
    }
}