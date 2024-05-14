package com.javaknight.game;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class MapLoader {

    public static final int TILE_SIZE = 16;

    private static TmxMapLoader loader = new TmxMapLoader();

    private TiledMap map;

    private TiledMapTileLayer collisionLayer;

    private ArrayList<Vector2> playerSpawnpoints = new ArrayList<>();
    private ArrayList<Vector2> enemySpawnpoints = new ArrayList<>();
    private Vector2 bossSpawnpoint;
    private Rectangle bossRoom;


    public MapLoader(String filename){
        map = loader.load(filename);
        collisionLayer = (TiledMapTileLayer) map.getLayers().get("collisionLayer");
        // Load spawnpoints
        MapLayer spawnpointsLayer = map.getLayers().get("spawnpoints");
        if(spawnpointsLayer != null) {
            for(MapObject mapObject : spawnpointsLayer.getObjects()){
                RectangleMapObject object = (RectangleMapObject) mapObject;
                // Get the position of the player spawn point
                float x = object.getRectangle().x;
                float y = object.getRectangle().y;
                if(object.getProperties().containsKey("player")){
                    playerSpawnpoints.add(new Vector2(x,y));
                } else if( object.getProperties().containsKey("enemy")) {
                    enemySpawnpoints.add(new Vector2(x,y));
                } else if( object.getProperties().containsKey("boss")) {
                    bossSpawnpoint = new Vector2(x,y);
                } else if( object.getProperties().containsKey("bossRoom")) {
                    bossRoom = object.getRectangle();
                }
            }
        }

    }

    public TiledMap getMap() {
        return map;
    }

    public ArrayList<Vector2> getEnemySpawnpoints() {
        return enemySpawnpoints;
    }

    public ArrayList<Vector2> getPlayerSpawnpoints() {
        return playerSpawnpoints;
    }

    public Vector2 getBossSpawnpoint() {
        return bossSpawnpoint;
    }

    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }

    public Rectangle getBossRoom() {
        return bossRoom;
    }
}
