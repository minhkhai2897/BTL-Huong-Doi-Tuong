package uet.oop.bomberman;

import java.awt.*;
import java.util.Random;

public class MyMath {
    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public static Point convertIntToPoint(int n) {
        int x = n % BombermanGame.getWidth();
        int y = (n - x) / BombermanGame.getWidth();
        return new Point(x, y);
    }

    public static int converPointToInt(Point p) {
        int n = BombermanGame.getWidth() * p.y + p.x;
        return n;
    }

    public static int converPointToInt(int x, int y) {
        int n = BombermanGame.getWidth() * y + x;
        return n;
    }
}
