package ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import model.PurchasesItem;
import services.ServicesPurchases;

public class UpdateClientPurchase {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesPurchases services = container.select(ServicesPurchases.class).get();
        PurchasesItem p = new PurchasesItem(2, 2, 2, 3);
        if (services.updatePurchase(p) >= 0) {
            System.out.println("Purchase updated successfully");
        } else {
            System.out.println("The product or the purchase doesn't exist");
        }
    }
}
