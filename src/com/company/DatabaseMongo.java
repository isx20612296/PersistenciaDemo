package com.company;

import com.company.model.Movie;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.stream.Stream;

import static com.mongodb.client.model.Filters.eq;

public class DatabaseMongo implements IDatabase{

    private final static String uri = "mongodb://localhost";

    @Override
    public void insert(String title) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("movies");

            // INSERT
            Document doc = new Document();
            doc.append("title", title);
            collection.insertOne(doc);

            //QUERY
            System.out.println(collection.find(eq("title", title)).first().toJson());
        }
    }

    @Override
    public Stream<Movie> selectAll() {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("movies");

            final ArrayList<Movie> movies = new ArrayList<>();
            collection.find().forEach(m -> {
                Movie movie = new Movie(m.getObjectId("_id"), m.getString("title"));
                movies.add(movie);
            });
            return movies.stream();
        }
    }

    @Override
    public Stream<Movie> selectOne(String title) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("movies");
            final ArrayList<Movie> movies = new ArrayList<>();
            collection.find(eq("title", title)).forEach(m -> {
                Movie movie = new Movie(m.getObjectId("_id"), m.getString("title"));
                movies.add(movie);
            });

            return movies.stream();
        }
    }

    @Override
    public void deleteOne(String title) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("movies");

            //QUERY
            collection.deleteOne(eq("title", title));
        }
    }

    @Override
    public void deleteAll() {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("sampledb");
            database.getCollection("movies").deleteMany(new Document());
        }
    }
}
