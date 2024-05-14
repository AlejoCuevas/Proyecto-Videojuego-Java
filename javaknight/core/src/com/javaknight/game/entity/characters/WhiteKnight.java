package com.javaknight.game.entity.characters;

import Sprites.Balas.Damage;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.javaknight.game.AnimationType;
import com.javaknight.game.entity.Entity;
import com.javaknight.game.entity.PlayableCharacter;
import com.javaknight.game.pantallas.PantallaJuego;

public class WhiteKnight extends PlayableCharacter {
    public WhiteKnight(PantallaJuego gameScreen) {
        super(new Texture("knight4.png"), gameScreen);
    }

    @Override
    public void useSkill(float x, float y) {
        Rectangle cursorArea = new Rectangle(x - 10, y - 10, x + 10, y + 10);
        for (Entity e : gameScreen.getEntityManager().getEntities()) {
            if (!e.isEnemy()) continue;
            if (e.getBoundingRectangle().overlaps(cursorArea)) {
                e.hit(new Damage() {
                    @Override
                    public float getDamage() {
                        return 5;
                    }
                }, false);
            }
        }
        gameScreen.animationManager.spawnAnimation(x, y, AnimationType.SKILL2.loadAnimation(), 1);
    }

}
