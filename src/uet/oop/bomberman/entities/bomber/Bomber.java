package uet.oop.bomberman.entities.bomber;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.animation.BomberAnimation;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.MovingEntity;
import uet.oop.bomberman.entities.stillObjects.Bomb;

import java.util.List;

public class Bomber extends MovingEntity {
    private int bomb = 1;
    private int flame = 1;
    private int createBomb = 0;

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
        this.animation = new BomberAnimation();
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
        this.handleKeyPress(BombermanGame.getScene());
        this.handleCollision();
        this.move();
        this.animation.setSprite(this);
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

            if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP) {
                this.moveUp = true;
            } else if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN) {
                this.moveDown = true;
            } else if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT) {
                this.moveLeft = true;
            } else if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT) {
                this.moveRight = true;
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                this.createBomb = 0;
            }

            if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP) {
                this.moveUp = false;
            } else if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN) {
                this.moveDown = false;
            }
            if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT) {
                this.moveLeft = false;
            } else if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT) {
                this.moveRight = false;
            }
        });
    }

    private void handleCollision() {
        List<Entity> flames = BombermanGame.getFlames();
        for (int i = 0; i < flames.size(); i++) {
            if (this.intersects(flames.get(i))) {
                this.setHp(0);
                break;
            }
        }

        List<Entity> enemies = BombermanGame.getEnemies();
        for (int i = 0; i < enemies.size(); i++) {
            if (this.intersects(enemies.get(i))) {
                this.setHp(0);
                break;
            }
        }

        List<Entity> bricks = BombermanGame.getBricks();
        if (!this.isWallPass()) {
            for (int i = 0; i < bricks.size(); i++) {
                this.checkObjectMovementAbility(bricks.get(i));
            }
        }

        List<Entity> bombs = BombermanGame.getBombs();
        for (int i = 0; i < bombs.size(); i++) {
            if (((Bomb)bombs.get(i)).isPassable()) {
                continue;
            }
            this.checkObjectMovementAbility(bombs.get(i));
        }

        List<Entity> walls = BombermanGame.getWalls();
        for (int i = 0; i < walls.size(); i++) {
            this.checkObjectMovementAbility(walls.get(i));
        }

        List<Entity> portals = BombermanGame.getPortals();
        for (int i = 0; i < portals.size(); i++) {
            if (this.intersects(portals.get(i)) && enemies.size() == 0) {
                boolean complete = true;
                for (int j = 0; j < bricks.size(); j++) {
                    if (portals.get(i).getX() == bricks.get(j).getX() &&
                            portals.get(i).getY() == bricks.get(j).getY()) {
                        complete = false;
                        break;
                    }
                }
                BombermanGame.setLevelComplete(complete);
            }
        }
    }
}
