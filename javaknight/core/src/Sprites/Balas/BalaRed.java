package Sprites.Balas;

public class BalaRed {

    public float x, y, directionX, directionY,damage;
    public boolean enemy;

    public BalaRed(float x, float y, float directionX, float directionY, float damage, boolean enemy) {
        this.x = x;
        this.y = y;
        this.directionX = directionX;
        this.directionY = directionY;
        this.enemy = enemy;
        this.damage = damage;
    }

}
