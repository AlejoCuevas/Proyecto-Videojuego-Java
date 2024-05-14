package com.javaknight.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BulletAnimationManager {
    private Texture bulletTexture;
    private TextureRegion[][] bulletFrames;
    private Animation<TextureRegion>[] bulletAnimations;

    public BulletAnimationManager(String texturePath, int frameWidth, int frameHeight, int numAnimations, int framesPerAnimation) {
        bulletTexture = new Texture(texturePath);
        bulletFrames = TextureRegion.split(bulletTexture, frameWidth, frameHeight);

        // Initialize animations array
        bulletAnimations = new Animation[numAnimations];

        // Create animations
        for (int i = 0; i < numAnimations; i++) {
            TextureRegion[] animationFrames = new TextureRegion[framesPerAnimation];
            for (int j = 0; j < framesPerAnimation; j++) {
                animationFrames[j] = bulletFrames[i][j];
            }
            bulletAnimations[i] = new Animation<>(0.1f, animationFrames);
        }
    }

    public Animation<TextureRegion> getAnimation(int index) {
        if (index >= 0 && index < bulletAnimations.length) {
            return bulletAnimations[index];
        }
        return null;
    }

    public void dispose() {
        bulletTexture.dispose();
    }
}
