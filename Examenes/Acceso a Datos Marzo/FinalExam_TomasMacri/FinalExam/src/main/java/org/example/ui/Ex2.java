package org.example.ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.model.hibernate.Client;
import org.example.services.impl.ItemsServicesImpl;

import java.util.Scanner;

public class Ex2 {
    public static void main(String[] args) {
        final SeContainer container = SeContainerInitializer.newInstance().initialize();
        final ItemsServicesImpl itemsServices = container.select(ItemsServicesImpl.class).get();

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the name of the client whose products you want to see: ");
        String name = sc.nextLine();

        System.out.println(itemsServices.getAll(new Client(name)));

    }
}
