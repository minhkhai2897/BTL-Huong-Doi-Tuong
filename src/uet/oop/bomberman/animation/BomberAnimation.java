package uet.oop.bomberman.animation;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.enemies.MovingEntity;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;
import java.util.List;
import uet.oop.bomberman.entities.stillObjects.Entity;


public class BomberAnimation extends Animation{

    /**
     * cac list là cac trang thai
     */
    private List<Image> left = new ArrayList<>();
    private List<Image> right = new ArrayList<>();
    private List<Image> down = new ArrayList<>();
    private List<Image> up = new ArrayList<>();
    private List<Image> dead = new ArrayList<>();

    /**
     * Nhap du lieu anh vao cac mang
     */
    public BomberAnimation() {
        left.add(Sprite.player_left.getFxImage());
        left.add(Sprite.player_left_1.getFxImage());
        left.add(Sprite.player_left_2.getFxImage());

        right.add(Sprite.player_right.getFxImage());
        right.add(Sprite.player_right_1.getFxImage());
        right.add(Sprite.player_right_2.getFxImage());

        up.add(Sprite.player_up.getFxImage());
        up.add(Sprite.player_up_1.getFxImage());
        up.add(Sprite.player_up_2.getFxImage());

        down.add(Sprite.player_down.getFxImage());
        down.add(Sprite.player_down_1.getFxImage());
        down.add(Sprite.player_down_2.getFxImage());

        dead.add(Sprite.player_dead1.getFxImage());
        dead.add(Sprite.player_dead2.getFxImage());
        dead.add(Sprite.player_dead3.getFxImage());
    }

    /**
     * chia truong hop de chon loai hoat anh phu hop
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
            if (this.countDeadFrames == this.numberOfDeadFrames * (this.dead.size())) {
                this.finishDeadAnimation = true;
            }
            return;
        }

        if (movingEntity.isMoveLeft()) {
            movingEntity.setImg(this.handle(left, "left"));
        } else if (movingEntity.isMoveRight()) {
            movingEntity.setImg(this.handle(right, "right"));
        } else if (movingEntity.isMoveUp()) {
            movingEntity.setImg(this.handle(up, "up"));
        } else if (movingEntity.isMoveDown()) {
            movingEntity.setImg(this.handle(down, "down"));
        }
    }
}
