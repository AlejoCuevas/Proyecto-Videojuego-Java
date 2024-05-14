package com.javaknight.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.javaknight.game.JavaKnight;
import com.javaknight.game.Utils;

import static com.javaknight.game.Utils.getMainFont;

public class CharacterSelectionScreen implements Screen {

    private Stage stage;
    private Table table;
    private Image characterImage;
    JavaKnight game;

    public CharacterSelectionScreen(JavaKnight game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);


        Label text = new Label("Seleccionar personaje", new Label.LabelStyle(getMainFont(30), Color.WHITE));
        table = new Table();
        table.setFillParent(true);
        table.add(text).spaceBottom(100).row();
        stage.addActor(table);

        characterImage = new Image();
        characterImage.setScaling(Scaling.fit);

        createUI();
    }

        private void createUI() {

            // Create an Image for character 1
            Image character1Image = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("knight1_face.png")))));
            character1Image.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Character 1 selected!");
                    game.setScreen(new PantallaJuego(game, 0));
                }
            });

            character1Image.scaleBy(3);

            // Add character name and image to the table
            //table.add(character1Button);
            table.add(character1Image).spaceRight(character1Image.getWidth()*3 + 30); // Adjust padding as needed
            //table.row();


            // Create an Image for character 2
            Image character2Image = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("knight2_face.png")))));
            character2Image.scaleBy(3);
            character2Image.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Character 2 selected!");
                    updateCharacterImage("knight2_face.png");
                    game.setScreen(new PantallaJuego(game, 1));
                }
            });
            // Add character name and image to the table
            //table.add(character2Button);
            table.add(character2Image).spaceLeft(character2Image.getWidth()*3);  // Adjust padding as needed
            table.row();

            // Add more character buttons and images as needed

            table.add(characterImage).colspan(3).padTop(20); // Adjust colspan as needed

    }

    private void updateCharacterImage(String imagePath) {
        Texture texture = new Texture(Gdx.files.internal(imagePath));
        TextureRegion characterRegion = new TextureRegion(texture);
        Drawable characterDrawable = new TextureRegionDrawable(characterRegion);
        characterImage.setDrawable(characterDrawable);
    }

    @Override
    public void show() {
        // Initialization logic, if needed
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // Pause logic, if needed
    }

    @Override
    public void resume() {
        // Resume logic, if needed
    }

    @Override
    public void hide() {
        // Hide logic, if needed
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
