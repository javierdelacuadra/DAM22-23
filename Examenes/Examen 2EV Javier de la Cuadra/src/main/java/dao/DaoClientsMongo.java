package dao;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.conversions.Bson;
import servicios.modelo.mongo.ClientMongo;

import static com.mongodb.client.model.Filters.eq;
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
}
