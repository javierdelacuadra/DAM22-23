package ui.consola;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MainPocho {
    public static void main(String[] args) {
        MongoClient mongo = MongoClients.create("mongodb://root:root@localhost:27017/");
        MongoDatabase db = mongo.getDatabase("javier");
        MongoCollection<Document> collection = db.getCollection("test");
        for (Document document : collection.find()) {
            System.out.println(document.toJson());
        }
    }
}
