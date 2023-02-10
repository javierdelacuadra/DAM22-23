package data;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jakarta.inject.Inject;
import model.Article;
import model.ReadArticle;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class DaoReadArticles {
    private MongoClient mongo;
    private MongoDatabase db;

    @Inject
    public DaoReadArticles() {
        this.mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
        this.db = mongo.getDatabase("JavierdelaCuadra");
    }

    public int save(ReadArticle readArticle, Article article) {
        MongoCollection<Document> collection = db.getCollection("newspapers");

        Document articleDoc = collection.find(eq("articles.name", article.getName())).first();
        if (articleDoc != null) {
            List<Document> articles = (List<Document>) articleDoc.get("articles");
            for (Document articleFromDb : articles) {
                if (articleFromDb.get("name").equals(article.getName())) {
                    List<Document> readers = (List<Document>) articleFromDb.get("readers");
                    if (readers != null) {
                        for (Document reader : readers) {
                            if (reader.get("id").equals(readArticle.getId())) {
                                return -1;
                            }
                        }
                        Document newReader = new Document("id", readArticle.getId())
                                .append("rating", readArticle.getMark());
                        readers.add(newReader);
                        articleFromDb.put("readers", readers);
                        collection.replaceOne(eq("articles.name", article.getName()), articleDoc);
                    } else {
                        List<Document> newReaders = new ArrayList<>();
                        newReaders.add(new Document("id", readArticle.getId())
                                .append("rating", readArticle.getMark()));
                        articleFromDb.put("readers", newReaders);
                        collection.replaceOne(eq("articles.name", article.getName()), articleDoc);
                    }
                    return 1;
                }
            }
        }
        return -2;
    }


    public int update(ReadArticle readArticle, Article article) {
        MongoCollection<Document> collection = db.getCollection("newspapers");

        Document articleDoc = collection.find(eq("articles.name", article.getName())).first();
        if (articleDoc != null) {
            List<Document> articles = (List<Document>) articleDoc.get("articles");
            for (Document articleFromDb : articles) {
                if (articleFromDb.get("name").equals(article.getName())) {
                    List<Document> readers = (List<Document>) articleFromDb.get("readers");
                    if (readers != null) {
                        for (Document reader : readers) {
                            if (reader.get("id").equals(readArticle.getId())) {
                                reader.put("rating", readArticle.getMark());
                                collection.replaceOne(eq("articles.name", article.getName()), articleDoc);
                                return 1;
                            }
                        }
                    }
                }
            }
        }
        return -1;
    }
}