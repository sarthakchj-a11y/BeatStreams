package com.beatstreams.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.File;

public class SongLoader {

    public static void main(String[] args) {

        MongoDatabase db = MongoConnection.getDatabase();
        MongoCollection<Document> songsCollection = db.getCollection("songs");
        MongoCollection<Document> albumsCollection = db.getCollection("albums");

        // ✅ USE C DRIVE (IMPORTANT FIX)
        File mainFolder = new File("C:\\beatstreams_songs");

        System.out.println("Exists: " + mainFolder.exists());
        System.out.println("Is Directory: " + mainFolder.isDirectory());

        File[] albumFolders = mainFolder.listFiles();

        if (albumFolders == null) {
            System.out.println("listFiles() returned NULL ❌");
            return;
        }

        System.out.println("Total folders: " + albumFolders.length);

        for (File albumFolder : albumFolders) {

            if (!albumFolder.isDirectory()) {
                System.out.println("Skipped: " + albumFolder.getName());
                continue;
            }

            String albumName = albumFolder.getName();
            System.out.println("Album: " + albumName);

            // ✅ IMAGE PATH
            String image = "";

if (albumName.equalsIgnoreCase("Rockstar")) {
    image = "images/rockstar.jpg";
} else if (albumName.equalsIgnoreCase("2 States")) {
    image = "images/2states.jpg";
} else if (albumName.equalsIgnoreCase("Dhurandhar The Revenge")) {
    image = "images/dhurandhar.jpg";
} else if (albumName.equalsIgnoreCase("Saiyaara")) {
    image = "images/saiyaara.jpg";
} else if (albumName.equalsIgnoreCase("Indie Artists")) {
    image = "images/indie.jpg";
}

            // Insert album
            if (albumsCollection.find(new Document("album", albumName)).first() == null) {
                albumsCollection.insertOne(
                        new Document("album", albumName).append("image", image)
                );
                System.out.println("Inserted album: " + albumName);
            }

            File[] songs = albumFolder.listFiles();

            if (songs == null) {
                System.out.println("No songs found in: " + albumName);
                continue;
            }

            for (File song : songs) {

                System.out.println("  Found: " + song.getName());

                if (song.isFile() && song.getName().toLowerCase().endsWith(".mp3")) {

                    Document doc = new Document("fileName", song.getName())
                            .append("album", albumName)
                            .append("path", song.getAbsolutePath())
                            .append("image", image);

                    songsCollection.insertOne(doc);

                    System.out.println("  Inserted: " + song.getName());
                }
            }
        }

        System.out.println("DONE");
    }
}