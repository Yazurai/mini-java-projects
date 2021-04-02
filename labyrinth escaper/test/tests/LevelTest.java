package tests;

import model.Cell;
import model.Direction;
import model.Game;
import model.GameLevel;
import static org.junit.Assert.assertEquals;
import org.junit.Before;

import org.junit.Test;

public class LevelTest {
  
    private GameLevel gl;
    
    @Before
    public void init() {
        gl = new GameLevel(2);
    }
    
    private void setupLevel(){
        Cell[][] grid = gl.grid; 
        
        grid[0][0].walls[1] = false;
        grid[0][0].walls[2] = false;
        
        grid[0][1].walls[2] = false;
        grid[0][1].walls[3] = false;
        
        grid[1][0].walls[0] = false;
        grid[1][0].walls[1] = true;
        
        grid[1][1].walls[0] = false;
        grid[1][1].walls[3] = true;
        
        gl.grid = grid;
    }
    
    @Test
    public void initialize() {
        assertEquals(gl.cols, gl.rows);
        assertEquals(gl.cols, 2);
        
        assertEquals(gl.player.x, 0);
        assertEquals(gl.player.y, 1);
        
        assertEquals(gl.dragon.x, 1);
        assertEquals(gl.dragon.y, 1);
        
        assertEquals(gl.goal.x, 1);
        assertEquals(gl.goal.y, 0);
    }
    
    @Test
    public void gridTest() {
        assertEquals(gl.grid.length, 2);
        assertEquals(gl.grid[0].length, 2);
        
        assertEquals(gl.grid[0][0].getX(), 0);
        assertEquals(gl.grid[0][0].getY(), 0);
        
        assertEquals(gl.grid[1][0].getX(), 0);
        assertEquals(gl.grid[1][0].getY(), 1);
        
        Cell[][] grid = gl.grid; 
        
        grid[0][0].walls[1] = false;
        grid[0][0].walls[2] = false;
        
        grid[0][1].walls[2] = false;
        grid[0][1].walls[3] = false;
        
        grid[1][0].walls[0] = false;
        grid[1][0].walls[1] = true;
        
        grid[1][1].walls[0] = false;
        grid[1][1].walls[3] = true;
        
        gl.grid = grid;
        
        assertEquals(gl.grid[0][0].getWalls()[2], false);
        assertEquals(gl.grid[0][1].getWalls()[3], false);
        assertEquals(gl.grid[1][0].getWalls()[2], true);
        assertEquals(gl.grid[1][1].getWalls()[3], true);
    }
    
    @Test
    public void cellTest() {
        setupLevel();
        assertEquals(gl.grid[1][0].legalMove(Direction.UP), true);
        assertEquals(gl.grid[1][0].legalMove(Direction.RIGHT), false);
        assertEquals(gl.grid[1][0].legalMove(Direction.DOWN), false);
        
        assertEquals(gl.grid[1][0].touches(gl.grid[0][0]), true);
        assertEquals(gl.grid[1][0].touches(gl.grid[1][1]), false);
    }
    
    @Test
    public void moveTest() {
        setupLevel();
        
        assertEquals(gl.player.x, 0);
        assertEquals(gl.player.y, 1);
        
        gl.movePlayer(Direction.RIGHT);
                
        assertEquals(gl.player.x, 0);
        assertEquals(gl.player.y, 1);
        
        gl.movePlayer(Direction.UP);
        
        assertEquals(gl.player.x, 0);
        assertEquals(gl.player.y, 0);
    }
    
    @Test
    public void goalTest() {
        setupLevel();
        
        assertEquals(gl.player.x, 0);
        assertEquals(gl.player.y, 1);
        assertEquals(gl.hasEscaped(), false);
        assertEquals(gl.gameFinished(), false);
        
        gl.movePlayer(Direction.UP);
        
        assertEquals(gl.player.x, 0);
        assertEquals(gl.player.y, 0);
        assertEquals(gl.hasEscaped(), false);
        assertEquals(gl.gameFinished(), false);
        
        gl.movePlayer(Direction.RIGHT);
                
        assertEquals(gl.player.x, 1);
        assertEquals(gl.player.y, 0);
        assertEquals(gl.hasEscaped(), true);
        assertEquals(gl.gameFinished(), true);
    }
    
    @Test
    public void deathTest() {
        setupLevel();
        
        assertEquals(gl.dragon.x, 1);
        assertEquals(gl.dragon.y, 1);
        assertEquals(gl.isDead(), false);
        assertEquals(gl.gameFinished(), false);
        
        gl.dragonDir = Direction.UP;
        gl.moveDragon();
        
        assertEquals(gl.dragon.x, 1);
        assertEquals(gl.dragon.y, 0);
        assertEquals(gl.isDead(), false);
        assertEquals(gl.gameFinished(), false);
        
        gl.dragonDir = Direction.LEFT;
        gl.moveDragon();
                
        assertEquals(gl.dragon.x, 0);
        assertEquals(gl.dragon.y, 0);
        assertEquals(gl.isDead(), true);
        assertEquals(gl.gameFinished(), true);
    }
}
