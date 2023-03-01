package ui;

import io.vavr.control.Either;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import servicios.ServicesOrders;
import servicios.ServicesOrdersMongo;
import servicios.modelo.hibernate.OrdersEntity;

import java.util.List;

public class Ejercicio4 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesOrders servicesOrders = container.select(ServicesOrders.class).get();
        ServicesOrdersMongo servicesOrdersMongo = container.select(ServicesOrdersMongo.class).get();

        //Getting all orders and its data from the database
        Either<Integer, List<OrdersEntity>> orders = servicesOrders.getAllOrders();

        if (orders.isRight()) {
            int result = servicesOrdersMongo.addAllOrders(orders.get());
            if (result == 1) {
                System.out.println("All orders added successfully");
            } else {
                System.out.println("Error adding the orders");
            }
        } else {
            System.out.println("Error getting the orders");
        }
    }
}
