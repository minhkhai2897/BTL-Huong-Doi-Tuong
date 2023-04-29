package uet.oop.bomberman;

import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;
import java.util.*;
import java.util.List;

public class MyMath {
    public static final int MULTIPLIER = 100000;
    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public static Point convertIntToPoint(int n) {
        int x = n % BombermanGame.WIDTH;
        int y = (n - x) / BombermanGame.WIDTH;
        return new Point(x, y);
    }

    public static int converPointToInt(Point p) {
        int n = BombermanGame.WIDTH * p.y + p.x;
        return n;
    }

    public static int converPointToInt(int x, int y) {
        int n = BombermanGame.WIDTH * y + x;
        return n;
    }

    public static List<Integer> assign_priority_scores_to_vertices(int width, int height) {
        List<List<Integer>> vertex = new ArrayList<>(height);
        for (int i = 0; i < height; i++) {
            vertex.add(new ArrayList<>(Collections.nCopies(width, 0)));
        }

        int width1 = width;
        int height1 = height;
        int priorityScore = 0;
        int t = 0;

        while (width1 > 0 && height1 > 0) {
            for (int i = t; i < t + width1; i++) {
                vertex.get(t).set(i, priorityScore);
            }
            for (int i = t; i < t + height1; i++) {
                vertex.get(i).set(t + width1 - 1, priorityScore);
            }
            for (int i = t + width1 - 1; i >= t; i--) {
                vertex.get(t + height1 - 1).set(i, priorityScore);
            }
            for (int i = t + height1 - 1; i >= t; i--) {
                vertex.get(i).set(t, priorityScore);
            }

            width1 -= 2;
            height1 -= 2;
            priorityScore++;
            t++;
        }

        List<Integer> priorityScores = new ArrayList<>(Collections.nCopies(width * height, 0));
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int n = converPointToInt(j, i);
                priorityScores.set(n, 2 * ((t - 1) - vertex.get(i).get(j)));
            }
        }

        return priorityScores;
    }

    public static int distanceManhattan(int u, int v) {
        Point p1 = convertIntToPoint(u);
        Point p2 = convertIntToPoint(v);
        return (Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y)) * MULTIPLIER;
    }
}


