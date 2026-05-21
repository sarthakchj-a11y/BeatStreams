package com.beatstreams.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (session != null && session.getAttribute("username") != null) {
            String username = (String) session.getAttribute("username");
            out.print("{\"username\":\"" + username + "\"}");
        } else {
            out.print("{\"username\":null}");
        }

        out.flush();
    }
}