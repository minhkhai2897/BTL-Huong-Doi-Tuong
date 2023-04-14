package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.MyMath;
import uet.oop.bomberman.animation.OnealAnimation;

public class Oneal extends Balloon {
    public Oneal(int x, int y, Image img) {
        super(x, y, img);
        this.setSpeed(2);
        animation = new OnealAnimation();
    }

    public void update() {
        this.speed = MyMath.getRandomNumber(1, 2);
        this.handleCollision();
        this.handleMove();
        this.move();
        this.animation.setSprite(this);
        this.ableToMoveDown = true;
        this.ableToMoveLeft = true;
        this.ableToMoveRight = true;
        this.ableToMoveUp = true;
    }
}
