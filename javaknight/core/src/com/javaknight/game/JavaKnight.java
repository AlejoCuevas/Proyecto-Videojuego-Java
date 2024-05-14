package com.javaknight.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.javaknight.game.pantallas.*;

public class JavaKnight extends Game {
	
	public static final int ANCHO = 1200;
	public static final int ALTO = 624;

	public static final int V_WIDTH = 600;//2000;//400;
	public static final int V_HEIGHT = 300;//1000;//208;
	public static final float PPM = 100;


	
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new PantallaMenu2(this));
	}

	@Override
	public void render () {
		super.render();
		
	}
	
}
