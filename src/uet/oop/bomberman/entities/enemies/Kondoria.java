package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.animation.BalloonAnimation;
import uet.oop.bomberman.animation.KondoriaAnimation;
import uet.oop.bomberman.entities.enemies.Balloon;

public class Kondoria extends Balloon {
    public Kondoria(int x, int y, Image img) {
        super(x, y, img);
        this.wallPass = true;
        animation = new KondoriaAnimation();
    }

    public void update() {
        this.handleMove();
        this.move();
        this.animation.setSprite(this);
        this.ableToMoveDown = true;
        this.ableToMoveLeft = true;
        this.ableToMoveRight = true;
        this.ableToMoveUp = true;
    }
}
