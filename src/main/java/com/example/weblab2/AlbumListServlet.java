package com.example.weblab2;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AlbumListServlet", value = "/")
public class AlbumListServlet extends HttpServlet {
    IAlbumSet currentSet;

    public void init()
    {
        System.out.println("AlbumList servlet was created");
        currentSet = new AlbumSet(new DBAlbumSetSaveLoader());
    }

    public void destroy()
    {
        System.out.println("AlbumList servlet was destroyed");
    }

    public void sendView(HttpServletRequest request, HttpServletResponse response,
                         IAlbumSet tableData) throws IOException
    {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head><title>All Albums</title></head>");
            out.println("<body>");
            out.println("<h1>All Albums</h1>");
            out.println("<table border='1' style=\"border-collapse:collapse;\">");
            out.println("<tr><td><h4>Performer</h4></td><td><h4>Album</h4></td><td><h4>Rating</h4></td></tr>");
            if(tableData!=null)
                out.println(tableData.getTable());
            out.println("</body>");
            out.println("</html>");
        }
    }

    public void processInputData(HttpServletRequest request)
    {
        boolean dataCorrect = true;
        int albumID = 0, rating = 0;
        try {
            albumID = Integer.parseInt(request.getParameter("albumid"));
            rating = Integer.parseInt(request.getParameter("rating"));
        }
        catch (NumberFormatException e) {
            dataCorrect = false;
        }
        String username = request.getParameter("username");
        if(dataCorrect && !username.isEmpty())
            currentSet.tryAddRating(albumID, rating, username);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processInputData(request);
        currentSet.load();
        sendView(request, response, currentSet);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processInputData(request);
        currentSet.load();
        sendView(request, response, currentSet);
    }
}
