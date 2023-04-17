package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.MyMath;
import uet.oop.bomberman.animation.OnealAnimation;

public class Oneal extends Enemy {
    public Oneal(int x, int y, Image img) {
        super(x, y, img);
        animation = new OnealAnimation();
    }

    public void update() {
        this.ableToMoveDown = true;
        this.ableToMoveLeft = true;
        this.ableToMoveRight = true;
        this.ableToMoveUp = true;
        this.setSpeed(MyMath.getRandomNumber(1, 2));
        this.handleCollision();
        this.handleMove();
        this.move();
        this.animation.setSprite(this);
    }

    public void handleMove() {
        int last = this.getLastMove();
        this.randomDirectionMove(last);
    }
}
