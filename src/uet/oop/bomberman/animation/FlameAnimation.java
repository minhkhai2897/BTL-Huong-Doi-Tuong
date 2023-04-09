package uet.oop.bomberman.animation;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import uet.oop.bomberman.entities.stillObjects.Entity;

public class FlameAnimation extends Animation {
    private static List<Image> epicenter = new ArrayList<>(Arrays.asList());
    private static List<Image> horizontal = new ArrayList<>(Arrays.asList(Sprite.explosion_horizontal.getFxImage()
    ,Sprite.explosion_horizontal1.getFxImage(),Sprite.explosion_horizontal2.getFxImage()));
    private static List<Image> horizontalLeftLast = new ArrayList<>(Arrays.asList(Sprite.explosion_horizontal_left_last.getFxImage()
    ,Sprite.explosion_horizontal_left_last1.getFxImage(),Sprite.explosion_horizontal_left_last2.getFxImage()));
    private static List<Image> horizontalRightLast = new ArrayList<>(Arrays.asList(Sprite.explosion_horizontal_right_last.getFxImage()
    ,Sprite.explosion_horizontal_right_last1.getFxImage(),Sprite.explosion_horizontal_right_last2.getFxImage()));
    private static List<Image> vertical = new ArrayList<>(Arrays.asList(Sprite.explosion_vertical.getFxImage()
    ,Sprite.explosion_vertical1.getFxImage(),Sprite.explosion_vertical2.getFxImage()));
    private static List<Image> verticalUpLast = new ArrayList<>(Arrays.asList(Sprite.explosion_vertical_top_last.getFxImage()
    ,Sprite.explosion_vertical_top_last1.getFxImage(),Sprite.explosion_vertical_top_last2.getFxImage()));
    private static List<Image> VerticalDownLast = new ArrayList<>(Arrays.asList(Sprite.explosion_vertical_down_last.getFxImage()
    ,Sprite.explosion_vertical_down_last1.getFxImage(),Sprite.explosion_vertical_down_last2.getFxImage()));


    public FlameAnimation() {

    }

    public void setSprite(Entity entity) {

    }


}
