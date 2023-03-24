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
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    private Bomber bomber;
    private Group root;
    private Scene scene;
    
    public static int width;
    public static int height;
    
    private GraphicsContext gc;
    private Canvas canvas;
    private List<String> map = new ArrayList<>();
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private List<Entity> grasses = new ArrayList<>();


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        readDataFromFile();

        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * width, Sprite.SCALED_SIZE * height);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        scene = new Scene(root);

        // Them scene vao stage
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

        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i) instanceof Bomber) {
                bomber = (Bomber) entities.get(i);
                break;
            }
        }
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
                object = null;
                char c = s.charAt(j);

                if (c == 'p') {
                    object = new Bomber(j, i, Sprite.player_down.getFxImage());
                    entities.add(object);
                } else if ('0' <= c && c <= '9') {
                    if (c == '1') {
                        object = new Balloon(j, i, Sprite.balloom_left1.getFxImage());
                    }
                    else if (c == '2') {
                        object = new Oneal(j, i, Sprite.oneal_right1.getFxImage());
                    } else {
                        object = new Balloon(j, i, Sprite.balloom_left1.getFxImage());
                    }
                    entities.add(object);
                } else {
                    if (c == '#') {
                        object = new Wall(j, i, Sprite.wall.getFxImage());
                    } else if (c == '*') {
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                    } else if (c == 'x') {
                        object = new Portal(j, i, Sprite.portal.getFxImage());
                    } else if (c == 'b') {
                        object = new Bomb(j, i, Sprite.powerup_bombs.getFxImage());
                    } else if (c == 'f') {
                        object = new Flame(j, i, Sprite.powerup_flames.getFxImage());
                    } else if (c == 's') {
                        object = new Speed(j, i, Sprite.powerup_speed.getFxImage());
                    }

                    if (object != null) {
                        stillObjects.add(object);
                    }
                }
            }
        }
    }

    public void update() {
        entities.forEach(Entity::update);
        bomber.handleKeyPress(this.scene);

        for (int i = 0; i < entities.size(); i++) {
            if (!(entities.get(i) instanceof Bomber)) {
                bomber.checkCollision(entities.get(i));
                MovingEntity movingEntity = (MovingEntity) entities.get(i);
                for (int j = 0; j < stillObjects.size(); j++) {
                    if (stillObjects.get(j) instanceof Portal) {
                        continue;
                    }
                    movingEntity.checkCollision(stillObjects.get(j));
                }
            }
        }
        for (int i = 0; i < stillObjects.size(); i++) {
            if (stillObjects.get(i) instanceof Portal) {
                continue;
            }
            bomber.checkCollision(stillObjects.get(i));
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        grasses.forEach(g -> g.render(gc));
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
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
}
