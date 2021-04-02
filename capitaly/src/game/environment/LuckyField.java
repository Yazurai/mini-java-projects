package game.environment;

import player.Player;

public class LuckyField extends Field {
    private int reward;

    /**
     * Creates a lucky field that grants money
     * @param reward The amount of money to give to the player whenever they step on the field
     * @throws IllegalArgumentException When the given reward amount is negative
     */
    public LuckyField(int reward) throws IllegalArgumentException {
        if(reward < 0){
            throw new IllegalArgumentException();
        } else {
            this.reward = reward;
        }
    }
    
    /**
     * Gives a money to the player that steps on the field
     * @param p The player that stepped onto the field
     * @return The amount the player receives when stepping onto this field
     */
    @Override
    public int onPlayerHit(Player p){
        return reward;
    }
    
    @Override
    public String toString(){
        return "Luc";
    }
}
