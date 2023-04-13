package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.MyMath;
import uet.oop.bomberman.animation.DollAnimation;
import uet.oop.bomberman.entities.MovingEntity;

import java.util.ArrayList;
import java.util.List;

public class Doll extends MovingEntity {
    public Doll(int x, int y, Image img) {
        super(x, y, img);
        this.setSpeed(2);
         animation = new DollAnimation();
    }

    public void update() {
        this.handleMove();
        this.move();
//        this.animation.setSprite(this);
        this.ableToMoveDown = true;
        this.ableToMoveUp = true;
    }

    public void handleMove() {
        int last = -1;

        if (moveUp) {
            last = 1;
            moveUp = false;
        }
        else if (moveDown) {
            last = 2;
            moveDown = false;
        }

        List<Integer> directions = new ArrayList<>();
        int t;
        if (this.ableToMoveUp) {
            directions.add(1);
        }
        if (this.ableToMoveDown) {
            directions.add(2);
        }

        if (directions.size() == 0) {
            return;
        }

        if (directions.size() > 1) {
            for (int i = 0; i < directions.size(); i++) {
                if (directions.get(i) + last == 3) {
                    directions.remove(i);
                    break;
                }
            }
        }

        int randomNumber = MyMath.getRandomNumber(0, directions.size() - 1);
        if (directions.get(randomNumber) == 1) {
            moveUp = true;
        } else if (directions.get(randomNumber) == 2) {
            moveDown = true;
        }
    }
}
