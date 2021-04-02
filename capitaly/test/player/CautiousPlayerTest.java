package player;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import static main.Main.setupGame;
import game.GameManager;
import game.NotEnoughRandomsException;

import game.environment.PropertyField;
import game.environment.PropertyField.PropertyState;
import game.environment.ServiceField;

public class CautiousPlayerTest {
    @Test
    public void fileTest() throws Exception {
        String gameFile = "tests/player/cautious.txt";
        String rollFile = "tests/player/cautious rolls.txt";
        GameManager game = setupGame(gameFile,rollFile);
        assertEquals(game.getSecondDeadPlayer(false), "B");
    }
    
    @Test
    public void classTest() {
        PropertyField rf1 = new PropertyField();
        PropertyField rf2 = new PropertyField();
        CautiousPlayer p = new CautiousPlayer("A");
        
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
        
        p.step(rf1);
        
        assertEquals(rf1.getState(), PropertyState.BOUGHT);
        assertEquals(rf1.getOwner(), p);
        assertEquals(rf2.getOwner(), null);
        assertEquals(p.getMoney(), 5000);
        
        p.step(rf2);
        
        assertEquals(rf1.getOwner(), p);
        assertEquals(rf2.getOwner(), p);
        assertEquals(p.getMoney(), 4000);
        assertEquals(p.isAlive(), true);
        
        sf = new ServiceField(4500);
        p.step(sf);
        
        assertEquals(p.isAlive(), false);
    }
}
 