package data;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Newspaper;
import org.bson.Document;
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
            newspapers.add(gson.fromJson(json, Newspaper.class));
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
    public Integer delete(Newspaper newspaper) {
        ObjectId id = newspaper.get_id();
        db.getCollection("newspapers").deleteOne(eq("_id", id));
        return 1;
    }

    @Override
    public Integer update(Newspaper newspaper) {
        ObjectId id = newspaper.get_id();
        String json = gson.toJson(newspaper);
        Document document = Document.parse(json);
        db.getCollection("newspapers").replaceOne(eq("_id", id), document);
        return 1;
    }

//    public Map<String, Integer> getNbrArticles(int newspaper) {
//        em = jpaUtil.getEntityManager();
//
//        try {
//            return em.createNamedQuery("HQL_GET_NUMBER_ARTICLES_BY_NEWSPAPER", Tuple.class)
//                    .setParameter("newspaperID", newspaper)
//                    .getResultStream()
//                    .collect(Collectors.toMap(
//                            tuple -> tuple.get("type").toString(),
//                            tuple -> ((Number) tuple.get("numberArticles")).intValue())
//                    );
//        } catch (PersistenceException e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            if (em != null) em.close();
//        }
//    }
}