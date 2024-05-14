package com.javaknight.game.guns;

import com.badlogic.gdx.graphics.Texture;

public class M4 extends Gun{

    private float timeBetweenShots = 0.25f; // Set the time between shots in seconds
    public float timeSinceLastShot = 0;

    public M4(){
        super(new Texture("guns/M4.png"), BulletType.SIMPLE, 1);
        setScale(0.9f);
    }


    public M4(float damage){
        super(new Texture("guns/M4.png"), BulletType.SIMPLE, damage);
        setScale(0.9f);
    }

    @Override
    public float getTimeBetweenShots() {
        return timeBetweenShots;
    }
}
