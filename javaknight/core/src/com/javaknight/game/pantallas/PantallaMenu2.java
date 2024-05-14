package com.javaknight.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.javaknight.game.JavaKnight;

public class PantallaMenu2 implements Screen {

    private final JavaKnight game;
    private Stage stage;

    public PantallaMenu2(final JavaKnight game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Create a default font
        BitmapFont font = new BitmapFont();

        // Create buttons with a default TextButtonStyle
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;

        TextButton singlePlayerButton = new TextButton("Jugar", buttonStyle);
        TextButton multiplayerButton = new TextButton("Server", buttonStyle);
        TextButton exitButton = new TextButton("Exit", buttonStyle);

        // Set button positions
        singlePlayerButton.setPosition(100, 200);
        multiplayerButton.setPosition(100, 150);
        exitButton.setPosition(100, 100);

        // Add buttons to stage
        stage.addActor(singlePlayerButton);
        stage.addActor(multiplayerButton);
        stage.addActor(exitButton);

        // Add button listeners
        singlePlayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Handle SinglePlayer button click
                game.setScreen(new PantallaOnline(game, 0));
            }
        });

        multiplayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Handle Multiplayer button click
                game.setScreen(new PantallaServidor(game, 0));
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }


    @Override
    public void show() {
        // Leave empty or add initialization code
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the stage
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // Leave empty or add pause code
    }

    @Override
    public void resume() {
        // Leave empty or add resume code
    }

    @Override
    public void hide() {
        // Leave empty or add hide code
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
