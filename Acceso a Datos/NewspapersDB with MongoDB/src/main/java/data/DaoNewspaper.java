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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DaoNewspaper {
    private JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoNewspaper(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
        this.em = jpaUtil.getEntityManager();
    }

    public Either<Integer, List<Newspaper>> getAll() {
//        List<Newspaper> newspapers = new ArrayList<>();
//        em = jpaUtil.getEntityManager();
//
//        try {
//            newspapers = em
//                    .createNamedQuery("HQL_GET_ALL_NEWSPAPERS", Newspaper.class)
//                    .getResultList();
//
//        } catch (PersistenceException e) {
//            e.printStackTrace();
//        } finally {
//            if (em != null) em.close();
//        }
//
//        return newspapers.isEmpty() ? Either.left(-1) : Either.right(newspapers);

        MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");

        MongoDatabase db = mongo.getDatabase("JavierdelaCuadra");
        MongoCollection<Document> est = db.getCollection("newspapers");
        List<Newspaper> newspapers = new ArrayList<>();

        List<Document> documents = est.find().into(new ArrayList<>());
        for (Document supplier : documents) {
               newspapers.add(new Gson().fromJson(supplier.toJson(), Newspaper.class));
           }

        return newspapers.isEmpty() ? Either.left(-1) : Either.right(newspapers);
    }

    public Newspaper get(Newspaper newspaper) {
        em = jpaUtil.getEntityManager();

        try {
            newspaper = em
                    .createNamedQuery("HQL_GET_NEWSPAPER_BY_ID", Newspaper.class)
                    .setParameter("id", newspaper.getId())
                    .getSingleResult();

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
        return newspaper;
    }

    public Integer add(Newspaper newspaper) {
        em = jpaUtil.getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(newspaper);
            transaction.commit();
            return 1;
        } catch (PersistenceException e) {
            assert transaction != null;
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
            return -1;
        } finally {
            if (em != null) em.close();
        }
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