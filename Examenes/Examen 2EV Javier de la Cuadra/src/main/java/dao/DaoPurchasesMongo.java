package dao;

import com.google.gson.Gson;
import com.mongodb.client.*;
import com.mongodb.client.result.UpdateResult;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.conversions.Bson;
import servicios.modelo.mongo.ItemMongo;
import servicios.modelo.mongo.PurchaseMongo;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.push;

public class DaoPurchasesMongo {
    private MongoClient mongo;
    private MongoDatabase db;
    private Gson gson;

    @Inject
    public DaoPurchasesMongo() {
        this.mongo = MongoClients.create("mongodb://root:root@localhost:27017/");
        this.db = mongo.getDatabase("javier");
        this.gson = new Gson();
    }

    public Either<Integer, List<PurchaseMongo>> getAll() {
        List<PurchaseMongo> newspapers = new ArrayList<>();
        FindIterable<Document> documents = db.getCollection("examPurchases").find();
        for (Document document : documents) {
            String json = document.toJson();
            PurchaseMongo newspaper = gson.fromJson(json, PurchaseMongo.class);
            newspaper.set_id(document.getObjectId("_id"));
            newspapers.add(newspaper);
        }
        return Either.right(newspapers);
    }

    public Integer add(ItemMongo item) {
        MongoCollection<Document> collection = db.getCollection("examPurchases");
        List<PurchaseMongo> list = getAll().get();
        Bson filter = eq("_id", list.get(1).get_id());
        Bson update = push("items", toDocument(item));
        UpdateResult result = collection.updateOne(filter, update);
        if (result.getModifiedCount() == 1) {
            return 1;
        } else {
            return -1;
        }
    }

    private Document toDocument(ItemMongo item) {
        Gson gson = new Gson();
        return Document.parse(gson.toJson(item));
    }
}