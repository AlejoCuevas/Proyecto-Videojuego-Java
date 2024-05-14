package com.javaknight.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.javaknight.game.JavaKnight;
import com.javaknight.game.pantallas.PantallaJuego;
import com.javaknight.game.pantallas.PantallaMenu;

public class GameOverScreen implements Screen {

    private FitViewport gamePort;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private BitmapFont font;

    JavaKnight game;

    public GameOverScreen(JavaKnight game) {
        this.camera = new OrthographicCamera();
        this.gamePort = new FitViewport(JavaKnight.V_WIDTH, JavaKnight.V_HEIGHT, camera);
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);
        this.game = game;
    }

    @Override
    public void show() {
        // Setup screen when it's set as the current screen
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Set the camera to the batch
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        // Draw "Game Over" text in the center of the screen
        // Draw "Game Over" text in the center of the screen
        font.draw(batch, "Game Over", Gdx.graphics.getWidth() / 2f - 50, Gdx.graphics.getHeight() / 2f - 20);

        // Draw "Press Enter to Continue" text below
        font.draw(batch, "Press Enter to Continue", Gdx.graphics.getWidth() / 2f - 110, Gdx.graphics.getHeight() / 2f - 50);

        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            this.game.setScreen(new PantallaMenu2(this.game));
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Update the camera when the screen is resized
        camera.setToOrtho(false, width, height);
        gamePort.update(width, height);
        gamePort.apply();
    }

    @Override
    public void pause() {
        // Handle when the game is paused
    }

    @Override
    public void resume() {
        // Handle when the game is resumed
    }

    @Override
    public void hide() {
        // Called when the screen is no longer the current screen
    }

    @Override
    public void dispose() {
        // Dispose of any resources when the screen is no longer needed
        batch.dispose();
        font.dispose();
    }
}
