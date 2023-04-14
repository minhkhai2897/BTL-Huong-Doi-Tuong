package uet.oop.bomberman.entities.stillObjects;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.animation.BombAnimation;
import uet.oop.bomberman.entities.bomber.Bomber;
import uet.oop.bomberman.entities.Entity;

import java.util.List;

public class Bomb extends Entity {
    private static final int time = 150;
    private int count = 0;
    private boolean isPassable = true;
    public Bomb(int x, int y, Image img) {
        super(x, y, img);
        animation = new BombAnimation();


    }

    public boolean isPassable() {
        return isPassable;
    }

    public void update() {
        this.bombTimer();
        this.animation.setSprite(this);
        this.handleCollision();
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

//    public void checkCharacterPassability(Bomber bomber) {
//        if (this.isPassable) {
//            if (!bomber.intersects(this)) {
//                this.isPassable = false;
//            }
//        }
//    }

    private void handleCollision() {
        List<Entity> flames = BombermanGame.getFlames();
        for (int j = 0; j < flames.size(); j++) {
            if (flames.get(j).intersects(this)) {
                this.setHp(0);
                break;
            }
        }

        List<Entity> bombers = BombermanGame.getBombers();
        for (int i = 0; i < bombers.size(); i++) {
            Bomber bomber = (Bomber) bombers.get(i);
            if (this.isPassable) {
                if (!bomber.intersects(this)) {
                    this.isPassable = false;
                }
            }
        }
    }
}
