package uet.oop.bomberman.entities.stillObjects;

import javafx.scene.image.Image;
import uet.oop.bomberman.animation.BrickAnimation;

public class Brick extends Entity {
    private BrickAnimation brickAnimation = new BrickAnimation();

    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        this.brickAnimation.setBrickSprite(this);
    }
}
