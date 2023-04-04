package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.animation.BalloonAnimation;
import uet.oop.bomberman.graphics.Sprite;

public class Balloon extends MovingEntity {
    private BalloonAnimation balloonAnimation = new BalloonAnimation();
    public Balloon(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        this.move();
        this.balloonAnimation.setBalloonSprite(this);
    }

}
