package Sprites.Enemigos;

import Sprites.Balas.Bullet;
import Sprites.Balas.BulletManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.javaknight.game.Utils;
import com.javaknight.game.guns.Gun;
import com.javaknight.game.guns.M4;

public class Enemy extends Sprite {
    private static final int FRAME_WIDTH = 17;
    private static final int FRAME_HEIGHT = 22;
    private static final int FRAME_SPACING = 1;
    private static final int FRAME_COUNT = 5;

    private Texture spriteSheet;
    private Array<TextureRegion> standingFrames = new Array<>();
    private Animation<TextureRegion> standingAnimation;
    private float stateTime = 0;
    public float life = 2;

    private Gun gun;
    public Enemy(Texture spriteSheet) {
        this.spriteSheet = spriteSheet;
        Utils.splitFrames(standingFrames, spriteSheet, FRAME_WIDTH,FRAME_HEIGHT,FRAME_COUNT,FRAME_SPACING);
        standingAnimation = new Animation<>(0.2f, standingFrames, Animation.PlayMode.LOOP);
        // Set the initial frame as the first frame of the standing animation
        setRegion(standingFrames.first());
        setBounds(getX(), getY(), FRAME_WIDTH, FRAME_HEIGHT);
        gun = new M4();
    }

    /*private void splitFrames() {
        standingFrames = new Array<>();
        int startX = 0; // Adjust if there's any spacing or margin at the beginning of the sprite sheet
        for (int i = 0; i < FRAME_COUNT; i++) {
            standingFrames.add(new TextureRegion(spriteSheet, startX + i * (FRAME_WIDTH + FRAME_SPACING), 0, FRAME_WIDTH, FRAME_HEIGHT));
        }
    } */

    public void update(float deltaTime, BulletManager bulletManager, Vector2 playerPos) {
        stateTime += deltaTime;
        setRegion(standingAnimation.getKeyFrame(stateTime));

        attachWeapon(gun);
        gun.timeSinceLastShot += deltaTime;
        // Handle shooting if enough time has passed
        float distance = playerPos.dst(gun.getX(),gun.getY());
        if (distance < 500 && gun.timeSinceLastShot > gun.getTimeBetweenShots()) {
            shootAtPlayerWithRandomness(bulletManager,playerPos);
            gun.timeSinceLastShot = MathUtils.random(-gun.getTimeBetweenShots(), 0);; // Reset the time since the last shot
            //shoot(bulletManager, camera);
        }
        if(distance > 50 && distance < 2000 ) followPlayerWithRandomness(playerPos, deltaTime);

    }

    // Dispose of the sprite sheet when it's no longer needed
    public void dispose() {
        spriteSheet.dispose();
    }

    public void hit(Bullet b){
        life -= b.getDamage();
    }

    private void followPlayerWithRandomness(Vector2 followTo, float deltaTime) {
        float speed = 50; // Adjust the speed as needed
        Vector2 playerPosition = new Vector2(followTo.x,followTo.y);
        Vector2 enemyPosition = new Vector2(getX(), getY());
        Vector2 direction = playerPosition.cpy().sub(enemyPosition).nor();
        // Introduce a random variation to the direction
        float randomAngle = MathUtils.random(-20f, 20f); // Adjust the range as needed
        direction.rotate(randomAngle);
        // Move towards the player with some randomness
        float velocityX = direction.x * speed * deltaTime;
        float velocityY = direction.y * speed * deltaTime;
        translate(velocityX, velocityY);
    }

    private void shootAtPlayerWithRandomness(BulletManager manager, Vector2 shootAt) {
            // Shoot at the player with some randomness
            float bulletSpeed = 1; // Adjust the bullet speed as needed
            Vector2 playerPosition = new Vector2(shootAt.x, shootAt.y);
            Vector2 enemyPosition = new Vector2(getX(), getY());
            Vector2 direction = playerPosition.cpy().sub(enemyPosition).nor();

            // Introduce a random variation to the bullet direction
            float randomAngle = MathUtils.random(-10f, 10f); // Adjust the range as needed
            direction.rotate(randomAngle);

            // Create a bullet and add it to the game's bullet manager
            // The actual implementation may vary based on your game structure
            // Adjust the parameters based on your bullet class and damage

            gun.shoot(manager, direction.x, direction.y, true);
            //Bullet bullet = new Bullet(getX(), getY(), direction.x * bulletSpeed, direction.y * bulletSpeed, gun.bulletType.getAnimation(), gun.getDamage(),true);
            //manager.addBullet(bullet);

            //bullet.setDamage(1); // Set the appropriate damage value
            // Introduce a random variation to the shoot cooldown
            float randomCooldown = MathUtils.random(0.5f, 1.5f); // Adjust the range as needed
    }

    public void attachWeapon(Gun g){
        // Calculate angle between weapon and cursor
        gun.setPosition(getX()-(isFlipX() ? getRegionWidth()/2 : 0),getY());
    }
    public void draw(SpriteBatch batch){
        super.draw(batch);
        gun.draw(batch);
    }
}
