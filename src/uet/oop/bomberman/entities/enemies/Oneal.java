package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.animation.OnealAnimation;
import uet.oop.bomberman.entities.enemies.Balloon;

public class Oneal extends Balloon {

    private OnealAnimation onealAnimation = new OnealAnimation();
    public Oneal(int x, int y, Image img) {
        super(x, y, img);
        this.setSpeed(2);
    }

    public void update() {
        this.handleMove();
        this.move();
        this.onealAnimation.setOnealSprite(this);
        this.ableToMoveDown = true;
        this.ableToMoveLeft = true;
        this.ableToMoveRight = true;
        this.ableToMoveUp = true;
    }
}
