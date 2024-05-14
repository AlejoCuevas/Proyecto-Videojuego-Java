package com.javaknight.game.entity;

import Sprites.Balas.BalaRed;
import Sprites.Balas.Bullet;
import Sprites.Balas.BulletManager;
import Sprites.Balas.Damage;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.javaknight.game.Utils;
import com.javaknight.game.guns.Gun;
import com.javaknight.game.guns.M4;
import com.javaknight.game.pantallas.PantallaJuego;
import com.javaknight.game.pantallas.PantallaServidor;

public class EnemyEntity extends Sprite implements Entity {

    // ENTITY ALLOCATIONS
    public float maxLife = 5;

    public float life = 1;

    // TEXTURE ALLOCATIONS
    final int FRAME_WIDTH = 17, FRAME_HEIGHT = 22, FRAME_SPACING = 1,FRAME_COUNT = 5;
    private Array<TextureRegion> standingFrames = new Array<>();
    private Animation<TextureRegion> standingAnimation;
    private float stateTime;

    // GUN ALLOCATIONS
    protected Gun gun;


    public EnemyEntity(Texture texture){
        super(texture);
        Utils.splitFrames(standingFrames, getTexture(), FRAME_WIDTH,FRAME_HEIGHT,FRAME_COUNT,FRAME_SPACING);
        standingAnimation = new Animation<>(0.2f, standingFrames, Animation.PlayMode.LOOP);
        setRegion(standingFrames.first());
        setBounds(getX(), getY(), FRAME_WIDTH, FRAME_HEIGHT);
        gun = new M4();
    }

    public EnemyEntity(Texture texture, float life, Gun gun){
        super(texture);
        Utils.splitFrames(standingFrames, getTexture(), FRAME_WIDTH,FRAME_HEIGHT,FRAME_COUNT,FRAME_SPACING);
        standingAnimation = new Animation<>(0.2f, standingFrames, Animation.PlayMode.LOOP);
        setRegion(standingFrames.first());
        setBounds(getX(), getY(), FRAME_WIDTH, FRAME_HEIGHT);
        this.gun = gun;
        this.life = life;
        this.maxLife = life;
    }

    @Override
    public float getLife() {
        return this.life;
    }


    @Override
    public void update(float dt) {
        stateTime += dt;
        setRegion(standingAnimation.getKeyFrame(stateTime));
        gun.setPosition(getX()-(isFlipX() ? getRegionWidth()/2 : 0),getY());
    }


    @Override
    public void hit(Damage b, boolean enemyShoot) {
        if(!enemyShoot) this.life -= b.getDamage();
    }

    public void draw(Batch batch){
        super.draw((SpriteBatch)batch);
        gun.draw(batch);
    }

    @Override
    public boolean isEnemy() {
        return true;
    }


    public void update(float deltaTime,PantallaJuego gameScreen) {
        stateTime += deltaTime;
        setRegion(standingAnimation.getKeyFrame(stateTime));
        gun.setPosition(getX()-(isFlipX() ? getRegionWidth()/2 : 0),getY());
        gun.timeSinceLastShot += deltaTime;

        Array<Entity> players = gameScreen.getEntityManager().getNonEnemyEntities();


        // Busca al jugador mas cercano
        Entity closestPlayer = null;
        float dist = 0;
        for(Entity player : players){
            float distance = player.getPosition().dst(gun.getX(),gun.getY());
            if(distance < dist || closestPlayer == null){
                closestPlayer = player;
                dist = distance;
            }
        }

        // Si no hay ninguno cerca devuelve la funcion
        if(closestPlayer == null) return;
        // Handle shooting if enough time has passed

        // Si la distancia < 500 y podes disparar
        if (dist < 500 && gun.timeSinceLastShot > gun.getTimeBetweenShots()) {

            if(gameScreen instanceof PantallaServidor){
                BalaRed bullet = gun.shootRed(gameScreen.getBulletManager(),closestPlayer.getPosition().x,closestPlayer.getPosition().y, true);
                ((PantallaServidor) gameScreen).server.enviarMensajeATodos("disparo#"+"-1"+"#"+bullet.x+"#"+bullet.y+"#"+bullet.directionX+"#"+ bullet.directionY+"#"+ bullet.damage);
            } else {
                gun.shoot(gameScreen.getBulletManager(),closestPlayer.getPosition().x,closestPlayer.getPosition().y, true);
            }
            gun.timeSinceLastShot = MathUtils.random(-gun.getTimeBetweenShots(), 0);; // Reset the time since the last shot
            //shoot(bulletManager, camera);
        }
        // Si la distancia > 50 y menor a 500
        if(dist > 50 && dist < 500) followPlayerWithRandomness(closestPlayer.getPosition(), deltaTime, gameScreen);
    }

    @Override
    public Vector2 getPosition() {
       return new Vector2(this.getX(), this.getY());
    }

    private void followPlayerWithRandomness(Vector2 followTo, float deltaTime, PantallaJuego gameScreen) {
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

        // Check on X
        if(gameScreen.detectCollision(getX()+velocityX,getY())) velocityX = 0;
        // Check on Y
        if(gameScreen.detectCollision(getX(),getY()+velocityY)) velocityY = 0;

        translate(velocityX, velocityY);
    }


}
