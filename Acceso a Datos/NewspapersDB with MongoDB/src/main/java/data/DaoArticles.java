package data;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Article;
import model.Newspaper;
import model.Reader;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.elemMatch;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Updates.*;

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
        MongoCollection<Document> collection = db.getCollection("newspapers");
        List<Article> articles = new ArrayList<>();
        for (Document document : collection.find().projection(fields(include("articles.name", "articles.type")))) {
            List<Document> articles1 = document.get("articles", List.class);
            if (articles1 != null) {
                articles.addAll(convertToArticles(articles1));
            }
        }
        if (articles.isEmpty()) {
            return Either.left(-1);
        } else {
            return Either.right(articles);
        }
    }

    private List<Article> convertToArticles(List<Document> articlesDoc) {
        List<Article> articles = new ArrayList<>();
        for (Document articleDoc : articlesDoc) {
            articles.add(new Article(articleDoc.getString("name"), articleDoc.getString("type")));
        }
        return articles;
    }

    public Article get(String name) {
        MongoCollection<Document> collection = db.getCollection("newspapers");
        Document result = collection.find(Filters.and(Filters.eq("articles.name", name))).projection(include("articles.$")).first();
        if (result != null) {
            List<Article> articles = result.get("articles", List.class);
            return articles.get(0);
        } else {
            return null;
        }
    }

    public Integer add(Article article, Newspaper newspaper) {
        MongoCollection<Document> collection = db.getCollection("newspapers");
        Bson filter = eq("name", newspaper.getName());
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

    public Integer update(Article article, String oldName) {
        try {
            MongoCollection<Document> collection = db.getCollection("newspapers");
            Bson filter = eq("articles.name", oldName);
            Bson update = set("articles.$", toDocument(article));
            UpdateResult updateResult = collection.updateOne(filter, update);
            return updateResult.getModifiedCount() == 1 ? 1 : -1;
        } catch (Exception e) {
            return -1;
        }
    }

    public Integer delete(Article article) {
        try {
            MongoCollection<Document> collection = db.getCollection("newspapers");
            Bson filter = eq("articles.name", article.getName());
            Bson update = pull("articles", new Document("name", article.getName()));
            UpdateResult updateResult = collection.updateOne(filter, update);
            return updateResult.getModifiedCount() == 1 ? 1 : -1;
        } catch (Exception e) {
            return -1;
        }
    }

    public Either<Integer, List<Article>> getAll(String name) {
        MongoCollection<Document> collection = db.getCollection("newspapers");
        List<Article> articles;
        Bson filter = eq("name", name);
        Document newspaper = collection.find(filter).projection(include("articles")).first();
        if (newspaper == null) {
            return Either.left(-1);
        }
        articles = (List<Article>) newspaper.get("articles");
        if (articles == null) {
            return Either.left(-1);
        } else {
            return Either.right(articles);
        }
    }

    public Either<Integer, List<Article>> getAll(Reader reader) {
        try {
            MongoCollection<Document> collection = db.getCollection("newspapers");
            Bson filter = elemMatch("readers", new Document("id", reader.getId()));
            Bson projection = fields(include("articles"));
            List<Document> newspapers = collection.find(filter).projection(projection).into(new ArrayList<>());
            List<Article> articles = new ArrayList<>();
            for (Document newspaper : newspapers) {
                List<Document> articleDocuments = (List<Document>) newspaper.get("articles");
                if (articleDocuments != null) {
                    for (Document articleDocument : articleDocuments) {
                        articles.add(gson.fromJson(articleDocument.toJson(), Article.class));
                    }
                }
            }
            return Either.right(articles);
        } catch (Exception e) {
            return Either.left(-1);
        }
    }


    public Integer delete(String name) {
        try {
            MongoCollection<Document> collection = db.getCollection("newspapers");
            Bson filter = eq("name", name);
            Bson update = unset("articles");
            UpdateResult result = collection.updateMany(filter, update);
            return result.getModifiedCount() == 1 ? 1 : -1;
        } catch (Exception e) {
            return -1;
        }
    }
}