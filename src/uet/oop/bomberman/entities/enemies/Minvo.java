package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.MyMath;
import uet.oop.bomberman.animation.MinvoAnimation;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomber.Bomber;

import java.awt.*;
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
        int n = this.findFirstVertexOnShortestPathAstar(adjListWallpass, u, v);
        if (n != -1) {
            boolean moved = this.moveToCell(n);
            if (!moved) {
                Point p = MyMath.convertIntToPoint(n);
                BombermanGame.removeNeighbor(adjList, p);
                BombermanGame.removeNeighbor(adjListWallpass, p);
//                if (u == n) {
//                    this.autoMoveToPlayer();
//                    return;
//                }
                this.handleMove();
            }
            return;
        }
        this.autoMoveToPlayer();
    }
}
