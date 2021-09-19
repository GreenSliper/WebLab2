package com.example.weblab2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBAlbumSetSaveLoader implements IAlbumSetSaveLoader{
    private final DBFacade db = DBFacade.getInstance();
    private final String dbPerformer = "performer", dbAlbum = "album",
            dbRating="rating", dbRatingsCount="ratingscount", dbId = "id";

    @Override
    public void save(List<IAlbumLine> set) {

    }

    @Override
    public boolean userExists(String username) {
        ResultSet res = null;
        try {
            res = db.ExecuteQuery("select * from users u where\n" +
                    " u.name = '" + username +"'");
            return  (res.next());
        } catch (DBFacade.DBNotConnectedException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean userHasRating(IAlbumLine line, String username) {
        try {
            var res = db.ExecuteQuery("select * from albumsratings ar where\n" +
                    "ar.userid = (select (id) from users u where u.name = '"+username+"')" +
                    "and ar.albumid = " + line.getId());
            return  (res.next());
        } catch (DBFacade.DBNotConnectedException | SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void updateRating(IAlbumLine line) {
        db.ExecuteUpdate("albums", new String[]{dbRating, dbRatingsCount},
                new Object[]{line.getRating(), line.getRatingsCount()},
                "WHERE albums.id =" + line.getId());
    }

    @Override
    public void addAlbumRatedNote(String username, IAlbumLine line) {
        db.executeInsertQuery("albumsratings",
                new String[]{"userid", "albumid"},
                new Object[]{getUserId(username), line.getId()});
    }

    int getUserId(String username)
    {
        ResultSet res = null;
        try {
            res = db.ExecuteQuery("select id from users u where u.name = '"+username+"'");
            if (res.next())
            {
                return res.getInt("id");
            }
        } catch (DBFacade.DBNotConnectedException | SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<IAlbumLine> load() {
        try {
            var lst = new ArrayList<IAlbumLine>();
            var res = db.ExecuteQuery("select * from albums a order by a.rating desc");
            while (res.next())
            {
                lst.add(new AlbumLine(res.getString(dbPerformer), res.getString(dbAlbum), res.getFloat(dbRating),
                        res.getInt(dbRatingsCount), res.getInt(dbId)));
            }
            return lst;
        } catch (DBFacade.DBNotConnectedException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
