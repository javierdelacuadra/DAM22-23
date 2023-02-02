package data;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class DaoLogin {

    private MongoClient mongo;
    private MongoDatabase db;

    public DaoLogin() {
        this.mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
        this.db = mongo.getDatabase("JavierdelaCuadra");
    }

    public Integer login(String user, String password) {
        MongoCollection<Document> collection = db.getCollection("login");
        Document document = collection.find(eq("user", user)).first();
        if (document == null) {
            return -3;
        }
        if (!document.getString("password").equals(password)) {
            return -2;
        }
        return document.getInteger("_id");
    }
}
