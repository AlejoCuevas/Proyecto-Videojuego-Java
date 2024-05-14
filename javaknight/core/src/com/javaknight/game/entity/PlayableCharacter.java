package com.javaknight.game.entity;

import Sprites.Balas.Bullet;
import Sprites.Balas.BulletManager;
import Sprites.Balas.Damage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.javaknight.game.AnimationType;
import com.javaknight.game.Utils;
import com.javaknight.game.guns.Gun;
import com.javaknight.game.guns.M4;
import com.javaknight.game.pantallas.PantallaJuego;

public class PlayableCharacter extends Sprite implements Entity {

    public int money = 0;

    // ENTITY ALLOCATIONS
    public float maxLife = 100;

    public float life = maxLife;
    float step = 2.5f;

    // TEXTURE ALLOCATIONS
    final int FRAME_WIDTH = 17, FRAME_HEIGHT = 22, FRAME_SPACING = 1,FRAME_COUNT = 5;
    private Array<TextureRegion> standingFrames = new Array<>();
    private Animation<TextureRegion> standingAnimation;
    private float stateTime;

    // GUN ALLOCATIONS
    private Gun gun;

    protected PantallaJuego gameScreen;

    public float skillLastUse = 5;
    public float skillCooldown = 5; // segundos


    public PlayableCharacter(Texture texture, PantallaJuego gameScreen){
        super(texture);
        this.gameScreen = gameScreen;
        Utils.splitFrames(standingFrames, getTexture(), FRAME_WIDTH,FRAME_HEIGHT,FRAME_COUNT,FRAME_SPACING);
        standingAnimation = new Animation<>(0.2f, standingFrames, Animation.PlayMode.LOOP);
        setRegion(standingFrames.first());
        setBounds(getX(), getY(), FRAME_WIDTH, FRAME_HEIGHT);
        gun = new M4();
    }

    @Override
    public void hit(Damage b, boolean enemyShoot) {
        if(enemyShoot) this.life -= b.getDamage();
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void update(float dt, PantallaJuego gameScreen) {
        stateTime += dt;
        setRegion(standingAnimation.getKeyFrame(stateTime));
        handleInputs();
        gun.setPosition(getX()-(isFlipX() ? getRegionWidth()/2 : 0),getY());
        gun.timeSinceLastShot += dt;
        skillLastUse += dt;

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && gun.timeSinceLastShot > gun.getTimeBetweenShots()) {
            shoot(gameScreen.getBulletManager(), gameScreen.getGamecam());
            gun.timeSinceLastShot = 0; // Reset the time since the last shot
        }
        if(skillLastUse > 5 && Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
                Vector3 cursorPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                gameScreen.getGamecam().unproject(cursorPos);
                if(!gameScreen.detectCollision(cursorPos.x, cursorPos.y)){
                    skillLastUse = 0;
                    // SKILL 1
                    this.useSkill(cursorPos.x,cursorPos.y);

                    // SKILL 2
                /*Rectangle cursorArea = new Rectangle(cursorPos.x-10,cursorPos.y-10,cursorPos.x+10,cursorPos.y+10);
                for(Entity e : gameScreen.getEntityManager().getEntities()){
                    if(!e.isEnemy()) continue;
                    if(e.getBoundingRectangle().overlaps(cursorArea)) {
                        e.hit(new Damage(){
                            @Override
                            public float getDamage() {
                                return 5;
                            }
                        }, false);
                    }


                    gameScreen.animationManager.spawnAnimation(cursorPos.x, cursorPos.y, AnimationType.SKILL2.loadAnimation(), 1);
                }*/
                };



        }

    }

    /*@Override
    public void update(float deltaTime) {

    } */


    public void shoot(BulletManager bulletManager, Camera camera) {
        // Obtener las coordenadas del cursor en relación con la cámara
        Vector3 cursorPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(cursorPos);
        System.out.println(cursorPos.x + " " + cursorPos.y);
        gun.shoot(bulletManager,cursorPos.x,cursorPos.y, false);
    }
    @Override
    public Vector2 getPosition() {
        return new Vector2(getX(), getY());
    }

    @Override
    public int getRegionHeight() {
        return super.getRegionHeight();
    }

    @Override
    public int getRegionWidth() {
        return super.getRegionWidth();
    }

    @Override
    public float getLife() {
        return this.life;
    }
    @Override
    public boolean isEnemy() {
        return false;
    }

    public void draw(Batch batch){
        super.draw((SpriteBatch)batch);
        gun.draw(batch);
    }

    public void handleInputs(){
        float velocidadX = 0;
        float velocidadY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocidadY = step;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocidadX = step;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocidadX = -step;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocidadY = -step;
        }

        // Check on X
        if(gameScreen.detectCollision(getX()+velocidadX,getY()) || gameScreen.detectCollision(getX()+getRegionWidth()+velocidadX,getY())) velocidadX = 0;
        // Check on Y
        if(gameScreen.detectCollision(getX(),getY()+velocidadY) ||gameScreen.detectCollision(getX(),getY()+getRegionHeight()/2+velocidadY) ) velocidadY = 0;

        translate(velocidadX,velocidadY);
    }

    public void useSkill(float x, float y){

    }




}
