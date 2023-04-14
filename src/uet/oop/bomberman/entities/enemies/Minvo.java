package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.MyMath;
import uet.oop.bomberman.animation.MinvoAnimation;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.MovingEntity;
import uet.oop.bomberman.entities.bomber.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Minvo extends Balloon {
    protected int k = 0;
    public Minvo(int x, int y, Image img) {
        super(x, y, img);
        this.wallPass = true;
        this.setSpeed(2);
        this.animation = new MinvoAnimation();
    }

    public void update() {
        this.handleCollision();
        this.handleMove();
        this.move();
        this.animation.setSprite(this);
        this.ableToMoveLeft = true;
        this.ableToMoveRight = true;
        this.ableToMoveDown = true;
        this.ableToMoveUp = true;
    }

    public void handleMove() {
        List<Entity> bombers = BombermanGame.getBombers();

        int last = this.getLastMove();
        this.resetMoveVariable();

        if (bombers.size() > 0) {
            Bomber bomber = (Bomber) bombers.get(0);
            int bomberX = ((bomber.getX() + (int)bomber.getImg().getWidth() / 2) / Sprite.SCALED_SIZE) * Sprite.SCALED_SIZE;
            int bomberY = ((bomber.getY() + (int)bomber.getImg().getHeight() / 2) / Sprite.SCALED_SIZE) * Sprite.SCALED_SIZE;
            int minvoX = this.getX();
            int minvoY = this.getY();

            if (k % 4 == 0) {
                if (bomberX + Sprite.SCALED_SIZE < minvoX + this.img.getWidth()) {
                    if (this.ableToMoveLeft) {
                        this.moveLeft = true;
                    } else {
                        this.randomDirectionMove(last);
                    }

                }
                else {
                    k = (k + 1) % 4;
                }
            }

            if (k % 4 == 1) {
                if (bomberY + Sprite.SCALED_SIZE < minvoY + this.img.getHeight()) {
                    if (this.ableToMoveUp) {
                        this.moveUp = true;
                    }
                    else {
                        this.randomDirectionMove(last);
                    }

                }
                else {
                    k = (k + 1) % 4;
                }
            }

            if (k % 4 == 2) {
                if (bomberX > minvoX) {
                    if (this.ableToMoveRight) {
                        this.moveRight = true;
                    }
                    else {
                        this.randomDirectionMove(last);
                    }

                }
                else {
                    k = (k + 1) % 4;
                }
            }

            if (k % 4 == 3){
                if (bomberY > minvoY) {
                    if (this.ableToMoveDown) {
                        this.moveDown = true;
                    }
                    else {
                        this.randomDirectionMove(last);
                    }

                }
                else {
                    k = (k + 1) % 4;
                }
            }
        }
    }

//    public void handleMove() {
//        int last = -1;
//
//        if (moveLeft) {
//            last = 0;
//            moveLeft = false;
//        } else if (moveRight) {
//            last = 3;
//            moveRight = false;
//        }
//
//        List<Integer> directions = new ArrayList<>();
//        int t;
//        if (this.ableToMoveLeft) {
//            directions.add(0);
//        }
//        if (this.ableToMoveRight) {
//            directions.add(3);
//        }
//
//        if (directions.size() == 0) {
//            return;
//        }
//
//        if (directions.size() > 1) {
//            for (int i = 0; i < directions.size(); i++) {
//                if (directions.get(i) + last == 3) {
//                    directions.remove(i);
//                    break;
//                }
//            }
//        }
//
//        int randomNumber = MyMath.getRandomNumber(0, directions.size() - 1);
//        if (directions.get(randomNumber) == 0) {
//            moveLeft = true;
//        } else if (directions.get(randomNumber) == 3) {
//            moveRight = true;
//        }
//    }
}
