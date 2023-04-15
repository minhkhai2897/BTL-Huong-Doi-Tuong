package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.animation.MinvoAnimation;

public class Minvo extends Balloon {
    public Minvo(int x, int y, Image img) {
        super(x, y, img);
        this.wallPass = true;
        this.setSpeed(2);
        this.animation = new MinvoAnimation();
    }

    public void update() {
        this.handleCollision();
        this.handleMove();
        this.move();
        this.animation.setSprite(this);
        this.ableToMoveLeft = true;
        this.ableToMoveRight = true;
        this.ableToMoveDown = true;
        this.ableToMoveUp = true;
    }

    public void handleMove() {
        this.autoMoveToPlayer();
    }
}
