package uet.oop.bomberman.entities.stillObjects;

import javafx.scene.image.Image;
import uet.oop.bomberman.animation.FlameAnimation;

public class Flame extends Entity {

    public Flame(int x, int y, Image img) {
        super(x, y, img);
        this.animation = new FlameAnimation();
    }

    public void update() {

    }

}
