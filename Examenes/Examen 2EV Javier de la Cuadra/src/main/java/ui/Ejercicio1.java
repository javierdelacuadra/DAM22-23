package ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import servicios.ServicesClients;
import servicios.ServicesPurchases;
import servicios.modelo.hibernate.ClientsEntity;
import servicios.modelo.hibernate.ItemsEntity;
import servicios.modelo.hibernate.PurchasesEntity;
import servicios.modelo.hibernate.PurchasesItemsEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Ejercicio1 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesPurchases services = container.select(ServicesPurchases.class).get();
        ServicesClients servicesClients = container.select(ServicesClients.class).get();
        ClientsEntity anne = servicesClients.getClientById(3);
        ItemsEntity milkItem = new ItemsEntity(1, "milk", 2);
        ItemsEntity fishItem = new ItemsEntity(4, "fish", 25.98);
        PurchasesItemsEntity milk = new PurchasesItemsEntity(milkItem, 1);
        PurchasesItemsEntity fish = new PurchasesItemsEntity(fishItem, 1);
        List<PurchasesItemsEntity> items = new ArrayList<>();
        items.add(milk);
        items.add(fish);
        double totalCost = 0;
        for (PurchasesItemsEntity item : items) {
            totalCost += item.getItems().getPrice() * item.getAmount();
        }
        PurchasesEntity purchase = new PurchasesEntity(anne, LocalDate.now(), totalCost, 0, items);
        if (services.addPurchase(purchase) == 1) {
            System.out.println("Purchase added successfully");
        } else {
            System.out.println("Unexpected error");
        }
    }
}