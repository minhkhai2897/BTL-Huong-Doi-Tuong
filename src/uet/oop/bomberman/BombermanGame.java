package uet.oop.bomberman;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.stage.Stage;
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
    public final static int WIDTH = 31;
    public final static int HEIGHT = 13;
    public static Image introImage;
    private static Group root;
    private static Scene scene;
    private static Canvas canvas;
    private static GraphicsContext gc;
    private static Font font;
    private static Font gameFont;
    private static Label label;
    private static Label levelLabel;
    private static Label livesLeftLabel;
    private static MediaPlayer music;
    private static AudioClip bombExplosionSound;
    private static AudioClip placedBombSound;
    private static AudioClip moveSound;
    private static AudioClip powerupSound;
    private static AudioClip playerDeathSound;
    private static int livesLeft = 2;
    private static int level = 0;
    private static int time = 0;
    private static boolean isIntroFinished = false;
    private static boolean isLevelComplete = false;
    private static boolean ableToPlayFindTheDoorMusic = true;
    private static boolean ableToPlayPlayerDeathSound = false;
    private static boolean newLevel = true;
    private static boolean win = false;
    private static boolean playerDeath = false;
    private static List<Integer> priorityScores;
    private static List<Integer> priorityScores1 = new ArrayList<>(Collections.nCopies(WIDTH * HEIGHT, 0));
    private static List<String> mapList = new ArrayList<>();
    private static List<List<Character>> map = new ArrayList<>();
    private static List<Entity> grasses = new ArrayList<>();
    private static List<Entity> enemies = new ArrayList<>();
    private static List<Entity> items = new ArrayList<>();
    private static List<Entity> walls = new ArrayList<>();
    private static List<Entity> bricks = new ArrayList<>();
    private static List<Entity> portals = new ArrayList<>();
    private static List<Entity> bombers = new ArrayList<>();
    private static List<Entity> bombs = new ArrayList<>();
    private static List<Entity> deads = new ArrayList<>();
    private static List<Entity> flames = new ArrayList<>();

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    public static Scene getScene() {
        return scene;
    }

    public static List<List<Character>> getMap() {
        return map;
    }

    public static List<Integer> getPriorityScores1() {
        return priorityScores1;
    }

    public static void setLevelComplete(boolean levelComplete) {
        isLevelComplete = levelComplete;
    }

    public static List<Entity> getEnemies() {
        return enemies;
    }

    public static void setMap(List<List<Character>> map) {
        BombermanGame.map = map;
    }

    public static List<Integer> getPriorityScores() {
        return priorityScores;
    }

    public static List<Entity> getWalls() {
        return walls;
    }

    public static List<Entity> getBricks() {
        return bricks;
    }

    public static List<Entity> getPortals() {
        return portals;
    }

    public static List<Entity> getBombers() {
        return bombers;
    }

    public static List<Entity> getBombs() {
        return bombs;
    }

    public static List<Entity> getFlames() {
        return flames;
    }

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        BombermanGame.level = level;
    }
    public static void setMapList(List<String> mapList) {
        BombermanGame.mapList = mapList;
    }



    public static List<String> getMapList() {
        List<String> mapListCopy = new ArrayList<>(mapList);
        return mapListCopy;
    }

    public void start(Stage stage) {
        GamePlayData.loadMapListFromFile();
        priorityScores = MyMath.assign_priority_scores_to_vertices(BombermanGame.WIDTH, BombermanGame.HEIGHT);
        for (int i = 0; i < priorityScores.size(); i++) {
            priorityScores1.set(i, (Math.min(BombermanGame.WIDTH, BombermanGame.HEIGHT) / 2) * 2 - priorityScores.get(i));
        }

        font = GamePlayData.loadFont(getClass().getResource("/font/calibrib.ttf").toString(), 24);
        gameFont = GamePlayData.loadFont(getClass().getResource("/font/game_font.ttf").toString(), 24);

        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * (HEIGHT + 2));
        gc = canvas.getGraphicsContext2D();
        root = new Group();
        root.getChildren().add(canvas);
        scene = new Scene(root);
        scene.setFill(Color.BLACK);

        label = new Label("STATE  1");
        label.setFont(gameFont);
        label.setStyle("-fx-text-fill: white;");
        label.setVisible(false);
        root.getChildren().add(label);

        levelLabel = new Label("  STAGE  " + (this.level + 1));
        levelLabel.setFont(font);
        levelLabel.setStyle("-fx-text-fill: white;");
        levelLabel.setLayoutX(24); levelLabel.setLayoutY(17);
        levelLabel.setVisible(false);
        root.getChildren().add(levelLabel);

        livesLeftLabel = new Label("LEFT  " + this.livesLeft);
        livesLeftLabel.setFont(font);
        livesLeftLabel.setStyle("-fx-text-fill: white;");
        livesLeftLabel.setLayoutX(892); livesLeftLabel.setLayoutY(17);
        livesLeftLabel.setVisible(false);
        root.getChildren().add(livesLeftLabel);

        stage.setResizable(false);
        stage.setTitle("BTL_Bomberman");
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (!isIntroFinished) {
                    startIntro();
                    return;
                }

                render();

                if (isLevelComplete) {
                    boolean continueProcess = handleLevelCompletion();
                    if (!continueProcess) {
                        return;
                    }
                }

                if (newLevel) {
                    boolean continueProcess = initializeGameView();
                    if (!continueProcess) {
                        return;
                    }
                }

                if (win) {
                    handleWin();
                    return;
                }

                if (playerDeath) {
                    handleAfterPlayerDeath();
                    return;
                }

                update();
            }
        };
        timer.start();
    }

    public void startIntro() {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                isIntroFinished = true;
            }
        });
        if (introImage == null) {
            introImage = new Image(getClass().getResource("/sprites/introImage.png").toString());
        }
        if (music == null) {
            music = AudioManager.setAndPlayMusic(music, getClass().getResource("/sounds/src_main_resources_sounds_Presentation.wav").toString());
        }

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.drawImage(introImage, 0, 0);
    }

    public void update() {
        Doll.checkAndSpawnDoll();
        setIdMinvo();
        setIdDoll();

        flames.forEach(Entity::update);
        bombers.forEach(Entity::update);
        enemies.forEach(Entity::update);
        bombs.forEach(Entity::update);
        bricks.forEach(Entity::update);
        deads.forEach(Entity::update);
        items.forEach(Entity::update);

        handleSound();
        removeDeadEntity();
        this.removeFinishedElements();

        if (deads.size() == 0) {
            for (int i = 0; i < bombers.size(); i++) {
                if ((bombers.get(i)).getHp() == 0 ) {
                    playerDeath = true;
                }
            }
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
        enemies.forEach(g -> g.render(gc));
        if (walls.size() > 0) {
            for (int i = 0; i < bombers.size(); i++) {
                if (bombers.get(i).getHp() > 0) {
                    bombers.get(i).render(gc);
                }
            }
        }
        flames.forEach(g -> g.render(gc));
    }

    public void createMap() {
        this.reset();

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Entity object = new Grass(j, i + 2, Sprite.grass.getFxImage());
                grasses.add(object);
                char c = map.get(i).get(j);

                if (c == 'p') {
                    if (bombers.size() != 0) {
                        bombers.get(0).setX(j);
                        bombers.get(0).setY(i + 2);
                        bombers.get(0).setImg(Sprite.player_down.getFxImage());
                    }
                    else {
                        object = new Bomber(j, i + 2, Sprite.player_down.getFxImage());
                        bombers.add(object);
                    }
                }
                else if ('0' <= c && c <= '9') {
                    if (c == '1') {
                        object = new Balloon(j, i + 2, Sprite.balloom_left1.getFxImage());
                    }
                    else if (c == '2') {
                        object = new Oneal(j, i + 2, Sprite.oneal_right1.getFxImage());
                    } else if (c == '3') {
                        object = new Kondoria(j, i + 2, Sprite.kondoria_left1.getFxImage());
                    } else if (c == '4') {
                        object = new Minvo(j, i + 2, Sprite.minvo_left1.getFxImage());
                    } else if (c == '5') {
                        object = new Doll(j, i + 2, Sprite.doll_left1.getFxImage());
                    } else {
                        object = new Balloon(j, i + 2, Sprite.balloom_left1.getFxImage());
                    }
                    enemies.add(object);
                }
                else {
                    if (c == '#') {
                        object = new Wall(j, i + 2, Sprite.wall.getFxImage());
                        walls.add(object);
                    } else if (c == '*') {
                        object = new Brick(j, i + 2, Sprite.brick.getFxImage());
                        bricks.add(object);
                    } else if (c == 'x') {
                        object = new Portal(j, i + 2, Sprite.portal.getFxImage());
                        portals.add(object);
                    } else if (c == 'b') {
                        object = new PowerupBomb(j, i + 2, Sprite.powerup_bombs.getFxImage());
                        items.add(object);
                    } else if (c == 'f') {
                        object = new PowerupFlame(j, i + 2, Sprite.powerup_flames.getFxImage());
                        items.add(object);
                    } else if (c == 's') {
                        object = new PowerupSpeed(j, i + 2, Sprite.powerup_speed.getFxImage());
                        items.add(object);
                    }   else if (c == 'w') {
                        object = new PowerupWallPass(j, i + 2, Sprite.powerup_wallpass.getFxImage());
                        items.add(object);
                    }
                }
            }
        }

        if (bricks.isEmpty()) {
            Entity portal = new Portal(1, 3, Sprite.portal.getFxImage());
            portals.add(portal);
        }
        else {
            int randomNumber = MyMath.getRandomNumber(0, bricks.size() - 1);
            Entity portal = new Portal(bricks.get(randomNumber).getX() / Sprite.SCALED_SIZE,
                    bricks.get(randomNumber).getY() / Sprite.SCALED_SIZE, Sprite.portal.getFxImage());
            portals.add(portal);
        }
        Astar.createAdjList();
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

    public void removeFinishedElements() {
        for (int i = 0; i < deads.size(); i++) {
            if (deads.get(i).getAnimation().isFinishDeadAnimation()) {
                if (deads.get(i) instanceof Bomber) {
                    bombers.add(deads.get(i));
                    deads.clear();
                }
                else {
                    deads.remove(i--);
                }
            }
        }

        for (int i = 0; i < flames.size(); i++) {
            if (flames.get(i).getAnimation().isFinishDeadAnimation()) {
                Point position = flames.get(i).getPosition();

                Astar.addNeighbor(Astar.getAdjList(), position);
                int n = MyMath.converPointToInt(position);
                Astar.getAdjList().get(n).clear();
                for (int j = 0; j < Astar.getAdjList0().get(n).size(); j++) {
                    Astar.getAdjList().get(n).add(Astar.getAdjList0().get(n).get(j));
                }

                Astar.addNeighbor(Astar.getAdjListWallpass(), position);
                Astar.getAdjListWallpass().get(n).clear();
                for (int j = 0; j < Astar.getAdjListWallpass0().get(n).size(); j++) {
                    Astar.getAdjListWallpass().get(n).add(Astar.getAdjListWallpass0().get(n).get(j));
                }

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
                Point position = bricks.get(i).getPosition();
                Astar.addNeighbor(Astar.getAdjList0(), position);
                Astar.addNeighbor(Astar.getAdjList(), position);
                bricks.remove(i--);
            }
        }

        for (int i = 0; i < bombers.size(); i++) {
            if (bombers.get(i).getHp() <= 0) {
                deads.add(bombers.get(i));
                bombers.remove(i--);
                ableToPlayPlayerDeathSound = true;
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
                for (int j = 0; j < bombers.size(); j++) {
                    if (flame.intersects(bombers.get(j))) {
                        bombers.get(j).setHp(0);
                        break;
                    }
                }
                for (int j = 0; j < enemies.size(); j++) {
                    if (flame.intersects(enemies.get(j))) {
                        enemies.get(j).setHp(0);
                    }
                }
                break;
            }
            flames.add(flame);
        }
    }

    public boolean handleLevelCompletion() {
        final int completionTime = 180;

        if (time == 0) {
            livesLeft = (livesLeft < 9 ? livesLeft + 1 : 9);
            music = AudioManager.setAndPlayMusic(music, getClass().getResource("/sounds/level_complete.wav").toString());
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

    public void handleWin() {
        if (time == 0) {
            this.initializeTitleScreen("YOU WIN!");
            music = AudioManager.setAndPlayMusic(music, getClass().getResource("/sounds/src_main_resources_assets_sounds_ending (2).wav").toString());
        }
        time = 1;
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                resetGame();
                isIntroFinished = false;
            }
        });
    }

    public void handleAfterPlayerDeath() {
        final int completionTime = 120;
        if (time == 0) {
            music = AudioManager.setAndPlayMusic(music, getClass().getResource("/sounds/just_died.wav").toString());
        }
        time = (time < 10000 ? (time + 1) : 10000);

        if (time == completionTime && livesLeft > 0) {
            reserAfterPlayerDeath();
            return;
        }

        if (time == completionTime) {
            this.initializeTitleScreen("YOU LOSE!");
            music = AudioManager.setAndPlayMusic(music, getClass().getResource("/sounds/src_main_resources_sounds_Game_Over.wav").toString());
        }
        if (time > completionTime) {
            scene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    resetGame();
                    isIntroFinished = false;
                }
            });
        }
    }

    public void reserAfterPlayerDeath() {
        this.time = 0;
        this.playerDeath = false;
        this.level--;
        this.livesLeft--;
        this.newLevel = true;
        for (int i = 0; i < bombers.size(); i++) {
            bombers.get(i).setHp(1);
            bombers.get(i).setImg(Sprite.player_down_1.getFxImage());
        }
    }

    public void resetGame() {
        reset();
        bombers.clear();
        livesLeft = 2;
        level = 0;
        time = 0;
        isIntroFinished = false;
        isLevelComplete = false;
        ableToPlayFindTheDoorMusic = true;
        ableToPlayPlayerDeathSound = false;
        newLevel = true;
        this.win = false;
        this.playerDeath = false;
        if (music != null && music.getStatus() == MediaPlayer.Status.PLAYING) {
            music.stop();
            music.dispose();
        }
        music = null;
        label.setVisible(false);
        levelLabel.setVisible(false);
        livesLeftLabel.setVisible(false);
    }

    public boolean initializeGameView() {
        final int completionTime = 180;

        if (time == 0) {
            levelLabel.setVisible(false);
            livesLeftLabel.setVisible(false);
            music = AudioManager.setAndPlayMusic(music, getClass().getResource("/sounds/new_level_music.mp3").toString());
            this.initializeTitleScreen("STAGE  " + (this.level + 1));
            levelLabel.setText("STAGE  " + (this.level + 1));
        }
        time++;
        if (time < completionTime) {
            return false;
        }

        label.setVisible(false);
        levelLabel.setVisible(true);
        livesLeftLabel.setText("LEFT  " + livesLeft);
        livesLeftLabel.setVisible(true);
        scene.setFill(Color.web("#bdbebd"));

        ableToPlayFindTheDoorMusic = true;
        music = AudioManager.setAndPlayMusicLoop(music, getClass().getResource("/sounds/src_main_resources_assets_music_stage_theme.mp3").toString());
        GamePlayData.readMapFromFile();
        canvas.setHeight(Sprite.SCALED_SIZE * (HEIGHT + 2));
        canvas.setWidth(Sprite.SCALED_SIZE * WIDTH);
        createMap();

        time = 0;
        newLevel = false;

        return true;
    }

    public void initializeTitleScreen(String title) {
        scene.setFill(Color.BLACK);
        levelLabel.setVisible(false);
        livesLeftLabel.setVisible(false);
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

        Astar.getAdjList0().clear();
        Astar.getAdjList().clear();
        Astar.getAdjListWallpass0().clear();
        Astar.getAdjListWallpass().clear();
    }

    public void handleSound() {
        for (int i = 0; i < bombers.size(); i++) {
            Bomber bomber = (Bomber) bombers.get(i);
            if ((bomber.isMoveLeft() && bomber.isAbleToMoveLeft())
                || (bomber.isMoveRight() && bomber.isAbleToMoveRight()))
            {
                if (moveSound == null || !moveSound.isPlaying()) {
                    moveSound = AudioManager.setAndPlaySound(moveSound, getClass().getResource( "/sounds/src_main_resources_assets_sounds_walk_1.wav").toString());
                }
            }

            if ((bomber.isMoveUp() && bomber.isAbleToMoveUp())
                || (bomber.isMoveDown() && bomber.isAbleToMoveDown()))
            {
                if (moveSound == null || !moveSound.isPlaying()) {
                    moveSound = AudioManager.setAndPlaySound(moveSound, getClass().getResource( "/sounds/Bomberman SFX (2).wav").toString());
                }
            }
        }

        for (int i = 0; i < bombs.size(); i++) {
            if (bombs.get(i).getHp() > 0) {
                break;
            }
            this.bombExplosionSound = AudioManager.setAndPlaySound(this.bombExplosionSound, getClass().getResource("/sounds/src_main_resources_assets_sounds_explosion.wav").toString());
        }

        for (int i = 0; i < bombers.size(); i++) {
            if (((Bomber)bombers.get(i)).isBombAdded()) {
                ((Bomber) bombers.get(i)).setBombAdded(false);
                this.placedBombSound = AudioManager.setAndPlaySound(this.placedBombSound, getClass().getResource("/sounds/src_main_resources_assets_sounds_placed_bomb.wav").toString());
            }
        }

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getHp() < 1) {
                this.powerupSound = AudioManager.setAndPlaySound(this.powerupSound, getClass().getResource("/sounds/src_main_resources_assets_sounds_powerup.wav").toString());
            }
        }

        if (ableToPlayPlayerDeathSound) {
            if (playerDeathSound == null || !playerDeathSound.isPlaying()) {
                playerDeathSound = AudioManager.setAndPlaySound(playerDeathSound, getClass().getResource("/sounds/src_main_resources_sounds_sound effects_death.wav").toString());
            }
            ableToPlayPlayerDeathSound = false;
        }

        if (enemies.size() < 1 && ableToPlayFindTheDoorMusic) {
            this.music = AudioManager.setAndPlayMusicLoop(music,  getClass().getResource("/sounds/Find_The_Door.wav").toString());
            ableToPlayFindTheDoorMusic = false;
        }
    }

    public void setIdMinvo() {
        int id = 0;
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i) instanceof Minvo) {
                ((Minvo)enemies.get(i)).setId(id++);
            }
        }
    }

    public void setIdDoll() {
        int id = 0;
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i) instanceof Doll) {
                ((Doll)enemies.get(i)).setId(id++);
            }
        }
    }
}
