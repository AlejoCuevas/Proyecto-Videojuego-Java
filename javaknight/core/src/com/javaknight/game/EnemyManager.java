package com.javaknight.game;

import Sprites.Balas.BulletManager;
import Sprites.Enemigos.Enemy;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class EnemyManager {
    private Array<Enemy> enemies;

    public EnemyManager() {
        enemies = new Array<>();
    }

    public void spawnEnemy(float x, float y) {
        Enemy enemy = new Enemy(new Texture("knight2.png"));
        enemy.setPosition(x,y);
        enemies.add(enemy);
    }

    public void update(float deltaTime, BulletManager bulletManager, Vector2 playerPos) {
        for (Enemy enemy : enemies) {
            if(enemy.life <= 0) enemies.removeValue(enemy,true);
            enemy.update(deltaTime,bulletManager, playerPos);
        }
    }

    public void draw(Batch batch) {
        for (Enemy enemy : enemies) {
            enemy.draw(batch);
        }
    }

    public void dispose() {
        for (Enemy enemy : enemies) {
            enemy.dispose();
        }
        enemies.clear();
    }

    public Array<Enemy> getEnemies() {
        return enemies;
    }
}

