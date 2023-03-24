package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class Balloon extends MovingEntity {
    public Balloon(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        this.handle();
        this.move();
        this.ableToMoveDown = true;
        this.ableToMoveLeft = true;
        this.ableToMoveRight = true;
        this.ableToMoveUp = true;
    }

    private void handle() {
        int n = this.getRandomNumber(0, 50);
        if (n != 0) {
            if ((moveLeft && ableToMoveLeft) || (moveRight && ableToMoveRight)
                    || (moveUp && ableToMoveUp) || (moveDown && ableToMoveDown)) {
                return;
            }
        }

        moveLeft = false;
        moveRight = false;
        moveUp = false;
        moveDown = false;

        n = this.getRandomNumber(0, 3);
        if (n == 0) {
            this.moveLeft = true;
        } else if (n == 1) {
            this.moveRight = true;
        } else if (n == 2) {
            this.moveUp = true;
        } else if (n == 3) {
            this.moveDown = true;
        }
    }
}
