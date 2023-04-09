package uet.oop.bomberman.animation;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;
import java.util.List;
import uet.oop.bomberman.entities.stillObjects.Entity;

public class FlameAnimation extends Animation {
    private List<Image> epicenter = new ArrayList<>();
    private List<Image> horizontal = new ArrayList<>();
    private List<Image> horizontalLeftLast = new ArrayList<>();
    private List<Image> horizontalRightLast = new ArrayList<>();
    private List<Image> vertical = new ArrayList<>();
    private List<Image> verticalUpLast = new ArrayList<>();
    private List<Image> VerticalDownLast = new ArrayList<>();


    public FlameAnimation() {
        // them anh vao list...
    }

    public void setSprite(Entity entity) {

    }


}
