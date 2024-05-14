package com.javaknight.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.javaknight.game.JavaKnight;
import com.javaknight.game.Utils;

import java.security.Key;

import static com.javaknight.game.Utils.getMainFont;

public class ShopScreen extends ScreenAdapter {

    private final PantallaJuego gameScreen;
    private final Stage stage;
    private OrthographicCamera camera;
    private Label moneyLabel;
    Table table;

    public boolean open = false;

    public ShopScreen(PantallaJuego gameScreen) {
        this.gameScreen = gameScreen;
        this.camera = new OrthographicCamera();
        this.stage = new Stage(new FitViewport(JavaKnight.V_WIDTH, JavaKnight.V_HEIGHT, camera));
        Gdx.input.setInputProcessor(stage);

        // Create UI elements for the shop
        table = new Table();
        table.background(new TextureRegionDrawable(new Texture("shopbg.png")));
        table.pad(50,50,50,50);
        table.setFillParent(true);

        // Add money label
        moneyLabel = new Label("Money: $" + gameScreen.getP1().money, new Label.LabelStyle(getMainFont(30), Color.FOREST));
        table.add(moneyLabel).space(15).left();


        table.center();

        //addShopItem("Item 2", 20, "item2_texture.png");

        // Set up close button
        TextButton closeButton = Utils.createTextButton("Close",30, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Close the shop screen
                //game.setScreen(new YourGameplayScreen(game));
                open = false;
            }
        });
        table.add(closeButton).left().space(15).row();

        // Set table alignment to center
        table.center();

        // Add shop items
        addShopItem("M4", 10, "guns/M4.png");
        addShopItem("SMG", 10, "guns/SMG.png");
        addShopItem("Potion", 10, "guns/potion.png");

        stage.addActor(table);
    }

    private void addShopItem(final String itemName, int price, String texturePath) {
        TextButton itemButton = Utils.createTextButton(itemName,12, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Handle item purchase logic
                // Deduct currency, unlock item, etc.
                // For simplicity, just print a message for now
                System.out.println(itemName + " purchased!");
            }
        });

        Image itemImage = new Image(new Sprite(new Texture(texturePath)));
        Label itemInfoLabel = new Label("$"+price, new Label.LabelStyle(getMainFont(12), Color.WHITE));

        // Add the item to the table
        Table itemTable = new Table();

        itemTable.background(new TextureRegionDrawable(new Texture("guns/epic.png")));
        itemTable.add(itemButton).padBottom(10).row();
        itemTable.add(itemImage).size(itemImage.getWidth(), itemImage.getHeight()).padBottom(10).row();
        itemTable.add(itemInfoLabel);
        itemTable.align(Align.center);
        table.add(itemTable).expandX();
        //stage.addActor(itemTable);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        // Update money label
        moneyLabel.setText("Money: $" + gameScreen.getP1().money);

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            open = false;
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }


}
