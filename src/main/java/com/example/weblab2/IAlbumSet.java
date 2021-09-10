package com.example.weblab2;
import java.util.*;

public interface IAlbumSet {
    void load();
    void save();
    boolean userHasRating(IAlbumLine line, String username);
    boolean canUserAddRating(int lineId, String username);
    boolean addRating(int lineId, int rating, String username);
    List<IAlbumLine> getLines();
}
