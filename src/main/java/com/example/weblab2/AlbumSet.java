package com.example.weblab2;

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
    public boolean userHasRating(IAlbumLine line, String username) {
        return saveLoader.userHasRating(line, username);
    }

    @Override
    public boolean canUserAddRating(int lineId, String username) {
        return saveLoader.userExists(username) &&
                !saveLoader.userHasRating(lines.get(lineId), username);
    }

    @Override
    public boolean addRating(int lineId, int rating, String username) {
        var line = lines.get(lineId);
        line.addRating(rating);
        saveLoader.updateRating(line);
        saveLoader.addAlbumRatedNote(username, line);
        return true;
    }

    @Override
    public List<IAlbumLine> getLines() {
        return lines;
    }
}
