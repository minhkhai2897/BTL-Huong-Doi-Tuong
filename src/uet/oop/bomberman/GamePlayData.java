package uet.oop.bomberman;

import uet.oop.bomberman.entities.Entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GamePlayData {
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

    public static void readDataFromFile() {
        List<String> map = new ArrayList<>();
        List<String> mapList = BombermanGame.getMapList();

        try {
            File file = new File(mapList.get(BombermanGame.getLevel()));
            BombermanGame.setLevel(BombermanGame.getLevel() + 1);
            Scanner scaner = new Scanner(file);
            int L;
            L = scaner.nextInt();
            BombermanGame.setHeight(scaner.nextInt());
            BombermanGame.setWidth(scaner.nextInt());

            String line = scaner.nextLine();;
            for (int i = 0; i < BombermanGame.getHeight(); i++) {
                line = scaner.nextLine();
                map.add(line);
            }
            BombermanGame.setMap(map);
            scaner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
