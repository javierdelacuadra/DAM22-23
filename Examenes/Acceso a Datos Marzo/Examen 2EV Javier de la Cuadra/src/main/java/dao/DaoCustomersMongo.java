package dao;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jakarta.inject.Inject;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Accumulators.addToSet;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

public class DaoCustomersMongo {

    private MongoClient mongo;
    private MongoDatabase db;
    private Gson gson;

    @Inject
    public DaoCustomersMongo() {
        this.mongo = MongoClients.create("mongodb://root:root@localhost:27017/");
        this.db = mongo.getDatabase("javier");
        this.gson = new Gson();
    }

    public List<String> aggregation() {
//MQL
//     [
//  {
//    $unwind: {
//      path: "$items",
//    },
//  },
//  {
//    $match: {
//      "items.name": "Steak",
//    },
//  },
//  {
//    $group:
//      /**
//       * _id: The id of the group.
//       * fieldN: The first field name.
//       */
//      {
//        _id: "$customer.first_name",
//        amountSteak: {
//          $sum: "$items.quantity",
//        },
//        lastName: {
//          $addToSet: "$customer.last_name",
//        },
//      },
//  },
//  {
//    $project: {
//      _id: 1,
//      amountSteak: 2,
//      "lastName": 3,
//    },
//  },
//]

        List<String> results = new ArrayList<>();
        MongoCollection<Document> collection = db.getCollection("examOrders");

        collection.aggregate((Arrays.asList(
                        unwind("$items"),
                        match(eq("items.name", "Steak")),
                        group("$customer.first_name", sum("amountSteak", "$items.quantity"), addToSet("lastNames", "$customer.last_name")),
                        project(fields(include("amountSteak", "lastNames"))
                        ))))
                .into(new ArrayList<>())
                .forEach(result -> results.add("First name: " + result.getString("_id") + " Last name: " + result.get("lastNames", ArrayList.class).stream().map(String::valueOf).toList() + " Amount of steak: " + result.getInteger("amountSteak")));

        return results;
    }
}
