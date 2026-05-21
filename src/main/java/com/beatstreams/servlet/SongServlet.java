package com.beatstreams.servlet;

import com.beatstreams.database.MongoConnection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/songs")
public class SongServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String search = request.getParameter("search");
        if (search != null && !search.isEmpty()) {
            searchSongs(search, response);
            return;
        }

        String fileName = request.getParameter("file");
        if (fileName != null && !fileName.isEmpty()) {
            streamSong(fileName, response);
            return;
        }

        getSongList(response);
    }

    private void getSongList(HttpServletResponse response) throws IOException {

        MongoDatabase db = MongoConnection.getDatabase();
        MongoCollection<Document> songsCol = db.getCollection("songs");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        StringBuilder json = new StringBuilder();
        json.append("{\"songs\":[");

        boolean first = true;

        for (Document doc : songsCol.find()) {

            if (!first) json.append(",");
            first = false;

            json.append("{");
            json.append("\"fileName\":\"").append(doc.getString("fileName")).append("\",");
            json.append("\"album\":\"").append(doc.getString("album")).append("\",");
            json.append("\"image\":\"").append(doc.getString("image")).append("\"");
            json.append("}");
        }

        json.append("]}");

        out.print(json.toString());
    }

    private void streamSong(String fileName, HttpServletResponse response) throws IOException {

        MongoDatabase db = MongoConnection.getDatabase();
        MongoCollection<Document> collection = db.getCollection("songs");

        Document song = collection.find(new Document("fileName", fileName)).first();

        if (song == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        File file = new File(song.getString("path"));

        response.setContentType("audio/mpeg");
        response.setContentLengthLong(file.length());

        FileInputStream fis = new FileInputStream(file);
        OutputStream os = response.getOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = fis.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }

        fis.close();
        os.close();
    }

    private void searchSongs(String query, HttpServletResponse response) throws IOException {

        MongoDatabase db = MongoConnection.getDatabase();
        MongoCollection<Document> collection = db.getCollection("songs");

        List<String> results = new ArrayList<>();

        for (Document doc : collection.find()) {
            String name = doc.getString("fileName");
            String album = doc.getString("album");

            if (name.toLowerCase().contains(query.toLowerCase()) ||
                album.toLowerCase().contains(query.toLowerCase())) {

                results.add(name);
            }
        }

        response.setContentType("application/json");

        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < results.size(); i++) {
            json.append("\"").append(results.get(i)).append("\"");
            if (i < results.size() - 1) json.append(",");
        }
        json.append("]");

        response.getWriter().print(json.toString());
    }
}