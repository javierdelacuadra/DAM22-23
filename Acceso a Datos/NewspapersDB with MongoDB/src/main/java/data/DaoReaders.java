package data;

import com.google.gson.Gson;
import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import data.hibernate.JPAUtil;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import model.Newspaper;
import model.Reader;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class DaoReaders {
    private JPAUtil jpaUtil;
    private EntityManager em;
    private MongoClient mongo;
    private MongoDatabase db;
    private Gson gson;

    @Inject
    public DaoReaders(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
        this.em = jpaUtil.getEntityManager();
        this.mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
        this.db = mongo.getDatabase("JavierdelaCuadra");
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
                readers.addAll(newspaper.getReaders());
            }
            return Either.right(readers);
        } catch (Exception e) {
            return Either.left(-1);
        }
    }

    public Reader get(String name) {
        try {
            MongoCollection<Document> collection = db.getCollection("readers");
            Document filter = new Document("name", name);
            Document document = collection.find(filter).first();
            if (document == null) {
                return null;
            }
            return gson.fromJson(document.toJson(), Reader.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer add(Reader reader) {
        try {
            MongoCollection<Document> collection = db.getCollection("readers");
            String json = gson.toJson(reader);
            Document document = Document.parse(json);
            collection.insertOne(document);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public Integer update(String name, Reader reader) {
        try {
            MongoCollection<Document> collection = db.getCollection("readers");
            Document filter = new Document("name", name);
            String json = gson.toJson(reader);
            Document update = Document.parse(json);
            UpdateResult result = collection.updateOne(filter, new Document("$set", update));
            if (result.getMatchedCount() == 0) {
                return -1;
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public Integer delete(String name) {
        try {
            MongoCollection<Document> collection = db.getCollection("readers");
            Document filter = new Document("name", name);
            DeleteResult result = collection.deleteOne(filter);
            if (result.getDeletedCount() == 0) {
                return -1;
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

//    public Either<Integer, List<Reader>> getAll(Newspaper newspaper) {
//        List<Reader> readers = new ArrayList<>();
//        em = jpaUtil.getEntityManager();
//
//        try {
//            readers = em
//                    .createNamedQuery("HQL_GET_READERS_BY_ID_NEWSPAPER", Reader.class)
//                    .setParameter("id_newspaper", newspaper.getId())
//                    .getResultList();
//
//        } catch (PersistenceException e) {
//            e.printStackTrace();
//        } finally {
//            if (em != null) em.close();
//        }
//        return readers.isEmpty() ? Either.left(-1) : Either.right(readers);
//    }
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
}