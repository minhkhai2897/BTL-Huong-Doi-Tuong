package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.animation.KondoriaAnimation;
import uet.oop.bomberman.entities.enemies.Balloon;

public class Kondoria extends Balloon {
    private KondoriaAnimation kondoriaAnimation = new KondoriaAnimation();
    public Kondoria(int x, int y, Image img) {
        super(x, y, img);
        this.wallPass = true;
    }

    public void update() {
        this.handleMove();
        this.move();
        this.kondoriaAnimation.setKondoriaSprite(this);
        this.ableToMoveDown = true;
        this.ableToMoveLeft = true;
        this.ableToMoveRight = true;
        this.ableToMoveUp = true;
    }
}
