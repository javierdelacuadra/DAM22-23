package data;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Indexes.ascending;
import static com.mongodb.client.model.Indexes.descending;
import static com.mongodb.client.model.Projections.*;

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

    public List<String> query1() {
        List<String> readers = new ArrayList<>();
        MongoCollection<Document> collection = db.getCollection("newspapers");

        collection.aggregate(Arrays.asList(match(eq("articles.type", "Sports")),
                unwind("$articles"),
                match(eq("articles.type", "Sports")),
                project(fields(include("articles.readers"), excludeId())),
                unwind("$articles.readers"),
                group("$articles.readers.id"))).into(new ArrayList<>()).forEach(reader -> readers.add(String.valueOf(reader.getInteger("_id"))));

        return readers;
    }

//    B. Get the average rating of the articles read by a reader, group by newspaper
//
//    [
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

    public List<String> query2() {
        List<String> readers = new ArrayList<>();
        MongoCollection<Document> collection = db.getCollection("newspapers");

        collection.aggregate(Arrays.asList(match(eq("readers.id", 3)),
                unwind("$articles"),
                unwind("$articles.readers"),
                match(eq("articles.readers.id", 3)),
                group("$name", avg("avg", "$articles.readers.rating")))).into
                (new ArrayList<>()).forEach(reader -> readers.add(reader.getString("_id") + " - " + reader.getDouble("avg")));

        return readers;
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

    public List<String> query3() {
        List<String> readers = new ArrayList<>();
        MongoCollection<Document> collection = db.getCollection("newspapers");

        collection.aggregate(Arrays.asList(unwind("$articles"),
                unwind("$articles.readers"),
                group("$articles.type", sum("count", 1)),
                sort(descending("count")),
                limit(1))).into(new ArrayList<>()).forEach(reader -> readers.add(reader.getString("_id")));

        return readers;

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

    public List<String> query4() {
        List<String> readers = new ArrayList<>();
        MongoCollection<Document> collection = db.getCollection("newspapers");

        collection.aggregate(Arrays.asList(match(eq("name", "El Adios Mundo")),
                unwind("$articles"),
                group("$articles.type", sum("count", 1)))).into(new ArrayList<>()).forEach(reader -> readers.add(reader.getString("_id") + " - " + reader.getInteger("count")));

        return readers;
    }

    //E. Get the description and the number of readers of each article

//[
//  {
//    $unwind: "$articles",
//  },
//  {
//    $project: {
//      _id: 0,
//      name: "$articles.name",
//      description: "$articles.type",
//      readers: {
//        $size: "$articles.readers",
//      },
//    },
//  },
//]

    public List<String> query5() {
        List<String> articles = new ArrayList<>();
        MongoCollection<Document> collection = db.getCollection("newspapers");

        collection.aggregate(Arrays.asList(unwind("$articles"),
                project(fields(include("articles.name", "articles.type", "articles.readers"), excludeId())))).into(new ArrayList<>()).forEach(reader -> {
            Document article = (Document) reader.get("articles");
            String name = article.getString("name");
            String type = article.getString("type");
            List readers = article.get("readers", List.class);
            int readerCount = (readers != null) ? readers.size() : 0;
            articles.add(name + " - " + type + " - " + readerCount);
        });

        return articles;
    }

    //F. Get the name of the 10 oldest newspapers

//[
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
//      releaseDate: 1,
//    },
//  },
//]

    public List<String> query6() {
        List<String> readers = new ArrayList<>();
        MongoCollection<Document> collection = db.getCollection("newspapers");

        collection.aggregate(Arrays.asList(sort(ascending("releaseDate")),
                limit(10),
                project(fields(include("name", "releaseDate"), excludeId())))).into(new ArrayList<>()).forEach(reader -> readers.add(reader.getString("name") + " - " + reader.getString("releaseDate")));

        return readers;
    }

//    G. Get the articles of a given type, together with the name of the newspaper
//
//    [
//  {
//    $unwind: "$articles",
//  },
//  {
//    $match: {
//      "articles.type": "Politics",
//    },
//  },
//  {
//    $project: {
//      _id: 0,
//      name: 1,
//      "articles.name": 1,
//    },
//  },
//]

    public List<String> query7() {
        List<String> readers = new ArrayList<>();
        MongoCollection<Document> collection = db.getCollection("newspapers");

        collection.aggregate(Arrays.asList(
                unwind("$articles"),
                match(eq("articles.type", "Politics")),
                project(fields(include("name", "articles.name"), excludeId())))).iterator().forEachRemaining(document -> {
            String newspaperName = document.getString("name");
            String articleName = document.get("articles", Document.class).getString("name");
            readers.add(newspaperName + " - " + articleName);
        });

        return readers;
    }

    //H. Get the number of Sports articles by newspaper

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
//    $group: {
//      _id: "$name",
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

    public List<String> query8() {
        List<String> readers = new ArrayList<>();
        MongoCollection<Document> collection = db.getCollection("newspapers");

        collection.aggregate(Arrays.asList(
                unwind("$articles"),
                match(eq("articles.type", "Sports")),
                group("$name", sum("count", 1)),
                project(fields(include("_id", "count"))))).into(new ArrayList<>()).forEach(reader -> readers.add(reader.getString("_id") + " - " + reader.getInteger("count")));

        return readers;
    }

    //I. Get the name of the newspaper with highest number of Sports articles

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
//    $group: {
//      _id: "$name",
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
//]

    public List<String> query9() {
        List<String> readers = new ArrayList<>();
        MongoCollection<Document> collection = db.getCollection("newspapers");

        collection.aggregate(Arrays.asList(
                unwind("$articles"),
                match(eq("articles.type", "Sports")),
                group("$name", sum("count", 1)),
                sort(descending("count")),
                limit(1),
                project(fields(include("_id"))))).into(new ArrayList<>()).forEach(reader -> readers.add(reader.getString("_id")));

        return readers;
    }

    //J. Get the articles with no rating lower than 3

//[
//  {
//    $unwind: "$articles",
//  },
//  {
//    $match: {
//      "articles.readers.mark": { $gte: 3 },
//    },
//  },
//  {
//    $project: {
//      _id: 0,
//      name: "$articles.name",
//      type: "$articles.type",
//      readers: "$articles.readers",
//    },
//  },
//]

    public List<String> query10() {
        List<String> articles = new ArrayList<>();
        MongoCollection<Document> collection = db.getCollection("newspapers");

        collection.aggregate(Arrays.asList(
                unwind("$articles"),
                match(gte("articles.readers.mark", 3)),
                project(fields(include("articles.name", "articles.type", "articles.readers"), excludeId())))).into(new ArrayList<>()).forEach(reader -> {
            Document article = (Document) reader.get("articles");
            String name = article.getString("name");
            String type = article.getString("type");
            List readers = article.get("readers", List.class);
            int readerCount = (readers != null) ? readers.size() : 0;
            articles.add(name + " - " + type + " - " + readerCount);
        });
        return articles;
    }

    //K. Get the average number of subscriptions per reader

// [
//    {
//        $unwind: "$articles"
//    },
//    {
//        $unwind: "$articles.readers"
//    },
//    {
//        $group: {
//            _id: "$articles.readers.id",
//            count: {
//                $sum: 1
//            }
//        }
//    },
//    {
//        $group: {
//            _id: null,
//            avgSubscriptions: {
//                $avg: "$count"
//            }
//        }
//    }
//]

    public List<String> query11() {
        List<String> readers = new ArrayList<>();
        MongoCollection<Document> collection = db.getCollection("newspapers");

        collection.aggregate(Arrays.asList(
                unwind("$articles"),
                unwind("$articles.readers"),
                group("$articles.readers.id", sum("count", 1)),
                group(null, avg("avgSubscriptions", "$count")))).into(new ArrayList<>()).forEach(reader -> readers.add(String.valueOf(reader.getDouble("avgSubscriptions"))));

        return readers;
    }

    //L. Number of read articles per reader

//    [
//  {
//    $unwind: "$readers",
//  },
//  {
//    $group: {
//      _id: "$readers.name",
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

    public List<String> query12() {
        List<String> readers = new ArrayList<>();
        MongoCollection<Document> collection = db.getCollection("newspapers");

        collection.aggregate(Arrays.asList(
                unwind("$readers"),
                group("$readers.name", sum("count", 1)),
                project(fields(include("_id", "count"))))).into(new ArrayList<>()).forEach(reader -> readers.add(reader.getString("_id") + " - " + reader.getInteger("count")));

        return readers;

    }

    //M. Number of articles with average rating greater than 4

//    [
//  {
//    $unwind: "$articles",
//  },
//  {
//    $unwind: "$articles.readers",
//  },
//  {
//    $group: {
//      _id: "$articles.name",
//      avgMark: {
//        $avg: "$articles.readers.mark",
//      },
//    },
//  },
//  {
//    $match: {
//      avgMark: {
//        $gt: 4,
//      },
//    },
//  },
//  {
//    $count: "count",
//  },
//]

    public List<String> query13() {
        List<String> readers = new ArrayList<>();
        MongoCollection<Document> collection = db.getCollection("newspapers");

        collection.aggregate(Arrays.asList(
                unwind("$articles"),
                unwind("$articles.readers"),
                group("$articles.name", avg("avgMark", "$articles.readers.mark")),
                match(gt("avgMark", 4)),
                count("count"))).into(new ArrayList<>()).forEach(reader -> readers.add(String.valueOf(reader.getInteger("count"))));

        return readers;
    }

//    N. Readers with no ratings lower than 3
//
//[
//  {
//    $unwind: "$articles"
//  },
//  {
//    $unwind: "$articles.readers"
//  },
//  {
//    $match: {
//      "articles.readers.mark": { $gte: 3 }
//    }
//  },
//  {
//    $group: {
//      "_id": null,
//      "names": {
//        $addToSet: "$readers.name"
//      }
//    }
//  },
//  {
//    $project: {
//      "_id": 0,
//      "names": 1
//    }
//  }
//]

    public List<String> query14() {
        List<String> readers = new ArrayList<>();
        MongoCollection<Document> collection = db.getCollection("newspapers");

        collection.aggregate(Arrays.asList(
                        unwind("$articles"),
                        unwind("$articles.readers"),
                        match(gte("articles.readers.mark", 3)),
                        group(null, addToSet("readerIds", "$articles.readers.id")),
                        project(fields(include("readerIds"), excludeId())))).into(new ArrayList<>())
                .forEach(reader -> {
                    if (reader.get("readerIds", ArrayList.class) != null) {
                        List<Integer> readerIds = reader.get("readerIds", ArrayList.class);
                        readers.addAll(readerIds.stream().map(String::valueOf).collect(Collectors.toList()));
                    }
                });
        return readers;
    }

    //O. Get the newspapers with an average rating lower than 5, indicating the readers that have rated more than 4 articles with a lower-than-5 rating

//    [
//{
//$unwind: "$articles"
//},
//{
//$unwind: "$articles.readers"
//},
//{
//$match: {
//"articles.readers.rating": {
//$lt: 5
//}
//}
//},
//{
//$group: {
//_id: {
//newspaper: "$name",
//reader: "$articles.readers.id"
//},
//count: {
//$sum: 1
//}
//}
//},
//{
//$match: {
//count: {
//$gt: 4
//}
//}
//},
//{
//$group: {
//_id: "$_id.newspaper",
//readers: {
//$push: "$_id.reader"
//}
//}
//},
//{
//$project: {
//_id: 0,
//newspaper: "$_id",
//readers: "$readers"
//}
//}
//]

    public List<String> query15() {
        List<String> readers = new ArrayList<>();
        MongoCollection<Document> collection = db.getCollection("newspapers");

        collection.aggregate(Arrays.asList(
                        match(lt("articles.readers.rating", 5)),
                        unwind("$articles"),
                        unwind("$articles.readers"),
                        group("$_id", avg("averageRating", "$articles.readers.rating"), sum("totalRatings", 1)),
                        match(gte("totalRatings", 4)),
                        group("$_id", avg("averageRating", "$averageRating")),
                        match(lt("averageRating", 5)),
                        project(fields(include("_id"), excludeId())))).into(new ArrayList<>())
                .forEach(reader -> {
                    if (reader.getString("_id") != null && !reader.getString("_id").isEmpty()) {
                        readers.add(reader.getString("_id"));
                    }
                });
        if (readers.isEmpty()) {
            readers.add("No results");
        }
        return readers;
    }

    //P. Readers that are not registered as users (Use $lookup) (users are in login collection)

//    [
//{
//"$lookup": {
//"from": "login",
//"localField": "readers.id",
//"foreignField": "_id",
//"as": "registered_readers"
//}
//},
//{
//"$unwind": "$registered_readers"
//},
//{
//"$group": {
//"_id": "$readers.id",
//"count": {
//"$sum": 1
//}
//}
//},
//{
//"$match": {
//"count": {
//"$eq": 0
//}
//}
//}
//]

    public List<String> query16() {
        List<String> readers = new ArrayList<>();
        MongoCollection<Document> collection = db.getCollection("newspapers");

        collection.aggregate(Arrays.asList(
                        lookup("login", "readers.id", "_id", "registered_readers"),
                        unwind("$registered_readers"),
                        group("$readers.id", sum("count", 1)),
                        match(eq("count", 0)))).into(new ArrayList<>())
                .forEach(reader -> {
                    if (reader.getString("_id") != null && !reader.getString("_id").isEmpty()) {
                        readers.add(reader.getString("_id"));
                    }
                });
        if (readers.isEmpty()) {
            readers.add("No results");
        }
        return readers;
    }
}