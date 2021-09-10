package com.example.weblab2;

public interface IAlbumLine {
    String getHTMLTableLine();
    void addRating(int newRating);
    int getId();
    int getRatingsCount();
    float getRating();
    String getPerformer();
    String getAlbum();
}
