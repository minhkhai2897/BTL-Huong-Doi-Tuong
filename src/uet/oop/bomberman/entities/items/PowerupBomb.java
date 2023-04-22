package uet.oop.bomberman.entities.items;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomber.Bomber;

import java.util.List;

public class PowerupBomb extends Entity {

    public PowerupBomb(int x, int y, Image img) {
        super(x, y, img);
    }

    public void update() {
        this.handleCollision();
    }

    private void handleCollision() {
        List<Entity> flames = BombermanGame.getFlames();
        for (int i = 0; i < flames.size(); i++) {
            if (flames.get(i).intersects(this)) {
                this.setHp(0);
                break;
            }
        }

        List<Entity> bombers = BombermanGame.getBombers();
        for (int i = 0; i < bombers.size(); i++) {
            Bomber bomber = (Bomber) bombers.get(i);
            if (bomber.isBomberInCell(this.x, this.y)) {
                bomber.setBomb(bomber.getBomb() + 1);
                this.setHp(0);
                break;
            }
        }
    }
}
