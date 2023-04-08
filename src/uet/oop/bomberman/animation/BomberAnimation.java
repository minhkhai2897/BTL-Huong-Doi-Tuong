package uet.oop.bomberman.animation;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.bomber.Bomber;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;
import java.util.List;

public class BomberAnimation extends Animation{

    /**
     * cac list là cac trang thai
     */
    private List<Image> left = new ArrayList<>();
    private List<Image> right = new ArrayList<>();
    private List<Image> down = new ArrayList<>();
    private List<Image> up = new ArrayList<>();
    private List<Image> dead = new ArrayList<>();

    /**
     * Khoi tao va nhap du lieu cho cac list.
     */
    public BomberAnimation() {
        left.add(Sprite.player_left.getFxImage());
        left.add(Sprite.player_left_1.getFxImage());
        left.add(Sprite.player_left_2.getFxImage());

        right.add(Sprite.player_right.getFxImage());
        right.add(Sprite.player_right_1.getFxImage());
        right.add(Sprite.player_right_2.getFxImage());

        up.add(Sprite.player_up.getFxImage());
        up.add(Sprite.player_up_1.getFxImage());
        up.add(Sprite.player_up_2.getFxImage());

        down.add(Sprite.player_down.getFxImage());
        down.add(Sprite.player_down_1.getFxImage());
        down.add(Sprite.player_down_2.getFxImage());

        dead.add(Sprite.player_dead1.getFxImage());
        dead.add(Sprite.player_dead2.getFxImage());
        dead.add(Sprite.player_dead3.getFxImage());
    }

    public void setBomberSprite(Bomber bomber) {
        if (bomber.getHp() <= 0) {
            bomber.setImg(this.handle(dead));
            return;
        }

        if (bomber.isMoveLeft()) {
            bomber.setImg(this.handle(left));
        } else if (bomber.isMoveRight()) {
            bomber.setImg(this.handle(right));
        } else if (bomber.isMoveUp()) {
            bomber.setImg(this.handle(up));
        } else if (bomber.isMoveDown()) {
            bomber.setImg(this.handle(down));
        } else {
            return;
        }
    }
}
