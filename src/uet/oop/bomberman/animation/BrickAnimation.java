package uet.oop.bomberman.animation;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.stillObjects.Brick;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;
import java.util.List;

public class BrickAnimation extends Animation {
    private List<Image> dead = new ArrayList<>();

    /**
     * Nhap du lieu anh vao cac mang
     */
    public BrickAnimation() {
        dead.add(Sprite.brick_exploded.getFxImage());
        dead.add(Sprite.brick_exploded1.getFxImage());
        dead.add(Sprite.brick_exploded2.getFxImage());
    }

    /**
     * chia truong hop de chon loai hoat anh phu hop
     * @param brick doi tuong can xu ly
     */
    public void setBrickSprite(Brick brick) {
        if (brick.getHp() > 0) {
            return;
        }
        brick.setImg(this.handle(dead));
    }
}
