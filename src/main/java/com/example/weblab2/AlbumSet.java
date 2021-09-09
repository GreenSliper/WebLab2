package com.example.weblab2;

import java.util.ArrayList;
import java.util.List;

public class AlbumSet implements IAlbumSet {

    private List<IAlbumLine> lines;
    private final IAlbumSetSaveLoader saveLoader;

    public AlbumSet(IAlbumSetSaveLoader saveLoader)
    {
        this.saveLoader = saveLoader;
    }

    @Override
    public void load() {
        lines = saveLoader.load();
    }

    @Override
    public void save() {
        saveLoader.save(lines);
    }

    @Override
    public boolean tryAddRating(int lineId, int rating) {
        //TODO
        return true;
    }

    @Override
    public String getTable() {
        StringBuilder result = new StringBuilder();
        for (var line : lines)
            result.append(line.getHTMLTableLine());
        return result.toString();
    }
}
