package ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import services.ServicesPurchases;

public class PayPurchase {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesPurchases services = container.select(ServicesPurchases.class).get();
        int clientID = 1;
        int purchaseID = 2;
        int resultado = services.payPurchase(clientID, purchaseID);
        switch (resultado) {
            case 1 -> System.out.println("The purchase was paid");
            case -1 -> System.out.println("Not enough balance to pay the purchase");
            case 2 -> System.out.println("Couldn't connect to the database");
            case 3 -> System.out.println("The purchase you tried to pay for doesn't exist");
        }
    }
}
