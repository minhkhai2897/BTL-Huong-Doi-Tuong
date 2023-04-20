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

    public void handleMove() {
        List<Entity> bombers = BombermanGame.getBombers();
        List<List<Integer>> adjListWallpass = BombermanGame.getAdjListWallpass();
        if (bombers.size() < 1) {
            return;
        }
        Bomber bomber = (Bomber) bombers.get(0);

        int u = MyMath.converPointToInt(this.getPosition());
        int v = MyMath.converPointToInt(bomber.getPosition());
        int n = this.findFirstVertexOnShortestPathDijkstra(adjListWallpass, u, v);
        if (n != -1) {
            boolean moved = this.moveToCell(n);
            if (!moved) {
                Point p = MyMath.convertIntToPoint(n);
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
