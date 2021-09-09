package com.example.weblab2;

public class AlbumLine implements IAlbumLine {
    protected String performer, album;
    protected float rating;

    public AlbumLine(String performer, String album, float rating)
    {
        this.performer = performer;
        this.album = album;
        this.rating = rating;
    }

    public String getHTMLTableLine()
    {
        return  "<tr><td>"+performer+"</td><td>"+album+"</td><td>"+String.valueOf(rating)+"</td></tr>";
    }
}
