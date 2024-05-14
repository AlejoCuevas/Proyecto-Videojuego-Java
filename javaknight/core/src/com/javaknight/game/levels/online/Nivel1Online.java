package com.javaknight.game.levels.online;

import Sprites.Balas.Bullet;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.javaknight.game.MapLoader;
import com.javaknight.game.entity.Character;
import com.javaknight.game.levels.Nivel;
import com.javaknight.game.levels.Nivel1;
import com.javaknight.game.levels.Nivel2;
import com.javaknight.game.pantallas.PantallaServidor;

public class Nivel1Online implements Nivel {

    private final PantallaServidor gameScreen;
    private final MapLoader mapa;

    public Nivel1Online(PantallaServidor gameScreen) {
        this.gameScreen = gameScreen;
        this.mapa = new MapLoader("safezone.tmx");
        this.gameScreen.renderizado = new OrthogonalTiledMapRenderer(mapa.getMap());

        this.gameScreen.getEntityManager().getEntities().clear();
        this.gameScreen.getBulletManager().getActiveBullets().clear();



        // Set positions
        this.gameScreen.getPlayer1().setPosition(mapa.getPlayerSpawnpoints().get(0).x,mapa.getPlayerSpawnpoints().get(0).y);
        this.gameScreen.getPlayer2().setPosition(mapa.getPlayerSpawnpoints().get(1).x,mapa.getPlayerSpawnpoints().get(1).y);

        this.gameScreen.getEntityManager().getEntities().add(this.gameScreen.getPlayer1());
        this.gameScreen.getEntityManager().getEntities().add(this.gameScreen.getPlayer2());
    }


    @Override
    public void update() {
        //mundo.step(1/60f, 6, 2);
        float dt = Gdx.graphics.getDeltaTime();
        gameScreen.getEntityManager().update(dt);
//		enemyEntity.update(dt);
        gameScreen.getGamecam().position.x = this.gameScreen.getPlayer1().getX();
        gameScreen.getGamecam().position.y = this.gameScreen.getPlayer1().getY();
        //gamecam.position.x = jugador.b2body.getPosition().x;
        gameScreen.getGamecam().update();
        // Update bullets
        gameScreen.getBulletManager().update(dt);

        gameScreen.getRenderizado().setView(gameScreen.getGamecam());

        RectangleMapObject startTrigger = (RectangleMapObject) mapa.getMap().getLayers().get("spawnpoints").getObjects().get("start");
        RectangleMapObject shopTrigger = (RectangleMapObject) mapa.getMap().getLayers().get("spawnpoints").getObjects().get("shop");

        for(Character charac : gameScreen.players){
            if(startTrigger != null && charac.getBoundingRectangle().overlaps(startTrigger.getRectangle())){
                System.out.println("start");
                gameScreen.server.enviarMensajeATodos("nivel#1");
                gameScreen.nivel = new Nivel2Online(gameScreen);
                //gameScreen.nivel = new Nivel1(gameScreen);
            }
            /*if(shopTrigger != null && gameScreen.getPlayer1().getBoundingRectangle().overlaps(shopTrigger.getRectangle()) && Gdx.input.isKeyPressed(Input.Keys.E)){
                //shop.open = true;
            }*/
        }

    }

    @Override
    public void render() {
        update();
        gameScreen.getRenderizado().getBatch().begin();
        for (MapLayer layer : mapa.getMap().getLayers()) {
            if (!layer.getName().equals("collisionLayer") && layer.getObjects().getCount() < 1) {
                gameScreen.getRenderizado().renderTileLayer((TiledMapTileLayer) layer);
            }
        }
        gameScreen.getRenderizado().getBatch().end();
        gameScreen.getGame().batch.setProjectionMatrix(gameScreen.getGamecam().combined);
        gameScreen.getGame().batch.begin();

        for(Bullet b : gameScreen.getBulletManager().getActiveBullets()){
            b.draw(gameScreen.getGame().batch);
        }

        gameScreen.getEntityManager().draw(gameScreen.getGame().batch);
        gameScreen.getGame().batch.end();
    }

    @Override
    public MapLoader getMap() {
        return mapa;
    }
}
