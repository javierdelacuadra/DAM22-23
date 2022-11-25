package ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import services.ServicesItems;

import java.util.Objects;

public class MostPurchasedProduct {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesItems services = container.select(ServicesItems.class).get();
        String mostPurchasedItem = services.mostPurchasedItem();
        System.out.println(Objects.requireNonNullElse(mostPurchasedItem, "There was an error trying to find the most purchased item"));
    }
}
