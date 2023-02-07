package data;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class DaoQueries {

    private MongoClient mongo;
    private MongoDatabase db;

    public DaoQueries() {
        this.mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
        this.db = mongo.getDatabase("JavierdelaCuadra");
    }

    //A. Get the id of the readers of articles of a specific type

//    [
//      {
//        $match:
//          /**
//           * query: The query in MQL.
//           */
//          {
//            "articles.type": "Sports",
//          },
//      },
//      {
//        $unwind: "$articles",
//      },
//      {
//        $match: {
//          "articles.type": "Sports",
//        },
//      },
//      {
//        $project: {
//          "articles.readers": 1,
//        },
//      },
//      {
//        $unwind: "$articles.readers",
//      },
//      {
//        $group: {
//          _id: "$articles.readers.id",
//        },
//      },
//      {
//        $project: {
//          _id: 1,
//        },
//      },
//    ]

    public void query1() {
        List<String> readers = new ArrayList<>();
        MongoCollection<Document> collection = db.getCollection("newspapers");

        List<Document> cosas = (List<Document>) collection.aggregate(List.of(
                new Document("$match", new Document("articles.type", "Sports")),
                new Document("$unwind", "$articles"),
                new Document("$match", new Document("articles.type", "Sports")),
                new Document("$project", new Document("articles.readers", 1L)),
                new Document("$unwind", "$articles.readers"),
                new Document("$group", new Document("_id", "$articles.readers.id")),
                new Document("$project", new Document("_id", 1L))
        ));

        cosas.forEach(reader -> readers.add(reader.getString("_id")));
    }

//    B. Get the average rating of the articles read by a reader, group by newspaper
//
    //[
//  {
//    $match: {
//      "readers.id": 3,
//    },
//  },
//  {
//    $unwind: "$articles",
//  },
//  {
//    $unwind: "$articles.readers",
//  },
//  {
//    $match: {
//      "articles.readers.id": 3,
//    },
//  },
//  {
//    $group: {
//      _id: "$name",
//      avg: {
//        $avg: "$articles.readers.rating",
//      },
//    },
//  },
//  {
//    $project: {
//      _id: 1,
//      avg: 1,
//    },
//  },
//]

    public void query2() {
        List<String> readers = new ArrayList<>();
        MongoCollection<Document> collection = db.getCollection("newspapers");

        List<Document> cosas = (List<Document>) collection.aggregate(List.of(
                new Document("$match", new Document("readers.id", 3)),
                new Document("$unwind", "$articles"),
                new Document("$unwind", "$articles.readers"),
                new Document("$match", new Document("articles.readers.id", 3)),
                new Document("$group", new Document("_id", "$name").append("avg", new Document("$avg", "$articles.readers.rating"))),
                new Document("$project", new Document("_id", 1L).append("avg", 1L))
        ));

        cosas.forEach(reader -> readers.add(reader.getString("_id")));
    }

    //C. Get the type of articles that are most read

//    [
//  {
//    $unwind: "$articles",
//  },
//  {
//    $unwind: "$articles.readers",
//  },
//  {
//    $group: {
//      _id: "$articles.type",
//      count: {
//        $sum: 1,
//      },
//    },
//  },
//  {
//    $sort: {
//      count: -1,
//    },
//  },
//  {
//    $limit: 1,
//  },
//  {
//    $project: {
//      _id: 1,
//    },
//  },

    public void query3() {
        List<String> readers = new ArrayList<>();
        MongoCollection<Document> collection = db.getCollection("newspapers");

        List<Document> cosas = (List<Document>) collection.aggregate(List.of(
                new Document("$unwind", "$articles"),
                new Document("$unwind", "$articles.readers"),
                new Document("$group", new Document("_id", "$articles.type").append("count", new Document("$sum", 1))),
                new Document("$sort", new Document("count", -1)),
                new Document("$limit", 1),
                new Document("$project", new Document("_id", 1L))
        ));

        cosas.forEach(reader -> readers.add(reader.getString("_id")));
    }

    //D. Show a list with the number of articles of each type of the selected newspaper

//    [
//  {
//    $match: {
//      name: "El Adios Mundo",
//    },
//  },
//  {
//    $unwind: "$articles",
//  },
//  {
//    $group: {
//      _id: "$articles.type",
//      count: {
//        $sum: 1,
//      },
//    },
//  },
//  {
//    $project: {
//      _id: 1,
//      count: 1,
//    },
//  },
//]

    public void query4() {
        List<String> readers = new ArrayList<>();
        MongoCollection<Document> collection = db.getCollection("newspapers");

        List<Document> cosas = (List<Document>) collection.aggregate(List.of(
                new Document("$match", new Document("name", "El Adios Mundo")),
                new Document("$unwind", "$articles"),
                new Document("$group", new Document("_id", "$articles.type").append("count", new Document("$sum", 1))),
                new Document("$project", new Document("_id", 1L).append("count", 1L))
        ));

        cosas.forEach(reader -> readers.add(reader.getString("_id")));
    }

    //E. Get the description and the number of readers of each article

//    [
//  {
//    $unwind: "$articles",
//  },
//  {
//    $project: {
//      _id: 0,
//      description: "$articles.description",
//      readers: {
//        $size: "$articles.readers",
//      },
//    },
//  },
//]

    public void query5() {
        List<String> readers = new ArrayList<>();
        MongoCollection<Document> collection = db.getCollection("newspapers");

        List<Document> cosas = (List<Document>) collection.aggregate(List.of(
                new Document("$unwind", "$articles"),
                new Document("$project", new Document("_id", 0L).append("description", "$articles.description").append("readers", new Document("$size", "$articles.readers")))
        ));

        cosas.forEach(reader -> readers.add(reader.getString("_id")));
    }

    //F. Get the name of the 10 oldest newspapers

//    [
//  {
//    $sort: {
//      releaseDate: 1,
//    },
//  },
//  {
//    $limit: 10,
//  },
//  {
//    $project: {
//      _id: 0,
//      name: 1,
//    },
//  },

    public void query6() {
        List<String> readers = new ArrayList<>();
        MongoCollection<Document> collection = db.getCollection("newspapers");

        List<Document> cosas = (List<Document>) collection.aggregate(List.of(
                new Document("$sort", new Document("releaseDate", 1)),
                new Document("$limit", 10),
                new Document("$project", new Document("_id", 0L).append("name", 1L))
        ));

        cosas.forEach(reader -> readers.add(reader.getString("_id")));
    }

    //G. Get the articles of a given type, together with the name of the newspaper

//    [
//  {
//    $unwind: "$articles",
//  },
//  {
//    $match: {
//      "articles.type": "Sports",
//    },
//  },
//  {
//    $project: {
//      _id: 0,
//      name: 1,
//      articles: 1,
//    },
//  },
//]

    public void query7() {
        List<String> readers = new ArrayList<>();
        MongoCollection<Document> collection = db.getCollection("newspapers");

        List<Document> cosas = (List<Document>) collection.aggregate(List.of(
                new Document("$unwind", "$articles"),
                new Document("$match", new Document("articles.type", "Sports")),
                new Document("$project", new Document("_id", 0L).append("name", 1L).append("articles", 1L))
        ));

        cosas.forEach(reader -> readers.add(reader.getString("_id")));
    }

    //H. Get the articles of a given type, together with the name of the newspaper, and the number of readers of each article
}