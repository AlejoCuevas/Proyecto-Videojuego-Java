package com.javaknight.game.entity.characters;

import com.badlogic.gdx.graphics.Texture;
import com.javaknight.game.AnimationType;
import com.javaknight.game.entity.PlayableCharacter;
import com.javaknight.game.pantallas.PantallaJuego;

public class BlackKnight extends PlayableCharacter {
    public BlackKnight(PantallaJuego gameScreen) {
        super(new Texture("knight1.png"), gameScreen);
    }

    @Override
    public void useSkill(float x, float y){
        this.setPosition(x,y);
        this.gameScreen.animationManager.spawnAnimation(x-getRegionWidth()/2, y-getRegionHeight()/2, AnimationType.DASH.loadAnimation(), 1);
    }



}
