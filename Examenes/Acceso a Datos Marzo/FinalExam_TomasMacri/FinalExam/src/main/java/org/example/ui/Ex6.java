package org.example.ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.services.impl.ClientServicesImpl;

public class Ex6 {
    //Get the amount of milk bought by each client
    public static void main(String[] args) {
        final SeContainer container = SeContainerInitializer.newInstance().initialize();
        final ClientServicesImpl clientServices = container.select(ClientServicesImpl.class).get();
        clientServices.amountOfMilkBought().forEach((client, integer) -> System.out.println("Client " + client.getName() + " has bought " + integer + " milks"));
    }
}
