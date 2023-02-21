package ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import servicios.ServicesClients;
import servicios.ServicesPurchases;
import servicios.modelo.hibernate.ClientsEntity;

import java.util.Objects;
import java.util.Scanner;

public class Ejercicio3 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesClients services = container.select(ServicesClients.class).get();
        ClientsEntity client = services.getClientById(4);
        ServicesPurchases servicesPurchases = container.select(ServicesPurchases.class).get();
        client = servicesPurchases.getPurchaseByClientId(client);
        Integer result = services.deleteAllClientData(client, false);
        Scanner sc = new Scanner(System.in);
        if (result == 1) {
            System.out.println("Client deleted successfully");
        } else if (result == -1) {
            System.out.println("The client doesn't exist or there was an error");
        } else if (result == -2) {
            System.out.println("The client has purchases, are you sure you want to delete it?");
            System.out.println("1 for yes and 2 for no");
            String input = sc.nextLine();
            if (Objects.equals(input, "1")) {
                services.deleteAllClientData(client, true);
            } else {
                System.out.println("The client was not deleted");
            }
        }
    }
}