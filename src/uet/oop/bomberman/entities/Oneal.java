package uet.oop.bomberman.entities;


import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.animation.OnealAnimation;
import uet.oop.bomberman.graphics.Sprite;


public class Oneal extends MovingEntity {
    private OnealAnimation onealAnimation = new OnealAnimation();
    public Oneal(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        this.move();
        this.onealAnimation.setOnealSprite(this);

public class Oneal extends Balloon {
    public Oneal(int x, int y, Image img) {
        super(x, y, img);
        this.setSpeed(2);

    }
}
