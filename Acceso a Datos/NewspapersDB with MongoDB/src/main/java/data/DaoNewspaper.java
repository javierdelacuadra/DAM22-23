package data;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import data.hibernate.JPAUtil;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Tuple;
import model.Newspaper;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.include;

public class DaoNewspaper {
    private JPAUtil jpaUtil;
    private EntityManager em;
    private MongoClient mongo;
    private MongoDatabase db;

    @Inject
    public DaoNewspaper(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
        this.em = jpaUtil.getEntityManager();
        this.mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
        this.db = mongo.getDatabase("JavierdelaCuadra");
    }

    public Either<Integer, List<Newspaper>> getAll() {
        MongoCollection<Document> est = db.getCollection("newspapers");
        List<Newspaper> newspapers = new ArrayList<>();
        Bson projection = include("id", "name", "releaseDate");
        List<Document> documents = est.find().projection(projection).into(new ArrayList<>());
        for (Document newspaper : documents) {
            ObjectId id = newspaper.getObjectId("_id");
            Newspaper newspaper1 = new Gson().fromJson(newspaper.toJson(), Newspaper.class);
            newspaper1.set_id(id);
            newspapers.add(newspaper1);
        }
        return newspapers.isEmpty() ? Either.left(-1) : Either.right(newspapers);
    }

    public Newspaper get(Newspaper newspaper) {

        MongoCollection<Document> est = db.getCollection("newspapers");
        List<Newspaper> newspapers = new ArrayList<>();

        est.find(eq("id", newspaper.getId())).into(new ArrayList<>()).forEach(document -> newspapers.add(new Gson().fromJson(document.toJson(), Newspaper.class)));
        if (newspapers.size() > 0) {
            newspaper = newspapers.get(0);
        }
        return newspaper;
    }

    public Integer add(Newspaper newspaper) {
        MongoCollection<Document> est = db.getCollection("newspapers");

        Document d = new Document();
        d.append("id", newspaper.getId());
        d.append("name", newspaper.getName());
        d.append("release_date", newspaper.getRelease_date());
        d.put("articles", Arrays.asList(new Document()
                        .append("id", newspaper.getArticles().get(0).getId())
                        .append("title", newspaper.getArticles().get(0).getName_article())
                        .append("content", newspaper.getArticles().get(0).getType().getDescription()),
                new Document()
                        .append("id", newspaper.getArticles().get(1).getId())
                        .append("title", newspaper.getArticles().get(1).getName_article())
                        .append("content", newspaper.getArticles().get(1).getType().getDescription()),
                new Document()
                        .append("id", newspaper.getArticles().get(2).getId())
                        .append("title", newspaper.getArticles().get(2).getName_article())
                        .append("content", newspaper.getArticles().get(2).getType().getDescription())));

        est.insertOne(d);
        return 1;
    }

    public Integer delete(Newspaper newspaper) {
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            em.remove(em.merge(newspaper));
            tx.commit();
            return 1;
        } catch (PersistenceException e) {
            if (tx.isActive()) tx.rollback();
            return -1;
        } finally {
            if (em != null) em.close();
        }
    }

    public Integer update(Newspaper newspaper) {
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = null;

        try {
            tx = em.getTransaction();
            tx.begin();
            em.merge(newspaper);
            tx.commit();
            return 1;
        } catch (PersistenceException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return -1;
        } finally {
            if (em != null) em.close();
        }
    }

    public Map<String, Integer> getNbrArticles(int newspaper) {
        em = jpaUtil.getEntityManager();

        try {
            return em.createNamedQuery("HQL_GET_NUMBER_ARTICLES_BY_NEWSPAPER", Tuple.class)
                    .setParameter("newspaperID", newspaper)
                    .getResultStream()
                    .collect(Collectors.toMap(
                            tuple -> tuple.get("type").toString(),
                            tuple -> ((Number) tuple.get("numberArticles")).intValue())
                    );
        } catch (PersistenceException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (em != null) em.close();
        }
    }
}