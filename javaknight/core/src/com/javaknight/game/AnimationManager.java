package com.javaknight.game;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AnimationManager {

    private List<AnimationSpawner> animationSpawners;

    public AnimationManager() {
        this.animationSpawners = new ArrayList<>();
    }

    public void spawnAnimation(float x, float y, Animation<TextureRegion> animation, int loopCount) {
        AnimationSpawner animationSpawner = new AnimationSpawner(x, y, animation, loopCount);
        animationSpawners.add(animationSpawner);
    }

    public void update(float deltaTime) {
        Iterator<AnimationSpawner> iterator = animationSpawners.iterator();
        while (iterator.hasNext()) {
            AnimationSpawner animationSpawner = iterator.next();
            animationSpawner.update(deltaTime);

            if (animationSpawner.getCurrentLoop() >= animationSpawner.getLoopCount()) {
                // If desired loop count is reached, remove the animation
                iterator.remove();
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (AnimationSpawner animationSpawner : animationSpawners) {
            animationSpawner.render(batch);
        }
    }
}

