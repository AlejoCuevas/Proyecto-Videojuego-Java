package com.javaknight.game.entity;

import Sprites.Balas.BalaRed;
import Sprites.Balas.Bullet;
import Sprites.Balas.BulletManager;
import Sprites.Enemigos.Enemy;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.javaknight.game.entity.boss.Boss;
import com.javaknight.game.entity.boss.BossRed;
import com.javaknight.game.guns.BulletType;
import com.javaknight.game.pantallas.PantallaJuego;
import com.javaknight.game.pantallas.PantallaOnline;
import com.javaknight.game.pantallas.PantallaServidor;

public class EntityManager {
    private Array<Entity> entities = new Array<>();
    public Array<EntidadRed> entidadesRed = new Array<>();

    PantallaJuego gameScreen;

    
  
    
    public EntityManager(PantallaJuego gameScreen){
        this.gameScreen = gameScreen;
    }

    public void update(float deltaTime){
    	// Crear las balas que vienen del online
        for(int i = 0; i < entidadesRed.size; i++){
            EntidadRed entidadRed= entidadesRed.get(i);
             EnemyEntity en;
            if(entidadRed instanceof BossRed) {
              en = new Boss(new Texture("knight2.png"));
            } else {
              en = new EnemyEntity(new Texture("knight2.png"));
            }
         
            en.setPosition(entidadRed.x, entidadRed.y);
            entities.add(en);
            entidadesRed.removeIndex(i);
        }

        for(int i = 0; i < entities.size; i++){
            Entity entity = entities.get(i);
            if(entity == null) continue;

            else if(entity.getLife() <= 0){
                if(gameScreen instanceof PantallaServidor){
                    if(entity instanceof EnemyEntity){
                        ((PantallaServidor) gameScreen).server.enviarMensajeATodos("enemy_die#"+i);
                    }
                }
                entities.removeValue(entity,true);
                continue;
            }
            if(!(gameScreen instanceof PantallaOnline && entity instanceof EnemyEntity)) entity.update(deltaTime,gameScreen);
            if(gameScreen instanceof PantallaServidor){
                if(entity instanceof EnemyEntity){
                    ((PantallaServidor) gameScreen).server.enviarMensajeATodos("enemy#"+i+"#"+entity.getX()+"#"+ entity.getY());
                }
            }

        }
    }

    public void draw(Batch batch) {
        for (Entity entity : entities) {
            entity.draw(batch);
        }
    }


    public Array<Entity> getEntities() {
        return entities;
    }

    public Array<Entity> getNonEnemyEntities() {
        Array<Entity> nonEnemyEntities = new Array<>();
        for(Entity e : entities){
            if(!e.isEnemy()) nonEnemyEntities.add(e);
        }
        return nonEnemyEntities;
    }
}
