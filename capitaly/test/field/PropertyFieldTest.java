package field;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import static main.Main.setupGame;
import game.environment.PropertyField;
import game.GameManager;
import game.NotEnoughRandomsException;

import player.GreedyPlayer;

public class PropertyFieldTest {
    @Test
    public void fileTest() throws Exception {
        String gameFile = "tests/field/property.txt";
        String rollFile = "tests/field/property rolls.txt";
        GameManager game = setupGame(gameFile,rollFile);
        assertEquals(game.getSecondDeadPlayer(false), "C");
    }
    
    @Test
    public void classTest() {
        PropertyField rf = new PropertyField();
        GreedyPlayer a = new GreedyPlayer("A");
        GreedyPlayer b = new GreedyPlayer("B");
        
        //A steps on residence and buys it
        assertEquals(a.getMoney(), 10000);
        assertEquals(rf.getCost(a), 1000);
        a.step(rf);     
        assertEquals(a.getMoney(), 9000);
        
        //B steps on residence and pays A
        assertEquals(b.getMoney(), 10000);
        b.step(rf);
        assertEquals(rf.getCost(b), 0);
        assertEquals(b.getMoney(), 9500);
        assertEquals(a.getMoney(), 9500);
        
        //A steps on residence and upgrades it
        assertEquals(a.getMoney(), 9500);
        assertEquals(rf.getCost(a), 4000);
        a.step(rf);
        assertEquals(a.getMoney(), 5500);
        
        //B steps on residence and pays A
        assertEquals(b.getMoney(), 9500);
        b.step(rf);
        assertEquals(b.getMoney(), 7500);
        assertEquals(a.getMoney(), 7500);
        
        //A steps on residence and upgrades it but nothing happens
        assertEquals(a.getMoney(), 7500);
        assertEquals(rf.getCost(a), 0);
        a.step(rf);
        assertEquals(a.getMoney(), 7500);
    }
}
 