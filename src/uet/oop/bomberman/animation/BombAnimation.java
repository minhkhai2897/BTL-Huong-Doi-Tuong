package uet.oop.bomberman.animation;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.stillObjects.Bomb;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;
import java.util.List;

public class BombAnimation extends Animation {
    private List<Image> bombSprite = new ArrayList<>();

    /**
     * Nhap du lieu anh vao cac mang
     */
    public BombAnimation() {
        bombSprite.add(Sprite.bomb.getFxImage());
        bombSprite.add(Sprite.bomb_1.getFxImage());
        bombSprite.add(Sprite.bomb_2.getFxImage());
    }

    /**
     * chia truong hop de chon loai hoat anh phu hop
     * @param bomb doi tuong can xu ly
     */
    public void setBombSprite(Bomb bomb) {
        bomb.setImg(this.handle(bombSprite));
    }
}