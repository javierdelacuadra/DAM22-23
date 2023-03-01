package ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import servicios.ServicesCustomers;
import servicios.modelo.hibernate.CustomersEntity;

import java.util.Objects;
import java.util.Scanner;

public class Ejercicio3 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesCustomers servicesCustomers = container.select(ServicesCustomers.class).get();

        CustomersEntity customer = servicesCustomers.getCustomerByEmail("johndoe@example.com");

        //Try to delete for the first time
        Integer result = servicesCustomers.deleteAllCustomerData(customer, false);

        Scanner sc = new Scanner(System.in);

        if (result == 1) {
            System.out.println("Customer deleted successfully");
        } else if (result == -1) {
            //Ask if the customer wants to delete because it has orders
            System.out.println("The customers has orders, are you sure you want to delete it?");
            System.out.println("1 for yes and 2 for no");
            String input = sc.nextLine();
            if (Objects.equals(input, "1")) {
                //Result of deleting the customer + orders
                Integer deleteResult = servicesCustomers.deleteAllCustomerData(customer, true);
                if (deleteResult == 1) {
                    System.out.println("The customer was deleted successfully");
                } else {
                    System.out.println("The customer was not deleted due to an error");
                }
            } else {
                System.out.println("You decided not to delete the customer");
            }
        }
    }
}