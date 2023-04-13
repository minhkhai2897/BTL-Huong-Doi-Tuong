package uet.oop.bomberman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import uet.oop.bomberman.entities.MovingEntity;
import uet.oop.bomberman.entities.bomber.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.enemies.*;
import uet.oop.bomberman.entities.items.PowerupBomb;
import uet.oop.bomberman.entities.items.PowerupFlame;
import uet.oop.bomberman.entities.items.PowerupSpeed;
import uet.oop.bomberman.entities.items.PowerupWallPass;
import uet.oop.bomberman.entities.stillObjects.*;
import uet.oop.bomberman.graphics.Sprite;

public class BombermanGame extends Application {
    private static int width = 31;
    private static int height = 13;
    private Group root;
    private Scene scene;
    private Canvas canvas;
    private GraphicsContext gc;
    private Label label;
    private MediaPlayer music;
    private AudioClip bombExplosionSound;
    private AudioClip placedBombSound;
    private AudioClip moveSound;
    private int level = 0;
    private int time = 0;
    private boolean newLevel = true;
    private boolean isLevelComplete = false;
    private boolean bombAdded = false;
    private boolean win = false;
    private boolean lose = false;
    private List<String> mapList = new ArrayList<>();
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
    private List<Entity> flames = new ArrayList<>();

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    public void start(Stage stage) {
        this.loadMapListFromFile();
        canvas = new Canvas(Sprite.SCALED_SIZE * width, Sprite.SCALED_SIZE * height);
        gc = canvas.getGraphicsContext2D();
        root = new Group();
        root.getChildren().add(canvas);
        scene = new Scene(root);

        scene.setFill(Color.BLACK);
        label = new Label("");
        label.setStyle("-fx-font-size: 36; -fx-text-fill: white;");
        root.getChildren().add(label);
        stage.setTitle("BTL_Bomberman");
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();

                if (isLevelComplete) {
                    boolean continueProcess = handleLevelCompletion(stage);
                    if (!continueProcess) {
                        return;
                    }
                }

                if (newLevel) {
                    boolean continueProcess = initializeGameView(stage);
                    if (!continueProcess) {
                        return;
                    }
                }

                if (win) {
                    handleWin(stage);
                    return;
                }

                if (lose) {
                    handleLose(stage);
                    return;
                }

                update();
            }
        };
        timer.start();
    }

    public void update() {
        bombers.forEach(Entity::update);
        enemies.forEach(Entity::update);
        deads.forEach(Entity::update);
        bombs.forEach(Entity::update);
        bricks.forEach(Entity::update);
        flames.forEach(Entity::update);

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
        this.handleCollision();
        handleSound();
        this.removeFinishedElements();
        removeDeadEntity();

        if (bombers.size() == 0 && deads.size() == 0) {
            lose = true;
        }
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
        flames.forEach(g -> g.render(gc));
        enemies.forEach(g -> g.render(gc));
        if (walls.size() > 0) {
            bombers.forEach(g -> g.render(gc));
        }
    }

    public void createMap() {
        this.reset();

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
                    } else if (c == '4') {
                        object = new Minvo(j, i, Sprite.minvo_left1.getFxImage());
                    } else if (c == '5') {
                        object = new Doll(j, i, Sprite.doll_left1.getFxImage());
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

    private void loadMapListFromFile() {
        mapList.clear();
        try {
            File file = new File("res/levels/mapList.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                mapList.add(line);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Doc du lieu tu file txt va ghi vao list map.
     */
    private void readDataFromFile() {
        map.clear();

        try {
            File file = new File(mapList.get(level++));
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
            scaner.close();
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

            for (int i = 0; i < flames.size(); i++) {
                if (bomber.intersects(flames.get(i))) {
                    bomber.setHp(0);
                    break;
                }
            }

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
                if (!this.isLevelComplete) {
                    if (bomber.intersects(portals.get(i)) && enemies.size() == 0) {
                        boolean complete = true;
                        for (int j = 0; j < bricks.size(); j++) {
                            if (portals.get(i).getX() == bricks.get(j).getX() &&
                                    portals.get(i).getY() == bricks.get(j).getY()) {
                                complete = false;
                                break;
                            }
                        }
                        this.isLevelComplete = complete;
                    }
                }
            }
            for (int i = 0; i < items.size(); i++) {
                if (bomber.intersects(items.get(i))) {
                    if (items.get(i) instanceof PowerupSpeed) {
                        bomber.setSpeed(bomber.getSpeed() + 1);
                    }
                    else if (items.get(i) instanceof PowerupWallPass) {
                        bomber.setWallPass(true);
                    }
                    else if (items.get(i) instanceof PowerupFlame) {
                        bomber.setFlame(bomber.getFlame() + 1);
                    }
                    else if (items.get(i) instanceof PowerupBomb) {
                        bomber.setBomb(bomber.getBomb() + 1);
                    }
                    items.get(i).setHp(0);
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
            for (int j = 0; j < flames.size(); j++) {
                if (movingEntity.intersects(flames.get(j))) {
                    movingEntity.setHp(0);
                }
            }
        }

        // Bomb
        for (int i = 0; i < bombs.size(); i++) {
            for (int j = 0; j < flames.size(); j++) {
                if (bombs.get(i).intersects(flames.get(j))) {
                    bombs.get(i).setHp(0);
                    break;
                }
            }
        }

        // Item
        for (int i = 0; i < items.size(); i++) {
            for (int j = 0; j < flames.size(); j++) {
                if (items.get(i).intersects(flames.get(j))) {
                    items.get(i).setHp(0);
                    break;
                }
            }
        }
    }

    /**
     * Them bomb khi nhan duoc lenh tu ban phim va co du dieu kien.
     */
    public void addBomb() {
        this.bombAdded = false;
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
            this.bombAdded = true;
            bombs.add(bomb);
        }
    }

    public void removeFinishedElements() {
        for (int i = 0; i < deads.size(); i++) {
            if (deads.get(i).getAnimation().isFinishDeadAnimation()) {
                deads.remove(i--);
            }
        }

        for (int i = 0; i < flames.size(); i++) {
            if (flames.get(i).getAnimation().isFinishDeadAnimation()) {
                flames.remove(i--);
            }
        }
    }

    /**
     * Xoa nhung doi tuong co hp <= 0 khoi cac list
     */
    public void removeDeadEntity() {
        for (int i = 0; i < bombs.size(); i++) {
            if (bombs.get(i).getHp() > 0) {
                break;
            }
            this.addFlame(bombs.get(i).getX() / Sprite.SCALED_SIZE, bombs.get(i).getY() / Sprite.SCALED_SIZE);
            bombs.remove(i--);
        }

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getHp() <= 0) {
                items.remove(i--);
            }
        }

        for (int i = 0; i < bricks.size(); i++) {
            if (bricks.get(i).getHp() <= 0) {
                deads.add(bricks.get(i));
                this.addRandomItem(bricks.get(i).getX() / Sprite.SCALED_SIZE, bricks.get(i).getY() / Sprite.SCALED_SIZE);
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

    public void addFlame(int xUnit, int yUnit) {
        if (bombers.size() == 0) {
            return;
        }

        Entity flame = new Flame(xUnit, yUnit, "epicenter");
        flames.add(flame);
        this.addFlame(xUnit, yUnit, -1, 0);
        this.addFlame(xUnit, yUnit, 1, 0);
        this.addFlame(xUnit, yUnit, 0, -1);
        this.addFlame(xUnit, yUnit, 0, 1);
    }

    private void addFlame(int xUnit, int yUnit, int x, int y) {
        Bomber bomber = (Bomber) bombers.get(0);

        boolean add = true;

        for (int i = 0; i < bomber.getFlame(); i++) {
            String s;
            if (x == -1) {
                s = "left";
            } else if (x == 1) {
                s = "right";
            } else if (y == -1) {
                s = "up";
            } else {
                s = "down";
            }

            Entity flame;
            if (i + 1 < bomber.getFlame()) {
                if (x == 0) {
                    flame = new Flame(xUnit + x * (i + 1), yUnit + y * (i + 1), Sprite.explosion_vertical.getFxImage(), "vertical");
                } else {
                    flame = new Flame(xUnit + x * (i + 1), yUnit + y * (i + 1), Sprite.explosion_horizontal.getFxImage(), "horizontal");
                }
            }
            else {
                if (x == -1) {
                    flame = new Flame(xUnit + x * (i + 1), yUnit + y * (i + 1), Sprite.explosion_horizontal_left_last.getFxImage(), "left");
                } else if (x == 1) {
                    flame = new Flame(xUnit + x * (i + 1), yUnit + y * (i + 1), Sprite.explosion_horizontal_right_last.getFxImage(), "right");
                } else if (y == -1) {
                    flame = new Flame(xUnit + x * (i + 1), yUnit + y * (i + 1), Sprite.explosion_vertical_top_last.getFxImage(), "up");
                } else {
                    flame = new Flame(xUnit + x * (i + 1), yUnit + y * (i + 1), Sprite.explosion_vertical_down_last.getFxImage(), "down");
                }
            }

            add = true;
            for (int j = 0; j < walls.size(); j++) {
                if (flame.intersects(walls.get(j))) {
                    add = false;
                    break;
                }
            }
            if (add) {
                for (int j = 0; j < bricks.size(); j++) {
                    if (flame.intersects(bricks.get(j))) {
                        bricks.get(j).setHp(0);
                        add = false;
                        break;
                    }
                }
            }

            if (!add) {
                break;
            }
            flames.add(flame);
        }
    }

    public boolean handleLevelCompletion(Stage stage) {
        final int completionTime = 180;

        if (time == 0) {
            try {
                if (music != null) {
                    music.stop();
                    music.dispose();
                }
                music = new MediaPlayer(new Media(getClass().getResource("/sounds/level_complete.wav").toString()));
                music.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        time++;
        if (time < completionTime) { // sau 180 khung hình thì map mới sẽ được tạo
            return false;
        }
        if (level >= mapList.size()) {
            win = true;
            time = 0;
            return true;
        }

        newLevel = true;
        time = 0;

        isLevelComplete = false;
        return true;
    }

    public void handleWin(Stage stage) {
        if (time == 0) {
            this.initializeTitleScreen("YOU WIN!");
            // them nhac va lap lai nhac mai mai
        }
        time = 1;
    }

    public void handleLose(Stage stage) {
        final int completionTime = 120;
        if (time == 0) {
            if (music != null) {
                music.stop();
                music.dispose();
            }
            music = new MediaPlayer(new Media(getClass().getResource("/sounds/just_died.wav").toString()));
            music.play();
        }
        time++;

        if (time == completionTime) {
            this.initializeTitleScreen("YOU LOSE!");
            if (music != null) {
                music.stop();
                music.dispose();
            }
            music = new MediaPlayer(new Media(getClass().getResource("/sounds/kl-peach-game-over-iii-142453.mp3").toString()));
            music.play();
        }
        if (time > 100000) {
            time = 100000;
        }
    }

    public boolean initializeGameView(Stage stage) {
        final int completionTime = 180;

        if (time == 0) {
            try{
                if (music != null) {
                    music.stop();
                    music.dispose();
                }
                music = new MediaPlayer(new Media(getClass().getResource("/sounds/new_level_music.mp3").toString()));
                music.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.initializeTitleScreen("STAGE  " + (this.level + 1));
        }
        time++;
        if (time < completionTime) {
            return false;
        }

        label.setVisible(false);

        if (music != null) {
            music.stop();
            music.dispose();
        }
        music = new MediaPlayer(new Media(getClass().getResource("/sounds/backgroundMusic.m4a").toString()));
        music.setOnEndOfMedia(new Runnable() {
            public void run() {
                music.seek(Duration.ZERO);
                music.play();
            }
        });
        music.play();

        this.readDataFromFile();
        canvas.setHeight(Sprite.SCALED_SIZE * height);
        canvas.setWidth(Sprite.SCALED_SIZE * width);
        createMap();

        time = 0;
        newLevel = false;

        return true;
    }

    public void initializeTitleScreen(String title) {
        this.reset();
        label.setText(title);
        label.setVisible(true);

        double rootWidth = root.getBoundsInLocal().getWidth();
        double rootHeight = root.getBoundsInLocal().getHeight();
        double labelWidth = label.getBoundsInLocal().getWidth();
        double labelHeight = label.getBoundsInLocal().getHeight();
        label.setLayoutX((rootWidth - labelWidth) / 2);
        label.setLayoutY((rootHeight - labelHeight) / 2);
    }

    public void reset() {
        flames.clear();
        deads.clear();
        bombs.clear();
        walls.clear();
        grasses.clear();
        enemies.clear();
        items.clear();
        bricks.clear();
        portals.clear();
    }

    public void handleSound() {
        for (int i = 0; i < bombers.size(); i++) {
            Bomber bomber = (Bomber) bombers.get(i);
            if ((bomber.isMoveLeft() && bomber.isAbleToMoveLeft())
                || (bomber.isMoveRight() && bomber.isAbleToMoveRight()))
            {
                try {
                    if (moveSound != null && moveSound.isPlaying()) {
                        break;
                    }
                    this.moveSound = new AudioClip(getClass().getResource( "/sounds/src_main_resources_assets_sounds_walk_1.wav").toString());
                    this.moveSound.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if ((bomber.isMoveUp() && bomber.isAbleToMoveUp())
                || (bomber.isMoveDown() && bomber.isAbleToMoveDown()))
            {
                try {
                    if (moveSound != null && moveSound.isPlaying()) {
                        break;
                    }
                    this.moveSound = new AudioClip(getClass().getResource( "/sounds/Bomberman SFX (2).wav").toString());
                    this.moveSound.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        for (int i = 0; i < bombs.size(); i++) {
            if (bombs.get(i).getHp() > 0) {
                break;
            }
            try {
                this.bombExplosionSound = new AudioClip(getClass().getResource("/sounds/bomb_explosion.wav").toString());
                this.bombExplosionSound.play();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (this.bombAdded) {
            try {
                this.placedBombSound = new AudioClip(getClass().getResource("/sounds/src_main_resources_assets_sounds_placed_bomb.wav").toString());
                this.placedBombSound.play();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
