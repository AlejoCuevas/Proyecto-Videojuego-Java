package com.javaknight.game.entity.boss;

import Sprites.Balas.Bullet;
import Sprites.Balas.Damage;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.javaknight.game.entity.EnemyEntity;
import com.javaknight.game.guns.Gun;
import com.javaknight.game.guns.M4;
import com.javaknight.game.pantallas.PantallaJuego;

public class Boss extends EnemyEntity {
    // ENTITY ALLOCATIONS
    public float life = 5;

    boolean active;


    // GUN ALLOCATIONS
    private Gun gun = new M4();
    public Boss(Texture texture) {
        super(texture);
        setBounds(getX(),getY(),getRegionWidth()*4,getRegionHeight()*4);
        this.gun.setScale(5f);
    }

    @Override
    public void hit(Damage d, boolean enemyShoot) {
        System.out.println("HIT "+active);
        if(active) {
            if(!enemyShoot) this.life -= d.getDamage();
        }
    }

    public void update(float deltaTime, PantallaJuego gameScreen) {
        if(active) super.update(deltaTime,gameScreen);
    }


    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public float getLife() {
        return life;
    }
}
