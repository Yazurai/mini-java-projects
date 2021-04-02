package game;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import static main.Main.setupGame;
import java.io.FileNotFoundException;
import main.InvalidInputException;

public class ExceptionTest {
    @Test(expected = FileNotFoundException.class)
    public void fileNotFoundTest() throws Exception {
        String gameFile = "aaaaaaaaa";
        String rollFile = "tests/game/ rolls.txt";
        GameManager game = setupGame(gameFile,rollFile);
        game.getSecondDeadPlayer(false);
    }
    
    @Test(expected = InvalidInputException.class)
    public void wrongFieldTest() throws Exception {
        String gameFile = "tests/game/incorrect field.txt";
        String rollFile = "tests/game/incorrect field rolls.txt";
        GameManager game = setupGame(gameFile,rollFile);
        game.getSecondDeadPlayer(false);
    }
    
    @Test(expected = InvalidInputException.class)
    public void wrongDiceTest() throws Exception {
        String gameFile = "tests/game/incorrect dice.txt";
        String rollFile = "tests/game/incorrect dice rolls.txt";
        GameManager game = setupGame(gameFile,rollFile);
        game.getSecondDeadPlayer(false);
    }
    
    @Test(expected = NotEnoughRandomsException.class)
    public void notEnoughRandomTest() throws Exception {
        String gameFile = "tests/game/not enough random.txt";
        String rollFile = "tests/game/not enough random rolls.txt";
        GameManager game = setupGame(gameFile,rollFile);
        game.getSecondDeadPlayer(false);
    }
    
    @Test
    public void notEnoughPlayers() throws Exception {
        String gameFile = "tests/game/not enough players.txt";
        String rollFile = "tests/game/not enough players rolls.txt";
        GameManager game = setupGame(gameFile,rollFile);
        String res = game.getSecondDeadPlayer(false);
        assertEquals(res, null);
    }
}
