package data;

import com.google.gson.Gson;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
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
import static com.mongodb.client.model.Updates.*;

public class DaoReaders {
    private MongoClient mongo;
    private MongoDatabase db;
    private Gson gson;

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
        this.gson = new Gson();
    }

    public Either<Integer, List<Reader>> getAll() {
        MongoCollection<Document> collection = db.getCollection("newspapers");
        try {
            List<Reader> readers = new ArrayList<>();
            FindIterable<Document> documents = collection.find();
            for (Document document : documents) {
                String json = document.toJson();
                Newspaper newspaper = gson.fromJson(json, Newspaper.class);
                if (newspaper.getReaders() != null) {
                    readers.addAll(newspaper.getReaders());
                }
            }
            return Either.right(readers);
        } catch (Exception e) {
            return Either.left(-1);
        }
    }

    public Reader get(Integer id) {
        MongoCollection<Document> collection = db.getCollection("newspapers");
        Bson filter = eq("readers.id", id);
        Document found = collection.find(filter).first();
        if (found != null) {
            List<Document> readers = (List<Document>) found.get("readers");
            for (Document reader : readers) {
                if (Objects.equals(reader.getInteger("id"), id)) {
                    return fromDocument(reader);
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

    public Either<Integer, List<Reader>> getAll(Newspaper newspaper) {
        MongoCollection<Document> collection = db.getCollection("newspapers");
        Bson filter = eq("name", newspaper.getName());
        Document document = collection.find(filter).first();
        List<Reader> readers = new ArrayList<>();
        if (document != null) {
            Newspaper newspaper1 = gson.fromJson(document.toJson(), Newspaper.class);
            readers = newspaper1.getReaders();
        }
        if (readers.isEmpty()) {
            return Either.left(-1);
        } else {
            return Either.right(readers);
        }
    }
}
//
//    public Either<Integer, List<Reader>> getAll(ArticleType type) {
//        List<Reader> readers = new ArrayList<>();
//        em = jpaUtil.getEntityManager();
//
//        try {
//            readers = em
//                    .createNamedQuery("HQL_GET_READERS_BY_ARTICLE_TYPE", Reader.class)
//                    .setParameter("description", type.getDescription())
//                    .getResultList();
//
//        } catch (PersistenceException e) {
//            e.printStackTrace();
//        } finally {
//            if (em != null) em.close();
//        }
//
//        return readers.isEmpty() ? Either.left(-1) : Either.right(readers);
//    }