package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.bomber.Bomber;

public abstract class MovingEntity extends Entity {
    protected int speed = 1;
    protected boolean wallPass = false;
    protected boolean moveLeft = false;
    protected boolean moveRight = false;
    protected boolean moveUp = false;
    protected boolean moveDown = false;
    protected boolean ableToMoveLeft = true;
    protected boolean ableToMoveRight = true;
    protected boolean ableToMoveUp = true;
    protected boolean ableToMoveDown = true;

    public MovingEntity(int x, int y, Image img) {
        super( x, y, img);
    }

    public boolean isWallPass() {
        return wallPass;
    }

    public void setWallPass(boolean wallPass) {
        this.wallPass = wallPass;
    }

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
        if (this.hp <= 0) {
            return;
        }
        if (moveLeft && this.ableToMoveLeft) {
            this.x -= this.getSpeed();
        }
        if (moveRight && this.ableToMoveRight) {
            this.x += this.getSpeed();
        }
        if (moveUp && this.ableToMoveUp) {
            this.y -= this.getSpeed();
        }
        if (moveDown && this.ableToMoveDown) {
            this.y += this.getSpeed();
        }
    }

    /**
     * Ham kiem tra va cham giua 2 doi tuong.
     * @param obj doi tuong bat ki
     * @return neu va cham tra ve true, con lai tra ve false
     */


    /**
     * Kiem tra xem mot nhan vat co duoc di sang trai, sang phai, ... hay khong
     * @param obj
     */
    public void checkObjectMovementAbility(Entity obj) {
        boolean left1, left2, right1, right2, up1, up2, down1, down2;

        if (this instanceof Bomber) {
            left1 = this.intersects(obj, this.getX() + 2, this.getY() + 5);
            left2 = this.intersects(obj, this.getX() + 2, this.getY() + this.getImg().getHeight() - 5);
            right1 = this.intersects(obj, this.getX() + this.getImg().getWidth() - 8 , this.getY() + 5);
            right2 = this.intersects(obj, this.getX() + this.getImg().getWidth() - 8, this.getY() + this. getImg().getHeight() - 5);
            up1 = this.intersects(obj, this.getX() + 5, this.getY() + 3);
            up2 = this.intersects(obj, this.getX() + this.getImg().getWidth() - 11, this .getY() + 3);
            down1 = this.intersects(obj, this.getX() + 5, this.getY() + this.getImg().getHeight() - 3);
            down2 = this.intersects(obj, this.getX() + this.getImg().getWidth() - 11, this .getY() + this.getImg().getHeight() - 3);
        } else {
            left1 = this.intersects(obj, this.getX() , this.getY() + 1);
            left2 = this.intersects(obj, this.getX() , this.getY() + this.getImg().getHeight() - 1);
            right1 = this.intersects(obj, this.getX() + this.getImg().getWidth() , this.getY() + 1);
            right2 = this.intersects(obj, this.getX() + this.getImg().getWidth(), this.getY() + this. getImg().getHeight() - 1);
            up1 = this.intersects(obj, this.getX() + 1, this.getY());
            up2 = this.intersects(obj, this.getX() + this.getImg().getWidth() - 1, this .getY());
            down1 = this.intersects(obj, this.getX() + 1, this.getY() + this.getImg().getHeight());
            down2 = this.intersects(obj, this.getX() + this.getImg().getWidth() - 1, this .getY() + this.getImg().getHeight());
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
    }

    protected void resetMoveVariable() {
        this.moveLeft = false;
        this.moveRight = false;
        this.moveUp = false;
        this.moveDown = false;
    }
}
