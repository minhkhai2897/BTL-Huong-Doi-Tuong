package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.MyMath;
import uet.oop.bomberman.animation.DollAnimation;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.MovingEntity;
import uet.oop.bomberman.entities.bomber.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import java.util.*;

public class Doll extends Balloon {
    private int k = 0;
    public Doll(int x, int y, Image img) {
        super(x, y, img);
        this.setSpeed(2);
        this.wallPass = true;
        this.animation = new DollAnimation();
    }

    public void update() {
        this.handleCollision();
        this.handleMove();
        this.move();
        this.animation.setSprite(this);
        this.ableToMoveDown = true;
        this.ableToMoveUp = true;
        this.ableToMoveLeft = true;
        this.ableToMoveRight = true;
    }

    public void handleMove() {
        List<Entity> bombers = BombermanGame.getBombers();

        if (bombers.size() > 0) {
            Bomber bomber = (Bomber) bombers.get(0);
            int bomberX = bomber.getX();
            int bomberY = bomber.getY();
            int dooX = this.getX();
            int dooY = this.getY();

            this.resetMoveVariable();

            if (k % 4 == 0) {
                if (bomberX < dooX) {
                    if (this.ableToMoveLeft) {
                        this.moveLeft = true;
                        return;
                    }
                    this.moveDown = true;
                }
                else {
                    k++;
                }
            }
            else if (k % 4 == 1) {
                if (bomberY < dooY) {
                    if (this.ableToMoveUp) {
                        this.moveUp = true;
                        return;
                    }
                    this.moveLeft = true;
                }
                else {
                    k++;
                }
            }
            else if (k % 4 == 2) {
                if (bomberX > dooX) {
                    if (this.ableToMoveRight) {
                        this.moveRight = true;
                        return;
                    }
                    this.moveUp = true;
                }
                else {
                    k++;
                }
            }
            else {
                if (bomberY > dooY) {
                    if (this.ableToMoveDown) {
                        this.moveDown = true;
                        return;
                    }
                    this.moveRight = true;
                }
                else {
                    k++;
                }
            }

        }
    }
}
