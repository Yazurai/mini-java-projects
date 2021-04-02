package field;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;

import static main.Main.setupGame;
import game.environment.LuckyField;
import game.GameManager;
import game.NotEnoughRandomsException;

import player.CautiousPlayer;
import player.GreedyPlayer;
import player.Player;
import player.TacticalPlayer;

public class LuckyFieldTest {
    @Test
    public void fileTest() throws Exception {
        String gameFile = "tests/field/lucky.txt";
        String rollFile = "tests/field/lucky rolls.txt";
        GameManager game = setupGame(gameFile,rollFile);
        assertEquals(game.getSecondDeadPlayer(false), "B");
    }
    
    @Test
    public void classTest() {
        LuckyField lf = new LuckyField(500);
        ArrayList<Player> ps = new ArrayList<>();
        ps.add(new GreedyPlayer("A"));
        ps.add(new CautiousPlayer("B"));
        ps.add(new TacticalPlayer("C"));
        for(Player p : ps){
            assertEquals(p.getMoney(), 10000);
        }
        for(Player p : ps){
            p.step(lf);
        }
        for(Player p : ps){
            assertEquals(p.getMoney(), 10500);
        }
    }
}
 