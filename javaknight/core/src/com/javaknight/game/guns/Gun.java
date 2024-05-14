package com.javaknight.game.guns;

import Sprites.Balas.BalaRed;
import Sprites.Balas.Bullet;
import Sprites.Balas.BulletManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;

public class Gun extends Sprite {

    public BulletType bulletType;

    private float timeBetweenShots = 0.3f; // Set the time between shots in seconds
    public float timeSinceLastShot = 0;

    private float damage;

    public Gun(Texture t, BulletType bulletType, float damage) {
        super(t);
        this.bulletType = bulletType;
        this.damage = damage;
        //setBounds(0, 0, 30, 10);
        //setScale(0.7f);
    }
    public BalaRed shootRed(BulletManager manager, float toX, float toY, boolean enemy) {
        //System.out.println(toX+ " " + toY);
        float bulletX = getX() + getWidth() / 2; // arma.getX() +  (arma.isFlipX()  ? 0 : arma.getWidth());
        float bulletY = getY() + getHeight() / 2;
        // Calcular la dirección hacia el cursor
        float directionX = toX - bulletX;
        float directionY = toY - bulletY;
        float length = (float) Math.sqrt(directionX * directionX + directionY * directionY);
        // Normalizar la dirección y establece  r la velocidad de la bala
        BalaRed bullet = null;
        if (length != 0) {
            directionX /= length;
            directionY /= length;
            //manager.spawnBulletDirection(bulletX, bulletY, directionX, directionY);
            bullet = new BalaRed(bulletX,bulletY,directionX,directionY,getDamage(),enemy);
            manager.balasRed.add(bullet);
            //manager.spawnBulletDirection(bulletType, bulletX, bulletY, directionX, directionY);
        }
        return bullet;
    }

    public void shoot(BulletManager manager, float toX, float toY, boolean enemy) {
        //System.out.println(toX+ " " + toY);
        float bulletX = getX() + getWidth() / 2; // arma.getX() +  (arma.isFlipX()  ? 0 : arma.getWidth());
        float bulletY = getY() + getHeight() / 2;
        // Calcular la dirección hacia el cursor
        float directionX = toX - bulletX;
        float directionY = toY - bulletY;
        float length = (float) Math.sqrt(directionX * directionX + directionY * directionY);
        // Normalizar la dirección y establece  r la velocidad de la bala
        if (length != 0) {
            directionX /= length;
            directionY /= length;
            //manager.spawnBulletDirection(bulletX, bulletY, directionX, directionY);
            Bullet bullet = new Bullet(bulletX,bulletY,directionX,directionY, bulletType.getAnimation(),getDamage(),enemy);
            manager.addBullet(bullet);
            //manager.spawnBulletDirection(bulletType, bulletX, bulletY, directionX, directionY);
        }
    }

    public float getTimeBetweenShots() {
        return timeBetweenShots;
    }

    public float getDamage() {
        return damage;
    }
}
