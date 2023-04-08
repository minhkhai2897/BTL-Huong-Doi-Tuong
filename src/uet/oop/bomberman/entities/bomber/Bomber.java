package uet.oop.bomberman.entities.bomber;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.animation.BomberAnimation;
import uet.oop.bomberman.entities.enemies.MovingEntity;

public class Bomber extends MovingEntity {
    private boolean createBomb = false;

    public boolean isCreateBomb() {
        return createBomb;
    }

    public void setCreateBomb(boolean createBomb) {
        this.createBomb = createBomb;
    }

    private int bomb = 1;
    private int flame = 1;
    private BomberAnimation bomberAnimation = new BomberAnimation();


    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    public int getFlame() {
        return flame;
    }

    public void setFlame(int flame) {
        if (1 <= flame && flame <= 5) {
            this.flame = flame;
        }
    }

    public int getBomb() {
        return bomb;
    }

    public void setBomb(int bomb) {
        if (1 <= bomb && bomb <= 5) {
            this.bomb = bomb;
        }
    }



    @Override
    public void update() {
        this.move();
        this.bomberAnimation.setBomberSprite(this);
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
            if (event.getCode() == KeyCode.W) {
                this.moveUp = true;
            } else if (event.getCode() == KeyCode.S) {
                this.moveDown = true;
            } else if (event.getCode() == KeyCode.A) {
                this.moveLeft = true;
            } else if (event.getCode() == KeyCode.D) {
                this.moveRight = true;
            }
            if (event.getCode() == KeyCode.SPACE) {
                this.createBomb = true;
            }
        });

        scene.setOnKeyReleased(event -> {
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
            if (event.getCode() == KeyCode.SPACE) {
                this.createBomb = false;
            }
        });
    }
}
