package com.javaknight.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.io.Serializable;

public enum AnimationType {
    DASH("guns/17.png", 6, 2, 0.1f, Animation.PlayMode.NORMAL),
    SKILL2("guns/14.png", 6, 2, 0.1f, Animation.PlayMode.NORMAL);
    //WALK("walk_animation.png", 6, 1, 0.08f, Animation.PlayMode.LOOP),
    //JUMP("jump_animation.png", 8, 2, 0.1f, Animation.PlayMode.NORMAL);

    private final String texturePath;
    private final int frameCols;
    private final int frameRows;
    private final float frameDuration;
    private final Animation.PlayMode playMode;

    AnimationType(String texturePath, int frameCols, int frameRows, float frameDuration, Animation.PlayMode playMode) {
        this.texturePath = texturePath;
        this.frameCols = frameCols;
        this.frameRows = frameRows;
        this.frameDuration = frameDuration;
        this.playMode = playMode;
    }

    public Animation<TextureRegion> loadAnimation() {
        Texture texture = new Texture(texturePath);
        TextureRegion[][] textureRegions = TextureRegion.split(texture, texture.getWidth() / frameCols, texture.getHeight() / frameRows);

        Array<TextureRegion> frames = new Array<>(frameCols * frameRows);
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
                frames.add(textureRegions[i][j]);
            }
        }


        return new Animation<TextureRegion>(frameDuration, frames, playMode);
    }
}
