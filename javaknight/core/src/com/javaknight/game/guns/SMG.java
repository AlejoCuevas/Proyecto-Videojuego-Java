package com.javaknight.game.guns;

import com.badlogic.gdx.graphics.Texture;

public class SMG extends Gun{

    private float timeBetweenShots = 0.15f; // Set the time between shots in seconds
    public float timeSinceLastShot = 0;

    public SMG(){
        super(new Texture("guns/SMG.png"), BulletType.SIMPLE, 0.3f);
        setScale(0.7f);
    }

    @Override
    public float getTimeBetweenShots() {
        return timeBetweenShots;
    }
}

