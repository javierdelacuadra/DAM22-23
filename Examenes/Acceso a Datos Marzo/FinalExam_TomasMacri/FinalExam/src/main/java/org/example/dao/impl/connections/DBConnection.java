package org.example.dao.impl.connections;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.example.config.Configuration;

@Singleton
@Log4j2
public class DBConnection {

    private final Configuration config;
    private final MongoClient mongo;

    private final MongoDatabase db;

    private final MongoCollection<Document> est;
    @Inject
    public DBConnection(Configuration configuration) {
        this.config = configuration;
        mongo = MongoClients.create(config.getUrlDB());
        db = mongo.getDatabase(config.getDatabase());
        est = db.getCollection(config.getCollection());

    }

    public MongoCollection<Document> getEst() {
        return est;
    }
}
