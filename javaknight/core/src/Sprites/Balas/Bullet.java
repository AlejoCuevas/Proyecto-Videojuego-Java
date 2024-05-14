package Sprites.Balas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Bullet extends Sprite implements Damage {
    float velocityX, velocityY; // velocity
    public static float velocityCoeff = 400f;

    private Animation<TextureRegion> animation;

    private float stateTime;

    private float damage = 1f;

    private boolean enemyShoot;

    // Other properties and methods
    public Bullet(float x, float y){
        super(new Texture("bullet.png"));
        setBounds(0,0,8,8);
        setScale(0.45f);
        setPosition(x,y);
    }

    public Bullet(float x, float y, float directionX, float directionY){
        super(new Texture("bullet.png"));
        setBounds(0,0,8,8);
        setScale(0.45f);
        setPosition(x,y);
        velocityX = directionX * velocityCoeff;
        velocityY = directionY * velocityCoeff;
    }

    public Bullet(float x, float y, float directionX, float directionY, Animation<TextureRegion> animation, float damage,boolean enemyShoot){
        super(new Texture("bullet.png"));
        setBounds(0,0,8,8);
        setScale(0.45f);
        setPosition(x,y);
        velocityX = directionX * velocityCoeff;
        velocityY = directionY * velocityCoeff;
        this.animation = animation;
        this.damage = damage;
        this.enemyShoot = enemyShoot;
    }

    public void update(float deltaTime){
        if(animation != null){
            stateTime += Gdx.graphics.getDeltaTime();
            // Get the current frame from the selected animation
            TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
            this.setRegion(currentFrame);
        }

    }

    public float getDamage() {
        return damage;
    }

    public boolean isEnemyShoot() {
        return enemyShoot;
    }
}
