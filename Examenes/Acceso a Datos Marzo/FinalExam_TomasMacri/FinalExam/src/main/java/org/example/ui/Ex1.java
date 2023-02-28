package org.example.ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.model.hibernate.Client;
import org.example.model.hibernate.Item;
import org.example.model.hibernate.Purchase;
import org.example.model.hibernate.Purchases_items;
import org.example.services.impl.ClientServicesImpl;
import org.example.services.impl.ItemsServicesImpl;
import org.example.services.impl.PurchaseServicesImpl;

import java.time.LocalDate;
import java.util.List;

public class Ex1 {
    public static void main(String[] args) {
        final SeContainer container = SeContainerInitializer.newInstance().initialize();
        final PurchaseServicesImpl purchaseServices = container.select(PurchaseServicesImpl.class).get();
        final ClientServicesImpl clientServices = container.select(ClientServicesImpl.class).get();
        final ItemsServicesImpl itemsServices = container.select(ItemsServicesImpl.class).get();


//        Client anne = allClients.get(allClients.indexOf(new Client("Anne")));
        Client anne = clientServices.get("Anne").get();
        Item milk = itemsServices.get("milk").get();
        Item fish = itemsServices.get("fish").get();

        Purchase annesPurchase = Purchase.builder()
                .id(0)
                .client(anne)
                .p_date(LocalDate.now())
                .paid(0)
                .build();


        Purchases_items milkPurchased = Purchases_items.builder()
                .id(0)
                .item(milk)
                .purchase(annesPurchase)
                .amount(1).build();

        Purchases_items fishPurchased = Purchases_items.builder()
                .id(0)
                .item(fish)
                .purchase(annesPurchase)
                .amount(1).build();


        List<Purchases_items> purchasesItems = List.of(milkPurchased, fishPurchased);
        double totalCost = purchasesItems.stream().mapToDouble(value -> value.getAmount() * value.getItem().getPrice()).sum();

        annesPurchase.setItems_purchased(purchasesItems);
        annesPurchase.setTotal_cost(totalCost);

        if (purchaseServices.add(annesPurchase) > 0){
            System.out.println("Anne's purchase was added successfully");
        } else {
            System.out.println("There was an error with Anne's purchase");
        }
    }
}