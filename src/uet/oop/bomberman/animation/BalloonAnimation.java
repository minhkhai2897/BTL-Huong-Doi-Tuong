package uet.oop.bomberman.animation;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.MovingEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;


public class BalloonAnimation extends Animation {
    private static List<Image> left = new ArrayList<>(Arrays.asList(Sprite.balloom_left1.getFxImage(),
            Sprite.balloom_left2.getFxImage(), Sprite.balloom_left3.getFxImage()));
    private static List<Image> right = new ArrayList<>(Arrays.asList(Sprite.balloom_right1.getFxImage(),
            Sprite.balloom_right2.getFxImage(), Sprite.balloom_right3.getFxImage()));
    private static List<Image> dead = new ArrayList<>(Arrays.asList(Sprite.balloom_dead.getFxImage(),
            Sprite.mob_dead1.getFxImage(), Sprite.mob_dead2.getFxImage(),
            Sprite.mob_dead3.getFxImage()));

    public BalloonAnimation() {
    }

    /**
     * chia truong hop de chon loai hoat anh phu hop
     *
     * @param entity doi tuong can xu ly
     */
    public void setSprite(Entity entity) {
        if (!(entity instanceof MovingEntity)) {
            return;
        }
        MovingEntity movingEntity = (MovingEntity) entity;
        if (movingEntity.getHp() <= 0) {
            this.numberOfFrames = this.numberOfDeadFrames;
            movingEntity.setImg(this.handle(dead, "dead"));

            this.countDeadFrames++;
            if (this.countDeadFrames == this.numberOfDeadFrames * (dead.size())) {
                this.finishDeadAnimation = true;
            }
            return;
        }

        if (movingEntity.isMoveLeft()) {
            movingEntity.setImg(this.handle(left, "left"));
        } else if (movingEntity.isMoveRight()) {
            movingEntity.setImg(this.handle(right, "right"));
        } else {
            movingEntity.setImg(this.handle(right, "right"));
        }
    }
}


