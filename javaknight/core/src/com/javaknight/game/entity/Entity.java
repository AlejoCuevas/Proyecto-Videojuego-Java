package com.javaknight.game.entity;

import Sprites.Balas.Bullet;
import Sprites.Balas.BulletManager;
import Sprites.Balas.Damage;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.javaknight.game.pantallas.PantallaJuego;

public interface Entity {
    float money = 0;

    float getLife();

    void hit(Damage b,boolean enemyShoot);

    void update(float dt);

    boolean isEnemy();

    float getX();
    float getY();

    int getRegionWidth();
    int getRegionHeight();

    void draw(Batch b);


    void update(float dt, PantallaJuego gameScreen);

    Vector2 getPosition();

    Rectangle getBoundingRectangle();

    void setPosition(float x, float y);
}
