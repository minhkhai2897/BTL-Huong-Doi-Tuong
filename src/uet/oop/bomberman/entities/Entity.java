package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.animation.Animation;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;


public abstract class Entity {
    protected int x;
    protected int y;
    protected Image img;
    protected Animation animation;
    protected int hp = 1;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas

    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }
    public Entity(int xUnit, int yUnit) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        if (hp < 0) {
            hp = 0;
        }
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

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    public abstract void update();

    public boolean intersects(Entity obj) {
        return obj.intersects(this, obj.getX() + obj.getImg().getWidth() / 2 , obj.getY() + obj.getImg().getHeight() / 2);
    }

    /**
     * Ham kiem tra xem mot diem co nam trong doi tuong ko
     * @param entity doi tuong
     * @param x toa do x
     * @param y toa do y
     * @return true neu co, false neu ko
     */
    public static boolean intersects(Entity entity, double x, double y) {
        return (entity.getX() <= x && x <= (entity.getX() + entity.getImg().getWidth())
                && entity.getY() <= y && y <= (entity.getY() + entity.getImg().getHeight()));
    }

    public Point getPosition() {
        int x = (this.x + (int)this.img.getWidth() / 2) / Sprite.SCALED_SIZE;
        int y = (this.y + (int)this.img.getHeight() / 2) / Sprite.SCALED_SIZE - 2;
        return new Point(x, y);
    }
}
