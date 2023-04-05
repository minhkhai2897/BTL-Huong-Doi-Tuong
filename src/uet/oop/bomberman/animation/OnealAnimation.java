package uet.oop.bomberman.animation;

import javafx.scene.image.ImageView;
import uet.oop.bomberman.entities.*;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class OnealAnimation extends Animation {
    private List<Image> left = new ArrayList<>();
    private List<Image> right = new ArrayList<>();
    private List<Image> dead = new ArrayList<>();

    public OnealAnimation() {
        left.add(Sprite.oneal_left1.getFxImage());
        left.add(Sprite.oneal_left2.getFxImage());
        left.add(Sprite.oneal_left3.getFxImage());

        right.add(Sprite.oneal_right1.getFxImage());
        right.add(Sprite.oneal_right2.getFxImage());
        right.add(Sprite.oneal_right3.getFxImage());

        dead.add(Sprite.oneal_dead.getFxImage());
    }

    public void setOnealSprite(Object obj) {
        if (obj instanceof Oneal) {
            Oneal oneal = (Oneal) obj;

            if (oneal.isMoveLeft()) {
                oneal.setImg(this.handle(left));
            } else if (oneal.isMoveRight()) {
                oneal.setImg(this.handle(right));
            } else {
                return;
            }
        }
    }
}

