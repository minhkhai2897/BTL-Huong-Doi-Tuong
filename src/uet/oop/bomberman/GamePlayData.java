package uet.oop.bomberman;

import javafx.scene.SubScene;
import javafx.scene.text.Font;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomber.Bomber;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GamePlayData {

    public static Font loadFont(String path, int size) {
        Font font = null;
        try {
            URL url = new URL(path);
            InputStream inputStream = url.openStream();
            font = Font.loadFont(inputStream, size);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return font;
    }

    public static void loadMapListFromFile() {
        List<String> mapList = new ArrayList<>();
        mapList.clear();
        try {
            File file = new File("res/levels/mapList.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                mapList.add(line);
            }

            BombermanGame.setMapList(mapList);
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void readMapFromFile() {
        List<String> mapList = BombermanGame.getMapList();

        try {
            File file = new File(mapList.get(BombermanGame.getLevel()));
            BombermanGame.setLevel(BombermanGame.getLevel() + 1);
            Scanner scaner = new Scanner(file);
            scaner.nextLine();
            List<List<Character>> map = new ArrayList<>();
            for (int i = 0; i < BombermanGame.HEIGHT; i++) {
                List<Character> list = new ArrayList<>();
                String line = scaner.nextLine();
                for (int j = 0; j < BombermanGame.WIDTH; j++) {
                    char c = line.charAt(j);
                    list.add(c);
                }
                map.add(list);
            }
            BombermanGame.setMap(map);
            scaner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
