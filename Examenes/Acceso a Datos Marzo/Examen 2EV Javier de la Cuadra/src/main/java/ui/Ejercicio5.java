package ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import servicios.ServicesClientsMongo;
import servicios.ServicesPurchasesMongo;
import servicios.modelo.mongo.ClientMongo;
import servicios.modelo.mongo.ItemMongo;

public class Ejercicio5 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesPurchasesMongo services = container.select(ServicesPurchasesMongo.class).get();
        ServicesClientsMongo servicesClients = container.select(ServicesClientsMongo.class).get();

        ItemMongo item = new ItemMongo("chocolate", 4, 1);
        int result = services.addItem(item);
        int currentBalance = servicesClients.getBalance("Sarah");
        double amountToSubtract = item.getPrice() * item.getAmount();
        int newBalance = (int) (currentBalance - amountToSubtract);
        if (result == 1 && newBalance != -1) {
            ClientMongo client = new ClientMongo("Sarah", newBalance);
            int updateResult = servicesClients.updateBalance(client);
            if (updateResult >= 1) {
                System.out.println("Item added successfully and balance updated");
            } else {
                System.out.println("Error updating balance");
            }
        } else {
            System.out.println("Error adding item or getting balance");
        }
    }
}
