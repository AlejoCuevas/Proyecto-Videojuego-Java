package com.javaknight.game.levels;

import Sprites.Balas.Bullet;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.javaknight.game.EnemyManager;
import com.javaknight.game.MapLoader;
import com.javaknight.game.entity.EnemyEntity;
import com.javaknight.game.entity.EntityManager;
import com.javaknight.game.entity.boss.Boss;
import com.javaknight.game.guns.SMG;
import com.javaknight.game.pantallas.PantallaJuego;

public class Nivel3 implements Nivel{
    private final PantallaJuego gameScreen;

    private final MapLoader mapa;

    private Boss boss = new Boss(new Texture("knight2.png"));

    public Nivel3(PantallaJuego gameScreen){
        this.gameScreen = gameScreen;
        this.mapa = new MapLoader("nivel3_new.tmx");
        this.gameScreen.renderizado = new OrthogonalTiledMapRenderer(mapa.getMap());


        this.gameScreen.getEntityManager().getEntities().clear();
        this.gameScreen.getBulletManager().getActiveBullets().clear();

        // Set positions
        this.gameScreen.getP1().setPosition(mapa.getPlayerSpawnpoints().get(0).x,mapa.getPlayerSpawnpoints().get(0).y);
        this.gameScreen.getEntityManager().getEntities().add(this.gameScreen.getP1());

        boss.setPosition(mapa.getBossSpawnpoint().x,mapa.getBossSpawnpoint().y);
        gameScreen.getEntityManager().getEntities().add(boss);

        for(Vector2 v : mapa.getEnemySpawnpoints()) {
            EnemyEntity en = new EnemyEntity(new Texture("knight2.png"), 20, new SMG());
            en.setPosition(v.x, v.y);
            this.gameScreen.getEntityManager().getEntities().add(en);
        }

    }

    public void update(){
        if(boss.getLife() <= 0){
            this.gameScreen.nivel = new Nivel3(gameScreen);
            return;
        }
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
        System.out.println(boss.getLife());
        boss.setActive(this.gameScreen.getP1().getBoundingRectangle().overlaps(mapa.getBossRoom()));
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
    }

    @Override
    public MapLoader getMap() {
        return mapa;
    }
}
