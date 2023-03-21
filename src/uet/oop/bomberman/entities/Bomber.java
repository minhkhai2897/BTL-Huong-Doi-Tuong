package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.animation.BomberAnimation;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Entity {
    private BomberAnimation bomberAnimation = new BomberAnimation();
    private int speed = 2;
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;


    public Bomber(int x, int y, Image img) {
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

    @Override
    public void update() {
        this.move();
        this.bomberAnimation.setBomberSprite(this);
    }

    /**
     * Xu ly khi cac phim duoc nhan, nha.
     * @param scene
     */
    public void handleKeyPress(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.W) {
                this.up = true;
            } else if (event.getCode() == KeyCode.S) {
                this.down = true;
            } else if (event.getCode() == KeyCode.A) {
                this.left = true;
            } else if (event.getCode() == KeyCode.D) {
                this.right = true;
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.W) {
                this.up = false;
            } else if (event.getCode() == KeyCode.S) {
                this.down = false;
            }
            if (event.getCode() == KeyCode.A) {
                this.left = false;
            } else if (event.getCode() == KeyCode.D) {
                this.right = false;
            }
        });
    }

    /**
     * di chuyen.
     */
    private void move() {
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
}
