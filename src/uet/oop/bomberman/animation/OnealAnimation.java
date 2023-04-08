package uet.oop.bomberman.animation;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.enemies.Oneal;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;
import java.util.List;

public class OnealAnimation extends Animation {
    private List<Image> left = new ArrayList<>();
    private List<Image> right = new ArrayList<>();
    private List<Image> dead = new ArrayList<>();

    /**
     * Nhap du lieu anh vao cac mang
     */
    public OnealAnimation() {
        left.add(Sprite.oneal_left1.getFxImage());
        left.add(Sprite.oneal_left2.getFxImage());
        left.add(Sprite.oneal_left3.getFxImage());

        right.add(Sprite.oneal_right1.getFxImage());
        right.add(Sprite.oneal_right2.getFxImage());
        right.add(Sprite.oneal_right3.getFxImage());

        dead.add(Sprite.oneal_dead.getFxImage());
        dead.add(Sprite.mob_dead1.getFxImage());
        dead.add(Sprite.mob_dead2.getFxImage());
        dead.add(Sprite.mob_dead3.getFxImage());
    }

    /**
     * chia truong hop de chon loai hoat anh phu hop
     * @param oneal doi tuong can xu ly
     */
    public void setOnealSprite(Oneal oneal) {
        if (oneal.getHp() <= 0) {
            oneal.setImg(this.handle(dead));
            return;
        }

        if (oneal.isMoveLeft()) {
            oneal.setImg(this.handle(left));
        } else if (oneal.isMoveRight()) {
            oneal.setImg(this.handle(right));
        } else {
            oneal.setImg(this.handle(left));
        }
    }
}

