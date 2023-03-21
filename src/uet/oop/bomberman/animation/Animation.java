package uet.oop.bomberman.animation;

import javafx.scene.image.Image;

import java.util.List;

public class Animation {
    private int count = 0;
    private final int numberOfFrames = 8;

    protected Image handle(List<Image> list) {
        Image img = (list.get(count / numberOfFrames));
        count++;
        if (count >= numberOfFrames * list.size()) {
            count = 0;
        }
        return img;
    }
}
