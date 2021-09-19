package com.example.weblab2;

import com.example.weblab2.Login.ILoginManager;
import com.example.weblab2.Login.LoginManager;
import com.example.weblab2.localization.Localizator;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "AlbumListServlet", value = "/")
public class AlbumListServlet extends HttpServlet {

    private IAlbumSet currentSet;
    private ILoginManager loginManager = new LoginManager();

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
        Localizator.getInstance(request).setLang(request, false);
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
        currentSet.load();
        setLang(request);
        request.setAttribute("lines", currentSet.getLines());
        var auth = loginManager.tryAuthViaCookies(request);
        if(auth.getAuthSuccess()) {
            request.setAttribute("canVoteList",
                    getCanVoteList(currentSet.getLines(),
                            auth.getUsername()));
            request.setAttribute("loggedInUsername", auth.getUsername());
        }else {
            loginManager.removeLoginCookies(request, response);
            request.setAttribute("canVoteList",
                    getCanVoteList(currentSet.getLines(),
                            null));
        }
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
        var auth = loginManager.tryAuthViaCookies(request);
        if(auth.getAuthSuccess()) {
            String username = auth.getUsername();
            if (dataCorrect)
                if (currentSet.canUserAddRating(albumID, username))
                    currentSet.addRating(albumID, rating, username);
        }
        else //return to login page with message of error
        {
            RequestDispatcher requestDispatcher =
                    request.getRequestDispatcher("login.jsp");
            request.setAttribute("incorrectData", true);
            requestDispatcher.forward(request, response);
        }
        doGet(request, response);
    }
}
