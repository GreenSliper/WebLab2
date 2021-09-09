package com.example.weblab2;

import java.util.List;

public interface IAlbumSetSaveLoader {
    void save(List<IAlbumLine> set);
    boolean userHasRating(IAlbumLine line, String username);
    void updateRating(IAlbumLine line);
    void addAlbumRatedNote(String user, IAlbumLine line);
    List<IAlbumLine> load();
}
