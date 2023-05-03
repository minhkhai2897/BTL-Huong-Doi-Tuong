package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.Astar;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.MyMath;
import uet.oop.bomberman.animation.MinvoAnimation;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomber.Bomber;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Minvo extends Enemy {
    public Minvo(int x, int y, Image img) {
        super(x, y, img);
        this.setSpeed(2);
        this.wallPass = true;
        this.animation = new MinvoAnimation();
    }

    public void update() {
        this.ableToMoveLeft = true;
        this.ableToMoveRight = true;
        this.ableToMoveDown = true;
        this.ableToMoveUp = true;

        this.moveOptimizations = this.updateMoveOptimizationsList();
        this.handleCollision();
        this.handleMove();
        this.move();
        this.animation.setSprite(this);
    }

    /*
     * Ham nay duoc chia lam hai phan
     * Neu ham tim duong di ngan nhat bang thuat toan dijkstra tra ve duong di thì se di chuyen theo duong do
     * Neu ko se di chuyen theo ham autoMoveToPlayer
     */
    public void handleMove() {
        List<Entity> bombers = BombermanGame.getBombers();
        List<List<Integer>> adjList = Astar.getAdjList();
        List<List<Integer>> adjListWallpass = Astar.getAdjListWallpass();
        if (bombers.size() < 1) {
            return;
        }
        Bomber bomber = (Bomber) bombers.get(0);

        int u = MyMath.converPointToInt(this.getPosition());
        int v = MyMath.converPointToInt(bomber.getPosition());
        List<Integer> priorityScores;
        if (this.id % 2 == 1 && (MyMath.distanceManhattan(u, v) / MyMath.MULTIPLIER <= 6)) {
            priorityScores = BombermanGame.getPriorityScores1();
        } else {
            priorityScores = BombermanGame.getPriorityScores();
        }
        int n = Astar.findFirstVertexOnShortestPathAstar(adjListWallpass, priorityScores, this.moveOptimizations, u, v);
        if (n != -1) {
            boolean moved = this.moveToCell(n);
            if (!moved) {
                Point p = MyMath.convertIntToPoint(n);
                Astar.removeNeighbor(adjList, p);
                Astar.removeNeighbor(adjListWallpass, p);
                this.autoMoveToPlayer();
            }
            return;
        }
        this.autoMoveToPlayer();
    }
}
