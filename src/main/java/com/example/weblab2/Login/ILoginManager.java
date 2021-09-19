package com.example.weblab2.Login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ILoginManager {
    boolean tryLogin(String login, String password);
    LoginManager.CookieAuthResilt tryAuthViaCookies(HttpServletRequest request);
    void removeLoginCookies(HttpServletRequest request, HttpServletResponse response);
}
