package com.example.weblab2;

public class AlbumLine implements IAlbumLine {
    protected String performer, album;
    protected int ratingsCount = 0, id;
    protected float rating;

    public AlbumLine(String performer, String album, float rating, int ratingsCount, int id)
    {
        this.performer = performer;
        this.album = album;
        this.rating = rating;
        this.ratingsCount = ratingsCount;
        this.id = id;
    }

    public String getHTMLTableLine()
    {
        return  "<tr><td>"+performer+"</td><td>"+album+"</td><td>" + rating +"</td></tr>";
    }

    /*
    * Add values
    */
    @Override
    public void addRating(int newRating) {
        rating = (rating*ratingsCount+newRating)/(++ratingsCount);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getRatingsCount() {
        return ratingsCount;
    }

    @Override
    public float getRating() {
        return rating;
    }
}
