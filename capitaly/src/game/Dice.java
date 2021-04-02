package game;

import java.util.LinkedList;
import java.util.Random;


public class Dice {
    private boolean isRandom;
    private Random r;
    private LinkedList<Integer> rolls;

    /**
     * Creates a Dice that generates a random roll
     */
    public Dice() {
        this.isRandom = true;
        r = new Random();
    }
    
    /**
     * Creates a Dice from a preset list of rolls
     * @param rolls A queue of the rolls
     */
    public Dice(LinkedList<Integer> rolls) {
        this.isRandom = false;
        this.rolls = rolls;
    }
    
    /**
     * Depending on the setup of the dice, returns the next dice roll(either a random or the next from the queue)
     * @return The next dice roll
     * @throws NotEnoughRandomsException in the case when there are not enough random
     * numbers int the queue
     */
    public int nextRoll() throws NotEnoughRandomsException {
        if(isRandom){
            return r.nextInt(6)+1;
        } else {
            Integer next = rolls.poll();
            if(next == null) {
                throw new NotEnoughRandomsException();
            } else {
                return next;
            }
        }
    }
}
