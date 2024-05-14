package com.javaknight.game.levels.online;

import Sprites.Balas.Bullet;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.javaknight.game.MapLoader;
import com.javaknight.game.entity.Character;
import com.javaknight.game.entity.EnemyEntity;
import com.javaknight.game.entity.boss.Boss;
import com.javaknight.game.levels.Nivel;
import com.javaknight.game.levels.Nivel3;
import com.javaknight.game.pantallas.GameOverScreen;
import com.javaknight.game.pantallas.PantallaServidor;

public class Nivel2Online implements Nivel {

    private final PantallaServidor gameScreen;
    private final MapLoader mapa;
    private Boss boss = new Boss(new Texture("knight2.png"));
    
    public Nivel2Online(PantallaServidor gameScreen) {
        this.gameScreen = gameScreen;
        this.mapa = new MapLoader("nivel1_new.tmx");
 
        
        this.gameScreen.renderizado = new OrthogonalTiledMapRenderer(mapa.getMap());

        this.gameScreen.getEntityManager().getEntities().clear();
        this.gameScreen.getBulletManager().getActiveBullets().clear();



        // Set positions
        this.gameScreen.getPlayer1().setPosition(mapa.getPlayerSpawnpoints().get(0).x,mapa.getPlayerSpawnpoints().get(0).y);
        this.gameScreen.getPlayer2().setPosition(mapa.getPlayerSpawnpoints().get(1).x,mapa.getPlayerSpawnpoints().get(1).y);

        this.gameScreen.getEntityManager().getEntities().add(this.gameScreen.getPlayer1());
        this.gameScreen.getEntityManager().getEntities().add(this.gameScreen.getPlayer2());

        boss.setActive(true);
        boss.setPosition(mapa.getBossSpawnpoint().x,mapa.getBossSpawnpoint().y);

        gameScreen.getEntityManager().getEntities().add(boss);

        for(Vector2 v : mapa.getEnemySpawnpoints()) {
            EnemyEntity en = new EnemyEntity(new Texture("knight2.png"));
            en.setPosition(v.x, v.y);
            this.gameScreen.getEntityManager().getEntities().add(en);
            gameScreen.server.enviarMensajeATodos("newenemy#"+v.x+"#"+v.y);
        }
        gameScreen.server.enviarMensajeATodos("newboss#"+boss.getX()+"#"+boss.getY());
    }


    @Override
    public void update() {
    	 if(boss.getLife() <= 0){
             this.gameScreen.server.enviarMensajeATodos("terminar#");
             return;
         }
    	 
    	 
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
