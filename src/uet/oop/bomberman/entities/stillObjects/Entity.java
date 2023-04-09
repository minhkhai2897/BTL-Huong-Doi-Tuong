package uet.oop.bomberman.entities.stillObjects;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.animation.Animation;
import uet.oop.bomberman.entities.bomber.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;


public abstract class Entity {
    protected int x;
    protected int y;
    protected Image img;
    protected Animation animation;
    protected int hp = 1;

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int xUnit) {
        this.x = xUnit * Sprite.SCALED_SIZE;
    }

    public void setY(int yUnit) {
        this.y = yUnit * Sprite.SCALED_SIZE;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public Animation getAnimation() {
        return animation;
    }

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    public abstract void update();

    public boolean intersects(Entity obj) {
        boolean left1, left2, right1, right2, up1, up2, down1, down2;

        if (this instanceof Bomber) {
            left1 = this.intersects(obj, this.getX() + 1, this.getY() + 4);
            left2 = this.intersects(obj, this.getX() + 1, this.getY() + this.getImg().getHeight() - 4);
            right1 = this.intersects(obj, this.getX() + this.getImg().getWidth() - 7 , this.getY() + 4);
            right2 = this.intersects(obj, this.getX() + this.getImg().getWidth() - 7, this.getY() + this. getImg().getHeight() - 4);
            up1 = this.intersects(obj, this.getX() + 4, this.getY() + 2);
            up2 = this.intersects(obj, this.getX() + this.getImg().getWidth() - 10, this .getY() + 2);
            down1 = this.intersects(obj, this.getX() + 4, this.getY() + this.getImg().getHeight() - 2);
            down2 = this.intersects(obj, this.getX() + this.getImg().getWidth() - 10, this .getY() + this.getImg().getHeight() - 2);
        } else {
            left1 = this.intersects(obj, this.getX() , this.getY() + 1);
            left2 = this.intersects(obj, this.getX() , this.getY() + this.getImg().getHeight() - 1);
            right1 = this.intersects(obj, this.getX() + this.getImg().getWidth() , this.getY() + 1);
            right2 = this.intersects(obj, this.getX() + this.getImg().getWidth(), this.getY() + this. getImg().getHeight() - 1);
            up1 = this.intersects(obj, this.getX() + 1, this.getY());
            up2 = this.intersects(obj, this.getX() + this.getImg().getWidth() - 1, this .getY());
            down1 = this.intersects(obj, this.getX() + 1, this.getY() + this.getImg().getHeight());
            down2 = this.intersects(obj, this.getX() + this.getImg().getWidth() - 1, this .getY() + this.getImg().getHeight());
        }

        return (left1 || left2 || right1 || right2 || up1 || up2 || down1 || down2);
    }

    /**
     * Ham kiem tra xem mot diem co nam trong doi tuong ko
     * @param entity doi tuong
     * @param x toa do x
     * @param y toa do y
     * @return true neu co, false neu ko
     */
    protected boolean intersects(Entity entity, double x, double y) {
        return (entity.getX() <= x && x <= (entity.getX() + entity.getImg().getWidth())
                && entity.getY() <= y && y <= (entity.getY() + entity.getImg().getHeight()));
    }

}
