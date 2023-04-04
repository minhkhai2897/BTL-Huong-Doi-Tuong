package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class Kondoria extends Balloon {
    public Kondoria(int x, int y, Image img) {
        super(x, y, img);
        this.wallPass = true;
    }
}
