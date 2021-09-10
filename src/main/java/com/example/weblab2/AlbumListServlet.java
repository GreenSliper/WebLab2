package com.example.weblab2;

import com.example.weblab2.localization.ILocalizator;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AlbumListServlet", value = "/")
public class AlbumListServlet extends HttpServlet {

    private IAlbumSet currentSet;
    private final ILocalizator.Languages defaultLang = ILocalizator.Languages.en;

    public void init()
    {
        System.out.println("AlbumList servlet was created");
        currentSet = new AlbumSet(new DBAlbumSetSaveLoader());
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
        request.setAttribute("lang", lang);
    }

    private List<Boolean> getCanVoteList(List<IAlbumLine> lines, String username)
    {
        var res = new ArrayList<Boolean>();
        if(username==null)
            for(var line : lines)
                res.add(false);
        else
        {
            for(int i = 0; i<lines.size(); i++)
                res.add(currentSet.canUserAddRating(i, username));
        }
        return res;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        //processInputData(request);
        currentSet.load();
        setLang(request);
        request.setAttribute("lines", currentSet.getLines());
        request.setAttribute("canVoteList",
                getCanVoteList(currentSet.getLines(),
                        request.getParameter("username")));
        RequestDispatcher requestDispatcher =
                request.getRequestDispatcher("AlbumList.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        if(dataCorrect && username!=null && !username.isEmpty())
            if(currentSet.canUserAddRating(albumID, username))
                currentSet.addRating(albumID, rating, username);
        doGet(request, response);
    }
}
