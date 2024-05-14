package com.javaknight.game.pantallas;

import Sprites.Balas.Bullet;
import Sprites.Balas.BulletManager;
import Sprites.Enemigos.Enemy;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.javaknight.game.BulletAnimationManager;
import com.javaknight.game.EnemyManager;
import com.javaknight.game.JavaKnight;

import Escenas.Hud;
import Sprites.Knight;
import com.javaknight.game.MapLoader;
import com.javaknight.game.entity.EnemyEntity;
import com.javaknight.game.entity.EntityManager;
import com.javaknight.game.entity.PlayableCharacter;
import com.javaknight.game.entity.boss.Boss;
import com.javaknight.game.levels.Nivel;
import com.javaknight.game.levels.Nivel1;
import com.javaknight.game.levels.Nivel2;

public class Porlasdudas implements Screen {
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
/*
    private final BulletAnimationManager bulletAnimationManager;
    private final Animation<TextureRegion> selectedAnimation;
    private float stateTime;

    private JavaKnight game;
    private TextureAtlas atlas;

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    private MapLoader mapa;
    private OrthogonalTiledMapRenderer renderizado;

//	private Knight jugador;

    private BulletManager bulletManager;

    private TiledMapTileLayer collisionLayer;

    private Enemy enemy;

    // Entities.

    private EntityManager entityManager;

    private PlayableCharacter p1;

    private Boss boss;

    public Nivel nivel;



    public Porlasdudas (JavaKnight game) {
        //Gdx.input.setCursorCatched(true);
        atlas = new TextureAtlas("Jugadores.pack");
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(JavaKnight.V_WIDTH, JavaKnight.V_HEIGHT, gamecam);
        hud = new Hud(game.batch);
        mapa = new MapLoader("nivel1_new.tmx");
        renderizado = new OrthogonalTiledMapRenderer(mapa.getMap());
        gamecam.position.set(JavaKnight.V_WIDTH / 2, JavaKnight.V_HEIGHT / 2, 0);
        //jugador = new Knight( this);
        entityManager = new EntityManager(this);
        bulletManager = new BulletManager(this);
        p1 = new PlayableCharacter(new Texture("knight1.png"), this);
        entityManager.getEntities().add(p1);
        enemy = new Enemy(new Texture("knight2.png"));
        boss = new Boss(new Texture("knight2.png"));
        boss.setPosition(mapa.getBossSpawnpoint().x,mapa.getBossSpawnpoint().y);
        entityManager.getEntities().add(boss);
        p1.setPosition(mapa.getPlayerSpawnpoints().get(0).x,mapa.getPlayerSpawnpoints().get(0).y);
        for(Vector2 v : mapa.getEnemySpawnpoints()){
            EnemyEntity en = new EnemyEntity(new Texture("knight2.png"));
            en.setPosition(v.x,v.y);
            entityManager.getEntities().add(en);
        }
        // Bullets
        bulletAnimationManager = new BulletAnimationManager("guns/bullets.png", 10, 10, 12, 4); // Adjust parameters as needed
        selectedAnimation = bulletAnimationManager.getAnimation(8); // Select the animation you want
        stateTime = 0f;

        //enemyEntity = new EnemyEntity(new Texture("knight2.png"));
        nivel = new Nivel2(this);
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show () {
        //Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Crosshair);
        collisionLayer = (TiledMapTileLayer) mapa.getMap().getLayers().get("collisionLayer");
    }


    public void update(float dt) {
        nivel.update();
		/*
		//mundo.step(1/60f, 6, 2);
		entityManager.update(dt);
//		enemyEntity.update(dt);
		gamecam.position.x = p1.getX();
		gamecam.position.y = p1.getY();
		//gamecam.position.x = jugador.b2body.getPosition().x;
		gamecam.update();
		// Update bullets
		bulletManager.update(dt);
		renderizado.setView(gamecam);

		System.out.println(boss.getLife());
		boss.setActive(p1.getBoundingRectangle().overlaps(mapa.getBossRoom()));
*//*

    }
/*

    @Override
    public void render (float delta) {
        //update(delta);
        ScreenUtils.clear(0, 0, 0, 1);
        //enderizado.render();

        nivel.render();

        game.batch.end();
        game.batch.setProjectionMatrix(hud.escenario.getCamera().combined);
        hud.escenario.draw();
    }

    @Override
    public void resize (int width, int height) {
        gamePort.update(width, height);
        gamePort.apply();
    }

    public TiledMap getMap() {
        return mapa.getMap();
    }

    @Override
    public void pause () {

    }

    @Override
    public void resume () {

    }

    @Override
    public void hide () {

    }

    @Override
    public void dispose () {
        mapa.getMap().dispose();
        renderizado.dispose();

    }

    public BulletManager getBulletManager() {
        return bulletManager;
    }
    public EntityManager getEntityManager(){
        return entityManager;
    }

    public OrthographicCamera getGamecam(){
        return gamecam;
    }

    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }


    public boolean detectCollision(float x, float y){
        int TILE_SIZE = 16;
        return nivel.getMap().getCollisionLayer().getCell((int)x/TILE_SIZE,(int)y/TILE_SIZE) != null;
    }

    public PlayableCharacter getP1(){
        return p1;
    }

    public OrthogonalTiledMapRenderer getRenderizado() {
        return renderizado;
    }

    public JavaKnight getGame() {
        return game;
    } */
}

