package uet.oop.bomberman.entities.enemies;

import java.awt.*;
import java.util.List;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.MyMath;
import uet.oop.bomberman.animation.DollAnimation;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomber.Bomber;

public class Doll extends Enemy {
    public Doll(int x, int y, Image img) {
        super(x, y, img);
        this.setSpeed(2);
        this.animation = new DollAnimation();
    }

    public void update() {
        this.ableToMoveDown = true;
        this.ableToMoveUp = true;
        this.ableToMoveLeft = true;
        this.ableToMoveRight = true;
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
        List<List<Integer>> adjList = BombermanGame.getAdjList();
        List<List<Integer>> adjListWallpass = BombermanGame.getAdjListWallpass();
        if (bombers.size() < 1) {
            return;
        }
        Bomber bomber = (Bomber) bombers.get(0);

        int u = MyMath.converPointToInt(this.getPosition());
        int v = MyMath.converPointToInt(bomber.getPosition());
        int n = this.findFirstVertexOnShortestPathDijkstra(adjList, u, v);
        if (n != -1) {
            boolean moved = this.moveToCell(n);
            if (!moved) {
                Point p = MyMath.convertIntToPoint(n);
                BombermanGame.removeNeighbor(adjList, p);
                BombermanGame.removeNeighbor(adjListWallpass, p);
                if (u == n) {
                    this.autoMoveToPlayer();
                    return;
                }
                this.handleMove();
            }
            return;
        }

        this.autoMoveToPlayer();
    }
}
