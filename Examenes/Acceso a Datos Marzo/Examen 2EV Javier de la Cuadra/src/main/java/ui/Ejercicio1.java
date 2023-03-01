package ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import servicios.ServicesCustomers;
import servicios.ServicesMenuItems;
import servicios.ServicesOrders;
import servicios.ServicesTables;
import servicios.modelo.hibernate.*;

import java.time.LocalDate;
import java.util.List;

public class Ejercicio1 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesCustomers servicesCustomers = container.select(ServicesCustomers.class).get();
        ServicesMenuItems servicesMenuItems = container.select(ServicesMenuItems.class).get();
        ServicesOrders servicesOrders = container.select(ServicesOrders.class).get();
        ServicesTables servicesTables = container.select(ServicesTables.class).get();

        //Getting the customer by email
        CustomersEntity john = servicesCustomers.getCustomerByEmail("johndoe@example.com");

        //Getting the items by name
        MenuItemsEntity steak = servicesMenuItems.getItemByName("Steak");
        MenuItemsEntity salmon = servicesMenuItems.getItemByName("Salmon");

        //Getting table by ID
        TablesEntity table = servicesTables.getTableById(2);

        OrdersEntity order = OrdersEntity.builder()
                .id(0)
                .table(table)
                .customer(john)
                .orderDate(LocalDate.now())
                .build();

        OrderItemsEntity steakItem = OrderItemsEntity.builder()
                .id(0)
                .menuItem(steak)
                .order(order)
                .price(steak.getPrice())
                .quantity(1)
                .build();

        OrderItemsEntity salmonItem = OrderItemsEntity.builder()
                .id(0)
                .menuItem(salmon)
                .order(order)
                .price(salmon.getPrice())
                .quantity(1)
                .build();

        List<OrderItemsEntity> orderItems = List.of(steakItem, salmonItem);
        double totalCost = 0;
        for (OrderItemsEntity item : orderItems) {
            totalCost += item.getMenuItem().getPrice() * item.getQuantity();
        }

        order.setOrderItems(orderItems);
        order.setTotal(totalCost);

        if (servicesOrders.addOrder(order) >= 1) {
            System.out.println("Order added successfully");
        } else {
            System.out.println("Unexpected error");
        }
    }
}