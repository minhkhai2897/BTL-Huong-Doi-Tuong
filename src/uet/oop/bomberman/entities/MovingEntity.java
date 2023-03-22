package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public abstract class MovingEntity extends Entity {
    protected int speed = 2;
    protected boolean left = false;
    protected boolean right = false;
    protected boolean up = false;
    protected boolean down = false;

    public MovingEntity(int x, int y, Image img) {
        super( x, y, img);
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        if (0 < speed && speed <= 3) {
            this.speed = speed;
        }
    }

    /**
     * di chuyen.
     */
    protected void move() {
        if (left) {
            this.x -= this.getSpeed();
        } else if (right) {
            this.x += this.getSpeed();
        } else if (up) {
            this.y -= this.getSpeed();
        } else if (down) {
            this.y += this.getSpeed();
        }
    }

    protected void checkCollision(Object obj) {

    }
}
