package org.example.ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.common.NumericConstants;
import org.example.model.hibernate.Client;
import org.example.services.impl.ClientServicesImpl;

import java.util.Scanner;

public class Ex3 {
    //Delete all the data of a client. If there are any purchases, ask the client before deleting
    public static void main(String[] args) {
        final SeContainer container = SeContainerInitializer.newInstance().initialize();
        final ClientServicesImpl clientServices = container.select(ClientServicesImpl.class).get();

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the name of the client you want to kill: ");
        String name = sc.nextLine();

        int code = clientServices.delete(new Client(name), false);

        switch (code) {
            case NumericConstants.DATA_INTEGRITY_VIOLATION_EXCEPTION -> {
                System.out.println("This client has purchases, are you sure you want to kill it? (press Y for yes)");
                boolean confirmed = sc.nextLine().equalsIgnoreCase("Y");
                if (confirmed) {
                    int defCode = clientServices.delete(new Client(name), true);
                    if (defCode == 1) {
                        System.out.println("The client " + name + " has been killed successfully");
                    } else {
                        System.out.println("ERROR KILLING THE CLIENT " + name);
                    }
                }
            }
            case 1 -> System.out.println("The client " + name + " has been killed successfully");
            case NumericConstants.DB_EXCEPTION_CODE -> System.out.println("DB ERROR");
            default -> {
            }
        }

    }
}
