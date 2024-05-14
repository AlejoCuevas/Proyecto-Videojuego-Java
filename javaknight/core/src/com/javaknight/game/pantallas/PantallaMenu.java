package com.javaknight.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.javaknight.game.JavaKnight;

public class PantallaMenu implements Screen{
	
	private static final int BOTON_SALIR_ALTO = 150;
	private static final int BOTON_SALIR_ANCHO = 300;
	private static final int BOTON_JUGAR_ALTO = 150;
	private static final int BOTON_JUGAR_ANCHO = 300;
	private static final int BOTON_SALIR_Y = 50;
	private static final int BOTON_JUGAR_Y = 300;
	
	JavaKnight game;
	
	Texture fondo;
	Texture botonJugarActivo;
	Texture botonJugarInactivo;
	Texture botonSalirActivo;
	Texture botonSalirInactivo;
	
	public PantallaMenu (JavaKnight game) {
		this.game = game;
		fondo = new Texture("preview4.png");
		botonJugarActivo = new Texture("buttonStock1h.png");
		botonJugarInactivo = new Texture("buttonStock2.png");
		botonSalirActivo = new Texture("buttonStock3.png");
		botonSalirInactivo = new Texture("buttonStock1.png");
	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1);
		game.batch.begin();
		
		game.batch.draw(fondo, 0, 0, JavaKnight.ANCHO, JavaKnight.ALTO);
		
		int x = JavaKnight.ANCHO / 2 - BOTON_SALIR_ANCHO / 2;
		if(Gdx.input.getX() < x + BOTON_SALIR_ANCHO && Gdx.input.getX() > x && JavaKnight.ALTO - Gdx.input.getY() < BOTON_SALIR_Y + BOTON_SALIR_ALTO && JavaKnight.ALTO - Gdx.input.getY() > BOTON_SALIR_Y) {
			game.batch.draw(botonSalirActivo, JavaKnight.ANCHO / 2 - BOTON_SALIR_ANCHO / 2, BOTON_SALIR_Y, BOTON_SALIR_ANCHO, BOTON_SALIR_ALTO);
			if(Gdx.input.isTouched()) {
				Gdx.app.exit();
			}
		} else {
			game.batch.draw(botonSalirInactivo, JavaKnight.ANCHO / 2 - BOTON_SALIR_ANCHO / 2, BOTON_SALIR_Y, BOTON_SALIR_ANCHO, BOTON_SALIR_ALTO);
		}
		
		x = JavaKnight.ANCHO / 2 - BOTON_JUGAR_ANCHO / 2;
		if(Gdx.input.getX() < x + BOTON_JUGAR_ANCHO && Gdx.input.getX() > x && JavaKnight.ALTO - Gdx.input.getY() < BOTON_JUGAR_Y + BOTON_JUGAR_ALTO && JavaKnight.ALTO - Gdx.input.getY() > BOTON_JUGAR_Y) {
			game.batch.draw(botonJugarActivo, JavaKnight.ANCHO / 2 - BOTON_JUGAR_ANCHO / 2, BOTON_JUGAR_Y, BOTON_JUGAR_ANCHO, BOTON_JUGAR_ALTO);
			if(Gdx.input.isTouched()) {
				this.dispose();
				game.setScreen(new CharacterSelectionScreen(game));
			}
		} else {
			game.batch.draw(botonJugarInactivo, JavaKnight.ANCHO / 2 - BOTON_JUGAR_ANCHO / 2, BOTON_JUGAR_Y, BOTON_JUGAR_ANCHO, BOTON_JUGAR_ALTO);
		}
		game.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}

}
