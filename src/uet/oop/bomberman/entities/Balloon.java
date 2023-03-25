package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Balloon extends MovingEntity {
    public Balloon(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        this.handleMove();
        this.move();
        this.ableToMoveDown = true;
        this.ableToMoveLeft = true;
        this.ableToMoveRight = true;
        this.ableToMoveUp = true;
    }

    /**
     * 0: left
     * 1: up
     * 2: down
     * 3: right
     */
    protected void handleMove() {
        int last = -1;

        if (moveLeft) {
            last = 0;
            moveLeft = false;
        } else if (moveRight) {
            last = 3;
            moveRight = false;
        } else if (moveUp) {
            last = 1;
            moveUp = false;
        }
        else if (moveDown) {
            last = 2;
            moveDown = false;
        }

        List<Integer> directions = new ArrayList<>();
        int t;
        if (this.ableToMoveLeft) {
            directions.add(0);
        }
        if (this.ableToMoveRight) {
            directions.add(3);
        }
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

        int n = this.getRandomNumber(0, directions.size() - 1);
        if (directions.get(n) == 0) {
            moveLeft = true;
        } else if (directions.get(n) == 3) {
            moveRight = true;
        } else if (directions.get(n) == 1) {
            moveUp = true;
        } else if (directions.get(n) == 2) {
            moveDown = true;
        }
    }
}
