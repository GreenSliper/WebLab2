package com.example.weblab2;

import java.util.Arrays;
import java.util.List;

public class DummyAlbumSetSaveLoader implements IAlbumSetSaveLoader{
    @Override
    public void save(List<IAlbumLine> set) { }

    @Override
    public List<IAlbumLine> load() {
        return Arrays.asList(
                new AlbumLine("Scorpions", "Humanity", 9),
                new AlbumLine("Metallica", "Master of Puppets", 8.7f)
        );
    }
}
