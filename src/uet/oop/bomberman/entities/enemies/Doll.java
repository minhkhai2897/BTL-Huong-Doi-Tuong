package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.animation.DollAnimation;

public class Doll extends Enemy {
    public Doll(int x, int y, Image img) {
        super(x, y, img);
        this.setSpeed(2);
        this.animation = new DollAnimation();
    }

    public void update() {
        this.ableToMoveDown = true;
        this.ableToMoveUp = true;
        this.ableToMoveLeft = true;
        this.ableToMoveRight = true;
        this.handleCollision();
        this.handleMove();
        this.move();
        this.animation.setSprite(this);
    }
    public void handleMove() {
        this.autoMoveToPlayer();
    }
}
