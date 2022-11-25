package ui;

import io.vavr.control.Either;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import model.Client;
import services.ServicesClients;

import java.util.List;

public class SaveClients {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesClients services = container.select(ServicesClients.class).get();
        Either<Integer, List<Client>> clients = services.getAllClients();
        if (clients.isRight()) {
            if (services.saveClients(clients.get()).isRight()) {
                System.out.println("Clients saved to XML file successfully");
            } else {
                System.out.println("Couldn't save the clients to XML file");
            }
        } else {
            System.out.println("The clients couldn't be loaded");
        }
    }
}
