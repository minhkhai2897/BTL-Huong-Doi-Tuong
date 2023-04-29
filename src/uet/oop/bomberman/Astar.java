package uet.oop.bomberman;

import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Astar {
    private static List<List<Integer>> adjList0 = new ArrayList<>(BombermanGame.WIDTH * BombermanGame.HEIGHT);
    private static List<List<Integer>> adjList = new ArrayList<>(BombermanGame.WIDTH * BombermanGame.HEIGHT);
    private static List<List<Integer>> adjListWallpass0 = new ArrayList<>(BombermanGame.WIDTH * BombermanGame.HEIGHT);
    private static List<List<Integer>> adjListWallpass = new ArrayList<>(BombermanGame.WIDTH * BombermanGame.HEIGHT);

    public static List<List<Integer>> getAdjList() {
        return adjList;
    }
    public static List<List<Integer>> getAdjListWallpass() {
        return adjListWallpass;
    }

    public static List<List<Integer>> getAdjList0() {
        return adjList0;
    }
    public static List<List<Integer>> getAdjListWallpass0() {
        return adjListWallpass0;
    }

    public static void removeSingleNeighbor(List<List<Integer>> list, int currentCell, int NeighborCell) {
        for (int i = 0; i < list.get(currentCell).size(); i++) {
            if (list.get(currentCell).get(i) == NeighborCell) {
                list.get(currentCell).remove(i);
                break;
            }
        }
    }

    public static void removeNeighbor(List<List<Integer>> list, Point p) {
        removeNeighbor(list, p.x, p.y);
    }

    public static void addNeighbor(List<List<Integer>> list, Point p) {
        addNeighbor(list, p.x, p.y);
    }

    public static void removeNeighbor(List<List<Integer>> list, int x, int y) {
        int n = MyMath.converPointToInt(x, y);
        list.get(n).clear();

        if (n - BombermanGame.WIDTH >= 0) {
            removeSingleNeighbor(list, n - BombermanGame.WIDTH, n);
        }
        if (n - 1 >= 0 && ((n - 1) / BombermanGame.WIDTH) == (n / BombermanGame.WIDTH)) {
            removeSingleNeighbor(list, n - 1, n);
        }
        if (n + 1 < BombermanGame.WIDTH * BombermanGame.HEIGHT
                && ((n + 1) / BombermanGame.WIDTH == (n / BombermanGame.WIDTH)))
        {
            removeSingleNeighbor(list, n + 1, n);
        }
        if (n + BombermanGame.WIDTH < BombermanGame.WIDTH * BombermanGame.HEIGHT) {
            removeSingleNeighbor(list, n + BombermanGame.WIDTH, n);
        }
    }

    public static void addNeighbor(List<List<Integer>> list, int x, int y) {
        int n = MyMath.converPointToInt(x, y);
        if (n - BombermanGame.WIDTH >= 0) {
            addSingleNeighbor(list, n - BombermanGame.WIDTH, n);
        }
        if (n - 1 >= 0 && ((n - 1) / BombermanGame.WIDTH) == (n / BombermanGame.WIDTH)) {
            addSingleNeighbor(list, n - 1, n);
        }
        if (n + 1 < BombermanGame.WIDTH * BombermanGame.HEIGHT
                && ((n + 1) / BombermanGame.WIDTH == (n / BombermanGame.WIDTH)))
        {
            addSingleNeighbor(list, n + 1, n);
        }
        if (n + BombermanGame.WIDTH < BombermanGame.WIDTH * BombermanGame.HEIGHT) {
            addSingleNeighbor(list, n + BombermanGame.WIDTH, n);
        }
    }

    public static void addSingleNeighbor(List<List<Integer>> list,int  currentCell, int neighborCell) {
        boolean k = true;
        for (int i = 0; i < list.get(currentCell).size(); i++) {
            if (list.get(currentCell).get(i) == neighborCell) {
                k = false;
                break;
            }
        }
        if (k) {
            list.get(currentCell).add(neighborCell);
        }
    }

    public static void createAdjList() {
        for (int i = 0; i < BombermanGame.WIDTH * BombermanGame.HEIGHT; i++) {
            adjList0.add(new ArrayList<>());
            adjList.add(new ArrayList<>());
            adjListWallpass0.add(new ArrayList<>());
            adjListWallpass.add(new ArrayList<>());
        }
        for (int i = 0; i < BombermanGame.getMap().size(); i++) {
            for (int j = 0; j < BombermanGame.getMap().get(i).size(); j++) {
                if (BombermanGame.getMap().get(i).get(j) != '#' && BombermanGame.getMap().get(i).get(j) != '*') {
                    addNeighbor(adjList0, j, i);
                    addNeighbor(adjList, j, i);
                    addNeighbor(adjListWallpass0, j, i);
                    addNeighbor(adjListWallpass, j, i);
                }
            }
        }

        for (int i = 0; i < BombermanGame.getMap().size(); i++) {
            for (int j = 0; j < BombermanGame.getMap().get(i).size(); j++) {
                if (BombermanGame.getMap().get(i).get(j) == '*') {
                    addNeighbor(adjListWallpass0, j, i);
                    addNeighbor(adjListWallpass, j, i);
                }
            }
        }
    }

    /**
     * // Ham nay tim duong di tu dinh bat dau den dinh ket thuc.
     * @param adjList danh sach ke
     * @param priorityScores diem uu tien cua cac dinh
     * @param moveOptimizations diem uu tien cua cac dinh (dua vao lan di chuyen cuoi cung)
     * @param u dinh bat dau
     * @param v dinh ket thuc
     * @return ô cuối cùng gần đỉnh bắt đầu.
     */
    public static int findFirstVertexOnShortestPathAstar(List<List<Integer>> adjList, List<Integer> priorityScores,
                                                         List<Integer> moveOptimizations, int u, int v)
    {
        // Để nhìn thấy toàn bộ đường đi của thuật toán A*
        //
        // B1: bỏ toàn bộ comment (từ test0 đến test4)
        //
        // B2: chạy game.

        if (adjList.get(v).isEmpty()) {
            return -1;
        }

        List<Boolean> visited = new ArrayList<>(Collections.nCopies(BombermanGame.WIDTH * BombermanGame.HEIGHT, false));
        List<Boolean> marked = new ArrayList<>(Collections.nCopies(BombermanGame.WIDTH * BombermanGame.HEIGHT, false));
        List<Integer> distTo = new ArrayList<>(Collections.nCopies(BombermanGame.WIDTH * BombermanGame.HEIGHT, 1000000000));
        List<Integer> predecessors  = new ArrayList<>(Collections.nCopies(BombermanGame.WIDTH * BombermanGame.HEIGHT, -1));
        Queue<Integer> pq;
        pq = new PriorityQueue<>(new Comparator<Integer>() {
                public int compare(Integer n1, Integer n2)
                {
                    // Chú ý: Khoang cach giua 2 dinh = khoang cach manhattan giua 2 dinh ke.
                    return (distTo.get(n1) + (int)(MyMath.distanceManhattan(n1, v) * 40 / 39) + priorityScores.get(n1))
                            - (distTo.get(n2) + (int)(MyMath.distanceManhattan(n2, v) * 40 / 39) + priorityScores.get(n2));
                }
            });

        pq.add(u);
        distTo.set(u, 0);
        predecessors.set(u, u);
        marked.set(u, true);

///////////////////////////////////////////////////////////////////////test0
//        List<Character> a = new ArrayList<>(BombermanGame.WIDTH * BombermanGame.HEIGHT);
//        for (int i = 0; i < BombermanGame.HEIGHT; i++) {
//            for (int j = 0; j < BombermanGame.WIDTH; j++) {
//                if (BombermanGame.getMap().get(i).get(j) == '#') {
//                    a.add('#');
//                } else if (BombermanGame.getMap().get(i).get(j) == '*') {
//                    a.add('*');
//                }
//                else {
//                    a.add(' ');
//                }
//            }
//        }
//        a.set(v, 'v');
//        a.set(u, 'u');
///////////////////////////////////////////////////////////////////////test0

        while (pq.size() > 0) {
            int t = pq.remove();
            visited.set(t, true);
            if (t == v) {
                break;
            }
            for (int i = 0; i < adjList.get(t).size(); i++) {
                if (!visited.get(adjList.get(t).get(i))) {
                    if (distTo.get(adjList.get(t).get(i)) > distTo.get(t) + MyMath.MULTIPLIER + moveOptimizations.get(adjList.get(t).get(i))) {
                        distTo.set(adjList.get(t).get(i), distTo.get(t) + MyMath.MULTIPLIER + moveOptimizations.get(adjList.get(t).get(i)));
                        predecessors.set(adjList.get(t).get(i), t);

///////////////////////////////////////////////////////////////////////test1
//                        mark(a, adjList.get(t).get(i));
///////////////////////////////////////////////////////////////////////test1

                    }
                    if (!marked.get(adjList.get(t).get(i))) {
                        marked.set(adjList.get(t).get(i), true);
                        pq.add(adjList.get(t).get(i));
                    }
                }
            }
        }


        if (predecessors.get(v) == -1) {
            return -1;
        }

        int t = v;
        while (predecessors.get(t) != u) {
            t = predecessors.get(t);

///////////////////////////////////////////////////////////////////////test2
//            if (a.get(t) != 'u' && a.get(t) != 'v') {
//                a.set(t, '1');
//            }
///////////////////////////////////////////////////////////////////////test2

        }

///////////////////////////////////////////////////////////////////////test3
//        print(a);
///////////////////////////////////////////////////////////////////////test3

        return t;
    }

///////////////////////////////////////////////////////////////////////test4
//    private static void mark(List<Character> a, int n) {
//        if (a.get(n) != 'u' && a.get(n) != 'v') {
//            a.set(n, '0');
//        }
//    }
//
//    private static void print(List<Character> a) {
//        for (int i = 0; i < BombermanGame.HEIGHT; i++) {
//            for (int j = 0; j < BombermanGame.WIDTH; j++) {
//                int n = MyMath.converPointToInt(j, i);
//                System.out.print(a.get(n));
//            }
//            System.out.println();
//        }
//        System.out.println();
//    }
///////////////////////////////////////////////////////////////////////test4
}
