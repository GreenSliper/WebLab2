package com.example.weblab2;

import java.util.Arrays;
import java.util.List;

public class DummyAlbumSetSaveLoader implements IAlbumSetSaveLoader{
    @Override
    public void save(List<IAlbumLine> set) { }

    @Override
    public boolean userHasRating(IAlbumLine line, String username) {
        return false;
    }

    @Override
    public void updateRating(IAlbumLine line) {

    }

    @Override
    public void addAlbumRatedNote(String user, IAlbumLine line) {

    }

    @Override
    public List<IAlbumLine> load() {
        return Arrays.asList(
                new AlbumLine("Scorpions", "Humanity", 9, 1, 0),
                new AlbumLine("Metallica", "Master of Puppets", 8.7f, 1, 1)
        );
    }
}
