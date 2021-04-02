package main;

import game.*;
import game.environment.Field;
import game.environment.LuckyField;
import game.environment.PropertyField;
import game.environment.ServiceField;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;
import player.CautiousPlayer;
import player.GreedyPlayer;
import player.Player;
import player.TacticalPlayer;


public class Main {
    public static void main(String[] args) {
        try{
            GameManager game  = setupGame("tests/test.txt","");
            String second = game.getSecondDeadPlayer(true);
            if(second == null) {
                System.out.println("There weren't enough players");
            } else {
                System.out.println(game.getSecondDeadPlayer(true));
            }
        } catch (InputMismatchException | InvalidInputException e) {
            System.err.println("File format is incorrect");
        } catch (FileNotFoundException e) {
            System.err.println("File does not exist");
        } catch (NotEnoughRandomsException e) {
            System.err.println("Not enough randoms");
        }
    }
    
    /**
     * Sets up a game from a given gamefile and if it exists, then configures the game
     * to have a pre-set list of dice rolls.
     * @param gameFile The path to the game config
     * @param rollsFile The path the the file that holds the list of rolls
     * @return A ready to go game manager
     */
    public static GameManager setupGame(String gameFile, String rollsFile) 
            throws InputMismatchException, InvalidInputException, FileNotFoundException {
        ArrayList<Field> fields = new ArrayList<>();
        ArrayList<Player> players = new ArrayList<>();
        GameManager gm = null;
        try {
            Scanner sc = new Scanner(new BufferedReader(new FileReader(gameFile)));
            int playerCount = sc.nextInt();
            for (int i = 0; i < playerCount; i++) {
                String name = sc.next();
                switch (sc.next()) {
                    case "Greedy":
                        players.add(new GreedyPlayer(name));
                        break;
                    case "Cautious":
                        players.add(new CautiousPlayer(name));
                        break;
                    case "Tactical":
                        players.add(new TacticalPlayer(name));
                        break;
                    default:
                        throw new InvalidInputException();
                }
            }
            int fieldCount = sc.nextInt();
            for (int i = 0; i < fieldCount; i++) {
                switch (sc.next()) {
                    case "Property":
                        fields.add(new PropertyField());
                        break;
                    case "Lucky":
                        fields.add(new LuckyField(sc.nextInt()));
                        break;
                    case "Service":
                        fields.add(new ServiceField(sc.nextInt()));
                        break;
                    default:
                        throw new InvalidInputException();
                }
            }
        } /*catch (InputMismatchException | InvalidInputException e) {
            System.err.println("File format is incorrect.");
        } catch (FileNotFoundException e) {
            System.err.println("File does not exist.");
        }*/ catch (FileNotFoundException | IllegalArgumentException | InvalidInputException e){
            throw e;
        }
        LinkedList<Integer> rolls = new LinkedList<>();
        try {
            Scanner sc = new Scanner(new BufferedReader(new FileReader(rollsFile)));
            while (sc.hasNext()) {
                int nextRoll = sc.nextInt();
                if (0 < nextRoll && nextRoll < 7) {
                    rolls.add(nextRoll);
                } else {
                    throw new InvalidInputException();
                }
            }
            gm = new GameManager(fields, players, rolls);
        } catch (InputMismatchException | InvalidInputException e) {
            throw e;
        } catch (FileNotFoundException e) {
            gm = new GameManager(fields, players);
        }
        return gm;
    }
    
}
