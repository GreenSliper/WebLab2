package com.example.weblab2;

public interface IAlbumSet {
    void load();
    void save();
    boolean tryAddRating(int lineId, int rating, String username);
    String getTable();
}
