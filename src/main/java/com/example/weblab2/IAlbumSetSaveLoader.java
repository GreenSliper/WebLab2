package com.example.weblab2;

import java.util.List;

public interface IAlbumSetSaveLoader {
    void save(List<IAlbumLine> set);
    List<IAlbumLine> load();
}
