package com.javaknight.game.pantallas;

import Escenas.Hud;
import Sprites.Balas.BulletManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.javaknight.game.AnimationManager;
import com.javaknight.game.JavaKnight;
import com.javaknight.game.entity.Entity;
import com.javaknight.game.entity.EntityManager;
import com.javaknight.game.entity.PlayableCharacter;
import com.javaknight.game.entity.characters.BlackKnight;
import com.javaknight.game.entity.characters.WhiteKnight;
import com.javaknight.game.levels.Nivel;
import com.javaknight.game.levels.Nivel1;
import com.javaknight.game.levels.Nivel2;
import com.javaknight.game.levels.SafeZone;

public class PantallaJuego implements Screen {

	private JavaKnight game;

	private OrthographicCamera gamecam;
	private Viewport gamePort;
	private Hud hud;

	public OrthogonalTiledMapRenderer renderizado;


	private BulletManager bulletManager;


	private EntityManager entityManager;

	private PlayableCharacter p1;


	public Nivel nivel;

	public AnimationManager animationManager;


	
	public PantallaJuego (JavaKnight game, int personajeId) {
		this.game = game;
		gamecam = new OrthographicCamera();
		gamePort = new FitViewport(JavaKnight.V_WIDTH, JavaKnight.V_HEIGHT, gamecam);
		hud = new Hud(game.batch);
		animationManager = new AnimationManager();
		gamecam.position.set(JavaKnight.V_WIDTH / 2, JavaKnight.V_HEIGHT / 2, 0);
		entityManager = new EntityManager(this);
	   	bulletManager = new BulletManager(this);
		if(personajeId == 0){
			p1 = new BlackKnight(this);
		} else {
			p1 = new WhiteKnight(this);
		}

		// NIVEL
		nivel = new SafeZone(this);
	}
	public PantallaJuego(JavaKnight game){
		this.game = game;
	}
	
	@Override
	public void show () {
	}

	
	public void update(float dt) {
		//nivel.update();
		if(p1.getLife() <= 0){
			this.game.setScreen(new GameOverScreen(this.game));
		}
		// Update logic
		animationManager.update(dt);
	}
	
	
	@Override
	public void render (float delta) {
		update(delta);
		ScreenUtils.clear(0, 0, 0, 1);
		nivel.render();
		animationManager.render(game.batch);
		game.batch.end();
		game.batch.setProjectionMatrix(hud.escenario.getCamera().combined);
		hud.render(game.batch, p1.getLife(), p1.maxLife);
		//hud.escenario.draw();
	}

	@Override
	public void resize (int width, int height) {
		gamePort.update(width, height);
		gamePort.apply();
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


	public boolean detectCollision(float x, float y){
		int TILE_SIZE = 16;

		// Get the dimensions of the TiledMap in pixels
		int mapWidthInPixels = nivel.getMap().getMap().getProperties().get("width", Integer.class) * 16;
		int mapHeightInPixels = nivel.getMap().getMap().getProperties().get("height", Integer.class) * 16;

		// Check if the player is outside the map
		return nivel.getMap().getCollisionLayer().getCell((int)x/TILE_SIZE,(int)y/TILE_SIZE) != null || (x < 0 || x > mapWidthInPixels ||
				y < 0 || y > mapHeightInPixels);
	}

	public Entity getP1(){
		return p1;
	}

	public OrthogonalTiledMapRenderer getRenderizado() {
		return renderizado;
	}

	public JavaKnight getGame() {
		return game;
	}
}

