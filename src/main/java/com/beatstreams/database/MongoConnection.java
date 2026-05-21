package com.beatstreams.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConnection {

    // MongoDB connection URI
    private static final String URI = "mongodb://localhost:27017";

    // Database name
    private static final String DB_NAME = "beatstreams";

    // Songs folder path (your requirement)
    public static final String SONGS_FOLDER = "E:/songs_library/";

    // Single MongoClient instance (best practice)
    private static MongoClient client = MongoClients.create(URI);

    // Get database
    public static MongoDatabase getDatabase() {
        return client.getDatabase(DB_NAME);
    }
}