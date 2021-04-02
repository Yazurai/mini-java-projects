package tests;

import java.util.ArrayList;
import java.util.Arrays;
import model.Game;
import static org.junit.Assert.assertEquals;
import org.junit.Before;

import org.junit.Test;

public class GameTest {
    private Game g;
    
    @Before
    public void init() {
        g = new Game(20);
    }
    
    @Test
    public void initialize() {
        assertEquals(g.getLevelCols(),20);
        assertEquals(g.getLevelRows(),20);
        assertEquals(g.getLevelsCleared(),0);
    }
    
    @Test
    public void clearTest() {
        assertEquals(g.getLevelsCleared(),0);
        
        g.clearLevel();
        
        assertEquals(g.getLevelsCleared(),1);
    }
    
    @Test
    public void deathTest() {
        assertEquals(g.getLevelsCleared(),0);
        
        g.clearLevel();
        g.clearLevel();
        
        assertEquals(g.getLevelsCleared(),2);
        
        g.newGame();
        
        assertEquals(g.getLevelsCleared(),0);
    }
}
