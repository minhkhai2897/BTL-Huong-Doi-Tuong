package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.MyMath;
import uet.oop.bomberman.animation.KondoriaAnimation;

public class Kondoria extends Enemy {
    public Kondoria(int x, int y, Image img) {
        super(x, y, img);
        this.wallPass = true;
        animation = new KondoriaAnimation();
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
