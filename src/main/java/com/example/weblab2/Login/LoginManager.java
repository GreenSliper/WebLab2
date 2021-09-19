package com.example.weblab2.Login;

import com.example.weblab2.DBFacade;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

public class LoginManager implements ILoginManager {
    private final DBFacade db = DBFacade.getInstance();
    @Override
    public boolean tryLogin(String login, String password) {
        try {
            var query = db.ExecuteQuery("select * from users u where u.name='"+ login
                    + "' and u.password='"+password+"';");
            return query.next();
        } catch (DBFacade.DBNotConnectedException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static class CookieAuthResilt
    {
        private String username, password;
        private boolean authSuccess = false;

        public CookieAuthResilt(String username, String password, boolean authSuccess)
        {
            this.username = username;
            this.password = password;
            this.authSuccess = authSuccess;
        }

        public String getUsername(){
            return username;
        }

        public boolean getAuthSuccess() {
            return authSuccess;
        }
    }

    @Override
    public CookieAuthResilt tryAuthViaCookies(HttpServletRequest request)
    {
        String username = "", password = "";
        try {
            var cookies = request.getCookies();
            username = Arrays.stream(cookies).
                    filter(x -> Objects.equals(x.getName(), "username"))
                    .findAny().get().getValue();
            password = Arrays.stream(cookies).
                    filter(x -> Objects.equals(x.getName(), "password"))
                    .findAny().get().getValue();
        }
        catch (Exception e) {
            return new CookieAuthResilt("", "", false);
        }
        return new CookieAuthResilt(username, password,
                tryLogin(username, password));
    }

    private void killCookie(Cookie cookie, HttpServletRequest request,
                            HttpServletResponse response)
    {
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    public void removeLoginCookies(HttpServletRequest request,
                                   HttpServletResponse response) {
        try {
            var cookies = request.getCookies();
            var username = Arrays.stream(cookies).
                    filter(x -> Objects.equals(x.getName(), "username"))
                    .findAny().get();
            killCookie(username, request, response);
            var password = Arrays.stream(cookies).
                    filter(x -> Objects.equals(x.getName(), "password"))
                    .findAny().get();
            killCookie(password, request, response);
        }
        catch (Exception ignored) {}
    }

}
