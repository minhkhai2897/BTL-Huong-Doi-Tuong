package uet.oop.bomberman.entities.bomber;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.animation.BomberAnimation;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.MovingEntity;
import uet.oop.bomberman.entities.stillObjects.Bomb;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public class Bomber extends MovingEntity {
    private int bomb = 1;
    private int flame = 1;
    private int createBomb = 0;
    private boolean bombAdded = false;

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
        this.animation = new BomberAnimation();
    }

    public boolean isBombAdded() {
        return bombAdded;
    }

    public void setBombAdded(boolean bombAdded) {
        this.bombAdded = bombAdded;
    }

    public int getFlame() {
        return flame;
    }

    public void setFlame(int flame) {
        if (1 <= flame && flame <= 3) {
            this.flame = flame;
        }
    }

    public int getBomb() {
        return bomb;
    }

    public void setBomb(int bomb) {
        if (1 <= bomb && bomb <= 4) {
            this.bomb = bomb;
        }
    }

    public void setCreateBomb(int createBomb) {
        if (createBomb > 100000) {
            createBomb = 10;
        }
        this.createBomb = createBomb;
    }

    public void update() {
        this.ableToMoveDown = true;
        this.ableToMoveLeft = true;
        this.ableToMoveRight = true;
        this.ableToMoveUp = true;
        this.handleKeyPress();
        this.handleCollision();
        this.move();
        this.animation.setSprite(this);
    }

    /**
     * Xu ly khi cac phim duoc nhan, nha.
     */
    public void handleKeyPress() {
        Scene scene = BombermanGame.getScene();
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.X) {
                this.setCreateBomb(createBomb + 1);
                if (createBomb == 1) {
                    this.addBomb();
                }
            }

            if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP) {
                this.moveUp = true;
            }
            if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN) {
                this.moveDown = true;
            }
            if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT) {
                this.moveLeft = true;
            }
            if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT) {
                this.moveRight = true;
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.X) {
                this.createBomb = 0;
            }

            if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP) {
                this.moveUp = false;
            }
            if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN) {
                this.moveDown = false;
            }
            if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT) {
                this.moveLeft = false;
            }
            if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT) {
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
            if (this.isBomberInCell(portals.get(i).getX(), portals.get(i).getY()) && enemies.size() == 0) {
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

    public boolean isBomberInCell(int cellX, int cellY) {
        if ((cellX + Sprite.SCALED_SIZE + 9 >= this.x + this.img.getWidth())
                && (cellY + Sprite.SCALED_SIZE + 4 >= this.y + this.img.getHeight())
                && (cellX <= this.x + 4)
                && (cellY <= this.y + 4)) {
            return true;
        }
        return false;
    }

    /**
     * Them bomb khi nhan duoc lenh tu ban phim va co du dieu kien.
     */
    public void addBomb() {
        if (this.getHp() < 1) {
            return;
        }

        List<Entity> bombs = BombermanGame.getBombs();
        List<Entity> bricks = BombermanGame.getBricks();
        List<Entity> portals = BombermanGame.getPortals();

        if (bombs.size() >= this.getBomb()) {
            return;
        }

        Bomb bomb = new Bomb((this.getX() + (int)(this.getImg().getWidth() / 2))/ Sprite.SCALED_SIZE,
                (this.getY() + (int)(this.getImg().getHeight() / 2)) / Sprite.SCALED_SIZE, Sprite.bomb.getFxImage());
        boolean add = true;
        for (int i = 0; i < bombs.size(); i++) {
            if (bombs.get(i).equals(bomb)) {
                add = false;
                break;
            }
        }
        for (int i = 0; i < portals.size(); i++) {
            if (portals.get(i).getX() == bomb.getX() && portals.get(i).getY() == bomb.getY()) {
                add = false;
                break;
            }
        }
        if (this.isWallPass() && add) {
            for (int i = 0; i < bricks.size(); i++) {
                if (bricks.get(i).getX() == bomb.getX() && bricks.get(i).getY() == bomb.getY()) {
                    add = false;
                    break;
                }
            }
        }
        if (add) {
            this.bombAdded = true;
            bombs.add(bomb);
        }
    }
}
