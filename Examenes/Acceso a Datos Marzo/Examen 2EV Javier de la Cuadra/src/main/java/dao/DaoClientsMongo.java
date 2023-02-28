package dao;

import com.google.gson.Gson;
import com.mongodb.Block;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.conversions.Bson;
import servicios.modelo.mongo.ClientMongo;
import servicios.modelo.mongo.PurchaseMongo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Accumulators.addToSet;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Updates.set;

public class DaoClientsMongo {
    private MongoClient mongo;
    private MongoDatabase db;
    private Gson gson;

    @Inject
    public DaoClientsMongo() {
        this.mongo = MongoClients.create("mongodb://root:root@localhost:27017/");
        this.db = mongo.getDatabase("javier");
        this.gson = new Gson();
    }

    public int update(ClientMongo client) {
        try {
            MongoCollection<Document> collection = db.getCollection("examPurchases");
            Bson filter = eq("client.name", client.getName());
            Bson update = set("client.balance", client.getBalance());
            UpdateResult updateResult = collection.updateMany(filter, update);
            return (int) updateResult.getModifiedCount();
        } catch (Exception e) {
            return -1;
        }
    }

    private Document toDocument(ClientMongo item) {
        Gson gson = new Gson();
        return Document.parse(gson.toJson(item));
    }

    //fromDocument
    public ClientMongo fromDocument(Document document) {
        String json = document.toJson();
        PurchaseMongo purchase = gson.fromJson(json, PurchaseMongo.class);
        return purchase.getClient();
    }

    public int getBalance(String sarah) {
        try {
            MongoCollection<Document> collection = db.getCollection("examPurchases");
            Bson filter = eq("client.name", sarah);
            Bson projection = excludeId();
            Document document = collection.find(filter).projection(projection).first();
            ClientMongo client = fromDocument(document);
            return client.getBalance();
        } catch (Exception e) {
            return -1;
        }
    }

    public List<String> aggregation() {
//MQL
//        [
//        {
//            $unwind: {
//                path: "$items",
//            },
//        },
//        {
//            $match: {
//                "items.name": "milk",
//            },
//        },
//        {
//            $group:
//            /**
//             * _id: The id of the group.
//             * fieldN: The first field name.
//             */
//            {
//                _id: "$client.name",
//                        priceMilk: {
//                $sum: "$items.amount",
//            },
//            },
//        },
//]

        List<String> results = new ArrayList<>();
        MongoCollection<Document> collection = db.getCollection("examPurchases");

        collection.aggregate((Arrays.asList(
                unwind("$items"),
                match(eq("items.name", "milk")),
                group("$client.name", sum("priceMilk", "$items.amount")))))
                .into(new ArrayList<>())
                .forEach(result -> {
                    results.add(String.valueOf(result));
                });

        return results;
    }
}