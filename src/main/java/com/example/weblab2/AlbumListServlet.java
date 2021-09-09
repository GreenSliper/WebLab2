package com.example.weblab2;

import com.example.weblab2.localization.ILocalizator;
import com.example.weblab2.localization.Localizator;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AlbumListServlet", value = "/")
public class AlbumListServlet extends HttpServlet {

    private IAlbumSet currentSet;
    private ILocalizator localizator;
    private final ILocalizator.Languages defaultLang = ILocalizator.Languages.en;

    public void init()
    {
        System.out.println("AlbumList servlet was created");
        currentSet = new AlbumSet(new DBAlbumSetSaveLoader());
        localizator = new Localizator();
    }

    public void destroy()
    {
        System.out.println("AlbumList servlet was destroyed");
    }

    private void setLang(HttpServletRequest request)
    {
        String langStr = request.getParameter("lang");
        ILocalizator.Languages lang;
        try {
            lang = ILocalizator.Languages.valueOf(langStr);
        }
        catch (Exception e)
        {
            lang = defaultLang;
        }
        localizator.init(lang);
    }

    public void sendView(HttpServletRequest request, HttpServletResponse response,
                         IAlbumSet tableData) throws IOException
    {
        setLang(request);
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head><title>"+localizator.getResource("title")+"</title></head>");
            out.println("<body>");
            out.println("<h1>"+localizator.getResource("header")+"</h1>");
            out.println("<table border='1' style=\"border-collapse:collapse;\">");
            out.println("<tr><td><h4>" + localizator.getResource("performer") +
                    "</h4></td><td><h4>" + localizator.getResource("album") +
                    "</h4></td><td><h4>" + localizator.getResource("rating") +
                    "</h4></td></tr>");
            if(tableData!=null)
                out.println(tableData.getTable());
            out.println("</table>");
            out.println("<br><br>");
            out.println("<p>" + localizator.getResource("switchLang") +
                    " <a href='/?lang=ru'>Русский</a>" +
                    " <a href='/?lang=en'>English</a>" +
                    " </p>");
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
