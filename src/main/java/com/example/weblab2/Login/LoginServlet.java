package com.example.weblab2.Login;

import com.example.weblab2.localization.Localizator;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private ILoginManager loginManager = new LoginManager();

    private void setLang(HttpServletRequest request)
    {
        Localizator.getInstance(request).setLang(request, false);
    }

    boolean checkLogout(HttpServletRequest request, HttpServletResponse responce)
    {
        var logout = request.getParameter("logout");
        if(logout!=null && logout.equals("true")){
            loginManager.removeLoginCookies(request, responce);
            return true;
        }
        return false;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(checkLogout(request, response)) {
            response.sendRedirect("/");
            return;
        }setLang(request);
        RequestDispatcher requestDispatcher =
                request.getRequestDispatcher("login.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            var username = request.getParameter("username");
            var pass = request.getParameter("password");
            if (loginManager.tryLogin(username, pass)) {
                Cookie userCookie = new Cookie("username", username);
                Cookie passCookie = new Cookie("password", pass);
                userCookie.setMaxAge(24 * 60 * 60);
                passCookie.setMaxAge(24 * 60 * 60);
                response.addCookie(userCookie);
                response.addCookie(passCookie);
            } else {
                loginManager.removeLoginCookies(request, response);
                request.setAttribute("username", username);
                request.setAttribute("incorrectData", true);
                RequestDispatcher requestDispatcher =
                        request.getRequestDispatcher("login.jsp");
                requestDispatcher.forward(request, response);
            }
        } finally {
            response.sendRedirect("/");
        }
    }
}
