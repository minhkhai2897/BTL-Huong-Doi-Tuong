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
}
