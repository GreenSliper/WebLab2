package com.example.weblab2.localization;

public interface ILocalizator {
    public enum Languages { ru, en }

    void init(Languages lang);
    String getResource(String key);
}
