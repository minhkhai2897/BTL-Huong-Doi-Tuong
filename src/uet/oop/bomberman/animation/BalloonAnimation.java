package uet.oop.bomberman.animation;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.enemies.Balloon;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;


public class BalloonAnimation extends Animation {
    private List<Image> left = new ArrayList<>();
    private List<Image> right = new ArrayList<>();
    private List<Image> dead = new ArrayList<>();

    /**
     * Nhap du lieu anh vao cac mang
     */
    public BalloonAnimation() {
        left.add(Sprite.balloom_left1.getFxImage());
        left.add(Sprite.balloom_left2.getFxImage());
        left.add(Sprite.balloom_left3.getFxImage());

        right.add(Sprite.balloom_right1.getFxImage());
        right.add(Sprite.balloom_right2.getFxImage());
        right.add(Sprite.balloom_right3.getFxImage());

        dead.add(Sprite.balloom_dead.getFxImage());
        dead.add(Sprite.mob_dead1.getFxImage());
        dead.add(Sprite.mob_dead2.getFxImage());
        dead.add(Sprite.mob_dead3.getFxImage());
    }

    /**
     * chia truong hop de chon loai hoat anh phu hop
     * @param balloon doi tuong can xu ly
     */
    public void setBalloonSprite(Balloon balloon) {
        if (balloon.getHp() <= 0) {
            balloon.setImg(this.handle(dead));
            return;
        }

        if (balloon.isMoveLeft()) {
            balloon.setImg(this.handle(left));
        } else if (balloon.isMoveRight()) {
            balloon.setImg(this.handle(right));
        } else {
            balloon.setImg(this.handle(right));
        }
    }
}


