package com.javaknight.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationSpawner {

    private float x;
    private float y;
    private Animation<TextureRegion> animation;
    private float stateTime;
    private int loopCount;
    private int currentLoop;

    public AnimationSpawner(float x, float y, Animation<TextureRegion> animation, int loopCount) {
        this.x = x;
        this.y = y;
        this.animation = animation;
        this.stateTime = 0f;
        this.loopCount = loopCount;
        this.currentLoop = 0;
    }

    public void update(float deltaTime) {
        if(currentLoop >= loopCount) return;
        stateTime += deltaTime;
        if (animation.isAnimationFinished(stateTime)) {
            currentLoop++;
            if (currentLoop >= loopCount) {
                // If the desired loop count is reached, you may choose to stop or reset the animation.
                // For now, let's reset the animation to play it again.
                //stateTime = 0f;
                //currentLoop = 0;

            }
        }
    }

    public void render(SpriteBatch batch) {
        if(currentLoop >= loopCount) return;
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, x, y);


    }

    public int getCurrentLoop() {
        return this.currentLoop;
    }

    public int getLoopCount() {
        return this.loopCount;
    }
}