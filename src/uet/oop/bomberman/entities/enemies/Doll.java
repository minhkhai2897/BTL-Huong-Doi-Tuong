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

public class Doll extends Minvo {
    protected boolean useSpecialMove = true;
    public Doll(int x, int y, Image img) {
        super(x, y, img);
        this.setSpeed(2);
//        this.wallPass = true;
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
        int last = this.getLastMove();
        if (this.useSpecialMove) {
            if ((last == 0 && ableToMoveLeft == false && ableToMoveUp == false && ableToMoveDown == false)
                    || (last == 3 && ableToMoveRight == false && ableToMoveUp == false && ableToMoveDown == false)
                    || (last == 1 && ableToMoveUp == false && ableToMoveLeft == false && ableToMoveRight == false)
                    || (last == 2 && ableToMoveDown == false && ableToMoveLeft == false && ableToMoveRight == false))
            {
                this.useSpecialMove = false;
            }
        }

        if (!this.useSpecialMove) {
            if (last == 0) {
                if (this.ableToMoveUp || this.ableToMoveDown) {
                    this.ableToMoveLeft = false;
                    this.useSpecialMove = true;
                }
            }
            else if (last == 3) {
                if (this.ableToMoveUp || this.ableToMoveDown) {
                    this.ableToMoveRight = false;
                    this.useSpecialMove = true;
                }
            }
            else if (last == 1) {
                if (this.ableToMoveLeft || this.ableToMoveRight) {
                    this.ableToMoveUp = false;
                    this.useSpecialMove = true;
                }
            }
            else if (last == 2) {
                if (this.ableToMoveLeft || this.ableToMoveRight) {
                    this.ableToMoveDown = false;
                    this.useSpecialMove = true;
                }
            }

            this.randomDirectionMove(last);
            return;
        }

        super.handleMove();
    }

}
