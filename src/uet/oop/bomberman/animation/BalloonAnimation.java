package uet.oop.bomberman.animation;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.enemies.MovingEntity;
import uet.oop.bomberman.entities.stillObjects.Entity;
import uet.oop.bomberman.graphics.Sprite;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;


public class BalloonAnimation extends Animation {
    // Them tu khoa static ngay sau private.
    // Viec nay giup tranh phai ton nhieu bo nho khi khai bao 1 doi tuong moi.

    private static List<Image> left = new ArrayList<>(Arrays.asList(Sprite.balloom_left1.getFxImage(),
            Sprite.balloom_left2.getFxImage(), Sprite.balloom_left3.getFxImage()));
    private static List<Image> right = new ArrayList<>(Arrays.asList(Sprite.balloom_right1.getFxImage(),
            Sprite.balloom_right2.getFxImage(), Sprite.balloom_right3.getFxImage()));
    private static List<Image> dead = new ArrayList<>(Arrays.asList(Sprite.balloom_dead.getFxImage(),
            Sprite.mob_dead1.getFxImage(), Sprite.mob_dead2.getFxImage(),
            Sprite.mob_dead3.getFxImage()));

    /**
     * Nhap du lieu anh vao cac mang
     */
    public BalloonAnimation() {
        // Toan bo phan them anh se bi bo va chuyen len phia tren kia:
        // Cu: private List<Image> dead = new ArrayList<>();
        // Moi: private static List<Image> dead = new ArrayList<>(Arrays.asList(Sprite.balloom_dead.getFxImage(),...));
        // Chu y: chi sua doi nhung phan lien quan den them anh vao cac List sau thoi nhe


//        left.add(Sprite.balloom_left1.getFxImage());
//        left.add(Sprite.balloom_left2.getFxImage());
//        left.add(Sprite.balloom_left3.getFxImage());
//
//        right.add(Sprite.balloom_right1.getFxImage());
//        right.add(Sprite.balloom_right2.getFxImage());
//        right.add(Sprite.balloom_right3.getFxImage());
//
//        dead.add(Sprite.balloom_dead.getFxImage());
//        dead.add(Sprite.mob_dead1.getFxImage());
//        dead.add(Sprite.mob_dead2.getFxImage());
//        dead.add(Sprite.mob_dead3.getFxImage());
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


