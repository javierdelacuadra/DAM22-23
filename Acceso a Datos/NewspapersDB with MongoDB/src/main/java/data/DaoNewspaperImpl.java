package data;

import com.google.gson.Gson;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Newspaper;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class DaoNewspaperImpl implements DaoNewspaper {
    private MongoClient mongo;
    private MongoDatabase db;
    private Gson gson;

    @Inject
    public DaoNewspaperImpl() {
        this.mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
        this.db = mongo.getDatabase("JavierdelaCuadra");
        this.gson = new Gson();
    }

    @Override
    public Either<Integer, List<Newspaper>> getAll() {
        List<Newspaper> newspapers = new ArrayList<>();
        FindIterable<Document> documents = db.getCollection("newspapers").find();
        for (Document document : documents) {
            String json = document.toJson();
            Newspaper newspaper = gson.fromJson(json, Newspaper.class);
            newspaper.set_id(document.getObjectId("_id"));
            newspapers.add(newspaper);
        }
        return Either.right(newspapers);
    }

    @Override
    public Newspaper get(Newspaper newspaper) {
        ObjectId id = newspaper.get_id();
        Document document = db.getCollection("newspapers").find(eq("_id", id)).first();
        if (document != null) {
            String json = document.toJson();
            return gson.fromJson(json, Newspaper.class);
        }
        return null;
    }

    @Override
    public Integer add(Newspaper newspaper) {
        String json = gson.toJson(newspaper);
        Document document = Document.parse(json);
        db.getCollection("newspapers").insertOne(document);
        return 1;
    }

    @Override
    public Integer update(Newspaper newspaper) {
        try {
            MongoCollection<Document> collection = db.getCollection("newspapers");
            Bson filter = Filters.eq("_id", new ObjectId(newspaper.get_id().toString()));
            Bson update = new Document("$set", new Document("name", newspaper.getName()).append("releaseDate", newspaper.getReleaseDate()));
            UpdateResult result = collection.updateOne(filter, update);
            if (result.getModifiedCount() == 1) {
                return 1;
            } else {
                return -1;
            }
        } catch (Exception e) {
            return -2;
        }
    }

    @Override
    public Integer delete(Newspaper newspaper) {
        MongoCollection<Document> collection = db.getCollection("newspapers");
        Document document = collection.find(eq("name", newspaper.getName())).first();
        if (document == null) {
            return -1;
        }
        if (document.get("readers", List.class).size() > 0 || document.get("articles", List.class).size() > 0) {
            return 0;
        }
        DeleteResult deleteResult = collection.deleteOne(document);
        return deleteResult.getDeletedCount() == 1 ? 1 : -1;
    }
}