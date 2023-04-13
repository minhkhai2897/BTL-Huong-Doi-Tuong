package uet.oop.bomberman.entities.stillObjects;

import javafx.scene.image.Image;
import uet.oop.bomberman.animation.BrickAnimation;
import uet.oop.bomberman.entities.Entity;

public class Brick extends Entity {
    public Brick(int x, int y, Image img) {
        super(x, y, img);
        animation = new BrickAnimation();
    }

    public void update() {
        this.animation.setSprite(this);
    }
}
