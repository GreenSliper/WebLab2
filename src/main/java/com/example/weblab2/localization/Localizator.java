package com.example.weblab2.localization;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

public class Localizator {

    public enum Languages {en, ru}

    private ResourceBundle currentResource;
    private  Languages currentLang;

    private final Languages defaultLang = Languages.en;

    static Localizator instance;
    public static Localizator getInstance(HttpServletRequest request) {
        if(instance == null) {
            instance = new Localizator();
            instance.setLang(request, true);
        }
        return instance;
    }

    public void setLang(HttpServletRequest request, boolean setDefaultIfNull) {
        Languages lang;
        try {
            String langStr = request.getParameter("lang");
            lang = Languages.valueOf(langStr);
            if(lang == currentLang)
                return;
        }
        catch (Exception e)
        {
            if(setDefaultIfNull)
                lang = defaultLang;
            else
                return;
        }
        currentResource = ResourceBundle.getBundle("AlbumList",
                new Locale(lang.name()));
    }

    public String getResource(String key)
    {
        if(key==null || key.trim().isEmpty())
            return "";
        return currentResource.getString(key);
    }
}
