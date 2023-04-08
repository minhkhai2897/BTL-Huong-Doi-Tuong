package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.MyMath;
import uet.oop.bomberman.animation.BalloonAnimation;

import java.util.ArrayList;
import java.util.List;

public class Balloon extends MovingEntity {

    private BalloonAnimation balloonAnimation = new BalloonAnimation();
    public Balloon(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        this.handleMove();
        this.move();
        this.balloonAnimation.setBalloonSprite(this);
        this.ableToMoveDown = true;
        this.ableToMoveLeft = true;
        this.ableToMoveRight = true;
        this.ableToMoveUp = true;
    }

    /**
     * Ham di chuyen ngau nhien cho enemy.
     *
     * Khong di chuyen truc tiep cac nhan vat. cac nhan vat duoc dieu khien bang cach dieu chinh
     * huong di (true/false): moveLeft (0), moveUp (1), moveDown (2), moveRight (3)
     * va cac bien (true/false): ableToMoveLeft,... nhan gia tri tu viec kiem tra thuc te vi tri cua nhan vat
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

        int randomNumber = MyMath.getRandomNumber(0, directions.size() - 1);
        if (directions.get(randomNumber) == 0) {
            moveLeft = true;
        } else if (directions.get(randomNumber) == 3) {
            moveRight = true;
        } else if (directions.get(randomNumber) == 1) {
            moveUp = true;
        } else if (directions.get(randomNumber) == 2) {
            moveDown = true;
        }
    }
}
