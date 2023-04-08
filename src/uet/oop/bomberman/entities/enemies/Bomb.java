package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.animation.BombAnimation;
import uet.oop.bomberman.entities.bomber.Bomber;
import uet.oop.bomberman.entities.stillObjects.Entity;

public class Bomb extends Entity {
    private boolean isPassable = true;
    private BombAnimation bombAnimation = new BombAnimation();
    private static final int time = 120;
    private int count = 0;
    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    public boolean isPassable() {
        return isPassable;
    }

    @Override
    public void update() {
        this.bombTimer();
        this.bombAnimation.setBombSprite(this);
    }

    public boolean equals(Object object) {
        if ((object instanceof Bomb)) {
            Bomb bomb = (Bomb) object;
            if (this.x == bomb.getX() && this.y == bomb.getY()) {
                return true;
            }
        }
        return false;
    }

    public void bombTimer() {
        // time: thoi gian ton tai cua bomb
        count++;
        if (count > time) {
            this.hp = 0;
        }
    }

    public void checkCharacterPassability(Bomber bomber) {
        if (this.isPassable) {
            if (!bomber.intersects(this)) {
                this.isPassable = false;
            }
        }
    }
}
