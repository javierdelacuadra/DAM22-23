package dao;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import jakarta.inject.Inject;
import org.bson.Document;
import servicios.modelo.mongo.OrderMongo;

import java.util.List;

public class DaoOrdersMongo {
    private MongoClient mongo;
    private MongoDatabase db;
    private Gson gson;

    @Inject
    public DaoOrdersMongo() {
        this.mongo = MongoClients.create("mongodb://root:root@localhost:27017/");
        this.db = mongo.getDatabase("javier");
        this.gson = new Gson();
    }

    public Integer add(List<OrderMongo> ordersMongo) {
        try {
            for (OrderMongo order : ordersMongo) {
                String json = gson.toJson(order);
                Document document = Document.parse(json);
                db.getCollection("examOrders").insertOne(document);
            }
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }
}
