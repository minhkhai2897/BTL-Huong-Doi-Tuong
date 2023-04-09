package uet.oop.bomberman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.bomber.Bomber;
import uet.oop.bomberman.entities.enemies.*;
import uet.oop.bomberman.entities.items.PowerupBomb;
import uet.oop.bomberman.entities.items.PowerupFlame;
import uet.oop.bomberman.entities.items.PowerupSpeed;
import uet.oop.bomberman.entities.items.PowerupWallPass;
import uet.oop.bomberman.entities.stillObjects.*;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    private Group root;
    private Scene scene;
    
    public static int width;
    public static int height;
    
    private GraphicsContext gc;
    private Canvas canvas;
    private List<String> map = new ArrayList<>();
    private List<Entity> grasses = new ArrayList<>();
    private List<Entity> enemies = new ArrayList<>();
    private List<Entity> items = new ArrayList<>();
    private List<Entity> walls = new ArrayList<>();
    private List<Entity> bricks = new ArrayList<>();
    private List<Entity> portals = new ArrayList<>();
    private List<Entity> bombers = new ArrayList<>();
    private List<Entity> bombs = new ArrayList<>();
    private List<Entity> deads = new ArrayList<>();

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    public void start(Stage stage) {
        readDataFromFile();

        canvas = new Canvas(Sprite.SCALED_SIZE * width, Sprite.SCALED_SIZE * height);
        gc = canvas.getGraphicsContext2D();
        root = new Group();
        root.getChildren().add(canvas);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

        createMap();
    }

    /**
     * tao map tu du lieu trong list map
     */
    public void createMap() {
        for (int i = 0; i < height; i++) {
            String s = map.get(i);
            for (int j = 0; j < width; j++) {
                Entity object = new Grass(j, i, Sprite.grass.getFxImage());
                grasses.add(object);
                char c = s.charAt(j);

                if (c == 'p') {
                    if (bombers.size() != 0) {
                        bombers.get(0).setX(j);
                        bombers.get(0).setY(i);
                    }
                    else {
                        object = new Bomber(j, i, Sprite.player_down.getFxImage());
                        bombers.add(object);
                    }
                }
                else if ('0' <= c && c <= '9') {
                    if (c == '1') {
                        object = new Balloon(j, i, Sprite.balloom_left1.getFxImage());
                    }
                    else if (c == '2') {
                        object = new Oneal(j, i, Sprite.oneal_right1.getFxImage());
                    } else if (c == '3') {
                        object = new Kondoria(j, i, Sprite.kondoria_left1.getFxImage());
                    } else {
                        object = new Balloon(j, i, Sprite.balloom_left1.getFxImage());
                    }
                    enemies.add(object);
                }
                else {
                    if (c == '#') {
                        object = new Wall(j, i, Sprite.wall.getFxImage());
                        walls.add(object);
                    } else if (c == '*') {
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        bricks.add(object);
                        // Them item ngau nhien
                        this.addRandomItem(j, i);

                    } else if (c == 'x') {
                        object = new Portal(j, i, Sprite.portal.getFxImage());
                        portals.add(object);
                    } else if (c == 'b') {
                        object = new PowerupBomb(j, i, Sprite.powerup_bombs.getFxImage());
                        items.add(object);
                    } else if (c == 'f') {
                        object = new PowerupFlame(j, i, Sprite.powerup_flames.getFxImage());
                        items.add(object);
                    } else if (c == 's') {
                        object = new PowerupSpeed(j, i, Sprite.powerup_speed.getFxImage());
                        items.add(object);
                    }   else if (c == 'w') {
                        object = new PowerupWallPass(j, i, Sprite.powerup_wallpass.getFxImage());
                        items.add(object);
                    }
                }
            }
        }
        int randomNumber = MyMath.getRandomNumber(0, bricks.size() - 1);
        Entity portal = new Portal(bricks.get(randomNumber).getX() / Sprite.SCALED_SIZE,
                bricks.get(randomNumber).getY() / Sprite.SCALED_SIZE, Sprite.portal.getFxImage());
        portals.add(portal);
    }

    public void addRandomItem(int x, int y) {
        Entity object;
        int randomNumber = MyMath.getRandomNumber(0, 99);
        if (randomNumber < 3) {
            object = new PowerupWallPass(x, y, Sprite.powerup_wallpass.getFxImage());
            items.add(object);
        }
        else if (randomNumber < 6) {
            object = new PowerupSpeed(x, y, Sprite.powerup_speed.getFxImage());
            items.add(object);
        }
        else if (randomNumber < 9) {
            object = new PowerupBomb(x, y, Sprite.powerup_bombs.getFxImage());
            items.add(object);
        }
        else if (randomNumber < 12) {
            object = new PowerupFlame(x, y, Sprite.powerup_flames.getFxImage());
            items.add(object);
        }
    }

    public void update() {
        this.conga();
        removeDeadEntity();
        bombers.forEach(Entity::update);
        enemies.forEach(Entity::update);
        deads.forEach(Entity::update);
        bombs.forEach(Entity::update);
        bricks.forEach(Entity::update);

        for (int i = 0; i < bombers.size(); i++) {
            ((Bomber)bombers.get(i)).handleKeyPress(this.scene);
        }
        for (int i = 0; i < bombs.size(); i++) {
            for (int j = 0; j < bombers.size(); j++) {
                Bomber bomber = (Bomber) bombers.get(j);
                ((Bomb)bombs.get(i)).checkCharacterPassability(bomber);
            }
        }

        this.addBomb();
        this.handleBomb();
        this.handleCollision();
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        grasses.forEach(g -> g.render(gc));
        items.forEach(g -> g.render(gc));
        portals.forEach(g -> g.render(gc));
        bricks.forEach(g -> g.render(gc));
        walls.forEach(g -> g.render(gc));
        bombs.forEach(g -> g.render(gc));
        deads.forEach(g -> g.render(gc));
        enemies.forEach(g -> g.render(gc));
        bombers.forEach(g -> g.render(gc));
    }

    /**
     * Doc du lieu tu file txt va ghi vao list map.
     */
    private void readDataFromFile(){
        try {
            File file = new File("res/levels/Level1.txt");
            Scanner scaner = new Scanner(file);
            int L;
            L = scaner.nextInt();
            height = scaner.nextInt();
            width = scaner.nextInt();

            String line = scaner.nextLine();;
            for (int i = 0; i < height; i++) {
                line = scaner.nextLine();
                map.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Xu ly va cham (tat ca moi va cham deu xu ly o day).
     */
    public void handleCollision() {
        // Nhan vat
        ///////////////////////////////////////////////////////
        if (bombers.size() > 0) {
            Bomber bomber = (Bomber) bombers.get(0);
            for (int i = 0; i < enemies.size(); i++) {
                if (bomber.intersects(enemies.get(i))) {
                    bomber.setHp(0);
                    break;
                }
            }
            if (!bomber.isWallPass()) {
                for (int i = 0; i < bricks.size(); i++) {
                    bomber.checkObjectMovementAbility(bricks.get(i));
                }
            }
            for (int i = 0; i < bombs.size(); i++) {
                if (((Bomb)bombs.get(i)).isPassable()) {
                    continue;
                }
                bomber.checkObjectMovementAbility(bombs.get(i));
            }
            for (int i = 0; i < walls.size(); i++) {
                bomber.checkObjectMovementAbility(walls.get(i));
            }
            for (int i = 0; i < portals.size(); i++) {
                if (bomber.intersects(portals.get(i)) && enemies.size() == 0) {
                    ///////////////////////////
                }
            }
            for (int i = 0; i < items.size(); i++) {
                if (bomber.intersects(items.get(i))) {
                    if (items.get(i) instanceof PowerupSpeed) {
                        bomber.setSpeed(bomber.getSpeed() + 1);
                        items.remove(i);
                    }
                    else if (items.get(i) instanceof PowerupWallPass) {
                        bomber.setWallPass(true);
                        items.remove(i);
                    }
                    else if (items.get(i) instanceof PowerupFlame) {
                        bomber.setFlame(bomber.getFlame() + 1);
                        items.remove(i);
                    }
                    else if (items.get(i) instanceof PowerupBomb) {
                        bomber.setBomb(bomber.getBomb() + 1);
                        items.remove(i);
                    }
                    break;
                }
            }
        }

        // Cac enemy
        ////////////////////////////////////////////////////////////////
        for (int i = 0; i < enemies.size(); i++) {
            MovingEntity movingEntity = (MovingEntity) enemies.get(i);
            if (!movingEntity.isWallPass()) {
                for (int j = 0; j < bricks.size(); j++) {
                    movingEntity.checkObjectMovementAbility(bricks.get(j));
                }
            }
            for (int j = 0; j < bombs.size(); j++) {
                movingEntity.checkObjectMovementAbility(bombs.get(j));
            }
            for (int j = 0; j < walls.size(); j++) {
                movingEntity.checkObjectMovementAbility(walls.get(j));
            }
        }
    }

    /**
     * Xoa nhung doi tuong co hp <= 0 khoi cac list
     */
    public void removeDeadEntity() {
        for (int i = 0; i < bricks.size(); i++) {
            if (bricks.get(i).getHp() <= 0) {
                deads.add(bricks.get(i));
                bricks.remove(i--);
            }
        }

        for (int i = 0; i < bombers.size(); i++) {
            if (bombers.get(i).getHp() <= 0) {
                deads.add(bombers.get(i));
                bombers.remove(i--);
            }
        }

        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).getHp() <= 0) {
                deads.add(enemies.get(i));
                enemies.remove(i--);
            }
        }
    }

    /**
     * Them bomb khi nhan duoc lenh tu ban phim va co du dieu kien.
     */
    public void addBomb() {
        if (bombers.size() <= 0) {
            return;
        }
        Bomber bomber = (Bomber) bombers.get(0);
        if ((bomber.getCreateBomb() != 1) || bombs.size() >= bomber.getBomb()) {
            return;
        }

        Bomb bomb = new Bomb((bomber.getX() + (int)(bomber.getImg().getWidth() / 2))/ Sprite.SCALED_SIZE,
                (bomber.getY() + (int)(bomber.getImg().getHeight() / 2)) / Sprite.SCALED_SIZE, Sprite.bomb.getFxImage());
        boolean add = true;
        for (int i = 0; i < bombs.size(); i++) {
            if (bombs.get(i).equals(bomb)) {
                add = false;
                break;
            }
        }
        if (bomber.isWallPass() && add) {
            for (int i = 0; i < bricks.size(); i++) {
                if (bricks.get(i).getX() == bomb.getX() && bricks.get(i).getY() == bomb.getY()) {
                    add = false;
                    break;
                }
            }
        }
        if (add) {
            bombs.add(bomb);
        }
    }

    public void handleBomb() {
        for (int i = 0; i < bombs.size(); i++) {
            if (bombs.get(i).getHp() > 0) {
                break;
            }
            bombs.remove(i--);
        }
    }

    public void conga() {
        for (int i = 0; i < deads.size(); i++) {
            if (deads.get(i).geatAnimation().isFinishDeadAnimation()) {
                deads.remove(i--);
            }
        }
    }
}
