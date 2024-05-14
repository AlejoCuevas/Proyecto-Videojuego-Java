package Sprites;

import Sprites.Armas.AK;
import Sprites.Balas.Bullet;
import Sprites.Balas.BulletManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.javaknight.game.guns.BulletType;
import com.javaknight.game.guns.Gun;
import com.javaknight.game.guns.M4;
import com.javaknight.game.guns.SMG;
import com.javaknight.game.pantallas.PantallaJuego;
import com.badlogic.gdx.utils.Array;
import org.w3c.dom.Text;

import java.util.Arrays;

public class Knight extends Sprite {
    public enum Estado { PARADO, CORRER }
    public Estado estadoActual;
    public Estado estadoAnterior;
    private TextureRegion knightStand;
    private Animation<TextureRegion> knightRun;
    private Animation<TextureRegion> knightStill;

    private float tiempoEstado;
    public boolean caminandoDerecha;

    public boolean moviendose;

    public Gun arma;

    private float timeBetweenShots = 0.3f; // Set the time between shots in seconds
    private float timeSinceLastShot = 0;


    public Knight(PantallaJuego screen) {
        //super(screen.getAtlas().findRegion("Run-Sheet"));
        super(new TextureRegion(new Texture("knight1.png")));
        arma = new SMG();
        estadoActual = Estado.PARADO;
        estadoAnterior = Estado.CORRER;
        tiempoEstado = 0;
        caminandoDerecha = true;
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(getTexture(), 1 + i * 0, 0, 17, 22));
        }
        knightRun = new Animation<TextureRegion>(1/10f,  frames);
        frames.clear();
        knightStand = new TextureRegion(getTexture(), 0, 0, 17, 22);
        setBounds(0, 0, 17,22);
        setRegion(knightStand);

    }

    public void update(float dt, BulletManager bulletManager,Camera camera) {
        attachWeapon(arma,camera);
        // HANDLE SHOTS
        arma.timeSinceLastShot += dt;
        // Handle shooting if enough time has passed
        if (Gdx.input.isTouched() && arma.timeSinceLastShot > arma.getTimeBetweenShots()) {
            shoot(bulletManager, camera);
            arma.timeSinceLastShot = 0; // Reset the time since the last shot
        }
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt) {
        TextureRegion region;
            region = knightRun.getKeyFrame(tiempoEstado, true);
        tiempoEstado = estadoActual == estadoAnterior ? tiempoEstado + dt : 0;
        estadoAnterior = estadoActual;
        return region;
    }
    public void draw(SpriteBatch batch){
        super.draw(batch);
        arma.draw(batch);
    }

    /*public void shoot(BulletManager bManager){
        //float bulletX = getX() + getRegionWidth();
        //float bulletY = getY() + getRegionHeight() / 2;
        float bulletVelocityX = 200; // Set your bullet speed
        float bulletVelocityY = 15; // Adjust based on your game requirements
        // Spawn a bullet using the BulletManager
        Gdx.input.getX();
        Gdx.input.getY();
        bManager.spawnBullet(arma.getX(), arma.getY(), bulletVelocityX, bulletVelocityY);
    } */
    public void shoot(BulletManager bulletManager, Camera camera) {
        float bulletX = arma.getX() + arma.getWidth()/2; // arma.getX() +  (arma.isFlipX()  ? 0 : arma.getWidth());
        float bulletY = arma.getY() + arma.getHeight() / 2;
        // Obtener las coordenadas del cursor en relación con la cámara
        Vector3 cursorPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(cursorPos);
        arma.shoot(bulletManager,cursorPos.x,cursorPos.y, false);
        /*
        // Calcular la dirección hacia el cursor
        float directionX = cursorPos.x - bulletX;
        float directionY = cursorPos.y - bulletY;
        float length = (float) Math.sqrt(directionX * directionX + directionY * directionY);
        // Normalizar la dirección y establece  r la velocidad de la bala
        if (length != 0) {
            directionX /= length;
            directionY /= length;
            bulletManager.spawnBulletDirection(bulletX,bulletY,directionX,directionY);
        } */
    }

   public void attachWeapon(Gun g, Camera camera){
       Vector3 cursorPosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
       camera.unproject(cursorPosition);
       // Calculate angle between weapon and cursor
       float angle = MathUtils.atan2(cursorPosition.y - arma.getY(), cursorPosition.x - arma.getX());
       // Convert angle to degrees and set rotation of the weapon sprite
       arma.setRotation(MathUtils.radiansToDegrees * angle);
       arma.setPosition(getX()-(isFlipX() ? getRegionWidth()/2 : 0),getY());
       arma.setFlip(false, cursorPosition.x < getX()+getRegionWidth()/2);
   }
}
