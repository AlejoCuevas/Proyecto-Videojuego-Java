package com.javaknight.game.levels;

import Sprites.Balas.Bullet;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.javaknight.game.EnemyManager;
import com.javaknight.game.MapLoader;
import com.javaknight.game.entity.EnemyEntity;
import com.javaknight.game.entity.EntityManager;
import com.javaknight.game.entity.boss.Boss;
import com.javaknight.game.guns.M4;
import com.javaknight.game.pantallas.PantallaJuego;
import com.javaknight.game.pantallas.ShopScreen;

public class SafeZone implements Nivel{
    private final PantallaJuego gameScreen;

    private final MapLoader mapa;

    ShopScreen shop;


    public SafeZone(PantallaJuego gameScreen){
        this.gameScreen = gameScreen;
        this.mapa = new MapLoader("safezone.tmx");
        this.gameScreen.renderizado = new OrthogonalTiledMapRenderer(mapa.getMap());

        this.gameScreen.getEntityManager().getEntities().clear();
        this.gameScreen.getBulletManager().getActiveBullets().clear();

        // Set positions
        this.gameScreen.getP1().setPosition(mapa.getPlayerSpawnpoints().get(0).x,mapa.getPlayerSpawnpoints().get(0).y);
        this.gameScreen.getEntityManager().getEntities().add(this.gameScreen.getP1());

        this.shop = new ShopScreen(gameScreen);

    }

    public void update(){
        //mundo.step(1/60f, 6, 2);
        float dt = Gdx.graphics.getDeltaTime();
        gameScreen.getEntityManager().update(dt);
//		enemyEntity.update(dt);
        gameScreen.getGamecam().position.x = this.gameScreen.getP1().getX();
        gameScreen.getGamecam().position.y = this.gameScreen.getP1().getY();
        //gamecam.position.x = jugador.b2body.getPosition().x;
        gameScreen.getGamecam().update();
        // Update bullets
        gameScreen.getBulletManager().update(dt);

        gameScreen.getRenderizado().setView(gameScreen.getGamecam());

        RectangleMapObject startTrigger = (RectangleMapObject) mapa.getMap().getLayers().get("spawnpoints").getObjects().get("start");
        RectangleMapObject shopTrigger = (RectangleMapObject) mapa.getMap().getLayers().get("spawnpoints").getObjects().get("shop");

        if(startTrigger != null && gameScreen.getP1().getBoundingRectangle().overlaps(startTrigger.getRectangle())){
            gameScreen.nivel = new Nivel1(gameScreen);
        }
        if(shopTrigger != null && gameScreen.getP1().getBoundingRectangle().overlaps(shopTrigger.getRectangle()) && Gdx.input.isKeyPressed(Input.Keys.E)){
            shop.open = true;
        }
    }

    public void render(){

        update();

        gameScreen.getRenderizado().getBatch().begin();
        for (MapLayer layer : mapa.getMap().getLayers()) {
            if (!layer.getName().equals("collisionLayer") && layer.getObjects().getCount() < 1) {
                gameScreen.getRenderizado().renderTileLayer((TiledMapTileLayer) layer);
            }
        }
        gameScreen.getRenderizado().getBatch().end();
        //b2dr.render(mundo, gamecam.combined);
        gameScreen.getGame().batch.setProjectionMatrix(gameScreen.getGamecam().combined);
        gameScreen.getGame().batch.begin();

        for(Bullet b : gameScreen.getBulletManager().getActiveBullets()){
            b.draw(gameScreen.getGame().batch);
        }
        gameScreen.getEntityManager().draw(gameScreen.getGame().batch);


        if(shop.open){
            shop.render(Gdx.graphics.getDeltaTime());
            return;
        }

    }

    @Override
    public MapLoader getMap() {
        return mapa;
    }
}
