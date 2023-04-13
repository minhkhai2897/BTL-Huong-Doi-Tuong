package uet.oop.bomberman.entities.bomber;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.animation.BomberAnimation;
import uet.oop.bomberman.entities.MovingEntity;

public class Bomber extends MovingEntity {
    private int bomb = 1;
    private int flame = 1;
    private int createBomb = 0;

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
        animation = new BomberAnimation();
    }

    public int getFlame() {
        return flame;
    }

    public void setFlame(int flame) {
        if (1 <= flame && flame <= 4) {
            this.flame = flame;
        }
    }

    public int getCreateBomb() {
        return createBomb;
    }

    public int getBomb() {
        return bomb;
    }

    public void setBomb(int bomb) {
        if (1 <= bomb && bomb <= 5) {
            this.bomb = bomb;
        }
    }

    public void update() {
        this.move();
        if ((this.moveLeft && this.ableToMoveLeft)
            || (this.moveRight && this.ableToMoveRight)
            || (this.moveUp && this.ableToMoveUp)
            || (this.moveDown && this.ableToMoveDown)
            || (this.hp <= 0))
        {
            this.animation.setSprite(this);
        }
        this.ableToMoveDown = true;
        this.ableToMoveLeft = true;
        this.ableToMoveRight = true;
        this.ableToMoveUp = true;
    }

    /**
     * Xu ly khi cac phim duoc nhan, nha.
     * @param scene
     */
    public void handleKeyPress(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                this.createBomb++;
                if (this.createBomb > 100000) {
                    this.createBomb = 10;
                }
            }

            if (event.getCode() == KeyCode.W) {
                this.moveUp = true;
            } else if (event.getCode() == KeyCode.S) {
                this.moveDown = true;
            } else if (event.getCode() == KeyCode.A) {
                this.moveLeft = true;
            } else if (event.getCode() == KeyCode.D) {
                this.moveRight = true;
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                this.createBomb = 0;
            }

            if (event.getCode() == KeyCode.W) {
                this.moveUp = false;
            } else if (event.getCode() == KeyCode.S) {
                this.moveDown = false;
            }
            if (event.getCode() == KeyCode.A) {
                this.moveLeft = false;
            } else if (event.getCode() == KeyCode.D) {
                this.moveRight = false;
            }
        });
    }
}
