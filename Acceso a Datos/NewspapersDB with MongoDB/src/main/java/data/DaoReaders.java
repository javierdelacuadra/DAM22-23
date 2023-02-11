package data;

import com.google.gson.Gson;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Newspaper;
import model.Reader;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Updates.*;

public class DaoReaders {
    private MongoClient mongo;
    private MongoDatabase db;

    @Inject
    public DaoReaders() {
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(
                        PojoCodecProvider.builder().automatic(true).build()
                )
        );
        this.mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
        this.db = mongo.getDatabase("JavierdelaCuadra").withCodecRegistry(pojoCodecRegistry);
    }

    public Either<Integer, List<Reader>> getAll() {
        MongoCollection<Document> collection = db.getCollection("newspapers");
        List<Reader> readers = new ArrayList<>();
        for (Document document : collection.find().projection(fields(include("readers.id", "readers.name", "readers.cancellationDate")))) {
            List<Document> readers1 = document.get("readers", List.class);
            if (readers1 != null) {
                readers.addAll(convertToReaders(readers1));
            }
        }
        if (readers.isEmpty()) {
            return Either.left(-1);
        } else {
            return Either.right(readers);
        }
    }

    private List<Reader> convertToReaders(List<Document> readers1) {
        List<Reader> readers = new ArrayList<>();
        for (Document reader : readers1) {
            readers.add(new Reader(
                    reader.getInteger("id"),
                    reader.getString("name"),
                    reader.getString("cancellationDate")));
        }
        return readers;
    }

    public Either<Integer, List<Reader>> getAll(Newspaper newspaper) {
        MongoCollection<Document> collection = db.getCollection("newspapers");
        Bson filter = eq("name", newspaper.getName());
        Document document = collection.find(filter).projection(fields(include("readers"))).first();
        List<Reader> readers = new ArrayList<>();
        if (document != null) {
            readers = convertToReaders(document.get("readers", List.class));
        }
        if (readers.isEmpty()) {
            return Either.left(-1);
        } else {
            return Either.right(readers);
        }
    }

    public Reader get(Integer id) {
        MongoCollection<Document> collection = db.getCollection("newspapers");
        Document result = collection.find().projection(fields(include("readers"))).first();
        if (result != null) {
            List<Document> readers = result.get("readers", List.class);
            if (readers != null) {
                for (Document reader : readers) {
                    if (Objects.equals(reader.getInteger("id"), id)) {
                        return fromDocument(reader);
                    }
                }
            }
        }
        return null;
    }

    public Reader fromDocument(Document document) {
        return new Reader(
                document.getInteger("id"),
                document.getString("name"),
                document.getString("cancellationDate")
        );
    }

    public Integer add(Reader reader, Newspaper newspaper) {
        try {
            MongoCollection<Document> collection = db.getCollection("newspapers");
            Bson filter = eq("name", newspaper.getName());
            Document readerDoc = new Document("id", reader.getId())
                    .append("name", reader.getName())
                    .append("cancellationDate", reader.getCancellationDate());
            Bson update = push("readers", readerDoc);
            UpdateResult updateResult = collection.updateOne(filter, update);
            if (updateResult.getModifiedCount() == 1) {
                return 1;
            } else {
                return -1;
            }
        } catch (Exception e) {
            return -2;
        }
    }


    public Integer update(Reader reader) {
        try {
            MongoCollection<Document> collection = db.getCollection("newspapers");
            Bson filter = eq("readers.id", reader.getId());
            Bson update = set("readers.$", toDocument(reader));
            UpdateResult updateResult = collection.updateMany(filter, update);
            return (int) updateResult.getModifiedCount();
        } catch (Exception e) {
            return -1;
        }
    }

    private Document toDocument(Reader reader) {
        Gson gson = new Gson();
        return Document.parse(gson.toJson(reader));
    }

    public Integer delete(String name) {
        try {
            MongoCollection<Document> collection = db.getCollection("newspapers");
            Bson filter = eq("readers.name", name);
            Bson update = pull("readers", eq("name", name));
            UpdateResult updateResult = collection.updateMany(filter, update);
            return (int) updateResult.getModifiedCount();
        } catch (Exception e) {
            return -1;
        }
    }
}