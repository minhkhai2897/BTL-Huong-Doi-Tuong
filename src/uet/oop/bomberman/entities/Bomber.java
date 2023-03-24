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

public class Bomber extends MovingEntity {
    private BomberAnimation bomberAnimation = new BomberAnimation();


    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        this.move();
        this.bomberAnimation.setBomberSprite(this);
        this.ableToMoveDown = true;
        this.ableToMoveLeft = true;
        this.ableToMoveRight = true;
        this.ableToMoveUp = true;
    }

    /**
     * Xu ly khi cac phim duoc nhan, nha.
     * @param scene
     */
    public void handleKeyPress(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.W) {
                this.moveUp = true;
            } else if (event.getCode() == KeyCode.S) {
                this.moveDown = true;
            } else if (event.getCode() == KeyCode.A) {
                this.moveLeft = true;
            } else if (event.getCode() == KeyCode.D) {
                this.moveRight = true;
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.W) {
                this.moveUp = false;
            } else if (event.getCode() == KeyCode.S) {
                this.moveDown = false;
            }
            if (event.getCode() == KeyCode.A) {
                this.moveLeft = false;
            } else if (event.getCode() == KeyCode.D) {
                this.moveRight = false;
            }
        });
    }
}
