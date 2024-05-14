package com.javaknight.game.pantallas;


import Escenas.Hud;
import Sprites.Balas.BulletManager;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
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
import com.javaknight.game.levels.SafeZone;
import com.javaknight.game.levels.online.Nivel1Online;
import com.javaknight.game.redes.HiloCliente;
import com.javaknight.game.entity.Character;
import com.javaknight.game.redes.HiloServidor;

public class PantallaServidor extends PantallaJuego {

    public HiloServidor server;
    private final Character player2,player1;
    private JavaKnight game;

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    public OrthogonalTiledMapRenderer renderizado;


    private BulletManager bulletManager;


    private EntityManager entityManager;

    private Character p1, p2;


    public Nivel nivel;

    public AnimationManager animationManager;

    public Array<Character> players = new Array<>();



    public PantallaServidor(JavaKnight game, int personajeId) {
        super(game);
        this.game = game;
        this.gamecam = new OrthographicCamera();
        this.gamePort = new FitViewport(JavaKnight.V_WIDTH, JavaKnight.V_HEIGHT, gamecam);
        this.hud = new Hud(game.batch);
        this.animationManager = new AnimationManager();
        this.gamecam.position.set(JavaKnight.V_WIDTH / 2, JavaKnight.V_HEIGHT / 2, 0);
        this.entityManager = new EntityManager(this);
        this.bulletManager = new BulletManager(this);

        this.player1 = new Character(new Texture("knight1.png"), this);
        this.player2 = new Character(new Texture("knight2.png"), this);

        players.add(player1,player2);
        // NIVEL
        nivel = new Nivel1Online(this);
        this.renderizado = new OrthogonalTiledMapRenderer(nivel.getMap().getMap());

        this.server = new HiloServidor(this);
        this.server.start();


        //this.server.enviarMensajeAlServer("conectar");
    }

    @Override
    public void show () {
    }


    public void update(float dt) {
        nivel.update();
        /*if(p1.getLife() <= 0){
            this.game.setScreen(new GameOverScreen(this.game));
        } */
        for(int i = 0; i < players.size; i++){
            server.enviarMensajeATodos("player#"+i+"#"+players.get(i).life);
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
        game.batch.setProjectionMatrix(hud.escenario.getCamera().combined);
        hud.render(game.batch, player1.getLife(), player1.maxLife);
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

    public Entity getPlayer1(){
        return this.player1;
    }

    public Entity getPlayer2(){
        return this.player2;
    }

    public OrthogonalTiledMapRenderer getRenderizado() {
        return renderizado;
    }

    public JavaKnight getGame() {
        return game;
    }



}

