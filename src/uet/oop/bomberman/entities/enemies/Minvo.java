package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.animation.MinvoAnimation;

public class Minvo extends Enemy {
    public void update() {
        this.ableToMoveLeft = true;
        this.ableToMoveRight = true;
        this.ableToMoveDown = true;
        this.ableToMoveUp = true;
        this.handleCollision();
        this.handleMove();
        this.move();
        this.animation.setSprite(this);
    }

    public Minvo(int x, int y, Image img) {
        super(x, y, img);
        this.wallPass = true;
        this.setSpeed(2);
        this.animation = new MinvoAnimation();
    }

    public void handleMove() {
        this.autoMoveToPlayer();
    }
}
