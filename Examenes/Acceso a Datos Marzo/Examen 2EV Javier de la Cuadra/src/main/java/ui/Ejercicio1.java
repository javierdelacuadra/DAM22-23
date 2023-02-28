package ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import servicios.ServicesClients;
import servicios.ServicesItems;
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
        ServicesItems servicesItems = container.select(ServicesItems.class).get();

        ClientsEntity anne = servicesClients.getClientById(3);

        ItemsEntity milkItem = servicesItems.getItemByName("milk");
        ItemsEntity fishItem = servicesItems.getItemByName("fish");

        PurchasesEntity purchase = PurchasesEntity.builder()
                .id(0)
                .clientsByIdClient(anne)
                .pDate(LocalDate.now())
                .paid(0)
                .build();

        PurchasesItemsEntity milk = PurchasesItemsEntity.builder()
                .id(0)
                .items(milkItem)
                .purchases(purchase)
                .amount(1)
                .build();
        PurchasesItemsEntity fish = PurchasesItemsEntity.builder()
                .id(0)
                .items(fishItem)
                .purchases(purchase)
                .amount(1)
                .build();

        List<PurchasesItemsEntity> items = List.of(milk, fish);
        double totalCost = 0;
        for (PurchasesItemsEntity item : items) {
            totalCost += item.getItems().getPrice() * item.getAmount();
        }

        purchase.setPurchasesItems(items);
        purchase.setTotalCost(totalCost);

        if (services.addPurchase(purchase) == 1) {
            System.out.println("Purchase added successfully");
        } else {
            System.out.println("Unexpected error");
        }
    }
}