package com.example.weblab2.localization;

import java.util.Locale;
import java.util.ResourceBundle;

public class Localizator implements ILocalizator {
    private ResourceBundle currentResource;

    @Override
    public void init(ILocalizator.Languages lang)
    {
        currentResource = ResourceBundle.getBundle("AlbumList",
                new Locale(lang.name()));
    }

    @Override
    public String getResource(String key)
    {
        if(key==null || key.trim().isEmpty())
            return "";
        return currentResource.getString(key);
    }
}
