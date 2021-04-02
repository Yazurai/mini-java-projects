package game;

import game.environment.Field;
import game.environment.PropertyField;
import java.util.ArrayList;
import java.util.LinkedList;
import player.Player;

public class GameManager {
    private ArrayList<Field> fields;
    private ArrayList<Player> players;
    private int currentPlayer;
    private Dice d;

    /**
     * Creates a game manager with a randomized dice
     * @param fields A list of fields that will form the playground
     * @param players A list of players to play the game
     */
    public GameManager(ArrayList<Field> fields, ArrayList<Player> players) {
        this.fields = fields;
        this.players = players;
        this.currentPlayer = 0;
        this.d = new Dice();
    }
    
    /**
     * Created a game manager with a list of preset dice rolls
     * @param fields A list of fields that will form the playground
     * @param players A list of players to play the game
     * @param rolls The queue of the preset list of dice rolls
     */
    public GameManager(ArrayList<Field> fields, ArrayList<Player> players, LinkedList<Integer> rolls) {
        this.fields = fields;
        this.players = players;
        this.currentPlayer = 0;
        this.d = new Dice(rolls);
    }
     
    /**
     * Moves the next player with the amount the dice rolls
     * @throws NotEnoughRandomsException when the dice is configured to be pre-set and there are no available rolls
     */
    private void moveNextPlayer() throws NotEnoughRandomsException {
        int distance = 0;
        try{
            distance = d.nextRoll();
        } catch(NotEnoughRandomsException e) {
            throw e;
        }
        Player p = players.get(currentPlayer);
        p.move(distance, fields.size());
        p.step(fields.get(p.getLocation()));
    }
    
    /**
     * Returns the second player to be eliminated with the given game settings
     * @param shouldPrint Whether to display game state information after each move
     * @return The name of the player that got eliminated second
     * @throws NotEnoughRandomsException when there were not enough random numbers supplied to finish the simulation
     */
    public String getSecondDeadPlayer(boolean shouldPrint) throws NotEnoughRandomsException {
        if(players.size() < 3){
            return null;
        }
        
        int count = 0;
        String name = "";
        while(count < 2){
            try{
                moveNextPlayer();
            } catch(NotEnoughRandomsException e) {
                throw e;
            }
            
            Player p = players.get(currentPlayer);
            if(!p.isAlive()){
                count++;
                name = p.getName();
                players.remove(p);
                for(Field f : fields){
                    if(f instanceof PropertyField) {
                        PropertyField rf = (PropertyField)f;
                        rf.reset(p);
                    }
                }
            } else {
                currentPlayer++;
            }
            currentPlayer %= players.size();
            if(shouldPrint){
                print();
            }
        }
        return name;
    }
    
    /**
     * Prints out the current game state to the console.
     * First the player names, their location and money.
     * Then the fields, their levels(if a residence) and their owners.
     */
    private void print(){
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            System.out.println(p.getName() + " - " + p.getLocation() + " - " + p.getMoney());
        }
        for (int i = 0; i < fields.size(); i++) {
            Field f = fields.get(i);
            System.out.print(i + "-" +f.toString() + "\t");
        }
        System.out.println();
        for (int i = 0; i < fields.size(); i++) {
            Field f = fields.get(i);
            if(f instanceof PropertyField){
                PropertyField rf = (PropertyField)f;
                System.out.print(rf.getState().ordinal() + "\t");
            } else {
                System.out.print("\t");
            }
        }
        System.out.println();
        for (int i = 0; i < fields.size(); i++) {
            Field f = fields.get(i);
            if(f instanceof PropertyField){
                PropertyField rf = (PropertyField)f;
                if(rf.getOwner() != null) {
                    System.out.print(rf.getOwner().getName() + "\t");
                } else {
                    System.out.print("\t");
                }
            } else {
                System.out.print("\t");
            }
        }
        System.out.println();
        System.out.println("-------------------------------------------------------------------------------------------------------------");
    }
}
