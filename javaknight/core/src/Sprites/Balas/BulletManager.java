package Sprites.Balas;

import Sprites.Enemigos.Enemy;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.javaknight.game.EnemyManager;
import com.javaknight.game.entity.Entity;
import com.javaknight.game.entity.EntityManager;
import com.javaknight.game.guns.BulletType;
import com.javaknight.game.pantallas.PantallaJuego;

import static com.javaknight.game.JavaKnight.V_HEIGHT;
import static com.javaknight.game.JavaKnight.V_WIDTH;

public class BulletManager {

    private final PantallaJuego gameScreen;

    public BulletManager(PantallaJuego gameScreen){
        this.gameScreen = gameScreen;
    }

    private Array<Bullet> activeBullets = new Array<>();
    public Array<BalaRed> balasRed = new Array<>();

    public void spawnBullet(float x, float y, float velocityX, float velocityY) {
        Bullet bullet = new Bullet(x,y);
        bullet.velocityX = velocityX;
        bullet.velocityY = velocityY;
        activeBullets.add(bullet);
    }

    public void spawnBulletDirection(float x, float y, float directionX, float directionY) {
        Bullet bullet = new Bullet(x,y,directionX,directionY);
        activeBullets.add(bullet);
    }


    /*public void spawnBulletDirection(BulletType bulletType, float x, float y, float directionX, float directionY) {
        //Bullet bullet = new Bullet(x,y,directionX,directionY, bulletType.getAnimation());
        activeBullets.add(bullet);
    }*/

    public void addBullet(Bullet b){
        activeBullets.add(b);
    }

    public void update(float deltaTime) {
        // Crear las balas que vienen del online
        for(int i = 0; i < balasRed.size; i++){
            BalaRed balared = balasRed.get(i);
            Bullet bullet = new Bullet(balared.x,balared.y,balared.directionX,balared.directionY, BulletType.SIMPLE.getAnimation(), balared.damage, balared.enemy);
            activeBullets.add(bullet);
            balasRed.removeIndex(i);
        }

        // Update bullet positions
        for (Bullet bullet : activeBullets) {
            bullet.translateX(bullet.velocityX * deltaTime);
            bullet.translateY(bullet.velocityY * deltaTime);
            if(gameScreen.detectCollision(bullet.getX(), bullet.getY())){
                activeBullets.removeValue(bullet, true);
                continue;
            }
            bullet.update(deltaTime);
            //bullet.y += bullet.velocityY * deltaTime;
            // Check for screen bounds or other conditions to remove bullets
            for (Entity entity : gameScreen.getEntityManager().getEntities()) {
                if(bullet.isEnemyShoot() && entity.isEnemy() || (!bullet.isEnemyShoot() && !entity.isEnemy())) continue;
                //Rectangle bulletBounding = new Rectangle(bullet.getX(), bullet.getY(), bullet.getRegionWidth(), bullet.getRegionHeight());
                //Rectangle enemyBounding = new Rectangle(entity.getX(), entity.getY(), entity.getRegionWidth(), entity.getRegionHeight());
                Rectangle bulletBounding = bullet.getBoundingRectangle();
                Rectangle enemyBounding = entity.getBoundingRectangle();
                if (bulletBounding.overlaps(enemyBounding)) {
                    activeBullets.removeValue(bullet, true);
                    entity.hit(bullet,bullet.isEnemyShoot());
                }
            }
        }

        // Remove off-screen bullets

        /*for(Bullet b : activeBullets.iterator()){
            if(b.x > V_WIDTH || b.x < 0 || b.y > V_HEIGHT || b.y < 0){
                activeBullets.removeValue(b, true);
            }
        } */
        //activeBullets.removeAll() removeIf(bullet -> isBulletOffScreen(bullet));
    }

    public Array<Bullet> getActiveBullets() {
        return activeBullets;
    }
}