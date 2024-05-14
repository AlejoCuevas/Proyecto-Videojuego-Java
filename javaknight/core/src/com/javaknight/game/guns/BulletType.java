package com.javaknight.game.guns;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum BulletType {
    SIMPLE(0, "guns/bullet1.png", 6, 6, 4);
    //BULLET_TYPE_2(1, "bullet_type_2.png", 10, 10, 4),
    //BULLET_TYPE_3(2, "bullet_type_3.png", 10, 10, 4);

    private final int animationIndex;
    private final Animation<TextureRegion> animation;

    BulletType(int animationIndex, String texturePath, int frameWidth, int frameHeight, int framesPerAnimation) {
        this.animationIndex = animationIndex;

        TextureRegion[][] bulletFrames = TextureRegion.split(new Texture(texturePath), frameWidth, frameHeight);
        TextureRegion[] animationFrames = new TextureRegion[framesPerAnimation];

        for (int j = 0; j < framesPerAnimation; j++) {
            animationFrames[j] = bulletFrames[animationIndex][j];
        }

        this.animation = new Animation<>(0.1f, animationFrames);
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }
}