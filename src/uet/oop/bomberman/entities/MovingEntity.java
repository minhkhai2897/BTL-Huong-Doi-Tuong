package uet.oop.bomberman.entities;

import java.util.Random;


import javafx.scene.image.Image;

public abstract class MovingEntity extends Entity {
    protected int speed = 1;
    protected boolean moveLeft = false;
    protected boolean moveRight = false;
    protected boolean moveUp = false;
    protected boolean moveDown = false;

    protected boolean ableToMoveLeft = true;
    protected boolean ableToMoveRight = true;
    protected boolean ableToMoveUp = true;
    protected boolean ableToMoveDown = true;

    public boolean isAbleToMoveLeft() {
        return ableToMoveLeft;
    }

    public boolean isAbleToMoveRight() {
        return ableToMoveRight;
    }

    public boolean isAbleToMoveUp() {
        return ableToMoveUp;
    }

    public boolean isAbleToMoveDown() {
        return ableToMoveDown;
    }

    public MovingEntity(int x, int y, Image img) {
        super( x, y, img);
    }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    public boolean isMoveUp() {
        return moveUp;
    }

    public boolean isMoveDown() {
        return moveDown;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int  newSpeed) {
        if (0 < newSpeed && newSpeed <= 2) {
            this.speed = newSpeed;
        }
    }

    /**
     * di chuyen.
     */
    protected void move() {
        if (moveLeft && this.ableToMoveLeft) {
            this.x -= this.getSpeed();
        } else if (moveRight && this.ableToMoveRight) {
            this.x += this.getSpeed();
        } else if (moveUp && this.ableToMoveUp) {
            this.y -= this.getSpeed();
        } else if (moveDown && this.ableToMoveDown) {
            this.y += this.getSpeed();
        }
    }

    public void checkCollision(Entity obj) {
        if (obj instanceof MovingEntity) {
            if (this.intersects(this, obj)) {
                System.out.println("conga");
            }
        }
        else {
            this.intersects(this, obj);
        }
    }

    private boolean intersects(MovingEntity movingEntity, Entity obj) {
        boolean left1, left2, right1, right2, up1, up2, down1, down2;

        if (movingEntity instanceof Bomber) {
            left1 = movingEntity.intersects(obj, movingEntity.getX() + 1, movingEntity.getY() + 4);
            left2 = movingEntity.intersects(obj, movingEntity.getX() + 1, movingEntity.getY() + movingEntity.getImg().getHeight() - 4);
            right1 = movingEntity.intersects(obj, movingEntity.getX() + movingEntity.getImg().getWidth() - 7 , movingEntity.getY() + 4);
            right2 = movingEntity.intersects(obj, movingEntity.getX() + movingEntity.getImg().getWidth() - 7, movingEntity.getY() + movingEntity. getImg().getHeight() - 4);
            up1 = movingEntity.intersects(obj, movingEntity.getX() + 4, movingEntity.getY() + 2);
            up2 = movingEntity.intersects(obj, movingEntity.getX() + movingEntity.getImg().getWidth() - 10, movingEntity .getY() + 2);
            down1 = movingEntity.intersects(obj, movingEntity.getX() + 4, movingEntity.getY() + movingEntity.getImg().getHeight() - 2);
            down2 = movingEntity.intersects(obj, movingEntity.getX() + movingEntity.getImg().getWidth() - 10, movingEntity .getY() + movingEntity.getImg().getHeight() - 2);
        } else {
            left1 = movingEntity.intersects(obj, movingEntity.getX() , movingEntity.getY() + 3);
            left2 = movingEntity.intersects(obj, movingEntity.getX() , movingEntity.getY() + movingEntity.getImg().getHeight() - 3);
            right1 = movingEntity.intersects(obj, movingEntity.getX() + movingEntity.getImg().getWidth() , movingEntity.getY() + 3);
            right2 = movingEntity.intersects(obj, movingEntity.getX() + movingEntity.getImg().getWidth(), movingEntity.getY() + movingEntity. getImg().getHeight() - 3);
            up1 = movingEntity.intersects(obj, movingEntity.getX() + 3, movingEntity.getY());
            up2 = movingEntity.intersects(obj, movingEntity.getX() + movingEntity.getImg().getWidth() - 3, movingEntity .getY());
            down1 = movingEntity.intersects(obj, movingEntity.getX() + 3, movingEntity.getY() + movingEntity.getImg().getHeight());
            down2 = movingEntity.intersects(obj, movingEntity.getX() + movingEntity.getImg().getWidth() - 3, movingEntity .getY() + movingEntity.getImg().getHeight());
        }

        if (this.ableToMoveLeft) {
            if (left1 || left2) {
                this.ableToMoveLeft = false;
            }
        }
        if (this.ableToMoveRight) {
            if (right1 || right2) {
                this.ableToMoveRight = false;
            }
        }
        if (this.ableToMoveUp) {
            if (up1 || up2) {
                this.ableToMoveUp = false;
            }
        }
        if (this.ableToMoveDown) {
            if (down1 || down2) {
                this.ableToMoveDown = false;
            }
        }

        return (left1 || left2 || right1 || right2 || up1 || up2 || down1 || down2);
    }

    private boolean intersects(Entity entity, double x, double y) {
        return (entity.getX() <= x && x <= (entity.getX() + entity.img.getWidth())
                && entity.getY() <= y && y <= (entity.getY() + entity.img.getHeight()));
    }

    protected int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
