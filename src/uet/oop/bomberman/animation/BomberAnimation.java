package uet.oop.bomberman.animation;

import javafx.scene.image.ImageView;
import uet.oop.bomberman.entities.*;
import javafx.scene.image.Image;
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

    /**
     * Ham dat trang thai cho nhan vat.
     *
     * @param obj có phan kiem tra class cua obj
     *            neu cung class thi moi thuc hien
     */
    public void setBomberSprite(Object obj) {
        if (obj instanceof Bomber) {
            Bomber bomber = (Bomber) obj;

            if (bomber.isLeft()) {
                bomber.setImg(this.handle(left));
            } else if (bomber.isRight()) {
                bomber.setImg(this.handle(right));
            } else if (bomber.isUp()) {
                bomber.setImg(this.handle(up));
            } else if (bomber.isDown()) {
                bomber.setImg(this.handle(down));
            } else {
                bomber.setImg(Sprite.player_down.getFxImage());
            }
            ImageView imageView = new ImageView(bomber.getImg());
            bomber.setImageView(imageView);
        }
    }
}
