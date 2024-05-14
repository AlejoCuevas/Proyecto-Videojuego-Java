package com.javaknight.game.pantallas;

import Escenas.Hud;
import Sprites.Balas.Bullet;
import Sprites.Balas.BulletManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.javaknight.game.AnimationManager;
import com.javaknight.game.JavaKnight;
import com.javaknight.game.MapLoader;
import com.javaknight.game.entity.Character;
import com.javaknight.game.entity.EntityManager;
import com.javaknight.game.entity.PlayableCharacter;
import com.javaknight.game.entity.boss.Boss;
import com.javaknight.game.entity.characters.BlackKnight;
import com.javaknight.game.entity.characters.WhiteKnight;
import com.javaknight.game.levels.Nivel;
import com.javaknight.game.levels.SafeZone;
import com.javaknight.game.levels.online.Nivel1Online;
import com.javaknight.game.redes.HiloCliente;
import com.javaknight.game.redes.RedesUtiles;

import javax.naming.directory.Attribute;

public class PantallaOnline extends PantallaJuego {

	private final HiloCliente cliente;
	private final Character player1,player2;
	private final Array<OrthogonalTiledMapRenderer> renderizados = new Array<>();
	private JavaKnight game;

	private OrthographicCamera gamecam;
	private Viewport gamePort;
	private Hud hud;



	private BulletManager bulletManager;


	private EntityManager entityManager;



	public Nivel nivel;

	Array<MapLoader> mapas = new Array<>();
	public int nivelActual;

	public AnimationManager animationManager;

	public Array<Character> players = new Array<>();

	

	private boolean previousWPressed = false;
    private boolean previousSPressed = false;
    private boolean previousAPressed = false;
    private boolean previousDPressed = false;
	private boolean WPressed = false;
	private boolean SPressed = false;
	private boolean APressed = false;
	private boolean DPressed = false;
	
	public PantallaOnline(JavaKnight game, int personajeId) {
		super(game);
		this.game = game;
		gamecam = new OrthographicCamera();
		gamePort = new FitViewport(JavaKnight.V_WIDTH, JavaKnight.V_HEIGHT, gamecam);
		hud = new Hud(game.batch);
		animationManager = new AnimationManager();
		gamecam.position.set(JavaKnight.V_WIDTH / 2, JavaKnight.V_HEIGHT / 2, 0);
		entityManager = new EntityManager(this);
	   	bulletManager = new BulletManager(this);
		this.player1 = new Character(new Texture("knight1.png"), this);
		this.player2 = new Character(new Texture("knight2.png"), this);
		players.add(player1,player2);
		entityManager.getEntities().add(player1,player2);
		// NIVEL
		//nivel = new SafeZone(this);
		// Cargar los mapas
		mapas.add(new MapLoader("safezone.tmx"),new MapLoader("nivel1_new.tmx"),new MapLoader("nivel2_new.tmx"),new MapLoader("nivel3_new.tmx"));
		for(MapLoader ml : mapas){
			this.renderizados.add(new OrthogonalTiledMapRenderer(ml.getMap()));
		}


		this.cliente = new HiloCliente(this);
		this.cliente.start();
		this.cliente.enviarMensajeAlServer("conectar");

	}
	
	@Override
	public void show () {
	}

	
	public void update(float dt) {

		player1.update(this, WPressed, SPressed, APressed, DPressed);
		player2.update(this, WPressed, SPressed, APressed, DPressed);
		//HANDLING ONLINE INPUTS
		handleInputs();

		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
				// Obtener las coordenadas del cursor en relación con la cámara
				Vector3 cursorPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
				gamecam.unproject(cursorPos);
				System.out.println(cursorPos.x + " " + cursorPos.y);
				cliente.enviarMensajeAlServer("disparar#"+cursorPos.x+"#"+cursorPos.y);
		}
		//
		// Update bullets
		getBulletManager().update(dt);

		entityManager.update(dt);
		//nivel.update();
		// Update logic
		animationManager.update(dt);
	}

	public void handleInputs() {
        // Verificar el estado actual de las teclas presionadas
        WPressed = Gdx.input.isKeyPressed(Input.Keys.W);
        SPressed = Gdx.input.isKeyPressed(Input.Keys.S);
        APressed = Gdx.input.isKeyPressed(Input.Keys.A);
        DPressed = Gdx.input.isKeyPressed(Input.Keys.D);

        // Enviar mensaje al servidor solo si hay un cambio en el estado de las teclas presionadas
        if (WPressed != previousWPressed || SPressed != previousSPressed ||
            APressed != previousAPressed || DPressed != previousDPressed) {
            cliente.enviarMensajeAlServer("moverse#" + WPressed + "#" + SPressed + "#" + APressed + "#" + DPressed);

            // Actualizar el estado anterior de las teclas presionadas
            previousWPressed = WPressed;
            previousSPressed = SPressed;
            previousAPressed = APressed;
            previousDPressed = DPressed;
        }
    }


	@Override
	public void render (float delta) {
		if(RedesUtiles.fin) {
			game.setScreen(new GameOverScreen(game));
			RedesUtiles.fin = false;
			return;
		}
		update(delta);
		ScreenUtils.clear(0, 0, 0, 1);
		//nivel.render();
		Character jugador = players.get(RedesUtiles.clienteId);
		jugador = jugador.life <= 0 ? RedesUtiles.clienteId==0 ? player2 : player1 : jugador;
		getGamecam().position.x = jugador.getX();
		getGamecam().position.y = jugador.getY();
		getGamecam().update();
		try {
			getRenderizado().setView(getGamecam());
			getRenderizado().getBatch().begin();
			for (MapLayer layer : mapas.get(nivelActual).getMap().getLayers()) {
				if (!layer.getName().equals("collisionLayer") && layer.getObjects().getCount() < 1) {
					getRenderizado().renderTileLayer((TiledMapTileLayer) layer);
				}
			}
			getRenderizado().getBatch().end();
		} catch(IllegalStateException e){
			System.out.println("batch error ! ");
		}


		getGame().batch.setProjectionMatrix(getGamecam().combined);
		getGame().batch.begin();
		for(Bullet b : getBulletManager().getActiveBullets()){
			b.draw(getGame().batch);
		}
		getEntityManager().draw(getGame().batch);
		animationManager.render(game.batch);
		//game.batch.end();
		game.batch.setProjectionMatrix(hud.escenario.getCamera().combined);
		getGame().batch.end();
		hud.render(game.batch, jugador.getLife(), jugador.maxLife);


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
		int mapWidthInPixels = mapas.get(nivelActual).getMap().getProperties().get("width", Integer.class) * 16;
		int mapHeightInPixels = mapas.get(nivelActual).getMap().getProperties().get("height", Integer.class) * 16;

		// Check if the player is outside the map
		return mapas.get(nivelActual).getCollisionLayer().getCell((int)x/TILE_SIZE,(int)y/TILE_SIZE) != null || (x < 0 || x > mapWidthInPixels ||
				y < 0 || y > mapHeightInPixels);
	}


	public OrthogonalTiledMapRenderer getRenderizado() {
		return renderizados.get(nivelActual);
	}

	public JavaKnight getGame() {
		return game;
	}

	public void setNivel(int id){
		nivelActual = id;
		entityManager.getEntities().clear();
		bulletManager.getActiveBullets().clear();
		entityManager.getEntities().add(player1,player2);
	}
}

