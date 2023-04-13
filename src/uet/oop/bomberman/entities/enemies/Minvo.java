package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.MyMath;
import uet.oop.bomberman.animation.MinvoAnimation;
import uet.oop.bomberman.entities.MovingEntity;

import java.util.ArrayList;
import java.util.List;

public class Minvo extends MovingEntity {
    public Minvo(int x, int y, Image img) {
        super(x, y, img);
        this.setSpeed(2);
         animation = new MinvoAnimation();
    }

    public void update() {
        this.handleMove();
        this.move();
        this.animation.setSprite(this);
        this.ableToMoveLeft = true;
        this.ableToMoveRight = true;
    }

    public void handleMove() {
        int last = -1;

        if (moveLeft) {
            last = 0;
            moveLeft = false;
        } else if (moveRight) {
            last = 3;
            moveRight = false;
        }

        List<Integer> directions = new ArrayList<>();
        int t;
        if (this.ableToMoveLeft) {
            directions.add(0);
        }
        if (this.ableToMoveRight) {
            directions.add(3);
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
        if (directions.get(randomNumber) == 0) {
            moveLeft = true;
        } else if (directions.get(randomNumber) == 3) {
            moveRight = true;
        }
    }
}
