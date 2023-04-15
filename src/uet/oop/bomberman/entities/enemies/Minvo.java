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
    protected boolean useSpecialMove = true;
    protected int k = 0;
    public Minvo(int x, int y, Image img) {
        super(x, y, img);
//        this.wallPass = true;
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

        if (bombers.size() > 0) {
            Bomber bomber = (Bomber) bombers.get(0);

            int last = this.getLastMove();
            this.resetMoveVariable();
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
}
