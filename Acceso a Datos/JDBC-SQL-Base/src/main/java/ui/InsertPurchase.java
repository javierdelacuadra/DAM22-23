package ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import model.Purchase;
import model.PurchasesItem;
import services.ServicesPurchases;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InsertPurchase {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesPurchases services = container.select(ServicesPurchases.class).get();
        PurchasesItem item = new PurchasesItem(0, 0, 1, 1);
        PurchasesItem item2 = new PurchasesItem(0, 0, 2, 1);
        List<PurchasesItem> items = new ArrayList<>();
        items.add(item);
        items.add(item2);
        Purchase purchase = new Purchase(0, 1, LocalDate.now(), 0, 0, items);
        switch (services.savePurchase(purchase)) {
            case 1 -> System.out.println("The purchase was added successfully");
            case -1 -> System.out.println("An item you tried to purchase doesn't exist");
            case -2 -> System.out.println("Couldn't connect to the database");
            default -> System.out.println("There was an error adding your purchase or the client doesn't exist");
        }
    }
}
