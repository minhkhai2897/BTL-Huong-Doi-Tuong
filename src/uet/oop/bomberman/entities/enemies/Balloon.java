package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.MyMath;
import uet.oop.bomberman.animation.BalloonAnimation;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.MovingEntity;
import uet.oop.bomberman.entities.bomber.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Balloon extends MovingEntity {
    protected boolean useSpecialMove = true;
    protected int actionCode = 0;
    public Balloon(int x, int y, Image img) {
        super(x, y, img);
        animation = new BalloonAnimation();
    }

    public void update() {
        this.handleCollision();
        this.handleMove();
        this.move();
        this.animation.setSprite(this);
        this.ableToMoveDown = true;
        this.ableToMoveLeft = true;
        this.ableToMoveRight = true;
        this.ableToMoveUp = true;
    }

    /**
     * Ham di chuyen ngau nhien cho enemy.
     *
     * Khong di chuyen truc tiep cac nhan vat. cac nhan vat duoc dieu khien bang cach dieu chinh
     * huong di (true/false): moveLeft (0), moveUp (1), moveDown (2), moveRight (3)
     * va cac bien (true/false): ableToMoveLeft,... nhan gia tri tu viec kiem tra thuc te vi tri cua nhan vat
     */
    protected void handleMove() {
        int last = this.getLastMove();
        this.randomDirectionMove(last);
    }

    protected void handleCollision() {
        List<Entity> bricks = BombermanGame.getBricks();
        if (!this.isWallPass()) {
            for (int j = 0; j < bricks.size(); j++) {
                this.checkObjectMovementAbility(bricks.get(j));
            }
        }

        List<Entity> bombs = BombermanGame.getBombs();
        for (int j = 0; j < bombs.size(); j++) {
            this.checkObjectMovementAbility(bombs.get(j));
        }

        List<Entity> walls = BombermanGame.getWalls();
        for (int j = 0; j < walls.size(); j++) {
            this.checkObjectMovementAbility(walls.get(j));
        }

        List<Entity> flames = BombermanGame.getFlames();
        for (int j = 0; j < flames.size(); j++) {
            if (this.intersects(flames.get(j))) {
                this.setHp(0);
            }
        }
    }

    protected void randomDirectionMove(int last) {
        this.resetMoveVariable();
        List<Integer> directions = new ArrayList<>();
        if (this.ableToMoveLeft) {
            directions.add(0);
        }
        if (this.ableToMoveRight) {
            directions.add(3);
        }
        if (this.ableToMoveUp) {
            directions.add(1);
        }
        if (this.ableToMoveDown) {
            directions.add(2);
        }

        if (directions.size() == 0) {
            return;
        }

        if (directions.size() > 1) {
            for (int i = 0; i < directions.size(); i++) {
                if (directions.get(i) + last == 3) {
                    directions.remove(i);
                    break;
                }
            }
        }

        int randomNumber = MyMath.getRandomNumber(0, directions.size() - 1);
        if (directions.get(randomNumber) == 0) {
            moveLeft = true;
        } else if (directions.get(randomNumber) == 3) {
            moveRight = true;
        } else if (directions.get(randomNumber) == 1) {
            moveUp = true;
        } else if (directions.get(randomNumber) == 2) {
            moveDown = true;
        }
    }

    /**
     * Left 0  Right 3  Up 1  Down 2.
     * Con lai la -1.
     * @return
     */
    protected int getLastMove() {
        int last = -1;

        if (moveLeft) {
            last = 0;
        } else if (moveRight) {
            last = 3;
        } else if (moveUp) {
            last = 1;
        }
        else if (moveDown) {
            last = 2;
        }

        return last;
    }

    public void autoMoveToPlayer() {
        // last: huong di chuyen cua lan di gan nhat (duoc chuyen thanh dang so de de xu ly)
        int last = this.getLastMove();

        // Khi tu dong di chuyen ve phia nguoi choi thi se co mot doan duong khong the di tiếp,
        // không thể đi sang hai bên va chi co the quay lai
        // phan nay de xu ly viec do
        if (this.useSpecialMove) {
            if ((last == 0 && ableToMoveLeft == false && ableToMoveUp == false && ableToMoveDown == false)
                    || (last == 3 && ableToMoveRight == false && ableToMoveUp == false && ableToMoveDown == false)
                    || (last == 1 && ableToMoveUp == false && ableToMoveLeft == false && ableToMoveRight == false)
                    || (last == 2 && ableToMoveDown == false && ableToMoveLeft == false && ableToMoveRight == false))
            {
                this.useSpecialMove = false;
            }
        }

        if (!this.useSpecialMove) {
            if (last == 0) {
                if (this.ableToMoveUp || this.ableToMoveDown) {
                    this.ableToMoveLeft = false;
                    this.useSpecialMove = true;
                }
            }
            else if (last == 3) {
                if (this.ableToMoveUp || this.ableToMoveDown) {
                    this.ableToMoveRight = false;
                    this.useSpecialMove = true;
                }
            }
            else if (last == 1) {
                if (this.ableToMoveLeft || this.ableToMoveRight) {
                    this.ableToMoveUp = false;
                    this.useSpecialMove = true;
                }
            }
            else if (last == 2) {
                if (this.ableToMoveLeft || this.ableToMoveRight) {
                    this.ableToMoveDown = false;
                    this.useSpecialMove = true;
                }
            }

            this.randomDirectionMove(last);
            return;
        }

        // Phan tu dong di chuyen ve phia nguoi choi
        List<Entity> bombers = BombermanGame.getBombers();
        if (bombers.size() > 0) {
            Bomber bomber = (Bomber) bombers.get(0);

            this.resetMoveVariable();
            int bomberX = ((bomber.getX() + (int)bomber.getImg().getWidth() / 2) / Sprite.SCALED_SIZE) * Sprite.SCALED_SIZE;
            int bomberY = ((bomber.getY() + (int)bomber.getImg().getHeight() / 2) / Sprite.SCALED_SIZE) * Sprite.SCALED_SIZE;
            int minvoX = this.getX();
            int minvoY = this.getY();

            if (actionCode % 4 == 0) {
                if (bomberX + Sprite.SCALED_SIZE < minvoX + this.img.getWidth()) {
                    if (this.ableToMoveLeft) {
                        this.moveLeft = true;
                    } else {
                        this.randomDirectionMove(last);
                    }
                }
                else {
                    actionCode = (actionCode + 1) % 4;
                }
            }
            if (actionCode % 4 == 1) {
                if (bomberY + Sprite.SCALED_SIZE < minvoY + this.img.getHeight()) {
                    if (this.ableToMoveUp) {
                        this.moveUp = true;
                    }
                    else {
                        this.randomDirectionMove(last);
                    }
                }
                else {
                    actionCode = (actionCode + 1) % 4;
                }
            }
            if (actionCode % 4 == 2) {
                if (bomberX > minvoX) {
                    if (this.ableToMoveRight) {
                        this.moveRight = true;
                    }
                    else {
                        this.randomDirectionMove(last);
                    }
                }
                else {
                    actionCode = (actionCode + 1) % 4;
                }
            }
            if (actionCode % 4 == 3){
                if (bomberY > minvoY) {
                    if (this.ableToMoveDown) {
                        this.moveDown = true;
                    }
                    else {
                        this.randomDirectionMove(last);
                    }
                }
                else {
                    actionCode = (actionCode + 1) % 4;
                }
            }
        }
    }

}
