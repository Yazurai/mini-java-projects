package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLevel {

    public final int rows, cols;
    private final List<Cell> cellList = new ArrayList<>();
    public Cell[][] grid;
    public Position goal;
    public Position player;
    public Position dragon;
    public Direction dragonDir;
    private boolean escaped;
    private boolean dead;

    private Random rand = new Random();

    public GameLevel(int size) {
        this.rows = size;
        this.cols = this.rows;
        this.escaped = false;
        this.dragonDir = Direction.DOWN;
        this.goal = new Position(size-1, 0);
        this.player = new Position(0, size-1);
        this.dragon = new Position(size/2, size/2);
        genBaseGrid(rows, cols);
        new LabGen(cellList, rows);
        convertGrid();
    }

    public GameLevel(GameLevel gl) {
        rows = gl.rows;
        cols = gl.cols;
        escaped = false;
        goal = new Position(gl.goal.x, gl.goal.y);
        player = new Position(gl.player.x, gl.player.y);
        dragon = new Position(gl.dragon.x, gl.dragon.y);
        dragonDir = gl.dragonDir;
    }

    public boolean hasEscaped() {
        return escaped;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean gameFinished() {
        return dead || escaped;
    }

    private boolean isValidPosition(Position p) {
        return (p.x >= 0 && p.y >= 0 && p.x < cols && p.y < rows);
    }

    private boolean isFree(Position p, Direction d) {
        if (!isValidPosition(p.translate(d))) {
            return false;
        } else {
            return grid[p.y][p.x].legalMove(d);
        }
    }

    public void movePlayer(Direction d) {
        Position curr = player;
        Position next = curr.translate(d);
        if (!gameFinished() && isFree(curr, d)) {
            player = next;
            escaped = player.x == goal.x && player.y == goal.y;
            checkDeath();
        }
    }

    public void moveDragon() {
        Position curr = dragon;
        boolean validDirection = false;
        while (!validDirection) {
            int i = rand.nextInt(100);
            if (55 <= i && i < 75) {
                dragonDir = dragonDir.turnLeft();
            } else if (75 <= i && i < 95) {
                dragonDir = dragonDir.turnRight();
            } else if (95 <= i) {
                dragonDir = dragonDir.turnOpposite();
            }
            validDirection = isFree(curr, dragonDir);
        }

        Position next = curr.translate(dragonDir);
        if (!gameFinished()) {
            dragon = next;
            checkDeath();
        }
    }

    private void checkDeath() {
        dead = grid[player.y][player.x].touches(grid[dragon.y][dragon.x]);
    }

    // GENERATION --------------------------------------------------------------------------------------------------------------------------
    private void genBaseGrid(int rows, int cols) {
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                cellList.add(new Cell(x, y));
            }
        }
    }

    private void convertGrid() {
        grid = new Cell[cols][cols];
        for (Cell c : cellList) {
            grid[c.getY()][c.getX()] = c;
        }
    }
}
