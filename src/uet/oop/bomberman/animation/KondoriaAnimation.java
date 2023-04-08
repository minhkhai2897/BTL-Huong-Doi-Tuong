package uet.oop.bomberman.animation;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.enemies.Kondoria;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;
import java.util.List;

public class KondoriaAnimation extends Animation {
    private List<Image> left = new ArrayList<>();
    private List<Image> right = new ArrayList<>();
    private List<Image> dead = new ArrayList<>();

    public KondoriaAnimation() {
        left.add(Sprite.kondoria_left1.getFxImage());
        left.add(Sprite.kondoria_left2.getFxImage());
        left.add(Sprite.kondoria_left3.getFxImage());

        right.add(Sprite.kondoria_right1.getFxImage());
        right.add(Sprite.kondoria_right2.getFxImage());
        right.add(Sprite.kondoria_right3.getFxImage());

        dead.add(Sprite.kondoria_dead.getFxImage());
        dead.add(Sprite.mob_dead1.getFxImage());
        dead.add(Sprite.mob_dead2.getFxImage());
        dead.add(Sprite.mob_dead3.getFxImage());

    }

    public void setKondoriaSprite(Kondoria kondoria) {
        if (kondoria.getHp() <= 0) {
            kondoria.setImg(this.handle(dead));
            return;
        }

        if (kondoria.isMoveLeft()) {
            kondoria.setImg(this.handle(left));
        } else if (kondoria.isMoveRight()) {
            kondoria.setImg(this.handle(right));
        } else {
            kondoria.setImg(this.handle(right));
        }
    }
}

