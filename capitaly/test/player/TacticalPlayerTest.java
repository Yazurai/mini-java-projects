package player;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import static main.Main.setupGame;
import game.GameManager;
import game.NotEnoughRandomsException;

import game.environment.PropertyField;
import game.environment.PropertyField.PropertyState;
import game.environment.ServiceField;

public class TacticalPlayerTest {
    @Test
    public void fileTest() throws Exception {
        String gameFile = "tests/player/tactical.txt";
        String rollFile = "tests/player/tactical rolls.txt";
        GameManager game = setupGame(gameFile,rollFile);
        assertEquals(game.getSecondDeadPlayer(false), "A");
    }
    
    @Test
    public void classTest() {
        PropertyField rf1 = new PropertyField();
        PropertyField rf2 = new PropertyField();
        TacticalPlayer p = new TacticalPlayer("A");
        
        assertEquals(rf1.getOwner(), null);
        assertEquals(rf2.getOwner(), null);
        assertEquals(p.getMoney(), 10000);
        
        p.step(rf1);
        
        assertEquals(rf1.getOwner(), p);
        assertEquals(rf2.getOwner(), null);
        assertEquals(p.getMoney(), 9000);
        
        ServiceField sf = new ServiceField(4000);
        p.step(sf);
        
        assertEquals(p.getMoney(), 5000);
        
        p.step(rf2);
        
        assertEquals(rf1.getOwner(), p);
        assertEquals(rf2.getOwner(), null);
        assertEquals(p.getMoney(), 5000);
        
        p.step(rf1);
        
        assertEquals(rf1.getState(), PropertyState.SINGLE);
        assertEquals(rf1.getOwner(), p);
        assertEquals(rf2.getOwner(), null);
        assertEquals(p.getMoney(), 1000);
        assertEquals(p.isAlive(), true);
        
        sf = new ServiceField(1500);
        p.step(sf);
        
        assertEquals(p.isAlive(), false);
    }
}
 