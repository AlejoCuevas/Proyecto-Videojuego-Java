package com.javaknight.game.levels;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.javaknight.game.MapLoader;

public interface Nivel {

    void update();
    void render();

    MapLoader getMap();
}
