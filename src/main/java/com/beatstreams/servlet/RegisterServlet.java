package com.beatstreams.servlet;

import com.beatstreams.database.MongoConnection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        MongoDatabase db = MongoConnection.getDatabase();
        MongoCollection<Document> users = db.getCollection("users");

        Document user = new Document("username", username)
                .append("password", password);

        users.insertOne(user);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h2>User Registered Successfully</h2>");
        out.println("<a href='login.html'>Login Now</a>");
    }
}