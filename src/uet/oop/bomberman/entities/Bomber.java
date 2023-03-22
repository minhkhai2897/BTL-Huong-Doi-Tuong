package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.animation.BomberAnimation;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends MovingEntity {
    private BomberAnimation bomberAnimation = new BomberAnimation();


    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        this.move();
        this.bomberAnimation.setBomberSprite(this);
    }

    /**
     * Xu ly khi cac phim duoc nhan, nha.
     * @param scene
     */
    public void handleKeyPress(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.W) {
                this.up = true;
            } else if (event.getCode() == KeyCode.S) {
                this.down = true;
            } else if (event.getCode() == KeyCode.A) {
                this.left = true;
            } else if (event.getCode() == KeyCode.D) {
                this.right = true;
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.W) {
                this.up = false;
            } else if (event.getCode() == KeyCode.S) {
                this.down = false;
            }
            if (event.getCode() == KeyCode.A) {
                this.left = false;
            } else if (event.getCode() == KeyCode.D) {
                this.right = false;
            }
        });
    }
}



/* import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;

public class ImageCollision {
    public static boolean checkCollision(ImageView img1, ImageView img2) {
        Rectangle2D rect1 = new Rectangle2D(img1.getX(), img1.getY(), img1.getBoundsInLocal().getWidth(), img1.getBoundsInLocal().getHeight());
        Rectangle2D rect2 = new Rectangle2D(img2.getX(), img2.getY(), img2.getBoundsInLocal().getWidth(), img2.getBoundsInLocal().getHeight());
        return rect1.intersects(rect2);
    }

    public static void main(String[] args) {
        Image image1 = new Image("path/to/image1.png");
        Image image2 = new Image("path/to/image2.png");

        ImageView imageView1 = new ImageView(image1);
        ImageView imageView2 = new ImageView(image2);

        // set positions of the image views
        imageView1.setX(100);
        imageView1.setY(100);
        imageView2.setX(200);
        imageView2.setY(200);

        // check collision
        boolean collision = checkCollision(imageView1, imageView2);
        System.out.println("Collision: " + collision);
    }
}
*/

/*
ImageView imageView = new ImageView(new Image("path/to/image.png"));
double x = 100;
double y = 200;
if (imageView.getBoundsInParent().contains(x, y)) {
    System.out.println("Point is inside the ImageView");
} else {
    System.out.println("Point is outside the ImageView");
}

 */

/*
ImageView imageView1 = new ImageView(new Image("path/to/image1.png"));
ImageView imageView2 = new ImageView(new Image("path/to/image2.png"));
if (imageView1.getBoundsInParent().intersects(imageView2.getBoundsInParent())) {
    System.out.println("Images intersect");
} else {
    System.out.println("Images do not intersect");
}

 */
