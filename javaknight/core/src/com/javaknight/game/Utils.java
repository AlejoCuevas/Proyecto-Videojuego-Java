package com.javaknight.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public abstract class Utils {

    static FreeTypeFontGenerator generator;

    public static void splitFrames(Array finalArray, Texture spriteSheet, int FRAME_WIDTH, int FRAME_HEIGHT, int FRAME_COUNT, int FRAME_SPACING) {
        finalArray.clear();
        int startX = 0; // Adjust if there's any spacing or margin at the beginning of the sprite sheet
        for (int i = 0; i < FRAME_COUNT; i++) {
            finalArray.add(new TextureRegion(spriteSheet, startX + i * (FRAME_WIDTH + FRAME_SPACING), 0, FRAME_WIDTH, FRAME_HEIGHT));
        }
    }

    public static boolean detectCollision(TiledMapTileLayer collisionLayer, float x, float y){
        int TILE_SIZE = 16;
        return collisionLayer.getCell((int)x/TILE_SIZE,(int)y/TILE_SIZE) != null;
    }

    public static BitmapFont getMainFont(int size){
        if(generator == null) generator =  new FreeTypeFontGenerator(Gdx.files.internal("upheavtt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        return generator.generateFont(parameter); // font size 12 pixels
    }

    public static TextButton createTextButton(String text, int size, ClickListener clickListener) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = getMainFont(size);
        style.fontColor = Color.WHITE;

        TextButton button = new TextButton(text, style);
        button.addListener(clickListener);
        return button;
    }
}
