package model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import res.ResourceLoader;

public class Game {

    private GameLevel gameLevel = null;
    private int levelsCleared;

    private final int size;

    public Game(int size) {
        this.size = size;
        newGame();
    }

    public void newGame() {
        levelsCleared = 0;
        loadLevel();
    }

    private void loadLevel() {
        gameLevel = new GameLevel(size);
    }

    public void clearLevel() {
        levelsCleared++;
        loadLevel();
    }

    public void stepPlayer(Direction d) {
        gameLevel.movePlayer(d);
    }

    public void stepDragon() {
        gameLevel.moveDragon();
    }

    public boolean isLevelLoaded() {
        return gameLevel != null;
    }

    public int getLevelRows() {
        return gameLevel.rows;
    }

    public int getLevelCols() {
        return gameLevel.cols;
    }

    public Cell getCell(int x, int y) {
        return gameLevel.grid[y][x];
    }

    public boolean hasEscaped() {
        return (gameLevel != null) ? gameLevel.hasEscaped() : false;
    }

    public boolean isDead() {
        return (gameLevel != null) ? gameLevel.isDead() : false;
    }

    public boolean gameFinished() {
        return (gameLevel != null) ? gameLevel.gameFinished() : false;
    }

    public int getLevelsCleared() {
        return levelsCleared;
    }

    public Position getPlayerPos() {
        return new Position(gameLevel.player.x, gameLevel.player.y);
    }

    public Position getDragonPos() {
        return new Position(gameLevel.dragon.x, gameLevel.dragon.y);
    }

    public Position getGoalPos() {
        return new Position(gameLevel.goal.x, gameLevel.goal.y);
    }
}
