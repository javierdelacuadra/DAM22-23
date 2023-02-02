package data;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Article;
import model.Newspaper;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.push;

public class DaoArticles {

    private MongoClient mongo;
    private MongoDatabase db;
    private Gson gson;

    @Inject
    public DaoArticles() {
        this.mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
        this.db = mongo.getDatabase("JavierdelaCuadra");
        this.gson = new Gson();
    }

    public Either<Integer, List<Article>> getAll() {
        List<Article> articles = new ArrayList<>();
        MongoCollection<Document> newspaperCollection = db.getCollection("newspapers");
        FindIterable<Document> newspapers = newspaperCollection.find();

        for (Document newspaper : newspapers) {
            String newspaperJson = newspaper.toJson();
            Newspaper newspaperObject = gson.fromJson(newspaperJson, Newspaper.class);
            articles.addAll(newspaperObject.getArticles());
        }

        if (articles.isEmpty()) {
            return Either.left(-1);
        } else {
            return Either.right(articles);
        }
    }

    public Article get(String name) {
        try {
            MongoCollection<Document> collection = db.getCollection("newspapers");
            FindIterable<Document> iterable = collection.find();

            for (Document document : iterable) {
                JsonReader reader = new JsonReader(new StringReader(document.toJson()));
                reader.setLenient(true);
                Newspaper newspaper = new Gson().fromJson(reader, Newspaper.class);
                for (Article article : newspaper.getArticles()) {
                    if (article.getName().equals(name)) {
                        return article;
                    }
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public Integer add(String newspaperName, Article article) {
        MongoCollection<Document> collection = db.getCollection("newspapers");
        Bson filter = eq("name", newspaperName);
        Bson update = push("articles", toDocument(article));
        UpdateResult result = collection.updateOne(filter, update);
        if (result.getModifiedCount() == 1) {
            return 1;
        } else {
            return -1;
        }
    }

    private Document toDocument(Article article) {
        Gson gson = new Gson();
        return Document.parse(gson.toJson(article));
    }

    public Integer update(Article article) {
        try {
            Bson filter = eq("name", article.getName());
            Bson newValue = new Document("$set", toDocument(article));
            UpdateResult updateResult = db.getCollection("articles").updateOne(filter, newValue);
            return updateResult.getModifiedCount() == 1 ? 1 : -1;
        } catch (Exception e) {
            return -1;
        }
    }

    public Integer delete(Article article) {
        try {
            Bson filter = eq("name", article.getName());
            DeleteResult deleteResult = db.getCollection("articles").deleteOne(filter);
            return deleteResult.getDeletedCount() == 1 ? 1 : -1;
        } catch (Exception e) {
            return -1;
        }
    }

//    public Either<Integer, List<Article>> getAll(Integer id) {
//        List<Article> articles = null;
//        em = jpaUtil.getEntityManager();
//
//        try {
//            articles = em
//                    .createNamedQuery("HQL_GET_ARTICLE_BY_ID", Article.class)
//                    .setParameter("id", id)
//                    .getResultList();
//
//        } catch (PersistenceException e) {
//            e.printStackTrace();
//        } finally {
//            if (em != null) em.close();
//        }
//
//        assert articles != null;
//        return articles.isEmpty() ? Either.left(-1) : Either.right(articles);
//    }
//
//    public Either<Integer, List<Article>> getAll(String type) {
//        List<Article> articles = new ArrayList<>();
//        em = jpaUtil.getEntityManager();
//
//        try {
//            articles = em
//                    .createNamedQuery("HQL_GET_ARTICLES_BY_TYPE", Article.class)
//                    .setParameter("description", type)
//                    .getResultList();
//
//        } catch (PersistenceException e) {
//            e.printStackTrace();
//        } finally {
//            if (em != null) em.close();
//        }
//
//        return articles.isEmpty() ? Either.left(-1) : Either.right(articles);
//    }
//
//    public Integer delete(Article article) {
//        em = jpaUtil.getEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        tx.begin();
//
//        try {
//            em.remove(em.merge(article));
//            tx.commit();
//            return 1;
//        } catch (Exception e) {
//            if (tx.isActive()) tx.rollback();
//            e.printStackTrace();
//            return -1;
//        } finally {
//            if (em != null) em.close();
//        }
//    }
//
//    public Integer delete(Integer id) {
//        em = jpaUtil.getEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        tx.begin();
//
//        try {
//            em.createNamedQuery("HQL_DELETE_ARTICLE_BY_NEWSPAPER_ID")
//                    .setParameter("id", id)
//                    .executeUpdate();
//            tx.commit();
//            return 1;
//        } catch (Exception e) {
//            if (tx.isActive()) tx.rollback();
//            e.printStackTrace();
//            return -1;
//        } finally {
//            if (em != null) em.close();
//        }
//    }
//
//    public List<Article> getAllWithTypes() {
//        em = jpaUtil.getEntityManager();
//        List<Article> articles = new ArrayList<>();
//
//        try {
//            articles = em
//                    .createNamedQuery("HQL_GET_ALL_ARTICLES_AND_TYPES", Article.class)
//                    .getResultList();
//
//        } catch (PersistenceException e) {
//            e.printStackTrace();
//        } finally {
//            if (em != null) em.close();
//        }
//        return articles;
//    }
}