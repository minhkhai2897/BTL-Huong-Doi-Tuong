package uet.oop.bomberman.animation;

import javafx.scene.image.ImageView;
import uet.oop.bomberman.entities.*;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;


public class BalloonAnimation extends Animation {
    private List<Image> left = new ArrayList<>();
    private List<Image> right = new ArrayList<>();
    private List<Image> dead = new ArrayList<>();

    public BalloonAnimation() {
        left.add(Sprite.balloom_left1.getFxImage());
        left.add(Sprite.balloom_left2.getFxImage());
        left.add(Sprite.balloom_left3.getFxImage());


        right.add(Sprite.balloom_right1.getFxImage());
        right.add(Sprite.balloom_right2.getFxImage());
        right.add(Sprite.balloom_right3.getFxImage());

        dead.add(Sprite.balloom_dead.getFxImage());
    }

    public void setBalloonSprite(Object obj) {
        if (obj instanceof Balloon) {
            Balloon balloon = (Balloon) obj;

            if (balloon.isMoveLeft()) {
                balloon.setImg(this.handle(left));
            } else if (balloon.isMoveRight()) {
                balloon.setImg(this.handle(right));
            } else {
                balloon.setImg(this.handle(right));
            }
        }
    }
}


