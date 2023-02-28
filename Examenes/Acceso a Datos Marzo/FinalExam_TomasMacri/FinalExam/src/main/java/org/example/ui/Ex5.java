package org.example.ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.bson.types.ObjectId;
import org.example.model.mongo.ItemMongo;
import org.example.model.mongo.PurchaseMongo;
import org.example.services.impl.ItemsServicesImpl;

import java.util.List;

public class Ex5 {
    public static void main(String[] args) {
        final SeContainer container = SeContainerInitializer.newInstance().initialize();
        final ItemsServicesImpl itemsServices = container.select(ItemsServicesImpl.class).get();

        ItemMongo i = new ItemMongo("Asado", 10,3);
        int updatedBalances = itemsServices.add(new PurchaseMongo(new ObjectId("63ef85f199fdcc58633c7a15"), List.of(i)));
        if (updatedBalances > 0 ){
            System.out.println("The client bought the product successfully");
        } else {
            System.out.println("There was an error while adding the item");
        }
    }
}
