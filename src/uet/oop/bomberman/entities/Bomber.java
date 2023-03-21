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
}
