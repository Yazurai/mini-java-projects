package game;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import static main.Main.setupGame;

import java.util.Arrays;
import java.util.Collection;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class GameTest {
    @Parameterized.Parameters( name = "{0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {     
                 {"1", "C"},
           });
    }
    
    private final String inputFile;
    private final String expected;
    
    public GameTest(String input, String expected) {
        this.inputFile = input;
        this.expected = expected;
    }
    
    @Test
    public void fileTest() throws Exception {
        String gameFile = "tests/game/" + inputFile + ".txt";
        String rollFile = "tests/game/" + inputFile + " rolls.txt";
        GameManager game = setupGame(gameFile,rollFile);
        
        assertEquals(game.getSecondDeadPlayer(false), expected);
    }
}
